package com.example.gamechanger.model;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameDao;
import com.example.gamechanger.model.User.User;

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

}
