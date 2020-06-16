package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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

import java.util.ArrayList;
import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBang;
import com.supplyingyourservice.ranjeet.singh.sys.SmallBangListener;
import com.supplyingyourservice.ranjeet.singh.sys.TrendingRecyclerViewAdapter;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;

public class all_books extends AppCompatActivity {
    String key = null;
    String value = null;

    private RecyclerView rv2;
    private String mpost_key;
    private DatabaseReference mref;
    List<aproduct> list = new ArrayList<>();
    private DatabaseReference dref;
    private FirebaseAuth mAuth;
    private ArrayList<aproduct> Bean1;
    private TrendingRecyclerViewAdapter baseAdapter1;
    private ProgressBar progress;
    private String brand_key;
    private String node="category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.supplyingyourservice.ranjeet.singh.sys.R.layout.products_list);
        progress=(ProgressBar)findViewById(R.id.progress);


        EditText search = (EditText) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.showbrand);
        search.setVisibility(View.GONE);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rv2 = (RecyclerView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.recyclerView);
        mref = FirebaseDatabase.getInstance().getReference().child("books");

        mpost_key = getIntent().getStringExtra("category");



        GridLayoutManager mLayoutManager = new GridLayoutManager(all_books.this,  2);
        rv2.setLayoutManager(mLayoutManager);
        mAuth= FirebaseAuth.getInstance();
        Bean1 = new ArrayList<aproduct>();


        Query query = mref.orderByChild("category").equalTo(mpost_key);




        FirebaseRecyclerAdapter<book_sample, FriendsViewHolder> adapter = new FirebaseRecyclerAdapter<book_sample,FriendsViewHolder>(
                book_sample.class, R.layout.book,FriendsViewHolder.class,query)
        {
            @Override
            protected void populateViewHolder(final FriendsViewHolder viewHolder, final book_sample model, int position) {

                final String post_key = getRef(position).getKey();
                if(post_key!=null){
                    progress.setVisibility(View.GONE);
                }


                dref = FirebaseDatabase.getInstance().getReference();
                dref.child("book_likes").child(mAuth.getCurrentUser().getUid()).child(post_key);
                dref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("like")) {
                            viewHolder.fav2.setVisibility(View.VISIBLE);
                            viewHolder.fav1.setVisibility(View.GONE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.mSmallBang = SmallBang.attach2Window(all_books.this);


                viewHolder.fav1.setOnClickListener(new View.OnClickListener() {
                    public DatabaseReference dref;

                    @Override
                    public void onClick(final View v) {

                        dref = FirebaseDatabase.getInstance().getReference();
                        dref.child("book_likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").setValue("set").addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viewHolder.fav2.setVisibility(View.VISIBLE);
                                viewHolder.fav1.setVisibility(View.GONE);
                                like(v);
                            }
                        });


                    }

                    public void like(View view) {
                        viewHolder.fav2.setImageResource(com.supplyingyourservice.ranjeet.singh.sys.R.drawable.f4);
                        viewHolder.mSmallBang.bang(view);
                        viewHolder.mSmallBang.setmListener(new SmallBangListener() {
                            @Override
                            public void onAnimationStart() {

                            }

                            @Override
                            public void onAnimationEnd() {

                            }


                        });
                    }

                });


                viewHolder.fav2.setOnClickListener(new View.OnClickListener() {
                    DatabaseReference dref;

                    @Override
                    public void onClick(View v) {

                        dref = FirebaseDatabase.getInstance().getReference();
                        dref.child("book_likes").child(mAuth.getCurrentUser().getUid()).child(post_key).child("like").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viewHolder.fav2.setVisibility(View.GONE);
                                viewHolder.fav1.setVisibility(View.VISIBLE);

                            }
                        });


                    }
                });


                viewHolder.setTitle(model.getTitle());
                viewHolder.setImage(model.getBook_image(), getApplicationContext());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this ,post_key,"product clicked",Toast.LENGTH_SHORT).show();
                        Intent productdetailsintent = new Intent(all_books.this, bookdetail.class);
                        productdetailsintent.putExtra("product_id", model.getBook_id());
                        startActivity(productdetailsintent);
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

rv2.setAdapter(adapter);



    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {


        private ImageView fav1, fav2 ;
        View mView;
        public SmallBang mSmallBang;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            fav1 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav1);
            fav2 = (ImageView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fav2);

        }
        public void  setTitle(String t){
            TextView title = (TextView) itemView.findViewById(R.id.title);
            title.setText(t); }
        public void setImage(String i, Context context){
            ImageView image = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
            Picasso.with(context).load(i).into(image); }




    }








}