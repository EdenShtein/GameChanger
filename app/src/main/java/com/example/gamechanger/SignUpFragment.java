package com.example.gamechanger;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamechanger.model.FireBaseModel;
import com.example.gamechanger.model.Model;


public class SignUpFragment extends Fragment {
    TextView signinLink;
    EditText email;
    EditText password;
    Button signup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("GameChanger");

        email=view.findViewById(R.id.signup_email_input);
        password=view.findViewById(R.id.signup_password_input);
        signup=view.findViewById(R.id.signup_continue_btn);
        signinLink= view.findViewById(R.id.signin_link);




        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                if (useremail.equals("") && userpassword.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Email and Password",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.signUpFB(useremail, userpassword);
                    Navigation.findNavController(view).navigate(R.id.action_signup_to_signin);
                }
            }
        });
        signinLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_signup_to_signin);
            }
        });


        return view;
    }


}