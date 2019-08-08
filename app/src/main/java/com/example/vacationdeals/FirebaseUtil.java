package com.example.vacationdeals;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseUtil {

    //FireBase instance entry point for the database
    public static FirebaseDatabase mFirebase;
    //Firebase reference location in db for read/write
    public static DatabaseReference mReference;
    public static FirebaseAuth mFirebaseAuth;
    public static FirebaseAuth.AuthStateListener mAuthListener;
    private  static FirebaseUtil mUtil;
    public static ArrayList<RichTravelDeal>  deals;
    private static AvailableDealsDisplay mcaller;
    private static  Boolean logedIn= false;
    private  static PrefManager prefManager;
    public static boolean isAdmin;

    private FirebaseUtil(){};

    public static  void  openFbReference(String reference, final AvailableDealsDisplay caller){
        if (mUtil == null){
            mUtil = new FirebaseUtil();
            mFirebase = FirebaseDatabase.getInstance();
            mFirebaseAuth = FirebaseAuth.getInstance();
            mcaller = caller;
            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if (firebaseAuth.getCurrentUser() == null){
                        FirebaseUtil.signIn();
                        prefManager= new PrefManager(caller.getBaseContext());
                        prefManager.setLogedIn(true);
                    } else {
                        String userId = firebaseAuth.getUid();
                        checkAdmin(userId);
                    }
                    prefManager= new PrefManager(caller.getBaseContext());
                    prefManager.setLogedIn(true);
                }
            };
        }
        deals = new ArrayList<>();
        mReference = mFirebase.getReference().child(reference);
    }

    private static void checkAdmin(String userId) {
        FirebaseUtil.isAdmin=false;
        DatabaseReference ref = mFirebase.getReference().child("admins")
                .child(userId);
        ChildEventListener listener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUtil.isAdmin=true;
                mcaller.showMenu();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(listener);

    }

    public static void addListener(){
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }
    public static void removeListener(){
        mFirebaseAuth.removeAuthStateListener(mAuthListener);
    }
    private static void signIn(){
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

// Create and launch sign-in intent
        mcaller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                1);

    }
}
