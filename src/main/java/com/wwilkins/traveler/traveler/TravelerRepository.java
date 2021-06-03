package com.wwilkins.traveler.traveler;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import javax.sql.DataSource;
import java.util.List;

public interface TravelerRepository {
    void setDataSource(DataSource ds);

    public ResponseEntity<?> insertTraveler(String body);
    public ResponseEntity<?>  getAllTravelers(String cid);
    public ResponseEntity<?> getOneTraveler(String tid);
    public ResponseEntity<?> deleteOneTraveler(String tid, String body);
    public ResponseEntity<?> updateOneTraveler(String tid, String body) throws JsonProcessingException;

}

