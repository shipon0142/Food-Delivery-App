package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.io.IOException;
import java.util.ArrayList;


import static com.example.shipon.khaidai.AdderssActivity.checkOrder;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.bdetails;
import static com.example.shipon.khaidai.FirstPage_Class.bfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.bkey;
import static com.example.shipon.khaidai.FirstPage_Class.bprice;
import static com.example.shipon.khaidai.FirstPage_Class.bratting;
import static com.example.shipon.khaidai.FirstPage_Class.ddetails;
import static com.example.shipon.khaidai.FirstPage_Class.details;
import static com.example.shipon.khaidai.FirstPage_Class.dfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.dkey;
import static com.example.shipon.khaidai.FirstPage_Class.dprice;
import static com.example.shipon.khaidai.FirstPage_Class.dratting;
import static com.example.shipon.khaidai.FirstPage_Class.flag;
import static com.example.shipon.khaidai.FirstPage_Class.foodName;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.key;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.price;
import static com.example.shipon.khaidai.FirstPage_Class.ratting;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.tot;

public class BreakFast_Activity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private SearchView searchView;
    private DatabaseReference ref;
    private RecyclerView lv;
    public static LinearLayout vieeew;
    public static TextView percentT, viewcart;
    public static TextView takaT;
    public static TextView textCartItemCount;
    public ArrayList<String> cachedEntries;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_break_fast_);
        lv = findViewById(R.id.recycler);
        setTitle("Food List");
        if (checkOrder == true) {
            finish();
        }
        vieeew = (LinearLayout) findViewById(R.id.viewButton);
        percentT = (TextView) findViewById(R.id.idPercent);
        takaT = (TextView) findViewById(R.id.idTaka);
        viewcart = (TextView) findViewById(R.id.vCart);

        BreakfastRecyclerAdopter myAdapter = new BreakfastRecyclerAdopter(this, foodName, price, details, ratting, key);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        lv.setLayoutManager(llm);
        lv.setAdapter(myAdapter);

        //  setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (kcode.size() > 0) {
            takaT.setVisibility(View.VISIBLE);
            percentT.setVisibility(View.VISIBLE);
            viewcart.setText("View your cart(" + kcode.size() + ")");
        } else {
            takaT.setVisibility(View.INVISIBLE);
            percentT.setVisibility(View.INVISIBLE);
        }
        vieeew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kcode.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Your cart is empty.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent ii = new Intent(BreakFast_Activity.this, CartActivity.class);

                    startActivity(ii);
                }
                // Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {

            this.finish();


        }
        if (id == R.id.action_cart) {
            // Bundle data = new Bundle();
            Intent i = new Intent(this, CartActivity.class);

            //  data.clear();
            //  data.putString("key", "null");
            //  i.putExtras(data);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //  Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // getMenuInflater().inflate(R.menu.cartitem, menu);

        //    final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        // searchView=myActionMenuItem.getActionView();
        //searchView = (SearchView) myActionMenuItem.getActionView();
        //   SearchView searchView= (SearchView) MenuItemCompat.getActionView((MenuItem) menu);
        //   searchView.setOnQueryTextListener(this);
        //  return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_bar, menu);
        // getMenuInflater().inflate(R.menu.badges, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);
        textCartItemCount.setText(String.valueOf(kcode.size()));
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        if (kcode.size() > 0) {
            vieeew.startAnimation(slideUp);
            vieeew.setVisibility(View.VISIBLE);
            total_Cost = 0;
            afterPromototal_Cost = 0;
            for (int i = 0; i < kcost.size(); i++) {
                String temp = kcost.get(i).substring(1, kcost.get(i).length());
                Double value = Double.valueOf(temp);
                total_Cost += value;
            }
            Double tcot = null;
            Double pe = null;
            for (int i = 0; i < tot.size(); i++) {

                if (tot.get(i) <= total_Cost) {
                    tcot = (total_Cost * per.get(i)) / 100;
                    pe = per.get(i);
                }

            }
            if (kcode.size() > 0) {
                takaT.setVisibility(View.VISIBLE);
                percentT.setVisibility(View.VISIBLE);
            } else {
                takaT.setVisibility(View.INVISIBLE);
                percentT.setVisibility(View.INVISIBLE);
            }
            if (pe != null) {
                Double vel = total_Cost - tcot;
                afterPromototal_Cost = vel;
                percentT.setText(pe + "% off");
                percent = pe;
                takaT.setText("Tk " + vel);
            } else {
                percentT.setText("");

                afterPromototal_Cost = total_Cost;
                takaT.setText("Tk " + afterPromototal_Cost);
            }
            textCartItemCount.setText(String.valueOf(kcode.size()));
            if (kcode.size() == 0) {
                takaT.setVisibility(View.INVISIBLE);
                percentT.setVisibility(View.INVISIBLE);
            }


        } else {
            vieeew.startAnimation(slideDown);
            //   vieeew.setVisibility(View.INVISIBLE);
        }
        try {
            cachedEntries = (ArrayList<String>) InternalStorage.readObject(this, "ccode");
            String size = String.valueOf(cachedEntries.size());
            textCartItemCount.setText(size);
            Toast.makeText(getApplicationContext(), "cached", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        ArrayList<String> result = new ArrayList<>();
        for (String name : foodName) {
            if (name.toLowerCase().contains(query.toLowerCase())) {
                result.add(name);
            }

        }
        // lv.setAdapter(result);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //   newText=newText.toLowerCase();
        // ArrayList<String> food = new ArrayList<String>();

        return false;
    }

}
