package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class shopdetails extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,ViewPagerEx.OnPageChangeListener{

    private String mpost_key;
    private TextView adressstv;
    private Query ref;
    private TextView shoptitle;
    private TextView to;
    private TextView price1;
    private TextView price2;
    private TextView pricetype;
    private TextView pricetypetv;
    private TextView promisetv;
    private TextView detailstv;
    private String mproduct_key;
    private DatabaseReference myref;
    SliderLayout sliderLayout ;
   private String photo ;
    private String photo2 ;
   private String photo3 ;
    private String i1;
    private TextView delivery;
    private ImageView send;
    private DatabaseReference myref2;
    private TextView timmings;
    private String Shop_name;
    private RelativeLayout rel;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.shopdetails);

        Bundle extras = getIntent().getExtras();
        mpost_key = extras.getString("shop_id");
        mproduct_key = extras.getString("product_id");
        final double latitude = extras.getDouble("lat");
        final double longitude = extras.getDouble("log");
        ImageView shoploc=(ImageView)findViewById(R.id.dir);
        shoploc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference shopref = FirebaseDatabase.getInstance().getReference().child("shop_locations");
                GeoFire geoFire2 = new GeoFire(shopref);


                geoFire2.getLocation(mpost_key, new com.firebase.geofire.LocationCallback() {
                    @Override
                    public void onLocationResult(String key, final GeoLocation location) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,

                                Uri.parse("http://maps.google.com/maps?saddr="+latitude+","+longitude+"&daddr="+location.latitude+","+location.longitude));
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
               
              //  Intent productdetailsintent = new Intent(shopdetails.this,shop_directions.class);
                //Bundle extras = new Bundle();
        //    extras.putString("shop_id",mpost_key);
          //      extras.putDouble("lat",latitude);
            ///    extras.putDouble("log",longitude);
               // productdetailsintent.putExtras(extras);
                //startActivity(productdetailsintent);


            }
        });

        ImageView shoplocaion=(ImageView)findViewById(R.id.shoploc);
        shoplocaion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent(shopdetails.this,shop_loc.class);
                Bundle extras = new Bundle();
                extras.putString("shop_id",mpost_key);

                productdetailsintent.putExtras(extras);
                startActivity(productdetailsintent);


            }
        });
        ImageView call=(ImageView)findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone!=null) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(shopdetails.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions(shopdetails.this,
                                    new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
                                    10);
                            return;
                        }
                        startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {
                        Log.e("Calling a Phone Number", "Call failed", activityException);
                    }

                }


            }
        });



        ref= FirebaseDatabase.getInstance().getReference().child("users").child(mpost_key);
        myref= FirebaseDatabase.getInstance().getReference().child("shop_products").child(mpost_key).child(mproduct_key);
        Button buy = (Button) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent(shopdetails.this,placeorder.class);
                Bundle extras = new Bundle();
                extras.putString("shop_id",mpost_key);
                extras.putString("product_id",mproduct_key);
                extras.putString("shop_name",Shop_name);
                extras.putString("number",phone);
                productdetailsintent.putExtras(extras);
                productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                 startActivity(productdetailsintent);
            }
        });


     adressstv=(TextView)findViewById(R.id.shop_addresss);


        timmings=(TextView)findViewById(R.id.timmings);
        shoptitle=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shoptitle);
        to=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tv2);
        price1=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvp1);
        price2=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvp3);
        pricetypetv=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.pricetype);

         detailstv=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.detailstv);
        sliderLayout= (SliderLayout) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shopslider);
        delivery=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.delivery);
        send=(ImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.send);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        myref2= FirebaseDatabase.getInstance().getReference().child("customer_locations");
        delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myref2.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){




                            Intent productdetailsintent = new Intent(shopdetails.this,placeorder_delivery.class);
                            Bundle extras = new Bundle();
                            extras.putString("shop_id",mpost_key);
                            extras.putString("product_id",mproduct_key);
                            productdetailsintent.putExtras(extras);


                            startActivity(productdetailsintent);

                        }else {

                            Intent productdetailsintent = new Intent (shopdetails.this,editinfo.class);
                            productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);


                            startActivity(productdetailsintent);
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        });

        rel=(RelativeLayout)findViewById(R.id.rel);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (shopdetails.this,ChatActivity.class);
                productdetailsintent.putExtra("shop_id",mpost_key);
                startActivity(productdetailsintent);

            }
        });

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pricetype ="none";
                if (dataSnapshot.hasChild("pricetype")) {
                    pricetype = dataSnapshot.child("pricetype").getValue().toString();
                }


                if (pricetype.equals("range")) {
                    pricetypetv.setText("Range");
                    String price = dataSnapshot.child("price").getValue().toString();
                    String pricesecond = dataSnapshot.child("price2").getValue().toString();
                    price2.setVisibility(View.VISIBLE);
                    to.setVisibility(View.VISIBLE);
                    price1.setText(price);
                    price2.setText(pricesecond);

                }



                if (pricetype.equals("fixed")){

                    String price = dataSnapshot.child("price").getValue().toString();
                    price1.setText(price);
                }


                if(pricetype.equals("none")){

                    price1.setText("Unspecified");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


         shop_detail_sample obj=new shop_detail_sample();
         obj=dataSnapshot.getValue(shop_detail_sample.class);


                assert obj != null;
                shoptitle.setText(obj.getShop_name());







                adressstv.setText(obj.getShop_address());
                timmings.setText(obj.getShop_timmings());
                detailstv.setText(obj.getDescription());
                Shop_name=obj.getShop_name();







                if(obj.getDelivery()!=null) {
                    if (obj.getDelivery().equals("off")) {
                        delivery.setVisibility(View.GONE);
                    }

                }




                 phone=obj.getPhone_number();




                   setGroup(obj.getPhoto(),obj.getPhotoo(),obj.getPhotooo());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    HashMap<String, String> HashMapForURL ;
    private String group,i2,i3;
    public void setGroup(String i1,String i2,String i3){

        this.i1=i1;
        this.i2=i2;
        this.i3=i3;
        HashMapForURL = new HashMap<String, String>();
        Log.d("photog",i1+" "+i2+" "+i3);
        if(i1!=null){
        HashMapForURL.put("",i1);}
        if(i2!=null){
        HashMapForURL.put("Donut",i2);}
        if(i3!=null){
        HashMapForURL.put("Eclair",i3);}
        final ProgressBar prgress = (ProgressBar) findViewById(R.id.productsrv);


        for(final String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(this);

            textSliderView
                    //.description(name)

                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .image(HashMapForURL.get(name)).setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    rel.setVisibility(View.VISIBLE);
                    prgress.setVisibility(View.VISIBLE);
                    PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

                    Picasso.with(shopdetails.this).load(HashMapForURL.get(name)).into(photoView, new Callback() {
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

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);

        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(100000);

        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

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

    @Override
    public void onBackPressed() {
        if(rel.getVisibility()==View.VISIBLE){
            rel.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }

    }

}
