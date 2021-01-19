package com.example.gamechanger.model;

import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String fName;
    private String lName;
    private String email;
    private String city;
    private String phoneNumber;
    private Long lastUpdated;

    public User(String id,String fName, String lName, String email, String city, String phoneNumber) {
        this.id=id;
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.city = city;
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fName", fName);
        result.put("lName", lName);
        result.put("phone", phoneNumber);
        result.put("email", email);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
