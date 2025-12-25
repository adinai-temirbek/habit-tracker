package com.example.habittracker.service.impl;

import com.example.habittracker.model.Habit;
import com.example.habittracker.model.HabitFrequency;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.service.HabitService;
import com.example.habittracker.strategy.DailyHabitStrategy;
import com.example.habittracker.strategy.HabitFrequencyStrategy;
import com.example.habittracker.strategy.WeeklyHabitStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class HabitServiceImpl implements HabitService {

    private final HabitRepository habitRepository;
    private final Map<HabitFrequency, HabitFrequencyStrategy> strategies = new HashMap<>();

    @Autowired
    public HabitServiceImpl(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        strategies.put(HabitFrequency.DAILY, new DailyHabitStrategy());
        strategies.put(HabitFrequency.WEEKLY, new WeeklyHabitStrategy());
    }

    @Override
    public Habit createHabit(Habit habit) {
        return habitRepository.save(habit);
    }

    @Override
    public void markHabitCompleted(Long habitId) {
        Optional<Habit> optionalHabit = habitRepository.findById(habitId);
        optionalHabit.ifPresent(habit -> {
            habit.setCompleted(true);
            habitRepository.save(habit);
        });
    }

    @Override
    public List<Habit> getHabitsByGoalId(Long goalId) {
        return habitRepository.findByGoalId(goalId);
    }

    @Override
    public void resetHabitsIfNeeded() {
        List<Habit> habits = habitRepository.findAll();
        for (Habit habit : habits) {
            HabitFrequencyStrategy strategy = strategies.get(habit.getFrequency());
            if (strategy != null && strategy.shouldReset()) {
                habit.setCompleted(false);
                habitRepository.save(habit);
            }
        }
    }
}