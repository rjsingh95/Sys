package com.supplyingyourservice.ranjeet.singh.sys;


        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;



        import android.os.Handler;
        import android.view.View;


        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;

    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;
        import com.supplyingyourservice.ranjeet.singh.sys.Home.MainActivity;


public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAnimation();
        DatabaseReference i = FirebaseDatabase.getInstance().getReference();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("customers");


                ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//******change activity here*******

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void setAnimation() {




        findViewById(R.id.imagelogo2).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.fade);
        findViewById(R.id.imagelogo2).startAnimation(anim);

    }
}