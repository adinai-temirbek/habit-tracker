package com.example.habittracker.service;

import com.example.habittracker.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    List<User> getAllUsers();

    // NEW: find a user by email for authentication
    User findByEmail(String email);
}
