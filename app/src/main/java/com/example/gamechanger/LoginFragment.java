package com.example.gamechanger;

import android.content.Context;
import android.content.SyncStatusObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gamechanger.model.FireBaseModel;
import com.example.gamechanger.model.Model;

public class LoginFragment extends Fragment {
    Button SignUpBtn;
    Button signInBtn;
    Button forgotPass;
    EditText email;
    EditText password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("GameChanger");

            SignUpBtn = view.findViewById(R.id.signin_signup_btn);
            SignUpBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).navigate(R.id.action_login_to_signUp);
                }
            });

            signInBtn = view.findViewById(R.id.signin_login_btn);
            email = view.findViewById(R.id.signin_email_input);
            password = view.findViewById(R.id.signin_password_input);
            signInBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userEmail = email.getText().toString();
                    String userPassword = password.getText().toString();
                    if (userEmail.equals("") && userPassword.equals("")) {
                        Toast.makeText(getActivity(), "Please Enter Email and Password", Toast.LENGTH_SHORT).show();
                    } else {
                        Model.instance.logInFB(userEmail, userPassword, new Model.SuccessListener() {
                            @Override
                            public void onComplete(boolean result) {
                                if (result) {
                                    Navigation.findNavController(view).navigate(R.id.action_signin_to_mainFeed);
                                } else {
                                    Toast.makeText(getActivity(), "Failed to login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            });

            forgotPass = view.findViewById(R.id.signin_forgot_btn);
            forgotPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Navigation.findNavController(view).navigate((R.id.action_signin_to_forgetPass));
                }
            });

            return view;
    }

    @Override
    public void onStart() {
        super.onStart();
/*        if(Model.instance.isUserLogIn())
        {
            Fragment loginFragment = new LoginFragment();
            FragmentManager fm = getParentFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.mainFeedFragment,loginFragment);
            transaction.commit();
        }*/
    }
}
