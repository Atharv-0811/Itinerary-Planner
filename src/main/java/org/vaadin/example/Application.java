package org.vaadin.example;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@PWA(name = "Test Application", shortName = "Test", offlineResources = {})
@Theme(value="my-theme")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

// OpenTripMap API_KEY = 5ae2e3f221c38a28845f05b6e02fba63d057108c4b115526e08e0b21
/*
TODO: CUSTOM TRIP CREATION FEATURE
    EX: add a button "explore restaurants/hotels - use Google API" then let user add restaurant to their own custom tour package
 */
/*
TODO: categorise the api query parameters based on the preferences.
    so for example
    CASE 1:  if user gives budget of 10000rs and accommodation type is hostel, i will allocate 3000rs(say) as parameter for api query for accommodation.
    CASE 2 : if user gives budget of 10000rs and accommodation type is resort, i will allocate 5000rs as parameter for hotels/accommodation in api query
*/

/*
TODO:  
Creative Ideas 💡 (Optional but Cool Features)
        🔥 "AI Smart Suggest" → If the user isn’t sure about their preferences, the system can suggest settings based on past trips or trending destinations.
        📍 "Map Integration" → Show a map with recommended places based on the user’s preferences.
        📅 "Trip Planner Sync" → Automatically schedule itinerary days based on preferences.
🔄 "Compare Preferences" → If traveling with friends, users can compare & merge preferences for better group trip planning.

*/