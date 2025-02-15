package org.vaadin.example.layout;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;

public class MainLayout extends VerticalLayout implements RouterLayout {

    private final VerticalLayout sidebar;

    public MainLayout() {
        setSizeFull();
        addClassName("main-layout");

        // Sidebar
        sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("20vw");

        H2 logo = new H2("Itinerary Planner");
        logo.getStyle().set("margin-bottom", "20px");

        Anchor homeLink = new Anchor("dashboard", "Home");
        Anchor tripsLink = new Anchor("my-trips", "My Trips");
        Anchor preferencesLink = new Anchor("preferences", "Preferences");

        sidebar.add(logo, homeLink, tripsLink, preferencesLink);

        // Add sidebar to layout
        add(sidebar);
    }
}
