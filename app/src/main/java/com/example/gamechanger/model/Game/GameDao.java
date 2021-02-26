package com.example.gamechanger.model.Game;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gamechanger.model.Game.Game;

import java.util.List;

@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Game game);

    @Update
    void Update(Game game);

    @Delete
    void delete(Game game);

    @Query("SELECT * FROM game_table ORDER BY game_id ASC")
    LiveData<List<Game>> getAllGames();

    @Query("SELECT game_image FROM game_table WHERE game_id=:gameId")
    public String getImageFromRoom(int gameId);

}
