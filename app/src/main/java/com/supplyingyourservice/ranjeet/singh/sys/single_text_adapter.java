package com.supplyingyourservice.ranjeet.singh.sys;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class single_text_adapter extends RecyclerView
        .Adapter<single_text_adapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<String> bean;
    private Context context;

    ValueEventListener main;
    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public single_text_adapter(Context context, List<String> bean) {


        this.context = context;
        this.bean = bean;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.single_text_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        //  holder.icon.setImageResource(mDataset.get(position).get());
        //holder.image.setImageResource(bean.get(position).getImage());

        holder.title.setText(bean.get(position));
        //holder.price.setText(aplha.getPrice());
        //holder.cutprice.setText(bean.get(position).getCutprice());
        //holder.discount.setText(bean.get(position).getDiscount());
        //holder.ratingtex.setText(bean.get(position).getRatingtext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_key =bean.get(position);
                Intent productdetailsintent = new Intent(context,products_list.class);

                Bundle extras = new Bundle();
                extras.putString("product_name",post_key);
                productdetailsintent.putExtras(extras);
                productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(productdetailsintent);
               
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

View mview;
        public DataObjectHolder(View itemView) {
            super(itemView);
mview=itemView;

            title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.text_layout);



            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
}