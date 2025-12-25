package com.example.habittracker.service.impl;

import com.example.habittracker.model.Goal;
import com.example.habittracker.repository.GoalRepository;
import com.example.habittracker.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @Override
    public Goal createGoal(Goal goal) {
        // The controller should set goal.setUser(user) before calling this
        return goalRepository.save(goal);
    }

    @Override
    public List<Goal> getGoalsByUserId(Long userId) {
        return goalRepository.findByUserId(userId);
    }
}
