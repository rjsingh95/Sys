package com.supplyingyourservice.ranjeet.singh.sys.Home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.DashboardFragment;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.offersFragment;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.ordersFragment;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.test;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.SignIn;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.WatchListFragment;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;

import org.jetbrains.annotations.NotNull;

import in.sys.ranjeet.service.map.Map;
import in.sys.ranjeet.service.map.MapInterface;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private BottomNavigationView navView;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mPagerAdapter;
    private MapInterface mapInterface;
    private Location mlastlocation;
    private Map map;
    private static final int Permission_rq = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navView =(BottomNavigationView) findViewById(R.id.nav_view);
        mViewPager  = (ViewPager) findViewById(R.id.viewpager);
        mapInterface=new MapInterface() {
            @Override
            public void onCurrentLocstion(@NotNull Location location) {
                mlastlocation=location;

            }
        };
        setUpLocation();
        setupViewPager();
        bottomnavigation();


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

            map=new Map(this,mapInterface);
        }

    }
    @Override
    public void onStart() {
        super.onStart();
        ///String s = ((commomloc) getActivity().getApplication()).getloc();
       // mref= FirebaseDatabase.getInstance().getReference().child("products");
//
//        if(s!=null) {
//            myref2= FirebaseDatabase.getInstance().getReference().child("homerv").child(s).child("top");
//
//            homerv(rv4, myref2, mref);
//
//        }
//        if(s!=null){
//            myref = FirebaseDatabase.getInstance().getReference().child("in_city").child(s);
//
//            homerv(rv2,myref,mref);
//
//        }
//
//
//        if(s!=null) {
//
//            myref3 = FirebaseDatabase.getInstance().getReference().child("homerv").child(s).child("middle");
//
//            homerv(rv, myref3, mref);
//
//        }



        mAuth=FirebaseAuth.getInstance();


        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

//
           finish();
            startActivity(new Intent(this, SignIn.class));

        } else {
            DatabaseReference muserRef = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());

            muserRef.child("online").setValue("true");
            final DatabaseReference myref1 = FirebaseDatabase.getInstance().getReference().child("customer_locations");

            if(mlastlocation!=null){
                GeoFire geoFire = new GeoFire(myref1);
                geoFire.setLocation(mAuth.getCurrentUser().getUid(), new GeoLocation(mlastlocation.getLatitude(), mlastlocation.getLongitude()), new GeoFire.CompletionListener() {
                    @Override
                    public void onComplete(String key, DatabaseError error) {
                        Toast.makeText(MainActivity.this, "Location Saved ", Toast.LENGTH_SHORT).show();

                    }
                });
            }

        }






    }


    private void bottomnavigation() {
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_house:

                        navView.getMenu().findItem(R.id.ic_house).setChecked(true);
                        mViewPager.setCurrentItem(0);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;



                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_circle:
                        navView.getMenu().findItem(R.id.ic_circle).setChecked(true);
                        mViewPager.setCurrentItem(1);
                         overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_alert:
                        navView.getMenu().findItem(R.id.ic_alert).setChecked(true);
                        mViewPager.setCurrentItem(2);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_android:
                        navView.getMenu().findItem(R.id.ic_android).setChecked(true);
                        mViewPager.setCurrentItem(3);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;
                }
                return false;
            }
        });
    }
    private void setupViewPager(){
        mPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mPagerAdapter.addFragment(new DashboardFragment());
        mPagerAdapter.addFragment(new WatchListFragment());
        mPagerAdapter.addFragment(new ordersFragment());
        mPagerAdapter.addFragment(new offersFragment());

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position==0) {
                    navView.getMenu().findItem(R.id.ic_house).setChecked(true);
                }
                if (position==1) {
                    navView.getMenu().findItem(R.id.ic_circle).setChecked(true);
                }
                if (position==2) {
                    navView.getMenu().findItem(R.id.ic_alert).setChecked(true);
                }
                if (position==3) {
                    navView.getMenu().findItem(R.id.ic_android).setChecked(true);
                }


            }

            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {
            DatabaseReference muserRef = FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getCurrentUser().getUid());

            muserRef.child("online").setValue(ServerValue.TIMESTAMP);

                final DatabaseReference myref1 = FirebaseDatabase.getInstance().getReference().child("in_city");

                if(mlastlocation!=null){
                    Log.d("chackloc",mlastlocation.toString());
                    GeoFire geoFire = new GeoFire(myref1);
                    geoFire.setLocation(mAuth.getCurrentUser().getUid(), new GeoLocation(mlastlocation.getLatitude(), mlastlocation.getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            Toast.makeText(MainActivity.this, "Location Saved ", Toast.LENGTH_SHORT).show();

                        }
                    });
                }

        }else {

        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}