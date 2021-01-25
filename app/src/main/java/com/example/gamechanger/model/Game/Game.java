package com.example.gamechanger.model.Game;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.gamechanger.model.User.User;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.annotations.SerializedName;

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

    //private long lastUpdated;

    @Ignore
    String ownedBy;

    @Ignore
    FirebaseAuth mAuth=FirebaseAuth.getInstance();
    String UserId = mAuth.getCurrentUser().getUid();

    public Game(){

    }

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
        /*result.put("lastUpdated", FieldValue.serverTimestamp());*/
        result.put("OwnedBy", UserId);
        return result;
    }

    public void fromMap(Map<String, Object> map){
        id = (String) map.get("id");
        name = (String)map.get("gameName");
        price = (String)map.get("gamePrice");
        imageURL = (String)map.get("imageUrl");
        /*Timestamp ts = (Timestamp)map.get("lastUpdated");
        lastUpdated = ts.getSeconds();*/
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

    /*public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }*/

    public String getOwnedBy() {
        return ownedBy;
    }
}
