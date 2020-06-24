package com.supplyingyourservice.ranjeet.singh.sys.elastic;

/**
 * Created by Ranjeet on 18-03-2018.
 */


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import
        android.os.Bundle;

import android.preference.PreferenceManager;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
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

import android.widget.Toast;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

import com.google.firebase.database.ValueEventListener;

import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightGridView;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.addprod;


import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.supplyingyourservice.ranjeet.singh.sys.all_cateogories;
import com.supplyingyourservice.ranjeet.singh.sys.brand;
import com.supplyingyourservice.ranjeet.singh.sys.model.category;
import okhttp3.Credentials;

import retrofit2.Call;

import retrofit2.Callback;

import retrofit2.Response;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;





public class SearchActivity extends AppCompatActivity{



    private static final String TAG = "SearchFragment";






    //vars

    private String mElasticSearchPassword;


    private ArrayList<addprod> mPosts;

    private ExpandableHeightGridView mRecyclerView;

    private GridviewAdapter mAdapter;
    private TextView refine;

    List<String > suggestedlist =new ArrayList<>();
    EditText materialsearch;
    private String mbrand, mcategory,mtype;
    private RecyclerView rv2;
    private EditText showbrand;
    private ProgressBar progressrv;
    private RelativeLayout rel;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.activity_productlist2);
        rel=(RelativeLayout)findViewById(R.id.rel);
        TextView filter = (TextView) findViewById(R.id.filter);
        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadcategory();
                rel.setVisibility(View.VISIBLE);
            }
        });
        TextView browse = (TextView) findViewById(R.id.browse);
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent productdetailsintent = new Intent(SearchActivity.this, all_cateogories.class);
                startActivity(productdetailsintent);

            }
        });


        mRecyclerView = (ExpandableHeightGridView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.gridview);

        getElasticSearchPassword();

        materialsearch=(EditText)findViewById(R.id.searchBar);
        materialsearch.setHint("Search Products");

        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("products");
        mref.orderByChild("level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postnapshot:dataSnapshot.getChildren()){
                    if (postnapshot.hasChild("title")){
                        String suggest=postnapshot.child("title").getValue().toString();
                        suggestedlist.add(suggest);
                       // materialsearch.setLastSuggestions(suggestedlist);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

         materialsearch.addTextChangedListener(new TextWatcher() {

             @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                 loadsuggests();
                 List<String>suggest=new ArrayList<>();
                 for(String search:suggestedlist){

                     if (search.toLowerCase().contains(materialsearch.getText()))
                         suggest.add(search);

                 }
                 View view = SearchActivity.this.getCurrentFocus();


             }


             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                 loadsuggests();
                 List<String>suggest=new ArrayList<>();
                 for(String search:suggestedlist){

                     if (search.toLowerCase().contains(materialsearch.getText()))
                         suggest.add(search);

                 }

             }

             @Override
             public void afterTextChanged(Editable editable) {


             }
         });

