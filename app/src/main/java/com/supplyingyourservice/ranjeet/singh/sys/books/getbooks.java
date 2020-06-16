package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.Manifest;

import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.BeanclassList;
import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightListView;
import com.supplyingyourservice.ranjeet.singh.sys.ListviewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;
import com.supplyingyourservice.ranjeet.singh.sys.editinfo;

public class getbooks extends AppCompatActivity {

    private ExpandableHeightListView listview;
    //private RecyclerView listview;
    private ArrayList<BeanclassList> Bean;
    private ListviewAdapter baseAdapter;
    private Location currentLocation;

    private static int Update_interval = 5000;

    private LocationRequest mlocationrequest;
    ImageView gridviewicon, listviewicon;
    LocationCallback locationCallback;
    private String mpost_key;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    List<book> list = new ArrayList<book>();

    private String s;
    private CardView card;
    private Boolean mode=true;
    private TextView modetv;
    private FirebaseUser mAuth;
    private DatabaseReference mybooks;
    private int l=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.nearby_shop);
        mAuth= FirebaseAuth.getInstance().getCurrentUser();
        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        toolbartitle.setText("Getting Sellers");


        s = ((commomloc) this.getApplication()).getloc();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mpost_key = getIntent().getStringExtra("product_id");

          listview = (ExpandableHeightListView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shoplistview);

        listview.setLayoutManager(new LinearLayoutManager(getbooks.this));
        Log.d("checkingloc", String.valueOf(1));


        card=(CardView)findViewById(R.id.card) ;
        modetv = (TextView) findViewById(R.id.indoor);
        modetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mode){
                    useoutdoormode();
                    modetv.setText("Use Saved Location");
                    mode=true;
                }else {

                    mode=false;

                    useindoormode();

                    modetv.setText("Get Current Location");
                }


            }
        });


        if(mode) {
            useoutdoormode();

        }else {
            useindoormode();

        }

    }

    private void useoutdoormode() {
        mode=true;
        modetv.setText("Use Saved Location");
        buildLocationCallback();

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            Log.d("checkingloc", String.valueOf(2));

        } else {

            getLocationPermission();
            getDeviceLocation();
            Log.d("checkingloc", String.valueOf(3));
        }
    }

    private void useindoormode() {
        mode=false;
        modetv.setText("Get Current Location");

        DatabaseReference shopref = FirebaseDatabase.getInstance().getReference().child("customer_locations");
        GeoFire geoFire2 = new GeoFire(shopref);
        geoFire2.getLocation(mAuth.getUid(), new com.firebase.geofire.LocationCallback() {
            @Override
            public void onLocationResult(String key, final GeoLocation location) {

                if (location!=null){
                    double lat = location.latitude;
                    double log = location.longitude;
                    Location location1=new Location("");
                    location1.setLatitude(lat);
                    location1.setLongitude(log);
                    currentLocation=location1;
                    if (currentLocation != null) {
                        getClosestDriver();


                    }
                }

                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getbooks.this);
                    builder.setMessage("Save your permanent address and use it to save battery")
                            .setCancelable(false)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    Intent productdetailsintent = new Intent (getbooks.this,editinfo.class);
                                    startActivity(productdetailsintent);

                                }
                            })
                            .setNegativeButton("Get Current Location", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    modetv.setText("Use Saved Location");
                                    mode=true;
                                    buildLocationCallback();

                                    final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                    assert manager != null;
                                    if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                                        buildAlertMessageNoGps();
                                        Log.d("checkingloc", String.valueOf(2));

                                    } else {
                                        getLocationPermission();
                                        getDeviceLocation();
                                        Log.d("checkingloc", String.valueOf(3));
                                    }
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("checkingloc", String.valueOf(4));

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            buildlocationRequest();
            mFusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());
            getLocationPermission();
            getDeviceLocation();

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Use Saved Location", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        useindoormode();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void getDeviceLocation() {
        Log.d("checkingloc", String.valueOf(20));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }


        try {

            if (mLocationPermissionsGranted) {


                if (mFusedLocationProviderClient != null) {
                    mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location locations) {

                            if (locations != null) {
                                currentLocation = locations;
                                getClosestDriver();
                                Log.d("checkingloc", String.valueOf(21));


                            } else {
                                Log.d("checkingloc", String.valueOf(22));

                                //This is what you need:
                                if (ActivityCompat.checkSelfPermission(getbooks.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                                        .checkSelfPermission(getbooks.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                buildLocationCallback();
                                buildlocationRequest();
                                mFusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());
                                if (currentLocation != null) {
                                    getClosestDriver();

                                }

                            }

                        }
                    });


                }
            }

        } catch (SecurityException e) {


        }

    }



    private void buildLocationCallback() {
        Log.d("checkingloc", String.valueOf(24));

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(com.google.android.gms.location.LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    currentLocation = location;
                    Log.d("checkingloc", String.valueOf(25));

                }
                getClosestDriver();
                Log.d("checkingloc", String.valueOf(26));

            }
        };

    }

    private void buildlocationRequest() {
        mlocationrequest = new LocationRequest();

        mlocationrequest.setInterval(Update_interval);

        mlocationrequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

    }


    private void getLocationPermission(){


        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,

                Manifest.permission.ACCESS_COARSE_LOCATION};



        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),

                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Log.d("checkingloc", String.valueOf(8));




            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),

                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

                mLocationPermissionsGranted = true;
                Log.d("checkingloc", String.valueOf(5));




            }else{

                ActivityCompat.requestPermissions(this,

                        permissions,

                        LOCATION_PERMISSION_REQUEST_CODE);
                Log.d("checkingloc", String.valueOf(6));


            }

        }else{

            ActivityCompat.requestPermissions(this,

                    permissions,

                    LOCATION_PERMISSION_REQUEST_CODE);
            Log.d("checkingloc", String.valueOf(7));


        }

    }
    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        mLocationPermissionsGranted = false;


        switch (requestCode) {

            case LOCATION_PERMISSION_REQUEST_CODE: {

                if (grantResults.length > 0) {

                    for (int i = 0; i < grantResults.length; i++) {

                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {

                            mLocationPermissionsGranted = false;

                            Log.d("checkingloc", String.valueOf(10));

                            return;

                        }

                    }
                    mLocationPermissionsGranted = true;
                    Log.d("checkingloc", String.valueOf(11));

                }

            }

        }
    }
    GeoQuery geoQuery;
    private double radius =.5;
    private Boolean driverFound = false;

    private String driverFoundID;
    int total_shops=0;
    ArrayList<String> mylist = new ArrayList<String>();

    private void getClosestDriver(){
        s = ((commomloc) this.getApplication()).getloc();
       
        if(s!=null) {
          

            DatabaseReference driverLocation = FirebaseDatabase.getInstance().getReference().child("book_in_city").child(s).child(mpost_key);

            Log.d("checkingloc", String.valueOf(15));


            GeoFire geoFire = new GeoFire(driverLocation);
            final Boolean founded = false;

            geoQuery = geoFire.queryAtLocation(new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()), radius);
            geoQuery.removeAllListeners();
            geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
                @Override
                public void onKeyEntered(String key, GeoLocation location) {
                    Log.d("radius", String.valueOf(radius));
                    if (mylist.size() < 6) {
                        if (!mylist.contains(key)) {
                            mylist.add(key);
                            FetchMatchInformation(key);
                            total_shops++;

                        }
                    }
                }

                @Override
                public void onGeoQueryReady() {
                    Log.d("checkingloc", String.valueOf(16));

                    if (mylist.size() < 5 && radius < 10) {

                        radius = radius + 1.25;
                        getClosestDriver();


                    } else {
                    }
                }

                @Override
                public void onKeyExited(String key) {

                }

                @Override
                public void onKeyMoved(String key, GeoLocation location) {

                }


                @Override

                public void onGeoQueryError(DatabaseError error) {


                }

            });
        }

    }
    private void FetchMatchInformation(String key) {
        Log.d("whatproblem","notshowing");

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("customers").child(key);
        mybooks=FirebaseDatabase.getInstance().getReference().child("mybooks").child(key).child(mpost_key);
        userDb.addListenerForSingleValueEvent(new ValueEventListener() {


            public String address;
            public String phone;
            public String price,quality;

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    final String shopid=  dataSnapshot.getRef().getKey();


                    String name = "";
                    String city="location";
                    String profileImageUrl = "";

                    if(dataSnapshot.hasChild("name")){
                        card.setVisibility(View.GONE);

                        name = dataSnapshot.child("name").getValue().toString();

                    }

                    if(dataSnapshot.hasChild("customer_thumb")){
                        card.setVisibility(View.GONE);

                        profileImageUrl = dataSnapshot.child("customer_thumb").getValue().toString();

                    }
                    if(dataSnapshot.hasChild("phone")){

                        phone = dataSnapshot.child("phone").getValue().toString();

                    }
                    if(dataSnapshot.hasChild("address")){

                        address = dataSnapshot.child("address").getValue().toString();

                    }

                    final String finalProfileImageUrl = profileImageUrl;
                    final String finalName = name;
                    mybooks.addListenerForSingleValueEvent(new ValueEventListener() {
                        public String time;

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild("price")){
                                price=dataSnapshot.child("price").getValue().toString();}
                            if(dataSnapshot.hasChild("quality")){
                                quality=dataSnapshot.child("quality").getValue().toString();

                            }
                            if(dataSnapshot.hasChild("time")){
                                time=dataSnapshot.child("time").getValue().toString();

                            }

                            book obj=new book(price, finalProfileImageUrl, finalName,quality,phone,time,shopid,address);

                            if(!list.contains(obj)) {
                                list.add(obj);
                            }
                            bookadapter baseAdapter1 = new bookadapter(list, getApplicationContext(), mpost_key, s, currentLocation,getbooks.this);
                            listview.setAdapter(baseAdapter1);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });







                }


            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        if(geoQuery!=null) {
            geoQuery.removeAllListeners();
        }
    }
}

