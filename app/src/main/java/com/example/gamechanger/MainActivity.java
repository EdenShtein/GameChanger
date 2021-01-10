package com.example.gamechanger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import com.example.gamechanger.model.Model;

public class MainActivity extends AppCompatActivity implements SignUpFragment.OnComplete,LoginFragment.OnComplete, ForgetPassFragment.OnComplete {

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

    @Override
    public void onSignInComplete(String user, String password, Model.SuccessListener listener) {
        Model.instance.logInFB(user, password, listener);
    }



    @Override
    public void onResetComplete(String email, Model.SuccessListener listener) {
        Model.instance.resetPass(email, listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

}