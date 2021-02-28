package com.example.gamechanger;

import android.media.Image;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameAdapter;
import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainFeedFragment extends Fragment {

    private GameViewModel gameViewModel;
    public RecyclerView gamesList_rv;
    FloatingActionButton addGamebtn;
    String gameTitle;
    String gamePrice;
    String imageUrl;
    ImageView mapBtn;
    ImageView nameSort;
    ImageView priceSort;
    static int flag =0;
    private View view;
    SwipeRefreshLayout swipeRefreshLayout;
    GameAdapter gamesAdapter;

    public int getMainFeedFlag(){
        return flag;
    }
    public void setMainFeedFlag(int flag){ this.flag = flag; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_main_feed, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Games Feed");
        setHasOptionsMenu(true);
        gamesList_rv = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList_rv.setLayoutManager(layoutManager);
        gamesAdapter = new GameAdapter();
        gamesList_rv.setAdapter(gamesAdapter);
        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        Model.instance.showAllFbGames(new Model.FbGamesListener() {
            @Override
            public void onComplete(List<Game> userGames) {
                gamesAdapter.setGamesData(userGames);
                gamesList_rv.setAdapter(gamesAdapter);
            }
        });

        swipeRefreshLayout = view.findViewById(R.id.mainfeed_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.design_default_color_primary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Model.instance.showAllFbGames(new Model.FbGamesListener() {
                    @Override
                    public void onComplete(List<Game> userGames) {
                        gamesAdapter.setGamesData(userGames);
                        gamesList_rv.setAdapter(gamesAdapter);
                    }
                });

                addGamebtn.setEnabled(true);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        nameSort = view.findViewById(R.id.mainfeed_name_sort);
        nameSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.sortByName(new Model.FbGamesListener() {
                    @Override
                    public void onComplete(List<Game> userGames) {
                        gamesAdapter.setGamesData(userGames);
                        gamesList_rv.setAdapter(gamesAdapter);
                    }
                });
            }
        });

        priceSort = view.findViewById(R.id.mainfeed_price_sort);
        priceSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.sortByPrice(new Model.FbGamesListener() {
                    @Override
                    public void onComplete(List<Game> userGames) {
                        gamesAdapter.setGamesData(userGames);
                        gamesList_rv.setAdapter(gamesAdapter);
                    }
                });
            }
        });

        addGamebtn = view.findViewById(R.id.mainfeed_addgame_btn);
        addGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_addGame);
            }
        });

        //For Details Game
        gamesAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game, View v) {
                GameDetailsFragment gameDetailsFragment = new GameDetailsFragment();
                gameDetailsFragment.setFeed_flag(1);
                String title = game.getName();
                String price = game.getPrice();
                String Id = game.getId();
                String imageUrl = game.getImageURL();
                MainFeedFragmentDirections.ActionMainFeedToGameDetails action = MainFeedFragmentDirections.actionMainFeedToGameDetails(title,price,Id,imageUrl);
                Navigation.findNavController(view).navigate(action);
            }
        });

        mapBtn= view.findViewById(R.id.mainfeed_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDetailsFragment gameDetailsFragment  = new GameDetailsFragment();
                gameDetailsFragment.setMap_flag(1);
                Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_generalMap);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem search = menu.findItem(R.id.searchmenu);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //Called when we press search button
            public boolean onQueryTextSubmit(String query) {
                if(query.isEmpty()) {
                    Model.instance.showAllFbGames(new Model.FbGamesListener() {
                        @Override
                        public void onComplete(List<Game> userGames) {
                            gamesAdapter.setGamesData(userGames);
                            gamesList_rv.setAdapter(gamesAdapter);
                        }
                    });
                } else {
                    Model.instance.searchGame(query, new Model.FbGamesListener() {
                        @Override
                        public void onComplete(List<Game> searchGames) {
                            gamesAdapter.setGamesData(searchGames);
                            gamesList_rv.setAdapter(gamesAdapter);
                        }
                    });
                }
                
                return false;
            }

            //Called as and when we type even a single letter
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signoutmenu:
                if(view != null) {
                    Model.instance.signOutFB();
                    Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_signin);
                }
                break;
            case R.id.myprofilemenu:
                if(view != null) {
                    Navigation.findNavController(view).navigate(R.id.userProfileFragment);
                }
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}