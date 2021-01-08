package com.example.gamechanger;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamechanger.model.Game;
import com.example.gamechanger.model.GameAdapter;
import com.example.gamechanger.model.GameViewModel;
import com.example.gamechanger.model.Model;

import java.util.LinkedList;
import java.util.List;


public class MainFeedFragment extends Fragment {

    private GameViewModel gameViewModel;
    RecyclerView gamesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_feed, container, false);

        gamesList = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList.setLayoutManager(layoutManager);

        final GameAdapter gamesAdapter = new GameAdapter();
        gamesList.setAdapter(gamesAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                //update RecyclerView
                gamesAdapter.setGamesData(games);
            }
        });

        return view;
    }

}