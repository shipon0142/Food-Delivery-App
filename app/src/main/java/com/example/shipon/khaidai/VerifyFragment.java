package com.example.shipon.khaidai;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.example.shipon.khaidai.BreakFast_Activity.takaT;
import static com.example.shipon.khaidai.CartActivity.empty;
import static com.example.shipon.khaidai.CartActivity.menuItem;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.lg;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.NavigationActivity.User;
import static com.example.shipon.khaidai.NavigationActivity.loginv;
import static com.example.shipon.khaidai.PhoneNumberFragment.smscode;

/**
 * Created by Shipon on 3/4/2018.
 */

public class VerifyFragment extends Fragment {


    private Button verify, cancel;
    private EditText editphone;
    private Button mVerifyButton;
    private Button mResendButton;
    private Button mSignOutButton;
    private String phoneVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential credential;
    private String phoneNumber;
    private String code;
    private String key;
    FirebaseUser user;
    private ProgressDialog progressDialog;
    public static ArrayList<String> promo = new ArrayList<String>();
    public static ArrayList<String> offer = new ArrayList<String>();
    public static ArrayList<String> deadline = new ArrayList<String>();
    public static ArrayList<Double> per = new ArrayList<Double>();
    public static ArrayList<Double> tot = new ArrayList<Double>();
    public static String userphoneno;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(getArguments().getString("phoneNumber"));

        // setDisplayHomeAsUpEnabled(true);
        //  getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.verify_number, container, false);
        mAuth = FirebaseAuth.getInstance();
        //code=getArguments().getString("smscode");
        //      phoneVerificationId=getArguments().getString("phoneVerificationId");
        //    phoneNumber=getArguments().getString("phoneNumber");
        // key=getArguments().getString("key");
        verify = view.findViewById(R.id.VerifyCode);
        cancel = view.findViewById(R.id.ccancel);
        editphone = view.findViewById(R.id.EditCode);


        // credential = PhoneAuthProvider.getCredential(editphone.getText().toString(), phoneNumber);
        setUpVerification();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editphone.getText().toString().equals("")) {
                    editphone.setError("Please input verification code");
                    return;
                }


                setUpVerification();
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Verifying...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                phoneVerificationId = getArguments().getString("phoneVerificationId");
                phoneNumber = getArguments().getString("phoneNumber");
                credential = PhoneAuthProvider.getCredential(phoneVerificationId.toString(), editphone.getText().toString());
                verificationCallBacks.onVerificationCompleted(credential);
                signInWithPhoneAuthCredential(credential);

                ////credential = PhoneAuthProvider.getCredential(phoneVerificationId.toString(), editphone.getText().toString());
                //verificationCallBacks.onVerificationCompleted(credential);
                ////  signInWithPhoneAuthCredential(credential);
            }
        });

        return view;
    }

    public void signin() {
        //  if(editphone.getText().toString().equals("1234")){
        Toast.makeText(getContext(), "Sign in succesful.", Toast.LENGTH_SHORT).show();
        loginv.setText("Sign out");
        userphoneno = user.getPhoneNumber().toString();
        User.setText(userphoneno);
        if (lg == true) {
            lg = false;
            getActivity().finish();
        }
        per.clear();
        tot.clear();

        if (kcode.size() == 0) {
            getActivity().finish();
        }
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
                if (afterPromototal_Cost != 0) {
                    Double tCost = afterPromototal_Cost;
                    Double Cost = Double.valueOf(0);

                    Double tcot = null;
                    Double pe = null;
                    for (int i = 0; i < tot.size(); i++) {

                        if (tot.get(i) <= tCost) {
                            tcot = (tCost * per.get(i)) / 100;
                            pe = per.get(i);
                        }

                    }
                    if (pe != null) {
                        //Toast.makeText(getContext(),"percent",Toast.LENGTH_SHORT).show();
                        Double vel = tCost - tcot;
                        afterPromototal_Cost = vel;
                        percent = pe;
                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage("Promo applied. You got " + pe + "% off. New Total : " + afterPromototal_Cost + " BDT")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        Intent iii = new Intent(getActivity(), AdderssActivity.class);
                                        startActivity(iii);
                                        getActivity().finish();
                                    }
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                        //  percentT.setText(pe+"% off");
                        //  takaT.setText("Tk "+vel);
                    } else {
                        //  percentT.setText("");
                        //takaT.setText("Tk "+total_Cost);
                        // afterPromototal_Cost = total_Cost;
                        Intent iii = new Intent(getActivity(), AdderssActivity.class);
                        startActivity(iii);
                        getActivity().finish();
                    }
                    if (afterPromototal_Cost == 0) {
                        menuItem.setTitle("Total: 0 BDT");
                        takaT.setText("Tk " + 0);
                        //  empty.setText("Your cart is empty");
                    } else {


                        menuItem.setTitle("Total: " + afterPromototal_Cost + " BDT");
                        takaT.setText("Tk " + afterPromototal_Cost);
                        //  empty.setText("Total: " + afterPromototal_Cost + "BDT");
                    }
                    if (afterPromototal_Cost != 0) {
                        empty.setVisibility(View.GONE);
                    } else {
                        empty.setVisibility(View.VISIBLE);
                        empty.setText("Your cart is empty");
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // }
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        setUpVerification();
        //verificationCallBacks.onVerificationCompleted(credential);
        //PhoneAuthProvider.getInstance().verifyPhoneNumber(
        //    phoneNumber,
        //  60,
        // TimeUnit.SECONDS,
        // getActivity(),
        // verificationCallBacks);
        //mVerificationInProgress = true;
    }

    private void setUpVerification() {
        // Toast.makeText(getContext(),"asce",Toast.LENGTH_SHORT).show();
        verificationCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                //signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(getContext(), "sent", Toast.LENGTH_SHORT).show();
                phoneVerificationId = s;
                mResendToken = forceResendingToken;

                //   LoginButton.setText("Verify & Login");
                //  PhoneNumber.setText("");
                // [START verify_with_code]

                // [END verify_with_code]
                // signInWithPhoneAuthCredential(credential);
            }
        };


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //   Log.d(TAG, "signInWithCredential:success");

                            user = task.getResult().getUser();
                            userphoneno = user.getPhoneNumber();
                            User.setText(user.getPhoneNumber());
                            progressDialog.cancel();
                            //     String check=getArguments().getString("check");
                            // if(check.equals("1")){
                            //     Intent i= new Intent(getActivity(),AdderssActivity.class);
                            //    startActivity(i);
                            //   }
                            // getActivity().finish();
                            // [START_EXCLUDE]
                            //  updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                            signin();
                        } else {
                            // Sign in failed, display a message and update the UI
                            //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //     mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                                Toast.makeText(getContext(), "Verification code invalid.", Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            //    updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });


    }


}
