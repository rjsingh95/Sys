package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.HashMap;

import customfonts.MyTextView;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;


public class ProductDetailActivity extends AppCompatActivity  implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener{
    SliderLayout mDemoSlider;

    int counter = 0;

    ScaleRatingBar ratingbar;

    private RecyclerView rv2;
    private ArrayList<BeanlistPeopleViewed> Bean1;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private Context context;
    private String mpostid;

private String mpost_key =null;
private DatabaseReference ref;
private TextView mtitle;
private ImageView mimage;
private TextView mdetails;
private MyTextView find;
    CustomImageSlider sliderLayout ;

    HashMap<String, String> HashMapForURL ;
    private PhotoViewAttacher mAttacher;
    private TextView price;
    private RelativeLayout rel;
    private TextView unvailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.activity_productdetail);
        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        checkLocationPermission();
        TextView review=(TextView)findViewById(R.id.reviews)
                ;
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProductDetailActivity.this, "Not Available", Toast.LENGTH_SHORT).show();
            }
        });

        toolbartitle.setText("PRODUCT DETAILS");


        find =(MyTextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.find);

        unvailable =(TextView)findViewById(R.id.unavailabale);
        ratingbar =(ScaleRatingBar)findViewById(R.id.ratingbar);


        rel=(RelativeLayout)findViewById(R.id.rel);
        mpost_key =getIntent().getStringExtra("product_id");
        ref= FirebaseDatabase.getInstance().getReference().child("products").child(mpost_key);
        String s = ((commomloc) this.getApplication()).getloc();
      
        sliderLayout= (CustomImageSlider) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.pslider);


        mtitle=(TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.product_title);
          mdetails= (TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.product_details);
            price=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.price);
//        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("in_city").child(s).child(mpost_key);
//        database.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                    find.setVisibility(View.VISIBLE);
//                    unvailable.setVisibility(View.GONE);
//
//                }else {
//                    find.setVisibility(View.GONE);
//                    unvailable.setVisibility(View.VISIBLE);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                product_detail_sample obj=new product_detail_sample();
//                obj=dataSnapshot.getValue(product_detail_sample.class);
//
//
//                price.setText("Rs "+obj.getPrice());
//                mdetails.setText(obj.getDescription());
//                mtitle.setText(obj.getTitle());
//
//                String o= String.valueOf(obj.getRating());
//                if(o != null){
//                ratingbar.setRating(obj.getRating()/2);
//
//                }
//                ratingbar=new ScaleRatingBar(ProductDetailActivity.this);
//                ratingbar.setClickable(false);
//                ratingbar.setIsIndicator(false);
//                ratingbar.setClickable(false);
//                ratingbar.setScrollable(false);
//                ratingbar.setClearRatingEnabled(false);
//
//
//
//
//                setGroup(obj.getImage(),obj.getImagee(),obj.getImageee());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });


find.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent productdetailsintent = new Intent (ProductDetailActivity.this,Search_shops.class);
        productdetailsintent.putExtra("product_id",mpost_key);
        startActivity(productdetailsintent);

    }
});








    }
    private String group;

    @Override
    public void onBackPressed() {
        if(rel.getVisibility()==View.VISIBLE){
            rel.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }

    }

    public void setGroup(String p1, String p2, String p3){


        HashMapForURL = new HashMap<String, String>();
        if(p1!=null){
        HashMapForURL.put("CupCake",p1);}
        if(p1!=null) {
            HashMapForURL.put("Donut", p2);
        }
        if(p1!=null){
        HashMapForURL.put("Eclair",p3);}
        final ProgressBar prgress = (ProgressBar) findViewById(R.id.productsrv);

        for(final String name : HashMapForURL.keySet()){


            DefaultSliderView v = new DefaultSliderView(ProductDetailActivity.this);


            v
                    //.description(name)

                    .setScaleType(BaseSliderView.ScaleType.CenterInside)
                    .image(HashMapForURL.get(name)).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    rel.setVisibility(View.VISIBLE);
                    prgress.setVisibility(View.VISIBLE);
                        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

                        Picasso.with(ProductDetailActivity.this).load(HashMapForURL.get(name)).into(photoView, new Callback() {
                            @Override
                            public void onSuccess() {

                                prgress.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        });


                }
            });

            sliderLayout.addSlider(v);
            ImageView iv = (ImageView) v.getView().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.daimajia_slider_image);
             mAttacher = new PhotoViewAttacher(iv);
             mAttacher.update();

        }



        sliderLayout.setDuration(100000);
        sliderLayout.setCustomIndicator(sliderLayout.getPagerIndicator());


        sliderLayout.addOnPageChangeListener(this);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        Log.v("click", "onSliderClick");
        ImageView iv = (ImageView) slider.getView().findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.daimajia_slider_image);

        mAttacher = new PhotoViewAttacher(iv);
        mAttacher.update();
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission")

                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(ProductDetailActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {


                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}
