package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class AddGameFragment extends Fragment {
    Button cancelBtn;
    Button saveBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);

        cancelBtn = view.findViewById(R.id.addgame_cancel_btn);
        saveBtn = view.findViewById(R.id.addgame_save_btn);

        cancelBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addGameFragment_pop));
        return view;
    }
}