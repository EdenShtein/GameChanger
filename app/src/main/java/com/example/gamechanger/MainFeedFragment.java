package com.example.gamechanger;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.gamechanger.model.Game;
import com.example.gamechanger.model.GameAdapter;
import com.example.gamechanger.model.GameViewModel;
import com.example.gamechanger.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainFeedFragment extends Fragment {

    private GameViewModel gameViewModel;
    RecyclerView gamesList_rv;
    FloatingActionButton addGamebtn;
    String gameTitle;
    String gamePrice;
    static int flag =0;
    //private View view;

    public int getMainFeedFlag(){
        return flag;
    }
    public int setMainFeedFlag(int flag){
        return this.flag = flag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_main_feed, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Main Feed");

        gamesList_rv = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList_rv.setLayoutManager(layoutManager);

        final GameAdapter gamesAdapter = new GameAdapter();
        gamesList_rv.setAdapter(gamesAdapter);

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

        if (getMainFeedFlag() == 1) {
            checkForNewGame(view);
        }
        if (getMainFeedFlag() == 2) {
            checkForNewUpdate(view);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                gameViewModel.delete(gamesAdapter.getGames(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Game has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(gamesList_rv);

        gamesAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game, View v) {
                String title = game.getName();
                String price = game.getPrice();
                int Id = game.getId();
                MainFeedFragmentDirections.ActionMainFeedToEditGame action = MainFeedFragmentDirections.actionMainFeedToEditGame(title, price, Id);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    private void checkForNewGame(View view) {
        gameTitle = MainFeedFragmentArgs.fromBundle(getArguments()).getGameTitle();
        gamePrice = MainFeedFragmentArgs.fromBundle(getArguments()).getGamePrice();
        Game game = new Game(gameTitle,gamePrice);
        gameViewModel.insert(game);

        return;
    }

    private void checkForNewUpdate(View view) {
        gameTitle = MainFeedFragmentArgs.fromBundle(getArguments()).getGameTitle();
        gamePrice = MainFeedFragmentArgs.fromBundle(getArguments()).getGamePrice();
        Game game = new Game(gameTitle,gamePrice);
        gameViewModel.update(game);

        return;
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