package com.example.gamechanger.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface GameDao {

    @Insert
    void Insert(Game game);

    @Update
    void Update(Game game);

    @Delete
    void delete(Game game);

    @Query("SELECT * FROM game_table ORDER BY id ASC")
    LiveData<List<Game>> getAllGames();

}
