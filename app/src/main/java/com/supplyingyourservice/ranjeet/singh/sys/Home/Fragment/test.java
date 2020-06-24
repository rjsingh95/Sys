package com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightListView;
import com.supplyingyourservice.ranjeet.singh.sys.GetTimeAgo;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;
import com.supplyingyourservice.ranjeet.singh.sys.order;
import com.supplyingyourservice.ranjeet.singh.sys.retrofit.repoadapter;
import com.supplyingyourservice.ranjeet.singh.sys.retrofit.response;
import com.supplyingyourservice.ranjeet.singh.sys.retrofit.service;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class test extends Fragment {


    private RecyclerView rv;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private static final int ACTIVITY_NUM = 2;
     private EditText fpy;
    private String phone_number;
    ArrayList<JsonObject> adslist=new ArrayList<>();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.testrv, container, false);

        rv=(RecyclerView)root.findViewById(R.id.rv);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(mLayoutManager);
        fetchData();

return root;
    }
    private void fetchData() {
        String a;

        a="https://worker-22dd9.firebaseio.com/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(a)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service api = retrofit.create(service.class);
        Call<JsonObject> call = api.getproducts();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
         //   adslist=response.body();
              //  adslist=response.body().getAsJsonObject();
                Gson gson = new Gson();
//                response user= gson.fromJson(response.body().getAsJsonArray(), response.class);

                assert response.body() != null;
        //        Log.d("chack4",response.body().getAsJsonArray().get(0).toString());

//                repoadapter madapter=new repoadapter(getActivity(),adslist);
//                rv.setAdapter(madapter);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("chack4","failed response");

            }



        });
    }
    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        private final TextView shop_name;
        private final TextView mordertime;
        private final TextView fprice;
        private final Button make_offer;
        private final Button accept;
        private final CardView waiting;
        private final TextView duration;
        private final Button call,notsubcriber,yoursubcriber;
        private final Button completed;
        private final Button cancel;
        private final Button remove;

        private final ProgressBar status_progress;
        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            completed=(Button)itemView.findViewById(R.id.complete);
            shop_name=(TextView)itemView.findViewById(R.id.shop_name);
            mordertime=(TextView)itemView.findViewById(R.id.ordertime2);
            fprice=(TextView)itemView.findViewById(R.id.fprice);
              make_offer=(Button)itemView.findViewById(R.id.make_offer);
            accept=(Button)itemView.findViewById(R.id.accept);
            waiting=(CardView)itemView.findViewById(R.id.waiting);
            duration=(TextView)itemView.findViewById(R.id.date);
            yoursubcriber=(Button)itemView.findViewById(R.id.yoursubcriber);
            call =(Button)itemView.findViewById(R.id.call);
            notsubcriber=(Button)itemView.findViewById(R.id.notsubscriber);
            remove=(Button)itemView.findViewById(R.id.removed);
            cancel=(Button)itemView.findViewById(R.id.cancel);

            status_progress=(ProgressBar)itemView.findViewById(R.id.progressBar1);

            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.p_name);
            userNameView.setText(name);

        }

        public void setImage(String thumb_image, Context ctx){

            ImageView userImageView = (ImageView) mView.findViewById(R.id.image);
            Picasso.with(ctx).load(thumb_image).into(userImageView);

        }
    }




    
}