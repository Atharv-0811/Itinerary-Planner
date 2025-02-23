package org.vaadin.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "tourPackages")
public class TourPackage {
    @Id
    private String id;
    private String destination;
    private int price;
    private int duration;
    private String accommodationType;
    private List<String> activities;
    private List<String> tags;

    public TourPackage() {}

    public TourPackage(String destination, int price, int duration, String accommodationType, List<String> activities, List<String> tags) {
        this.destination = destination;
        this.price = price;
        this.duration = duration;
        this.accommodationType = accommodationType;
        this.activities = activities;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public int getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public List<String> getActivities() {
        return activities;
    }

    public List<String> getTags() {
        return tags;
    }
}
