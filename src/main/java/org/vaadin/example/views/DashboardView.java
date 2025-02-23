package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.TourPackageRepository;
import org.vaadin.example.repositories.UserPreferencesRepository;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.TourPackageFilter;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    private final Div packagesContainer = new Div(); // Container for filtered tour cards
    private final ComboBox<String> tripTypeFilter = new ComboBox<>("Filter by Trip Type");
    private final TourPackageRepository tourPackageRepository;
    private final AuthService authService;
    private final UserPreferencesRepository userPreferencesRepository;

    @Autowired
    public DashboardView(HttpSession httpSession, TourPackageRepository tourPackageRepository, AuthService authService, UserPreferencesRepository userPreferencesRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.authService = authService;
        this.userPreferencesRepository = userPreferencesRepository;

        setSpacing(true);
        setPadding(true);

        configureTripTypeFilter();
        add(tripTypeFilter, packagesContainer);

        loadTourPackages("All"); // Initially load all packages
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!authService.isUserLoggedIn()) {
            event.forwardTo("login");
        }
    }

    private boolean isBudgetMatch(TourPackage pkg, UserPreferences preferences) {
        // Get the min and max budget from the user preferences
        int minBudget = preferences.getMinBudget();
        int maxBudget = preferences.getMaxBudget();

        // Get the budget range of the tour package (min and max values)
        int packageMinBudget = pkg.getBudgetRange().get(0);
        int packageMaxBudget = pkg.getBudgetRange().get(1);

        // Check if the package's budget range is within the user's budget range
        return packageMinBudget >= minBudget && packageMaxBudget <= maxBudget;
    }



    private void configureTripTypeFilter() {
        // Get unique trip types from the database
        List<TourPackage> tourPackages = tourPackageRepository.findAll();
        Set<String> tripTypes = tourPackages.stream()
                .map(TourPackage::getTripType)
                .collect(Collectors.toSet());
        tripTypes.add("All");
        tripTypeFilter.setItems(tripTypes);
        tripTypeFilter.setValue("All");
        tripTypeFilter.setPlaceholder("Select Trip Type");
        tripTypeFilter.addValueChangeListener(event -> loadTourPackages(event.getValue()));
    }

    private void loadTourPackages(String selectedTripType) {
        packagesContainer.removeAll(); // Clear previous results
        String userId = authService.getLoggedInUser();

        // Fetch user preferences from the database
        Optional<UserPreferences> userPreferencesOptional = userPreferencesRepository.findByUserId(userId);

        if (userPreferencesOptional.isPresent()) {
            UserPreferences userPreferences = userPreferencesOptional.get();
            // Fetch all tour packages from the repository
            List<TourPackage> tourPackages = tourPackageRepository.findAll();

            // Create filter object
            TourPackageFilter filter = new TourPackageFilter(userPreferences);

            // Apply all filters (you can apply more filters as you add them)
            List<TourPackage> filteredPackages = filter.filterByBudget(tourPackages);
            filteredPackages = filter.filterByDestination(filteredPackages);
            filteredPackages = filter.filterByAccommodation(filteredPackages);

            // Display the filtered packages (for now, print to console)
            filteredPackages.forEach(pkg -> System.out.println("Filtered Package: " + pkg.getId()));

        } else {
            // Handle case where user preferences are not found
            System.out.println("User preferences not found.");
        }

//        List<TourPackage> tourPackages = tourPackageRepository.findAll();
//
//        // Filter packages if a specific trip type is selected
//        if (selectedTripType != null && !selectedTripType.equals("All")) {
//            tourPackages = tourPackages.stream()
//                    .filter(pkg -> pkg.getTripType().equals(selectedTripType))
//                    .collect(Collectors.toList());
//        }
//
//        tourPackages.forEach(this::addTourPackageCard);
    }

    private void addTourPackageCard(TourPackage tourPackage) {
        // Create a container for the package
        Div container = new Div();
        container.setWidthFull();

        // Create the heading for Package ID
        H3 packageIdHeading = new H3(tourPackage.getId());
        packageIdHeading.addClassName("welcome-msg");

        // Create the tour card
        Div card = new Div();
        card.addClassName("tour-card");
        card.setWidthFull();

        // Adding formatted content using HTML elements
        card.getElement().setProperty("innerHTML",
                "<strong>Accommodation Type:</strong> " + tourPackage.getAccommodation().getType() + "<br>" +
                        "<strong>Activities:</strong> " + String.join(", ", tourPackage.getActivities()) + "<br>" +
                        "<strong>Budget Range:</strong> ₹" + tourPackage.getBudgetRange().get(0) + " - ₹" + tourPackage.getBudgetRange().get(1) + "<br>" +
                        "<strong>Cultural Activities:</strong> " + String.join(", ", tourPackage.getCulturalInterests()) + "<br>" +
                        "<strong>Duration:</strong> " + tourPackage.getDuration() + " days<br>" +
                        "<strong>Food:</strong> " + String.join(", ", tourPackage.getFoodPreferences())
        );

        // Add elements to the container
        container.add(packageIdHeading, card);

        // Add the container to the UI container
        packagesContainer.add(container);
    }
}