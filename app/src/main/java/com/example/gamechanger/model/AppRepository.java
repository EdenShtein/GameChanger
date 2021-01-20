package com.example.gamechanger.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameDao;
import com.example.gamechanger.model.Listing.Listing;
import com.example.gamechanger.model.Listing.ListingDao;
import com.example.gamechanger.model.User.User;
import com.example.gamechanger.model.User.UserDao;

import java.util.List;

public class AppRepository {

    private GameDao gameDao;
    private LiveData<List<Game>> games;

    private ListingDao listingDao;
    private LiveData<List<Listing>> listings;

    private UserDao userDao;
    private LiveData<List<User>> users;

    public AppRepository(Application application)
    {
        AppDatabase database = AppDatabase.getInstance(application);

        gameDao = database.gameDao();
        games = gameDao.getAllGames();

        listingDao = database.listingDao();
        listings = listingDao.getAllListings();

        userDao = database.userDao();
        users = userDao.getAllUsers();

    }

    public void insert(Game game)
    {
        new InsertGameAsyncTask(gameDao).execute(game);
    }
    public void insert(Listing listing)
    {
        new InsertListingAsyncTask(listingDao).execute(listing);
    }
    public void insert(User user)
    {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public void update(Game game)
    {
        new UpdateGameAsyncTask(gameDao).execute(game);
    }
    public void update(Listing listing)
    {
        new UpdateListingAsyncTask(listingDao).execute(listing);
    }
    public void update(User user)
    {
        new UpdateUserAsyncTask(userDao).execute(user);
    }

    public void delete(Game game)
    {
        new DeleteGameAsyncTask(gameDao).execute(game);
    }
    public void delete(Listing listing)
    {
        new DeleteListingAsyncTask(listingDao).execute(listing);
    }
    public void delete(User user) { new DeleteUserAsyncTask(userDao).execute(user); }


    public LiveData<List<Game>> getAllGames()
    {
        return games;
    }
    public LiveData<List<Listing>> getAllListings()
    {
        return listings;
    }
    public LiveData<List<User>> getAllUsers()
    {
        return users;
    }

    //Games---------///
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

    //Users---------///
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void>
    {
        private UserDao userDao;

        private InsertUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.Insert(users[0]);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void>
    {
        private UserDao userDao;

        private UpdateUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.Update(users[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<User, Void, Void>
    {
        private UserDao userDao;

        private DeleteUserAsyncTask(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.delete(users[0]);
            return null;
        }
    }

    //Listings------------///

    private static class InsertListingAsyncTask extends AsyncTask<Listing, Void, Void>
    {
        private ListingDao listingDao;

        private InsertListingAsyncTask(ListingDao listingDao)
        {
            this.listingDao = listingDao;
        }

        @Override
        protected Void doInBackground(Listing... listings) {
            listingDao.Insert(listings[0]);
            return null;
        }
    }

    private static class UpdateListingAsyncTask extends AsyncTask<Listing, Void, Void>
    {
        private ListingDao listingDao;

        private UpdateListingAsyncTask(ListingDao listingDao)
        {
            this.listingDao = listingDao;
        }

        @Override
        protected Void doInBackground(Listing... listings) {
            listingDao.Update(listings[0]);
            return null;
        }
    }

    private static class DeleteListingAsyncTask extends AsyncTask<Listing, Void, Void>
    {
        private ListingDao listingDao;

        private DeleteListingAsyncTask(ListingDao listingDao)
        {
            this.listingDao = listingDao;
        }

        @Override
        protected Void doInBackground(Listing... listings) {
            listingDao.delete(listings[0]);
            return null;
        }
    }
}
