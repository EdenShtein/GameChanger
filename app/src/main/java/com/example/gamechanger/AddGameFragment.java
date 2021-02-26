package com.example.gamechanger;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;

import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class AddGameFragment extends Fragment {
    private View view;
    Game game;
    Button cancelBtn;
    Button saveBtn;

    EditText gameTitle;
    EditText gamePrice;

    private GameViewModel gameViewModel;

    ImageView avatarImageView;
    ImageButton editImage;
    ImageView mapBtn;

    double latitude;
    double longitude;

    static int mapFlag =0;

    public int getMapFlag(){
        return mapFlag;
    }
    public void setMapFlag(int flag){ this.mapFlag = flag; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_add_game, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add new Game");

        avatarImageView = view.findViewById(R.id.addgame_avatar_imv);
        editImage = view.findViewById(R.id.addgame_edit_image_btn);

        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        gameTitle = view.findViewById(R.id.addgame_title_input);
        gamePrice = view.findViewById(R.id.addgame_price_input);
        gamePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            //presenting dollar sign next to price
            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches("^\\$(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 0 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 0) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 0, "");

                    gamePrice.removeTextChangedListener(this);
                    gamePrice.setText(cashAmountBuilder.toString());

                    gamePrice.setTextKeepState("$" + cashAmountBuilder.toString());
                    Selection.setSelection(gamePrice.getText(), cashAmountBuilder.toString().length() + 1);

                    gamePrice.addTextChangedListener(this);

                    // getting the value as float
                    //Float amount = Float.parseFloat(gamePrice.getText().toString().replace("$" ,""));
            }
        }});

        cancelBtn = view.findViewById(R.id.addgame_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_addGame_to_mainFeed);
            }
        });

        editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        saveBtn = view.findViewById(R.id.addgame_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                if (title.equals("") || price.equals("")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data", Toast.LENGTH_SHORT).show();
                }
                else {
                    BitmapDrawable drawable = (BitmapDrawable)avatarImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    //final Game game= new Game(title,price,null);
                    //AddGameFragmentDirections.ActionAddGameToMainFeed action= AddGameFragmentDirections
                    // .actionAddGameToMainFeed(title, price, null);
                    Model.instance.uploadImage(bitmap, Model.instance.getUserId(), new Model.UploadImageListener() {
                        @Override
                        public void onComplete(String url) {
                            if (url == null) {
                                displayFailedError();
                            } else {
                                game = new Game(title, price, url);
                                game.setLatitude(latitude);
                                game.setLongitude(longitude);

                                AddGameFragmentDirections.ActionAddGameToMainFeed action = AddGameFragmentDirections
                                        .actionAddGameToMainFeed(title, price, url);

                                MainFeedFragment mainFeedFragment = new MainFeedFragment();
                                mainFeedFragment.setMainFeedFlag(1);

                                Navigation.findNavController(view).navigate(action);
                                gameViewModel.insert(game);
                                //adding game to firebase collection
                                Model.instance.addGame(game, new Model.GameListener() {
                                    @Override
                                    public void onComplete() {
                                        Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        mapBtn=view.findViewById(R.id.add_map);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setAdd_flag(1);
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                AddGameFragmentDirections.ActionAddGameToMaps action = AddGameFragmentDirections.actionAddGameToMaps(title,price,null);
                Navigation.findNavController(view).navigate(action);
            }
        });

        //updating main feed about new game
        if(getMapFlag() == 1) {
            checkForNewCoordinates();
            this.setMapFlag(0);
        }
        return view;
    }

    public void checkForNewCoordinates(){
        gameTitle.setText(AddGameFragmentArgs.fromBundle(getArguments()).getGameAddTitle());
        gamePrice.setText(AddGameFragmentArgs.fromBundle(getArguments()).getGameAddPrice());
        latitude = AddGameFragmentArgs.fromBundle(getArguments()).getLatitude();
        longitude = AddGameFragmentArgs.fromBundle(getArguments()).getLongitude();
    }

    private void displayFailedError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Operation Failed");
        builder.setMessage("Saving image failed, please try again later...");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    private void editImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        avatarImageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            avatarImageView.setImageBitmap(selectedImage);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                    break;
            }
        }
    }
}
























































































































































