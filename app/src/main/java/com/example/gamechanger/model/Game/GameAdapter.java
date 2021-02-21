package com.example.gamechanger.model.Game;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gamechanger.R;
import com.squareup.picasso.Picasso;
import java.util.LinkedList;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {

    public static List<Game> gamesData = new LinkedList<Game>();
    private static OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public Game getGames(int position)
    {
        return gamesData.get(position);
    }

    public void setGamesData(List<Game> games) {
        this.gamesData = games;
        notifyDataSetChanged();
    }

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
        holder.itemView.setTag(currentGame);
    }

    @Override
    public int getItemCount() {
        return gamesData.size();
    }

    public interface OnItemClickListener{
        void onItemClick(Game game, View view);
    }

    //---------------GameHolder----------------//

    public static class GameHolder extends RecyclerView.ViewHolder{
        TextView gameText;
        TextView gameSubText;
        ImageView gameImage;
        int position;

        public GameHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.gamedetails_game_image);
            gameText = itemView.findViewById(R.id.gamedetails_game_title);
            gameSubText = itemView.findViewById(R.id.gamedetails_game_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(gamesData.get(position), v);
                    }
                }
            });
        }

        public void bindData(Game game, int position){
            gameText.setText(game.getName());
            gameSubText.setText(game.getPrice());
            gameImage.setImageResource(R.drawable.gamechangersimple);
            String url = game.getImageURL();
            if (game.getImageURL() != null) {
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
