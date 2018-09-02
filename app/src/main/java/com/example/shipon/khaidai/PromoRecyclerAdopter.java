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


/**
 * Created by Shipon on 3/27/2018.
 */

public class PromoRecyclerAdopter extends RecyclerView.Adapter<PromoRecyclerAdopter.ViewHolder> {
    ArrayList<String> promoCode;
    ArrayList<String> offer;
    ArrayList<String> deadline;
    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

    //private StorageReference firebaseStorage=FirebaseStorage.getInstance().getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/khaidai-65b30.appspot.com/o/foodimage%2F110.png?alt=media&token=13e72acb-044a-484c-b021-f9c2cb1c7867");
    // data is passed into the constructor
    PromoRecyclerAdopter(Context context, ArrayList<String> promoCode, ArrayList<String> offer, ArrayList<String> deadline) {
        this.mInflater = LayoutInflater.from(context);
        this.promoCode = promoCode;
        this.offer = offer;
        this.deadline = deadline;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.promo_view, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // String animal = mData.get(position);
        holder.pcode.setText(promoCode.get(position).toString());
        holder.poffer.setText(offer.get(position).toString());
        holder.pdeadline.setText(deadline.get(position).toString());


        //   Picasso.with(holder.photo.getContext()).load().into(holder.photo);
        // Glide.with(mContext)
        //     .using(new FirebaseImageLoader())
        //    .load(firebaseStorage)
        //  .into(holder.photo);


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return promoCode.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView pcode;
        TextView poffer;
        TextView pdeadline;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            pcode = (TextView) itemView.findViewById(R.id.idPromoCode);
            poffer = (TextView) itemView.findViewById(R.id.idOffer);
            pdeadline = (TextView) itemView.findViewById(R.id.idDeadline);


        }


    }


    // convenience method for getting data at click position
    String getItem(int id) {
        return promoCode.get(id);
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
