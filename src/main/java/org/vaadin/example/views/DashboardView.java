package org.vaadin.example.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.vaadin.example.layout.MainLayout;
import org.vaadin.example.service.AuthService;

@Route(value = "dashboard", layout = MainLayout.class)
@PageTitle("Dashboard | Itinerary Planner")
public class DashboardView extends VerticalLayout implements BeforeEnterObserver {

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!AuthService.isUserLoggedIn()) {
            event.forwardTo(LoginView.class);
        }
    }

    public DashboardView() {
        addClassName("dashboard-content");

        String userId = AuthService.getLoggedInUser();

        // Dashboard content
        H1 welcomeMsg = new H1("Welcome, " + userId);
        welcomeMsg.addClassName("welcome-msg");
        add(welcomeMsg);
    }
}
