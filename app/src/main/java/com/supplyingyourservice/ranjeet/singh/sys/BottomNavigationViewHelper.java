package com.supplyingyourservice.ranjeet.singh.sys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(false);
        bottomNavigationViewEx.setIconSize(30f,30f);
        bottomNavigationViewEx.setIconsMarginTop(30);
      
        bottomNavigationViewEx.setItemHeight(120);
        }

    public static void enableNavigation(final Context context,final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){


                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_house:
                        Intent intent1 = new Intent(context, MainActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        callingActivity.finish();
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;



                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_circle:
                        Intent intent3 = new Intent(context,WatchListFragment.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        callingActivity.finish();
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_alert:
                        Intent intent4 = new Intent(context, orders.class);//ACTIVITY_NUM = 3

                        context.startActivity(intent4);
                        callingActivity.finish();
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                        break;

                    case com.supplyingyourservice.ranjeet.singh.sys.R.id.ic_android:
                        Intent intent5 ;//ACTIVITY_NUM = 4
                        intent5 = new Intent(context, offers.class);
                        context.startActivity(intent5);
                        callingActivity.finish();
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                        break;
                }


                return false;
            }
        });
    }




}
