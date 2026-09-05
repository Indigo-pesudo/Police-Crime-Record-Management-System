package com.pcrms.controller;

import com.pcrms.model.User;
import com.pcrms.repository.UserRepository;
import com.pcrms.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        User user = userRepository
                .findByUsername(authentication.getName())
                .orElseThrow();

        String token = jwtService.generateToken(
                user.getUsername(),
                user.getRole().name()
        );

        return new LoginResponse(
                user.getUsername(),
                user.getFullName(),
                user.getRole().name(),
                token
        );
    }

    public record LoginRequest(
            String username,
            String password
    ) {}

    public record LoginResponse(
            String username,
            String fullName,
            String role,
            String token
    ) {}
}