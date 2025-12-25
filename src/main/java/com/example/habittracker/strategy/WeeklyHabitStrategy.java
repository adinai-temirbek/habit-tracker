package com.example.habittracker.strategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class WeeklyHabitStrategy implements HabitFrequencyStrategy {
    private LocalDate lastResetDate = LocalDate.now();

    @Override
    public boolean shouldReset() {
        LocalDate now = LocalDate.now();
        if (ChronoUnit.WEEKS.between(lastResetDate, now) >= 1) {
            lastResetDate = now;
            return true;
        }
        return false;
    }
}