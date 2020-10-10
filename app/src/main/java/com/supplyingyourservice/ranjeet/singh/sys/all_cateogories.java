package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
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
import com.supplyingyourservice.ranjeet.singh.sys.model.category;

import java.util.ArrayList;
import java.util.List;

public class all_cateogories extends AppCompatActivity {
    String key=null;
    String value=null;

    private RecyclerView rv2;
    private EditText showbrand;
    private EditText edit;
    private int num;
    boolean gaya =false;
    private String mpost_key;


       @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_categories);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final RelativeLayout rel = (RelativeLayout) findViewById(R.id.rel);
        final ProgressBar progressrv = (ProgressBar) findViewById(R.id.progressBar2);
        rv2=(RecyclerView)findViewById(R.id.recyclerView);
        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("categories");
        final List<String> list = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager1);
        showbrand= (EditText) findViewById(R.id.showbrand);
        showbrand.clearFocus();
        showbrand.requestFocusFromTouch();
        showbrand.clearFocus();
        String j=showbrand.getText().toString();
        num =0;
        if(j.equalsIgnoreCase("")) {

            String recieve = getIntent().getStringExtra("category");
            if(recieve!=null ) {
                 mpost_key = recieve.toLowerCase();
            }else {
                mpost_key="";

            }

                Query query = mref.orderByChild("t_c")
                        .startAt(mpost_key)
                        .endAt(mpost_key + "\uf8ff");
        FirebaseRecyclerAdapter<category, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<category, FriendsViewHolder>(
                category.class,
                R.layout.single_text_layout,
                FriendsViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final category friends, int i) {

 final String key=getRef(i).getKey();
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
                }, 6500);

                if(key!=null){
                    gaya=true;
                    progressrv.setVisibility(View.GONE);
                    rel.setVisibility(View.GONE);
                }

                AllproductsViewHolder.setTitle(friends.getCategory());

                AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        assert key != null;
                        Log.d("allcat",key);

                        Intent productdetailsintent = new Intent (all_cateogories.this,all_brands.class);
                        productdetailsintent.putExtra("category",friends.getCategory());
                        startActivity(productdetailsintent);


                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        };
        rv2.setAdapter(friendsRecyclerViewAdapter);
        }

        showbrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("categories");
                Query query = myref.orderByChild("scategory")
                        .startAt(String.valueOf(charSequence))
                        .endAt(charSequence + "\uf8ff");
                FirebaseRecyclerAdapter<category, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<category, FriendsViewHolder>(
                        category.class,
                        R.layout.single_text_layout,
                        FriendsViewHolder.class,
                        query
                ) {
                    @Override
                    protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final category friends, int i) {


                        AllproductsViewHolder.setTitle(friends.getCategory());

                        AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Intent productdetailsintent = new Intent(all_cateogories.this, all_brands.class);
                                productdetailsintent.putExtra("category", friends.getCategory());
                                startActivity(productdetailsintent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }

                };
                rv2.setAdapter(friendsRecyclerViewAdapter);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



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
        }, 7500);








      

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
