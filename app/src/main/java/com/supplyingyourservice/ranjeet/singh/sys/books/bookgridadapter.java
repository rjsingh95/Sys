package com.supplyingyourservice.ranjeet.singh.sys.books;

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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.supplyingyourservice.ranjeet.singh.sys.ProductDetailActivity;
import com.supplyingyourservice.ranjeet.singh.sys.R;

import com.squareup.picasso.Picasso;

import java.util.List;


public class bookgridadapter extends RecyclerView
        .Adapter<bookgridadapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private List<book_sample> bean;
    private Context context;

    ValueEventListener main;
    private static MyClickListener myClickListener;
    Typeface fonts1,fonts2,fonts3,fonts4,fonts5;



    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public bookgridadapter(Context context, List<book_sample> bean) {


        this.context = context;
        this.bean = bean;
    }



    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.book, parent, false);
        fonts1 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");

        fonts2 = Typeface.createFromAsset(context.getAssets(),
                "fonts/MavenPro-Regular.ttf");
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        final book_sample aplha =bean.get(position);

        //  holder.icon.setImageResource(mDataset.get(position).get());
        //holder.image.setImageResource(bean.get(position).getImage());

if(aplha!=null) {
    final DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    if (aplha.getBook_id() != null) {
        database.child("books").child(aplha.getBook_id()).child("book_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String img = dataSnapshot.getValue().toString();

                Picasso.with(context).load(img).into(holder.image);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); }

    holder.title.setTypeface(fonts2);
    holder.author.setTypeface(fonts1);


    holder.title.setText(aplha.getTitle());


    holder.author.setText(aplha.getAuthor());


    //holder.price.setText(aplha.getPrice());
    //holder.cutprice.setText(bean.get(position).getCutprice());
    //holder.discount.setText(bean.get(position).getDiscount());
    //holder.ratingtex.setText(bean.get(position).getRatingtext());
    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String post_key = aplha.getBook_id();
            if (aplha.getBook_id() != null) {
                Intent productdetailsintent = new Intent(context, ProductDetailActivity.class);
                Bundle extras = new Bundle();
                extras.putString("book_id", post_key);
                productdetailsintent.putExtras(extras);
                productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(productdetailsintent);
               
            } else {
                Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();
            }
        }
    });

}

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


        private final TextView author;
        ImageView image;
        TextView title;
        TextView price;

        View mview;
        public DataObjectHolder(View itemView) {
            super(itemView);
            mview=itemView;


            image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
            title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.title);
            author = (TextView) itemView.findViewById(R.id.author);

            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            //       myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
}