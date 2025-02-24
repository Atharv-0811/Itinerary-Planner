package org.vaadin.example.layout;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.component.button.Button;
import org.vaadin.example.service.AuthService;
import com.vaadin.flow.component.orderedlayout.FlexComponent;

public class MainLayout3 extends VerticalLayout implements RouterLayout {

    private final VerticalLayout sidebar;

    public MainLayout3() {
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

        // Content layout to push items to top
        VerticalLayout content = new VerticalLayout(logo, homeLink, tripsLink, preferencesLink);
        content.setSpacing(true);
        content.setPadding(true);
        content.setSizeFull();
        content.setAlignItems(FlexComponent.Alignment.START);

        // Make content take full height so logout stays at the bottom
        setFlexGrow(1, content);

        // Logout Button
        Button logoutBtn = new Button("Logout");
        logoutBtn.addClassName("logout-button");
        logoutBtn.setWidthFull();

        logoutBtn.addClickListener(e ->{
            AuthService.logout();
            Notification.show("Logged out!");
            getUI().ifPresent(ui -> ui.navigate("login"));
        });

        sidebar.add(content, logoutBtn);
        add(sidebar);
    }
}
