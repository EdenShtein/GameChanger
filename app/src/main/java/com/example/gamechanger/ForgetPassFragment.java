package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class ForgetPassFragment extends Fragment {

    Button resetBtn;
    EditText email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_pass, container, false);

        resetBtn = view.findViewById(R.id.forgetpass_reset_btn);
        email = view.findViewById(R.id.forgetpass_email_input);


        return view;
    }
}