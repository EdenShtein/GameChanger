package com.example.gamechanger.model;

import android.app.Activity;

public class Model {

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();


    private Model(){

    }
    public void signUpFB(String email,String password)
    {
        fireBase.signUpToFireBase(email,password,mActivity);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

}
