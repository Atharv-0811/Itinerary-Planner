package org.vaadin.example.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.example.GreetService;

import java.time.LocalDate;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */

public class TempView extends VerticalLayout {

    /**
     * Construct a new Vaadin view.
     * <p>
     * Build the initial UI state for the user accessing the application.
     *
     * @param service
     *            The message service. Automatically injected Spring managed bean.
     */
    public TempView(GreetService service) {

        // Use TextField for standard text input
        TextField textField = new TextField("Your name");
        textField.addClassName("bordered");

        // Button click listeners can be defined as lambda expressions
        Button button = new Button("Say hello", e -> {
            add(new Paragraph(service.greet(textField.getValue())));
        });

        // Theme variants give you predefined extra styles for components.
        // Example: Primary button has a more prominent look.
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        // You can specify keyboard shortcuts for buttons.
        // Example: Pressing enter in this view clicks the Button.
        button.addClickShortcut(Key.ENTER);

        // Use custom CSS classes to apply styling. This is defined in
        // styles.css.
        addClassName("centered-content");

        add(textField, button);

        DatePicker datePicker = new DatePicker("Select a date");

        ComboBox<String> comboBox = new ComboBox<>("Choose a fruit");
        comboBox.setItems("Apple", "Banana","Orange");

        Paragraph result = new Paragraph();

        Button processButton = new Button("Show Details", e -> {
            String name = textField.getValue();
            LocalDate selectedDate = datePicker.getValue();
            String selectedFruit = comboBox.getValue();

            String message = "Hello";
            if (selectedDate != null){
                message += "Date : " + selectedDate;
            }
            if (selectedFruit != null){
                message += "Selected Fruit : " + selectedFruit;
            }

            result.setText(message);
        });

        processButton.addClassName("red-button");
//        processButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(datePicker, comboBox, processButton,result);
    }
}
