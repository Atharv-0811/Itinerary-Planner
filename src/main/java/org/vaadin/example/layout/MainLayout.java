package org.vaadin.example.layout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import org.vaadin.example.service.AuthService;

public class MainLayout extends VerticalLayout implements RouterLayout {

    public MainLayout() {
        setSizeFull();
        addClassName("main-layout");

        // Sidebar
        VerticalLayout sidebar = new VerticalLayout();
        sidebar.addClassName("sidebar");
        sidebar.setWidth("20vw");

        H2 logo = new H2("Itinerary Planner");
        logo.getStyle().set("margin-bottom", "20px");

        Anchor homeLink = new Anchor("dashboard", "Home");
        Anchor tripsLink = new Anchor("my-trips", "My Trips");
        Anchor customTripLink = new Anchor("custom-trip", "Custom Trip");
        Anchor preferencesLink = new Anchor("preferences", "Preferences");

        // Content layout
        VerticalLayout content = new VerticalLayout(logo, homeLink, tripsLink, customTripLink, preferencesLink);
        content.setSpacing(true);
        content.setPadding(true);
        content.setSizeFull();
        content.setAlignItems(FlexComponent.Alignment.START);

        // Make content take full height so user menu stays at the bottom
        setFlexGrow(1, content);

        // --- User Profile Layout ---
        HorizontalLayout userLayout = new HorizontalLayout();
        userLayout.addClassName("user-profile");
        userLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        userLayout.setSpacing(true);
        userLayout.setPadding(true);
        userLayout.setWidthFull();

        // Username
        String userId = AuthService.getLoggedInUser();
        Span username;
        if (userId == null) {
            username = new Span("Sign In");
            username.addClassName("username");

            // Dropdown Arrow Icon
            Icon dropdownIcon = new Icon(VaadinIcon.CHEVRON_DOWN);
            dropdownIcon.addClassName("dropdown-icon");

            // Context Menu for Logout
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setTarget(userLayout);
            contextMenu.setOpenOnClick(true); // 🔥 Fix: Now clicking the username layout opens the menu
            contextMenu.addItem("Sign In", e -> {
                AuthService.logout();
                UI.getCurrent().navigate("login");
            });

            userLayout.add(username, dropdownIcon);
        }
        else{
            username = new Span(userId);
            username.addClassName("username");

            // Dropdown Arrow Icon
            Icon dropdownIcon = new Icon(VaadinIcon.CHEVRON_DOWN);
            dropdownIcon.addClassName("dropdown-icon");

            // Context Menu for Logout
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.setTarget(userLayout);
            contextMenu.setOpenOnClick(true); // 🔥 Fix: Now clicking the username layout opens the menu
            contextMenu.addItem("Sign out", e -> {
                AuthService.logout();
                Notification.show("Logged out!");
                UI.getCurrent().navigate("login");
            });

            userLayout.add(username, dropdownIcon);
        }

        // Add elements to sidebar
        sidebar.add(content, userLayout);
        add(sidebar);
    }
}
