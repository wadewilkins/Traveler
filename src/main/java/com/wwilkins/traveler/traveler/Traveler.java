package com.wwilkins.traveler.traveler;


import java.time.LocalDate;

//@DynamoDBTable(tableName = "traveler")
public class Traveler {
    private Long guid;
    private String name;
    private LocalDate dob;
    private String email;

    public Traveler() {
    }

    public Traveler(Long guid, String name, LocalDate dob, String email) {
        this.guid = guid;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Traveler(String name, LocalDate dob, String email) {
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Long getGuid() {
        return guid;
    }

    public void setGuid(Long guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Traveler{" +
                "guid=" + guid +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }

}
