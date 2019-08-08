package com.example.vacationdeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class AvailableDealsDisplay extends AppCompatActivity implements AvailableDealsAdapter.onDealListener{
    private  PrefManager prefManager;

/*private FirebaseDatabase mFirebaseDB;
    private DatabaseReference mDdReference;
    private ChildEventListener mChildListener;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_deals_display);
        FirebaseUtil.openFbReference("richTravels",AvailableDealsDisplay.this);
       /* FirebaseUtil.openFbReference("richTravels");
        mFirebaseDB = FirebaseUtil.mFirebase;
        mDdReference = FirebaseUtil.mReference;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                TextView mDeals = (TextView)findViewById(R.id.tvDeals);
                RichTravelDeal rDeals = dataSnapshot.getValue(RichTravelDeal.class);
                mDeals.setText(mDeals.getText()+ "\n"+rDeals.getTitle());
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
        mDdReference.addChildEventListener(mChildListener);*/

        prefManager = new PrefManager(this);
        if (prefManager.Logged()==false) {
            launchLogincreen();
            //finish();
        }


    }

    private void launchLogincreen() {
        FirebaseUtil.openFbReference("richTravels",AvailableDealsDisplay.this);
        FirebaseUtil.addListener();
        //finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.available_deals_menu,menu);
        MenuItem addDeal = menu.findItem(R.id.addDeal);
        if (FirebaseUtil.isAdmin == true) {
            addDeal.setVisible(true);
        }
        else {
            addDeal.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.addDeal: {
                Intent openAddDeal = new Intent(this, AID.class);
                startActivity(openAddDeal);
                return true;
            }
            case R.id.logout:{
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                                FirebaseUtil.addListener();
                                Log.d("Loged out","user Loged out");
                            }
                        });
                FirebaseUtil.removeListener();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //FirebaseUtil.removeListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUtil.openFbReference("richTravels",this);
        RecyclerView dealsRV = (RecyclerView)findViewById(R.id.dealsRV);
        final AvailableDealsAdapter  dealsAdapter= new AvailableDealsAdapter(this);
        dealsRV.setAdapter(dealsAdapter);

        dealsRV.setLayoutManager(new LinearLayoutManager(this));

        //FirebaseUtil.addListener();
    }


    @Override
    public void onDealclick(int position,ArrayList<RichTravelDeal> mDeals) {
        Log.d("Rich","onDealClicked: deal" + position + "clicked");
        Intent openAddDeal = new Intent(this, AID.class);
        openAddDeal.putExtra("DEAL",mDeals.get(position));
        startActivity(openAddDeal);

    }
    public void showMenu() {
        invalidateOptionsMenu();
    }
}
