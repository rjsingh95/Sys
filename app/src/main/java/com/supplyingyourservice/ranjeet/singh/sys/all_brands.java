package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.books.all_books;
import com.supplyingyourservice.ranjeet.singh.sys.model.Acategory;
import com.supplyingyourservice.ranjeet.singh.sys.model.category;

public class all_brands extends AppCompatActivity {
    String key=null;
    String value=null;
    private single_text_adapter baseAdapter1;
    private RecyclerView rv2;
    private String mpost_key;
    private DatabaseReference mref;
    boolean gaya =false;
    List<String> list = new ArrayList<>();
    private single_text_adapter baseAdapter2;
    private RecyclerView rv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_categories);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);
        final ProgressBar progressrv = (ProgressBar) findViewById(R.id.progressBar2);
        rv2=(RecyclerView)findViewById(R.id.recyclerView);
        EditText search=(EditText)findViewById(R.id.showbrand);
       search.setVisibility(View.GONE);


        final String recieve = getIntent().getStringExtra("category");
        mpost_key =recieve.toLowerCase();



        search.setHint("Search Brands..");
         mref = FirebaseDatabase.getInstance().getReference().child("categories");

       // DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("brand");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(all_brands.this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager);
        Query query = mref.orderByChild("type")
                .equalTo(mpost_key);
        FirebaseRecyclerAdapter<Acategory, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<Acategory, FriendsViewHolder>(
                Acategory.class,
                R.layout.single_text_layout,
                FriendsViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final Acategory friends, int i) {

                final Handler handler = new Handler();
                final String list_user_id = getRef(i).getKey();

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


                    }
                }, 6500);

                if(list_user_id!=null){
                    gaya=true;
                    progressrv.setVisibility(View.GONE);
                    rel.setVisibility(View.GONE);
                }
                AllproductsViewHolder.setTitle(friends.getCategory());

                AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent productdetailsintent = new Intent(all_brands.this, products_list.class);
                        productdetailsintent.putExtra("product_name",mpost_key+friends.getT_c());
                        startActivity(productdetailsintent);



                        //   Intent productdetailsintent = new Intent(all_cateogories.this, all_brands.class);
                       // productdetailsintent.putExtra("category", friends.getCategory());
                        //startActivity(productdetailsintent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        };
        rv2.setAdapter(friendsRecyclerViewAdapter);



        final EditText showbrand = (EditText) findViewById(R.id.showbrand);

        String recieve2 = getIntent().getStringExtra("extra_type");
        TextView tv = (TextView) findViewById(R.id.tv);
        rv3=(RecyclerView)findViewById(R.id.recyclerView2);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(all_brands.this, LinearLayoutManager.VERTICAL, false);
        rv3.setLayoutManager(mLayoutManager2);
        if(recieve2!=null) {
            tv.setVisibility(View.VISIBLE);
            rv3.setVisibility(View.VISIBLE);
            showbrand.setVisibility(View.GONE);
            final DatabaseReference mref2 = FirebaseDatabase.getInstance().getReference().child("categories");

            Query query2 = mref2.orderByChild("type")
                    .startAt(recieve2)
                    .endAt(recieve2 + "\uf8ff");
            FirebaseRecyclerAdapter<category, all_cateogories.FriendsViewHolder> friendsRecyclerViewAdapter2 = new FirebaseRecyclerAdapter<category, all_cateogories.FriendsViewHolder>(
                    category.class,
                    R.layout.single_text_layout,
                    all_cateogories.FriendsViewHolder.class,
                    query2
            ) {
                @Override
                protected void populateViewHolder(final all_cateogories.FriendsViewHolder AllproductsViewHolder, final category friends, int i) {

                    final String key = getRef(i).getKey();
                    final Handler handler = new Handler();

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (getItemCount() == 0) {
                                rel.setVisibility(View.VISIBLE);
                                progressrv.setVisibility(View.GONE);
                            } else {
                                rel.setVisibility(View.GONE);
                            }

                            // Do something after 5s = 5000ms

                        }
                    }, 2500);

                    if (key != null) {
                        gaya = true;
                        progressrv.setVisibility(View.GONE);
                        rel.setVisibility(View.GONE);
                    }

                    AllproductsViewHolder.setTitle(friends.getCategory());

                    AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("allcat", key);

                            Intent productdetailsintent = new Intent(all_brands.this, all_brands.class);
                            productdetailsintent.putExtra("category", friends.getCategory());
                            startActivity(productdetailsintent);


                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }

            };
            rv3.setAdapter(friendsRecyclerViewAdapter2);
        }


        showbrand.requestFocusFromTouch();
        showbrand.clearFocus();


   showbrand.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           Query query = mref.orderByChild("b_c")
                   .startAt(mpost_key+String.valueOf(charSequence))
                   .endAt(charSequence + "\uf8ff");
           FirebaseRecyclerAdapter<brand, all_cateogories.FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<brand, all_cateogories.FriendsViewHolder>(
                   brand.class,
                   R.layout.single_text_layout,
                   all_cateogories.FriendsViewHolder.class,
                   query
           ) {
               @Override
               protected void populateViewHolder(final all_cateogories.FriendsViewHolder AllproductsViewHolder, final brand friends, int i) {


                   final String brand = friends.getBrand_show();


                   AllproductsViewHolder.setTitle(brand);



                   AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           showbrand.setText(brand);

                           Intent productdetailsintent = new Intent(all_brands.this, products_list.class);
                           productdetailsintent.putExtra("product_name",mpost_key+brand);
                           startActivity(productdetailsintent);
                       }
                   });




               }
           };
           rv2.setAdapter(friendsRecyclerViewAdapter);


       }




       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    Query query = mref.orderByChild("b_c")
            .startAt(mpost_key+String.valueOf(charSequence))
            .endAt(charSequence + "\uf8ff");
    FirebaseRecyclerAdapter<brand, all_cateogories.FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<brand, all_cateogories.FriendsViewHolder>(
            brand.class,
            R.layout.single_text_layout,
            all_cateogories.FriendsViewHolder.class,
            query
    ) {
        @Override
        protected void populateViewHolder(final all_cateogories.FriendsViewHolder AllproductsViewHolder, final brand friends, int i) {


            final String brand = friends.getBrand_show();


            AllproductsViewHolder.setTitle(brand);



            AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showbrand.setText(brand);

                    Intent productdetailsintent = new Intent(all_brands.this, products_list.class);
                    productdetailsintent.putExtra("product_name",mpost_key+brand);
                    startActivity(productdetailsintent);
                }
            });




    }
    };
    rv2.setAdapter(friendsRecyclerViewAdapter);

       }

       @Override
       public void afterTextChanged(Editable editable) {

       }
   });




    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.text_layout);
            userNameView.setText(name);

        }

    }

}
