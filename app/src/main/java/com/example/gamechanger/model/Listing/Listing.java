package com.example.gamechanger.model.Listing;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Listing_table")
public class Listing {

    @PrimaryKey(autoGenerate = true)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }






}
