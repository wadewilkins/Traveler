package com.wwilkins.traveler.traveler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelerContoller {
    private final TravelerService travelerService;
    @Autowired
    public TravelerContoller(TravelerService travelerService) { this.travelerService = travelerService; }

    // Get All
    @GetMapping("/api/v1/travelers")
    public List<Traveler> getTravelers() { return travelerService.getTravelers(); }
    // Get One
    @GetMapping("/api/v1/traveler/{id}")
    public List<Traveler> getTraveler(@PathVariable String id) { return travelerService.getTraveler(id); }
    // Create
    @PostMapping("/api/v1/traveler")
    public String createTraveler(@RequestBody String body) { return travelerService.createTraveler(body); }
    // Update
    @PutMapping("/api/v1/traveler/{id}")
    public List<String> putTraveler(@PathVariable String id,@RequestBody String body) { return travelerService.putTraveler(id,body); }
    // Delete One
    @DeleteMapping("/api/v1/traveler/{id}")
    public List<String> deleteTraveler(@PathVariable String id,@RequestBody String body) { return travelerService.deleteTraveler(id,body); }
}
