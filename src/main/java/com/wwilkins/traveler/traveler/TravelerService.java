package com.wwilkins.traveler.traveler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwilkins.traveler.TravelerApplication;
import org.hibernate.type.UUIDBinaryType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;



@Service
public class TravelerService extends NamedParameterJdbcDaoSupport implements TravelerRepository{
    @Autowired
    DataSource dataSource;
    @Autowired
    //private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    private void initialize(){
        setDataSource(dataSource);

    }
    //private TravelerRepository travelerRepository;
    //public TravelerContoller(TravelerService travelerService) { this.travelerService = travelerService; }


    private static final Logger logger = LoggerFactory.getLogger(TravelerApplication.class);
    /*
    private Connection conn;
    private String connectionUrl = "jdbc:mysql://localhost:3306/wade?serverTimezone=UTC";

    public void TravelerService() {
        try {
                conn = DriverManager.getConnection(connectionUrl, "root", "Burrhead#21");
        }
        catch (SQLException e ) {
            e.printStackTrace();
        }

    }
     */
    public List<Traveler> getTravelers() {
        return List.of(
                new Traveler(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Wade",
                        "Clayton",
                        "Wilkins",
                         "1980-03-17"
                ),
                new Traveler(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Barb",
                        "Stamey",
                        "Wilkins",
                        "1980-07-28"
                )
        );
    }
    public List<Traveler> getTraveler( String id ) {
        return List.of(
                new Traveler(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        "Wade",
                        "Clayton",
                        "Wilkins",
                        "1980-03-17"
                )
        );
    }

    public List<String> putTraveler(String id,String body){
        return List.of("Put","Me",id,body);
    }

    public List<String> deleteTraveler( String id, String body) {
        return List.of("Delete","Me",body);
    }

    @Override
    public ResponseEntity insertTraveler(String body) {
        LocalDateTime registeredAt;
        LocalDateTime lastLogin;
        Traveler t2 = null;
        Set<ConstraintViolation<Traveler>> violations = null;
        StringBuilder allErrors = new StringBuilder();

        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t2 = objectMapper.readValue(body, Traveler.class);
            logger.error("POST start3:  " + body);
            t2.setTravelerId(UUID.randomUUID());
            logger.error("test:  " + t2.getTravelerId() );
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();
            violations = validator.validate(t2);
            int error = 1;
            allErrors.append("{");
            for (ConstraintViolation<Traveler> violation : violations) {
                logger.error(violation.getMessage());
                if (error > 1)
                    allErrors.append(", \"Error"+error+"\": \""+ violation.getMessage()+"\"");
                else
                    allErrors.append("\"Error"+error+"\": \""+ violation.getMessage()+"\"");
                error++;
            }
            allErrors.append("}");
        }
        catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (violations.isEmpty()) {
            try {
                String sql = "INSERT INTO Traveler (customer_id,traveler_id,first_name,last_name) VALUES (UUID_TO_BIN(?),UUID_TO_BIN(?), ?, ?)";
                getJdbcTemplate().update(sql, new Object[]{
                        t2.getCustomerId().toString(), t2.getTravelerId().toString(), t2.getFirstName(), t2.getLastName()
                });
                logger.info("Created traveler:  " + t2.getTravelerId() + "  " + t2.getFirstName());
                return new ResponseEntity<>(t2.toString(), HttpStatus.CREATED);
            }
            catch( Exception e){
                return new ResponseEntity<>("\"Failure\":  \"Duplicate?\"", HttpStatus.BAD_REQUEST);
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
    public List<Traveler> getAllTravelers(String cid){
        logger.info("In getAllTravelers NOW...." + cid);
        String sql = "SELECT * FROM Traveler WHERE customer_id = UUID_TO_BIN('" + cid + "')" ;
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Traveler> result = new ArrayList<Traveler>();
        for(Map<String, Object> row:rows){
            Traveler t = new Traveler();
            //t = (UUID).fromString(row.get("traveler_id"));
            //t.setTravelerId((row.get("traveler_id"));
            //t.setCustomerId((UUID)row.get("customer_id"));
            t.setFirstName((String)row.get("first_name"));
//            t.setMiddleName((String)row.get("middleName"));
            t.setLastName((String)row.get("last_name"));
//            t.setGender((String)row.get("gender"));
              t.setDob("1967-03-17");
//            t.setDob((String)row.get("dob"));
//            t.setCountryCode1((String)row.get("countryCode1"));
//            t.setPhone1((String)row.get("phone1"));
//            t.setCountryCode2((String)row.get("countryCode2"));
//            t.setPhone2((String)row.get("phone2"));
//            t.setEmergencyFirstName((String)row.get("emergencyFirstName"));
//            t.setEmergencyLastName((String)row.get("emergencyLastName"));
//            t.setEmergencyCountryCode((String)row.get("emergencyCountryCode"));
//            t.setEmergencyPhone((String)row.get("emergencyPhone"));
//            // Frequent flyer programs
//            t.setFlightPrefSeat((String)row.get("flightPrefSeat"));
//            t.setFlightPrefSpecial((String)row.get("flightPrefSpecial"));
//            t.setPassportCountryCode((String)row.get("passportCountryCode"));
//            t.setPassportNumber((String)row.get("passportNumber"));
            result.add(t);
        }
        logger.info("Returning..................");
        return result;
    }

    @Override
    public Traveler getOneTraveler(String tid) {
        String sql = "SELECT * FROM Traveler WHERE traveler_id = UUID_TO_BIN(?)";
        return (Traveler)getJdbcTemplate().queryForObject(sql, new Object[]{tid}, new RowMapper<Traveler>(){
            @Override
            public Traveler mapRow(ResultSet rs, int rwNumber) throws SQLException {
                Traveler t = new Traveler();
                t.setFirstName(rs.getString("first_name"));
                t.setLastName(rs.getString("last_name"));
                t.setDob("1967-03-17");
                return t;
            }
        });
    }

    @Override
    public ResponseEntity deleteOneTraveler(String tid, String body) {
        //private JdbcTemplate jdbcTemplateObject;
        logger.info("In Delete........................");
        String sql = "DELETE FROM Traveler WHERE traveler_id = UUID_TO_BIN(?)";
        assert getJdbcTemplate() != null;
        getJdbcTemplate().update(sql, tid);
        logger.info("DELETE!!!!!!!!!!!!!!!!!!!!!!!!!!!  " +tid);
        return new ResponseEntity<>(tid, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity updateOneTraveler(String tid, String body) {
        Traveler t2 = null;
        StringBuilder UpdateQuery = new StringBuilder();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            t2 = objectMapper.readValue(body, Traveler.class);
            Map<String, Object> params = new HashMap<String, Object>();
            int TheFirst = 0;

            UpdateQuery.append("update Traveler set ");
            if( t2.getCustomerId() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("customer_id=UUID_TO_BIN(:customer_id)"); params.put("customer_id", t2.getCustomerId().toString()); TheFirst++; }
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
            if( t2.getPassportNumber() != null) { if(TheFirst >= 1) UpdateQuery.append(", "); UpdateQuery.append("passportNumber=:ppn"); params.put("ppn", t2.getPassportNumber()); TheFirst++;}
            //private String frequentFlyerPrograms;
            UpdateQuery.append(" WHERE traveler_id = UUID_TO_BIN(:tid)");
            params.put("tid", tid);

            int status = getNamedParameterJdbcTemplate().update(UpdateQuery.toString(), params);
            if(status != 0){
                logger.info("Traveler data updated for ID " + t2.getTravelerId());
            }else{
                logger.error("No Traveler found with ID " + t2.getTravelerId());
            }
            return new ResponseEntity<>(t2.toString(), HttpStatus.CREATED);
        } catch ( Exception e ){
            logger.info("UPDATE FAIL!!!!!!!!!!!!!!!!!!!!!!!!!!!  ");
            logger.info(e.getMessage());
            return new ResponseEntity<>("\"Failure\":  \"Duplicate?\"", HttpStatus.BAD_REQUEST);
        }
    }


}
