package com.wwilkins.traveler.traveler;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

//@Entity
public class Traveler {
    //@Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    //@Type(type="uuid-char")
    //@Column(name="id", columnDefinition = "VARCHAR(255)", insertable = false, updatable = false, nullable = false)
    //    @NotNull(message = "customerId cannot be null")
    //    private String customerId
    @NotNull(message = "travelerId cannot be null")
    private UUID travelerId;
    @NotNull(message = "customerId cannot be null")
    private UUID customerId;
    @NotNull(message = "firstName cannot be null")
    private String firstName;
    private String middleName;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    private String gender;
    private LocalDate dob;
    private String countryCode1;
    private String phone1;
    private String countryCode2;
    private String phone2;
    private String emergencyFirstName;
    private String emergencyLastName;
    private String emergencyCountryCode;
    private String emergencyPhone;
    // Frequent flyer programs
    //private String frequentFlyerPrograms;
    private String flightPrefSeat;
    private String flightPrefSpecial;
    private String passportCountryCode;
    private String passportNumber;
    private Long id;


//    private int age;

    public Traveler() {

    }


    public Traveler( UUID travelerId,
                    UUID customerId,
                    String fistName,
                    String middleName,
                    String lastName,
                    String dob
                    ){
        this.travelerId = travelerId;
        this.customerId = customerId;
        this.firstName = fistName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = LocalDate.parse(dob);
    }


    public Traveler(
                    String fistName,
                    String middleName,
                    String lastName,
                    String dob
                    ){
        this.travelerId = UUID.randomUUID();
        this.firstName = fistName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.dob = LocalDate.parse(dob);
    }



    public UUID getTravelerId() { return travelerId; }
    public void setTravelerId(UUID travelerId) {
        if (travelerId.equals(UUID.fromString("00000000-0000-0000-0000-000000000000") ))
            this.travelerId = UUID.randomUUID();
        else
            this.travelerId = travelerId;
    }


    public UUID getCustomerId() { return customerId; }
    public void setCustomerId(UUID customerId) {
            this.customerId = customerId;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getDob() { return dob.toString(); }
    public void setDob(String dob) {
        if(dob != null) this.dob = LocalDate.parse(dob);
    }


    public String getCountryCode1() { return countryCode1; }
    public void setCountryCode1(String countryCode1) { this.countryCode1 = countryCode1; }

    public String getPhone1() { return phone1; }
    public void setPhone1(String phone1) { this.phone1 = phone1; }

    public String getCountryCode2() { return countryCode2; }
    public void setCountryCode2(String countryCode2) { this.countryCode2 = countryCode2; }

    public String getPhone2() { return phone2; }
    public void setPhone2(String phone2) { this.phone2 = phone2; }

    public String getEmergencyFirstName() { return emergencyFirstName; }
    public void setEmergencyFirstName(String emergencyFirstName) { this.emergencyFirstName = emergencyFirstName; }

    public String getEmergencyLastName() { return emergencyLastName; }
    public void setEmergencyLastName(String emergencyLastName) { this.emergencyLastName = emergencyLastName; }

    public String getEmergencyCountryCode() { return emergencyCountryCode; }
    public void setEmergencyCountryCode(String emergencyCountryCode) { this.emergencyCountryCode = emergencyCountryCode; }

    public String getEmergencyPhone() { return emergencyPhone; }
    public void setEmergencyPhone(String emergencyPhone) { this.emergencyPhone = emergencyPhone; }

    // Frequent flyer programs
    //private String frequentFlyerPrograms;

    public String getFlightPrefSeat() { return flightPrefSeat; }
    public void setFlightPrefSeat(String flightPrefSeat) { this.flightPrefSeat = flightPrefSeat; }

    public String getFlightPrefSpecial() { return flightPrefSpecial;  }
    public void setFlightPrefSpecial(String flightPrefSpecial) { this.flightPrefSpecial = flightPrefSpecial; }

    public String getPassportCountryCode() { return passportCountryCode;  }
    public void setPassportCountryCode(String passportCountryCode) { this.passportCountryCode = passportCountryCode; }

    public String getPassportNumber() { return passportNumber;  }
    public void setPassportNumber(String passportNumber) { this.passportNumber = passportNumber; }


    @Override
    public String toString() {
        return "Traveler{"  +
                " travelerid=" + travelerId.toString() +
                ", customerId="+ customerId.toString() +
                ", firstName='" + firstName + "'" +
                ", middleName='" + middleName + "'" +
                ", lastName='" + lastName + "'" +
                ", gender='" + gender + "'" +
                ", dob='" + dob + "'" +
                ", countryCode1='" + countryCode1 + "'" +
                ", phone1='" + phone1 + "'" +
                ", countryCode2='" + countryCode2 + "'" +
                ", phone2='" + phone2 + "'" +

                ", emergencyFirstName='" + emergencyFirstName + "'" +
                ", emergencyLastName='" + emergencyLastName + "'" +
                ", emergencyCountryCode='" + emergencyCountryCode + "'" +
                ", emergencyPhone='" + emergencyPhone + "'" +
                ", flightPrefSeat='" + flightPrefSeat + "'" +
                ", flightPrefSpecial='" + flightPrefSpecial + "'" +
                ", passportCountryCode='" + passportCountryCode + "'" +
                ", passportNumber='" + passportNumber + "'" +
                "}";
    }

}
