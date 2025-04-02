package org.vaadin.example.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.layout.MainLayout3;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.repositories.TourPackageRepository;
import org.vaadin.example.repositories.UserPreferencesRepository;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.TourPackageFilter;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "dashboard2", layout = MainLayout3.class)
public class DashboardView2 extends VerticalLayout implements BeforeEnterObserver {

    private final Div packagesContainer = new Div(); // Container for filtered tour cards
    private final ComboBox<String> tripTypeFilter = new ComboBox<>("Filter by Trip Type");
    private final TourPackageRepository tourPackageRepository;
    private final AuthService authService;
    private final UserPreferencesRepository userPreferencesRepository;

    @Autowired
    public DashboardView2(TourPackageRepository tourPackageRepository, AuthService authService, UserPreferencesRepository userPreferencesRepository) {
        this.tourPackageRepository = tourPackageRepository;
        this.authService = authService;
        this.userPreferencesRepository = userPreferencesRepository;

        setSpacing(true);
        setPadding(true);

        configureTripTypeFilter();
        H1 title = new H1("Recommendations for You");
        title.addClassName("tagline");
        title.getStyle().set("opacity", "100%");

        // Header to display title and filter options
        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        header.setAlignItems(Alignment.CENTER);
        header.getStyle().set("height", "7vh");
        header.add(title, tripTypeFilter);

        Hr divider = new Hr();
        divider.addClassName("divider");

        add(header, divider, packagesContainer);

        loadTourPackages("All"); // Initially load all packages
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!authService.isUserLoggedIn()) {
            event.forwardTo("login");
        }
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

            // Create filter object with user preferences
            TourPackageFilter filter = new TourPackageFilter(userPreferences);

            // Apply budget filter (you can add more filters here as needed)
            List<TourPackage> filteredPackagesByBudget = filter.filterByBudget(tourPackages);
            List<TourPackage> filteredPackagesByDuration = filter.filterByTripDuration(filteredPackagesByBudget);

            // Filter by trip type if selected
            if (selectedTripType != null && !selectedTripType.equals("All")) {
                filteredPackagesByDuration = filteredPackagesByDuration.stream()
                        .filter(pkg -> pkg.getTripType().equals(selectedTripType))
                        .collect(Collectors.toList());
            }

            // Debugging: Print out how many filtered packages are available
            System.out.println("Number of filtered packages: " + filteredPackagesByDuration.size() + "\n\n");

            // Ensure only valid packages are being passed to the display
            if (filteredPackagesByDuration.isEmpty()) {
                System.out.println("No valid packages found after filtering.");
            } else {
                // Now display the valid (filtered) packages
                filteredPackagesByDuration.forEach(this::addTourPackageCard);
            }

        } else {
            // Handle case where user preferences are not found
            System.out.println("User preferences not found.");
        }
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