package com.example.gamechanger;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class GameDetailsFragment extends Fragment {

    TextView title;
    TextView price;
    TextView postedBy;
    TextView postDate;

    ImageView gameImage;
    ImageView back_btn;
    ImageView edit_btn;

    Button contact_btn;

    String imageUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_details, container, false);

        back_btn = view.findViewById(R.id.gamedetails_close_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.mainFeedFragment);
            }
        });

        title = view.findViewById(R.id.gamedetails_game_title);
        title.setText(GameDetailsFragmentArgs.fromBundle(getArguments()).getGameDetailsTitle());

        price = view.findViewById(R.id.gamedetails_game_price);
        price.setText(GameDetailsFragmentArgs.fromBundle(getArguments()).getGameDetailsPrice());

        gameImage = view.findViewById(R.id.gamedetails_game_image);
        imageUrl = GameDetailsFragmentArgs.fromBundle(getArguments()).getGameDetailsImage();
        if (imageUrl != null){
            Picasso.get().load(imageUrl).placeholder(R.drawable.gamechangersimple).into(gameImage);
        }

        postedBy = view.findViewById(R.id.gamedetails_postby_input);


        return view;
    }
}