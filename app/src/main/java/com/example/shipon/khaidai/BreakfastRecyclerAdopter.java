package com.example.shipon.khaidai;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static android.provider.Contacts.SettingsColumns.KEY;
import static com.example.shipon.khaidai.BreakFast_Activity.percentT;
import static com.example.shipon.khaidai.BreakFast_Activity.takaT;
import static com.example.shipon.khaidai.BreakFast_Activity.textCartItemCount;
import static com.example.shipon.khaidai.BreakFast_Activity.vieeew;

import static com.example.shipon.khaidai.BreakFast_Activity.viewcart;
import static com.example.shipon.khaidai.FirstPage_Class.afterPromototal_Cost;
import static com.example.shipon.khaidai.FirstPage_Class.foodName;
import static com.example.shipon.khaidai.FirstPage_Class.kcode;
import static com.example.shipon.khaidai.FirstPage_Class.kcost;
import static com.example.shipon.khaidai.FirstPage_Class.key;
import static com.example.shipon.khaidai.FirstPage_Class.kfood;
import static com.example.shipon.khaidai.FirstPage_Class.kqty;
import static com.example.shipon.khaidai.FirstPage_Class.percent;
import static com.example.shipon.khaidai.FirstPage_Class.price;
import static com.example.shipon.khaidai.FirstPage_Class.total_Cost;

import static com.example.shipon.khaidai.VerifyFragment.per;
import static com.example.shipon.khaidai.VerifyFragment.tot;


/**
 * Created by Shipon on 3/27/2018.
 */

public class BreakfastRecyclerAdopter extends RecyclerView.Adapter<BreakfastRecyclerAdopter.ViewHolder> {
    ArrayList<String> foodName;
    ArrayList<String> price;
    ArrayList<String> details;
    ArrayList<String> ratting;
    ArrayList<Integer> code;
    private int q, p;
    private double cost;

    String ss, ee;
    Context mContext;
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    private StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();

