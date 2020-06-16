package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.supplyingyourservice.ranjeet.singh.sys.R;

public class bookdetail extends AppCompatActivity{


    private TextView mtitle,mdetails;
    private String mpost_key;
    private DatabaseReference ref;
    private ImageView photo;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.book_detail);
      TextView toolbartitle=(TextView)findViewById(R.id.Toolbartitle);
        mpost_key =getIntent().getStringExtra("product_id");
      toolbartitle.setText("BOOK DETAIL");
      mtitle=(TextView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.product_title);
      mdetails= (TextView) findViewById(R.id.author);




      photo=(ImageView)findViewById(R.id.pslider);
      Button sell=(Button)findViewById(R.id.sell);
      sell.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent productdetailsintent = new Intent (bookdetail.this,sell_book.class);
              productdetailsintent.putExtra("product_id",mpost_key);
              productdetailsintent.putExtra("name",name);
              startActivity(productdetailsintent);
          }
      });
        Button buy=(Button)findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent productdetailsintent = new Intent (bookdetail.this,getbooks.class);
                productdetailsintent.putExtra("product_id",mpost_key);
                productdetailsintent.putExtra("name",name);
                startActivity(productdetailsintent);




            }


        });


        ref= FirebaseDatabase.getInstance().getReference().child("books").child(mpost_key);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                book_sample obj=new book_sample();
                obj=dataSnapshot.getValue(book_sample.class);



                mdetails.setText("by " +obj.getAuthor());
                mtitle.setText(obj.getTitle());
                String i=obj.getBook_image();
                name=obj.getTitle();


                if(i!=null) {
                    Picasso.with(bookdetail.this).load(i).placeholder(R.drawable.loading).into(photo);
                }
                else {
                    Picasso.with(bookdetail.this).load(R.drawable.notebook).placeholder(R.drawable.loading).centerInside().into(photo);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }}