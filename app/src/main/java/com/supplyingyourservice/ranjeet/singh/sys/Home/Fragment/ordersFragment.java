package com.supplyingyourservice.ranjeet.singh.sys.Home.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.supplyingyourservice.ranjeet.singh.sys.ExpandableHeightListView;
import com.supplyingyourservice.ranjeet.singh.sys.GetTimeAgo;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.order;

import java.util.HashMap;

public class ordersFragment extends Fragment {


    private RecyclerView rv;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private static final int ACTIVITY_NUM = 2;
     private EditText fpy;
    private String phone_number;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_order, container, false);
        TextView toolbartitle = (TextView)root. findViewById(R.id.Toolbartitle);
        toolbartitle.setText("ORDERS");
        mAuth = FirebaseAuth.getInstance();

        rv = (RecyclerView) root.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.listview);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        database = FirebaseDatabase.getInstance().getReference();

        FirebaseRecyclerAdapter<order, FriendsViewHolder> friendsRecyclerViewAdapter = new FirebaseRecyclerAdapter<order, FriendsViewHolder>(
                order.class,
                com.supplyingyourservice.ranjeet.singh.sys.R.layout.list_order,
                FriendsViewHolder.class,
                database.child("delivery").child(mAuth.getCurrentUser().getUid())
        ) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void populateViewHolder(final FriendsViewHolder friendsViewHolder, final order friends, int i) {


                final String list_user_id = getRef(i).getKey();

                long lastTime = friends.getTime();
                DatabaseReference mref = database.child("subscribers").child(friends.getShop_id()).child(mAuth.getCurrentUser().getUid());

                final Handler handler = new Handler();
                mref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String request = dataSnapshot.child("request").getValue().toString();
                            if (request.equals("accepted")) {
                                friendsViewHolder.yoursubcriber.setVisibility(View.VISIBLE);
                                friendsViewHolder.notsubcriber.setVisibility(View.GONE);
                            } else {
                                friendsViewHolder.yoursubcriber.setVisibility(View.GONE);
                                friendsViewHolder.notsubcriber.setVisibility(View.VISIBLE);

                            }
                        } else {
                            friendsViewHolder.yoursubcriber.setVisibility(View.GONE);
                            friendsViewHolder.notsubcriber.setVisibility(View.VISIBLE);

                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                friendsViewHolder.call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse("tel:" + phone_number));
                            if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling`
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                ActivityCompat.requestPermissions(requireActivity(),
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
                        );

                        GetTimeAgo getTimeAgo = new GetTimeAgo();
                        if(friends.getStatus().equals("cwaiting")){

                            friendsViewHolder.accept.setVisibility(View.VISIBLE);
                            friendsViewHolder.waiting.setVisibility(View.GONE);
                            friendsViewHolder.make_offer.setVisibility(View.VISIBLE);

                            friendsViewHolder.completed.setVisibility(View.GONE);

                            friendsViewHolder.status_progress.setProgress(0);

                            friendsViewHolder.cancel.setVisibility(View.VISIBLE);


                        }
                        if(friends.getStatus().equals("swaiting")){
                            friendsViewHolder.accept.setVisibility(View.GONE);
                            friendsViewHolder.make_offer.setVisibility(View.GONE);
                            friendsViewHolder.waiting.setVisibility(View.VISIBLE);
                            friendsViewHolder.completed.setVisibility(View.GONE);

                            friendsViewHolder.cancel.setVisibility(View.VISIBLE);


                        }
                if(friends.getStatus().equals("accepted")){
                    friendsViewHolder.accept.setVisibility(View.VISIBLE);
                    friendsViewHolder.status_progress.setProgress(50);

                    friendsViewHolder.make_offer.setVisibility(View.GONE);
                    friendsViewHolder.waiting.setVisibility(View.VISIBLE);
                    friendsViewHolder.accept.setText("Accepted");
                    friendsViewHolder.cancel.setTextColor(getResources().getColor(R.color.white));
                    friendsViewHolder.accept.setEnabled(false);
                    friendsViewHolder.cancel.setBackground(getResources().getDrawable(R.drawable.round_red,null));
                 //   friendsViewHolder.waiting.setText("Customer Expecting Delivery Soon");
                    friendsViewHolder.completed.setVisibility(View.VISIBLE);

                    friendsViewHolder.cancel.setVisibility(View.VISIBLE);


                }
                if(friends.getStatus().equals("completed")){
                    friendsViewHolder.accept.setVisibility(View.GONE);
                    friendsViewHolder.make_offer.setVisibility(View.GONE);
                    friendsViewHolder.waiting.setVisibility(View.GONE);
                    friendsViewHolder.completed.setVisibility(View.GONE);

                    friendsViewHolder.cancel.setVisibility(View.GONE);

                    friendsViewHolder.status_progress.setProgress(100);

                    friendsViewHolder.notsubcriber.setVisibility(View.GONE);
                    friendsViewHolder.yoursubcriber.setVisibility(View.GONE);
                    //friendsViewHolder.remove.setVisibility(View.VISIBLE);
                    friendsViewHolder.call.setVisibility(View.VISIBLE);
                    friendsViewHolder.remove.setVisibility(View.VISIBLE);

                }
                    friendsViewHolder.cancel.setOnClickListener(
                            new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final HashMap<String,Object> notishop=new HashMap<>();

                        notishop.put("status","cancelled");
                        database.child("delivery").child(mAuth.getCurrentUser().getUid()).child(list_user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                database.child("delivery").child(friends.getShop_id()).child(mAuth.getCurrentUser().getUid()+list_user_id).updateChildren(notishop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        database.child("cancelcustomer").child(friends.getShop_id()).push().child("from").setValue(mAuth.getCurrentUser().getUid());


                                    }
                                });


                            }
                        });
                    }
                });

                friendsViewHolder.remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,Object> notishop=new HashMap<>();

                        notishop.put("status","cancelled");
                        database.child("delivery").child(mAuth.getCurrentUser().getUid()).child(list_user_id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {




                            }
                        });
                    }
                });

                friendsViewHolder.duration.setText(friends.getDelivery_time());

                        friendsViewHolder.make_offer.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                final AlertDialog.Builder alert= new AlertDialog.Builder(requireContext());
                                alert.setTitle("Offer a Deal");

                                LayoutInflater inflater=getLayoutInflater();
                                final View view =inflater.inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.accept_order_dialog,null);

                                fpy= (EditText)view.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.efp);
                                fpy.setText(friends.getFinal_price());

                                alert.setPositiveButton("Make Offer", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {


                                        if(!fpy.getText().toString().equals(friends.getFinal_price()) && !fpy.getText().toString().equals("")){
                                            HashMap<String,Object> notishop=new HashMap<>();
                                            notishop.put("status","swaiting");
                                            notishop.put("final_price",fpy.getText().toString());
                                                database.child("delivery").child(mAuth.getCurrentUser().getUid()).child(list_user_id).updateChildren(notishop).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        HashMap<String,Object> notishop2=new HashMap<>();
                                                        notishop2.put("status","swaiting");
                                                        notishop2.put("final_price",fpy.getText().toString());

                                                        database.child("delivery").child(friends.getShop_id()).child(mAuth.getCurrentUser().getUid()+list_user_id).updateChildren(notishop2);

                                                    database.child("notifications_bargain").child(friends.getShop_id()).push().child("from").setValue(mAuth.getCurrentUser().getUid());
                                                        friendsViewHolder.waiting.setVisibility(View.VISIBLE);
                                                        friendsViewHolder.accept.setVisibility(View.GONE);
                                                        friendsViewHolder.make_offer.setVisibility(View.GONE);

                                                    }
                                                });

                                            }
                                        }


                                });

                                alert.setView(view);

                                AlertDialog dialog = alert.create();
                                dialog.show();


                            }
                        });


                friendsViewHolder.accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final AlertDialog.Builder alert = new AlertDialog.Builder(requireContext());
                        alert.setTitle("Set Delivery Time");
                                //.setMessage("");

                        LayoutInflater inflater = getLayoutInflater();
                        final View view = inflater.inflate(R.layout.accept_order_dialog, null);



                        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if(friendsViewHolder.fprice.getText().equals(friends.getFinal_price()) && !friendsViewHolder.fprice.getText().equals("")) {
                                    database.child("delivery").child(mAuth.getCurrentUser().getUid()).child(list_user_id).child("status").setValue("accepted").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            database.child("delivery").child(friends.getShop_id()).child(mAuth.getCurrentUser().getUid()+list_user_id).child("status").setValue("accepted");
                                            database.child("acceptcustomer").child(friends.getShop_id()).push().child("from").setValue(mAuth.getCurrentUser().getUid());
                                            friendsViewHolder.accept.setVisibility(View.GONE);
                                            friendsViewHolder.make_offer.setVisibility(View.GONE);


                                        }
                                    });


                                }





                            }
                        });
                        alert.setView(view);

                        AlertDialog dialog = alert.create();
                        dialog.show();


                    }
                });


                        String lastSeenTime = getTimeAgo.getTimeAgo(lastTime, getActivity());
                        friendsViewHolder.mordertime.setText(lastSeenTime);
                         friendsViewHolder.fprice.setText(friends.getFinal_price());

                        database.child("products").child(list_user_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild("title")) {
                                    String title = dataSnapshot.child("title").getValue().toString();
                                    friendsViewHolder.setTitle(title);

                                }

                                if(dataSnapshot.hasChild("productdp")) {
                                    String title = dataSnapshot.child("productdp").getValue().toString();
                                    friendsViewHolder.setImage(title,requireActivity());

                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                        database.child("users").child(friends.getShop_id()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                               if(dataSnapshot.hasChild("name")) {
                                   final String userName = dataSnapshot.child("name").getValue().toString();

                                   friendsViewHolder.shop_name.setText(userName);

                               }
                                if(dataSnapshot.hasChild("phone_number")) {
                                    final String userName = dataSnapshot.child("phone_number").getValue().toString();

                                   phone_number=userName;
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                };

                rv.setAdapter(friendsRecyclerViewAdapter);




return root;
    }
    public static class FriendsViewHolder extends RecyclerView.ViewHolder {

        private final TextView shop_name;
        private final TextView mordertime;
        private final TextView fprice;
        private final Button make_offer;
        private final Button accept;
        private final CardView waiting;
        private final TextView duration;
        private final Button call,notsubcriber,yoursubcriber;
        private final Button completed;
        private final Button cancel;
        private final Button remove;

        private final ProgressBar status_progress;
        View mView;

        public FriendsViewHolder(View itemView) {
            super(itemView);
            completed=(Button)itemView.findViewById(R.id.complete);
            shop_name=(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.shop_name);
            mordertime=(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.ordertime2);
            fprice=(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.fprice);
              make_offer=(Button)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.make_offer);
            accept=(Button)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.accept);
            waiting=(CardView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.waiting);
            duration=(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.date);
            yoursubcriber=(Button)itemView.findViewById(R.id.yoursubcriber);
            call =(Button)itemView.findViewById(R.id.call);
            notsubcriber=(Button)itemView.findViewById(R.id.notsubscriber);
            remove=(Button)itemView.findViewById(R.id.removed);
            cancel=(Button)itemView.findViewById(R.id.cancel);

            status_progress=(ProgressBar)itemView.findViewById(R.id.progressBar1);

            mView = itemView;

        }



        public void setTitle(String name){

            TextView userNameView = (TextView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.p_name);
            userNameView.setText(name);

        }

        public void setImage(String thumb_image, Context ctx){

            ImageView userImageView = (ImageView) mView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.image);
            Picasso.with(ctx).load(thumb_image).into(userImageView);

        }
    }




    
}