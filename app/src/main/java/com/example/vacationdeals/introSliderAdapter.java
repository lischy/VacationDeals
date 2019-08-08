package com.example.vacationdeals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class introSliderAdapter  extends PagerAdapter {

    Context context;
    public introSliderAdapter(Context context){
        this.context = context;
    }

    //Array that holds the images
    public  int[] swipe_images = {
            R.drawable.img_slider1,
            R.drawable.img_slider2,
            R.drawable.img_slider3
    };
    //Array that holds the sliderHeadings.
    public String[] slider_headings ={
            "CANADA",
            "USA",
            "KENYA"

    };

    //Array that holds the sliderDescriptions.
    public String[] slider_descriptions ={
            "Vacation to Canada",
            "Vacation to United States of America",
            "Great holiday deals to Kenya"

    };
    @Override
    public int getCount() {
        return slider_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //assign view to the main object
        return view==(RelativeLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.intro_slider,container,false);

        ImageView slider_image= (ImageView)view.findViewById(R.id.slider_imageView);
        TextView slider_heading = (TextView)view.findViewById(R.id.slider_heading);
        TextView slider_description = (TextView)view.findViewById(R.id.slider_description);

        //setResources
        slider_image.setImageResource(swipe_images[position]);
        slider_heading.setText(slider_headings[position]);
        slider_description.setText(slider_descriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
