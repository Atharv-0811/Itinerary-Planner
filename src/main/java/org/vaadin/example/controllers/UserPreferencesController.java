package org.vaadin.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.UserPreferencesRepository;
import org.vaadin.example.service.AuthService;

import java.util.Optional;

@RestController
@RequestMapping("/api/preferences")
public class UserPreferencesController {

    private final UserPreferencesRepository preferencesRepository;

    // Constructor to initialize preferencesRepository
    public UserPreferencesController(UserPreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    private String getCurrentUserId() {
        String username = AuthService.getLoggedInUser();
        if (username == null) {
            throw new IllegalStateException("User is not logged in");
        }
        return username;
    }

    @PostMapping("/save")
    public ResponseEntity<?> savePreferences(@RequestBody UserPreferences preferences) {
        String userId = getCurrentUserId();
        System.out.println("Saving preferences for user: " + userId);
        System.out.println("Received Preferences: " + preferences);

        Optional<UserPreferences> existingPreferences = preferencesRepository.findByUserId(userId);

        if (existingPreferences.isPresent()) {
            UserPreferences userPrefs = existingPreferences.get();
            userPrefs.updateFrom(preferences);
            preferencesRepository.save(userPrefs);
            System.out.println("Updated existing preferences: " + userPrefs);
        } else {
            preferences.setUserId(userId);
            preferencesRepository.save(preferences);
            System.out.println("Saved new preferences: " + preferences);
        }

        return ResponseEntity.ok("Preferences saved successfully!");
    }


    @GetMapping("/get")
    public ResponseEntity<?> getUserPreferences() {
        String userId = getCurrentUserId();
        Optional<UserPreferences> preferences = preferencesRepository.findByUserId(userId);

        return preferences.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
