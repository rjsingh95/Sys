package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;

public class Search_shops extends AppCompatActivity {

    private ExpandableHeightListView listview;
    //private RecyclerView listview;
    private ArrayList<BeanclassList> Bean;
    private ListviewAdapter baseAdapter;
    private Location currentLocation;
    private DatabaseReference mproductdatabase;
    private DatabaseReference mshopdatabase;
    private static int Update_interval = 5000;

    ArrayList<GeoLocation> geo = new ArrayList<GeoLocation>();

    private LocationRequest mlocationrequest;
    ImageView gridviewicon, listviewicon;
    LocationCallback locationCallback;
    private String mpost_key;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;

    private FusedLocationProviderClient mFusedLocationProviderClient;


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    List<shopkeeper_info> list = new ArrayList<>();
    private shopadapter baseAdapter1;
    private String s;
    private CardView card;
    private Boolean mode=true;
    private TextView modetv;
    private FirebaseUser mAuth;
    private Context context=Search_shops.this;
    private ImageView gps;
    private Animation outAnimation;
    private Animation inAnimation;
    private Animation outAnimation2;
    private ProgressBar pr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.nearby_shop);
        mAuth= FirebaseAuth.getInstance().getCurrentUser();


     gps=(ImageView)findViewById(R.id.gps);

        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        toolbartitle.setText("Nearby Shops");




// Create the Animation objects.
      outAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        outAnimation2 = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        inAnimation = AnimationUtils.loadAnimation(context, R.anim.fade_out);



        s = ((commomloc) this.getApplication()).getloc();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mpost_key = getIntent().getStringExtra("product_id");
if(mpost_key!=null){
        mproductdatabase = FirebaseDatabase.getInstance().getReference().child("in_city").child(s).child(mpost_key);}
        else {
    finish();
        }
        mshopdatabase = FirebaseDatabase.getInstance().getReference().child("users");
        listview = (ExpandableHeightListView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shoplistview);

        listview.setLayoutManager(new LinearLayoutManager(Search_shops.this));
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
        outAnimation.setAnimationListener(new Animation.AnimationListener(){

            // Other callback methods omitted for clarity.

            @Override
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationEnd(Animation animation){

                // Modify the resource of the ImageButton
                gps.setImageResource(R.drawable.home_silhouette);

                // Create the new Animation to apply to the ImageButton.
                gps.startAnimation(inAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gps.startAnimation(outAnimation);

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
        outAnimation2.setAnimationListener(new Animation.AnimationListener(){

            // Other callback methods omitted for clarity.

            @Override
            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationEnd(Animation animation){

                // Modify the resource of the ImageButton
                gps.setImageResource(R.drawable.location);

                // Create the new Animation to apply to the ImageButton.
                gps.startAnimation(inAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        gps.startAnimation(outAnimation2);

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
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Search_shops.this);
                    builder.setMessage("Save your permanent address and use it to save battery")
                            .setCancelable(false)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    Intent productdetailsintent = new Intent (Search_shops.this,editinfo.class);
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
                                if (ActivityCompat.checkSelfPermission(Search_shops.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                                        .checkSelfPermission(Search_shops.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                buildLocationCallback();
                                buildlocationRequest();
                                mFusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //Do something after 20 seconds

                                        if(currentLocation!=null){

                                            getClosestDriver();

                                        }
                                        else{
                                            buildLocationCallback();
                                            buildlocationRequest();
                                            mFusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());

                                            handler.postDelayed(this, 1000);
                                        }
                                    }
                                }, 2000);


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

        mlocationrequest.setPriority(LocationRequest.PRIORITY_LOW_POWER);

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

        DatabaseReference driverLocation = FirebaseDatabase.getInstance().getReference().child("in_city").child(s).child(mpost_key);

        Log.d("checkingloc", String.valueOf(15));


        GeoFire geoFire = new GeoFire(driverLocation);
        final Boolean founded=false;

        geoQuery = geoFire.queryAtLocation(new GeoLocation(currentLocation.getLatitude(), currentLocation.getLongitude()), radius);
        geoQuery.removeAllListeners();
        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, GeoLocation location) {
                Log.d("radius", String.valueOf(radius));
                if(mylist.size()<6){
                if (!mylist.contains(key) ){

                    mylist.add(key);
                        FetchMatchInformation(key,location);
                        total_shops++;

                }}



            }
            @Override
            public void onGeoQueryReady() {
                Log.d("checkingloc", String.valueOf(16));

                if (mylist.size()<5&& radius<10)
                {

                    radius=radius+1.25;
                    getClosestDriver();



                }
                else{
               }
            }@Override
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
    private void FetchMatchInformation(String key, final GeoLocation loc) {

        DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("users").child(key);

        userDb.addValueEventListener(new ValueEventListener() {



            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                     String shopid=  dataSnapshot.getRef().getKey();

                    String name = "";
                    String city="location";
                    String profileImageUrl = "";

                    if(dataSnapshot.hasChild("shop_name")){
                        card.setVisibility(View.GONE);

                        name = dataSnapshot.child("shop_name").getValue().toString();

                    }



                 if(dataSnapshot.hasChild("photo")){
                        card.setVisibility(View.GONE);

                        profileImageUrl = dataSnapshot.child("photo").getValue().toString();

                    }
                    if(dataSnapshot.hasChild("city")){
                        card.setVisibility(View.GONE);

                        city = dataSnapshot.child("city").getValue().toString();

                    }





                    shopkeeper_info obj = new shopkeeper_info(name,profileImageUrl,shopid,city,loc);
                   if(!list.contains(obj)) {
                       list.add(obj);
                   }
                }

               baseAdapter1 = new shopadapter(list,getApplicationContext(),mpost_key,s,currentLocation);
                listview.setAdapter(baseAdapter1);

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

