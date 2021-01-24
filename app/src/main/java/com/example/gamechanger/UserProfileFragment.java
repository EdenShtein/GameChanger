package com.example.gamechanger;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameAdapter;
import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;
import java.util.LinkedList;
import java.util.List;

public class UserProfileFragment extends Fragment {

    public GameViewModel gameViewModel;
    public RecyclerView gamesList_rv;

    public List<Game> userGames= new LinkedList<Game>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        String email = Model.instance.getUserEmail();
        String id = Model.instance.getUserId();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(email);

        gamesList_rv = view.findViewById(R.id.userprofile_posts_rv);
        gamesList_rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList_rv.setLayoutManager(layoutManager);


        ////----------------------------------------------/////

        //RecyclerView recyclerView = view.findViewById(R.id.userprofile_posts_rv);
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //Query query = gamesRef.whereEqualTo("OwnedBy", id);

        Model.instance.showUserGames(new Model.AddGameListener() {
            @Override
            public void onComplete() {
                GameAdapter gamesAdapter = new GameAdapter();
                gamesAdapter.setGamesData(userGames);
                gamesList_rv.setAdapter(gamesAdapter);
            }
        });






        /*FirestoreRecyclerOptions<Game> options = new FirestoreRecyclerOptions.Builder<Game>()
                .setQuery(query, Game.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Game, GameAdapter.GameHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GameAdapter.GameHolder gameHolder, int i, @NonNull Game game) {
                gameHolder.bindData(game,i);
            }

            @NonNull
            @Override
            public GameAdapter.GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfeed_list_row, parent, false);
                return new GameAdapter.GameHolder(view);
            }

        };*/




        /*FirestoreRecyclerOptions<Game> options = Model.instance.getUserGames();

        FirestoreRecyclerAdapter adapter = Model.instance.FirebaseRecyclerAdapter(options);
*/
        ///-----------------------------------------------////

        //gamesList_rv.setAdapter(adapter);

        /*gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);
        gameViewModel.getAllGames().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(List<Game> games) {
                //update RecyclerView
                gamesAdapter.setGamesData(games);
            }
        });*/


        return view;
    }

}