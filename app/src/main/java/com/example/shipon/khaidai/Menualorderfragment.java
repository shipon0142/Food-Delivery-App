package com.example.shipon.khaidai;


import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

import static com.example.shipon.khaidai.NavigationActivity.User;

/**
 * Created by Shipon on 3/4/2018.
 */

public class Menualorderfragment extends Fragment {
    RadioButton phone1, phone2, phone3, phone4;
    Button call;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle("Phone call order");

        // setDisplayHomeAsUpEnabled(true);
        //  getActivity().getActionBar().setDisplayShowHomeEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.menual_order, container, false);
        phone1 = view.findViewById(R.id.phone1);
        phone2 = view.findViewById(R.id.phone2);
        phone3 = view.findViewById(R.id.phone3);
        phone4 = view.findViewById(R.id.phone4);
        call = view.findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*
                Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
                callIntent.setData(Uri.parse("tel:01942040142"));    //this is the phone number calling

                if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    Toast.makeText(getContext(),"Not permission",Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
                            11);
                    return;
                } else {     //have got permission
                    try {
                        startActivity(callIntent);  //call activity and make phone call
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getContext(),"Not ok",Toast.LENGTH_SHORT).show();
                    }


                }
*/
                if (phone1.isChecked()) {
                    Uri number = Uri.parse("tel:" + phone1.getText().toString());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);

                } else if (phone2.isChecked()) {
                    Uri number = Uri.parse("tel:" + phone2.getText().toString());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);

                } else if (phone3.isChecked()) {
                    Uri number = Uri.parse("tel:" + phone3.getText().toString());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);

                } else if (phone4.isChecked()) {
                    Uri number = Uri.parse("tel:" + phone4.getText().toString());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    startActivity(callIntent);

                } else {
                    Toast.makeText(getContext(), "Please mark any number", Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }


}
