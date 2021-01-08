package com.example.gamechanger.model;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private GameRepository repository;
    private LiveData<List<Game>> gamesList;

    public GameViewModel(@NonNull Application application) {
        super(application);

        repository = new GameRepository(application);
        gamesList = repository.getAllGames();
    }

    public void insert (Game game){
        repository.insert(game);
    }

    public void update(Game game){
        repository.update(game);
    }

    public void delete(Game game){
        repository.delete(game);
    }

    public LiveData<List<Game>> getAllGames()
    {
        return gamesList;
    }
}
