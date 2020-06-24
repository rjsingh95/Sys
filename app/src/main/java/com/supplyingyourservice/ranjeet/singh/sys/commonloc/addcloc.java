package com.supplyingyourservice.ranjeet.singh.sys.commonloc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment.DashboardFragment;
import com.supplyingyourservice.ranjeet.singh.sys.Home.MainActivity;
import com.supplyingyourservice.ranjeet.singh.sys.R;

public class addcloc extends AppCompatActivity  implements AdapterView.OnItemSelectedListener{
    private Spinner locations;
    private String item="item";
    private DatabaseReference myref;
    private FirebaseUser mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.commonloc);
        locations=(Spinner)findViewById(R.id.locspinner);
        locations.setOnItemSelectedListener(this);

        final List<String> categories = new ArrayList<String>();
        categories.add("Moradabad");
        categories.add("Delhi");

        categories.add("Chennai");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    myref= FirebaseDatabase.getInstance().getReference().child("customers");
    mAuth=FirebaseAuth.getInstance().getCurrentUser();
        // attaching data adapter to spinner
        locations.setAdapter(dataAdapter);
        ImageView mgo = (ImageView) findViewById(R.id.gomain);
    mgo.setOnClickListener(new View.OnClickListener() {


        @Override
      public void onClick(View view) {
          if (!item.equals("item")){



              myref.child(mAuth.getUid()).child("city").setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      ((commomloc) addcloc.this.getApplication()).setloc(item);

                      myref.child(mAuth.getUid()).child("new_message").setValue("new");
                      final String MY_PREFS_NAME = "BrackatPrefsFile";
                      SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                      editor.putString("city", item);
                      editor.apply();

                      Intent productdetailsintent = new Intent (addcloc.this, MainActivity.class);
                      productdetailsintent.putExtra("city",item);
                      startActivity(productdetailsintent);
                      finish();
                      Toast.makeText(addcloc.this, "Welocome to Brackat", Toast.LENGTH_SHORT).show();

                  }
              });
          }
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
