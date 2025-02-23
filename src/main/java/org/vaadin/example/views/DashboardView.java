package org.vaadin.example.views;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.repositories.TourPackageRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final Div packagesContainer = new Div(); // Container for filtered tour cards
    private final ComboBox<String> tripTypeFilter = new ComboBox<>("Filter by Trip Type");
    private final TourPackageRepository tourPackageRepository;

    @Autowired
    public DashboardView(TourPackageRepository tourPackageRepository) {
        this.tourPackageRepository = tourPackageRepository;

        setSpacing(true);
        setPadding(true);

        configureTripTypeFilter();
        add(tripTypeFilter, packagesContainer);

        loadTourPackages("All"); // Initially load all packages
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
        List<TourPackage> tourPackages = tourPackageRepository.findAll();

        // Filter packages if a specific trip type is selected
        if (selectedTripType != null && !selectedTripType.equals("All")) {
            tourPackages = tourPackages.stream()
                    .filter(pkg -> pkg.getTripType().equals(selectedTripType))
                    .collect(Collectors.toList());
        }

        tourPackages.forEach(this::addTourPackageCard);
    }

    private void addTourPackageCard(TourPackage tourPackage) {
        // Create a container for the package
        Div container = new Div();
        container.setWidthFull();

        // Create the heading for Package ID
        H3 packageIdHeading = new H3("Package ID: " + tourPackage.getId());
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