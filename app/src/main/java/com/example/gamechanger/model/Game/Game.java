package com.example.gamechanger.model.Game;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

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

    private long lastUpdated;

    public Game(String name, String price,@Nullable String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("gameName", name);
        result.put("gamePrice", price);
        result.put("imgUrl", imageURL);
        result.put("lastUpdated", FieldValue.serverTimestamp());
        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (int) map.get("id");
        name = (String)map.get("gameName");
        price = (String)map.get("gamePrice");
        imageURL = (String)map.get("imageUrl");
        Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();
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

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
