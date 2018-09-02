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
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.shipon.khaidai.FirstPage_Class.notifi;
import static com.example.shipon.khaidai.FirstPage_Class.notifidate;

/**
 * Created by Shipon on 3/4/2018.
 */

public class Notificationfragment extends Fragment {
    RadioButton phone1, phone2;
    Button call;
    RecyclerView notificationView;
    public static ArrayList<String> notifiy = new ArrayList<String>();
    String str = "The Android notifications menu sometimes fills up so fast, its easy to clear it without properly checking it. If youve done this, and think you might have accidentally swiped something important into non-existence, you can still get it back. Stock Android users can recover lost notifications on their Android device by finding the Android notification history. For devices with a different UI (such as Samsungs TouchWiz), this approach might not be available, but there is an app-based solution.";

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Notification");

        // setDisplayHomeAsUpEnabled(true);
        //  getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.notification, container, false);
        notificationView = view.findViewById(R.id.nrecycler);
        DatabaseReference re;
        re = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mCategoryRe = re.child("Notification");

        mCategoryRe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                notifi.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    notifidate.add(dsp.getKey().toString());
                    notifi.add((String) dsp.child("n1").getValue());
                }
                NotificationRecyclerAdopter myAdapter = new NotificationRecyclerAdopter(getContext(), notifi, notifidate);
                LinearLayoutManager llm = new LinearLayoutManager(getContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                notificationView.setLayoutManager(llm);
                notificationView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


        return view;
    }


}
