package org.vaadin.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.vaadin.example.model.Place;
import org.vaadin.example.repositories.PlaceRepository;

import java.util.Arrays; import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private final PlaceRepository repository;

    public DataInitializer(PlaceRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            repository.saveAll(List.of(
                    new Place("Goa", "Beach + Nightlife", Arrays.asList(1.0, 0.0, 1.0, 1.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Manali", "Mountain + Adventure", Arrays.asList(0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Jaipur", "Cultural + Heritage", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Leh-Ladakh", "Adventure + Nature", Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0)),
                    new Place("Rishikesh", "Spiritual + Adventure", Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0)),
                    new Place("Agra", "Historical", Arrays.asList(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Udaipur", "Romantic + Cultural", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Darjeeling", "Mountain + Scenic", Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Kolkata", "Cultural + Foodie", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Sundarbans", "Wildlife", Arrays.asList(1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 3.0)),
                    new Place("Jaisalmer", "Desert + Historical", Arrays.asList(0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 3.0)),
                    new Place("Mysore", "Heritage + Cultural", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Pondicherry", "Beach + French", Arrays.asList(1.0, 1.0, 0.0, 1.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Hampi", "Ancient + Heritage", Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Ooty", "Hill Station", Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Coorg", "Nature + Coffee", Arrays.asList(0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Gokarna", "Beach + Calm", Arrays.asList(1.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Varanasi", "Spiritual", Arrays.asList(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0)),
                    new Place("Mount Abu", "Hill Station", Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Chennai", "City + Food", Arrays.asList(1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Madurai", "Temple Town", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0)),
                    new Place("Auli", "Skiing + Adventure", Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Lonavala", "Weekend + Nature", Arrays.asList(0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 1.0)),
                    new Place("Khajuraho", "Temples + Heritage", Arrays.asList(0.0, 1.0, 0.0, 0.0, 0.0, 1.0, 0.0, 2.0)),
                    new Place("Srinagar", "Valley + Romantic", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Mahabaleshwar", "Hill Station + Nature", Arrays.asList(0.0, 0.0, 1.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Nainital", "Lake + Nature", Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Ranthambore", "Wildlife + Jungle", Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Bodh Gaya", "Spiritual + Historical", Arrays.asList(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0)),
                    new Place("Puri", "Beach + Spiritual", Arrays.asList(1.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0)),
                    new Place("Amritsar", "Cultural + Religious", Arrays.asList(0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 1.0, 1.0)),
                    new Place("Shillong", "Hill Station + Music", Arrays.asList(0.0, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Kaziranga", "Wildlife + Nature", Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Tawang", "Scenic + Spiritual", Arrays.asList(0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 1.0, 2.0)),
                    new Place("Cherrapunji", "Rain + Nature", Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Diu", "Beach + History", Arrays.asList(1.0, 1.0, 0.0, 1.0, 1.0, 0.0, 0.0, 1.0)),
                    new Place("Kanha", "Forest + Wildlife", Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0)),
                    new Place("Ziro Valley", "Nature + Culture", Arrays.asList(0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Rameswaram", "Spiritual + Coastal", Arrays.asList(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0)),
                    new Place("Velankanni", "Pilgrimage", Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0)),
                    new Place("Lakshadweep", "Island + Beach", Arrays.asList(1.0, 0.0, 1.0, 0.0, 0.0, 1.0, 0.0, 2.0)),
                    new Place("Bikaner", "Desert + Food", Arrays.asList(0.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 2.0)),
                    new Place("Mussoorie", "Hill Station", Arrays.asList(0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 2.0)),
                    new Place("Gir", "Lion Safari", Arrays.asList(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 2.0))
            ));
        }
    }
}
