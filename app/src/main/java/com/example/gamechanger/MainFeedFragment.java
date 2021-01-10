package com.example.gamechanger;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.gamechanger.model.Game;
import com.example.gamechanger.model.GameAdapter;
import com.example.gamechanger.model.GameViewModel;
import com.example.gamechanger.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainFeedFragment extends Fragment {

    private GameViewModel gameViewModel;
    RecyclerView gamesList;
    FloatingActionButton addGamebtn;
    //private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main_feed, container, false);

        gamesList = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList.setLayoutManager(layoutManager);

        final GameAdapter gamesAdapter = new GameAdapter();
        gamesList.setAdapter(gamesAdapter);

        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                //update RecyclerView
                gamesAdapter.setGamesData(games);
            }
        });

        addGamebtn = view.findViewById(R.id.mainfeed_addgame_btn);
        addGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_addGame);
            }
        });
        return view;
    }


/*
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.signoutmenu:
                Model.instance.signOutFB();
                Navigation.findNavController(this.view).navigate(R.id.action_mainFeed_to_signin);

        }
        return true;
    }
*/
}