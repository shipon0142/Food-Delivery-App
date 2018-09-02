package com.example.shipon.khaidai;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.shipon.khaidai.BreakFast_Activity.takaT;
import static com.example.shipon.khaidai.BreakFast_Activity.textCartItemCount;
import static com.example.shipon.khaidai.CartActivity.empty;
import static com.example.shipon.khaidai.CartActivity.menuItem;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;
import static com.example.shipon.khaidai.MyorderFragment.c;
import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.tot;


/**
 * Created by Shipon on 3/27/2018.
 */

public class MyOrderRecyclerAdopter extends RecyclerView.Adapter<MyOrderRecyclerAdopter.ViewHolder> {
    ArrayList<String> foodName;
    ArrayList<Double> price;
    ArrayList<Integer> qty;
    ArrayList<Integer> no;

    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    MyOrderRecyclerAdopter(Context context, ArrayList<Integer> no, ArrayList<String> foodName, ArrayList<Double> price, ArrayList<Integer> qty) {
        this.mInflater = LayoutInflater.from(context);
        this.no = no;
        this.foodName = foodName;
        this.price = price;
        this.qty = qty;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.my_order_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // String animal = mData.get(position);
        holder.cot.setText(no.get(position).toString());
        holder.foodname.setText(foodName.get(position));
        holder.price.setText(price.get(position).toString());
        holder.quantity.setText(qty.get(position).toString());


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
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView foodname;
        TextView price;
        TextView cot;
        TextView quantity;

        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            cot = (TextView) itemView.findViewById(R.id.cott);
            foodname = (TextView) itemView.findViewById(R.id.mfoodname);
            price = (TextView) itemView.findViewById(R.id.mcost);
            quantity = (TextView) itemView.findViewById(R.id.mqty);


        }


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
