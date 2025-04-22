package org.vaadin.example.repositories;
// ============================
// Repository: PlaceRepository.java
// ============================

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vaadin.example.model.Place;
import java.util.List;

public interface PlaceRepository extends MongoRepository<Place, String> { Place findByName(String name); }


