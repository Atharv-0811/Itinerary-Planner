package org.vaadin.example.views;

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

        // Dashboard content
        H1 title = new H1("This is the Dashboard page");
        add(title);
    }
}
