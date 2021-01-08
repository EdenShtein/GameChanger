package com.example.gamechanger.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import java.util.List;

public class GameRepository {

    private GameDao gameDao;
    private LiveData<List<Game>> games;

    public GameRepository(Application application)
    {
        GameDatabase database = GameDatabase.getInstance(application);
        gameDao = database.gameDao();
        games = gameDao.getAllGames();
    }

    public void insert(Game game)
    {
        new InsertGameAsyncTask(gameDao).execute(game);
    }

    public void update(Game game)
    {
        new UpdateGameAsyncTask(gameDao).execute(game);
    }

    public void delete(Game game)
    {
        new DeleteGameAsyncTask(gameDao).execute(game);
    }

    public LiveData<List<Game>> getAllGames()
    {
        return games;
    }

    private static class InsertGameAsyncTask extends AsyncTask<Game, Void, Void>
    {
        private GameDao gameDao;

        private InsertGameAsyncTask(GameDao gameDao)
        {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.Insert(games[0]);
            return null;
        }
    }

    private static class UpdateGameAsyncTask extends AsyncTask<Game, Void, Void>
    {
        private GameDao gameDao;

        private UpdateGameAsyncTask(GameDao gameDao)
        {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.Update(games[0]);
            return null;
        }
    }

    private static class DeleteGameAsyncTask extends AsyncTask<Game, Void, Void>
    {
        private GameDao gameDao;

        private DeleteGameAsyncTask(GameDao gameDao)
        {
            this.gameDao = gameDao;
        }

        @Override
        protected Void doInBackground(Game... games) {
            gameDao.delete(games[0]);
            return null;
        }
    }
}
