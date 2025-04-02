package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.UserPreferencesService;
import java.util.Optional;

@PermitAll
@Route(value = "preferences", layout = MainLayout.class)
@PageTitle("User Preferences")
public class PreferencesView extends VerticalLayout {

    private final UserPreferencesService preferencesService;

    private NumberField minBudgetField = new NumberField("Min Budget");
    private NumberField maxBudgetField = new NumberField("Max Budget");
    private DatePicker startDatePicker = new DatePicker("Start Date");
    private NumberField daysField = new NumberField("Number of Days");
    private TextField accommodationField = new TextField("Accommodation Type");
    private TextField destinationField = new TextField("Destination");

    private Button saveButton = new Button("Save Preferences");

    public PreferencesView(UserPreferencesService preferencesService) {
        this.preferencesService = preferencesService;

        // Load user preferences when the view is opened
        loadUserPreferences();

        saveButton.addClickListener(e -> savePreferences());

        addClassName("centered-content");

        add(minBudgetField, maxBudgetField, startDatePicker, daysField, accommodationField, destinationField, saveButton);
    }

    private void loadUserPreferences() {
        String userId = AuthService.getLoggedInUser();
        if (userId == null) {
            Notification.show("User is not logged in");
            return;
        }

        Optional<UserPreferences> preferences = preferencesService.getUserPreferences(userId);

        if (preferences.isPresent()) {
            UserPreferences prefs = preferences.get();
            minBudgetField.setValue((double) ((double) prefs.getMinBudget() !=0 ? prefs.getMinBudget() : 5000));
            maxBudgetField.setValue((double) ((double) prefs.getMaxBudget() !=0 ? prefs.getMaxBudget() : 20000));
            startDatePicker.setValue(prefs.getStartDate());
            daysField.setValue((double) ((double) prefs.getDays() !=0 ? prefs.getDays() : 5));
            accommodationField.setValue(prefs.getAccommodationType() != null ? prefs.getAccommodationType() : "");
            destinationField.setValue(prefs.getDestination() != null ? prefs.getDestination() : "");
        } else {
            Notification.show("No preferences found. Using defaults.");
        }
    }

    private void savePreferences() {
        String userId = AuthService.getLoggedInUser();
        if (userId == null) {
            Notification.show("User is not logged in");
            return;
        }

        UserPreferences updatedPreferences = new UserPreferences(
                userId,
                minBudgetField.getValue() != null ? minBudgetField.getValue().intValue() : 0,
                maxBudgetField.getValue() != null ? maxBudgetField.getValue().intValue() : 0,
                startDatePicker.getValue(),
                daysField.getValue() != null ? daysField.getValue().intValue() : 0,
                accommodationField.getValue(),
                destinationField.getValue()
        );

        preferencesService.savePreferences(updatedPreferences);
        Notification.show("Preferences saved successfully!");
    }
}