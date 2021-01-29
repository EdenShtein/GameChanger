package com.example.gamechanger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamechanger.model.Model;
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
    String gameId;
    String ownerId;
    String ownerName;
    String gameDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_details, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Game Details");

        gameId = GameDetailsFragmentArgs.fromBundle(getArguments()).getGameDetailsId();

        back_btn = view.findViewById(R.id.gamedetails_close_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).popBackStack();
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
        Model.instance.getOwnerId(gameId, new Model.StringListener() {
            @Override
            public void onComplete(String string) {
                ownerId = string;
                Model.instance.getOwnerName(ownerId, new Model.StringListener() {
                    @Override
                    public void onComplete(String string) {
                        ownerName = string;
                        postedBy.setText(ownerName);
                    }
                });
            }
        });

        postDate = view.findViewById(R.id.gamedetails_date_input);
        Model.instance.getGameDate(gameId, new Model.StringListener() {
            @Override
            public void onComplete(String string) {
                gameDate = string;
                postDate.setText(gameDate + " UTC");
            }
        });

        contact_btn = view.findViewById(R.id.gamedetails_contact_btn);
        contact_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model.instance.getOwnerPhone(ownerId, new Model.StringListener() {
                    @Override
                    public void onComplete(String data) {
                        phoneCalls(data);
                    }
                });
            }
        });



        return view;
    }

    public void phoneCalls(String phoneNumber)
    {
        if(ActivityCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},100);
            return;
        }
        String uri = "tel:" + phoneNumber.trim() ;
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(uri));
        startActivity(intent);
    }
}