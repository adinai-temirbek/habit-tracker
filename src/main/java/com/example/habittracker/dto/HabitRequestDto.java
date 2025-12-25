package com.example.habittracker.dto;
import com.example.habittracker.model.HabitFrequency;
public class HabitRequestDto {
    private String name;
    private HabitFrequency frequency;
    private Long goalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HabitFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(HabitFrequency frequency) {
        this.frequency = frequency;
    }

    public Long getGoalId() {
        return goalId;
    }

    public void setGoalId(Long goalId) {
        this.goalId = goalId;
    }
}