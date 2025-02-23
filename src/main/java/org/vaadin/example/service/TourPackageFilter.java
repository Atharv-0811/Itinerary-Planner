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
        double budgetTolerance = 0.10;
        double maxAllowedBudget = userPreferences.getMaxBudget() * (1 + budgetTolerance);

        System.out.println("User's Min Budget: " + userPreferences.getMinBudget());
        System.out.println("User's Max Budget (with tolerance): " + maxAllowedBudget);

        return tourPackages.stream()
                .filter(pkg -> {
                    // Get the budget range from the TourPackage model
                    List<Integer> budgetRange = pkg.getBudgetRange();
                    if (budgetRange == null || budgetRange.size() < 2) return false;

                    int packageMinBudget = budgetRange.get(0);
                    int packageMaxBudget = budgetRange.get(1);

                    // Debugging lines to check values
                    System.out.println("Package: " + pkg.getId());
                    System.out.println("Package Min Budget: " + packageMinBudget + ", Package Max Budget: " + packageMaxBudget);

                    // Check if the package's budget range falls within the user's preferred range
                    boolean isValid = packageMinBudget >= userPreferences.getMinBudget()
                            && packageMaxBudget <= maxAllowedBudget;

                    System.out.println("Package " + pkg.getId() + " is " + (isValid ? "valid" : "invalid") + " for user's budget.");
                    return isValid;
                })
                .collect(Collectors.toList());
    }

    // Check if the tour package's budget range is within the user's preferred budget range
    private boolean isBudgetInRange(TourPackage pkg) {
        List<Integer> budgetRange = pkg.getBudgetRange();
        if (budgetRange != null && budgetRange.size() == 2) {
            int packageMinBudget = budgetRange.get(0);
            int packageMaxBudget = budgetRange.get(1);

            return packageMinBudget >= userPreferences.getMinBudget()
                    && packageMaxBudget <= userPreferences.getMaxBudget();
        }
        return false; // If budgetRange is not valid, exclude the package
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
