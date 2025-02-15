package org.vaadin.example.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.UserService;
import com.vaadin.flow.component.notification.Notification;

@Route("login")
@PageTitle("Login | Itinerary Planner")
@UIScope
public class LoginView extends VerticalLayout {

    private final UserService userService;

    public LoginView(UserService userService) {
        this.userService = userService;

        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Login Page");
        title.addClassName("centered-content");

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        // Login Button Functionality
        loginButton.addClickListener(e -> {
            String username = usernameField.getValue().trim();
            String password = passwordField.getValue().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Notification.show("Username and password cannot be empty", 3000, Notification.Position.TOP_CENTER);
                return; // Stop further execution
            }

            ResponseEntity<String> response = userService.authenticateUser(username, password);
            if (response.getStatusCode().is2xxSuccessful()) {
                AuthService.login(username); // Store session
                Notification.show(response.getBody(), 3000, Notification.Position.TOP_CENTER);
                UI.getCurrent().navigate("dashboard"); // Redirects user to Dashboard
            } else {
                Notification.show(response.getBody(), 3000, Notification.Position.TOP_CENTER);
            }
        });

        // Redirect to /register
        registerButton.addClickListener( e -> {
           UI.getCurrent().navigate("register");
        });


        VerticalLayout formLayout = new VerticalLayout(usernameField, passwordField, loginButton, registerButton);
        formLayout.addClassName("login-form");

        // Center content inside the VerticalLayout
//        formLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        formLayout.setAlignItems(Alignment.CENTER); // Ensures form content is centered
        add(title, formLayout);


    }
}
