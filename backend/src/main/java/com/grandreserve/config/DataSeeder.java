package com.grandreserve.config;

import com.grandreserve.entity.User;
import com.grandreserve.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds one default management staff account on first startup.
 *
 * To add more staff accounts:
 *   Option A — add another block here and restart the app.
 *   Option B — POST /api/auth/admin/create-staff with a management JWT token.
 *
 * Default staff credentials:
 *   Username : staff1
 *   Password : staff123
 */
@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository  userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("staff1")) {
            userRepository.save(User.builder()
                .username("staff1")
                .email("staff@grandreserve.com")
                .password(passwordEncoder.encode("staff123"))
                .name("Harman Saini")
                .role(User.Role.MANAGEMENT)
                .build());
            System.out.println("[GrandReserve] Default staff account seeded → username: staff1 / password: staff123");
        }
    }
}