    // data is passed into the constructor
    BreakfastRecyclerAdopter(Context context, ArrayList<String> foodName, ArrayList<String> price, ArrayList<String> details, ArrayList<String> ratting, ArrayList<Integer> code) {
        this.mInflater = LayoutInflater.from(context);
        this.ratting = ratting;
        this.foodName = foodName;
        this.price = price;
        this.code = code;
        this.details = details;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.recyccler_view, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // String animal = mData.get(position);
        holder.foodname.setText(foodName.get(position));
        holder.pprice.setText("BDT: " + price.get(position).substring(4, price.get(position).length()));

        holder.details.setText(details.get(position));
        holder.foodCode.setText("code-" + code.get(position));
        String s = ratting.get(position);
        String ss = s.substring(1, s.length());
        holder.foodRatting.setRating(Float.parseFloat(ss));
        //   Glide.with(holder.photo.getContext())
        //            .using(new FirebaseImageLoader())
        //    .load(mStorageRef + "/Photos/" + userId)
        // .into(imageView);
        // Picasso.with(holder.photo.getContext()).load("khaidai-e7e86.appspot.com/foodimage/221.png").into(holder.photo);
        //  Glide.with(mContext)
        //    .using(new FirebaseImageLoader())
        //  .load(filePath)
        //   .into(holder.photo);
        StorageReference storageRef = firebaseStorage.child("foodimage").child(code.get(position) + ".png");
        Task<Uri> uri = storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //   Log.e("URI", uri.toString());
                Picasso.with(mContext).load(uri).into(holder.photo);
                holder.pload.setVisibility(View.GONE);
            }
        });


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return foodName.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView photo;
        TextView foodname;
        TextView pprice;
        TextView details;
        TextView foodCode;
        RatingBar foodRatting;
        ProgressBar pload;
        Button onegative;
        TextView oquantity;
        Button opositive;
        Button dButtonCart;
        LinearLayout linearLayout;


        ViewHolder(View itemView) {
            super(itemView);
            foodname = (TextView) itemView.findViewById(R.id.refoodname);
            pprice = (TextView) itemView.findViewById(R.id.reprice);
            details = (TextView) itemView.findViewById(R.id.redetails);
            photo = itemView.findViewById(R.id.breakfastImg);
            linearLayout = itemView.findViewById(R.id.lid);
            foodCode = itemView.findViewById(R.id.foodCode);
            foodRatting = itemView.findViewById(R.id.rattingBar);
            pload = itemView.findViewById(R.id.pload);
            onegative = itemView.findViewById(R.id.dButtonNegative);
            oquantity = itemView.findViewById(R.id.dButtonQuantity);
            opositive = itemView.findViewById(R.id.dButtonPositive);
            dButtonCart = itemView.findViewById(R.id.dButtonCart);
            //itemView.setOnClickListener(this);

            dButtonCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = getAdapterPosition();
                    if (q == 0) {
                        Toast.makeText(mContext, "Please select at least one Quantity", Toast.LENGTH_SHORT).show();
                        //return;
                        if (kcode.contains("p" + code.get(p).toString())) {

                            int i = kcode.indexOf("p" + code.get(p).toString());
                            kcode.remove(i);

                            kfood.remove(i);

                            kqty.remove(i);
                            kcost.remove(i);


                        }
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
                        if (kcode.size() > 0) {

                            takaT.setVisibility(View.VISIBLE);
                            percentT.setVisibility(View.VISIBLE);
                        }
                        if (pe != null) {
                            Double vel = total_Cost - tcot;
                            afterPromototal_Cost = vel;
                            percent = pe;
                            percentT.setText(pe + "% off");
                            takaT.setText("Tk " + vel);
                        } else {
                            percentT.setText("");

                            afterPromototal_Cost = total_Cost;
                            takaT.setText("Tk " + afterPromototal_Cost);
                        }
                        if (kcode.size() == 0) {
                            takaT.setVisibility(View.INVISIBLE);
                            percentT.setVisibility(View.INVISIBLE);
                        }
                        viewcart.setText("View your cart(" + kcode.size() + ")");
                        textCartItemCount.setText(String.valueOf(kcode.size()));
                        Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                        Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
                        if (afterPromototal_Cost == 0) {
                            vieeew.setVisibility(View.VISIBLE);
                        }
                        if (kcode.size() > 0) {
                            vieeew.startAnimation(slideUp);
                            vieeew.setVisibility(View.VISIBLE);
                        } else {
                            vieeew.startAnimation(slideDown);
                            vieeew.setVisibility(View.VISIBLE);
                        }
                        textCartItemCount.setText(String.valueOf(kcode.size()));
                        //  CartRecyclerAdopter myAdapter = new CartRecyclerAdopter(mContext, kfood,kcost,kqty,kcode);
                        //  myAdapter.notifyItemRemoved(i);
                        //  myAdapter.notifyItemRangeChanged(i,getItemCount());
                        //CartRecyclerAdopter myAdapter = new CartRecyclerAdopter(mContext, kfood,kcost,kqty,kcode);
                        // myAdapter.notifyItemChanged(i);
                    } else {
                        //  Toast.makeText(mContext, "Added to cart", Toast.LENGTH_SHORT).show();

                        String pp = price.get(p).substring(4, price.get(p).length());
                        ;
                        final Integer foodcode = key.get(p);
                        cost = Double.valueOf((String) pp);
                        if (kcode.contains("p" + code.get(p).toString())) {

                            int i = kcode.indexOf("p" + code.get(p).toString());
                            kcode.remove(i);

                            kfood.remove(i);

                            kqty.remove(i);
                            kcost.remove(i);


                        }

                        kcode.add("p" + code.get(p).toString());
                        kfood.add("p" + foodName.get(p).toString());
                        kqty.add(("p" + q).toString());
                        kcost.add(("p" + cost * q).toString());
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
                        if (kcode.size() > 0) {
                            takaT.setVisibility(View.VISIBLE);
                            percentT.setVisibility(View.VISIBLE);
                        }
                        if (pe != null) {
                            Double vel = total_Cost - tcot;
                            afterPromototal_Cost = vel;
                            percent = pe;
                            percentT.setText(pe + "% off");
                            takaT.setText("Tk " + vel);
                        } else {
                            percentT.setText("");

                            afterPromototal_Cost = total_Cost;
                            takaT.setText("Tk " + afterPromototal_Cost);
                        }
                        if (kcode.size() == 0) {
                            takaT.setVisibility(View.INVISIBLE);
                            percentT.setVisibility(View.INVISIBLE);
                        }
                        viewcart.setText("View your cart(" + kcode.size() + ")");
                        textCartItemCount.setText(String.valueOf(kcode.size()));
                        Animation slideUp = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
                        Animation slideDown = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
                        if (afterPromototal_Cost == 0) {
                            vieeew.setVisibility(View.VISIBLE);
                        }
                        if (kcode.size() > 0) {
                            vieeew.startAnimation(slideUp);
                            vieeew.setVisibility(View.VISIBLE);
                        } else {
                            vieeew.startAnimation(slideDown);
                            vieeew.setVisibility(View.VISIBLE);
                        }
                    }

                }
            });
            opositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    q = Integer.parseInt(oquantity.getText().toString());
                    if (q < 10) {
                        q++;
                        //  cost=Integer.valueOf(price.get(pos))*q;
                        //   ototal.setText(cost*q+"/-");
                        oquantity.setText("" + q);
                    }
                }
            });
            onegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    q = Integer.parseInt(oquantity.getText().toString());
                    if (q > 0) {
                        q--;
                        // cost=Integer.valueOf(price.get(pos))*q;
                        //ototal.setText(cost*q+"/-");
                        oquantity.setText("" + q);
                    }

                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int p = getAdapterPosition();
                    //  Intent i= new Intent(mContext,OrderFood.class);
                    //  i.putExtra("position", String.valueOf(p));
                    //  mContext.startActivity(i, ActivityOptions.makeSceneTransitionAnimation((Activity)mContext).toBundle());
                    // LayoutInflater myLayout = LayoutInflater.from(mContext);
                    // View dialogView = myLayout.inflate(R.layout.dialog, null);
                    // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    //         mContext);
                    // AlertDialog alertDialog = alertDialogBuilder.create();
                    // alertDialogBuilder.setView(dialogView);
                    // alertDialog.show();
                    //   final Dialog dialog = new Dialog(mContext); // Context, this, etc.-
                    //   dialog.setContentView(R.layout.dialog);
                    //  TextView oprice=dialog.findViewById(R.id.dPrice);
                    //    TextView oclose=dialog.findViewById(R.id.dClose);
                    //   final TextView ofoodname=dialog.findViewById(R.id.dFoodName);
                    //   final TextView ototal=dialog.findViewById(R.id.dTotal);
                    //  Button onegative=dialog.findViewById(R.id.dButtonNegative);
                    //  final Button oquantity=dialog.findViewById(R.id.dButtonQuantity);
                    //   Button opositive=dialog.findViewById(R.id.dButtonPositive);
                    //   Button oorder=dialog.findViewById(R.id.dOrder);

                    //   ofoodname.setText(foodName.get(p));
                    // String PP=price.get(p);
                    //oprice.setText(price.get(p));
                    //    String pp=price.get(p).substring(4,price.get(p).length());
                    //   final Integer foodcode=key.get(p);
                    //    cost= Double.valueOf((String)pp);
                    //    oprice.setText(Double.valueOf(pp)+"/-");
                    // q=0;

                    //         ototal.setText(cost*q+"/-");
