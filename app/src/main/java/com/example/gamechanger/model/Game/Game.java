package com.example.gamechanger.model.Game;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "game_table")
public class Game {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "game_id")
    @SerializedName("id")
    private int id;

    @ColumnInfo(name = "game_name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "game_price")
    @SerializedName("price")
    private String price;

    @ColumnInfo(name = "game_image")
    @SerializedName("img")
    private String imageURL;


    public Game(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
