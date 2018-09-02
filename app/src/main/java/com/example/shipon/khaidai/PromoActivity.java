package com.example.shipon.khaidai;

import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.shipon.khaidai.FirstPage_Class.notifi;
import static com.example.shipon.khaidai.FirstPage_Class.notifidate;
import static com.example.shipon.khaidai.NavigationActivity.User;
import static com.example.shipon.khaidai.VerifyFragment.deadline;
import static com.example.shipon.khaidai.VerifyFragment.offer;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.promo;
import static com.example.shipon.khaidai.VerifyFragment.tot;

public class PromoActivity extends AppCompatActivity {

    private RecyclerView RV;
    private Button applyPromo;
    private EditText code;
    private String promocode;
    private TextView nopromo;
    PromoRecyclerAdopter myAdapter;
    DatabaseReference re;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);
        RV = findViewById(R.id.promoRecycler);
        applyPromo = findViewById(R.id.idPromo);
        nopromo = findViewById(R.id.nopromo);

        setTitle("My promotions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        re = FirebaseDatabase.getInstance().getReference();
        retrivePromo();


        applyPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getText().toString() == "Guest") {
                    Toast.makeText(getApplicationContext(), "Please login First", Toast.LENGTH_SHORT).show();
                    return;
                }
                LayoutInflater myLayout = LayoutInflater.from(PromoActivity.this);
                final Dialog dialog = new Dialog(PromoActivity.this); // Context, this, etc.-
                dialog.setContentView(R.layout.dialog);
                Button oorder = dialog.findViewById(R.id.dOrder);
                TextView oclose = dialog.findViewById(R.id.dClose);
                code = dialog.findViewById(R.id.codeee);
                Window window = dialog.getWindow();
                WindowManager.LayoutParams lp = window.getAttributes();
                window.setGravity(Gravity.CENTER);
                oclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                oorder.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        promocode = code.getText().toString();
                        if (promocode.equals("")) {
                            code.setError("Please enter code");
                        } else {

                            // final DatabaseReference promoactive=re.child("promo/"+User.getText());
                            DatabaseReference promocod = re.child("promo/all/" + promocode);
                            promocod.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {

                                        // code.setError("ok");
                                        //Toast.makeText(getApplicationContext(), "ache", Toast.LENGTH_SHORT).show();


                                        String value = (String) dataSnapshot.child("deadline").getValue();
                                        String value1 = (String) dataSnapshot.child("percent").getValue();
                                        String value2 = (String) dataSnapshot.child("upto").getValue();
                                        DatabaseReference ref = re.child("promoactivated/" + User.getText().toString() + "/" + promocode);

                                        ref.child("percent").setValue(value1);
                                        ref.child("deadline").setValue(value);
                                        ref.child("upto").setValue(value2);
                                        per.add(Double.valueOf(value1.substring(0, value1.length() - 1)));
                                        tot.add(Double.valueOf(value2.substring(0, value2.length() - 3)));
                                        promo.add(promocode);
                                        offer.add(value1 + " off on your next order.(Upto " + value2 + ")");
                                        deadline.add(value);
                                        if (promo.size() == 0) {
                                            nopromo.setVisibility(View.VISIBLE);
                                            RV.setVisibility(View.GONE);
                                        } else {
                                            RV.setVisibility(View.VISIBLE);
                                            nopromo.setVisibility(View.GONE);
                                        }
                                        myAdapter = new PromoRecyclerAdopter(PromoActivity.this, promo, offer, deadline);
                                        LinearLayoutManager llm = new LinearLayoutManager(PromoActivity.this);
                                        llm.setOrientation(LinearLayoutManager.VERTICAL);
                                        RV.setLayoutManager(llm);
                                        RV.setAdapter(myAdapter);
                                        Toast.makeText(getApplicationContext(), "Promo added succesfully.", Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Promo code not valid.", Toast.LENGTH_SHORT).show();

                                    }
                                    //if(dataSnapshot.hasChild(promocode)){

                                    //    code.setError("ok");
                                    //    Toast.makeText(getApplicationContext(),"ache",Toast.LENGTH_SHORT).show();
                                    //  }
                                    // else {
                                    //  code.setError("no");
                                    //   Toast.makeText(getApplicationContext(),"no",Toast.LENGTH_SHORT).show();
                                    //  }


                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });


                        }


                    }
                });


                // lp.alpha = 0.7f;
                //   window.setAttributes(lp);
                dialog.setCancelable(true);
                dialog.show();

            }
        });

    }

    @Override
    protected void onResume() {
        retrivePromo();
        super.onResume();
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
                    deadline.add("Deadline: " + value);

                }
                if (promo.size() == 0) {
                    nopromo.setVisibility(View.VISIBLE);
                    RV.setVisibility(View.GONE);
                } else {
                    RV.setVisibility(View.VISIBLE);
                    nopromo.setVisibility(View.GONE);
                    myAdapter = new PromoRecyclerAdopter(PromoActivity.this, promo, offer, deadline);
                    LinearLayoutManager llm = new LinearLayoutManager(PromoActivity.this);
                    llm.setOrientation(LinearLayoutManager.VERTICAL);
                    RV.setLayoutManager(llm);
                    RV.setAdapter(myAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
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
