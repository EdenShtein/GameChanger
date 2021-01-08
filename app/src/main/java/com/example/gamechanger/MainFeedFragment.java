package com.example.gamechanger;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamechanger.model.Game;
import com.example.gamechanger.model.Model;

import java.util.LinkedList;
import java.util.List;


public class MainFeedFragment extends Fragment {

    RecyclerView gamesList;
    List<Game> data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main_feed, container, false);

        gamesList = view.findViewById(R.id.mainfeed_gameslist_rv);
        gamesList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        gamesList.setLayoutManager(layoutManager);

        data = Model.instance.getAllGames();

        MyAdapter adapter = new MyAdapter();
        gamesList.setAdapter(adapter);

        return view;
    }

        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView gameText;
            TextView gameSubText;
            ImageView gameImage;
            int position;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                gameImage = itemView.findViewById(R.id.listrow_image_v);
                gameText = itemView.findViewById(R.id.listrow_text_v);
                gameSubText = itemView.findViewById(R.id.listrow_subtext_v);
            }

            public void bindData(Game game, int position){
                gameText.setText(game.name);
                gameSubText.setText(game.price);
                this.position = position;
            }
        }

        class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().inflate(R.layout.mainfeed_list_row, null);
                MyViewHolder holder = new MyViewHolder(view);
                return holder;
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                Game game = data.get(position);
                holder.bindData(game,position);
            }

            @Override
            public int getItemCount() {
                return data.size();
            }
        }





}