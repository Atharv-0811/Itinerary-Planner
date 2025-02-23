package org.vaadin.example.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
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

@Route("")
public class MainView extends VerticalLayout {
    public MainView() {
//        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
//        setWidthFull();
//        setSpacing(false);
//        setPadding(false);

        // Center content both vertically and horizontally
        setSizeFull();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);
        addClassName("landing-page");

        H1 title = new H1("TravelGenie...");
        title.addClassName("title");

        Div titleWrapper = new Div(title);
        titleWrapper.addClassName("title-wrapper");

        H2 tagline = new H2("Plan you next trip with TravelGenie");
        tagline.addClassName("tagline");

        // Subtitle
        Paragraph subtitle = new Paragraph(
                "Build, personalize, and optimize your itineraries. " +
                        "Designed for vacations, workations, and everyday adventures.");
        subtitle.addClassName("paragraph");

        // Create a trip button
        Button createTripBtn = new Button("Create a new trip", VaadinIcon.CLIPBOARD.create());
        createTripBtn.addClassName("create-trip-button");

        createTripBtn.addClickListener( e-> {
            UI.getCurrent().navigate("dashboard");
        });

        // Section with images/icons
        HorizontalLayout iconSection = new HorizontalLayout();
        iconSection.setSpacing(true);
        iconSection.getStyle().set("margin-top", "2rem");

//        Paragraph tripPlannerText = new Paragraph("Trip Planner AI is now part of ");
//        tripPlannerText.getStyle().set("font-weight", "bold").set("font-size", "1.5rem");

//        H2 laylaText = new H2("Layla ↗");
//        laylaText.getStyle().set("color", "#38b6ff")
//                .set("display", "inline")
//                .set("font-size", "1.5rem");

//        VerticalLayout tripPlannerSection = new VerticalLayout(tripPlannerText,laylaText);
//        tripPlannerSection.setAlignItems(Alignment.CENTER);
//        tripPlannerSection.setSpacing(false);
//        tripPlannerSection.setPadding(false);

        // Adding everything to the main layout
        add(titleWrapper,tagline, subtitle, createTripBtn);
    }

}
