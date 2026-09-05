package com.pcrms.controller;

import com.pcrms.model.User;
import com.pcrms.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // GET ALL USERS
    // =========================

    @GetMapping("/users")
    public List<User> getUsers() {

        return userRepository.findAll();
    }

    // =========================
    // DASHBOARD STATISTICS
    // =========================

    @GetMapping("/stats")
    public AdminStats getStats() {

        long totalUsers = userRepository.count();

        long inspectors =
                userRepository.countByRole(User.Role.INSPECTOR);

        long clerks =
                userRepository.countByRole(User.Role.CLERK);

        long activeUsers =
                userRepository.countByActive(true);

        return new AdminStats(
                totalUsers,
                inspectors,
                clerks,
                activeUsers
        );
    }

    // =========================
    // CREATE USER
    // =========================

    @PostMapping("/users")
    public User createUser(@RequestBody CreateUserRequest request) {

        if (userRepository.findByUsername(request.username()).isPresent()) {

            throw new RuntimeException("Username already exists");
        }

        User user = new User();

        user.setUsername(request.username());
        user.setPassword(
                passwordEncoder.encode(request.password())
        );
        user.setFullName(request.fullName());
        user.setRole(request.role());
        user.setActive(true);

        return userRepository.save(user);
    }

    // =========================
    // ACTIVATE / DEACTIVATE USER
    // =========================

    @PutMapping("/users/{id}/status")
    public User updateUserStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setActive(active);

        return userRepository.save(user);
    }

    // =========================
    // REQUEST RECORDS
    // =========================

    public record CreateUserRequest(
            String username,
            String password,
            String fullName,
            User.Role role
    ) {}

    public record AdminStats(
            long totalUsers,
            long inspectors,
            long clerks,
            long activeUsers
    ) {}
}