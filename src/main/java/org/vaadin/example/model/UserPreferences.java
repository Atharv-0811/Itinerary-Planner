package org.vaadin.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "userPreferences")
public class UserPreferences {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private int minBudget = 5000;
    private int maxBudget = 50000;
    private LocalDate startDate = LocalDate.now();
    private int days = 3;
    private String accommodationType = "Hotel";
    private String destination; // Added destination field

    public UserPreferences() {}

    public UserPreferences(String userId, int minBudget, int maxBudget, LocalDate startDate, int days, String accommodationType, String destination) {
        this.userId = userId;
        this.minBudget = minBudget;
        this.maxBudget = maxBudget;
        this.startDate = startDate;
        this.days = days;
        this.accommodationType = accommodationType;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDestination() { // Getter for destination
        return destination;
    }

    public void setDestination(String destination) { // Setter for destination
        this.destination = destination;
    }

    // **NEW: Update method to merge existing preferences with new ones**
    public void updateFrom(UserPreferences newPreferences) {
        if (newPreferences.getMinBudget() > 0) {
            this.minBudget = newPreferences.getMinBudget();
        }
        if (newPreferences.getMaxBudget() > 0) {
            this.maxBudget = newPreferences.getMaxBudget();
        }
        if (newPreferences.getStartDate() != null) {
            this.startDate = newPreferences.getStartDate();
        }
        if (newPreferences.getDays() > 0) {
            this.days = newPreferences.getDays();
        }
        if (newPreferences.getAccommodationType() != null) {
            this.accommodationType = newPreferences.getAccommodationType();
        }
        if (newPreferences.getDestination() != null) {
            this.destination = newPreferences.getDestination();
        }
    }

}
