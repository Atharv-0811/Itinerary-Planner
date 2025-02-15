package org.vaadin.example.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")  // Maps this class to the "users" collection in MongoDB
@CompoundIndex(def = "{'username': 1}", unique = true) // Ensures unique usernames
public class User {

    @Id
    private String id;  // Unique identifier (automatically generated)
    private String username;
    private String password;

    // Default constructor
    public User() {}

    // Constructor with parameters
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
