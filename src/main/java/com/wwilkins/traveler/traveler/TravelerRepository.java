package com.wwilkins.traveler.traveler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.util.List;

public interface TravelerRepository {
    void setDataSource(DataSource ds);

    ResponseEntity<?> insertTraveler(String body);
    public ResponseEntity<?>  getAllTravelers(String cid);
    ResponseEntity<?> getOneTraveler(String tid);
    ResponseEntity<?> deleteOneTraveler(String tid, String body);
    ResponseEntity<?> updateOneTraveler(String tid, String body) throws JsonProcessingException;

}

