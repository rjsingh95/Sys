package com.supplyingyourservice.ranjeet.singh.sys.books;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.supplyingyourservice.ranjeet.singh.sys.R;

public class customer_loc extends FragmentActivity implements OnMapReadyCallback {
    private SupportMapFragment mapFragment;
    private DatabaseReference shopref;
    private GeoFire geoFire2;
    private Marker mcurrent;
    private GoogleMap mMap;
    private String mpost_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.shop_loc);

        Bundle extras = getIntent().getExtras();
        mpost_key = extras.getString("shop_id");

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(com.supplyingyourservice.ranjeet.singh.sys.R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Bundle extras = getIntent().getExtras();
        assert extras != null;
        mpost_key = extras.getString("shop_id");
        shopref = FirebaseDatabase.getInstance().getReference().child("customer_locations");
        geoFire2 = new GeoFire(shopref);
        Log.d("locationkey",mpost_key);
        geoFire2.getLocation(mpost_key, new com.firebase.geofire.LocationCallback() {
            @Override
            public void onLocationResult(String key, final GeoLocation location) {

                if (mcurrent != null)
                    mcurrent.remove();
                if(location!=null) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.latitude, location.longitude), 18.0f));
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 2s
                            mcurrent = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_shop))
                                    .position(new LatLng(location.latitude, location.longitude))
                                    .title("Location"));

                            Toast.makeText(customer_loc.this, "Location", Toast.LENGTH_SHORT).show();

                            onMarkerClick(mcurrent);
                        }
                    }, 2500);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




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
