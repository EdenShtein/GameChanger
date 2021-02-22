package com.example.gamechanger.model.User;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private long lastUpdated;

    public User(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("fName", firstName);
        result.put("lName", lastName);
        result.put("email", email);
        result.put("phone", phoneNumber);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map) {
        id = (String)map.get("id");
        firstName = (String)map.get("fName");
        lastName = (String)map.get("lName");
        email = (String)map.get("email");
        phoneNumber = (String)map.get("phone");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}