package com.example.gamechanger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;
import com.example.gamechanger.model.User.User;
import com.example.gamechanger.model.User.UserViewModel;

import java.util.Random;


public class SignUpFragment extends Fragment {
    TextView signinLink;
    EditText email;
    EditText password;
    EditText fName;
    EditText lName;
    EditText phonenumber;
    Button signup;

    UserViewModel userViewModel;

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
        fName=view.findViewById(R.id.signup_fname_input);
        lName=view.findViewById(R.id.signup_lname_input);
        phonenumber=view.findViewById(R.id.signup_phonenumber_input);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String useremail=email.getText().toString();
                String userpassword=password.getText().toString();
                String firstName=fName.getText().toString();
                String lastName=lName.getText().toString();
                String phoneNumber=phonenumber.getText().toString();
                if (useremail.equals("") || userpassword.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                } else {
                    final User user = new User(firstName, lastName, useremail, phoneNumber);
                    Model.instance.signUpFB(user,userpassword);
                    userViewModel.insert(user);
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