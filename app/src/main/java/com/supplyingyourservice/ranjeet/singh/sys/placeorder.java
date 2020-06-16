package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class placeorder extends AppCompatActivity {


    private TextView to, price1 ,price2;
    private TextView pricetypetv;
    private DatabaseReference myref;
    private String mpost_key, mproduct_key;
    private TextView total;
    private String price;
    private String fprice;
    private float orginal;
    private float sh1;
    private TextView d1,d2,to2;
    private float sh2;
    private long orginal2;
    private TextView save;
    private String shop_name;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.activity_placeorder);
        to=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tv2);
        price1=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvp1);
        save=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.save);
        price2=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tvp3);
        pricetypetv=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.pricetype);
        d1=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.d1);
        d2=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.d2);
        to2=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.tod);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent(placeorder.this,product_confirmation.class);
                Bundle extras = new Bundle();
                extras.putString("shop_name",shop_name);
                extras.putString("product_id",mproduct_key);
                extras.putString("number",number);

                productdetailsintent.putExtras(extras);
                productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(productdetailsintent);

            }
        });


        total=(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.total);

        Bundle extras = getIntent().getExtras();
        mpost_key = extras.getString("shop_id");
        shop_name=extras.getString("shop_name");


        mproduct_key = extras.getString("product_id");
        number = extras.getString("number");


        myref= FirebaseDatabase.getInstance().getReference().child("shop_products").child(mpost_key).child(mproduct_key);
        DatabaseReference myref2 = FirebaseDatabase.getInstance().getReference().child("products");

         myref2.child(mproduct_key).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                price= dataSnapshot.child("price").getValue().toString();
                 orginal= Float.parseFloat(price);
                total.setText(price);

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String pricetype ="none";
                if (dataSnapshot.hasChild("pricetype")) {
                    pricetype = dataSnapshot.child("pricetype").getValue().toString();

                }

                if (pricetype.equals("range")) {
                    pricetypetv.setText("Range");
                    Log.d("totu", "" + orginal);
                    String price = dataSnapshot.child("price").getValue().toString();
                    sh1 = Float.parseFloat(price);

                    String pricesecond = dataSnapshot.child("price2").getValue().toString();

                    sh2 = Float.parseFloat(pricesecond);
                    to2.setVisibility(View.VISIBLE);
                    price2.setVisibility(View.VISIBLE);
                    to.setVisibility(View.VISIBLE);
                    price1.setText(price);
                    price2.setText(pricesecond);
                    if (orginal >= sh1 && orginal<=sh2) {
                        long dis3 = (long) (((orginal - sh1) / orginal) * 100);
                        d2.setVisibility(View.VISIBLE);
                        d1.setVisibility(View.GONE);
                        to2.setVisibility(View.GONE);
                        d2.setText("upto " +dis3);
                    }

                    if (orginal >sh1 && orginal>sh2) {


                          long dis1 = (long) (((orginal - sh1) / orginal) * 100);
                        long dis3 = (long) (((orginal - sh2) / orginal) * 100);
                        d1.setText("" + dis3);
                        d2.setText("" + dis1);

                        d1.setVisibility(View.VISIBLE);
                        d2.setVisibility(View.VISIBLE);
                    }

                    if (orginal <sh1 && orginal<sh2) {


                        d1.setVisibility(View.VISIBLE);
                        d1.setText("none");
                        d2.setVisibility(View.GONE);
                        to2.setVisibility(View.GONE);
                    }

                }
                if (pricetype.equals("fixed")){

                    fprice = dataSnapshot.child("price").getValue().toString();

                    long sh3 = Long.parseLong(fprice);




                     if(sh3<orginal) {
                         long dis= (long) (((orginal-sh3)/orginal)*100);

                         d2.setText("" + dis);
                     }
                     else {
                         d2.setText("none");


                     }



                     price2.setVisibility(View.GONE);
                     to.setVisibility(View.GONE);
                     price1.setVisibility(View.VISIBLE);




                    price1.setText(fprice);
                }
                if(pricetype.equals("none")){
                    price1.setText("Unspecified");
                    price2.setVisibility(View.GONE);
                    to.setVisibility(View.GONE);

                    d2.setVisibility(View.GONE);
                    to2.setVisibility(View.GONE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText("0");

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}