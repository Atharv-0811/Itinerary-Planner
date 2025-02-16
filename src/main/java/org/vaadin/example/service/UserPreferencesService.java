package org.vaadin.example.service;

import io.netty.handler.codec.mqtt.MqttReasonCodes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.UserPreferencesRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserPreferencesService {

    private final UserPreferencesRepository preferencesRepository;

    // Constructor-based injection (Recommended) - instead of @Autowired
    public UserPreferencesService(UserPreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public ResponseEntity<UserPreferences> getPreferences(String userId) {
        Optional<UserPreferences> preferences = preferencesRepository.findByUserId(userId);

        if (preferences.isEmpty()) {
            // If no preferences exist for this user, return a default set
            UserPreferences defaultPreferences = new UserPreferences();
            defaultPreferences.setUserId(userId);
            defaultPreferences.setMinBudget(5000);
            defaultPreferences.setMaxBudget(50000);
            defaultPreferences.setStartDate(LocalDate.now());
            defaultPreferences.setDays(3);
            defaultPreferences.setAccommodationType("Hotel");

            return ResponseEntity.ok(defaultPreferences);
        }

        return ResponseEntity.ok(preferences.get());
    }


    public ResponseEntity<String> savePreferences(UserPreferences preferences) {

        String userId = AuthService.getLoggedInUser();

        if (preferences.getUserId() == null || preferences.getUserId().isEmpty()) {
            return ResponseEntity.badRequest().body("User ID is required to save preferences");
        }

        preferences.setUserId(userId);

        Optional<UserPreferences> existingPreferences = preferencesRepository.findByUserId(preferences.getUserId());

        if (existingPreferences.isPresent()) {
            UserPreferences storedPreferences = existingPreferences.get();

            // Update fields correctly
            storedPreferences.setUserId(preferences.getUserId());
            storedPreferences.setMinBudget(preferences.getMinBudget());
            storedPreferences.setMaxBudget(preferences.getMaxBudget());
            storedPreferences.setStartDate(preferences.getStartDate());
            storedPreferences.setDays(preferences.getDays());
            storedPreferences.setAccommodationType(preferences.getAccommodationType());

            preferencesRepository.save(storedPreferences);
            return ResponseEntity.ok("Preferences updated successfully");

        } else {
            // If no user in db, create new entry for that user
            preferences.setUserId(userId);
            preferencesRepository.save(preferences);
            return ResponseEntity.ok("New preferences saved");
        }
    }
}
