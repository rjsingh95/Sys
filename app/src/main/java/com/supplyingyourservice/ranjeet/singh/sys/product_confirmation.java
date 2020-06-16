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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class product_confirmation extends AppCompatActivity {
    private String mpost_key,mproduct_key;
    private DatabaseReference database;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        Bundle extras = getIntent().getExtras();
        TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        toolbartitle.setText("Confirmation");

        mpost_key = extras.getString("shop_name");
        phone = extras.getString("number");


        mproduct_key = extras.getString("product_id");
        TextView call=(TextView)findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phone!=null) {
                    try {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + phone));
                        if (ActivityCompat.checkSelfPermission(product_confirmation.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions(product_confirmation.this,
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


        database= FirebaseDatabase.getInstance().getReference();
        TextView shopname=(TextView)findViewById(R.id.shopname);
        if(mpost_key!=null) {
            shopname.setText(mpost_key);

        }
        final ImageView imageView=(ImageView)findViewById(R.id.imageView);
        final TextView title=(TextView)findViewById(R.id.title);
        database.child("products").child(mproduct_key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("productdp")){
                Picasso.with(product_confirmation.this).load(dataSnapshot.child("productdp").getValue().toString()).placeholder(R.drawable.loading).into(imageView);}
                if(dataSnapshot.hasChild("title")){
                    title.setText(dataSnapshot.child("title").getValue().toString());

                }
             }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}