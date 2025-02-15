package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.vaadin.example.service.AuthService;

import javax.swing.*;
import java.util.Dictionary;

@Route("dashboard")
@PageTitle("Dashboard | Itinerary Planner")
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!AuthService.isUserLoggedIn()) {
//            UI.getCurrent().getPage().setLocation("login"); // setLocation changes the URL in browser to the correct endpoint
            event.forwardTo(LoginView.class);
        }
    }

    public DashboardView(){

        setSizeFull();
        addClassName("dashboard-layout");

        // Sidebar
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("250px");

        H2 logo = new H2("Itinerary Planner");
        logo.getStyle().set("margin-bottom", "20px");

        Anchor homeLink = new Anchor("dashboard", "Home");
        Anchor tripsLink = new Anchor("my-trips", "My Trips");
        Anchor preferencesLink = new Anchor("preferences", "Preferences");

        sidebar.add(logo, homeLink, tripsLink, preferencesLink);

        // Content area
        Div content = new Div();
        content.addClassName("content");

        add(sidebar, content);

//        String username = AuthService.getLoggedInUser();
//
//        H1 title = new H1("Welcome, " + (username != null ? username : "Guest") + "!");
//
//        Button logoutButton = new Button("Logout", event -> {
//            AuthService.logout();
//            UI.getCurrent().navigate("login");
//        });
//        add(title, logoutButton);
    }
}
