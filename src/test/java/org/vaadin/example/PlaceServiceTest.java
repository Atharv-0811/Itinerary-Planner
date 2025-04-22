package org.vaadin.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.vaadin.example.model.Place;
import org.vaadin.example.service.PlaceService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Test
    public void testGetAllPlaces() {
        List<Place> places = placeService.getAllPlaces();
        assertNotNull(places);
        System.out.println("Places: " + places);
    }
}