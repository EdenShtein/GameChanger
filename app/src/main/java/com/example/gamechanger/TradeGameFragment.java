package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

// TODO remove this fragment - NOT INCLUED AFTER ALL

public class TradeGameFragment extends Fragment {

    Button saveBtn;
    Button cancelBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trade_game, container, false);
        saveBtn = view.findViewById(R.id.tradegame_save_btn);
        cancelBtn = view.findViewById(R.id.tradegame_cancel_btn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Navigation.findNavController(view).navigate(R.id.action_tradeGame_to_mainFeed);
            }
        });

        return view;
    }
}