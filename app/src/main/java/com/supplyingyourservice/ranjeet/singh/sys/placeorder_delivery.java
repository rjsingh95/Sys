package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.ordersFragment;

import java.util.HashMap;

public  class placeorder_delivery extends AppCompatActivity {


    private TextView to, price1 ,
            price2;
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
    private EditText yourprice;
    private LinearLayout linyourprice;
    private String pricetype;
    private String pricefirst;
    private String pricesecond;
    private DatabaseReference datbase;
    private FirebaseUser mAuth;
    private TextView payable;
    private TextView notinrange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.placeorder_delivery);
        final ProgressBar progrss = (ProgressBar) findViewById(R.id.progressBar3);
        to=(TextView)findViewById(R.id.tv2);
        price1=(TextView)findViewById(R.id.tvp1);
        price2=(TextView)findViewById(R.id.tvp3);

        notinrange=(TextView)findViewById(R.id.notrange);
        pricetypetv=(TextView)findViewById(R.id.pricetype);
        d1=(TextView)findViewById(R.id.d1);
        d2=(TextView)findViewById(R.id.d2);
        to2=(TextView)findViewById(R.id.tod);
        mAuth= FirebaseAuth.getInstance().getCurrentUser();

        
        Bundle extras = getIntent().getExtras();
        mpost_key = extras.getString("shop_id");
        total=(TextView)findViewById(R.id.total);
        payable=(TextView)findViewById(R.id.payable);
        yourprice=(EditText)findViewById(R.id.yourprice);
        linyourprice=(LinearLayout)findViewById(R.id.linyourprice);
        TextView deliver = (TextView) findViewById(R.id.deliver);
       




        mproduct_key = extras.getString("product_id");

