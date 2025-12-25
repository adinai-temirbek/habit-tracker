package com.example.habittracker.controller;

import com.example.habittracker.dto.HabitRequestDto;
import com.example.habittracker.model.Goal;
import com.example.habittracker.model.Habit;
import com.example.habittracker.model.User;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.service.HabitService;
import com.example.habittracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;
    private final GoalRepository goalRepository;
    private final UserService userService;

    @Autowired
    public HabitController(HabitService habitService, GoalRepository goalRepository, UserService userService) {
        this.habitService = habitService;
        this.goalRepository = goalRepository;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Habit> createHabit(@RequestBody HabitRequestDto dto) {
        // Get the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        // Find the goal and check ownership
        Optional<Goal> optionalGoal = goalRepository.findById(dto.getGoalId());
        if (optionalGoal.isEmpty() || !optionalGoal.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden if goal doesn't belong to user
        }

        Habit habit = new Habit(dto.getName(), dto.getFrequency(), false, optionalGoal.get());
        Habit savedHabit = habitService.createHabit(habit);
        return ResponseEntity.ok(savedHabit);
    }

    @PutMapping("/{habitId}/complete")
    public ResponseEntity<Void> markHabitCompleted(@PathVariable Long habitId) {
        habitService.markHabitCompleted(habitId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/goals/{goalId}")
    public ResponseEntity<List<Habit>> getHabitsByGoalId(@PathVariable Long goalId) {
        // Get the authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userService.findByEmail(email);

        Optional<Goal> optionalGoal = goalRepository.findById(goalId);
        if (optionalGoal.isEmpty() || !optionalGoal.get().getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).build(); // Forbidden if goal doesn't belong to user
        }

        List<Habit> habits = habitService.getHabitsByGoalId(goalId);
        return ResponseEntity.ok(habits);
    }
}
