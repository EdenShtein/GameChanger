package com.example.gamechanger.model.Game;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.gamechanger.model.Model;
import com.example.gamechanger.model.User.User;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Nullable;

@Entity(tableName = "game_table")
public class Game {

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "game_id")
    @SerializedName("id")
    private String id = UUID.randomUUID().toString();

    @ColumnInfo(name = "game_name")
    @SerializedName("name")
    private String name;

    @ColumnInfo(name = "game_price")
    @SerializedName("price")
    private String price;

    @ColumnInfo(name = "game_image")
    @SerializedName("img")
    private String imageURL;

    @ColumnInfo(name = "game_latitude")
    @SerializedName("latitude")
    private double latitude;

    @ColumnInfo(name = "game_longitude")
    @SerializedName("longitude")
    private double longitude;

    @Ignore
    String ownedBy;

    @Ignore
    String UserId = Model.instance.getUserId();

    @Ignore
    private long lastUpdated;

    public Game(){ }

    public Game(String name, String price,@Nullable String imageURL) {
        this.name = name;
        this.price = price;
        this.imageURL = imageURL;
    }

    public Map<String, Object> toMap() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("gameName", name);
        result.put("search",name.toLowerCase());
        result.put("gamePrice", price);
        if(price != null) {
            result.put("price",Integer.parseInt(price.replace("$","")));
        }
        result.put("imageUrl", imageURL);
        result.put("latitude", latitude);
        result.put("longitude", longitude);
        result.put("OwnedBy", UserId);
        result.put("Posted At", formatter.format(date));
        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (String) map.get("id");
        name = (String)map.get("gameName");
        price = (String)map.get("gamePrice");
        imageURL = (String)map.get("imageUrl");
        latitude = (double)map.get("latitude");
        longitude = (double)map.get("longitude");
        UserId = (String) map.get("OwnedBy");
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public void setId(String id) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getOwnedBy() {
        return ownedBy;
    }
}
