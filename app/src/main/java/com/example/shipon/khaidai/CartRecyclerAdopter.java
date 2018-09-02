package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.shipon.khaidai.BreakFast_Activity.percentT;
import static com.example.shipon.khaidai.BreakFast_Activity.takaT;
import static com.example.shipon.khaidai.BreakFast_Activity.textCartItemCount;
import static com.example.shipon.khaidai.BreakFast_Activity.viewcart;
import static com.example.shipon.khaidai.CartActivity.empty;
import static com.example.shipon.khaidai.CartActivity.menuItem;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.tot;


/**
 * Created by Shipon on 3/27/2018.
 */

public class CartRecyclerAdopter extends RecyclerView.Adapter<CartRecyclerAdopter.ViewHolder> {
    ArrayList<String> foodName;
    ArrayList<String> price;
    ArrayList<String> qty;
    ArrayList<String> code;
    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    CartRecyclerAdopter(Context context, ArrayList<String> foodName, ArrayList<String> price, ArrayList<String> qty, ArrayList<String> code) {
        this.mInflater = LayoutInflater.from(context);
        this.code = code;
        this.foodName = foodName;
        this.price = price;
        this.qty = qty;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.cart_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // String animal = mData.get(position);
        holder.foodname.setText(foodName.get(position).substring(1, foodName.get(position).length()));
        holder.price.setText("Total cost: " + price.get(position).substring(1, price.get(position).length()));
        holder.quantity.setText("Quantity: " + qty.get(position).substring(1, qty.get(position).length()));

        StorageReference storageRef = firebaseStorage.child("foodimage").child(code.get(position).substring(1, code.get(position).length()) + ".png");
        Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString());
                Picasso.with(mContext).load(uri).into(holder.photo);
            }
        });

        //   Picasso.with(holder.photo.getContext()).load().into(holder.photo);
        // Glide.with(mContext)
        //     .using(new FirebaseImageLoader())
        //    .load(firebaseStorage)
        //  .into(holder.photo);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return foodName.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView foodname;
        TextView price;
        ImageView photo;
        TextView quantity;
        TextView delete;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            foodname = (TextView) itemView.findViewById(R.id.refoodname);
            price = (TextView) itemView.findViewById(R.id.recost);
            quantity = (TextView) itemView.findViewById(R.id.reqty);
            delete = (TextView) itemView.findViewById(R.id.redelete);
            photo = (ImageView) itemView.findViewById(R.id.breakfastImg);
            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            else if (view.equals(delete)) {
                removeat(getAdapterPosition());
                //total_Cost-=Double.valueOf(kcost.get(getAdapterPosition()).substring(1,kcost.get(getAdapterPosition()).length()));
                total_Cost = 0;
                afterPromototal_Cost = 0;
                for (int i = 0; i < kcost.size(); i++) {
                    String temp = kcost.get(i).substring(1, kcost.get(i).length());
                    Double value = Double.valueOf(temp);
                    total_Cost += value;
                }
                Double tcot = null;
                Double pe = null;
                for (int i = 0; i < tot.size(); i++) {
                    if (tot.get(i) <= total_Cost) {
                        tcot = (total_Cost * per.get(i)) / 100;
                        pe = per.get(i);
                    }
                }
                if (pe != null) {
                    Double vel = total_Cost - tcot;
                    afterPromototal_Cost = vel;
                    percent = pe;
                    //  percentT.setText(pe+"% off");
                    //  takaT.setText("Tk "+vel);
                } else {
                    //  percentT.setText("");
                    //takaT.setText("Tk "+total_Cost);
                    afterPromototal_Cost = total_Cost;
                }
                if (kcode.size() > 0) {
                    takaT.setVisibility(View.VISIBLE);
                    percentT.setVisibility(View.VISIBLE);
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


                if (kcode.size() == 0) {
                    takaT.setVisibility(View.INVISIBLE);
                    percentT.setVisibility(View.INVISIBLE);
                }
                viewcart.setText("View your cart(" + kcode.size() + ")");


            }
        }


    }

    private void removeat(int adapterPosition) {
        kcode.remove(adapterPosition);
        kfood.remove(adapterPosition);
        kqty.remove(adapterPosition);
        kcost.remove(adapterPosition);
        textCartItemCount.setText(String.valueOf(kcode.size()));
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition, getItemCount());


    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return foodName.get(id);
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
