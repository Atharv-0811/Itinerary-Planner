package org.vaadin.example.views;

import com.vaadin.flow.component.UI; import com.vaadin.flow.component.button.Button; import com.vaadin.flow.component.combobox.ComboBox; import com.vaadin.flow.component.dialog.Dialog; import com.vaadin.flow.component.html.Div; import com.vaadin.flow.component.html.H1; import com.vaadin.flow.component.html.H3; import com.vaadin.flow.component.html.Hr; import com.vaadin.flow.component.orderedlayout.FlexComponent; import com.vaadin.flow.component.orderedlayout.HorizontalLayout; import com.vaadin.flow.component.orderedlayout.VerticalLayout; import com.vaadin.flow.router.BeforeEnterEvent; import com.vaadin.flow.router.BeforeEnterObserver; import com.vaadin.flow.router.Route; import org.springframework.beans.factory.annotation.Autowired; import org.vaadin.example.layout.MainLayout; import org.vaadin.example.model.TourPackage; import org.vaadin.example.model.UserPreferences; import org.vaadin.example.repositories.TourPackageRepository; import org.vaadin.example.repositories.UserPreferencesRepository; import org.vaadin.example.service.AuthService; import org.vaadin.example.service.TourPackageFilter;

import java.util.List; import java.util.Optional; import java.util.Set; import java.util.stream.Collectors;

@Route(value = "dashboard", layout = MainLayout.class) public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    private final Div packagesContainer = new Div();
    private final ComboBox<String> tripTypeFilter = new ComboBox<>("Filter by Trip Type");
    private final TourPackageRepository tourPackageRepository;
    private final AuthService authService;
    private final UserPreferencesRepository userPreferencesRepository;

    private final Div packageCountLabel = new Div();

    @Autowired
    public DashboardView(TourPackageRepository tourPackageRepository,
                         AuthService authService,
                         UserPreferencesRepository userPreferencesRepository) {

        this.tourPackageRepository = tourPackageRepository;
        this.authService = authService;
        this.userPreferencesRepository = userPreferencesRepository;

        setSpacing(true);
        setPadding(true);

        H1 title = new H1("Recommended Trips for You");
        title.addClassName("tagline");

        // Configure filters
        configureTripTypeFilter();

        // Custom Trip button
        Button customTripBtn = new Button("🎒 Custom Trip", click -> UI.getCurrent().navigate("custom-trip"));
        customTripBtn.getStyle()
                .set("margin-top", "10px")
                .set("background-color", "#007bff")
                .set("color", "white")
                .set("border-radius", "6px");

        // Header layout with filter
        HorizontalLayout header = new HorizontalLayout(title, tripTypeFilter);
        header.setWidthFull();
        header.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        header.setAlignItems(FlexComponent.Alignment.CENTER);

        Hr divider = new Hr();

        packagesContainer.getStyle().set("display", "grid")
                .set("grid-template-columns", "repeat(auto-fit, minmax(300px, 1fr))")
                .set("gap", "20px");

        add(header, customTripBtn, packageCountLabel, divider, packagesContainer);

        loadTourPackages("All"); // Initially load all
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!authService.isUserLoggedIn()) {
            event.forwardTo("login");
        }
    }

    private void configureTripTypeFilter() {
        List<TourPackage> tourPackages = tourPackageRepository.findAll();
        Set<String> tripTypes = tourPackages.stream().map(TourPackage::getTripType).collect(Collectors.toSet());
        tripTypes.add("All");

        tripTypeFilter.setItems(tripTypes);
        tripTypeFilter.setValue("All");
        tripTypeFilter.setPlaceholder("Select Trip Type");
        tripTypeFilter.addValueChangeListener(event -> loadTourPackages(event.getValue()));
    }

    private void loadTourPackages(String selectedTripType) {
        packagesContainer.removeAll();
        String userId = authService.getLoggedInUser();
        Optional<UserPreferences> userPreferencesOptional = userPreferencesRepository.findByUserId(userId);

        if (userPreferencesOptional.isPresent()) {
            UserPreferences userPreferences = userPreferencesOptional.get();
            List<TourPackage> tourPackages = tourPackageRepository.findAll();
            TourPackageFilter filter = new TourPackageFilter(userPreferences);

            List<TourPackage> filteredPackages = filter.filterByTripDuration(filter.filterByBudget(tourPackages));

            if (selectedTripType != null && !selectedTripType.equals("All")) {
                filteredPackages = filteredPackages.stream()
                        .filter(pkg -> pkg.getTripType().equals(selectedTripType))
                        .collect(Collectors.toList());
            }

            packageCountLabel.setText("Showing " + filteredPackages.size() + " out of " + tourPackages.size() + " packages");
            packageCountLabel.getStyle().set("color", "#f4e285ff");

            filteredPackages.forEach(this::addTourPackageCard);

        } else {
            System.out.println("User preferences not found.");
        }
    }

    private void addTourPackageCard(TourPackage tourPackage) {
        VerticalLayout card = new VerticalLayout();
        card.addClassName("tour-card");
        card.setWidthFull();
        card.getStyle()
                .set("border", "1px solid #ddd")
                .set("border-radius", "8px")
                .set("padding", "15px")
                .set("box-shadow", "0 2px 5px rgba(0, 0, 0, 0.1)");

        H3 packageTitle = new H3("Trip: " + tourPackage.getId());
        packageTitle.getStyle().set("margin", "0");

        Div details = new Div();
        details.getElement().setProperty("innerHTML",
                "<strong>Accommodation:</strong> " + tourPackage.getAccommodation().getType() + "<br>" +
                        "<strong>Activities:</strong> " + String.join(", ", tourPackage.getActivities()) + "<br>" +
                        "<strong>Budget:</strong> ₹" + tourPackage.getBudgetRange().get(0) + " - ₹" + tourPackage.getBudgetRange().get(1) + "<br>" +
                        "<strong>Duration:</strong> " + tourPackage.getDuration() + " days");

        Button moreInfoButton = new Button("More Info", event -> showMoreDetails(tourPackage));

        card.add(packageTitle, details, moreInfoButton);
        packagesContainer.add(card);
    }

    private void showMoreDetails(TourPackage tourPackage) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Trip Details");

        VerticalLayout content = new VerticalLayout();
        content.getElement().setProperty("innerHTML",
                "<strong>Trip Type:</strong> " + tourPackage.getTripType() + "<br>" +
                        "<strong>Accommodation:</strong> " + tourPackage.getAccommodation().getType() + "<br>" +
                        "<strong>Activities:</strong> " + String.join(", ", tourPackage.getActivities()) + "<br>" +
                        "<strong>Budget:</strong> ₹" + tourPackage.getBudgetRange().get(0) + " - ₹" + tourPackage.getBudgetRange().get(1) + "<br>" +
                        "<strong>Duration:</strong> " + tourPackage.getDuration() + " days<br>" +
                        "<strong>Food Preferences:</strong> " + String.join(", ", tourPackage.getFoodPreferences()));

        Button closeButton = new Button("Close", event -> dialog.close());

        dialog.add(content, closeButton);
        dialog.open();
    }
}