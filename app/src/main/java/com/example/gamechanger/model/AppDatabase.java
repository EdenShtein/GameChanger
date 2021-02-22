package com.example.gamechanger.model;

import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameDao;
import com.example.gamechanger.model.User.User;
import com.example.gamechanger.model.User.UserDao;

@Database(entities = {Game.class, User.class}, version = 18, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract GameDao gameDao();
    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app_database").fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){

        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private GameDao gameDao;
        private UserDao userDao;

        private PopulateDbAsyncTask(AppDatabase database) {
            gameDao = database.gameDao();
            userDao = database.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //gameDao.Insert(new Game("Title 1", "Price 1", ""));
            return null;
        }
    }
}
