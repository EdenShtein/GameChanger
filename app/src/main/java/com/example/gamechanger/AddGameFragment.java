package com.example.gamechanger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class AddGameFragment extends Fragment {
    Button cancelBtn;
    Button saveBtn;

    EditText gameTitle;
    EditText gamePrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add new Game");

        gameTitle = view.findViewById(R.id.addgame_title_input);
        gamePrice = view.findViewById(R.id.addgame_price_input);

        cancelBtn = view.findViewById(R.id.addgame_cancel_btn);
        cancelBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_addGame_to_mainFeed));

        saveBtn = view.findViewById(R.id.addgame_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                AddGameFragmentDirections.ActionAddGameToMainFeed action = AddGameFragmentDirections.actionAddGameToMainFeed(title, price);
                MainFeedFragment mainFeedFragment = new MainFeedFragment();
                mainFeedFragment.setMainFeedFlag(1);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

}