package com.example.gamechanger;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MainFeedFragment extends Fragment {
    RecyclerView gamesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_feed, container, false);
        gamesList = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        gamesList.setLayoutManager(layoutManager);
        return view;
    }
}