package com.example.shipon.khaidai;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends AppCompatActivity {
    private ViewPager sliderViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sliderViewPager = findViewById(R.id.idsliderViewPager);
        ViewPagerAdapter_Class viewPagerAdapter_class = new ViewPagerAdapter_Class(this);
        sliderViewPager.setAdapter(viewPagerAdapter_class);
    }
}
