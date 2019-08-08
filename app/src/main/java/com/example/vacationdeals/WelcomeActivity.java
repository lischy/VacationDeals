package com.example.vacationdeals;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {
    //widgets
    private ViewPager sliderPager;
    private LinearLayout actionsLinearLayout;
    private  introSliderAdapter introSliderAdapter;
    private Button previousBtn, nextBtn;
    private  PrefManager prefManager;
    private TextView[] tvDots;

    //vars
    private  int mCurrentPage; //sets the current page on new page selection.
    private String TAG = this.getClass().getSimpleName();
    private Context context=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        prefManager = new PrefManager(this);
        if (prefManager.FirstLaunch()==false) {
            launchHomeScreen();
            finish();
        }

        sliderPager = findViewById(R.id.imgpager);
        actionsLinearLayout = findViewById(R.id.actions_linear_Layout);
        introSliderAdapter = new introSliderAdapter(this);
        sliderPager.setAdapter(introSliderAdapter);

        previousBtn = findViewById(R.id.btn_prev);
        nextBtn = findViewById(R.id.btn_next);


        addDots(0);
        sliderPager.addOnPageChangeListener(viewListener);

        //set onClickListener to the buttons.
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCurrentPage < introSliderAdapter.getCount()-1){
                    sliderPager.setCurrentItem(mCurrentPage + 1);
                }else {
                    launchHomeScreen();
                }
            }
        });
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sliderPager.setCurrentItem(mCurrentPage - 1);
            }
        });

    }

    private void launchHomeScreen() {
       //FirebaseUtil.openFbReference("richTravels",WelcomeActivity.this);
       prefManager.setFirstTimeLaunch(false);
       startActivity(new Intent(WelcomeActivity.this, AvailableDealsDisplay.class));
        finish();
        Toast.makeText(getApplicationContext(),"No more slides",Toast.LENGTH_SHORT).show();
        Log.d(TAG,"NextBtn OnClickListener: end of slides");
    }

    //add dot indicators.
    private void addDots(int position){

        tvDots = new TextView[introSliderAdapter.getCount()];
        //avoid creation of multiple number of dots
        actionsLinearLayout.removeAllViews();
        for (int i=0 ; i<tvDots.length ; i++){
            tvDots[i] = new TextView(this);
            tvDots[i].setText(Html.fromHtml("&#8226;"));
            tvDots[i].setTextSize(35);
            tvDots[i].setTextColor(getResources().getColor(R.color.colorAccent));
            actionsLinearLayout.addView(tvDots[i]);
        }

        if (tvDots.length > 0){
            tvDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            mCurrentPage = position;

            if (position ==0 ){
                nextBtn.setEnabled(true);
                previousBtn.setEnabled(false);
                previousBtn.setVisibility(View.INVISIBLE);

                nextBtn.setText("Next");
                previousBtn.setText("");
            }else if (position == tvDots.length -1){

                nextBtn.setEnabled(true);
                previousBtn.setEnabled(true);
                previousBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Finish");
                previousBtn.setText("Back");

            }else {

                nextBtn.setEnabled(true);
                previousBtn.setEnabled(true);
                previousBtn.setVisibility(View.VISIBLE);

                nextBtn.setText("Next");
                previousBtn.setText("Back");
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
