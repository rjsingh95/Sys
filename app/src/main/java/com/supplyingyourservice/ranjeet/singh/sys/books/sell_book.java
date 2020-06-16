package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;
import com.supplyingyourservice.ranjeet.singh.sys.editinfo;

public class sell_book extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{


    private String mpost_key;
    private String name;
    private DatabaseReference ref;
    private TextView nametv;
    private String phonetv;
    private EditText price;
    private Spinner quality;
    private TextView address;
    private TextView phone;
    private String item;
    private TextView sell;
    private DatabaseReference bref;
    private String s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.sell_book);

        mpost_key =getIntent().getStringExtra("product_id");
        name =getIntent().getStringExtra("name");
        final FirebaseUser mAuth = FirebaseAuth.getInstance().getCurrentUser();
        nametv=(TextView)findViewById(R.id.name);
        phone=(TextView)findViewById(R.id.phone);
       address=(TextView)findViewById(R.id.address) ;
        quality=(Spinner)findViewById(R.id.quality);
        price=(EditText)findViewById(R.id.price);




        address=(TextView)findViewById(R.id.address);
        quality.setOnItemSelectedListener(sell_book.this);
        s = ((commomloc) this.getApplication()).getloc();


        ref= FirebaseDatabase.getInstance().getReference().child("customers").child(mAuth.getUid());
        final List<String> categories = new ArrayList<String>();
        categories.add("Quality Of Book");
        categories.add("New");
        categories.add("Old (Good)");
        categories.add("Old (Average)");
        categories.add("Old (Good)");

        DatabaseReference fire = FirebaseDatabase.getInstance().getReference();
 bref=fire.child("book_in_city").child(s).child(mpost_key);
        final DatabaseReference cloc = fire.child("customer_locations");
        final DatabaseReference mybook = fire.child("mybooks");


        sell=(TextView)findViewById(R.id.save);
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
          if(price.getText().toString()!=null && !price.getText().toString().equals("")) {

              Map chatAddMap = new HashMap();
              chatAddMap.put("price", price.getText().toString());
              chatAddMap.put("quality", item);
              chatAddMap.put("from",mAuth.getUid().toString());
              chatAddMap.put("time",ServerValue.TIMESTAMP);



              mybook.child(mAuth.getUid()).child(mpost_key).setValue(chatAddMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                  @Override
                  public void onSuccess(Void aVoid) {
                      GeoFire geoFire2 = new GeoFire(cloc);
                      final GeoFire geoFire3 = new GeoFire(bref);

                      geoFire2.getLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new com.firebase.geofire.LocationCallback() {
                          @Override
                          public void onLocationResult(String key, final GeoLocation location) {
                              if (location != null) {
                                  geoFire3.setLocation(FirebaseAuth.getInstance().getCurrentUser().getUid(), new GeoLocation(location.latitude, location.longitude), new GeoFire.CompletionListener() {
                                      @Override
                                      public void onComplete(String key, DatabaseError error) {
                                          Toast.makeText(sell_book.this, "Requested", Toast.LENGTH_SHORT).show();
                                          finish();


                                      }
                                  });


                              }
                          }

                          @Override
                          public void onCancelled(DatabaseError databaseError) {

                          }


                      });
                  }
              });

              }

          else {
              Toast.makeText(sell_book.this, "newtwork failed", Toast.LENGTH_SHORT).show();
          }
            }
        });

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        quality.setAdapter(dataAdapter);



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                if(dataSnapshot.hasChild("name")) {
                   String name = dataSnapshot.child("name").getValue().toString();
                   nametv.setText(name);

               }else {
                   Intent productdetailsintent = new Intent (sell_book.this,editinfo.class);

                   startActivity(productdetailsintent);
                   ref.removeEventListener(this);

               }

               if(dataSnapshot.hasChild("phone")) {
                   phonetv = dataSnapshot.child("phone").getValue().toString();
                   phone.setText(phonetv);

               }else {
                   Intent productdetailsintent = new Intent (sell_book.this,editinfo.class);

                   startActivity(productdetailsintent);
                   ref.removeEventListener(this);

               }
                if(dataSnapshot.hasChild("address")) {
                    String addresss = dataSnapshot.child("address").getValue().toString();
                    address.setText(addresss);


                }else {
                    Intent productdetailsintent = new Intent (sell_book.this,editinfo.class);

                    startActivity(productdetailsintent);
                    ref.removeEventListener(this);

                }






            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}