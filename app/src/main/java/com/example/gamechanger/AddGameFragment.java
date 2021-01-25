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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Model;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class AddGameFragment extends Fragment {
    Button cancelBtn;
    Button saveBtn;

    Button mapBtn;

    EditText gameTitle;
    EditText gamePrice;

    ImageView avatarImageView;
    ImageButton editImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_game, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add new Game");

        avatarImageView = view.findViewById(R.id.addgame_avatar_imv);
        editImage = view.findViewById(R.id.addgame_edit_image_btn);

        gameTitle = view.findViewById(R.id.addgame_title_input);
        gamePrice = view.findViewById(R.id.addgame_price_input);

        cancelBtn = view.findViewById(R.id.addgame_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_addGame_to_mainFeed);
                /*MainFeedFragment mainFeedFragment = new MainFeedFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.addgame_layout, mainFeedFragment);
                fragmentTransaction.commit();*/

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
                BitmapDrawable drawable = (BitmapDrawable)avatarImageView.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //final Game game= new Game(title,price,null);
                //AddGameFragmentDirections.ActionAddGameToMainFeed action= AddGameFragmentDirections
                // .actionAddGameToMainFeed(title, price, null);
                Model.instance.uploadImage(bitmap, Model.instance.getUserId(), new Model.UploadImageListener() {
                    @Override
                    public void onComplete(String url) {
                        if (url == null){
                            displayFailedError();
                        }else{
                            Game game= new Game(title,price,url);
                            //game.setImageURL(url);

                            AddGameFragmentDirections.ActionAddGameToMainFeed action= AddGameFragmentDirections
                                    .actionAddGameToMainFeed(title, price, url);

                            /*Bundle bundle = new Bundle();
                            bundle.putString("gameTitle",title);
                            bundle.putString("gamePrice",price);
                            bundle.putString("imageUrl",url);*/

                            Toast.makeText(getActivity(), "image url saved", Toast.LENGTH_SHORT).show();

                            MainFeedFragment mainFeedFragment = new MainFeedFragment();
                            mainFeedFragment.setMainFeedFlag(1);

                            Navigation.findNavController(view).navigate(action);

                            Model.instance.addGame(game, new Model.AddGameListener() {
                                @Override
                                public void onComplete() {
                                    //mainFeedFragment.GetDataFromFirebase();
                                    Toast.makeText(getActivity(), "Complete", Toast.LENGTH_SHORT).show();
                                    /*
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    mainFeedFragment.setArguments(bundle);
                                    fragmentTransaction.replace(R.id.addgame_layout, mainFeedFragment);
                                    cancelBtn.setVisibility(v.GONE);
                                    saveBtn.setVisibility(v.GONE);
                                    fragmentTransaction.commit();*/
                                }
                            });

                        }
                    }
                });

                //Navigation.findNavController(view).navigate(R.id.action_addGame_to_mainFeed);
            }
        });
        mapBtn=view.findViewById(R.id.add_map);
        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_addGame_to_maps);

            }
        });
        Bundle bundle = getArguments();
        if(bundle!=null) {
            Double latitude;
            latitude = bundle.getDouble("latitude");
        }
        return view;
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
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);

                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                avatarImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();

                        }
                    }
                    break;
            }
        }
    }

}