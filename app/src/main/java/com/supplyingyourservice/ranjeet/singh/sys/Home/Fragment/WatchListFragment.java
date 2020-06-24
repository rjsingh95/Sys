package com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.supplyingyourservice.ranjeet.singh.sys.likesdemo;
import com.willy.ratingbar.ScaleRatingBar;



public class WatchListFragment extends Fragment {
    private DatabaseReference mshopdatabase;
    private DatabaseReference mproductdatabase;
    private FirebaseAuth mAuth;
    private String mCurrent_user_id;
    private static final int ACTIVITY_NUM = 1;

    private RecyclerView mproductList;
    private ProgressBar progress;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_watchlist, container, false);
   TextView toolbartitle=(TextView)root.findViewById(R.id.Toolbartitle);
        progress=(ProgressBar)root.findViewById(R.id.progress);
        toolbartitle.setText("WISHLIST");

        mproductList = (RecyclerView)root. findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.productsrv);
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
        mCurrent_user_id = mAuth.getCurrentUser().getUid();
        mshopdatabase = FirebaseDatabase.getInstance().getReference().child("Likes").child(mCurrent_user_id);
        mshopdatabase.keepSynced(true);
        mproductdatabase = FirebaseDatabase.getInstance().getReference().child("products");
        mproductdatabase.keepSynced(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        mproductList.setLayoutManager(mLayoutManager);
        mproductList.setHasFixedSize(true);
        setupBottomNavigationView();}
    return  root;
    }


    private void setupBottomNavigationView() {

         FirebaseRecyclerAdapter<likesdemo, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<likesdemo, FriendsViewHolder>(
                likesdemo.class,
                com.supplyingyourservice.ranjeet.singh.sys.R.layout.listview_productlist,
                FriendsViewHolder.class,
                mshopdatabase
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder friendsViewHolder, likesdemo friends, int i) {


                final String list_user_id = getRef(i).getKey();
                if(list_user_id!=null){
                    progress.setVisibility(View.GONE);
                }
                friendsViewHolder.rating.setVisibility(View.GONE);
                friendsViewHolder.remove.setVisibility(View.VISIBLE);
                friendsViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mshopdatabase.child(list_user_id).removeValue();
                    }
                });
                friendsViewHolder.price.setVisibility(View.GONE);

                mproductdatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        final String userName = dataSnapshot.child("title").getValue().toString();
                        String userThumb = dataSnapshot.child("productdp").getValue().toString();
                        friendsViewHolder.setTitle(userName);
                        friendsViewHolder.fav1.setVisibility(View.GONE);

                        friendsViewHolder.setImage(userThumb, requireActivity());

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

        private final ImageView fav1;
        private final TextView remove;
        private final ScaleRatingBar rating;
        private final TextView price;
        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);

            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            rating = (ScaleRatingBar)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingbar);
            fav1.setVisibility(View.GONE);
            remove=(TextView)itemView.findViewById(R.id.remove);
            price=(TextView)itemView.findViewById(R.id.price);

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