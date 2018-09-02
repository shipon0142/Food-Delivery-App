package com.example.shipon.khaidai;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.view.Display;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.example.shipon.khaidai.MyNotificationService.check;

/**
 * Created by Shipon on 3/4/2018.
 */

public class FirstPage_Class extends Fragment {
    public ViewPager sliderViewPager;
    private ImageButton lunch, dinner, menu33, menu44;
    ViewPager viewPager;
    LinearLayout sliderDots;
    public int dotCounts;
    public ImageView[] dots;
    public static Timer t = new Timer();
    public static int flag = 0;
    public static ArrayList<String> notifidate = new ArrayList<String>();
    public static ArrayList<String> notifi = new ArrayList<String>();
    public static ArrayList<String> foodName = new ArrayList<String>();
    public static ArrayList<String> price = new ArrayList<String>();
    public static ArrayList<String> details = new ArrayList<String>();
    public static ArrayList<String> ratting = new ArrayList<String>();
    public static ArrayList<Integer> key = new ArrayList<Integer>();

    public static ArrayList<String> kfood = new ArrayList<String>();
    public static ArrayList<String> kqty = new ArrayList<String>();
    public static ArrayList<String> kcost = new ArrayList<String>();
    public static ArrayList<String> kcode = new ArrayList<String>();

    public static ArrayList<String> bfoodName = new ArrayList<String>();
    public static ArrayList<String> bprice = new ArrayList<String>();
    public static ArrayList<String> bdetails = new ArrayList<String>();
    public static ArrayList<String> bratting = new ArrayList<String>();
    public static ArrayList<Integer> bkey = new ArrayList<Integer>();

    public static ArrayList<String> dfoodName = new ArrayList<String>();
    public static ArrayList<String> dprice = new ArrayList<String>();
    public static ArrayList<String> ddetails = new ArrayList<String>();
    public static ArrayList<String> dratting = new ArrayList<String>();
    public static ArrayList<Integer> dkey = new ArrayList<Integer>();

    public static ArrayList<String> efoodName = new ArrayList<String>();
    public static ArrayList<String> eprice = new ArrayList<String>();
    public static ArrayList<String> edetails = new ArrayList<String>();
    public static ArrayList<String> eratting = new ArrayList<String>();
    public static ArrayList<Integer> ekey = new ArrayList<Integer>();

    public static ArrayList<String> ffoodName = new ArrayList<String>();
    public static ArrayList<String> fprice = new ArrayList<String>();
    public static ArrayList<String> fdetails = new ArrayList<String>();
    public static ArrayList<String> fratting = new ArrayList<String>();
    public static ArrayList<Integer> fkey = new ArrayList<Integer>();
    private boolean ck = false;
    public IntentFilter filter;
    TextView m1, m2, m3, m4;
    private boolean connect = false;
    public static String menu1, menu2, menu3, menu4;


    public static TextView message;
    public static double total_Cost = 0, percent = 0;
    public static double afterPromototal_Cost = 0;
    public static boolean lg = false;
    private NetworkChangeReceiver receiver;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    public static ArrayList<Integer> slideImage = new ArrayList<>();
    public static PathGenerator pathGenerator;
    public static ArrayList<String> photoURLs;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Food Menu");


        //  getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.first_page, container, false);
        photoURLs = new ArrayList<>();

        pathGenerator = new PathGenerator();

        new UrlProducer(pathGenerator);
        sliderDots = view.findViewById(R.id.dotlayout);
        message = view.findViewById(R.id.text);
        lunch = view.findViewById(R.id.lunch);
        dinner = view.findViewById(R.id.dinner);
        menu33 = view.findViewById(R.id.menu33);
        menu44 = view.findViewById(R.id.menu44);
        m1 = view.findViewById(R.id.idMenu1);
        m2 = view.findViewById(R.id.idMenu2);
        m3 = view.findViewById(R.id.idMenu3);
        m4 = view.findViewById(R.id.idmenu4);

