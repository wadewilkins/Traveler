package com.wwilkins.traveler.traveler;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;


@RestController
public class TravelerContoller {
    private final TravelerService travelerService;

    public TravelerContoller(TravelerService travelerService) { this.travelerService = travelerService; }
    // Get All
    @GetMapping("/api/v1/travelers/{id}")
    public ResponseEntity getAllTravelers(@PathVariable String id) { return travelerService.getAllTravelers(id); }
    // Get One
    @GetMapping("/api/v1/traveler/{id}")
    public ResponseEntity getOneTraveler(@PathVariable String id) { return travelerService.getOneTraveler(id); }
    // Create
    @PostMapping("/api/v1/traveler")
    public ResponseEntity insertTraveler(@RequestBody String body) { return travelerService.insertTraveler(body); }
    // Update
    @PutMapping("/api/v1/traveler/{id}")
    public ResponseEntity updateOneTraveler(@PathVariable String id,@RequestBody String body) { return travelerService.updateOneTraveler(id,body); }
    // Delete One
    @DeleteMapping("/api/v1/traveler/{id}")
    public ResponseEntity deleteOneTraveler(@PathVariable String id,@RequestBody String body) { return travelerService.deleteOneTraveler(id,body); }
}
