package com.example.gamechanger;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameViewModel;
import com.example.gamechanger.model.Model;
import com.google.firebase.firestore.FieldValue;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditGameFragment extends AddGameFragment {

    Button saveBtn;

    EditText gameTitle;
    EditText gamePrice;

    ImageView avatarImageView;
    ImageView map_Btn;
    ImageView editImageBtn;

    GameViewModel gameViewModel;

    String gameId;
    String title;
    String price;
    String ownerId;
    String imgUrl;

    static int map_flag =0;

    Map<String, Object> editGameMap;

    double latitude;
    double longitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_game, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Edit Game");

        gameViewModel = ViewModelProviders.of(getActivity()).get(GameViewModel.class);

        gameId = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameId();
        ownerId = Model.instance.getUserId();

        gameTitle = view.findViewById(R.id.editgame_title_input);
        title = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameTitle();
        gameTitle.setText(title);

        imgUrl = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameImage();

        gamePrice = view.findViewById(R.id.editgame_price_input);
        price = EditGameFragmentArgs.fromBundle(getArguments()).getEditGamePrice();
        gamePrice.setText(price);
        gamePrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

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

        game = new Game(title, price, imgUrl);
        avatarImageView = view.findViewById(R.id.editgame_avatar_imv);
        avatarImageView.setImageResource(R.drawable.gamechangersimple);
        if (imgUrl != null) {
            Picasso.get().load(imgUrl).placeholder(R.drawable.gamechangersimple).into(avatarImageView);
        }

        editImageBtn = view.findViewById(R.id.editgame_edit_image_btn);
        editImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editImage();
            }
        });

        saveBtn = view.findViewById(R.id.editgame_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                if (title.equals("") || price.equals("") || price.equals("$")) {
                    Toast.makeText(getActivity(),"Please Enter Full Data",Toast.LENGTH_SHORT).show();
                }
                else {
                    BitmapDrawable drawable = (BitmapDrawable) avatarImageView.getDrawable();
                    Bitmap bitmap = drawable.getBitmap();
                    Model.instance.uploadImage(bitmap, Model.instance.getUserId(), new Model.UploadImageListener() {
                        @Override
                        public void onComplete(String url) {
                            if (url == null) {
                                displayFailedError();
                            } else {

                                game = new Game(title, price, url);
                                game.setLatitude(latitude);
                                game.setLongitude(longitude);
                                gameViewModel.update(game);
                                //////////////////////
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                Date date = new Date();
                                editGameMap = new HashMap<>();
                                editGameMap.put("id", gameId);
                                editGameMap.put("gameName", title);
                                editGameMap.put("search",title.toLowerCase());
                                editGameMap.put("gamePrice", price);
                                editGameMap.put("price", Integer.parseInt(price.replace("$","")));
                                editGameMap.put("imageUrl", url);
                                editGameMap.put("Posted At", formatter.format(date));
                                editGameMap.put("latitude", latitude);
                                editGameMap.put("longitude", longitude);
                                editGameMap.put("OwnedBy", ownerId);


                                Model.instance.editGame(gameId, editGameMap, new Model.GameListener() {
                                    @Override
                                    public void onComplete() {
                                        EditGameFragmentDirections.ActionEditGameToGameDetails action = EditGameFragmentDirections.actionEditGameToGameDetails(title, price, gameId, url);
                                        Navigation.findNavController(view).navigate(action);
                                        Toast.makeText(getActivity(), "Game Edited Successfully", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            /*Model.instance.addGame(game, new Model.GameListener() {
                                @Override
                                public void onComplete() {

                                    Toast.makeText(getActivity(), "Added Game", Toast.LENGTH_SHORT).show();
                                    Model.instance.deleteFbGame(gameId, new Model.GameListener() {
                                        @Override
                                        public void onComplete() {
                                            Toast.makeText(getActivity(), "Deleted Game", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            });*/

                            }
                        }
                    });
                }
            }

        });
        map_Btn=view.findViewById(R.id.editgame_add_map);
        map_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapsFragment mapsFragment = new MapsFragment();
                mapsFragment.setEdit_flag(1);
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                EditGameFragmentDirections.ActionEditGameToMaps action = EditGameFragmentDirections.actionEditGameToMaps(title,price,gameId);
                Navigation.findNavController(view).navigate(action);
            }
        });

        UpdateGame(view);

        if(getMap_flag() == 1){
            checkForNewCoordinates();
            this.setMap_flag(0);
        }

       /* editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = gameTitle.getText().toString();
                String price = gamePrice.getText().toString();
                EditGameFragmentDirections.ActionEditGameToMainFeed action = EditGameFragmentDirections.actionEditGameToMainFeed(title, price);
                MainFeedFragment mainFeedFragment = new MainFeedFragment();
                mainFeedFragment.setMainFeedFlag(2);
                Navigation.findNavController(view).navigate(action);
            }
        });*/

        return view;
    }

    private void UpdateGame(View view) {
        /*String title = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameTitle();
        String price = EditGameFragmentArgs.fromBundle(getArguments()).getEditGamePrice();
        String id = EditGameFragmentArgs.fromBundle(getArguments()).getEditGameId();

        gameTitle.setText(title);
        gamePrice.setText(price);*/

        return;
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

    public void checkForNewCoordinates(){
        gameTitle.setText(EditGameFragmentArgs.fromBundle(getArguments()).getEditGameTitle());
        gamePrice.setText(EditGameFragmentArgs.fromBundle(getArguments()).getEditGamePrice());
        latitude = EditGameFragmentArgs.fromBundle(getArguments()).getLatitude();
        longitude = EditGameFragmentArgs.fromBundle(getArguments()).getLongitude();
    }

    public int getMap_flag() {
        return map_flag;
    }

    public void setMap_flag(int map_flag) {
        this.map_flag = map_flag;
    }
}