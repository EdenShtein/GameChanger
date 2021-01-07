package com.example.gamechanger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

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
                Intent i = new Intent(Splash_Screen.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },5000);
    }
}