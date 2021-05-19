package com.wwilkins.traveler.traveler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wwilkins.traveler.traveler.Traveler;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.util.List;

public interface TravelerRepository {
    public void setDataSource(DataSource ds);

    ResponseEntity insertTraveler(String body);
    List<Traveler> getAllTravelers(String cid);
    public Traveler getOneTraveler(String tid);
    public ResponseEntity deleteOneTraveler(String tid, String body);
    public ResponseEntity updateOneTraveler(String tid, String body) throws JsonProcessingException;

}

