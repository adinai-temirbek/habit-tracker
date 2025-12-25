package com.example.habittracker.controller;

import com.example.habittracker.model.Goal;
import com.example.habittracker.model.User;
import com.example.habittracker.service.GoalService;
import com.example.habittracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goals")
public class GoalController {

    private final GoalService goalService;
    private final UserService userService;

    @Autowired
    public GoalController(GoalService goalService, UserService userService) {
        this.goalService = goalService;
        this.userService = userService;
    }

    // Create a goal for the authenticated user
    @PostMapping
    public ResponseEntity<Goal> createGoal(@RequestBody Goal goal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // Fetch email from authentication
        User user = userService.findByEmail(email); // Load the actual User entity

        goal.setUser(user); // Assign user to goal
        Goal savedGoal = goalService.createGoal(goal);

        return ResponseEntity.ok(savedGoal);
    }

    // Fetch goals for the authenticated user
    @GetMapping("/my")
    public ResponseEntity<List<Goal>> getMyGoals() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        List<Goal> goals = goalService.getGoalsByUserId(user.getId());
        return ResponseEntity.ok(goals);
    }
}
