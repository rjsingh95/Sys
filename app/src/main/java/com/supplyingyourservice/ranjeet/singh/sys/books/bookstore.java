package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.BeanlistGrooming;
import com.supplyingyourservice.ranjeet.singh.sys.GroomingRecyclerViewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.R;

public class bookstore extends AppCompatActivity {
    String key=null;
    String value=null;

    private RecyclerView rv2;
    private EditText showbrand;
    private EditText edit;
    private int num;
    boolean gaya =false;
    private String mpost_key;
    private String[] TITLEG = {
            "Entrance Exams",
            "Academics",
            "Literature & Fiction",
            "Indian Writing",
            "Children",
            "Business",
            "Self Help",
            "Non-Fiction",

    };

    private ArrayList<BeanlistGrooming> Bean;
    private RecyclerView rv;
    private DatabaseReference mref;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookstore);

        progress=(ProgressBar)findViewById(R.id.progress);

        TextView toolbartitle = (TextView) findViewById(R.id.Toolbartitle);
        toolbartitle.setText("BOOK STORE");
        rv2=(RecyclerView)findViewById(R.id.recyclerView);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager);
        Bean = new ArrayList<BeanlistGrooming>();

       rv = (RecyclerView) findViewById(R.id.rv);


        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(bookstore.this, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(mLayoutManager2);



        for (String aTITLEG : TITLEG) {

            BeanlistGrooming bean = new BeanlistGrooming(aTITLEG);
            Bean.add(bean);

        }


        GroomingRecyclerViewAdapter baseAdapter = new GroomingRecyclerViewAdapter(this, bookstore.this, Bean,"bookstore") {
        };
        rv2.setAdapter(baseAdapter);


        final ProgressBar progressrv = (ProgressBar) findViewById(R.id.progressBar2);

       mref = FirebaseDatabase.getInstance().getReference().child("books");
        final List<String> list = new ArrayList<>();
        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv2.setLayoutManager(mLayoutManager1);
        showbrand= (EditText) findViewById(R.id.showbrand);
        showbrand.clearFocus();
        showbrand.requestFocusFromTouch();
        showbrand.clearFocus();
     

      showbrand.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent productdetailsintent = new Intent (bookstore.this, book_search.class);

              startActivity(productdetailsintent);

          }
      });
        num =0;
homerv();






    }

    private void homerv(){

        FirebaseRecyclerAdapter<book_sample,FriendsViewHolder> adapter = new FirebaseRecyclerAdapter<book_sample, FriendsViewHolder>(book_sample.class, com.supplyingyourservice.ranjeet.singh.sys.R.layout.book, FriendsViewHolder.class,mref)
        {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, final book_sample model, int position) {

                final String post_key=  getRef(position).getKey();
                if(post_key!=null){
                    progress.setVisibility(View.GONE
                    );




                }
                viewHolder.setTitle(model.getTitle());

                viewHolder.setImage(model.getBook_image(),getApplicationContext());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       
                        Intent productdetailsintent = new Intent (bookstore.this,bookdetail.class);
                        productdetailsintent.putExtra("product_id",post_key);
                        startActivity(productdetailsintent);
                    }
                });




            }

        };
        rv.setAdapter(adapter);

    }
    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(R.id.title);
            userNameView.setText(name);

        }


        public void setImage(String i, Context context){
            ImageView image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
            Picasso.with(context).load(i).placeholder(R.drawable.loading).into(image);
            //   Glide.with(context).load(i).into(image);
        }

    }

}
