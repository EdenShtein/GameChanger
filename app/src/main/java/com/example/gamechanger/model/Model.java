package com.example.gamechanger.model;

import android.app.Activity;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();

    public void signUpFB(String email,String password)
    {
        fireBase.signUpToFireBase(email,password,mActivity);
    }

    public interface LoginListener{
        void onComplete(boolean result);
    }
    public void logInFB(String email,String password, LoginListener listener)
    {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

}
