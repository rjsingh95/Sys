package com.supplyingyourservice.ranjeet.singh.sys.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private BottomNavigationView navView;
    private ViewPager mViewPager;
    private SectionsPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navView =(BottomNavigationView) findViewById(R.id.nav_view);
        mViewPager  = (ViewPager) findViewById(R.id.viewpager);

        setupViewPager();
        bottomnavigation();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();

        if (mAuth != null) {
            // User is signed in
        } else {
            Intent intent =new Intent(MainActivity.this, SignIn.class);
            startActivity(intent);
            finish();
            // No user is signed in
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