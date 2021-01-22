package com.example.gamechanger.model.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gamechanger.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    private List<Game> gamesData = new LinkedList<Game>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainfeed_list_row, parent, false);
        GameHolder holder = new GameHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Game currentGame = gamesData.get(position);
        holder.bindData(currentGame,position);
    }

    @Override
    public int getItemCount() {
        return gamesData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Game game, View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
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

    class GameHolder extends RecyclerView.ViewHolder{
        TextView gameText;
        TextView gameSubText;
        ImageView gameImage;
        int position;

        public GameHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.listrow_image_v);
            gameText = itemView.findViewById(R.id.listrow_text_v);
            gameSubText = itemView.findViewById(R.id.listrow_subtext_v);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                    {
                        listener.onItemClick(gamesData.get(position), v);
                    }
                }
            });
        }


        public void bindData(Game game, int position){
            gameText.setText(game.getName());
            gameSubText.setText(game.getPrice());

            //Getting image from URL

            gameImage.setImageResource(R.drawable.gamechangersimple);
            if (game.getImageURL() != null){
                Picasso.get().load(game.getImageURL()).placeholder(R.drawable.gamechangersimple).into(gameImage);
            }

           /* URL url = null;
            try {
                url = new URL(game.getImageURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BitmapDrawable drawable = (BitmapDrawable)gameImage.getDrawable();
            Bitmap bmp = drawable.getBitmap();
            try {
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }

            gameImage.setImageBitmap(bmp);*/

            this.position = position;
        }

    }
}
