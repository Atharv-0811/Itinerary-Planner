package org.vaadin.example.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vaadin.example.model.UserPreferences;
import java.util.Optional;

public interface UserPreferencesRepository extends MongoRepository<UserPreferences, String> {
    Optional<UserPreferences> findByUserId(String userId);
}
