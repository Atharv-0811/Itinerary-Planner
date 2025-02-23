package org.vaadin.example.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vaadin.example.model.TourPackage;
import java.util.List;

public interface TourPackageRepository extends MongoRepository<TourPackage, String> {
    List<TourPackage> findByDestination(String destination);
    List<TourPackage> findByPriceBetween(int minPrice, int maxPrice);
    List<TourPackage> findByAccommodationType(String accommodationType);
}
