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


        class MyViewHolder extends RecyclerView.ViewHolder{
            TextView gameText;
            TextView gameSubText;
            ImageView gameImage;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                gameImage = view.findViewById(R.id.listrow_image_v);
                gameText = view.findViewById(R.id.listrow_text_v);
                gameSubText = view.findViewById(R.id.listrow_subtext_v);
            }
        }

        /*class MyAdapter extends RecyclerView.Adapter<>{

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public int getItemCount() {
                return 0;
            }
        }*/

        return view;
    }


}