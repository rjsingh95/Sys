package com.supplyingyourservice.ranjeet.singh.sys.books;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.shopdetails;

public class bookviewholder  extends RecyclerView.ViewHolder implements View.OnClickListener{

   public TextView distance;
   public TextView time;
    public RelativeLayout linear;
    public CardView card;
    public ImageView navig;
    public ImageView location;
    public  ImageView call;
    public TextView quality;
    public TextView price,title;
    public RelativeLayout Rel;

    public TextView address;
    public TextView mMatchId, mMatchName;

    public CircleImageView mMatchImage;

    public bookviewholder(View itemView) {

        super(itemView);


        title = (TextView) itemView.findViewById(R.id.name);
        mMatchImage = (CircleImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Images);
        Rel=(RelativeLayout)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Rel);
        price =(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.price);
        quality=(TextView)itemView.findViewById(R.id.quality);
        distance=(TextView)itemView.findViewById(R.id.distance);
        call=(ImageView)itemView.findViewById(R.id.call);
        location=(ImageView)itemView.findViewById(R.id.location);
        
        linear=(RelativeLayout) itemView.findViewById(R.id.linear);
        address=(TextView)itemView.findViewById(R.id.address);
        time=(TextView)itemView.findViewById(R.id.time);
        card=(CardView)itemView.findViewById(R.id.cardnav);
        navig=(ImageView)itemView.findViewById(R.id.navig);
    }



    @Override

    public void onClick(View view) {

        Intent intent = new Intent(view.getContext(), shopdetails.class);

        Bundle b = new Bundle();

        b.putString("matchId", mMatchId.getText().toString());

        intent.putExtras(b);

        view.getContext().startActivity(intent);

    }


}

