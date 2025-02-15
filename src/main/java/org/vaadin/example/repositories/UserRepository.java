package org.vaadin.example.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vaadin.example.model.User;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Optional<User> doesn't return null when user not found | Helps avoid NullPointerExceptions
    Optional<User> findByUsername(String username);  // Find user by username
}
