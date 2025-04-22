package org.vaadin.example.model;

import org.springframework.data.annotation.Id; import org.springframework.data.mongodb.core.mapping.Document; import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "tourPackages") public class TourPackage {

    @Id
    private String mongoId;

    @Field("packageId")
    private String id;

    private String destination;

    @Field("budgetRange")
    private List<Integer> budgetRange;

    @Field("tripType")
    private String tripType;

    @Field("tripDuration")
    private int duration;

    @Field("accommodation")
    private Accommodation accommodation;

    private List<String> transport;
    private List<String> activities;
    private List<String> culturalInterests;
    private List<String> foodPreferences;
    private List<String> tags;

    public TourPackage() {}

    public TourPackage(String id, String destination, List<Integer> budgetRange, String tripType, int duration,
                       Accommodation accommodation, List<String> transport, List<String> activities,
                       List<String> culturalInterests, List<String> foodPreferences, List<String> tags) {
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

    // Getters & Setters
    public String getMongoId() { return mongoId; }
    public void setMongoId(String mongoId) { this.mongoId = mongoId; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public List<Integer> getBudgetRange() { return budgetRange; }
    public void setBudgetRange(List<Integer> budgetRange) { this.budgetRange = budgetRange; }

    public String getTripType() { return tripType; }
    public void setTripType(String tripType) { this.tripType = tripType; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public Accommodation getAccommodation() { return accommodation; }
    public void setAccommodation(Accommodation accommodation) { this.accommodation = accommodation; }

    public List<String> getTransport() { return transport; }
    public void setTransport(List<String> transport) { this.transport = transport; }

    public List<String> getActivities() { return activities; }
    public void setActivities(List<String> activities) { this.activities = activities; }

    public List<String> getCulturalInterests() { return culturalInterests; }
    public void setCulturalInterests(List<String> culturalInterests) { this.culturalInterests = culturalInterests; }

    public List<String> getFoodPreferences() { return foodPreferences; }
    public void setFoodPreferences(List<String> foodPreferences) { this.foodPreferences = foodPreferences; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getAccommodationType() {
        return accommodation != null ? accommodation.getType() : "Unknown";
    }

    // Nested static class
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
        public void setType(String type) { this.type = type; }

        public int getBudgetAllocation() { return budgetAllocation; }
        public void setBudgetAllocation(int budgetAllocation) { this.budgetAllocation = budgetAllocation; }

        public List<Integer> getRatings() { return ratings; }
        public void setRatings(List<Integer> ratings) { this.ratings = ratings; }

        public List<String> getAmenities() { return amenities; }
        public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    }
}