        slideImage.add(R.drawable.imgt2);
        slideImage.add(R.drawable.image_2);
        slideImage.add(R.drawable.imgt1);
        slideImage.add(R.drawable.special);
        if (menu1 == null || menu2 == null || menu3 == null || menu4 == null) {
            Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        } else {
            m1.setText(menu1);
            m2.setText(menu2);
            m3.setText(menu3);
            m4.setText(menu4);
        }
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodName = bfoodName;
                price = bprice;
                details = bdetails;
                ratting = bratting;
                key = bkey;
                if (foodName.size() == 0) {
                    Load load = new Load();
                    load.BackgroundBreakfast();
                }
                if (m1.getText().toString().contains("Upcom")) {
                    Toast.makeText(getContext(), "This menu is coming soon", Toast.LENGTH_SHORT).show();
                } else {
//                   getContext().unregisterReceiver(receiver);
                    Intent i = new Intent(getActivity(), BreakFast_Activity.class);
                    flag = 1;
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }

            }
        });
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodName = dfoodName;
                price = dprice;
                details = ddetails;
                ratting = dratting;
                key = dkey;
                if (foodName.size() == 0) {
                    Load load = new Load();
                    load.BackgroundBreakfast();
                }
                if (m2.getText().toString().contains("Upcom")) {
                    Toast.makeText(getContext(), "This menu is coming soon", Toast.LENGTH_SHORT).show();
                } else {
                    //      getContext().unregisterReceiver(receiver);
                    Intent i = new Intent(getActivity(), BreakFast_Activity.class);
                    flag = 2;
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }

            }
        });
        menu33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodName = efoodName;
                price = eprice;
                details = edetails;
                ratting = eratting;
                key = ekey;
                if (foodName.size() == 0) {
                    Load load = new Load();
                    load.BackgroundBreakfast();
                }
                if (m3.getText().toString().contains("Upcom")) {
                    Toast.makeText(getContext(), "This menu is coming soon", Toast.LENGTH_SHORT).show();
                } else {
                    //   getContext().unregisterReceiver(receiver);
                    Intent i = new Intent(getActivity(), BreakFast_Activity.class);
                    flag = 3;
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            }
        });
        menu44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                foodName = ffoodName;
                price = fprice;
                details = fdetails;
                ratting = fratting;
                key = fkey;
                if (foodName.size() == 0) {
                    Load load = new Load();
                    load.BackgroundBreakfast();
                }
                if (m4.getText().toString().contains("Upcom")) {
                    Toast.makeText(getContext(), "This menu is coming soon", Toast.LENGTH_SHORT).show();
                } else {
                    //   getContext().unregisterReceiver(receiver);
                    Intent i = new Intent(getActivity(), BreakFast_Activity.class);
                    flag = 4;
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
                }
            }
        });
        sliderViewPager = view.findViewById(R.id.idsliderViewPager);

        ViewPagerAdapter_Class viewPagerAdapter_class = new ViewPagerAdapter_Class(getContext());
        sliderViewPager.setAdapter(viewPagerAdapter_class);
        dotCounts = sliderViewPager.getChildCount();
        try {
            t.schedule(new Mytimer(), 500, 3000);
        } catch (Exception E) {

        }

        //t.scheduleAtFixedRate(new Mytimer(), 2000, 3000);

//if(getActivity()!=null) {

