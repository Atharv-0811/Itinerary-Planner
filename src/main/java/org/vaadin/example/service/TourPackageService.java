package org.vaadin.example.service;

import org.springframework.stereotype.Service;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.TourPackageRepository;

import java.util.List;

@Service
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public List<TourPackage> getRecommendedPackages(UserPreferences preferences) {
        return tourPackageRepository.findMatchingPackages(
                preferences.getMinBudget(),
                preferences.getMaxBudget(),
                preferences.getDays(),
                preferences.getAccommodationType()
        );
    }
}