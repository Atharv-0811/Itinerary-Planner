package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.UserPreferencesRepository;

import java.util.Optional;

@Service
public class UserPreferencesService {

    private final UserPreferencesRepository preferencesRepository;

    @Autowired
    public UserPreferencesService(UserPreferencesRepository preferencesRepository) {
        this.preferencesRepository = preferencesRepository;
    }

    public UserPreferences savePreferences(UserPreferences preferences) {
        Optional<UserPreferences> existingPreferences = preferencesRepository.findByUserId(preferences.getUserId());

        if (existingPreferences.isPresent()) {
            // Update existing preferences instead of creating a new entry
            UserPreferences existing = existingPreferences.get();
            existing.setMinBudget(preferences.getMinBudget());
            existing.setMaxBudget(preferences.getMaxBudget());
            existing.setStartDate(preferences.getStartDate());
            existing.setDays(preferences.getDays());
            existing.setAccommodationType(preferences.getAccommodationType());
            existing.setDestination(preferences.getDestination());

            return preferencesRepository.save(existing);
        } else {
            // Save new preferences if none exist
            return preferencesRepository.save(preferences);
        }
    }

    public Optional<UserPreferences> getUserPreferences(String userId) {
        return preferencesRepository.findByUserId(userId);
    }

    public UserPreferences updateUserPreferences(String userId, UserPreferences updatedPreferences) {
        Optional<UserPreferences> existingPreferences = preferencesRepository.findByUserId(userId);

        if (existingPreferences.isPresent()) {
            UserPreferences existing = existingPreferences.get();
            existing.setMinBudget(updatedPreferences.getMinBudget());
            existing.setMaxBudget(updatedPreferences.getMaxBudget());
            existing.setStartDate(updatedPreferences.getStartDate());
            existing.setDays(updatedPreferences.getDays());
            existing.setAccommodationType(updatedPreferences.getAccommodationType());
            existing.setDestination(updatedPreferences.getDestination());

            return preferencesRepository.save(existing);
        } else {
            throw new RuntimeException("User preferences not found!");
        }
    }
}
