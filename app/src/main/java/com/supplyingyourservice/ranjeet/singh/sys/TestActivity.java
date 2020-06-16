package com.supplyingyourservice.ranjeet.singh.sys;

import android.app.Activity;
import android.os.Bundle;

import com.supplyingyourservice.ranjeet.singh.sys.R;


public class TestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         /**
          * Below shows setting the scroll view shadow properties programatically.
          */
          StickyScrollView scrollView = (StickyScrollView)
          findViewById(R.id.scroll);
          scrollView.setShadowDrawable(getResources().getDrawable(R.drawable.sticky_shadow_default));
          scrollView.setShadowHeight(111);
    }
}
