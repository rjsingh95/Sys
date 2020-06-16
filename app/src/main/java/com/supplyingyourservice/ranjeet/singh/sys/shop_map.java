package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.Manifest;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class shop_map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int Permission_rq = 5000;
    private static final int playservice_rq = 5001;
    private static int Update_interval = 5000;
    private static int Fastest_interval = 3000;
    private static int dislpacement = 50;
    DatabaseReference shopref;
    GeoFire geoFire,geoFire2;
    GeoFire geoFire3;
    private String loc;

    private LocationRequest mlocationrequest;
    LocationCallback locationCallback;
    Marker mcurrent;
    private Location mlastlocation;
    Switch locaton_switch;
    SupportMapFragment mapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteFragment autocompleteFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_map);
         autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        final CardView card_search=(CardView) findViewById(R.id.cardsearch);



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
               
               LatLng location2 =  place.getLatLng();
                mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        .position(new LatLng(location2.latitude, location2.longitude))
                        .title("Edit Your Shop Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location2.latitude, location2.longitude), 18.0f));


            }

            @Override
            public void onError(Status status) {
               
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locaton_switch = (Switch) findViewById(R.id.mapswitch);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        shopref = FirebaseDatabase.getInstance().getReference("customer_locations");
        geoFire = new GeoFire(shopref);
        final ImageView ileft=(ImageView)findViewById(R.id.left) ;
        final ImageView iright=(ImageView)findViewById(R.id.right) ;
        final TextView tvleft=(TextView)findViewById(R.id.tvleft);
        final TextView tvright=(TextView)findViewById(R.id.tvright);


        tvright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locaton_switch.setChecked(true);
            }
        });


        tvleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locaton_switch.setChecked(false);

            }
        });

        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        assert manager != null;
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            Log.d("checkingloc", String.valueOf(2));

        }
        if(!locaton_switch.isChecked()){
            ileft.setImageAlpha(30);
            tvleft.setAlpha(.3f);
            iright.setImageAlpha(120);
            tvright.setAlpha(1f);


        }
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("IN")
                .build();

        autocompleteFragment.setFilter(typeFilter);





        locaton_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if (isChecked) {


                    iright.setImageAlpha(30);
                    tvright.setAlpha(.3f);
                    ileft.setImageAlpha(200);
                    tvleft.setAlpha(1f);
                    card_search.setVisibility(View.VISIBLE);

                    if (ActivityCompat.checkSelfPermission(shop_map.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(shop_map.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    buildLocationCallback();
                    buildlocationRequest();

                    fusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());

                    displaylocation();
                   

                } else {
                    card_search.setVisibility(View.GONE);



                    ileft.setImageAlpha(30);
                    tvleft.setAlpha(.3f);
                    iright.setImageAlpha(120);
                    tvright.setAlpha(1f);



                    LatLng location=mcurrent.getPosition();
                    final double latitude =location.latitude;

                    final double longitude = location.longitude;
                    final String mAuth=FirebaseAuth.getInstance().getCurrentUser().getUid();



                    final DatabaseReference myref3 = FirebaseDatabase.getInstance().getReference().child("customer_locations");

                    geoFire3 = new GeoFire(myref3);
                    geoFire3.setLocation(mAuth, new GeoLocation(latitude, longitude), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (mcurrent != null)
                                mcurrent.remove();


                            mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop))
                                    .position(new LatLng(latitude, longitude))
                                    .title("Home"));
                            onMarkerClick(mcurrent);

                        }
                    });



                }
            }

        });

        setUpLocation();


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
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void setUpLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, Permission_rq);
        } else {
            buildlocationRequest();
            buildLocationCallback();


        }

    }

    private void buildLocationCallback() {

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(com.google.android.gms.location.LocationResult locationResult) {
                for (Location location : locationResult.getLocations()) {
                    mlastlocation = location;
                    autocompleteFragment.setBoundsBias(new LatLngBounds(
                            new LatLng(location.getLatitude()-.01, location.getLongitude()-.01),
                            new LatLng(location.getLatitude()+.01, location.getLongitude()+.01)));
                }
                displaylocation();
            }
        };

    }


    private void buildlocationRequest() {
        mlocationrequest = new LocationRequest();
        mlocationrequest.setInterval(Update_interval);
        mlocationrequest.setFastestInterval(Fastest_interval);
        mlocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mlocationrequest.setSmallestDisplacement(dislpacement);

    }
    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Permission_rq:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    buildLocationCallback();
                    buildlocationRequest();
                        if (locaton_switch.isChecked())
                            displaylocation();

                    }
                }
        }



    private void displaylocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                mlastlocation = location;
                if (mlastlocation != null) {
                    if (locaton_switch.isChecked()) {
                        final double latitude = mlastlocation.getLatitude();
                        final double longitude = mlastlocation.getLongitude();
                        if (mcurrent != null)
                            mcurrent.remove();

                        mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                                .position(new LatLng(latitude, longitude))
                                .title("Shop"));
                     

                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 18.0f));


                    }

                }

                else {
                    Log.d("error", "cannot get your location");

                }
            }
        });

    }




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(locaton_switch.isChecked()) {
                    mcurrent.remove();
                    mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                            .position(new LatLng(latLng.latitude, latLng.longitude))
                            .title("Your Shop Location"));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude, latLng.longitude), 20.0f));
                }

            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildlocationRequest();
        buildLocationCallback();
        fusedLocationProviderClient.requestLocationUpdates(mlocationrequest, locationCallback, Looper.myLooper());

        shopref = FirebaseDatabase.getInstance().getReference("shop_locations");
        geoFire2 = new GeoFire(shopref);
        geoFire2.getLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new com.firebase.geofire.LocationCallback() {
            @Override
            public void onLocationResult(String key, final GeoLocation location) {
                if (location != null) {
                    if (mcurrent != null)
                        mcurrent.remove();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 18.0f));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 2s
                            mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop))
                                    .position(new LatLng(location.latitude, location.longitude))
                                    .title("Shop"));

                           
                            onMarkerClick(mcurrent);
                        }
                    }, 2000);

                } else {
                    LatLng sydney = new LatLng(28.6129, 77.2295);
                    mMap.addMarker(new MarkerOptions().position(sydney)
                            .title("Delhi"));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}








    private void onMarkerClick(final Marker mcurrent) {

        //Make the marker bounce
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        final LatLng markerLatLng = mcurrent.getPosition();
        Point startPoint = proj.toScreenLocation(markerLatLng);
        startPoint.offset(0, -100);
        final LatLng startLatLng = proj.fromScreenLocation(startPoint);

        final Interpolator interpolator = new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - startTime;
                float t = interpolator.getInterpolation((float) elapsed / duration);
                double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                mcurrent.setPosition(new LatLng(lat, lng));

                if (t < 1.0) {
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
                }
            }
        });


    }

}