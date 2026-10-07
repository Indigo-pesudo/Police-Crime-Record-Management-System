package com.pcrms.controller;

import com.pcrms.dto.UserResponse;
import com.pcrms.model.User;
import com.pcrms.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------- GET ALL USERS ----------------

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {

        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getUsername(),
                        user.getFullName(),
                        user.getRole(),
                        user.isActive()
                ))
                .toList();

        return ResponseEntity.ok(users);
    }

    // ---------------- ADMIN STATISTICS ----------------

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {

        long totalUsers = userRepository.count();
        long inspectors = userRepository.countByRole(User.Role.INSPECTOR);
        long clerks = userRepository.countByRole(User.Role.CLERK);
        long activeUsers = userRepository.countByActive(true);

        return ResponseEntity.ok(Map.of(
                "totalUsers", totalUsers,
                "inspectors", inspectors,
                "clerks", clerks,
                "activeUsers", activeUsers
        ));
    }

    // ---------------- CREATE USER ----------------

    @PostMapping("/users")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        // Admin is NOT allowed to create another ADMIN
        if (request.role() == User.Role.ADMIN) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "Admin users cannot be created through this API"
                    ));
        }

        // Username must be unique
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message",
                            "Username already exists"
                    ));
        }

        User user = new User(
                request.username(),
                passwordEncoder.encode(request.password()),
                request.fullName(),
                request.role(),
                true
        );

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getFullName(),
                savedUser.getRole(),
                savedUser.isActive()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ---------------- ACTIVATE / DEACTIVATE USER ----------------

    @PutMapping("/users/{id}/status")
    public ResponseEntity<?> updateUserStatus(
            @PathVariable Long id,
            @RequestParam boolean active) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                            "message",
                            "User not found"
                    ));
        }

        // Prevent deactivating the main admin account
        if ("admin".equalsIgnoreCase(user.getUsername()) && !active) {
            return ResponseEntity.badRequest()
                    .body(Map.of(
                            "message",
                            "The main admin account cannot be deactivated"
                    ));
        }

        user.setActive(active);
        userRepository.save(user);

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        active ? "User activated" : "User deactivated"
                )
        );
    }

    // ---------------- CREATE USER DTO ----------------

    public record CreateUserRequest(

            @NotBlank(message = "Username is required")
            @Size(min = 3, max = 50,
                    message = "Username must be between 3 and 50 characters")
            String username,

            @NotBlank(message = "Password is required")
            @Size(min = 8, max = 100,
                    message = "Password must be between 8 and 100 characters")
            String password,

            @NotBlank(message = "Full name is required")
            @Size(max = 100,
                    message = "Full name must not exceed 100 characters")
            String fullName,

            @NotNull(message = "Role is required")
            User.Role role
    ) {
    }
}