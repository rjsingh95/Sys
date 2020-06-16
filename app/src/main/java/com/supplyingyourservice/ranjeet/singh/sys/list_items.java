package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.squareup.picasso.Picasso;


/**
 * Created by Ranjeet on 10/22/2017.
 */

public class list_items extends AppCompatActivity {
    private DatabaseReference mshopdatabase;
    private DatabaseReference mproductdatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = list_items.this;

    private RecyclerView mproductList;
    private String mpost_key;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.fragment_watchlist);
        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        toolbartitle.setText("PRODUCTS LIST");

        mpost_key = getIntent().getStringExtra("category");


        mproductList = (RecyclerView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.productsrv);
        mAuth = FirebaseAuth.getInstance();
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mshopdatabase = FirebaseDatabase.getInstance().getReference().child("Lists").child(mpost_key);

        mshopdatabase.keepSynced(true);
        mproductdatabase = FirebaseDatabase.getInstance().getReference().child("products");
        mproductdatabase.keepSynced(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(list_items.this, LinearLayoutManager.VERTICAL, false);
        mproductList.setLayoutManager(mLayoutManager);
        mproductList.setHasFixedSize(true);

    }



    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<likesdemo, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<likesdemo, FriendsViewHolder>(
                likesdemo.class,
                com.supplyingyourservice.ranjeet.singh.sys.R.layout.listview_productlist,
                FriendsViewHolder.class,
                mshopdatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder friendsViewHolder, likesdemo friends, int i) {


                final String list_user_id = getRef(i).getKey();

                mproductdatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("title").getValue().toString();
                        String userThumb = dataSnapshot.child("productdp").getValue().toString();
                        friendsViewHolder.setTitle(userName);
                       
                        friendsViewHolder.setImage(userThumb, list_items.this);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        };
        mproductList.setAdapter(friendsRecyclerViewAdapter);


    }


    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Title);
            userNameView.setText(name);

        }

        public void setImage(String thumb_image, Context ctx){

            ImageView userImageView = (ImageView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
            Picasso.with(ctx).load(thumb_image).into(userImageView);

        }
    }


}