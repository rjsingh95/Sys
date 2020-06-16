package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

import com.supplyingyourservice.ranjeet.singh.sys.GetTimeAgo;
import com.supplyingyourservice.ranjeet.singh.sys.R;

class bookadapter extends RecyclerView.Adapter<bookviewholder>{

    private final String customer_key;
    private final String s;
    private final Location current_location;
    private List<book> matchesList;

    private Context context;
    private Activity act;
    private String number;


    public bookadapter(List<book> matchesList, Context context, String product_key, String s, Location location, Activity a){

        this.matchesList = matchesList;

        this.context = context;
        this.customer_key =product_key;
        this.s=s;
        this.current_location =location;
        this.act=a;


    }

    @Override

    public bookviewholder onCreateViewHolder(ViewGroup parent, int viewType) {



        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookseller, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutView.setLayoutParams(lp);

        bookviewholder rcv = new bookviewholder(layoutView);
       return rcv;




    }



    @Override

    public void onBindViewHolder(final bookviewholder holder, final int position) {
            String myString=matchesList.get(position).getName();
        String upperString = myString.substring(0,1).toUpperCase() + myString.substring(1);
        holder.title.setText(upperString);
        Toast.makeText(context, "found", Toast.LENGTH_SHORT).show();

        holder.price.setText("Rs "+matchesList.get(position).getPrice());
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        holder.card.setVisibility(View.VISIBLE);
        holder.linear.setVisibility(View.VISIBLE);





  }
});

if(matchesList.get(position).getTime()!=null) {
    GetTimeAgo getTimeAgo = new GetTimeAgo();

    long lastTime = Long.parseLong(matchesList.get(position).getTime());

    String lastSeenTime = getTimeAgo.getTimeAgo(lastTime, context);

    holder.time.setText("Posted:" +lastSeenTime);
}
holder.address.setText(matchesList.get(position).getAddress());


        String image =matchesList.get(position).getCustomer_thumb();
        if(image !=null && !image.isEmpty()) {

            Picasso.with(context).load(image).placeholder(R.drawable.default_avatar).into(holder.mMatchImage);
        }

        DatabaseReference shopref = FirebaseDatabase.getInstance().getReference().child("customer_locations");
        GeoFire geoFire2 = new GeoFire(shopref);


      geoFire2.getLocation(matchesList.get(position).getCustomer_key(), new com.firebase.geofire.LocationCallback() {
            @Override
            public void onLocationResult(String key, final GeoLocation location) {
if(location!=null) {
    double Lati = location.latitude;
    double logi = location.longitude;

    Location location1 =  new Location(LocationManager.GPS_PROVIDER);
    location1.setLatitude(Lati);
    location1.setLongitude(logi);


    Float dis = (current_location.distanceTo(location1))/1000;
    String dist = String.valueOf(dis);
    String s = String.format("%.2f",dis);
    holder.distance.setText(s+" Km");
}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.navig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final View v=view;
                DatabaseReference shopref = FirebaseDatabase.getInstance().getReference().child("customer_locations");
                GeoFire geoFire2 = new GeoFire(shopref);


                geoFire2.getLocation(matchesList.get(position).getCustomer_key(), new com.firebase.geofire.LocationCallback() {
                    @Override
                    public void onLocationResult(String key, final GeoLocation location) {
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,

                                Uri.parse("http://maps.google.com/maps?saddr="+current_location.getLatitude()+","+current_location.getLongitude()+"&daddr="+location.latitude+","+location.longitude));
                       v.getContext(). startActivity(intent);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        holder.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent productdetailsintent = new Intent(context,customer_loc.class);
                        Bundle extras = new Bundle();
                        extras.putString("shop_id",matchesList.get(position).getCustomer_key());

                        productdetailsintent.putExtras(extras);
                        view.getContext().startActivity(productdetailsintent);



            }
        });

holder.call.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        number = matchesList.get(position).getPhone();
        if (number != null) {
            try {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling`
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    ActivityCompat.requestPermissions(act,
                            new String[]{android.Manifest.permission.CALL_PHONE},   //request specific permission from user
                            10);
                    return;
                }
                view.getContext().startActivity(callIntent);
            } catch (ActivityNotFoundException activityException) {
                Log.e("Calling a Phone Number", "Call failed", activityException);
            }

        } else {
            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show();

        }
    }
});

        holder.quality.setText("Book Condition :"+ matchesList.get(position).getQuality());



    }



    @Override

    public int getItemCount() {

        return this.matchesList.size();

    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
