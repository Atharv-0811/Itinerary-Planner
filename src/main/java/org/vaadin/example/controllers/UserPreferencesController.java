package org.vaadin.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vaadin.example.model.UserPreferences;
import org.vaadin.example.service.UserPreferencesService;

@RestController
@RequestMapping("/preferences")
public class UserPreferencesController {

    @Autowired
    private UserPreferencesService preferencesService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserPreferences> getUserPreferences(@PathVariable String userId){
        return preferencesService.getPreferences(userId);
    }

    @PostMapping
    public ResponseEntity<String> saveUserPreferences(@RequestBody UserPreferences preferences){
        return preferencesService.savePreferences(preferences);
    }
}
