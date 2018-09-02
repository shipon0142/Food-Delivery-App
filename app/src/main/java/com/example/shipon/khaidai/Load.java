package com.example.shipon.khaidai;

import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.shipon.khaidai.FirstPage_Class.bdetails;
import static com.example.shipon.khaidai.FirstPage_Class.bfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.bkey;
import static com.example.shipon.khaidai.FirstPage_Class.bprice;
import static com.example.shipon.khaidai.FirstPage_Class.bratting;
import static com.example.shipon.khaidai.FirstPage_Class.ddetails;
import static com.example.shipon.khaidai.FirstPage_Class.dfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.dkey;
import static com.example.shipon.khaidai.FirstPage_Class.dprice;
import static com.example.shipon.khaidai.FirstPage_Class.dratting;
import static com.example.shipon.khaidai.FirstPage_Class.edetails;
import static com.example.shipon.khaidai.FirstPage_Class.efoodName;
import static com.example.shipon.khaidai.FirstPage_Class.ekey;
import static com.example.shipon.khaidai.FirstPage_Class.eprice;
import static com.example.shipon.khaidai.FirstPage_Class.eratting;
import static com.example.shipon.khaidai.FirstPage_Class.fdetails;
import static com.example.shipon.khaidai.FirstPage_Class.ffoodName;
import static com.example.shipon.khaidai.FirstPage_Class.fkey;
import static com.example.shipon.khaidai.FirstPage_Class.fprice;

import static com.example.shipon.khaidai.FirstPage_Class.fratting;
import static com.example.shipon.khaidai.FirstPage_Class.menu1;
import static com.example.shipon.khaidai.FirstPage_Class.menu2;
import static com.example.shipon.khaidai.FirstPage_Class.menu3;
import static com.example.shipon.khaidai.FirstPage_Class.menu4;
import static com.example.shipon.khaidai.NavigationActivity.User;
import static com.example.shipon.khaidai.VerifyFragment.deadline;
import static com.example.shipon.khaidai.VerifyFragment.offer;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.promo;
import static com.example.shipon.khaidai.VerifyFragment.tot;

/**
 * Created by Shipon on 7/28/2018.
 */

public class Load {
    public void retrivePromo(){
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
                    per.add(Double.valueOf(value1.substring(0,value1.length()-1)));
                    tot.add(Double.valueOf(value2.substring(0,value2.length()-3)));
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
    public void BackgroundBreakfast(){
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

         DatabaseReference menuu=ref.child("menuname");
        menuu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            menu1= String.valueOf(dataSnapshot.child("menu1").getValue());
            menu2= String.valueOf(dataSnapshot.child("menu2").getValue());
            menu3= String.valueOf(dataSnapshot.child("menu3").getValue());
            menu4= String.valueOf(dataSnapshot.child("menu4").getValue());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        DatabaseReference mCategoryRef = ref.child("Breakfast");
        mCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bkey.clear();
                bratting.clear();
                bfoodName.clear();
                bprice.clear();
                bdetails.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    bkey.add(Integer.valueOf(dsp.getKey()));
                    bratting.add((String) dsp.child("ratting").getValue());
                    bfoodName.add((String) dsp.child("foodname").getValue());
                    bprice.add((String) dsp.child("price").getValue());
                    bdetails.add((String) dsp.child("details").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference dCategoryRef = ref.child("Dinner");
        dCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dkey.clear();
                dratting.clear();
                dfoodName.clear();
                dprice.clear();
                ddetails.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    dkey.add(Integer.valueOf(dsp.getKey()));
                    dratting.add((String) dsp.child("ratting").getValue());
                    dfoodName.add((String) dsp.child("foodname").getValue());
                    dprice.add((String) dsp.child("price").getValue());
                    ddetails.add((String) dsp.child("details").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference eCategoryRef = ref.child("menu3");
        eCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ekey.clear();
                eratting.clear();
                efoodName.clear();
                eprice.clear();
                edetails.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    ekey.add(Integer.valueOf(dsp.getKey()));
                    eratting.add((String) dsp.child("ratting").getValue());
                    efoodName.add((String) dsp.child("foodname").getValue());
                    eprice.add((String) dsp.child("price").getValue());
                    edetails.add((String) dsp.child("details").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference fCategoryRef = ref.child("menu4");
        fCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                fkey.clear();
                fratting.clear();
                ffoodName.clear();
                fprice.clear();
                fdetails.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    fkey.add(Integer.valueOf(dsp.getKey()));
                    fratting.add((String) dsp.child("ratting").getValue());
                    ffoodName.add((String) dsp.child("foodname").getValue());
                    fprice.add((String) dsp.child("price").getValue());
                    fdetails.add((String) dsp.child("details").getValue());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });




    }


}
