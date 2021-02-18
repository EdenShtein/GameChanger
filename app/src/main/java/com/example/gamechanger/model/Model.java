package com.example.gamechanger.model;

import android.app.Activity;
import android.graphics.Bitmap;

import com.example.gamechanger.model.Game.Game;
import com.example.gamechanger.model.User.User;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

    public interface Listener<T>{
        void onComplete(T t);
    }

    public interface SuccessListener{
        void onComplete(boolean result);
    }

    private Activity mActivity;
    public final static Model instance = new Model();
    FireBaseModel fireBase = new FireBaseModel();



    public void signUpFB(User user,String password)
    {
        fireBase.signUpToFireBase(user,password,mActivity);
    }

    public void logInFB(String email,String password, SuccessListener listener)
    {
        fireBase.logInToFireBase(email,password,mActivity, listener);
    }
    public void resetPass(String email, SuccessListener listener)
    {
        fireBase.forgotPassword(email,mActivity, listener);
    }

    public void setActivity(Activity activity){
        this.mActivity = activity;
    }

    public Boolean isUserLogIn(){
        return fireBase.isUserExist();

    }

    public void signOutFB(){
        fireBase.signOutFromFireBase();
    }

    public String getUserId(){
        return fireBase.getId();
    }

    public String getUserEmail(){
        return fireBase.getEmail();
    }

    public interface UploadImageListener {
        void onComplete(String result);
    }

    public void uploadImage(Bitmap imageBmp, String name, final UploadImageListener listener) {
        fireBase.uploadImage(imageBmp, name, listener);
    }

    public interface GameListener {
        void onComplete();
    }

    public void addGame(final Game game, final GameListener listener) {
        fireBase.addGame(game, new GameListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public interface AddUserListener {
        void onComplete();
    }

    public void addUser(final User user, final AddUserListener listener) {
        fireBase.addUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void updateUser(final User user, final AddUserListener listener) {
        fireBase.updateUser(user, new AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public interface FbGamesListener {
            void onComplete(List<Game> userGames);
    }

    public void showAllFbGames(final FbGamesListener listener){
        fireBase.showAllFbGames(listener);
    }

    public void showUserGames(final FbGamesListener listener){
        fireBase.showUserGames(listener);
    }

    public interface StringListener {
        void onComplete(String data);
    }

    public void getOwnerId(String gameId, StringListener data){
        fireBase.getOwnerId(gameId,data);
    }

    public void getOwnerName(String ownerId, StringListener data){
        fireBase.getOwnerName(ownerId,data);
    }

    public void getGameDate(String gameId, StringListener data){
        fireBase.getGameDate(gameId,data);
    }

    public void getOwnerPhone(String ownerId, StringListener listener){
        fireBase.getOwnerPhone(ownerId,listener);
    }

    public void deleteFbGame(String gameId, GameListener listener){
        fireBase.deleteFbGame(gameId,listener);
    }

    public interface LatLongListener {
        void onComplete(List<Double> latitudePoint,List<Double> longitudePoints,List<String> gameIDS);
    }

    public void getLatLongPoint(LatLongListener listener)
    {
        fireBase.getLatLong(listener);
    }

    public interface GameDataListener {
        void onComplete(Game game);
    }

    public void getGameData(String gameID,GameDataListener listener)
    {
        fireBase.getGameDetails(gameID,listener);
    }

    public void editGame(String gameId, Map<String, Object> map, GameListener listener){
        fireBase.editGame(gameId, map, listener);
    }

    public interface userDataListener {
        void onComplete(String fname, String lname, String phone);
    }

    public void getUserData(String userId, userDataListener listener){
        fireBase.getUserData(userId,listener);
    }

}