////
                    // oquantity.setText("Qty-"+q);
                 /*   opositive.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(q<10) {
                                q++;
                                //  cost=Integer.valueOf(price.get(pos))*q;
                                ototal.setText(cost*q+"/-");
                                oquantity.setText("Qty-" + q);
                            }

                        }
                    });
                    onegative.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(q>0) {
                                q--;
                                // cost=Integer.valueOf(price.get(pos))*q;
                                ototal.setText(cost*q+"/-");
                                oquantity.setText("Qty-" + q);
                            }
                        }
                    });
                    oclose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    oorder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // dialog.cancel();
                            if(kcode.contains("p"+foodcode.toString())){

                                int i=kcode.indexOf("p"+foodcode.toString());
                                kcode.remove(i);

                                kfood.remove(i);
                                kqty.remove(i);
                                kcost.remove(i);

                            }

                            kcode.add("p" + foodcode.toString());
                            kfood.add("p" + ofoodname.getText().toString());
                            kqty.add(("p" + q).toString());
                            kcost.add(("p" + cost * q).toString());
                            textCartItemCount.setText(String.valueOf(kcode.size()));
                            try {
                                InternalStorage.writeObject(mContext, "ccode", kcode);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(mContext, "Added", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    });
                    Window window = dialog.getWindow();
                    WindowManager.LayoutParams lp = window.getAttributes();
                    window.setGravity(Gravity.CENTER);

                    lp.width = 600;
                    lp.height = 500;
                   // lp.alpha = 0.7f;
                    window.setAttributes(lp);
                    dialog.setCancelable(false);
                  //  dialog.show();

*/
                    //window.setLayout(WindowManager.LayoutParams.ROTATION_ANIMATION_CHANGED, WindowManager.LayoutParams.ROTATION_ANIMATION_CHANGED);
                }
            });
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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

    public void setname(ArrayList<String> name) {
        this.foodName = name;
        notifyDataSetChanged();
    }

}
