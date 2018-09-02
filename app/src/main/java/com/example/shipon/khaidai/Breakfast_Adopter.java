package com.example.shipon.khaidai;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shipon on 12/6/2017.
 */

public class Breakfast_Adopter extends ArrayAdapter<String> {
    ArrayList<String> foodName;
    ArrayList<String> price;
    ArrayList<String> details;
    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;

    public Breakfast_Adopter(@NonNull Context context, ArrayList<String> foodName, ArrayList<String> price, ArrayList<String> details) {
        super(context, R.layout.recyccler_view);
        this.foodName = foodName;
        this.price = price;
        this.details = details;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return foodName.size();

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        mViewHolder = new ViewHolder();
        //       Toast.makeText(mContext,""+foodName.size(),Toast.LENGTH_SHORT).show();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.recyccler_view, parent, false);

            mViewHolder.foodname = (TextView) convertView.findViewById(R.id.refoodname);
            mViewHolder.price = (TextView) convertView.findViewById(R.id.reprice);

            mViewHolder.details = (TextView) convertView.findViewById(R.id.redetails);
            mViewHolder.photo = convertView.findViewById(R.id.breakfastImg);


            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.foodname.setText(foodName.get(position));
        mViewHolder.price.setText(price.get(position));
        mViewHolder.details.setText(details.get(position));


        return convertView;

    }

    static class ViewHolder {
        ImageView photo;
        TextView foodname;
        TextView price;
        TextView details;
    }
}


