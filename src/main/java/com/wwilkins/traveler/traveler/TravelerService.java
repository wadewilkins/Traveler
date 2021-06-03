package com.wwilkins.traveler.traveler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwilkins.traveler.TravelerApplication;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;
import org.slf4j.Logger;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


//@Service
@Component
public class TravelerService extends NamedParameterJdbcDaoSupport implements TravelerRepository{
    @Autowired
    DataSource dataSource;
    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);
    }
    //private static final Logger logger = LoggerFactory.getLogger(TravelerApplication.class);


    @Override
    public ResponseEntity<?> insertTraveler(String body) {
        Traveler t2 = null;
        Set<ConstraintViolation<Traveler>> violations = null;
        StringBuilder allErrors = new StringBuilder();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t2 = objectMapper.readValue(body, Traveler.class);

            t2.setTravelerId(UUID.randomUUID());
            //logger.error("Creating new traveler:  " + t2.toString() );


            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            violations = validator.validate(t2);

            int error = 1;

            allErrors.append("{");
            for (ConstraintViolation<Traveler> violation : violations) {
                //logger.error(violation.getMessage());
                String t;
                if ( error > 1) {
                    t = ", \"Error" + error + "\": \"" + violation.getMessage() + "\"";
                }
                else {
                    t = "\"Error" + error + "\": \"" + violation.getMessage() + "\"";
                }
                allErrors.append(t);
                error++;
            }
            allErrors.append("}");
        }
       catch (JsonProcessingException e) {
            //logger.error("Bad JSON on insert "+ body);
            //throw e;
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
//
        if (violations.isEmpty()) {
            try {
                String sql = "INSERT INTO Traveler (customer_id,traveler_id,"+
                                                    "first_name,middleName, "+
                                                    "last_name,phone1, "+
                                                    "gender,countryCode1," +
                                                    "countryCode2,phone2, "+
                                                    "emergencyFirstName, emergencyLastName,"+
                                                    "emergencyCountryCode,emergencyPhone," +
                                                    "flightPrefSeat, flightPrefSpecial," +
                                                    "passportCountryCode,passportNumber"+
                              "  ) "+
                             " VALUES (UUID_TO_BIN(?),UUID_TO_BIN(?), "+
                             "?, ?, ?, ?,?, ?,"+
                             "?, ?,?,?,?,?,?,?,?,?"+
                             " )";
                assert getJdbcTemplate() != null;
                logger.info(t2.toString());
                getJdbcTemplate().update(sql, t2.getCustomerId().toString(), t2.getTravelerId().toString(), t2.getFirstName(),
                                              t2.getMiddleName(),t2.getLastName(),t2.getPhone1(),
                                              t2.getGender(), t2.getCountryCode1(),
                                              t2.getCountryCode2(), t2.getPhone2(),
                                              t2.getEmergencyFirstName(),t2.getEmergencyLastName(),
                                              t2.getEmergencyCountryCode(), t2.getEmergencyPhone(),
                                              t2.getFlightPrefSeat(), t2.getFlightPrefSpecial(),
                                              t2.getPassportCountryCode(), t2.getPassportNumber()
                                         );

                //logger.info("Created traveler:  " + t2);
                return new ResponseEntity<>(t2.toString(), HttpStatus.CREATED);
            }
            catch( DataAccessException sa ){
                return new ResponseEntity<>( sa.getMessage(), HttpStatus.BAD_REQUEST);
            }
            //return "Created traveler:  " + t2.toString();
        }
        else{
            return new ResponseEntity<>(allErrors, HttpStatus.BAD_REQUEST);
            //return "Error";
        }
    }
    // End insert traveler
    @Override
    public ResponseEntity<?>  getAllTravelers(String cid) {
        //logger.info("In getAllTravelers NOW...." + cid);
        List<Traveler> result = new ArrayList<>();
        try {
            String sql = "SELECT BIN_TO_UUID(customer_id) AS C,BIN_TO_UUID(traveler_id) AS T, DATE_FORMAT(dob,'%Y-%m-%d') AS D, " +
                    "first_name,middleName,last_name,gender, " +
                    "countryCode1,phone1,countryCode2,phone2,emergencyFirstName,emergencyLastName, " +
                    "emergencyCountryCode, emergencyPhone, " +
                    "flightPrefSeat,flightPrefSpecial,passportCountryCode,passportNumber " +
                    //"FROM Traveler WHERE customer_id = UUID_TO_BIN('" + cid + "')" ;
                    "FROM Traveler WHERE customer_id = UUID_TO_BIN(?)";
            assert getJdbcTemplate() != null;
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql, cid);
            for (Map<String, Object> row : rows) {
                Traveler t = new Traveler();
                String Tstring = (String) row.get("T");
                UUID tid = UUID.fromString(Tstring);
                t.setTravelerId(tid);
                UUID cust_id = UUID.fromString((String) row.get("C"));
                t.setCustomerId(cust_id);
                t.setFirstName((String) row.get("first_name"));
                t.setMiddleName((String) row.get("middleName"));
                t.setLastName((String) row.get("last_name"));
                t.setGender((String) row.get("gender"));
                t.setDob((String) row.get("D"));
                t.setCountryCode1((String) row.get("countryCode1"));
                t.setPhone1((String) row.get("phone1"));
                t.setCountryCode2((String) row.get("countryCode2"));
                t.setPhone2((String) row.get("phone2"));
                t.setEmergencyFirstName((String) row.get("emergencyFirstName"));
                t.setEmergencyLastName((String) row.get("emergencyLastName"));
                t.setEmergencyCountryCode((String) row.get("emergencyCountryCode"));
                t.setEmergencyPhone((String) row.get("emergencyPhone"));
                // Frequent flyer programs
                t.setFlightPrefSeat((String) row.get("flightPrefSeat"));
                t.setFlightPrefSpecial((String) row.get("flightPrefSpecial"));
                t.setPassportCountryCode((String) row.get("passportCountryCode"));
                t.setPassportNumber((String) row.get("passportNumber"));

                result.add(t);
            }
        }catch (DataAccessException sa){
            //logger.error("Error get all!  cid="+cid);
            //logger.error("Stack trace:  ", sa.getMessage(), sa);
            return new ResponseEntity<>( sa.getMessage(), HttpStatus.BAD_REQUEST);
           // return new ResponseEntity<>("\"Failure\":  \"Fetch all\"", HttpStatus.BAD_REQUEST);
        }
        //logger.info("Returning..................");
        //return result;
        return new ResponseEntity<>(result.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOneTraveler(String tid) {
        //logger.info("In getOneTraveler....");
        Traveler t = new Traveler();
        int count=0;
        String sql = "SELECT BIN_TO_UUID(customer_id) AS C,BIN_TO_UUID(traveler_id) AS T, DATE_FORMAT(dob,'%Y-%m-%d') AS D, " +
                        "first_name,middleName,last_name,gender, " +
                        "countryCode1,phone1,countryCode2,phone2,emergencyFirstName,emergencyLastName, " +
                        "emergencyCountryCode, emergencyPhone, " +
                        "flightPrefSeat,flightPrefSpecial,passportCountryCode,passportNumber " +
                    //"FROM Traveler WHERE traveler_id = UUID_TO_BIN('" + tid + "')" ;
                     "FROM Traveler WHERE traveler_id = UUID_TO_BIN(?)" ;
        try{
            assert getJdbcTemplate() != null;
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql,tid);
            //List<Traveler> result = new ArrayList<Traveler>();
            for(Map<String, Object> row:rows){
                    //logger.info("Getting One Traveler....");
                    count++;
                    String Tstring = (String) row.get( "T" );
                    UUID newtid = UUID.fromString(Tstring);
                    t.setTravelerId(newtid);
                    UUID cust_id = UUID.fromString((String) row.get("C"));
                    t.setCustomerId(cust_id);
                    t.setFirstName((String)row.get("first_name"));
                    t.setMiddleName((String)row.get("middleName"));
                    t.setLastName((String)row.get("last_name"));
                    t.setGender((String)row.get("gender"));
                    t.setDob((String)row.get("D"));
                    t.setCountryCode1((String)row.get("countryCode1"));
                    t.setPhone1((String)row.get("phone1"));
                    t.setCountryCode2((String)row.get("countryCode2"));
                    t.setPhone2((String)row.get("phone2"));
                    t.setEmergencyFirstName((String)row.get("emergencyFirstName"));
                    t.setEmergencyLastName((String)row.get("emergencyLastName"));
                    t.setEmergencyCountryCode((String)row.get("emergencyCountryCode"));
                    t.setEmergencyPhone((String)row.get("emergencyPhone"));
                    // Frequent flyer programs
                    t.setFlightPrefSeat((String)row.get("flightPrefSeat"));
                    t.setFlightPrefSpecial((String)row.get("flightPrefSpecial"));
                    t.setPassportCountryCode((String)row.get("passportCountryCode"));
                    t.setPassportNumber((String)row.get("passportNumber"));
                }
                if (count == 0){
                    //logger.error("Error:  GetOneTraveler, traveler not found:  "+tid);
                    return new ResponseEntity<>( "Traveler not found"+tid, HttpStatus.BAD_REQUEST);
                }
        } catch(DataAccessException sa){
            //logger.error("Error  for update one!  cid="+tid);
            //logger.error("Stack trace:  ", sa.getMessage(), sa);
            return new ResponseEntity<>( sa.getMessage(), HttpStatus.BAD_REQUEST);
            // return new ResponseEntity<>("\"Failure\":  \"Fetch all\"", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>( t.toString(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteOneTraveler(String tid, String body) {
        //private JdbcTemplate jdbcTemplateObject;
        //logger.info("In Delete........................");
        try {
            String sql = "DELETE FROM Traveler WHERE traveler_id = UUID_TO_BIN(?)";
            assert getJdbcTemplate() != null;
            int i = getJdbcTemplate().update(sql, tid);
            if ( i < 1 ) {
                //logger.info("DELETE ERROR  " + tid);
                return new ResponseEntity<>("Error:  Delete one traveler, traveler does not exists:  "+tid, HttpStatus.BAD_REQUEST);
            }
            //logger.info("DELETE!!!!!!!!!!!!!!!!!!!!!!!!!!!  " + tid);
            return new ResponseEntity<>(tid, HttpStatus.OK);
        } catch(DataAccessException sa){
            //logger.info("DELETE failed for:   " + tid);
            //logger.error("Stack trace:  ", sa.getMessage(), sa);
            return new ResponseEntity<>( sa.getMessage(), HttpStatus.BAD_REQUEST);
            // return new ResponseEntity<>("\"Failure\":  \"Fetch all\"", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> updateOneTraveler(String tid, String body) {
        Traveler t2;
        StringBuilder UpdateQuery = new StringBuilder();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t2 = objectMapper.readValue(body, Traveler.class);
            Map<String, Object> params = new HashMap<>();
            int TheFirst = 0;
            t2.setTravelerId(UUID.fromString(tid));

            UpdateQuery.append("update Traveler set ");
            if( t2.getCustomerId() != null) { UpdateQuery.append("customer_id=UUID_TO_BIN(:customer_id)"); params.put("customer_id", t2.getCustomerId().toString()); TheFirst++; }
            if( t2.getFirstName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("first_name=:first_name"); params.put("first_name", t2.getFirstName()); TheFirst++;}
            if( t2.getMiddleName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("middleName=:middle_name"); params.put("middle_name", t2.getMiddleName()); TheFirst++;}
            if( t2.getLastName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("last_name=:last_name"); params.put("last_name", t2.getLastName()); TheFirst++;}
            if( t2.getGender() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("gender=:gender"); params.put("gender", t2.getGender()); TheFirst++;}
            if( t2.getLastName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("last_name=:last_name"); params.put("last_name", t2.getLastName()); TheFirst++;}
            if( t2.getDob() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("dob=:dob"); params.put("dob", t2.getDob()); TheFirst++;}
            if( t2.getCountryCode1() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("countryCode1=:country_code1"); params.put("country_code1", t2.getCountryCode1()); TheFirst++;}
            if( t2.getPhone1() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("phone1=:phone1"); params.put("phone1", t2.getPhone1()); TheFirst++;}
            if( t2.getCountryCode2() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("countryCode2=:country_code2"); params.put("country_code2", t2.getCountryCode2()); TheFirst++;}
            if( t2.getPhone2() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("phone2=:phone2"); params.put("phone2", t2.getPhone2()); TheFirst++;}
            if( t2.getEmergencyFirstName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("emergencyFirstName=:efname"); params.put("efname", t2.getEmergencyFirstName()); TheFirst++;}
            if( t2.getEmergencyLastName() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("emergencyLastName=:elname"); params.put("elname", t2.getEmergencyLastName()); TheFirst++;}
            if( t2.getEmergencyCountryCode() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("emergencyCountryCode=:emcc"); params.put("emcc", t2.getEmergencyCountryCode()); TheFirst++;}
            if( t2.getEmergencyPhone() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("emergencyPhone=:ephone"); params.put("ephone", t2.getEmergencyPhone()); TheFirst++;}
            if( t2.getFlightPrefSeat() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("flightPrefSeat=:fps"); params.put("fps", t2.getFlightPrefSeat()); TheFirst++;}
            if( t2.getFlightPrefSpecial() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("flightPrefSpecial=:fpspecial"); params.put("fpspecial", t2.getFlightPrefSpecial()); TheFirst++;}
            if( t2.getPassportCountryCode() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("passportCountryCode=:pcc"); params.put("pcc", t2.getPassportCountryCode()); TheFirst++;}
            if( t2.getPassportNumber() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("passportNumber=:ppn"); params.put("ppn", t2.getPassportNumber()); }
            //private String frequentFlyerPrograms;
            UpdateQuery.append(" WHERE traveler_id = UUID_TO_BIN(:tid)");
            params.put("tid", tid);
            int status;
            try {
                assert ( getNamedParameterJdbcTemplate() != null);
                status = getNamedParameterJdbcTemplate().update(UpdateQuery.toString(), params);
                if ( status == 1  ) {
                    //logger.info("Traveler data updated for ID " + t2.getTravelerId());
                    return new ResponseEntity<>(t2.toString(), HttpStatus.CREATED);
                } else {
                    //logger.error("Error:  No Traveler found with ID " + t2.getTravelerId());
                    //logger.error(UpdateQuery.toString());
                    //params.entrySet().forEach(entry -> {
                    //    logger.error(entry.getKey() + " " + entry.getValue());
                    // });
                    return new ResponseEntity<>("Traveler Not Found:  " + tid, HttpStatus.BAD_REQUEST);
                }
            }catch (DataAccessException sa){
                //logger.error("Error  for update one!  cid="+tid);
                //logger.error("Stack trace:  ", sa.getMessage(), sa);
                return new ResponseEntity<>( sa.getMessage(), HttpStatus.BAD_REQUEST);
                // return new ResponseEntity<>("\"Failure\":  \"Fetch all\"", HttpStatus.BAD_REQUEST);
            }
            //catch ( NullPointerException e ){
            //    logger.info(e.getMessage());
            //    return new ResponseEntity<>(t2.toString(), HttpStatus.BAD_REQUEST);
            //}

        } catch ( Exception e ){
            //logger.error("Error  for update one!  cid="+tid);
            //logger.error("Stack trace:  ", e.getMessage(), e);
            return new ResponseEntity<>( e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
