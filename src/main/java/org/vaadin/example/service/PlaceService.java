package org.vaadin.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vaadin.example.model.Place;
import org.vaadin.example.repositories.PlaceRepository;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<Place> getAllPlaces() {
        return placeRepository.findAll();
    }

    public List<String> getAllPlaceNames() {
        return placeRepository.findAll().stream()
                .map(Place::getName)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<Place> getRecommendedPlaces(List<Double> userVector) {
        List<Place> allPlaces = getAllPlaces();

        return allPlaces.stream()
                .map(p -> {
                    double score = cosineSimilarity(userVector, p.getVector());
                    return new AbstractMap.SimpleEntry<>(p, score);
                })
                .filter(entry -> entry.getValue() > 0.75) // changed from >= 0.75 to > 0 to allow partial match
                .sorted(Map.Entry.<Place, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }

    private double cosineSimilarity(List<Double> userVector, List<Double> placeVector) {
        double dot = 0.0, normUser = 0.0, normPlace = 0.0;

        for (int i = 0; i < userVector.size(); i++) {
            if (userVector.get(i) != 0.0) {
                dot += userVector.get(i) * placeVector.get(i);
                normUser += Math.pow(userVector.get(i), 2);
                normPlace += Math.pow(placeVector.get(i), 2);
            }
        }

        return (normUser == 0 || normPlace == 0) ? 0 : dot / (Math.sqrt(normUser) * Math.sqrt(normPlace));
    }
}