progressrv=(ProgressBar)findViewById(R.id.productsrv);
    }

    private void loadcategory() {


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rv2=(RecyclerView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.recyclerView);
        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("categories");
        final List<String> list = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager1);
        showbrand= (EditText) findViewById(R.id.showbrand);
        showbrand.setHint(" Search Categories..");

        showbrand.clearFocus();
        showbrand.requestFocusFromTouch();
        showbrand.clearFocus();
        String j=showbrand.getText().toString();
        int num =0;
        String mpost_key;
        if(j.equalsIgnoreCase("")) {

            String recieve = getIntent().getStringExtra("type");
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
                    com.supplyingyourservice.ranjeet.singh.sys.R.layout.single_title,
                  FriendsViewHolder.class,
                    query
            ) {
                @Override
                protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final category friends, int i) {


                    String key=getRef(i).getKey();
                    if(key!=null){
                        progressrv.setVisibility(View.GONE);
                    }

                    AllproductsViewHolder.setTitle(friends.getCategory());

                    AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           mcategory=friends.getCategory();
loadbrand(friends.getCategory().toLowerCase());
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }

            };
            rv2.setAdapter(friendsRecyclerViewAdapter);}



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
                        R.layout.single_title,
               FriendsViewHolder.class,
                        query
                ) {

                    @Override
                    protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final category friends, int i) {


                        AllproductsViewHolder.setTitle(friends.getCategory());
                        String key=getRef(i).getKey();
                        if(key!=null){
                            progressrv.setVisibility(View.GONE);
                        }


                        AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                               mcategory=friends.getCategory();
                                loadbrand(friends.getCategory().toLowerCase());
                                progressrv.setVisibility(View.VISIBLE);


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


    }

    private void loadbrand(String category) {
        EditText search=(EditText)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.showbrand);
        search.setHint(" Search Brands..");

        final DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("brands");

        DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("brand");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(SearchActivity.this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager);
        Query query = myref.orderByChild("b_c")
                .startAt(category)
                .endAt(category + "\uf8ff");
        FirebaseRecyclerAdapter<brand, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<brand, FriendsViewHolder>(
                brand.class,
                R.layout.single_title,
                FriendsViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final brand friends, int i) {


                AllproductsViewHolder.setTitle(friends.getBrand_show());
                String key=getRef(i).getKey();
                if(key!=null){
                    progressrv.setVisibility(View.GONE);
                }

                AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mbrand=friends.getBrand();
                        rel.setVisibility(View.GONE);





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




        showbrand.requestFocusFromTouch();
        showbrand.clearFocus();


        final String finalCategory = category;
        final String finalCategory1 = category;
        showbrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Query query = mref.orderByChild("b_c")
                        .startAt(finalCategory.toLowerCase() +String.valueOf(charSequence).toLowerCase())
                        .endAt(charSequence.toString().toLowerCase() + "\uf8ff");
                FirebaseRecyclerAdapter<brand,FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<brand, FriendsViewHolder>(
                        brand.class,
                        R.layout.single_title,
                   FriendsViewHolder.class,
                        query
                ) {
                    @Override
                    protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final brand friends, int i) {


                        final String brand = friends.getBrand_show();


                        AllproductsViewHolder.setTitle(brand);

                        AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                mbrand=friends.getBrand();
                                rel.setVisibility(View.GONE);


                            }
                        });




                    }
                };
                rv2.setAdapter(friendsRecyclerViewAdapter);

            }




            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                Query query = mref.orderByChild("b_c")
                        .startAt(finalCategory1 +String.valueOf(charSequence))
                        .endAt(charSequence + "\uf8ff");
                FirebaseRecyclerAdapter<brand, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<brand,FriendsViewHolder>(
                        brand.class,
                        R.layout.single_title,
                     FriendsViewHolder.class,
                        query
                ) {
                    @Override
                    protected void populateViewHolder(final FriendsViewHolder AllproductsViewHolder, final brand friends, int i) {


                        final String brand = friends.getBrand_show();


                        AllproductsViewHolder.setTitle(brand);


                        AllproductsViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                mbrand=friends.getBrand();
                                rel.setVisibility(View.GONE);


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

    private void loadsuggests() {
        DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("products");
        mref.orderByChild("level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postnapshot:dataSnapshot.getChildren()){
            if (postnapshot.hasChild("title")){
                    String suggest=postnapshot.child("title").getValue().toString();
                    suggestedlist.add(suggest);
            }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(rel.getVisibility()==View.VISIBLE){
         rel.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getFilters();
    }
    private void getFilters(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mbrand = preferences.getString("brand", "");
        mcategory = preferences.getString("category", "");
        mtype = preferences.getString("type", "");


    }

    private void setupPostsList(){


        mRecyclerView.setExpanded(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new GridviewAdapter(SearchActivity.this, mPosts,SearchActivity.this);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    private void init(CharSequence s) {

        if (mElasticSearchPassword != null) {
            final CharSequence SearchText = s;
            mPosts = new ArrayList<addprod>();


            Retrofit retrofit = new Retrofit.Builder()

                    .baseUrl(url)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            ElasticSearchAPI searchAPI = retrofit.create(ElasticSearchAPI.class);


            HashMap<String, String> headerMap = new HashMap<String, String>();

            headerMap.put("Authorization", Credentials.basic("user", mElasticSearchPassword));


            String searchString = "";


            if (!SearchText.equals("")) {

                searchString = searchString + SearchText + "*";

            }
            if (!mbrand.equals("")) {
                searchString = searchString + " brand:" + mbrand;
            }
            if (!mcategory.equals("")) {
                searchString = searchString + " category:" + mcategory;
            }


            Call<HitsObject> call = searchAPI.search(headerMap, "AND", searchString);


            call.enqueue(new Callback<HitsObject>() {

                @Override

                public void onResponse(Call<HitsObject> call, Response<HitsObject> response) {


                    HitsList hitsList = new HitsList();

                    String jsonResponse = "";

                    try {

                        Log.d(TAG, "onResponse: server response: " + response.toString());


                        if (response.isSuccessful()) {

                            hitsList = response.body().getHits();

                        } else {

                            jsonResponse = response.errorBody().string();

                        }


                        Log.d(TAG, "onResponse: hits: " + hitsList);


                        for (int i = 0; i < hitsList.getPostIndex().size(); i++) {

                            Log.d(TAG, "onResponse: data: " + hitsList.getPostIndex().get(i).getPost().toString());

                            mPosts.add(hitsList.getPostIndex().get(i).getPost());

                        }


                        Log.d(TAG, "onResponse: size: " + mPosts.size());

                        //setup the list of posts

                        setupPostsList();


                    } catch (NullPointerException e) {

                        Log.e(TAG, "onResponse: NullPointerException: " + e.getMessage());

                    } catch (IndexOutOfBoundsException e) {

                        Log.e(TAG, "onResponse: IndexOutOfBoundsException: " + e.getMessage());

                    } catch (IOException e) {

                        Log.e(TAG, "onResponse: IOException: " + e.getMessage());

                    }

                }


                @Override

                public void onFailure(Call<HitsObject> call, Throwable t) {

                    Log.e(TAG, "onFailure: " + t.getMessage());

                    Toast.makeText(SearchActivity.this, "search failed", Toast.LENGTH_SHORT).show();

                }

            });


        }

    else {


            Toast.makeText(SearchActivity.this, "Internet Slow", Toast.LENGTH_SHORT).show();
        }
    }






    private void getElasticSearchPassword(){

        Log.d(TAG, "getElasticSearchPassword: retrieving elasticsearch password.");



        Query query = FirebaseDatabase.getInstance().getReference()

                .child(getString(com.supplyingyourservice.ranjeet.singh.sys.R.string.node_elasticsearch))

                .orderByValue();



        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                mElasticSearchPassword  = dataSnapshot.child("password").getValue().toString();

                url=dataSnapshot.child("url_products").getValue().toString();
            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });

    }
    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.text_layout);
            userNameView.setText(name);

        }

    }






}