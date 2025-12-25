package com.example.habittracker.service.impl;

import com.example.habittracker.model.User;
import com.example.habittracker.repository.UserRepository;
import com.example.habittracker.security.AuthenticationRequest;
import com.example.habittracker.security.AuthenticationResponse;
import com.example.habittracker.security.JwtService;
import com.example.habittracker.security.RegisterRequest;
import com.example.habittracker.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Fetch user from DB
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Debug output to check password
        System.out.println("Login attempt for email: " + request.getEmail());
        System.out.println("Raw password input: " + request.getPassword());
        System.out.println("Encoded password in DB: " + user.getPassword());
        System.out.println("Password matches? " + passwordEncoder.matches(request.getPassword(), user.getPassword()));

        try {
            // Proceed with authentication
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            // Log exact reason for failure
            System.err.println("Authentication failed for email " + request.getEmail() + ": " + e.getMessage());
            throw new RuntimeException("Invalid email or password"); // Will return 401 in controller
        }

        // Generate JWT if authentication succeeds
        String jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }
}
