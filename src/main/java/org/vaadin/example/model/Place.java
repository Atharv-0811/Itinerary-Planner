package org.vaadin.example.model;import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("places")
public class Place {
    @Id
    private String id;
    private String name;
    private String category;
    private List<Double> vector;

    // 👉 Default constructor (required by Spring & MongoDB)
    public Place() {
    }

    // 👉 Custom constructor for convenience
    public Place(String name, String category, List<Double> vector) {
        this.name = name;
        this.category = category;
        this.vector = vector;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Double> getVector() {
        return vector;
    }

    public void setVector(List<Double> vector) {
        this.vector = vector;
    }
}
