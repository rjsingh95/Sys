package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.geofire.GeoLocation;
import com.squareup.picasso.Picasso;

import java.util.List;


class shopadapter extends RecyclerView.Adapter<shopsviewholder>{

    private final String product_key;
    private final String s;
    private final Location location;
    private List<shopkeeper_info> matchesList;

    private Context context;





    public shopadapter(List<shopkeeper_info> matchesList, Context context, String product_key, String s, Location location){

        this.matchesList = matchesList;

        this.context = context;
        this.product_key=product_key;
        this.s=s;
this.location=location;

    }



    @Override

    public shopsviewholder onCreateViewHolder(ViewGroup parent, int viewType) {



        View layoutView = LayoutInflater.from(parent.getContext()).inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.ashop, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutView.setLayoutParams(lp);

        shopsviewholder rcv = new shopsviewholder(layoutView);



        return rcv;

    }



    @Override


    public void onBindViewHolder(shopsviewholder holder, final int position) {
        if(position/2==0){
            holder.Rel.setGravity(Gravity.END);
        }else {
            holder.Rel.setGravity(Gravity.START);
        }




        holder.title.setText(matchesList.get(position).getDisplay_name());
        GeoLocation ge=matchesList.get(position).getLoc();
        double lat = ge.latitude;
        double log = ge.longitude;

        Location targetLocation = new Location("");
        targetLocation.setLatitude(lat);
        targetLocation.setLongitude(log);



        double dist=(targetLocation.distanceTo(location))/1000;

        String s = String.format("%.2f",dist);
        holder.distance.setText(s+" Km");


    holder.address.setText(matchesList.get(position).getCity());


        String image =matchesList.get(position).getPhoto_thumb();


        if(image !=null && !image.isEmpty()) {

    Picasso.with(context).load(matchesList.get(position).getPhoto_thumb()).placeholder(R.drawable.loading).into(holder.mMatchImage);
}



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String post_key =matchesList.get(position).getShop_id();
                Intent productdetailsintent = new Intent(context,shopdetails.class);

                Bundle extras = new Bundle();
                extras.putString("shop_id",post_key);
                extras.putString("product_id",product_key);
                extras.putDouble("lat",location.getLatitude());
                extras.putDouble("log",location.getLongitude());
                productdetailsintent.putExtras(extras);
                productdetailsintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(productdetailsintent);
              
            }
        });

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
