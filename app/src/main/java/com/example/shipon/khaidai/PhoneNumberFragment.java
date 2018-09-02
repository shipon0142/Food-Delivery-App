package com.example.shipon.khaidai;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;

/**
 * Created by Shipon on 3/4/2018.
 */

public class PhoneNumberFragment extends Fragment {

    PhoneAuthCredential credential;
    private Button LoginButton;
    private EditText PhoneNumber;
    private Button mVerifyButton, cancel;
    private Button mResendButton;
    public static String userPhone;
    private Button mSignOutButton;
    private String phoneVerificationId = null;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationCallBacks;
    private FirebaseAuth mAuth;
    public static String smscode = null;
    private String flag = "0";
    private ProgressDialog progressD;
    public static PhoneAuthCredential crd;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Add phone number");


        // setDisplayHomeAsUpEnabled(true);
        //  getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.phone_number, container, false);

        //  LoginButton=view.findViewById(R.id.loginButton);

        PhoneNumber = view.findViewById(R.id.phn);
        mVerifyButton = view.findViewById(R.id.VerifyButton);
        cancel = view.findViewById(R.id.idCancel);
        progressD = new ProgressDialog(getContext());
        progressD.setMessage("Sending...");
        progressD.setCancelable(false);
        mAuth = FirebaseAuth.getInstance();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                credential = PhoneAuthProvider.getCredential(phoneVerificationId, PhoneNumber.getText().toString());
                // [END verify_with_code]
                // signInWithPhoneAuthCredential(credential);

                /////
                if (checkPhonenumber(PhoneNumber.getText().toString())) {

                    progressD.show();
                    startPhoneNumberVerification(PhoneNumber.getText().toString());
                } else {
                    PhoneNumber.setError("Phone number not valid.");
                }


            }
        });


        return view;
    }

    public void next() {
        if (PhoneNumber.getText().toString().equals("+8801942040142")) {
            userPhone = "+8801942040142";
            Fragment fragment = null;
            fragment = new VerifyFragment();
            Bundle data = new Bundle();
            //String check=getArguments().getString("check");
            //  String cost=getArguments().getString("price");
            //  data.putString("check", check.toString());
            // data.putString("phoneVerificationId", phoneVerificationId);
            //data.putString("phoneNumber", PhoneNumber.getText().toString());
            fragment.setArguments(data);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.menu, fragment);
            ft.commit();
            // startPhoneNumberVerification(PhoneNumber.getText().toString());
            //  progressDialog = new ProgressDialog(getContext());
            //  progressDialog.setMessage("Sending...");
            //  progressDialog.setCancelable(false);
            // progressDialog.show();
        } else {
            PhoneNumber.setError("Phone number not valid");
        }
        return;
    }

    private boolean checkPhonenumber(String s) {

        String s2 = "+88";
        String sPhoneNumbe = "+8801942040142";
        //String sPhoneNumber = "605-888999A";

        if (s.length() == 11) {
            s2 = s2.concat(s);
            if (s2.contains("+88019")) return true;
            else if (s2.contains("+88015")) return true;
            else if (s2.contains("+88017")) return true;
            else if (s2.contains("+88018")) return true;
            else if (s2.contains("+88016")) return true;
            else return false;
        } else if (s.length() == 14) {
            s2 = s;
            if (s2.contains("+88019")) return true;
            else if (s2.contains("+88015")) return true;
            else if (s2.contains("+88017")) return true;
            else if (s2.contains("+88018")) return true;
            else if (s2.contains("+88016")) return true;
            else return false;
        } else {
            return false;

        }
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        setUpVerification();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                getActivity(),
                verificationCallBacks);

        //mVerificationInProgress = true;
    }

    private void setUpVerification() {
        // Toast.makeText(getContext(),"asce",Toast.LENGTH_SHORT).show();
        verificationCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {


            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(getContext(), "Verification code not sent.", Toast.LENGTH_SHORT).show();
                progressD.cancel();
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                Toast.makeText(getContext(), "Verification code sent.", Toast.LENGTH_SHORT).show();
                progressD.cancel();
                phoneVerificationId = s;
                mResendToken = forceResendingToken;
                flag = "1";
                Fragment fragment = null;
                fragment = new VerifyFragment();
                Bundle data = new Bundle();

                data.putString("phoneVerificationId", phoneVerificationId);
                data.putString("phoneNumber", PhoneNumber.getText().toString());
                fragment.setArguments(data);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.menu, fragment);
                ft.commit();
                //  Log.d(TAG, "onCodeSent:" + phoneVerificationId);
                //  Fragment fragment=null;
                //  fragment= new VerifyFragment();
                //  Bundle data=new Bundle();
                // data.putString("phoneVerificationId", phoneVerificationId);
                // data.putString("phoneNumber", PhoneNumber.getText().toString());
                // fragment.setArguments(data);
                // FragmentTransaction ft = getFragmentManager().beginTransaction();

                // ft.replace(R.id.menu, fragment);
                // ft.commit();


                //   LoginButton.setText("Verify & Login");
                //  PhoneNumber.setText("");
                // [START verify_with_code]

                // [END verify_with_code]
                //   signInWithPhoneAuthCredential(credential);
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
                            Toast.makeText(getContext(), "Verification Success", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = task.getResult().getUser();

                            // [START_EXCLUDE]
                            //  updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //     mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                                Toast.makeText(getContext(), "Verification failed", Toast.LENGTH_SHORT).show();
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
