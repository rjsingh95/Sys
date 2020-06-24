package com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;
import com.supplyingyourservice.ranjeet.singh.sys.offer_sample;

public class offersFragment extends Fragment {


        private RecyclerView listview;
        private DatabaseReference myref;
        private static final int ACTIVITY_NUM = 3;
        boolean doubleBackToExitPressedOnce = false;

        private DatabaseReference mUserRef;
        private FirebaseAuth mAuth;
        private ProgressBar progressrv;
        private RelativeLayout rel;
        boolean gaya =false;
    private DatabaseReference database;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.offersfragment, container, false);

        TextView toolbartitle=(TextView)root.findViewById(R.id.Toolbartitle);
        toolbartitle.setText("OFFFERS FROM SUBSCRIBED DEALERS");
            listview = (RecyclerView) root.findViewById(R.id.listview);
            mAuth=FirebaseAuth.getInstance();

             database =FirebaseDatabase.getInstance().getReference();
                String s = ((commomloc) getActivity().getApplication()).getloc();

                mUserRef = FirebaseDatabase.getInstance().getReference().child("todays_offer").child(s).child(mAuth.getCurrentUser().getUid());



            RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
            listview.setLayoutManager(mLayoutManager2);



            rel=(RelativeLayout)root.findViewById(R.id.rel);
            progressrv=(ProgressBar)root.findViewById(R.id.progressBar2);
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
                                viewHolder.setImage(image,requireActivity());

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
return root;
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