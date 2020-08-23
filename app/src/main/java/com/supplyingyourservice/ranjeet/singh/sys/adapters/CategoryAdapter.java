package com.supplyingyourservice.ranjeet.singh.sys.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.supplyingyourservice.ranjeet.singh.sys.ProductDetailActivity;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBang;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBangListener;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView
        .Adapter<CategoryAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<aproduct> bean;
    private Context context;

    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;
    private String post_key;


    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public CategoryAdapter(Context context, ArrayList<aproduct> bean) {


        this.context = context;
        this.bean = bean;

    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {





    }

    public void deleteItem(int index) {
        bean.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return 10;
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
            image = (ImageView) itemView.findViewById(R.id.Images);
            title = (TextView) itemView.findViewById(R.id.Title);
            price = (TextView) itemView.findViewById(R.id.price);

            ratingtex = (TextView) itemView.findViewById(R.id.ratingtext);

//        ***********ratingBar**********
            ratingbar = (ScaleRatingBar) itemView.findViewById(R.id.ratingbar);

            fav1 = (ImageView)itemView.findViewById(R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(R.id.fav2);


            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
}