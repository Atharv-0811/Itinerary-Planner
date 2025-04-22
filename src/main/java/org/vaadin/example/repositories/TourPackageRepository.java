package org.vaadin.example.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.vaadin.example.model.TourPackage;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

@Repository
public interface TourPackageRepository extends MongoRepository<TourPackage, String> {
    @Query("{ 'budgetRange': { $gte: ?0, $lte: ?1 }, 'tripDuration': ?2, 'accommodation.type': ?3 }")
    List<TourPackage> findMatchingPackages(int minBudget, int maxBudget, int duration, String accommodationType);
    Optional<TourPackage> findByDestinationIgnoreCase(String destination);
}
