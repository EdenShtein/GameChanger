package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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

        AddGameFragment addGameFragment = new AddGameFragment();
        TradeGameFragment tradeGameFragment = new TradeGameFragment();
        MainFeedFragment mainFeedFragment = new MainFeedFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //tradeBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_tradeGame));
       /* tradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.decision_layout, tradeGameFragment);
                tradeBtn.setVisibility(view.GONE);
                sellBtn.setVisibility(view.GONE);
                fragmentTransaction.commit();
            }
        });*/


        //sellBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_addGame));
       /* sellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.decision_layout, addGameFragment);
                sellBtn.setVisibility(view.GONE);
                tradeBtn.setVisibility(view.GONE);
                fragmentTransaction.commit();
            }
        });*/


        // cancelLink.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_decision_to_mainFeed));
       /* cancelLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTransaction.replace(R.id.decision_layout, mainFeedFragment);
                sellBtn.setVisibility(view.GONE);
                tradeBtn.setVisibility(view.GONE);
                fragmentTransaction.commit();
            }
        });*/


        return view;
    }
}