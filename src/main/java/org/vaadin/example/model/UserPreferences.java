package org.vaadin.example.model;

import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "userPreferences")
public class UserPreferences {
    @Id
    private String id;
    private String userId;
    private int minBudget = 5000;
    private int maxBudget = 50000;
    private LocalDate startDate = LocalDate.now();
    private int days = 3;
    private String accommodationType = "Hotel";

    public UserPreferences(){}

    public UserPreferences(String userId, int minBudget, int maxBudget, LocalDate startDate, int days, String accommodationType) {
        this.userId = userId;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.startDate = startDate;
        this.days = days;
        this.accommodationType = accommodationType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMinBudget() {
        return minBudget;
    }

    public void setMinBudget(int minBudget) {
        this.minBudget = minBudget;
    }

    public int getMaxBudget() {
        return maxBudget;
    }

    public void setMaxBudget(int maxBudget) {
        this.maxBudget = maxBudget;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }
}
