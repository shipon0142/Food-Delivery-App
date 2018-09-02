package com.example.shipon.khaidai;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.shipon.khaidai.AdderssActivity.checkOrder;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.NavigationActivity.User;

public class CartActivity extends AppCompatActivity {
    RecyclerView RV;
    public static TextView empty;
    public static TextView totall;
    public static MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My cart");
        setContentView(R.layout.activity_cart);
        if (checkOrder == true) {
            this.finish();
        }
        //getSupportActionBar().setTitle("My cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        empty = findViewById(R.id.totaltk);
        if (afterPromototal_Cost != 0) {
            empty.setVisibility(View.GONE);
        } else {
            empty.setVisibility(View.VISIBLE);
            empty.setText("Your cart is empty");
        }


        // empty.setText("Total: "+afterPromototal_Cost+"BDT");
        RV = findViewById(R.id.carecycler);
        // if (getIntent().getExtras().getString("key").equals("notnull")) {
        //    String n = getIntent().getExtras().getString("name");
        //  String f = getIntent().getExtras().getString("foodcode");
        //  String k = getIntent().getExtras().getString("qty");
        //  String c = getIntent().getExtras().getString("price");
        //   kcode.add(f);
        //  kfood.add(n);
        //   kqty.add(k);
        //    kcost.add(c);
        //   }
        CartRecyclerAdopter myAdapter = new CartRecyclerAdopter(this, kfood, kcost, kqty, kcode);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        RV.setLayoutManager(llm);
        RV.setAdapter(myAdapter);


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
        getMenuInflater().inflate(R.menu.cartitem, menu);
        // getMenuInflater().inflate(R.menu.badges, menu);

        menuItem = menu.findItem(R.id.crt);

        menuItem.setTitle("Total: " + afterPromototal_Cost + " BDT");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == android.R.id.home) {
            //  super.onBackPressed();
            this.finish();
            //Intent i= new Intent(this,NavigationActivity.class);
            //startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
        return super.onOptionsItemSelected(item);
    }

    public void orderProdact(View view) {
        if (kcode.size() == 0) {
            Toast.makeText(getApplicationContext(), "Your cart is empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        // Fragment fragment=null;

        //  fragment= new PhoneNumberFragment();
        //  fragment.setArguments(data);
        //   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //   ft.replace(R.id.reView, fragment);
        //  ft.commit();
        if (User.getText().toString().length() > 7) {
            Intent iii = new Intent(this, AdderssActivity.class);

            startActivity(iii, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            //finish();
        } else {
            Intent i = new Intent(this, ActivityMenu.class);
            Bundle extra = new Bundle();
            extra.putString("flag", "66");
            i.putExtras(extra);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        }
    }
}
