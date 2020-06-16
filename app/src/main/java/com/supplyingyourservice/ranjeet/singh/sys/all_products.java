package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.willy.ratingbar.ScaleRatingBar;

public class all_products extends AppCompatActivity {
    String key = null;
    String value = null;
    private RecyclerView rv2;
    private String mpost_key;
    private DatabaseReference mref;
    List<aproduct> list = new ArrayList<>();
    private DatabaseReference dref;
    private FirebaseAuth mAuth;
    private ArrayList<aproduct> Bean1;
    private TrendingRecyclerViewAdapter baseAdapter1;
    private ProgressBar progress;
    private String brand_key;
    private String node="category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.products_list);
      progress=(ProgressBar)findViewById(R.id.progress);


        EditText search = (EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.showbrand);
        search.setVisibility(View.GONE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rv2 = (RecyclerView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.recyclerView);
        mref = FirebaseDatabase.getInstance().getReference().child("products");

        mpost_key = getIntent().getStringExtra("category");


        brand_key = getIntent().getStringExtra("brand");
        if(brand_key!=null){
            node="brand";
            mpost_key=brand_key;
        }

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(all_products.this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager);
        mAuth= FirebaseAuth.getInstance();
        Bean1 = new ArrayList<aproduct>();


        Query query = mref.orderByChild(node).equalTo(mpost_key);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(dataSnapshot1.hasChild(node)){

                        aproduct obj = new aproduct();

                        obj = dataSnapshot1.getValue(aproduct.class);
                        Bean1.add(obj);


                    }
                }
                Log.d("comparelo","start");
                Collections.sort(Bean1, new Comparator<aproduct>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public int compare(aproduct aproduct, aproduct t1) {

                        return Long.compare(t1.getLevel(), aproduct.getLevel());
                    }
                });
                Log.d("comparelo", "end");
                baseAdapter1 = new TrendingRecyclerViewAdapter(all_products.this, Bean1, all_products.this);
                 baseAdapter1.notifyDataSetChanged();
                 progress.setVisibility(View.GONE);
                rv2.setAdapter(baseAdapter1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        FirebaseRecyclerAdapter<aproduct, FriendsViewHolder> adapter = new FirebaseRecyclerAdapter<aproduct,FriendsViewHolder>(
                aproduct.class, com.supplyingyourservice.ranjeet.singh.sys.R.layout.listview_productlist,FriendsViewHolder.class,query)
        {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, final aproduct model, int position) {

                final String post_key = getRef(position).getKey();


                dref = FirebaseDatabase.getInstance().getReference();
                dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key);
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("like")) {


                            viewHolder.fav2.setVisibility(View.VISIBLE);
                            viewHolder.fav1.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.mSmallBang = SmallBang.attach2Window(all_products.this);


                viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
                    public DatabaseReference dref;

                    @Override
                    public void onClick(final View v) {

                        dref = FirebaseDatabase.getInstance().getReference();
                        dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").setValue("set").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viewHolder.fav2.setVisibility(View.VISIBLE);
                                viewHolder.fav1.setVisibility(View.GONE);
                                like(v);
                            }
                        });


                    }

                    public void like(View view) {
                        viewHolder.fav2.setImageResource(com.supplyingyourservice.ranjeet.singh.sys.R.drawable.f4);
                        viewHolder.mSmallBang.bang(view);
                        viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {

                            }


                        });
                    }

                });


                viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
                    DatabaseReference dref;

                    @Override
                    public void onClick(View v) {

                        dref = FirebaseDatabase.getInstance().getReference();
                        dref.child("Likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viewHolder.fav2.setVisibility(View.GONE);
                                viewHolder.fav1.setVisibility(View.VISIBLE);

                            }
                        });


                    }
                });


                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(model.getProductdp(), getApplicationContext());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      
                        Intent productdetailsintent = new Intent(all_products.this, ProductDetailActivity.class);
                        productdetailsintent.putExtra("product_id", post_key);
                        startActivity(productdetailsintent);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };





    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        private final TextView price,discount;
        private final ScaleRatingBar ratingbar;
        private ImageView fav1, fav2 ;
        View mView;
        public SmallBang mSmallBang;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);
            price = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.price);
            ratingbar = (ScaleRatingBar) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingbar);
            discount = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.discount);
        }
        public void  setTitle(String t){
            TextView title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Title);
            title.setText(t); }
        public void setImage(String i, Context context){
            ImageView image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
            Picasso.with(context).load(i).into(image); }




    }








}