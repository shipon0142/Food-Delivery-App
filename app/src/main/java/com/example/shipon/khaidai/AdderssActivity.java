package com.example.shipon.khaidai;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.NavigationActivity.fromAddress;
import static com.example.shipon.khaidai.VerifyFragment.userphoneno;


public class AdderssActivity extends AppCompatActivity {
    private Button submit;
    private TextView phonenum;
    private EditText name, address;
    public static boolean checkOrder = false;
    public static String totalorder;
    public static double previoustotal = 0;
    int child = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adderss);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add location");
        submit = findViewById(R.id.submitConfirm);
        phonenum = findViewById(R.id.phoneNumber);
        name = findViewById(R.id.customername);
        address = findViewById(R.id.customeraddress);
        phonenum.setText(userphoneno.toString());
        //String phonenum = getArguments().getString("phone_number");
        getTotal();
        child = 0;
        //  getTotal();
        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mCategoryRef = ref.child("Customerorder/" + phonenum.getText().toString()).child("information");
        final DatabaseReference mCategory = ref.child("Customerorder/" + phonenum.getText().toString());
        final DatabaseReference mC = ref.child("Customerorder/" + phonenum.getText().toString());
        mC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                child = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(AdderssActivity.this,totalorder,Toast.LENGTH_SHORT).show();
                if (totalorder.equals("ull")) {
                    previoustotal = 0;
                } else {

                    previoustotal = Double.valueOf(totalorder);

                }
                // Toast.makeText(AdderssActivity.this,""+previoustotal,Toast.LENGTH_SHORT).show();
                if (name.getText().toString().equals("")) {
                    name.setError("Enter Your name please");
                    return;
                }
                if (address.getText().toString().equals("")) {
                    address.setError("Enter Delivery please");
                    return;

                }

                AlertDialog.Builder builder = new AlertDialog.Builder(AdderssActivity.this);
                builder.setMessage("Are you want to confirm this order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mCategoryRef.removeValue();
                                if (child > 0) child--;
                                for (int i = 0; i < kcode.size(); i++) {
                                    // DatabaseReference tref = mCategoryRef.child("order-" + (11));
                                    mCategory.child("order-" + (child + 1)).child("foodcode").setValue(kcode.get(i).toString());
                                    mCategory.child("order-" + (child + 1)).child("price").setValue(kcost.get(i).toString());
                                    mCategory.child("order-" + (child + 1)).child("qty").setValue(kqty.get(i).toString());
                                    mCategory.child("order-" + (child + 1)).child("foodname").setValue(kfood.get(i).toString());
                                    child++;
                                }

                                mCategoryRef.child("name").setValue(name.getText().toString());
                                mCategoryRef.child("address").setValue(address.getText().toString());
                                mCategoryRef.child("delivery").setValue("pending");
                                mCategoryRef.child("review").setValue("pending");
                                mCategoryRef.child("percent").setValue("p" + percent);

                                mCategoryRef.child("total").setValue("p" + (afterPromototal_Cost + previoustotal));
                                Toast.makeText(getApplicationContext(), "Your order accepted succesfully.", Toast.LENGTH_SHORT).show();
                                // Intent  iuu = new Intent(AdderssActivity.this, ActivityMenu.class);
                                // fromAddress=true;

                                Bundle extra = new Bundle();
                                extra.putString("flag", "1");
                                // t.cancel();
                                Intent i = new Intent(AdderssActivity.this, ActivityMenu.class);
                                i.putExtras(extra);

                                startActivity(i, ActivityOptions.makeSceneTransitionAnimation(AdderssActivity.this).toBundle());
                                // Intent iuu = new Intent(AdderssActivity.this, NavigationActivity.class);
                                //  iuu.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                //  startActivity(iuu);
                                //   iuu.putExtra("KK","2");


                                //startActivity(i);


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                builder.show();

            }
        });

    }

    public void getTotal() {

        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference m = ref.child("Customerorder/" + phonenum.getText().toString()).child("information");

        m.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                totalorder = String.valueOf(dataSnapshot.child("total").getValue()).substring(1);
                String ddress = String.valueOf(dataSnapshot.child("address").getValue());
                String ame = String.valueOf(dataSnapshot.child("name").getValue());
                if (!ddress.equals("null")) {
                    address.setText(ddress);
                    name.setText(ame);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return;
    }

    @Override
    protected void onResume() {


        //  Toast.makeText(AdderssActivity.this,""+totalorder,Toast.LENGTH_SHORT).show();

        super.onResume();
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
}
