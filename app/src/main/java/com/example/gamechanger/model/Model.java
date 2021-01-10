package com.example.gamechanger.model;

import android.app.Activity;

import com.google.firebase.auth.FirebaseUser;

public class Model {

    public interface LoginListener{
        void onComplete(boolean result);
    }


    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public void signUpFB(String email,String password)
    {
        fireBase.signUpToFireBase(email,password,mActivity);
    }



    public void logInFB(String email,String password, LoginListener listener)
    {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Boolean isUserLogIn(){
        if(fireBase.getUser()!=null) {return true;}
        else {return false;}

    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

}
