package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.model.Place;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.repositories.TourPackageRepository;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.PlaceService;

import java.util.ArrayList;
import java.util.List;

@PageTitle("Custom Trip Planner")
@Route(value = "custom-trip", layout = MainLayout.class)
public class CustomTripView extends VerticalLayout implements BeforeEnterObserver {

    private final PlaceService placeService;
    private final TourPackageRepository tourPackageRepository;
    private List<Place> lastResults = new ArrayList<>();
    private final AuthService authService;

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!authService.isUserLoggedIn()) {
            event.forwardTo("login");
        }
    }

    @Autowired
    public CustomTripView(PlaceService placeService, TourPackageRepository tourPackageRepository, AuthService authService) {
        this.placeService = placeService;
        this.tourPackageRepository = tourPackageRepository;
        this.authService = authService;

        // Set up the layout
        setSpacing(false);
        setPadding(false);
        setWidthFull();

        // Title
        H1 title = new H1("Custom Trip Planner");
        title.getStyle().set("margin-bottom", "20px");
        title.addClassName("tagline");
        add(title);

        // Interest checkboxes
        VerticalLayout checkboxLayout = new VerticalLayout();
        checkboxLayout.setSpacing(false);
        checkboxLayout.setPadding(false);

        Checkbox beachBox = new Checkbox("Beach");
        beachBox.addClassName("checkbox");

        Checkbox culturalBox = new Checkbox("Cultural / Heritage");
        culturalBox.addClassName("checkbox");

        Checkbox adventureBox = new Checkbox("Adventure");
        adventureBox.addClassName("checkbox");

        Checkbox nightlifeBox = new Checkbox("Nightlife");
        nightlifeBox.addClassName("checkbox");

        Checkbox foodBox = new Checkbox("Foodie");
        foodBox.addClassName("checkbox");

        Checkbox romanticBox = new Checkbox("Romantic");
        romanticBox.addClassName("checkbox");

        Checkbox spiritualBox = new Checkbox("Spiritual");
        spiritualBox.addClassName("checkbox");

        H3 checkboxTitle = new H3("Choose Your Interests:");
        checkboxTitle.addClassName("checkbox-title");
        checkboxLayout.add(checkboxTitle,
                new HorizontalLayout(beachBox, culturalBox, adventureBox),
                new HorizontalLayout(nightlifeBox, foodBox, romanticBox, spiritualBox));

        // Budget selection
        Paragraph budgetLabel = new Paragraph("Select Budget:");
        budgetLabel.addClassName("budget-label");
        Button lowBtn = new Button("Low");
        Button medBtn = new Button("Medium");
        Button highBtn = new Button("High");

        medBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY); // Default selection
        double[] budgetValue = {2.0};

        lowBtn.addClickListener(e -> {
            budgetValue[0] = 1.0;
            lowBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            medBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
            highBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        });

        medBtn.addClickListener(e -> {
            budgetValue[0] = 2.0;
            medBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            lowBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
            highBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        });

        highBtn.addClickListener(e -> {
            budgetValue[0] = 3.0;
            highBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            lowBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
            medBtn.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
        });

        HorizontalLayout budgetLayout = new HorizontalLayout(lowBtn, medBtn, highBtn);

        // Search button
        Button searchButton = new Button("Find Places");
        searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // Save trip button
        Button saveTripButton = new Button("Save This Trip");
        saveTripButton.setVisible(false);

        // Dialog for place details
        Dialog infoDialog = new Dialog();
        infoDialog.setWidth("400px");

        searchButton.addClickListener(e -> {
            try {
                List<Double> userVector = new ArrayList<>();
                userVector.add(beachBox.getValue() ? 1.0 : 0.0);
                userVector.add(culturalBox.getValue() ? 1.0 : 0.0);
                userVector.add(adventureBox.getValue() ? 1.0 : 0.0);
                userVector.add(nightlifeBox.getValue() ? 1.0 : 0.0);
                userVector.add(foodBox.getValue() ? 1.0 : 0.0);
                userVector.add(romanticBox.getValue() ? 1.0 : 0.0);
                userVector.add(spiritualBox.getValue() ? 1.0 : 0.0);
                userVector.add(budgetValue[0]);

                List<Place> results = placeService.getRecommendedPlaces(userVector);
                lastResults = results;
                removeAll();

                add(title, checkboxLayout, budgetLabel, budgetLayout, searchButton);

                if (results.isEmpty()) {
                    add(new Paragraph("No relevant places found for your preferences."));
                    saveTripButton.setVisible(false);
                } else {
                    add(new H3("Recommended Places:"));

                    HorizontalLayout row = new HorizontalLayout();
                    row.getStyle().set("flex-wrap", "wrap");

                    for (Place place : results) {
                        Div card = new Div();
                        card.getStyle()
                                .set("border", "1px solid #ccc")
                                .set("padding", "12px")
                                .set("margin", "8px")
                                .set("border-radius", "8px")
                                .set("width", "220px")
                                .set("box-shadow", "2px 2px 5px rgba(0,0,0,0.1)")
                                .set("background-color", "#fafafa")
                                .set("cursor", "pointer");

                        card.add(new H3(place.getName()));
                        card.add(new Paragraph("Category: " + place.getCategory()));

                        card.addClickListener(ev -> {
                            String destination = place.getName().trim();
                            TourPackage matched = tourPackageRepository
                                    .findByDestinationIgnoreCase(destination)
                                    .orElse(null);

                            infoDialog.removeAll();
                            if (matched != null) {
                                infoDialog.add(new H3(matched.getDestination()));
                                infoDialog.add(new Paragraph("Trip Type: " + String.join(", ", matched.getTags())));
                                infoDialog.add(new Paragraph("Budget: " +
                                        matched.getBudgetRange().get(0) + " - " + matched.getBudgetRange().get(1)));
                                infoDialog.add(new Paragraph("Transport: " + String.join(", ", matched.getTransport())));
                                infoDialog.add(new Paragraph("Food: " + String.join(", ", matched.getFoodPreferences())));
                                infoDialog.add(new Paragraph("Stay: " + matched.getAccommodation().getType()));
                                infoDialog.add(new Paragraph("Activities: " + String.join(", ", matched.getActivities())));
                            } else {
                                infoDialog.add(new Paragraph("No details available for this place."));
                            }
                            infoDialog.open();
                        });

                        row.add(card);
                    }

                    add(row);
                }
            } catch (Exception ex) {
                Notification.show("Invalid input! Please fill all values correctly.");
            }
        });

        add(title, checkboxLayout, budgetLabel, budgetLayout, searchButton);
    }
}
