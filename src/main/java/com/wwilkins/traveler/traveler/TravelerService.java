package com.wwilkins.traveler.traveler;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class TravelerService {
    public List<Traveler> getTravelers() {
        return List.of(
                new Traveler(
                        1L,
                        "Wade C2 Wilkins",
                        LocalDate.of(2000, Month.JANUARY, 5),
                        "wadewilkins@gmail.com"
                ),
                new Traveler(
                        2L,
                        "Barb Wilkins",
                        LocalDate.of(2000, Month.JANUARY, 6),
                        "barbwilkins@gmail.com"
                )
        );
    }
    public List<Traveler> getTraveler( String id ) {
        return List.of(
                new Traveler(
                        1L,
                        "Wade C2 Wilkins",
                        LocalDate.of(2000, Month.JANUARY, 5),
                        "wadewilkins@gmail.com"
                )
        );
    }
    public List<String> putTraveler(String id,String body){
        return List.of("Put","Me",body);
    }
    public String createTraveler(String body){
        return "Create "+"Me "+body;
    }
    public List<String> deleteTraveler( String id, String body) {
        return List.of("Delete","Me",body);
    }
}
