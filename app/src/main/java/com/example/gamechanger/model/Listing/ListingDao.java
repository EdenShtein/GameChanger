package com.example.gamechanger.model.Listing;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.User.User;

import java.util.List;

@Dao
public interface ListingDao {

    @Insert
    void Insert(Listing listing);

    @Update
    void Update(Listing listing);

    @Delete
    void delete(Listing listing);

    @Query("SELECT * FROM listing_table ORDER BY id ASC")
    LiveData<List<Listing>> getAllListings();
}
