package org.vaadin.example.service;

import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static void login(String username){
        VaadinSession.getCurrent().setAttribute("username", username);
    }

    public static void logout(){
        VaadinSession.getCurrent().setAttribute("username", null);
        VaadinSession.getCurrent().close();
    }

    public static boolean isUserLoggedIn(){
        return VaadinSession.getCurrent().getAttribute("username") != null;
    }

    public static String getLoggedInUser(){
        return (String) VaadinSession.getCurrent().getAttribute("username");
    }
}
