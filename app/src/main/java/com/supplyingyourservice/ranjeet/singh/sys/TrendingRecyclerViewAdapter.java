package com.supplyingyourservice.ranjeet.singh.sys;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;


public class TrendingRecyclerViewAdapter extends RecyclerView
        .Adapter<TrendingRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<aproduct> bean;
    private Context context;

    Activity main;
    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;
    private String post_key;


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public TrendingRecyclerViewAdapter( Context context, List<aproduct> bean,Activity act) {


        this.context = context;
        this.bean = bean;
        this.main=act;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_productlist, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
final aproduct aplha =bean.get(position);
    String price= String.valueOf(aplha.getPrice());

        post_key = aplha.getProduct_id();
        //  holder.icon.setImageResource(mDataset.get(position).get());
        //holder.image.setImageResource(bean.get(position).getImage());
        Picasso.with(context).load(aplha.getProductdp()).into(holder.image);
        holder.price.setText(price);

        holder.title.setText(aplha.getTitle());
        //holder.price.setText(aplha.getPrice());
        //holder.cutprice.setText(bean.get(position).getCutprice());
        //holder.discount.setText(bean.get(position).getDiscount());
        //holder.ratingtex.setText(bean.get(position).getRatingtext());
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String post_key = aplha.getProduct_id();
                Intent productdetailsintent = new Intent (context,ProductDetailActivity.class);
                productdetailsintent.putExtra("product_id",post_key);
                view.getContext().startActivity(productdetailsintent);

            }
        });
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();

        float i =  aplha.getRating();
        holder.ratingbar.setRating(i/2);
        holder.ratingbar =new ScaleRatingBar(context);

        DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
        if(post_key!=null){
        dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key);
            dref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("like")) {
                        holder.fav2.setVisibility(View.VISIBLE);
                        holder.fav1.setVisibility(View.GONE);

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else {

           Log.d("null key",aplha.getTitle());

        }

        holder.mSmallBang = SmallBang.attach2Window(main);


        holder.fav1.setOnClickListener(new View.OnClickListener() {
            public DatabaseReference dref;

            @Override
            public void onClick(final View v) {

                dref = FirebaseDatabase.getInstance().getReference();
                dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").setValue("set").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        holder.fav2.setVisibility(View.VISIBLE);
                        holder.fav1.setVisibility(View.GONE);
                        like(v);
                    }
                });


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
                dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        holder.fav2.setVisibility(View.GONE);
                        holder.fav1.setVisibility(View.VISIBLE);

                    }
                });


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
        TextView price;
        TextView cutprice;

        TextView ratingtex;
        ImageView fav1,fav2;
        ScaleRatingBar ratingbar;
        private SmallBang mSmallBang;
View mview;
        public DataObjectHolder(View itemView) {
            super(itemView);
mview=itemView;
            image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
            title = (TextView) itemView.findViewById(R.id.Title);
            price = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.price);

            ratingtex = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingtext);

//        ***********ratingBar**********
            ratingbar = (ScaleRatingBar) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingbar);

            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
}