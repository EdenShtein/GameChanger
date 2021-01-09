package com.example.gamechanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Display;
import android.widget.Toast;

import com.example.gamechanger.model.Model;

public class MainActivity extends AppCompatActivity implements SignUpFragment.OnComplete {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Model.instance.setActivity(this);

    }

    @Override
    public void onSignUpComplete(String user, String password) {
        Model.instance.signUpFB(user, password);
    }
}