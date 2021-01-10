package com.example.gamechanger;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gamechanger.model.Game;

public class EditGameFragment extends Fragment {

    Button cancelBtn;
    Button editBtn;

    EditText gameTitle;
    EditText gamePrice;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_game, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Game");

        gameTitle = view.findViewById(R.id.editgame_title_input);
        gamePrice = view.findViewById(R.id.editgame_price_input);

        cancelBtn = view.findViewById(R.id.editGame_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("EditGameFragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        editBtn = view.findViewById(R.id.editGame_edit_btn);

        UpdateGame(view);

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                EditGameFragmentDirections.ActionEditGameToMainFeed action = EditGameFragmentDirections.actionEditGameToMainFeed(title, price);
                MainFeedFragment mainFeedFragment = new MainFeedFragment();
                mainFeedFragment.setMainFeedFlag(2);
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    private void UpdateGame(View view) {
        String title = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameTitle();
        String price = EditGameFragmentArgs.fromBundle(getArguments()).getEditGamePrice();
        int id = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameId();

        gameTitle.setText(title);
        gamePrice.setText(price);


        return;
    }
}