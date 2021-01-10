package com.example.gamechanger.model;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class FireBaseModel {
    public FirebaseAuth mAuth=FirebaseAuth.getInstance();

    public void signUpToFireBase (String email, String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
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

    public FirebaseUser getUser(){
        return mAuth.getCurrentUser();
    }

    public void signOutFromFireBase (){
        mAuth.signOut();
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


}
