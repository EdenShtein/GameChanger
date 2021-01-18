package com.example.gamechanger.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import java.util.List;

public class Model {

    public interface SuccessListener{
        void onComplete(boolean result);
    }


    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public void signUpFB(String email,String password)
    {
        fireBase.signUpToFireBase(email,password,mActivity);
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

}
