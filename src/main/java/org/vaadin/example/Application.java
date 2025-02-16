package org.vaadin.example;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@PWA(name = "Project Base for Vaadin with Spring", shortName = "Project Base")
@Theme(value="my-theme")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

/*

TODO:  
Creative Ideas 💡 (Optional but Cool Features)
        🔥 "AI Smart Suggest" → If the user isn’t sure about their preferences, the system can suggest settings based on past trips or trending destinations.
        📍 "Map Integration" → Show a map with recommended places based on the user’s preferences.
        📅 "Trip Planner Sync" → Automatically schedule itinerary days based on preferences.
🔄 "Compare Preferences" → If traveling with friends, users can compare & merge preferences for better group trip planning.

 */