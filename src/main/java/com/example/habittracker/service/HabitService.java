package com.example.habittracker.service;

import com.example.habittracker.model.Habit;

import java.util.List;

public interface HabitService {
    Habit createHabit(Habit habit);
    void markHabitCompleted(Long habitId);
    List<Habit> getHabitsByGoalId(Long goalId);
    void resetHabitsIfNeeded();
}