datbase=FirebaseDatabase.getInstance().getReference();
        myref= datbase.child("shop_products").child(mpost_key).child(mproduct_key);
        DatabaseReference myref2 = datbase.child("products");
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
                 pricetype ="none";
                if (dataSnapshot.hasChild("pricetype")) {
                    pricetype = dataSnapshot.child("pricetype").getValue().toString();
                    
                }

                if (pricetype.equals("range")) {
                    pricefirst = dataSnapshot.child("price").getValue().toString();
                    sh1 = Float.parseFloat(pricefirst);
                    linyourprice.setVisibility(View.VISIBLE);


                    pricesecond = dataSnapshot.child("price2").getValue().toString();


                    sh2 = Float.parseFloat(pricesecond);

                    LinearLayout linpay = (LinearLayout) findViewById(R.id.lin);
                    linpay.setVisibility(View.GONE);
                    yourprice.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                       float p=Float.parseFloat(String.valueOf(charSequence));
                       if(sh1<sh2){
                           if(sh1<p&&p<sh2){
                               notinrange.setVisibility(View.GONE);
                           }
                           else {
                               notinrange.setVisibility(View.VISIBLE);
                           }
                       }
                       else {
                           if(sh2<p&&p<sh1){
                               notinrange.setVisibility(View.GONE);
                           }
                           else {
                               notinrange.setVisibility(View.VISIBLE);
                           }

                       }


                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });
                    pricetypetv.setText("Range");
                    Log.d("totu", "" + orginal);
                    pricefirst = dataSnapshot.child("price").getValue().toString();
                    sh1 = Float.parseFloat(pricefirst);
                    linyourprice.setVisibility(View.VISIBLE);
                    

                     pricesecond = dataSnapshot.child("price2").getValue().toString();
                    

                    sh2 = Float.parseFloat(pricesecond);

                    to2.setVisibility(View.VISIBLE);
                    price2.setVisibility(View.VISIBLE);


                    to.setVisibility(View.VISIBLE);


                        if(sh1>sh2){
                            price1.setText(pricesecond);
                            price2.setText(pricefirst);
                        }
                        else {
                            price1.setText(pricefirst);
                            price2.setText(pricesecond);
                        }

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
                    payable.setText(fprice);



                    long dis= (long) (((orginal-sh3)/orginal)*100);
                    d2.setText(""+dis);
                    price2.setVisibility(View.GONE);
                    to.setVisibility(View.GONE);
                    price1.setVisibility(View.VISIBLE);

                  linyourprice.setVisibility(View.GONE);


                    price1.setText(price);
                }

                if(pricetype.equals("none")){

                    price1.setText("Unspecified");
                    d2.setVisibility(View.GONE);
                    to2.setVisibility(View.GONE);
                    price2.setVisibility(View.GONE);
                    linyourprice.setVisibility(View.GONE);
                    d1.setVisibility(View.VISIBLE);
                    d1.setText("0");
                    to.setVisibility(View.GONE);

                }

            }
        @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if(pricetype.equals("fixed")) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(placeorder_delivery.this);
                    alert.setMessage("Check Your Product before giving money to delivery guy");
                    // .setMessage("");

                    LayoutInflater inflater = getLayoutInflater();


                    final View view = inflater.inflate(R.layout.accept_order_dialog3, null);
                    TextView fpy = (TextView) view.findViewById(R.id.okay);

                    fpy.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            progrss.setVisibility(View.VISIBLE);



                                HashMap<String, Object> notiuser = new HashMap<>();
                                notiuser.put("time", ServerValue.TIMESTAMP);
                                notiuser.put("final_price", fprice);
                                notiuser.put("shop_id", mpost_key);
                                notiuser.put("status", "swaiting");
                                notiuser.put("delivery_time", "none");
                                datbase.child("delivery").child(mAuth.getUid()).child(mproduct_key).setValue(notiuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String, Object> notishop = new HashMap<>();
                                        notishop.put("time", ServerValue.TIMESTAMP);
                                        notishop.put("final_price", fprice);
                                        notishop.put("product_id", mproduct_key);
                                        notishop.put("customer_id", mAuth.getUid());
                                        notishop.put("status", "swaiting");
                                        notishop.put("delivery_time", "none");
                                        datbase.child("delivery").child(mpost_key).child(mAuth.getUid() + mproduct_key).setValue(notishop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                HashMap<String, String> noti = new HashMap<>();
                                                noti.put("from", mAuth.getUid());
                                                noti.put("type", "delivery");
                                                datbase.child("notifications").child(mpost_key).push().setValue(noti).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Intent productdetailsintent = new Intent(placeorder_delivery.this, ordersFragment.class);
                                                        startActivity(productdetailsintent);
                                                        finish();
                                                        placeorder_delivery.this.overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.fade_out);

                                                    }
                                                });


                                            }
                                        });
                                    }
                                });

                            }



          });
                    alert.setView(view);

                    AlertDialog dialog = alert.create();
                    dialog.show();
                }

                if(pricetype.equals("range")) {

                    String yp = yourprice.getText().toString();

                    if(yp!=null&&!yp.equals("")){
                    final long fyp = (long) Float.parseFloat(yp);
                    if (sh1 <= fyp && sh2 >= fyp) {

                        final AlertDialog.Builder alert = new AlertDialog.Builder(placeorder_delivery.this);
                        alert.setMessage("Check Your Product before giving money to delivery guy");
                        // .setMessage("");

                        LayoutInflater inflater = getLayoutInflater();
                        final View view = inflater.inflate(R.layout.accept_order_dialog3, null);


                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progrss.setVisibility(View.VISIBLE);


                                final String sfyp = String.valueOf(fyp);

                                HashMap<String, Object> notiuser = new HashMap<>();
                                notiuser.put("time", ServerValue.TIMESTAMP);
                                notiuser.put("final_price", sfyp);
                                notiuser.put("shop_id", mpost_key);
                                notiuser.put("status", "swaiting");
                                notiuser.put("delivery_time", "none");
                                datbase.child("delivery").child(mAuth.getUid()).child(mproduct_key).setValue(notiuser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        HashMap<String, Object> notishop = new HashMap<>();
                                        notishop.put("time", ServerValue.TIMESTAMP);
                                        notishop.put("final_price", sfyp);
                                        notishop.put("product_id", mproduct_key);
                                        notishop.put("customer_id", mAuth.getUid());
                                        notishop.put("status", "swaiting");
                                        notishop.put("delivery_time", "none");
                                        datbase.child("delivery").child(mpost_key).child(mAuth.getUid() + mproduct_key).setValue(notishop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                HashMap<String, String> noti = new HashMap<>();
                                                noti.put("from", mAuth.getUid());
                                                noti.put("type", "delivery");
                                                datbase.child("notifications").child(mpost_key).push().setValue(noti).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        Intent productdetailsintent = new Intent(placeorder_delivery.this, ordersFragment.class);
                                                        startActivity(productdetailsintent);
                                                        finish();
                                                        placeorder_delivery.this.overridePendingTransition(R.anim.anim_slide_in_from_left, R.anim.fade_out);

                                                    }
                                                });


                                            }
                                        });
                                    }
                                });


                            }


                        });
                        alert.setView(view);

                        AlertDialog dialog = alert.create();
                        dialog.show();
                    }

                }
                }else {
                    Toast.makeText(placeorder_delivery.this, "Enter Price", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}
