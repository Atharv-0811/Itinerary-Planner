package org.vaadin.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "tourPackages")
public class TourPackage {

    @Field("packageId") // Maps to "packageId" in MongoDB
    private String id;

    private String destination;

    @Field("budgetRange")
    private List<Integer> budgetRange;  // Stores [min, max] budget

    @Field("tripType")
    private String tripType;

    @Field("tripDuration")
    private int duration;  // Maps to MongoDB's tripDuration

    @Field("accommodation")
    private Accommodation accommodation;  // Maps nested accommodation object

    private List<String> transport;
    private List<String> activities;
    private List<String> culturalInterests;
    private List<String> foodPreferences;
    private List<String> tags;

    public TourPackage() {}

    public TourPackage(String id, String destination, List<Integer> budgetRange, String tripType, int duration, Accommodation accommodation,
                       List<String> transport, List<String> activities, List<String> culturalInterests, List<String> foodPreferences, List<String> tags) {
        this.id = id;
        this.destination = destination;
        this.budgetRange = budgetRange;
        this.tripType = tripType;
        this.duration = duration;
        this.accommodation = accommodation;
        this.transport = transport;
        this.activities = activities;
        this.culturalInterests = culturalInterests;
        this.foodPreferences = foodPreferences;
        this.tags = tags;
    }

    // Getters
    public String getId() { return id; }
    public String getDestination() { return destination; }
    public List<Integer> getBudgetRange() { return budgetRange; }
    public String getTripType() { return tripType; }
    public int getDuration() { return duration; }
    public Accommodation getAccommodation() { return accommodation; }
    public String getAccommodationType() { return accommodation != null ? accommodation.getType() : "Unknown"; }
    public List<String> getTransport() { return transport; }
    public List<String> getActivities() { return activities; }
    public List<String> getCulturalInterests() { return culturalInterests; }
    public List<String> getFoodPreferences() { return foodPreferences; }
    public List<String> getTags() { return tags; }

    // Nested class for Accommodation
    public static class Accommodation {
        private String type;
        private int budgetAllocation;
        private List<Integer> ratings;
        private List<String> amenities;

        public Accommodation() {}

        public Accommodation(String type, int budgetAllocation, List<Integer> ratings, List<String> amenities) {
            this.type = type;
            this.budgetAllocation = budgetAllocation;
            this.ratings = ratings;
            this.amenities = amenities;
        }

        public String getType() { return type; }
        public int getBudgetAllocation() { return budgetAllocation; }
        public List<Integer> getRatings() { return ratings; }
        public List<String> getAmenities() { return amenities; }
    }
}
