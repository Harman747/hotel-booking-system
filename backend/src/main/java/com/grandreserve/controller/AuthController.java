package com.grandreserve.controller;

import com.grandreserve.dto.LoginRequest;
import com.grandreserve.dto.LoginResponse;
import com.grandreserve.dto.RegisterRequest;
import com.grandreserve.entity.User;
import com.grandreserve.repository.UserRepository;
import com.grandreserve.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService    authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authService     = authService;
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Used by both customers (email) and staff (username) */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    /** Customer self-registration — always creates CUSTOMER role */
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    /**
     * Admin-only endpoint for creating management staff accounts.
     *
     * How to use:
     *   POST /api/auth/admin/create-staff
     *   Header: Authorization: Bearer <management_jwt_token>
     *   Body: { "name": "...", "username": "...", "password": "..." }
     *
     * Only existing MANAGEMENT users can call this endpoint.
     */
    @PostMapping("/admin/create-staff")
    @PreAuthorize("hasRole('MANAGEMENT')")
    public ResponseEntity<Map<String, Object>> createStaff(@RequestBody CreateStaffRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            Map<String, Object> err = new HashMap<>();
            err.put("error", "Username already taken");
            return ResponseEntity.badRequest().body(err);
        }

        User staff = User.builder()
                .username(req.getUsername())
                .email(req.getEmail() != null ? req.getEmail() : null)
                .password(passwordEncoder.encode(req.getPassword()))
                .name(req.getName())
                .role(User.Role.MANAGEMENT)
                .build();
        userRepository.save(staff);

        Map<String, Object> res = new HashMap<>();
        res.put("message",  "Staff account created successfully");
        res.put("username", staff.getUsername());
        res.put("name",     staff.getName());
        res.put("role",     "MANAGEMENT");
        return ResponseEntity.ok(res);
    }

    public static class CreateStaffRequest {
        private String name, username, password, email;
        public CreateStaffRequest() {}
        public String getName()     { return name; }
        public String getUsername() { return username; }
        public String getPassword() { return password; }
        public String getEmail()    { return email; }
        public void setName(String v)     { this.name = v; }
        public void setUsername(String v) { this.username = v; }
        public void setPassword(String v) { this.password = v; }
        public void setEmail(String v)    { this.email = v; }
    }
}
