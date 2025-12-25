package com.example.habittracker.service;

import com.example.habittracker.security.AuthenticationRequest;
import com.example.habittracker.security.AuthenticationResponse;
import com.example.habittracker.security.RegisterRequest;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}