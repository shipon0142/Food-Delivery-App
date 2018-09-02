package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.media.session.MediaSession;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


import static com.example.shipon.khaidai.FirstPage_Class.details;
import static com.example.shipon.khaidai.FirstPage_Class.foodName;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.price;
import static com.example.shipon.khaidai.FirstPage_Class.key;
import static java.lang.Integer.parseInt;
import static java.util.concurrent.TimeUnit.MINUTES;

public class OrderFood extends AppCompatActivity {
    private Button positive, quantity, negative;
    private int q = 0;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private Integer foodcode;
    FragmentManager fragmentManager;
    Fragment fragmenthave;
    private int pos;
    private double cost;
    private TextView Foodname, Details, Price, Total;
    Bundle data = new Bundle();

    //@SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food);
        this.setTitle("Order");
        positive = findViewById(R.id.buttonPositive);
        quantity = findViewById(R.id.buttonQuantity);
        negative = findViewById(R.id.buttonNegative);
        Foodname = findViewById(R.id.foodNameO);
        Details = findViewById(R.id.detailsO);
        Price = findViewById(R.id.priceO);
        Total = findViewById(R.id.totalO);
        String data = getIntent().getExtras().getString("position");
        int position = Integer.valueOf(data);
        Foodname.setText(foodName.get(position));
        Details.setText(details.get(position));
        String pp = price.get(position).substring(4, price.get(position).length());
        foodcode = key.get(position);
        cost = Double.valueOf((String) pp);
        Price.setText(Double.valueOf(pp) + "/-");
        q = 0;

        Total.setText(cost * q + "/-");

        quantity.setText("Qty-" + q);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q < 10) {
                    q++;
                    //  cost=Integer.valueOf(price.get(pos))*q;
                    Total.setText(cost * q + "/-");
                    quantity.setText("Qty-" + q);
                }

            }
        });
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (q > 0) {
                    q--;
                    // cost=Integer.valueOf(price.get(pos))*q;
                    Total.setText(cost * q + "/-");
                    quantity.setText("Qty-" + q);
                }
            }
        });

        // final   EditText instruction=findViewById(R.id.editInstruction);
     /*   instruction.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                instruction.setTranslationY(-600f);
                return true;
            }

        });
        */
        //  instruction.setTranslationY(-600f);
     /*   instruction.setOnEditorActionListener(new TextView.OnEditorActionListener() {
             @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(event!=null && (event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
                    InputMethodManager in=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(instruction.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                }
                instruction.setTranslationY(0f);
                return false;
            }
        });
        */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.cartitem, menu);
        // final MenuItem item = menu.findItem(R.id.badge);
        //   MenuItemCompat.setActionView(item, R.layout.badge);
        //   MenuItemCompat.setActionView(item, R.layout.badge);
        //    final View actionViewNotification = MenuItemCompat.getActionView(item);
        //   RelativeLayout badgeLayout = (RelativeLayout)    menu.findItem(R.id.badge).getActionView();
        //  TextView tv = (TextView) badgeLayout.findViewById(R.id.actionbar_notifcation_textview);
        //  tv.setText("12");
        //  actionViewNotification.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //    onOptionsItemSelected(item);
        //   Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
        //  }
        //   });


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        fragmentManager = getSupportFragmentManager();
        fragmenthave = fragmentManager.findFragmentById(R.id.orderfood);
        if (id == android.R.id.home) {

            if (fragmenthave != null) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(fragmenthave);
                fragmentTransaction.commit();
            } else {
                this.finish();
            }

        }
        if (id == R.id.crt) {
            Intent i = new Intent(this, CartActivity.class);
            //  data.clear();
            //  data.putString("key", "null");
            //  i.putExtras(data);
            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            finish();
            Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void order(View view) {
        // Fragment fragment=null;
        //   data.clear();
        // fragment= new PhoneNumberFragment();
        //  data.putString("key", "notnull");
        // data.putString("foodcode", "p"+foodcode.toString());
        //  data.putString("qty", ("p"+q).toString());
        //  data.putString("price",("p"+ cost*q).toString());
        //  data.putString("name","p"+ Foodname.getText().toString());
        if (kcode.contains("p" + foodcode.toString())) {

            int i = kcode.indexOf("p" + foodcode.toString());
            kcode.remove(i);

            kfood.remove(i);
            kqty.remove(i);
            kcost.remove(i);

        }

        kcode.add("p" + foodcode.toString());
        kfood.add("p" + Foodname.getText().toString());
        kqty.add(("p" + q).toString());
        kcost.add(("p" + cost * q).toString());
        Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();


        // i.putExtras(data);

        //  data.clear();
        //  data.putString("key", "null");
        //  i.putExtras(data);
        //    Intent i = new Intent(this, BreakFast_Activity.class);
        //  startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        //finish();
        this.finish();
        //    fragment.setArguments(data);
        //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //   ft.replace(R.id.orderfood, fragment);
        //    ft.commit();
    }

    @Override
    public void onBackPressed() {

        fragmentManager = getSupportFragmentManager();
        fragmenthave = fragmentManager.findFragmentById(R.id.orderfood);
        if (fragmenthave != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.remove(fragmenthave);
            fragmentTransaction.commit();
        } else super.onBackPressed();
    }
}
