package com.pcrms.config;

import com.pcrms.model.User;
import com.pcrms.repository.UserRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Value("${PCRMS_ADMIN_PASSWORD}")
    private String adminPassword;

    @Bean
    CommandLineRunner initializeAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            User admin = userRepository
                    .findByUsername("admin")
                    .orElse(null);

            if (admin == null) {

                admin = new User(
                        "admin",
                        passwordEncoder.encode(adminPassword),
                        "System Administrator",
                        User.Role.ADMIN,
                        true
                );

            } else {

                admin.setPassword(
                        passwordEncoder.encode(adminPassword)
                );

                admin.setActive(true);
            }

            userRepository.save(admin);

            System.out.println("PCRMS Admin password updated.");
        };
    }
}