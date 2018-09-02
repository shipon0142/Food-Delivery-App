package com.example.shipon.khaidai;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;

/**
 * Created by Shipon on 3/4/2018.
 */

public class address extends Fragment {


    private Button LoginButton;
    private TextView PhoneNumber;
    private Button mVerifyButton, submit;
    private Button mResendButton;
    private Button mSignOutButton;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private FirebaseAuth mAuth;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Confirm Order");
        //  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.address, container, false);
        submit = view.findViewById(R.id.submitConfirm);
        String phonenum = getArguments().getString("phone_number");

        final DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mCategoryRef = ref.child("Customerorder/" + phonenum);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Are you want to confirm this order?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //   String foodcode=getArguments().getString("foodcode");
                                //  String qt=getArguments().getString("qty");
                                //  String cos=getArguments().getString("cost");
                                // FIRE ZE MISSILES!
                                //   mCategoryRef.child("foodcode").setValue(foodcode.toString());
                                //   mCategoryRef.child("price").setValue(cos.toString());
                                //   mCategoryRef.child("qty").setValue(qt.toString());
                                for (int i = 0; i < kcode.size(); i++) {
                                    DatabaseReference tref = mCategoryRef.child("order-" + (i + 1));
                                    tref.child("foodcode").setValue(kcode.get(i).toString());
                                    tref.child("price").setValue(kcost.get(i).toString());
                                    tref.child("qty").setValue(kqty.get(i).toString());
                                }


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

        TextView phoneNumber = view.findViewById(R.id.phoneNumber);
        phoneNumber.setText(phonenum);
        return view;
    }


}
