package com.wwilkins.traveler.traveler;


import org.joda.time.DateTime;

import java.time.LocalDate;
import java.time.LocalDateTime;

//@DynamoDBTable(tableName = "traveler")
public class Traveler {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String userName;
    private String mobile;
    private String email;
    private String passwordHash;
    private LocalDateTime registeredAt;
    private LocalDateTime lastLogin;
    private LocalDate dob;

    public Traveler() {
    }

    public Traveler(Long id,
                    String fistName,
                    String middleName,
                    String lastName,
                    String userName,
                    String mobile,
                    String email,
                    String passwordHash,
                    LocalDateTime registeredAt,
                    LocalDateTime lastLogin,
                    LocalDate dob){
        this.id = id;
        this.firstName = fistName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.userName = userName;
        this.mobile = mobile;
        this.passwordHash = passwordHash;
        this.registeredAt = registeredAt;
        this.lastLogin = lastLogin;
        this.dob = dob;
        this.email = email;
    }

    public Traveler(String firstName, LocalDate dob, String email) {
        this.firstName = firstName;
        this.dob = dob;
        this.email = email;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }
    public void setPasswordHash(String passwordHash) {
        this.email = passwordHash;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
    }
    public void setRegisteredAt(LocalDateTime registeredAT) {
        this.registeredAt = registeredAt;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    @Override
    public String toString() {
        return "Traveler{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }

}
