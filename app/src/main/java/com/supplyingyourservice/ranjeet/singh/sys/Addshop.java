package com.supplyingyourservice.ranjeet.singh.sys;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public  class Addshop extends AppCompatActivity{

    private CircleImageView dp;
    private TextView name;
    private ImageView shop_dp;
    private TextView shopname;
    String key=null;
    private LinearLayout info;
    private TextView error;
    private CardView card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.addshop);
        ImageView add =(ImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.add);
         dp =(CircleImageView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.banar1);
         final EditText shop=(EditText)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.editshop);
        shop_dp = (ImageView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
        final FirebaseAuth mAuth=FirebaseAuth.getInstance();
        error =(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.error);
        error.setVisibility(View.GONE);
         name =(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.name);


        card =(CardView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.card);
        shopname =(TextView)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shopname);
        final Button subscribe=(Button)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.subsbcribe);
        info=(LinearLayout)findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.info);
        info.setVisibility(View.GONE);
        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(key!=null) {
                    DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("subscribers").child(key).child(mAuth.getCurrentUser().getUid()).child("request");
                    mref.setValue("recieved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            subscribe.setText("Request Send");
                        }
                    });
                }

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card.setVisibility(View.VISIBLE);
              String shop_id=  shop.getText().toString();
              checkIfUsernameExists(shop_id);

            }
        });





    }
    private void checkIfUsernameExists(final String username) {




        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        Query query = reference

                .child("users")

                .orderByChild("phone_number")

                .equalTo(username);

        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {




                if(dataSnapshot.exists()){
                    card.setVisibility(View.GONE);
                    info.setVisibility(View.VISIBLE);
                    key=null;
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        key=postSnapshot.getKey();

                    //add the username
                    if(postSnapshot.hasChild("name")){
                        String n=postSnapshot.child("name").getValue().toString();
                        name.setText(n);
                    }
                    if(postSnapshot.hasChild("shop_name")){
                        String m=postSnapshot.child("shop_name").getValue().toString();
                        shopname.setText(m);
                    }
                    if(postSnapshot.hasChild("shopkeeper_thumb")){
                       String o= postSnapshot.child("shopkeeper_thumb").getValue().toString();
                        Picasso.with(Addshop.this).load(o).into(dp);

                    } if(postSnapshot.hasChild("photo_thumb")){
                        String p=postSnapshot.child("photo_thumb").getValue().toString();

                            Picasso.with(Addshop.this).load(p).into(shop_dp);



                        }

                        error.setVisibility(View.GONE);



                }
                }else {

                    error.setVisibility(View.VISIBLE);
                    card.setVisibility(View.GONE);
                }


            }



            @Override

            public void onCancelled(DatabaseError databaseError) {



            }

        });

    }


}