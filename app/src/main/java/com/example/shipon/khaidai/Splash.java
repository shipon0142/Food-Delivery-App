package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

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
import static com.example.shipon.khaidai.FirstPage_Class.notifi;
import static com.example.shipon.khaidai.FirstPage_Class.t;
import static com.example.shipon.khaidai.MyNotificationService.check;


public class Splash extends Activity {
    LinearLayout dot;
    private ImageView khaiDaiLogo;
    private TextView tv;
    boolean connected;
    ImageView back;
    LinearLayout falseinternet, calll;
    Button tryagain, callnow;
    Thread timer;
    Intent iuu;
    public int count = 0;
    RotateAnimation anim;
    ProgressBar PB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle("KhaiDaai");
        dot = findViewById(R.id.dotlay);
        khaiDaiLogo = findViewById(R.id.khaiDaiLogo);
        tryagain = findViewById(R.id.tryAgain);
        callnow = findViewById(R.id.callnowbtn);
        calll = findViewById(R.id.loutcallnow);
        falseinternet = findViewById(R.id.internetFalse);
        PB = findViewById(R.id.pb);
        setTimer();
        animation();

        if (checkInternet() == false) {
            // falseinternet.setVisibility(View.GONE);
            // khaiDaiLogo.setVisibility(View.VISIBLE);
            // timer.start();
            PB.setVisibility(View.GONE);
            calll.setVisibility(View.VISIBLE);
            falseinternet.setVisibility(View.VISIBLE);
            khaiDaiLogo.setVisibility(View.GONE);

        } else {

            Load obj = new Load();
            obj.BackgroundBreakfast();


            // setTitle("");
            PB.setVisibility(View.VISIBLE);
            calll.setVisibility(View.GONE);
            falseinternet.setVisibility(View.GONE);
            khaiDaiLogo.setVisibility(View.VISIBLE);
            timer.start();
        }
        callnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri number = Uri.parse("tel:" + "+8801842891915");
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);

            }
        });
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInternet() == false) {
                    PB.setVisibility(View.GONE);
                    calll.setVisibility(View.VISIBLE);
                    falseinternet.setVisibility(View.VISIBLE);
                    khaiDaiLogo.setVisibility(View.GONE);

                } else {
                    try {
                        Load obj = new Load();
                        obj.BackgroundBreakfast();
                    } catch (Exception E) {

                    }
                    setTitle("");
                    PB.setVisibility(View.VISIBLE);
                    calll.setVisibility(View.GONE);
                    falseinternet.setVisibility(View.GONE);
                    khaiDaiLogo.setVisibility(View.VISIBLE);

                    timer.start();
                }
            }
        });


    }


    public void animation() {
        anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(700);


    }

    public void setTimer() {
        Bundle extras = new Bundle();
        Animation animation = AnimationUtils.loadAnimation(Splash.this, R.anim.myanim);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setDuration(700);


        // khaiDaiLogo.setAnimation(mAnimation);
        //  khaiDaiLogo.startAnimation(animation);

        timer = new Thread() {
            public void run() {
                try {
                    sleep(6000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {


                    //  if(check!=null){
                    //   iuu.putExtra("check","true");
                    //
//                    Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_SHORT).show();
                    khaiDaiLogo.setAnimation(null);

                    iuu = new Intent(Splash.this, NavigationActivity.class);
                    // iuu.putExtra("KK","1");
                    startActivity(iuu);
                    finish();

                }


            }
        };
    }

    public boolean checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else
            connected = false;
        return connected;
    }

//    public void BackgroundBreakfast(){
//        DatabaseReference ref;
//        ref = FirebaseDatabase.getInstance().getReference();
//
////         DatabaseReference menuu=ref.child("menuname");
////        menuu.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(DataSnapshot dataSnapshot) {
////            menu1= String.valueOf(dataSnapshot.child("menu1").getValue());
////            menu2= String.valueOf(dataSnapshot.child("menu2").getValue());
////            menu3= String.valueOf(dataSnapshot.child("menu3").getValue());
////            menu4= String.valueOf(dataSnapshot.child("menu4").getValue());
////            }
////            @Override
////            public void onCancelled(DatabaseError databaseError) {
////            }
////        });
//
//        DatabaseReference mCategoryRef = ref.child("Breakfast");
//        mCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                bkey.clear();
//                bratting.clear();
//                bfoodName.clear();
//                bprice.clear();
//                bdetails.clear();
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    bkey.add(Integer.valueOf(dsp.getKey()));
//                    bratting.add((String) dsp.child("ratting").getValue());
//                    bfoodName.add((String) dsp.child("foodname").getValue());
//                    bprice.add((String) dsp.child("price").getValue());
//                    bdetails.add((String) dsp.child("details").getValue());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference dCategoryRef = ref.child("Dinner");
//        dCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                dkey.clear();
//                dratting.clear();
//                dfoodName.clear();
//                dprice.clear();
//                ddetails.clear();
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    dkey.add(Integer.valueOf(dsp.getKey()));
//                    dratting.add((String) dsp.child("ratting").getValue());
//                    dfoodName.add((String) dsp.child("foodname").getValue());
//                    dprice.add((String) dsp.child("price").getValue());
//                    ddetails.add((String) dsp.child("details").getValue());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference eCategoryRef = ref.child("menu3");
//        eCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ekey.clear();
//                eratting.clear();
//                efoodName.clear();
//                eprice.clear();
//                edetails.clear();
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    ekey.add(Integer.valueOf(dsp.getKey()));
//                    eratting.add((String) dsp.child("ratting").getValue());
//                    efoodName.add((String) dsp.child("foodname").getValue());
//                    eprice.add((String) dsp.child("price").getValue());
//                    edetails.add((String) dsp.child("details").getValue());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference fCategoryRef = ref.child("menu4");
//        fCategoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                fkey.clear();
//                fratting.clear();
//                ffoodName.clear();
//                fprice.clear();
//                fdetails.clear();
//                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
//                    fkey.add(Integer.valueOf(dsp.getKey()));
//                    fratting.add((String) dsp.child("ratting").getValue());
//                    ffoodName.add((String) dsp.child("foodname").getValue());
//                    fprice.add((String) dsp.child("price").getValue());
//                    fdetails.add((String) dsp.child("details").getValue());
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//
//
//
//
//    }

}
