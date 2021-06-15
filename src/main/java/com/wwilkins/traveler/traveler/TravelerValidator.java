package com.wwilkins.traveler.traveler;

import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;


import javax.validation.ConstraintViolation;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Component
public class TravelerValidator {

    public String validate(final Traveler t) {
        Set<String> violations = new HashSet<String>();
        StringBuilder allErrors = new StringBuilder();
        int error =0;

        if (StringUtils.isBlank(t.toString())) {
            violations.add("bad request");
            error++;
        }
        if (StringUtils.isBlank(t.getFirstName())) {
            violations.add("First name can not be blank");
            error++;
        }
        else
        if( t.getFirstName().length() >= 255 ) {
            violations.add("First name is maximum of 255 digits");
            error++;
        }
        if (StringUtils.isBlank(t.getLastName())) {
            violations.add("Last name can not be blank");
            error++;
        }
        else
        if( t.getLastName().length() >= 255 ) {
            violations.add("Last name is maximum of 255 digits");
            error++;
        }


        if ((t.getCustomerId() == null)) {
            violations.add("CustomerID can not be null");
            error++;
        }
        else if (StringUtils.isBlank(t.getCustomerId().toString())) {
            violations.add("CustomerID can not be blank");
            error++;
        }

        if ((t.getTravelerId() == null)) {
            violations.add("Traveler ID can not be null");
            error++;
        }
        else
        if (StringUtils.isBlank(t.getTravelerId().toString())) {
            violations.add("TravelerID can not be blank");
            error++;
        }

        if (!StringUtils.isBlank(t.getGender())) {
            if( t.getGender().length() != 1 ) {
                violations.add("Gender is always a single digit");
                error++;
            }
            if( !t.getGender().equals("M") && !t.getGender().equals("F")){
                violations.add("Gender must be M or F");
                error++;
            }
        }


        if (!StringUtils.isBlank(t.getCountryCode1())) {
            if( t.getCountryCode1().length() > 3 ) {
                violations.add("Country Code 1 is maximum of 3 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getCountryCode1());
            } catch (NumberFormatException nfe) {
                violations.add("Country code 1  must be numeric");
                error++;

            }
        }
        if (!StringUtils.isBlank(t.getPhone1())) {
            if( t.getPhone1().length() != 10 ) {
                violations.add("Phone1 number needs to be 10 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getPhone1());
            } catch (NumberFormatException nfe) {
                violations.add("Phone1 number must be numeric");
                error++;

            }
        }
        if (!StringUtils.isBlank(t.getCountryCode2())) {
            if( t.getCountryCode2().length() > 3 ) {
                violations.add("Country Code 2 is maximum of 3 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getCountryCode2());
            } catch (NumberFormatException nfe) {
                violations.add("Country code 2  must be numeric");
                error++;

            }
        }
        if (!StringUtils.isBlank(t.getPhone2())) {
            if( t.getPhone2().length() != 10 ) {
                violations.add("Phone2 number needs to be 10 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getPhone2());
            } catch (NumberFormatException nfe) {
                violations.add("Phone2 number must be numeric" );
                error++;

            }
        }
        if (!StringUtils.isBlank(t.getEmergencyCountryCode())) {
            if( t.getEmergencyCountryCode().length() > 3 ) {
                violations.add("Emergency Country Code is maximum of 3 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getEmergencyCountryCode());
            } catch (NumberFormatException nfe) {
                violations.add("Emergency country code must be numeric");
                error++;

            }
        }
        if (!StringUtils.isBlank(t.getEmergencyPhone())) {

            if( t.getEmergencyPhone().length() != 10 ) {
                violations.add("Emergency phone number needs to be 10 digits");
                error++;
            }
            try {
                double d = Double.parseDouble(t.getEmergencyPhone());

            } catch (NumberFormatException nfe) {
                violations.add("Emergency phone number must be numeric" );
                error++;
            }
        }

        if (!StringUtils.isBlank(t.getDob())) {
            if( t.getDob().toString().length() != 10 ) {
                violations.add("Birthday needs format YYYY-MM-DD");
                error++;
            }
            try {
                LocalDate d = LocalDate.parse(t.getDob());
                LocalDate today = LocalDate.now();
                if( today.isBefore(d)  ){
                    violations.add("Birthday must be in the past");
                    error++;
                }
            } catch (Exception nfe) {
                violations.add("Birthday needs format YYYY-MM-DD");
                error++;
            }
        }

        if (!StringUtils.isBlank(t.getPassportCountryCode())) {
            if( t.getPassportCountryCode().length() != 3 ) {
                violations.add("Passport Country Code is always of 3 digits");
                error++;
            }
        }

        if (!StringUtils.isBlank(t.getPassportNumber())) {
            if( t.getPassportNumber().length() >= 255 ) {
                violations.add("Passport number is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getMiddleName())) {
            if( t.getMiddleName().length() >= 255 ) {
                violations.add("Middle name is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getLastName())) {
            if( t.getLastName().length() >= 255 ) {
                violations.add("Last name is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getFirstName())) {
            if( t.getFirstName().length() >= 255 ) {
                violations.add("First name is maximum of 256 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getFlightPrefSpecial())) {
            if( t.getFlightPrefSpecial().length() >= 255 ) {
                violations.add("Flight preferences special is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getFlightPrefSeat())) {
            if( t.getFlightPrefSeat().length() >= 255 ) {
                violations.add("Flight preferences seat is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getEmergencyFirstName())) {
            if( t.getEmergencyFirstName().length() >= 255 ) {
                violations.add("Emergency first name is maximum of 255 digits");
                error++;
            }
        }
        if (!StringUtils.isBlank(t.getEmergencyLastName())) {
            if( t.getEmergencyLastName().length() >= 255 ) {
                violations.add("Emergency last name is maximum of 255 digits");
                error++;
            }
        }






        if ( error == 0 )
            allErrors.append("Good");
        else {
            int e=1;
            allErrors.append("{");
            for (String violation : violations) {
                //logger.error(violation.getMessage());
                String s;
                if (e > 1) {
                    s = ", \"Error" + e + "\": \"" + violation + "\"";
                } else {
                    s = "\"Error" + e + "\": \"" + violation + "\"";
                }
                allErrors.append(s);
                e++;
            }
            allErrors.append("}");
        }

        return allErrors.toString();
    }

}