package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.supplyingyourservice.ranjeet.singh.sys.model.Asubcategory;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.List;

public class products_list extends AppCompatActivity {
    String key = null;
    String value = null;
    private single_text_adapter baseAdapter1;
    private RecyclerView rv2;
    private String mpost_key;
    private DatabaseReference mref;
    List<aproduct> list = new ArrayList<>();
    private DatabaseReference dref;
    private ProgressBar progressrv;

    boolean gaya =false;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.products_list);


        EditText search = (EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.showbrand);
        search.setVisibility(View.GONE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rv2 = (RecyclerView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.recyclerView);
        mref = FirebaseDatabase.getInstance().getReference().child("sub_categories");

        mpost_key = getIntent().getStringExtra("t_c");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(products_list.this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager);
        mAuth= FirebaseAuth.getInstance();



        progressrv=(ProgressBar)findViewById(R.id.progress);
        Query query = mref.orderByChild("t_c")
                .startAt(mpost_key.toLowerCase())
                .endAt(mpost_key.toLowerCase() + "\uf8ff");

        FirebaseRecyclerAdapter<Asubcategory, FriendsViewHolder> adapter = new FirebaseRecyclerAdapter<Asubcategory,FriendsViewHolder>(
                Asubcategory.class, com.supplyingyourservice.ranjeet.singh.sys.R.layout.listview_productlist,FriendsViewHolder.class,query)
        {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, final Asubcategory model, int position) {

                final String post_key = getRef(position).getKey();

                final Handler handler = new Handler();


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(getItemCount()==0  )
                        {

                            progressrv.setVisibility(View.GONE);
                        }
                        // Do something after 5s = 5000ms

                    }
                }, 2500);




                viewHolder.setTitle(model.getSubCategories());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this ,post_key,"product clicked",Toast.LENGTH_SHORT).show();
                        Intent productdetailsintent = new Intent(products_list.this, ProductDetailActivity.class);
                        productdetailsintent.putExtra("product_id", post_key);
                        startActivity(productdetailsintent);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };





    rv2.setAdapter(adapter);

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
                price = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.price);
                ratingbar = (ScaleRatingBar) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ratingbar);
                discount = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.discount);
                fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
                fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);
                }
                public void  setTitle(String t){
                TextView title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Title);
                title.setText(t); }
                public void setImage(String i, Context context){
                ImageView image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
                Picasso.with(context).load(i).into(image); }




        }








}