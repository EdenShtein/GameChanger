package com.example.gamechanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.content.Intent;
import android.os.Bundle;
import com.example.gamechanger.model.Model;


public class MainActivity extends AppCompatActivity  {

    NavController navController;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Model.instance.setActivity(this);

        navController = Navigation.findNavController(this,R.id.fragment);


        Intent intent = getIntent();
        if(intent != null){
            if(intent.getBooleanExtra("isLogin", false)){
                userIsLogin();
            }
        }
    }

    public void userIsLogin(){
        navController.navigate(R.id.action_signin_to_mainFeed);
    }


}