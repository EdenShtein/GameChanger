package com.example.gamechanger;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameAdapter;
import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;

import java.util.List;

public class UserProfileFragment extends Fragment {

    public GameViewModel gameViewModel;
    public RecyclerView gamesList_rv;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView back_btn;
    ImageView edit_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String email = Model.instance.getUserEmail();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(email);

        back_btn = view.findViewById(R.id.userprofile_back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_userProfile_to_mainFeed);
            }
        });

        edit_btn = view.findViewById(R.id.userprofile_edit_btn);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserProfileFragmentDirections.ActionUserProfileToEdituser action = UserProfileFragmentDirections.actionUserProfileToEdituser(email);
                Navigation.findNavController(view).navigate(action);
            }
        });

        gamesList_rv = view.findViewById(R.id.userprofile_posts_rv);
        gamesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList_rv.setLayoutManager(layoutManager);
        GameAdapter gamesAdapter = new GameAdapter();
        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        Model.instance.showUserGames(new Model.FbGamesListener() {
                    @Override
                    public void onComplete(List<Game> userGames) {
                        gamesAdapter.setGamesData(userGames);
                        gamesList_rv.setAdapter(gamesAdapter);
                    }
                });

        swipeRefreshLayout = view.findViewById(R.id.userprofile_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Model.instance.showUserGames(new Model.FbGamesListener() {
                    @Override
                    public void onComplete(List<Game> userGames) {
                        gamesAdapter.setGamesData(userGames);
                        gamesList_rv.setAdapter(gamesAdapter);
                    }
                });
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> userGames) {
                //update RecyclerView
                gamesAdapter.setGamesData(userGames);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                gameViewModel.delete(gamesAdapter.getGames(viewHolder.getAdapterPosition()));
                String gameId = gamesAdapter.getGames(viewHolder.getAdapterPosition()).getId();
                Model.instance.deleteFbGame(gameId, new Model.GameListener() {
                    @Override
                    public void onComplete() { }
                });
                Toast.makeText(getActivity(), "Post has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(gamesList_rv);

        gamesAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game, View v) {
                GameDetailsFragment gameDetailsFragment = new GameDetailsFragment();
                gameDetailsFragment.setUser_flag(1);
                String title = game.getName();
                String price = game.getPrice();
                String Id = game.getId();
                String imageUrl = game.getImageURL();
                UserProfileFragmentDirections.ActionUserProfileToGameDetails action = UserProfileFragmentDirections.actionUserProfileToGameDetails(title,price,Id,imageUrl);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }
}