package com.example.habittracker.service;

import com.example.habittracker.model.Goal;

import java.util.List;

public interface GoalService {
    Goal createGoal(Goal goal);
    List<Goal> getGoalsByUserId(Long userId);
}