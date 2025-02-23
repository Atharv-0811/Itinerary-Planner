package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.TourPackageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TourPackageService {

    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public TourPackageService(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;
    }

    public List<TourPackage> findPackagesBasedOnPreferences(UserPreferences preferences) {
        List<TourPackage> filteredPackages = tourPackageRepository.findByPriceBetween(preferences.getMinBudget(), preferences.getMaxBudget());

        // Apply additional filtering based on user preferences
        return filteredPackages.stream()
                .filter(pkg -> pkg.getDestination().equalsIgnoreCase(preferences.getDestination()))
                .filter(pkg -> pkg.getAccommodationType().equalsIgnoreCase(preferences.getAccommodationType()))
                .collect(Collectors.toList());
    }
}
