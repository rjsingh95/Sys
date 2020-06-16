package com.supplyingyourservice.ranjeet.singh.sys;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import com.supplyingyourservice.ranjeet.singh.sys.books.all_books;


public class GroomingRecyclerViewAdapter extends RecyclerView
        .Adapter<GroomingRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private final String type;
    private ArrayList<BeanlistGrooming>bean;
    Activity main;


    private static MyClickListener myClickListener;
  private Context context;

    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {


        private final View mview;
        TextView title;


        public DataObjectHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.text_layout);

mview=itemView;





        }

        @Override
        public void onClick(View v) {
     //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }



    public GroomingRecyclerViewAdapter(Activity activity,Context context, ArrayList<BeanlistGrooming> bean,String i) {

this.main = activity;
        this.context = context;
        this.bean = bean;
        this.type=i;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_text_layout, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {




        holder.title.setText(bean.get(position).getTitle());

          final int i=position;
          holder.mview.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(type.equals("browser")){
                  Intent productdetailsintent = new Intent(context,all_type_category.class);
                  Bundle extras = new Bundle();
                  extras.putString("category",bean.get(i).getTitle());
                  productdetailsintent.putExtras(extras);
                  productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                  context.startActivity(productdetailsintent);
                  }
                  if(type.equals("bookstore")){
                      Intent productdetailsintent = new Intent(context,all_books.class);
                      Bundle extras = new Bundle();
                      extras.putString("category",bean.get(i).getTitle());
                      productdetailsintent.putExtras(extras);
                      productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                      context.startActivity(productdetailsintent);
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






}
