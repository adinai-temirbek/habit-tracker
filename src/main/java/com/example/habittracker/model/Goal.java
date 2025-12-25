package com.example.habittracker.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goals")
public class Goal extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Forward reference – serialized in JSON, prevents recursion
    private List<Habit> habits = new ArrayList<>();

    // Default constructor
    public Goal() {}

    public Goal(String title, GoalStatus status, User user) {
        this.title = title;
        this.status = status;
        this.user = user;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }

    // Convenience methods for bidirectional relationship
    public void addHabit(Habit habit) {
        habits.add(habit);
        habit.setGoal(this);
    }

    public void removeHabit(Habit habit) {
        habits.remove(habit);
        habit.setGoal(null);
    }
}

enum GoalStatus {
    ACTIVE, COMPLETED, ABANDONED
}
