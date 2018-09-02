package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ClipData;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.support.v4.app.FragmentPagerAdapter;
import android.webkit.WebView;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;

import static com.example.shipon.khaidai.AdderssActivity.checkOrder;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.lg;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.t;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.MyNotificationService.check;
import static com.example.shipon.khaidai.VerifyFragment.deadline;
import static com.example.shipon.khaidai.VerifyFragment.offer;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.promo;
import static com.example.shipon.khaidai.VerifyFragment.tot;
import static com.example.shipon.khaidai.VerifyFragment.userphoneno;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TextView User;
    public static TextView loginv;
    public static boolean fromAddress = false;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (checkOrder == true) {
            checkOrder = false;
        }


        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        navigationView.setNavigationItemSelectedListener(this);
        User = (TextView) header.findViewById(R.id.userphone);
        loginv = (TextView) header.findViewById(R.id.loginV);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {

            userphoneno = user.getPhoneNumber();
            // retrivePromo();
        }
        if (userphoneno == null) {
            User.setText("Guest");
            loginv.setText("Sign in");
        } else {

            // String phone= user.getPhoneNumber();
            User.setText(userphoneno);
            loginv.setText("Sign out");
            Load OBJ = new Load();
            OBJ.retrivePromo();
        }
        // Fragment fragment=null;
        // fragment= new FirstPage_Class();
        //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //  ft.replace(R.id.idMainNav, fragment);
        //  ft.commit();
        //  Bundle extras=new Bundle();
        // if(extras.getString("check")!=null){
        //    Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
        // }
        if (fromAddress) {
            fromAddress = false;

            Bundle extra = new Bundle();
            extra.putString("flag", "1");
            t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i);
            // Fragment fragment=null;
            //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            //drawer.closeDrawer(GravityCompat.START);
            ///  Toast.makeText(getApplicationContext(),"22222",Toast.LENGTH_SHORT).show();
            //  navigationView.getMenu().getItem(1).setChecked(true);
        } else {
            // Toast.makeText(getApplicationContext(),"11111",Toast.LENGTH_SHORT).show();
        }

        Fragment fragment = null;
        fragment = new FirstPage_Class();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.idMainNav, fragment);
        ft.commit();
        DrawerLayout drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_layout.closeDrawer(GravityCompat.START);
        if (check == true) {
            check = false;
            onNavigationItemSelected(navigationView.getMenu().findItem(R.id.notification));
        }

    }


    public void retrivePromo() {
        DatabaseReference re;
        re = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mCategoryRef = re.child("promoactivated/" + User.getText().toString());
        mCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //  bkey.clear();
                // bratting.clear();
                //  bfoodName.clear();
                //  bprice.clear();
                // bdetails.clear();
                promo.clear();
                offer.clear();
                deadline.clear();

                for (DataSnapshot dsp : dataSnapshot.getChildren()) {

                    String val = dsp.getKey().toString();
                    String value = (String) dsp.child("deadline").getValue();
                    String value1 = (String) dsp.child("percent").getValue();
                    String value2 = (String) dsp.child("upto").getValue();
                    per.add(Double.valueOf(value1.substring(0, value1.length() - 1)));
                    tot.add(Double.valueOf(value2.substring(0, value2.length() - 3)));
                    promo.add(val);
                    offer.add(value1 + " off on your next order.(Upto " + value2 + ")");
                    deadline.add(value);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {


            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void displayScene(int id) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //   int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        //  if (id == R.id.action_settings) {
        //     return true;
        //  }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {


        } else if (id == R.id.myorder) {
            Bundle extra = new Bundle();
            extra.putString("flag", "1");
            // t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.menualorder) {
            Bundle extra = new Bundle();
            extra.putString("flag", "2");
            // t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.notification) {
            Bundle extra = new Bundle();
            extra.putString("flag", "3");
            //   t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i);
            //finish();
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.promo) {
            Bundle extra = new Bundle();
            extra.putString("flag", "4");
            // t.cancel();
            Intent i = new Intent(this, PromoActivity.class);
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.about) {
            Bundle extra = new Bundle();
            extra.putString("flag", "5");
            //  t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //finish();
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        } else if (id == R.id.hotline) {
            Bundle extra = new Bundle();
            extra.putString("flag", "10");
            //  t.cancel();
            Intent i = new Intent(this, ActivityMenu.class);
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //finish();
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void login(View view) {
        if (userphoneno == null) {
            //Toast.makeText(getApplicationContext(), "Log in succesfull", Toast.LENGTH_SHORT).show();
            //   t.cancel();
            lg = true;
            Intent i = new Intent(this, ActivityMenu.class);
            Bundle extra = new Bundle();
            extra.putString("flag", "6");
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            // Fragment fragment=null;
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            loginv.setText("Log in");
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            User.setText("Guest");
            userphoneno = null;
            afterPromototal_Cost = 0;
            total_Cost = 0;
            percent = 0;
            promo.clear();
            offer.clear();
            deadline.clear();
            per.clear();
            tot.clear();
            kfood.clear();
            kqty.clear();
            kcost.clear();
            kcode.clear();
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            Toast.makeText(getApplicationContext(), "Signout successful", Toast.LENGTH_SHORT).show();
        }

    }
}
