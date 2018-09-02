package com.example.shipon.khaidai;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.shipon.khaidai.FirstPage_Class.bdetails;
import static com.example.shipon.khaidai.FirstPage_Class.bfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.bkey;
import static com.example.shipon.khaidai.FirstPage_Class.bprice;
import static com.example.shipon.khaidai.FirstPage_Class.bratting;
import static com.example.shipon.khaidai.FirstPage_Class.menu1;
import static com.example.shipon.khaidai.FirstPage_Class.menu2;
import static com.example.shipon.khaidai.FirstPage_Class.menu3;
import static com.example.shipon.khaidai.FirstPage_Class.menu4;
import static com.example.shipon.khaidai.VerifyFragment.userphoneno;

/**
 * Created by Shipon on 3/4/2018.
 */

public class MyorderFragment extends Fragment {
    RadioButton phone1, phone2;
    TextView nameTV, addressTV, totalTV, offTV, deliveryTV, noorder;
    LinearLayout orrder;
    RecyclerView Rv;
    Button call;
    String address, name, total, delevery, review, mypercent;
    public static ArrayList<String> myFoodname = new ArrayList<String>();
    public static ArrayList<Integer> myQuantity = new ArrayList<Integer>();
    public static ArrayList<Double> myOrderPrice = new ArrayList<Double>();
    public static ArrayList<Integer> no = new ArrayList<Integer>();
    public static int c = 1;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("My order");

        // setDisplayHomeAsUpEnabled(true);
        //  getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.my_order, container, false);
        orrder = view.findViewById(R.id.orrder);
        noorder = view.findViewById(R.id.noOrder);
        Rv = view.findViewById(R.id.mRecycle);
        nameTV = view.findViewById(R.id.mname);
        addressTV = view.findViewById(R.id.maddress);
        totalTV = view.findViewById(R.id.mtaka);
        offTV = view.findViewById(R.id.moff);
        deliveryTV = view.findViewById(R.id.mdelivery);
        noorder.setVisibility(View.VISIBLE);
        orrder.setVisibility(View.GONE);
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();

        DatabaseReference menuu = ref.child("Customerorder/" + userphoneno + "/information");

        menuu.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                address = String.valueOf(dataSnapshot.child("address").getValue());
                name = String.valueOf(dataSnapshot.child("name").getValue());
                total = String.valueOf(dataSnapshot.child("total").getValue());
                delevery = String.valueOf(dataSnapshot.child("delivery").getValue());
                review = String.valueOf(dataSnapshot.child("review").getValue());
                mypercent = String.valueOf(dataSnapshot.child("percent").getValue());
                if (!address.equals("null")) {
                    orrder.setVisibility(View.VISIBLE);
                    noorder.setVisibility(View.GONE);
                    nameTV.setText(name);
                    addressTV.setText(address);
                    totalTV.setText(total.substring(1));
                    offTV.setText(mypercent.substring(1));
                    deliveryTV.setText(delevery);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        DatabaseReference me = ref.child("Customerorder/" + userphoneno);
        me.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                c = 1;
                myFoodname.clear();
                myQuantity.clear();
                myOrderPrice.clear();
                no.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    if (!dsp.getKey().equals("information")) {
                        String foodname = dsp.child("foodname").getValue().toString().substring(1);
                        String t1 = dsp.child("qty").getValue().toString();
                        Integer qty = Integer.valueOf(t1.substring(1, t1.length()));
                        String t2 = dsp.child("price").getValue().toString();
                        Double pp = Double.valueOf(t2.substring(1, t2.length()));
                        no.add(c);
                        myFoodname.add(foodname);
                        myQuantity.add(qty);
                        myOrderPrice.add(pp);
                        c++;
                    }

                }
                Toast.makeText(getContext(), "here", Toast.LENGTH_SHORT).show();
                MyOrderRecyclerAdopter myAdapter = new MyOrderRecyclerAdopter(getContext(), no, myFoodname, myOrderPrice, myQuantity);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                Rv.setLayoutManager(llm);
                Rv.setAdapter(myAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;
    }


}
