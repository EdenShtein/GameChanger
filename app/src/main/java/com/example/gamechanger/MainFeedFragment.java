package com.example.gamechanger;

import android.media.Image;
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
import android.widget.ImageView;
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
    static int flag =0;
    private View view;

    public int getMainFeedFlag(){
        return flag;
    }
    public void setMainFeedFlag(int flag){ this.flag = flag; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_main_feed, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Main Feed");
        setHasOptionsMenu(true);
        gamesList_rv = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList_rv.setLayoutManager(layoutManager);

        final GameAdapter gamesAdapter = new GameAdapter();
        gamesList_rv.setAdapter(gamesAdapter);

        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        Model.instance.showAllFbGames(new Model.FbGamesListener() {
            @Override
            public void onComplete(List<Game> userGames) {
                gamesAdapter.setGamesData(userGames);
                gamesList_rv.setAdapter(gamesAdapter);

            }

        });
        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> userGames) {
                //update RecyclerView
                gamesAdapter.setGamesData(userGames);
            }
        });



        addGamebtn = view.findViewById(R.id.mainfeed_addgame_btn);
        addGamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_decision);
                /*DecisionFragment decisionFragment = new DecisionFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainfeed_layout, decisionFragment);
                addGamebtn.setVisibility(v.GONE);
                fragmentTransaction.commit();*/
            }
        });

        if (getMainFeedFlag() == 1) {
            checkForNewGame(view);
            this.setMainFeedFlag(0);
        }
        if (getMainFeedFlag() == 2) {
            checkForNewUpdate(view);
            this.setMainFeedFlag(0);
        }

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                gameViewModel.delete(gamesAdapter.getGames(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Post has been deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(gamesList_rv);

        //For Editing Game
        gamesAdapter.setOnItemClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game, View v) {
                String title = game.getName();
                String price = game.getPrice();
                String Id = game.getId();
                MainFeedFragmentDirections.ActionMainFeedToEditGame action = MainFeedFragmentDirections.actionMainFeedToEditGame(title, price, Id);
                Navigation.findNavController(view).navigate(action);
            }
        });



        mapBtn= view.findViewById(R.id.mainfeed_map_btn);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFeed_to_maps);
            }
        });


        return view;
    }

    /*public void GetDataFromFirebase(){

        Query query = myRef.child("Games");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //ClearAll();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Game game = new Game();
                    game.setImageURL(snapshot.child("imgUrl").getValue().toString());
                    game.setName(snapshot.child("gameName").getValue().toString());
                    game.setPrice(snapshot.child("gamePrice").getValue().toString());

                    gmList.add(game);
                }

                gameAdapter = new GameAdapter(gmList,getActivity().getApplicationContext());
                gamesList_rv.setAdapter(gameAdapter);
                gameAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }*/

    private void checkForNewGame(View view) {

        gameTitle = MainFeedFragmentArgs.fromBundle(getArguments()).getGameTitle();
        gamePrice = MainFeedFragmentArgs.fromBundle(getArguments()).getGamePrice();
        imageUrl = MainFeedFragmentArgs.fromBundle(getArguments()).getImageUrl();

        /*Bundle bundle = getArguments();
        gameTitle = bundle.getString("gameTitle");
        gamePrice = bundle.getString("gamePrice");
        imageUrl = bundle.getString("imageUrl");*/
        //imageUrl = MainFeedFragmentArgs.fromBundle(getArguments()).getImageUrl();

        Game game = new Game(gameTitle,gamePrice,imageUrl);
        gameViewModel.insert(game);

        return;
    }

    private void checkForNewUpdate(View view) {
        gameTitle = MainFeedFragmentArgs.fromBundle(getArguments()).getGameTitle();
        gamePrice = MainFeedFragmentArgs.fromBundle(getArguments()).getGamePrice();
        imageUrl = MainFeedFragmentArgs.fromBundle(getArguments()).getImageUrl();
        Game game = new Game(gameTitle,gamePrice,imageUrl);
        gameViewModel.update(game);

        return;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu,inflater);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
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