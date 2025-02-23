package org.vaadin.example.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.http.ResponseEntity;
import org.vaadin.example.service.UserService;

@Route("register")
@PageTitle("Register | Itinerary Planner")
@UIScope
public class RegisterView extends VerticalLayout {

    private final UserService userService;

    public RegisterView(UserService userService){

        this.userService = userService;
        setAlignItems(Alignment.CENTER);

        H1 title = new H1("Register Page");
        addClassName("centered-content");

        TextField usernameField = new TextField("Username");
        PasswordField passwordField = new PasswordField("Password");
        Button registerButton = new Button("Register");

        // Register Button Functionality
        registerButton.addClickListener(e -> {
            String username = usernameField.getValue().trim();
            String password = passwordField.getValue().trim();

            if(username.isEmpty() || password.isEmpty()){
                Notification.show("Username and password cannot be empty", 3000, Notification.Position.TOP_CENTER);
                return;
            }

            try{
                ResponseEntity<String> response = userService.registerUser(username, password);
                if(response.getStatusCode().is2xxSuccessful()){
                    Notification.show(response.getBody(), 3000, Notification.Position.TOP_CENTER);
                    UI.getCurrent().navigate("login");
                } else {
                    Notification.show(response.getBody(), 3000, Notification.Position.TOP_CENTER);
                }
            } catch (Exception ex){
                Notification.show("Error connecting to server", 3000, Notification.Position.TOP_CENTER);
            }
        });

        registerButton.addClickShortcut(Key.ENTER);

        VerticalLayout formLayout = new VerticalLayout(usernameField, passwordField, registerButton);
        formLayout.addClassName("register-form");
        formLayout.setAlignItems(Alignment.CENTER);

        add(title, formLayout);
    }
}
