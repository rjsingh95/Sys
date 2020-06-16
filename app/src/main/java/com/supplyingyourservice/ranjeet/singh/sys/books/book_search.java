package com.supplyingyourservice.ranjeet.singh.sys.books;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.preference.PreferenceManager;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;

import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;

import com.google.firebase.database.ValueEventListener;

import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightGridView;
import com.supplyingyourservice.ranjeet.singh.sys.R;


import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.supplyingyourservice.ranjeet.singh.sys.elastic.bookobject;
import com.supplyingyourservice.ranjeet.singh.sys.elastic.booksearchapi;
import com.supplyingyourservice.ranjeet.singh.sys.elastic.hitlis;
import okhttp3.Credentials;

import retrofit2.Call;

import retrofit2.Callback;

import retrofit2.Response;

import retrofit2.Retrofit;

import retrofit2.converter.gson.GsonConverterFactory;





public class book_search extends AppCompatActivity{



    private static final String TAG = "SearchFragment";






    //vars

    private String mElasticSearchPassword;


    private ArrayList<book_sample> mPosts;

    private ExpandableHeightGridView mRecyclerView;

    private bookgridadapter mAdapter;
    private TextView refine;

    List<String > suggestedlist =new ArrayList<>();
    EditText materialsearch;
    private String mbrand, mcategory,mtype;
    private RecyclerView rv2;
    private EditText showbrand;
    private ProgressBar progressrv;
    private RelativeLayout rel;
    private String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.booksearch);



        final TextView addbook=(TextView)findViewById(R.id.addbook);


        addbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (book_search.this, addabook.class);

                startActivity(productdetailsintent);


            }
        });
        mRecyclerView = (ExpandableHeightGridView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.gridview);

        getElasticSearchPassword();

        materialsearch=(EditText)findViewById(R.id.searchBar);
        materialsearch.setHint("Search Books");

        materialsearch.addTextChangedListener(new TextWatcher() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                View view = book_search.this.getCurrentFocus();
     addbook.setVisibility(View.VISIBLE);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }






    @Override
    public void onResume() {
        super.onResume();
       // getFilters();
    }
    private void getFilters(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mbrand = preferences.getString("brand", "");
        mcategory = preferences.getString("category", "");
        mtype = preferences.getString("type", "");


    }

    private void setupPostsList(){


        mRecyclerView.setExpanded(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(book_search.this, 2);

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new bookgridadapter(book_search.this, mPosts);
        mRecyclerView.setAdapter(mAdapter);


    }


    private void init(CharSequence s) {


        final CharSequence SearchText = s;
        mPosts = new ArrayList<book_sample>();



        Retrofit retrofit = new Retrofit.Builder()

                .baseUrl(url)

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        booksearchapi searchAPI = retrofit.create(booksearchapi.class);


        HashMap<String, String> headerMap = new HashMap<String, String>();

        headerMap.put("Authorization", Credentials.basic("user", mElasticSearchPassword));


        String searchString = "";


        if (!SearchText.equals("")) {

            searchString = searchString + SearchText + "*";

        }



        Call<bookobject> call = searchAPI.search(headerMap, "AND", searchString);


        call.enqueue(new Callback<bookobject>() {

            @Override

            public void onResponse(Call<bookobject> call, Response<bookobject> response) {


                hitlis hitsList = new hitlis();

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

                       // Log.d(TAG, "onResponse: data: " + hitsList.getPostIndex().get(i).getPost().toString());

                        mPosts.add(hitsList.getPostIndex().get(i).getBook());

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
            public void onFailure(Call<bookobject> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

                Toast.makeText(book_search.this, "search failed", Toast.LENGTH_SHORT).show();

            }




        });



    }









    private void getElasticSearchPassword(){

        Log.d(TAG, "getElasticSearchPassword: retrieving elasticsearch password.");



        Query query = FirebaseDatabase.getInstance().getReference()

                .child(getString(com.supplyingyourservice.ranjeet.singh.sys.R.string.node_elasticsearch))

                .orderByValue();



        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {


                mElasticSearchPassword = dataSnapshot.child("password").getValue().toString();
                url=dataSnapshot.child("url_books").getValue().toString();

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