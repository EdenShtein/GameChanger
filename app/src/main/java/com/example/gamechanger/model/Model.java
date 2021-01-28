package com.example.gamechanger.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.User.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.sql.Timestamp;
import java.util.List;

public class Model {

    public interface SuccessListener{
        void onComplete(boolean result);
    }

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();



    public void signUpFB(User user,String password)
    {
        fireBase.signUpToFireBase(user,password,mActivity);
    }

    public void logInFB(String email,String password, SuccessListener listener)
    {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }
    public void resetPass(String email, SuccessListener listener)
    {
        fireBase.forgotPassword(email,mActivity, listener);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Boolean isUserLogIn(){
        return fireBase.isUserExist();

    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

    public String getUserId(){
        return fireBase.getId();
    }

    public String getUserEmail(){
        return fireBase.getEmail();
    }

    public interface UploadImageListener {
        void onComplete(String result);
    }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        fireBase.uploadImage(imageBmp, name, listener);
    }

    public interface AddGameListener {
        void onComplete();
    }

    public void addGame(final Game game, final AddGameListener listener) {
        fireBase.addGame(game, new AddGameListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public interface AddUserListener {
        void onComplete();
    }

    public void addUser(final User user, final AddUserListener listener) {
        fireBase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public interface FbGamesListener {
            void onComplete(List<Game> userGames);
    }

    public void showAllFbGames(final FbGamesListener listener){
        fireBase.showAllFbGames(listener);
    }

    public void showUserGames(final FbGamesListener listener){
        fireBase.showUserGames(listener);
    }

    public interface StringListener {
        void onComplete(String string);
    }

    public void getOwnerId(String gameId, StringListener string){
        fireBase.getOwnerId(gameId,string);
    }

    public void getOwnerName(String ownerId, StringListener string){
        fireBase.getOwnerName(ownerId,string);
    }

    public void getGameDate(String gameId, StringListener string){
        fireBase.getGameDate(gameId,string);
    }

}
