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

import com.example.gamechanger.model.Model;


public class ForgetPassFragment extends Fragment {

    Button resetBtn;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("GameChanger");

        resetBtn = view.findViewById(R.id.forgetpass_reset_btn);
        email = view.findViewById(R.id.forgetpass_email_input);
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                if (userEmail.equals("")) {
                    Toast.makeText(getActivity(),"You must enter Email",Toast.LENGTH_SHORT).show();
                }
                else {
                    Model.instance.resetPass(userEmail, new Model.SuccessListener() {
                        @Override
                        public void onComplete(boolean result) {
                            if (result) {
                                Navigation.findNavController(view).navigate(R.id.action_forgetPass_to_signin);
                            } else {
                                Toast.makeText(getActivity(), "Failed to send email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }
}