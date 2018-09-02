package com.example.shipon.khaidai;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


/**
 * Created by Shipon on 3/27/2018.
 */

public class NotificationRecyclerAdopter extends RecyclerView.Adapter<NotificationRecyclerAdopter.ViewHolder> {
    ArrayList<String> notifi;
    ArrayList<String> notifidate;
    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    NotificationRecyclerAdopter(Context context, ArrayList<String> notifi, ArrayList<String> notifidate) {
        if (context != null) {
            this.mInflater = LayoutInflater.from(context);
            this.notifi = notifi;
            this.notifidate = notifidate;

            this.mContext = context;
        }
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.notification_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // String animal = mData.get(position);
        //String date=notifidate.get(position).replace();
        holder.nnn.setText(notifi.get(position).toString());
        String a = notifidate.get(position).toString().replaceAll("-", ":");
        String rt = month(a);
        holder.date.setText(rt);


        //   Picasso.with(holder.photo.getContext()).load().into(holder.photo);
        // Glide.with(mContext)
        //     .using(new FirebaseImageLoader())
        //    .load(firebaseStorage)
        //  .into(holder.photo);
    }

    public String month(String date) {
        int str1 = date.indexOf(":");
        int str2 = date.lastIndexOf(":");
        String st1 = date.substring(0, str1);
        String st2 = date.substring(str1 + 1, str2);
        String st3 = date.substring(str2 + 1, date.length());
        int ind = Integer.valueOf(st1);
        String DAT[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String dat = "Date:  " + st2 + "," + DAT[ind - 1] + ",20" + st3;
        return dat;

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return notifi.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nnn;
        TextView date;

        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            nnn = (TextView) itemView.findViewById(R.id.nid);
            date = itemView.findViewById(R.id.datee);


        }


    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return notifi.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
