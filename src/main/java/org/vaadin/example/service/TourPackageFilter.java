package org.vaadin.example.service;

import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;

import java.util.List;
import java.util.stream.Collectors;

public class TourPackageFilter {

    private UserPreferences userPreferences;

    public TourPackageFilter(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    // Function to filter packages based on the user's budget
    public List<TourPackage> filterByBudget(List<TourPackage> tourPackages) {
        double budgetTolerance = 0.10;  // Allow 10% above max budget
        double minAllowedBudget = userPreferences.getMinBudget() * (1 - budgetTolerance);
        double maxAllowedBudget = userPreferences.getMaxBudget() * (1 + budgetTolerance);

        System.out.println("User's Min Budget (with tolerance): " + minAllowedBudget);
        System.out.println("User's Max Budget (with tolerance): " + maxAllowedBudget);

        // Filtering valid tour packages based on the budget
        List<TourPackage> validPackages = tourPackages.stream()
                .filter(pkg -> {
                    boolean isValid = pkg.getBudgetRange().get(0) >= minAllowedBudget
                            && pkg.getBudgetRange().get(1) <= maxAllowedBudget;
                    return isValid;
                })
                .collect(Collectors.toList());

        // Print the entire list of valid packages
        System.out.println("Filtered Valid Packages: ");
        validPackages.forEach(pkg -> System.out.println(pkg.getId() + ": " + pkg.getDestination()));

        return validPackages;

    }

    // Filter by Trip Duration
    public List<TourPackage> filterByTripDuration(List<TourPackage> tourPackages) {
        Integer days =  userPreferences.getDays();
        return tourPackages.stream()
                .filter(pkg -> pkg.getDuration() <= userPreferences.getDays())
                .collect(Collectors.toList());
    }

    // Add other filters (e.g., destination, accommodation) as needed
    public List<TourPackage> filterByDestination(List<TourPackage> tourPackages) {
        return tourPackages.stream()
                .filter(pkg -> pkg.getDestination().equals(userPreferences.getDestination()))
                .collect(Collectors.toList());
    }

    public List<TourPackage> filterByAccommodation(List<TourPackage> tourPackages) {
        return tourPackages.stream()
                .filter(pkg -> pkg.getAccommodationType().equals(userPreferences.getAccommodationType()))
                .collect(Collectors.toList());
    }
}