//}

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();


        //  getContext().registerReceiver(receiver, filter);

        return view;
    }

    @Override
    public void onPause() {
        getContext().unregisterReceiver(receiver);
        //getContext().registerReceiver(receiver, filter);
        // Toast.makeText(getContext(),"Pouse",Toast.LENGTH_SHORT).show();
        super.onPause();

    }

    @Override
    public void onStart() {

        //Toast.makeText(getContext(),"Start",Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    class PathGenerator {
        //start photo number
        int photoNumber = 0;
        //boolean that indicates if the path has been used already or not.
        boolean pathIsTaken = false;
        //our storage path.
        String path;

        /**
         * This method will generate a new path.
         * while path is taken , wait because we didn't use it yet.
         *
         * @param photoNumber
         */

        public synchronized String generatePath(int photoNumber) {

            while (pathIsTaken) {
                try {
                    wait();
                } catch (Exception e) {
                }
            }
            this.photoNumber = photoNumber;
            path = "test/" + String.valueOf(photoNumber) + ".jpg";
            pathIsTaken = true;
            Log.e("eyaldebug", "path is :  " + path);
            return path;
        }

        /**
         * Simple set method.
         *
         * @param value
         */

        public synchronized void setPathisSet(boolean value) {
            this.pathIsTaken = value;
        }

        /**
         * Unfreeze the thread, we call this method after onSucsess.
         */

        public synchronized void unfreeze() {
            notifyAll();
        }

    }


    /**
     * Our URLProducer calls will take the paths,and will
     * send HTTP request to the storage that we'll get a
     * download URL returned.
     * later we'll be using Glide\Picasso to display those images.
     */

    class UrlProducer implements Runnable {

        PathGenerator mPathGenerator;

        //initialize a String type ArrayList which will contain our URLS.
        public ArrayList<String> photoURLs = new ArrayList<>();

        //constructor that will be called in the activity
        public UrlProducer(PathGenerator mPathGenerator) {
            this.mPathGenerator = mPathGenerator;

            Thread b = new Thread(this, "UrlProducer");
            b.start();
        }

        /**
         * Here we a simple download URL method using FirebaseStorage.
         * for the documentation for FirebaseStoarge download go to :
         * <p>
         * https://firebase.google.com/docs/storage/android/download-files
         * <p>
         * IF the task was successful we UNfreeze the threads so it will
         * keep sending us new URLS.
         * IF the onFailure was called the stroage is must likely empty and
         * we should stop trying to get new photos.
         */

        @Override
        public void run() {


            int photoNumber = 0;

            while (true) {


                photoNumber++;

                try {
                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference ref = storage.getReference().child("foodimage");


                    ref.child(pathGenerator.generatePath(photoNumber)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            Log.e(TAG, "Success! " + uri);

                            //add the URL into the ArrayList
                            photoURLs.add(String.valueOf(uri));
                            Toast.makeText(getContext(), photoURLs.size(), Toast.LENGTH_SHORT).show();

                            //tell the generate method that the path has been used.
                            pathGenerator.setPathisSet(false);

                            //Unfreeze the thread so it will continue generate new paths.
                            pathGenerator.unfreeze();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //When onFailure is called shutdown,and output the given ArrayList.

                            Log.e(TAG, "onFailure was called , storage is empty!");
                            Log.e(TAG, "----------------------------------------");
                            for (String singleUrl : photoURLs) {
                                Log.e(TAG, "" + singleUrl);
                            }
                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onStop() {
        //   Toast.makeText(getContext(),"Stop",Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    public void onResume() {
        getContext().registerReceiver(receiver, filter);
        //  super.onResume();

        Load load = new Load();
        load.BackgroundBreakfast();
        set();

        super.onResume();


        // Toast.makeText(getContext(),"Resume",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        getContext().unregisterReceiver(receiver);
    }

    private void set() {
        m1.setText(menu1);
        m2.setText(menu2);
        m3.setText(menu3);
        m4.setText(menu4);
        // getContext().unregisterReceiver(receiver);
    }

    public boolean checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connect = true;
        } else
            connect = false;
        return connect;
    }


    public class Mytimer extends TimerTask {

        @Override
        public void run() {
            if (getActivity() == null)
                return;
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    //  Log.d("UI thread", "I am the UI thread");
                    if (sliderViewPager.getCurrentItem() == 3) {
                        sliderViewPager.setCurrentItem(0);
                        //   dotLayoutCreate(0);
                    } else {
                        sliderViewPager.setCurrentItem(sliderViewPager.getCurrentItem() + 1);
                        //dotLayoutCreate(sliderViewPager.getCurrentItem()+1);
                    }

                    dotLayoutCreate(sliderViewPager.getCurrentItem());


                }
            });
        }


    }

    private void dotLayoutCreate(int position) {
        if (sliderDots != null) {
            sliderDots.removeAllViews();
            dots = new ImageView[4];
            for (int i = 0; i < 4; i++) {
                dots[i] = new ImageView(getActivity());
                if (i == position) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));
                } else {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.not_active_dot));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4, 0, 4, 0);
                sliderDots.addView(dots[i], params);
            }
        }
    }

    public class NetworkChangeReceiver extends BroadcastReceiver {
        private boolean isConnected = false;

        @Override
        public void onReceive(Context context, Intent intent) {
            isNetworkAvailable(context);
        }

        public boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivity = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (int i = 0; i < info.length; i++) {
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            if (!isConnected) {
                                isConnected = true;
                                Load load = new Load();
                                load.BackgroundBreakfast();
                                //  Toast.makeText(getContext(),"change",Toast.LENGTH_SHORT).show();
                            }
                            //  m1.setText(menu1);
                            // m2.setText(menu2);
                            // m3.setText(menu3);
                            // m4.setText(menu4);
                            // if(menu1==null || menu2==null || menu3==null || menu4==null) {
                            //  getContext().unregisterReceiver(receiver);
                            //    onPause();
                            //    onStop();
                            //onStart();
                            // onResume();
                            getContext().registerReceiver(receiver, filter);
                            //  }
                            ck = true;
                            //onPause();
                            //   onStop();
                            // onStart();
                            // onResume();
                            return true;
                        }
                        // m1.setText(menu1);
                        // m2.setText(menu2);
                        // m3.setText(menu3);
                        // m4.setText(menu4);
                    }
                    //  m1.setText(menu1);
                    // m2.setText(menu2);
                    // m3.setText(menu3);
                    // m4.setText(menu4);
                }
            }
            //  Log.v(LOG_TAG, "You are not connected to Internet!");
            //  networkStatus.setText("You are not connected to Internet!");
            ck = false;
            isConnected = false;
            onResume();
            return false;
        }
    }


}
