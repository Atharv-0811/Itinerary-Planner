package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.example.service.AuthService;

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

        String username = AuthService.getLoggedInUser();

        H1 title = new H1("Welcome, " + (username != null ? username : "Guest") + "!");

        Button logoutButton = new Button("Logout", event -> {
            AuthService.logout();
            UI.getCurrent().navigate("login");
        });
        add(title, logoutButton);
    }
}
