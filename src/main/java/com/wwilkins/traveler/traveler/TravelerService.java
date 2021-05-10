package com.wwilkins.traveler.traveler;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

@Service
public class TravelerService {
    public List<Traveler> getTravelers() {
        return List.of(
                new Traveler(
                        1L,
                        "Wade",
                        "Clayton",
                        "Wilkins",
                        "wadewilkins",
                        "4252238062",
                        "wadewilkins@gmail.com",
                        "hashofpassword",
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00,00 ),
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00, 00),
                        LocalDate.of(2000, Month.JANUARY, 5)
                ),
                new Traveler(
                        2L,
                        "Barb",
                        "Stamey",
                        "Wilkins",
                        "barbwilkins",
                        "360",
                        "barbiewilkins@gmail.com",
                        "hashofpassword",
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00,00 ),
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00, 00),
                        LocalDate.of(2000, Month.JANUARY, 5)
                )
        );
    }
    public List<Traveler> getTraveler( String id ) {
        return List.of(
                new Traveler(
                        1L,
                        "Wade",
                        "Clayton",
                        "Wilkins",
                        "wadewilkins",
                        "4252238062",
                        "wadewilkins@gmail.com",
                        "hashofpassword",
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00,00 ),
                        LocalDateTime.of( 2000, Month.JANUARY, 5, 00, 00),
                        LocalDate.of(2000, Month.JANUARY, 5)
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
