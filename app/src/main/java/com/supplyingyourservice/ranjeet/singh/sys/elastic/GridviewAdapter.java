package com.supplyingyourservice.ranjeet.singh.sys.elastic;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.supplyingyourservice.ranjeet.singh.sys.ProductDetailActivity;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBang;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBangListener;
import com.supplyingyourservice.ranjeet.singh.sys.addprod;
import com.squareup.picasso.Picasso;

import java.util.List;


public class GridviewAdapter extends RecyclerView
        .Adapter<GridviewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<addprod> bean;
    private Context context;

    ValueEventListener main;
    Activity Act;
    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public GridviewAdapter(Context context, List<addprod> bean,Activity a) {


        this.context = context;
        this.Act=a;
        this.bean = bean;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.grid_productlist, parent, false);
        fonts1 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final addprod aplha =bean.get(position);

        //  holder.icon.setImageResource(mDataset.get(position).get());
        //holder.image.setImageResource(bean.get(position).getImage());


        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        if(aplha.getProduct_id()!=null) {
            database.child("products").child(aplha.getProduct_id()).addValueEventListener(new ValueEventListener() {





                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    String img = dataSnapshot.child("productdp").getValue().toString();

                    Picasso.with(context).load(img).into(holder.image);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        holder.title.setTypeface(fonts2);



        holder.title.setText(aplha.getTitle());


        holder.title.setText(aplha.getTitle());
        String price = ("MRP " + String.valueOf(aplha.getPrice()));


        holder.title.setTypeface(fonts2);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();


        holder.mSmallBang = SmallBang.attach2Window(Act);


        holder.fav1.setOnClickListener(new View.OnClickListener() {
            public DatabaseReference dref;

            @Override
            public void onClick(final View v) {

                dref = FirebaseDatabase.getInstance().getReference();
                if(aplha.getProduct_id()!=null) {
                    dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(aplha.getProduct_id()).child("like").setValue("set").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            holder.fav2.setVisibility(View.VISIBLE);
                            holder.fav1.setVisibility(View.GONE);
                            like(v);
                        }
                    });

                }
            }

            public void like(View view) {
                holder.fav2.setImageResource(com.supplyingyourservice.ranjeet.singh.sys.R.drawable.f4);
                holder.mSmallBang.bang(view);
                holder.mSmallBang.setmListener(new SmallBangListener() {
                    @Override
                    public void onAnimationStart() {

                    }

                    @Override
                    public void onAnimationEnd() {

                    }


                });
            }

        });


        holder.fav2.setOnClickListener(new View.OnClickListener() {
            DatabaseReference dref;

            @Override
            public void onClick(View v) {

                dref = FirebaseDatabase.getInstance().getReference();

                if(aplha.getProduct_id()!=null) {
                dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(aplha.getProduct_id()).child("like").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        holder.fav2.setVisibility(View.GONE);
                        holder.fav1.setVisibility(View.VISIBLE);

                    }
                });


            }}
        });






        //holder.price.setText(aplha.getPrice());
        //holder.cutprice.setText(bean.get(position).getCutprice());
        //holder.discount.setText(bean.get(position).getDiscount());
        //holder.ratingtex.setText(bean.get(position).getRatingtext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String post_key =aplha.getProduct_id();
                if(aplha.getProduct_id()!=null) {
                    Intent productdetailsintent = new Intent(context, ProductDetailActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("product_id", post_key);
                    productdetailsintent.putExtras(extras);
                    productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(productdetailsintent);
                   
                }
                else {
                    Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void deleteItem(int index) {
        bean.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return bean.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);



    }



    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {





        ImageView image;
        TextView title;

        ImageView fav1,fav2;
        RatingBar ratingbar;
        private SmallBang mSmallBang;
        View mview;
        public DataObjectHolder(View itemView) {
            super(itemView);
            mview=itemView;


            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);
            image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.image);
            title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.title);


//  *****for line on text********
         //   cutprice.setPaintFlags(cutprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


//        ***********ratingBar**********
         //   ratingbar = (RatingBar) itemView.findViewById(R.id.ratingbar);
      //      LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
       //     stars.getDrawable(2).setColorFilter(Color.parseColor("#f8d64e"), PorterDuff.Mode.SRC_ATOP);

       //     fav1 = (ImageView)itemView.findViewById(R.id.fav1);
       //     fav2 = (ImageView)itemView.findViewById(R.id.fav2);


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
}