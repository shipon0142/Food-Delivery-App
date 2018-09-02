package com.example.shipon.khaidai;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.shipon.khaidai.FirstPage_Class.bdetails;
import static com.example.shipon.khaidai.FirstPage_Class.bfoodName;
import static com.example.shipon.khaidai.FirstPage_Class.bkey;
import static com.example.shipon.khaidai.FirstPage_Class.bprice;
import static com.example.shipon.khaidai.FirstPage_Class.bratting;
import static com.example.shipon.khaidai.FirstPage_Class.message;

public class MyNotificationService extends FirebaseMessagingService {
    public static boolean check = false;

    public MyNotificationService() {
    }

    @SuppressLint("WrongThread")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //  super.onMessageReceived(remoteMessage);
        check = true;
        DatabaseReference ref;
        ref = FirebaseDatabase.getInstance().getReference().child("Notification");
        DatabaseReference mCategoryRef = ref.child(currentDate());
        mCategoryRef.child("n1").setValue(remoteMessage.getNotification().getBody());
        sendNotification(remoteMessage.getNotification().getBody());

    }

    private String currentDate() {
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        Calendar c = Calendar.getInstance();
        Date formattedDate = c.getTime();
        String str = dateFormat.format(formattedDate).toString();
        String str2 = null;
        if (str.contains("/")) {
            str2 = str.replaceAll("/", "-");
        }
        return str2;
    }

    public void sendNotification(String body) {
        Intent iii = new Intent(this, Splash.class);

        //   iii.putExtra("check", "true");
        iii.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, iii, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultsound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setSmallIcon(R.drawable.home);
        notificationBuilder.setContentTitle("KhaiDai");
        notificationBuilder.setContentText(body);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound(defaultsound);
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


    }

}
