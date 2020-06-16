package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.squareup.picasso.Picasso;

import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;

public class offers extends AppCompatActivity {


        private RecyclerView listview;
        private DatabaseReference myref;
        private static final int ACTIVITY_NUM = 3;
        boolean doubleBackToExitPressedOnce = false;
        private Context mContext = offers.this;
        private DatabaseReference mUserRef;
        private FirebaseAuth mAuth;
        private ProgressBar progressrv;
        private RelativeLayout rel;
        boolean gaya =false;
    private DatabaseReference database;


    @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onCreate(@Nullable Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.offers);
        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        toolbartitle.setText("OFFFERS FROM SUBSCRIBED DEALERS");
            listview = (RecyclerView) findViewById(R.id.listview);
            setupBottomNavigationView();
            mAuth=FirebaseAuth.getInstance();

             database =FirebaseDatabase.getInstance().getReference();
                String s = ((commomloc) offers.this.getApplication()).getloc();

                mUserRef = FirebaseDatabase.getInstance().getReference().child("todays_offer").child(s).child(mAuth.getCurrentUser().getUid());



            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            listview.setLayoutManager(mLayoutManager2);



            rel=(RelativeLayout)findViewById(R.id.rel);
            progressrv=(ProgressBar)findViewById(R.id.progressBar2);
            FirebaseRecyclerAdapter<offer_sample,newsholder> adapter = new FirebaseRecyclerAdapter<offer_sample,newsholder>
                    (offer_sample.class, R.layout.aoffer,newsholder.class,mUserRef)
            {
                @Override
                protected void populateViewHolder(final newsholder viewHolder, offer_sample model, int position) {
                    final String post_key=  getRef(position).getKey();
                    if(post_key!=null){
                        gaya=true;
                        progressrv.setVisibility(View.GONE);
                    }
                    viewHolder.discount.setText("-discount "+"Rs:"+model.getDiscount());
                    database.child("products").child(model.getProduct_id()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("title")) {
                                String title = dataSnapshot.child("title").getValue().toString();
                                viewHolder.setTitle(title);


                            }
                            if(dataSnapshot.hasChild("price")){
                                viewHolder.price.setText("MRP:"+dataSnapshot.child("price").getValue().toString());
                            }
                            if(dataSnapshot.hasChild("productdp")){
                                String image = dataSnapshot.child("productdp").getValue().toString();
                                viewHolder.setImage(image,offers.this);

                            }




                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    database.child("users").child(post_key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild("shop_name")) {
                                final String userName = dataSnapshot.child("shop_name").getValue().toString();

                                viewHolder.shopkeeper.setText("From " +userName);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    final Handler handler = new Handler();


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(getItemCount()==0  )
                            {
                                rel.setVisibility(View.VISIBLE);
                                progressrv.setVisibility(View.GONE);
                            }else {
                                rel.setVisibility(View.GONE);
                            }

                            // Do something after 5s = 5000ms

                        }
                    }, 2500);



                }

            };
            listview.setAdapter(adapter);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if( !gaya)
                    {
                        rel.setVisibility(View.VISIBLE);
                        progressrv.setVisibility(View.GONE);
                    }else {
                        rel.setVisibility(View.GONE);
                    }
                    // Do something after 5s = 5000ms

                }
            }, 6500);

        }
        @RequiresApi(api = Build.VERSION_CODES.M)
        private void setupBottomNavigationView(){
            BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
            BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
            BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
            Menu menu = bottomNavigationViewEx.getMenu();
            MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
            menuItem.setChecked(true);

            menuItem.setChecked(true);
            bottomNavigationViewEx.setIconTintList(ACTIVITY_NUM, ColorStateList.valueOf(getColor(R.color.colorAccent)));

        }

        @Override
        public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;

            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        public static class newsholder
                extends RecyclerView.ViewHolder{


            private final TextView price;
            private final TextView discount;
            private final TextView shopkeeper;
            View mView;
            public newsholder(View itemView) {
                super(itemView);
               
                price = (TextView) itemView.findViewById(R.id.price);

                shopkeeper = (TextView) itemView.findViewById(R.id.shopkeeper);


                discount = (TextView) itemView.findViewById(R.id.discount);
                //cutprice = (TextView) itemView.findViewById(R.id.cutprice);
                //discount = (TextView) itemView.findViewById(R.id.discount);
                //ratingtex = (TextView) itemView.findViewById(R.id.ratingtext);
                mView =itemView;

            }
            public void  setTitle(String t){

                TextView title = (TextView) itemView.findViewById(R.id.Title);
                title.setText(t);
            }
            public void setImage(String thumb_image, Context ctx){

                ImageView userImageView = (ImageView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
                Picasso.with(ctx).load(thumb_image).into(userImageView);

            }
           



    }
}