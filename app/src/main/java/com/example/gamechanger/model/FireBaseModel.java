package com.example.gamechanger.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.example.gamechanger.R;
import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.Game.GameAdapter;
import com.example.gamechanger.model.User.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FireBaseModel {

    public FirebaseAuth mAuth=FirebaseAuth.getInstance();
    public FirebaseStorage storage = FirebaseStorage.getInstance();
    public FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void signUpToFireBase (User user,String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(user.getEmail(), password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            user.setId(mAuth.getCurrentUser().getUid());
                            Model.instance.addUser(user,()->{
                            });
                            Toast.makeText(activity, "User Created Successfully", Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "User Failed To Create", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    public void logInToFireBase (String email, String password, Activity activity, Model.SuccessListener listener){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(activity, "Sign In was Successfully", Toast.LENGTH_SHORT).show();
                            listener.onComplete(true);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "FAIL login Firebase:" + task.getException(), Toast.LENGTH_SHORT).show();
                            listener.onComplete(false);
                        }

                        // ...
                    }
                });
    }

    public void forgotPassword(String email,Activity activity, Model.SuccessListener listener){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(activity, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success");
                    Toast.makeText(activity, "Reset was successful", Toast.LENGTH_SHORT).show();
                    listener.onComplete(true);
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(activity, "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                    listener.onComplete(false);
                }
            }
        });

    }



    public void uploadImage(Bitmap imageBmp, String name, final Model.UploadImageListener listener){
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        final StorageReference imagesRef = storage.getReference(mAuth.getCurrentUser().getEmail()).child("game"+ timeStamp);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });
            }
        });
    }

    public void addUser(User user, final Model.AddUserListener listener) {
        db.collection("Users").document(user.getId())
                .set(user.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","User added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding User");
                listener.onComplete();
            }
        });
    }

    public void updateUser(User user, final Model.AddUserListener listener) {
        Map<String, Object> edituser = new HashMap<>();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String id = mAuth.getCurrentUser().getUid();
        String phoneNumber = user.getPhoneNumber();

        edituser.put("id", id);
        edituser.put("fName", firstName);
        edituser.put("lName", lastName);
        edituser.put("email", email);
        edituser.put("lastUpdated", FieldValue.serverTimestamp());
        edituser.put("phone", phoneNumber);

        db.collection("Users").document(id)
                .update(edituser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","User added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","fail adding User");
                listener.onComplete();
            }
        });
    }

    public void addGame(Game game, final Model.GameListener listener) {
        db.collection("Games").document(String.valueOf(game.getId()))
                .set(game.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG","Game added successfully");
                listener.onComplete();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG","failed adding Game");
                listener.onComplete();
            }
        });
    }


    public Boolean isUserExist(){
        if(mAuth.getCurrentUser()!=null)
        {
            return true;
        }else {return false;}
    }
    public String getEmail(){
        return mAuth.getCurrentUser().getEmail();
    }

    public void signOutFromFireBase (){
        mAuth.signOut();
    }

    public String getId(){return mAuth.getCurrentUser().getUid();}

    public void showUserGames(final Model.FbGamesListener listener){
        String id = Model.instance.getUserId();
        List<Game> userGames = new LinkedList<Game>();
        db.collection("Games").whereEqualTo("OwnedBy", id).get().addOnCompleteListener((new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot querySnapshot : task.getResult()){
                        Game game = new Game();
                        game.fromMap(querySnapshot.getData());
                        userGames.add(game);
                        Log.d("TAG","game: " + game.getId());
                    }
                }
                listener.onComplete(userGames);
            }
        }));
    }

    public void showAllFbGames(final Model.FbGamesListener listener){
        List<Game> userGames = new LinkedList<Game>();
        db.collection("Games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().isEmpty() == false){
                        for (DocumentSnapshot doc : task.getResult()){
                            Game game = new Game();
                            game.fromMap(doc.getData());
                            userGames.add(game);
                            Log.d("TAG","game: " + game.getId());
                        }
                    }
                }
                listener.onComplete(userGames);
            }
        });
    }

    public void getOwnerId(String gameId, Model.StringListener listener){
        db.collection("Games").document(gameId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    String ownedBy = doc.getString("OwnedBy");
                    listener.onComplete(ownedBy);
                }
            }
        });
    }

    public void getOwnerName(String ownerId, Model.StringListener listener){
        db.collection("Users").document(ownerId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            String firstName = doc.getString("fName");
                            String LastName = doc.getString("lName");
                            String fullName = firstName + " " + LastName;
                            listener.onComplete(fullName);
                        }
                    }
                });
    }

    public void getUserData(String userId, Model.userDataListener listener){
        db.collection("Users").document(userId).get()
               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                       if (task.isSuccessful()){
                           DocumentSnapshot doc = task.getResult();
                           String firstName = doc.getString("fName");
                           String LastName = doc.getString("lName");
                           String phoneNum = doc.getString("phone");
                           String city = doc.getString("city");
                           listener.onComplete(firstName,LastName,phoneNum);
                       }
                   }
               });
    }

    public void getGameDate(String gameId, Model.StringListener listener){
        db.collection("Games").document(gameId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            String date = doc.get("Posted At").toString();
                            listener.onComplete(date);
                        }
                    }
                });
    }

    public void getOwnerPhone(String ownerId, Model.StringListener listener){
        db.collection("Users").document(ownerId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot doc = task.getResult();
                            String phoneNumber = doc.getString("phone");
                            listener.onComplete(phoneNumber);
                        }
                    }
                });
    }

    public void editGame(String gameId, Map<String,Object> map, Model.GameListener listener){

        db.collection("Games").document(gameId)
                .update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
        listener.onComplete();
    }

    public void deleteFbGame(String gameId, Model.GameListener listener){
        db.collection("Games").document(gameId).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Game successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error deleting game");
                    }
                });
    }


    public void getLatLong(final Model.LatLongListener listener){
        String id = Model.instance.getUserId();
        List<Double> latitudePoints = new LinkedList<Double>();
        List<Double> longitudePoints = new LinkedList<Double>();
        List<String> gameID = new LinkedList<String>();
        db.collection("Games").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot querySnapshot : task.getResult()){
                        Game game = new Game();
                        game.fromMap(querySnapshot.getData());
                        latitudePoints.add(game.getLatitude());
                        longitudePoints.add(game.getLongitude());
                        gameID.add(game.getId());

                    }
                }
                listener.onComplete(latitudePoints,longitudePoints,gameID);
            }
        });
    }

    public void getGameDetails(String gameId,final Model.GameDataListener listener){
        db.collection("Games").document(gameId).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){

                                DocumentSnapshot doc = task.getResult();
                                    Game game = new Game();
                                    game.fromMap(doc.getData());

                                listener.onComplete(game);


                            }


                    }
                });
    }

    public void getSearchableGame(String query,Model.FbGamesListener listener)
    {
        List<Game> matchGames = new LinkedList<Game>();
        db.collection("Games").whereEqualTo("search", query.toLowerCase()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot querySnapshot : task.getResult()){
                        Game game = new Game();
                        game.fromMap(querySnapshot.getData());
                        matchGames.add(game);

                    }
                }
                listener.onComplete(matchGames);
            }
        });
    }

    public void sortByName(final Model.FbGamesListener listener){
        List<Game> userGames = new LinkedList<Game>();
        db.collection("Games").orderBy("search", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().isEmpty() == false){
                        for (DocumentSnapshot doc : task.getResult()){
                            Game game = new Game();
                            game.fromMap(doc.getData());
                            userGames.add(game);
                        }
                    }
                }
                listener.onComplete(userGames);
            }
        });
    }

    public void sortByPrice(final Model.FbGamesListener listener){
        List<Game> userGames = new LinkedList<Game>();
        db.collection("Games").orderBy("price", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().isEmpty() == false){
                        for (DocumentSnapshot doc : task.getResult()){
                            Game game = new Game();
                            game.fromMap(doc.getData());
                            userGames.add(game);
                        }
                    }
                }
                listener.onComplete(userGames);
            }
        });
    }


}
