package org.vaadin.example.views;//package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.model.User;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.UserPreferencesService;

import java.awt.*;
import java.time.LocalDate;

@Route(value = "preferences", layout = MainLayout.class)
@PageTitle("Preferences")
public class PreferencesView extends VerticalLayout {

    private final UserPreferencesService preferencesService;
    private final String userId;

    public PreferencesView(UserPreferencesService preferencesService) {
        this.preferencesService = preferencesService;
        this.userId = AuthService.getLoggedInUser();

        System.out.println("Logged-in User ID: " + userId);  // Debugging output

        if (userId == null || userId.isEmpty()) {
            Notification.show("Error: User ID is missing!");
            return;
        }

        setSpacing(true);
        setPadding(true);

        // UI Fields
        NumberField minBudgetField = new NumberField("Min Budget");
        NumberField maxBudgetField = new NumberField("Max Budget");
        DatePicker startDateField = new DatePicker("Start Date");
        NumberField daysField = new NumberField("Number of Days");
        ComboBox<String> accommodationTypeField = new ComboBox<>("Accommodation Type");

        // Default Values
        minBudgetField.setValue(5000.0);
        maxBudgetField.setValue(50000.0);
        startDateField.setValue(LocalDate.now());
        daysField.setValue(3.0);

        accommodationTypeField.setItems("Hotel", "Hostel", "Resort", "Apartment");
        accommodationTypeField.setValue("Hotel");

        // Fetch and display preferences
        UserPreferences existingPreferences = preferencesService.getPreferences(userId).getBody();

        if (existingPreferences != null) {
            minBudgetField.setValue((double) existingPreferences.getMinBudget());
            maxBudgetField.setValue((double) existingPreferences.getMaxBudget());
            startDateField.setValue(existingPreferences.getStartDate());
            daysField.setValue((double) existingPreferences.getDays());
            accommodationTypeField.setValue(existingPreferences.getAccommodationType());
        }

        // Save Preferences
        Button saveButton = new Button("Save Preferences", e -> {
           UserPreferences preferences = new UserPreferences();
           preferences.setUserId(userId);
            preferences.setMinBudget(minBudgetField.getValue().intValue());
            preferences.setMaxBudget(maxBudgetField.getValue().intValue());
            preferences.setStartDate(startDateField.getValue());
            preferences.setDays(daysField.getValue().intValue());
            preferences.setAccommodationType(accommodationTypeField.getValue());

            preferencesService.savePreferences(preferences);
            Notification.show("Preferences saved successfully!");
        });

        add(minBudgetField, maxBudgetField, startDateField, daysField, accommodationTypeField, saveButton);
        addClassName("centered-content");

    }

}