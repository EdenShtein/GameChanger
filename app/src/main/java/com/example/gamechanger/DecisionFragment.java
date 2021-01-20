package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class DecisionFragment extends Fragment {

    Button tradeBtn;
    Button sellBtn;
    TextView cancelLink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_decision, container, false);

        tradeBtn = view.findViewById(R.id.decision_trading_btn);
        sellBtn = view.findViewById(R.id.decision_selling_btn);
        cancelLink = view.findViewById(R.id.decision_cancel_text);

        tradeBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_tradeGame));
        sellBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_addGame));
        cancelLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_mainFeed));

        return view;
    }
}