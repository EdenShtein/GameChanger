package com.example.gamechanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.gamechanger.model.Model;

public class Splash_Screen extends AppCompatActivity {
    ImageView splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);

        splash=findViewById(R.id.imageViewSplash);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        splash.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);

                if(Model.instance.isUserLogIn())
                    intent.putExtra("isLogin", true);

                    startActivity(intent);
                    finish();

            }
        },5000);
    }
}