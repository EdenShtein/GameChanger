package com.example.gamechanger.model.Game;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.gamechanger.model.AppRepository;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<Game>> gamesList;

    public GameViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
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
