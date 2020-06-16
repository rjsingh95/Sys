package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightListView;
import com.supplyingyourservice.ranjeet.singh.sys.R;

public class mybooks extends AppCompatActivity {


    private ExpandableHeightListView rv;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = mybooks.this;
    private EditText fpy;
    private String phone_number;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mybooks);
        TextView toolbartitle = (TextView) findViewById(R.id.Toolbartitle);
        toolbartitle.setText("MY BOOKS");
        mAuth = FirebaseAuth.getInstance();

        rv = (ExpandableHeightListView) findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.listview);
        rv.setLayoutManager(new LinearLayoutManager(mybooks.this));
        database = FirebaseDatabase.getInstance().getReference();

        final DatabaseReference mref = database.child("mybooks").child(mAuth.getCurrentUser().getUid());

        final ProgressBar progress=(ProgressBar)findViewById(R.id.progress);


        FirebaseRecyclerAdapter<mybook,mybooks. FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<mybook,mybooks. FriendsViewHolder>(
                mybook.class,
                R.layout.people_single_layout,
               mybooks. FriendsViewHolder.class,
                mref
        ) {
            @Override
            protected void populateViewHolder(final mybooks.FriendsViewHolder holder, mybook friends, int i) {
                final Handler handler = new Handler();



                String id= getRef(i).getKey();
                if(id!=null){
                    progress.setVisibility(View.GONE);
                }


                holder.cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final DatabaseReference myref = FirebaseDatabase.getInstance().getReference().child("book_in_city");

                        myref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    final String key = snapshot.getKey();


                                   if(snapshot.hasChild(mAuth.getCurrentUser().getUid())){
                                       myref.child(key).child(mAuth.getCurrentUser().getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                           @Override
                                           public void onSuccess(Void aVoid) {
                               mref.child(mAuth.getCurrentUser().getUid()).child(key).removeValue();
                                           }
                                       });
                                   }

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });






                    }
                });
                holder.price.setText(friends.getPrice());


                DatabaseReference mref2;
                        mref2=database.child("books");
                mref2.child(id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.hasChild("book_image") ) {

                            String image = dataSnapshot.child("book_image").getValue().toString();
                            holder.setImage(image,mybooks.this);

                        }


                        if(dataSnapshot.hasChild("title")){
                            String title =dataSnapshot.child("title").getValue().toString();
                            holder.setTitle(title);}


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
            }




        };

        rv.setAdapter(friendsRecyclerViewAdapter);




    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder {


        private final Button cancel;

        private final TextView title;
        private final TextView price;
        private final TextView time;
        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);



            title=(TextView)itemView.findViewById(R.id.user_single_name);
            price=(TextView)itemView.findViewById(R.id.price);
            time=(TextView)itemView.findViewById(R.id.time);

            cancel=(Button)itemView.findViewById(R.id.cancel);


            mView = itemView;

        }



        public void setTitle(String name){


            title.setText(name);

        }

        public void setImage(String thumb_image, Context ctx){

            ImageView userImageView = (ImageView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.user_single_image);
            Picasso.with(ctx).load(thumb_image).into(userImageView);

        }
    }


}