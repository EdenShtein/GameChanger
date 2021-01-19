package com.example.gamechanger.model;

public class User {

    private int id;
    private String fName;
    private String lName;
    private String email;
    private String city;
    private String phoneNumber;

    public User(int id,String fName, String lName, String email, String city, String phoneNumber) {
        this.id=id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
