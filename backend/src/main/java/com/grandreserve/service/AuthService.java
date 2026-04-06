package com.grandreserve.service;

import com.grandreserve.dto.LoginRequest;
import com.grandreserve.dto.LoginResponse;
import com.grandreserve.dto.RegisterRequest;
import com.grandreserve.entity.User;
import com.grandreserve.repository.UserRepository;
import com.grandreserve.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil         = jwtUtil;
    }

    /**
     * Login for both customers (identifier = email) and staff (identifier = username).
     */
    public LoginResponse login(LoginRequest req) {
        // Try email first, then username
        User user = userRepository.findByEmail(req.getIdentifier())
                .or(() -> userRepository.findByUsername(req.getIdentifier()))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");

        if (!user.getRole().name().equalsIgnoreCase(req.getRole()))
            throw new RuntimeException("Wrong role selected for this account");

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new LoginResponse(token, user.getId(), user.getUsername(),
                                 user.getName(), user.getRole().name(), user.getEmail());
    }

    /**
     * Self-registration for CUSTOMERS only via the UI.
     * Management staff must be created via DataSeeder or the /api/auth/admin/create-staff endpoint.
     */
    public LoginResponse register(RegisterRequest req) {
        String email = req.getEmail().trim().toLowerCase();

        if (userRepository.existsByEmail(email))
            throw new RuntimeException("An account with this email already exists.");

        if (req.getPassword() == null || req.getPassword().length() < 6)
            throw new RuntimeException("Password must be at least 6 characters.");

        User user = User.builder()
                .username(email)           // use email as username
                .email(email)
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName().trim())
                .role(User.Role.CUSTOMER)  // always CUSTOMER — staff cannot self-register
                .build();
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole().name());
        return new LoginResponse(token, user.getId(), user.getUsername(),
                                 user.getName(), user.getRole().name(), user.getEmail());
    }
}
