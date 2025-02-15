package org.vaadin.example.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.UserPreferencesRepository;

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
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(preferences.get());
    }


    public ResponseEntity<String> savePreferences(UserPreferences preferences) {
        Optional<UserPreferences> existingPreferences = preferencesRepository.findByUserId(preferences.getUserId());

        if (existingPreferences.isPresent()) {
            UserPreferences storedPreferences = existingPreferences.get();

            // Update fields correctly
            storedPreferences.setMinBudget(preferences.getMinBudget());
            storedPreferences.setMaxBudget(preferences.getMaxBudget());
            storedPreferences.setStartDate(preferences.getStartDate());
            storedPreferences.setDays(preferences.getDays());
            storedPreferences.setAccommodationType(preferences.getAccommodationType());

            preferencesRepository.save(storedPreferences);
        } else {
            preferencesRepository.save(preferences);
        }

        return ResponseEntity.ok("Preferences saved successfully");
    }


}
