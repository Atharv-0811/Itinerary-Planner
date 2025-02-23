package org.vaadin.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.vaadin.example.model.TourPackage;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.service.TourPackageService;

import java.util.List;

@RestController
@RequestMapping("/api/tour-packages")
public class TourPackageController {

    private final TourPackageService tourPackageService;

    @Autowired
    public TourPackageController(TourPackageService tourPackageService) {
        this.tourPackageService = tourPackageService;
    }

    @PostMapping("/recommend")
    public List<TourPackage> getRecommendations(@RequestBody UserPreferences preferences) {
        return tourPackageService.getRecommendedPackages(preferences);
    }
}
