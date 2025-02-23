package org.vaadin.example.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.vaadin.example.model.User;
import org.vaadin.example.service.AuthService;
import org.vaadin.example.service.UserService;

@Route("login")
@PageTitle("Login | Itinerary Planner")
@UIScope
public class LoginView extends VerticalLayout {

    private final UserService userService;

    public LoginView(UserService userService) {

        this.userService = userService;

        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Login Page");
        addClassName("centered-content");
        title.getStyle().set("color", "#0FBAAEff");


        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button loginButton = new Button("Login");

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

        loginButton.addClickShortcut(Key.ENTER); // Pressing Enter will trigger the button click

        Div divider = new Div();
        divider.addClassName("login-divider");

        // The "Register" word as a clickable link
        Anchor registerLink = new Anchor("register", "Register");
        registerLink.getStyle().set("color", "blue").set("text-decoration", "none");

        // Normal text before the link
        Span text = new Span(" if you don't have an account ");

        // Add everything to the divider
        divider.add(registerLink, text);

        VerticalLayout formLayout = new VerticalLayout(usernameField, passwordField, loginButton, divider);
        formLayout.addClassName("login-form");

        formLayout.setAlignItems(Alignment.CENTER);

        add(title, formLayout);
    }
}
