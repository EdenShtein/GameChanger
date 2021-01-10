package com.example.gamechanger.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gamechanger.R;
import java.util.LinkedList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.MyViewHolder> {

    private List<Game> gamesData = new LinkedList<Game>();

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfeed_list_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Game currentGame = gamesData.get(position);
        holder.bindData(currentGame,position);
    }

    @Override
    public int getItemCount() {
        return gamesData.size();
    }

    public Game getGames(int position)
    {
        return gamesData.get(position);
    }

    public void setGamesData(List<Game> games)
    {
        this.gamesData = games;
        notifyDataSetChanged();
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
}
