package com.example.shipon.khaidai;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.shipon.khaidai.FirstPage_Class.lg;
import static com.example.shipon.khaidai.FirstPage_Class.t;
import static com.example.shipon.khaidai.MyNotificationService.check;
import static com.example.shipon.khaidai.NavigationActivity.User;

public class ActivityMenu extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Bundle extra = getIntent().getExtras();
        String value = extra.getString("flag");

        if (value.equals("1")) {
            Fragment fragment = null;
            fragment = new MyorderFragment();
            //  fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();
            //  Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
        } else if (value.equals("2")) {

            Fragment fragment = null;
            fragment = new Menualorderfragment();
            //  fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();
        } else if (value.equals("3")) {

            Fragment fragment = null;
            fragment = new Notificationfragment();
            //  fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();
            //  Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
        } else if (value.equals("4")) {
            //Toast.makeText(getApplicationContext(), "4", Toast.LENGTH_SHORT).show();
        } else if (value.equals("5")) {

            Fragment fragment = null;
            fragment = new AboutUsfragment();
            //  fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();


            // Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
        } else if (value.equals("10")) {

            Fragment fragment = null;
            fragment = new Hotlinefragment();
            //  fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();


            // Toast.makeText(getApplicationContext(), "5", Toast.LENGTH_SHORT).show();
        } else if (value.equals("6") || value.equals("66")) {
            Fragment fragment = null;
            fragment = new PhoneNumberFragment();
            //  fragment.setArguments(data);
            Bundle data = new Bundle();
            if (value.equals("66")) {
                data.putString("check", "1");
            } else {
                data.putString("check", "0");
            }
            fragment.setArguments(data);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();
        }

    }

    @Override
    public void onBackPressed() {

        lg = false;
        NavUtils.navigateUpFromSameTask(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {
            //  super.onBackPressed();
            lg = false;
            this.finish();
            //Intent i= new Intent(this,NavigationActivity.class);
            //startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return super.onOptionsItemSelected(item);

    }
}
