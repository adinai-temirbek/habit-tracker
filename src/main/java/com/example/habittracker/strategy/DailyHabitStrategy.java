package com.example.habittracker.strategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DailyHabitStrategy implements HabitFrequencyStrategy {
    private LocalDate lastResetDate = LocalDate.now();

    @Override
    public boolean shouldReset() {
        LocalDate now = LocalDate.now();
        if (ChronoUnit.DAYS.between(lastResetDate, now) >= 1) {
            lastResetDate = now;
            return true;
        }
        return false;
    }
}