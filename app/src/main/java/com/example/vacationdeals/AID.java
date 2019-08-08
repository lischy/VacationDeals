package com.example.vacationdeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AID extends AppCompatActivity {

    //FireBase instance entry point for the database
    private FirebaseDatabase mFirebaseDB;
    //Firebase reference location in db for read/write
    private DatabaseReference mDdReference;
    //Widgets
    EditText tvTitle,tvDescription,tvPrice;

    RichTravelDeal deal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aid);

        //FirebaseUtil.openFbReference("richTravels",this);
        mFirebaseDB = FirebaseUtil.mFirebase;
        mDdReference = FirebaseUtil.mReference;

        tvTitle = (EditText)findViewById(R.id.tv_title);
        tvPrice = (EditText)findViewById(R.id.tv_price);
        tvDescription =  (EditText)findViewById(R.id.tv_description);

        Intent intent =getIntent();
        RichTravelDeal deal = (RichTravelDeal)intent.getParcelableExtra("DEAL");
        if (deal == null){
            deal = new RichTravelDeal();
        }
        this.deal = deal;
        tvTitle.setText(deal.getTitle());
        tvDescription.setText(deal.getDescription());
        tvPrice.setText(deal.getPrice());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_deal_menu:{
                addDeal();
                reset();
                Toast.makeText(this,"Deal added",Toast.LENGTH_SHORT).show();
                back();
                return  true;
            }
            case R.id.delete_deal_menu:{
                deleteDeal();
                Toast.makeText(this,"Deal deleted",Toast.LENGTH_SHORT).show();
                back();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }



    private void addDeal() {
        deal.setTitle(tvTitle.getText().toString());
        deal.setDescription(tvDescription.getText().toString());
        deal.setPrice(tvPrice.getText().toString());

        if (deal.getId() == null){
            //insert the new item into the database using the push method.
            mDdReference.push().setValue(deal);
        }else {
            mDdReference.child(deal.getId()).setValue(deal);
        }

    }

    private  void deleteDeal(){
        if (deal == null){
            return;
        }
        mDdReference.child(deal.getId()).removeValue();
    }
    private void reset() {
        tvTitle.setText("");
        tvTitle.requestFocus();
        tvDescription.setText("");
        tvPrice.setText("");
    }

    private  void  back(){
        Intent back = new Intent(this,AvailableDealsDisplay.class);
        startActivity(back);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_deal_menu,menu);
        if (FirebaseUtil.isAdmin) {
            menu.findItem(R.id.delete_deal_menu).setVisible(true);
            menu.findItem(R.id.save_deal_menu).setVisible(true);
            enableEditTexts(true);
        }
        else {
            menu.findItem(R.id.delete_deal_menu).setVisible(false);
            menu.findItem(R.id.save_deal_menu).setVisible(false);
            enableEditTexts(false);
        }

        return true;
    }

    private void enableEditTexts(boolean b) {
        tvTitle.setEnabled(b);
        tvDescription.setEnabled(b);
        tvPrice.setEnabled(b);
    }
}
