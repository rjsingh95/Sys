package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

  public class shopsviewholder  extends RecyclerView.ViewHolder implements View.OnClickListener{

   public RelativeLayout Rel;

    public TextView address;
    public TextView title, mMatchName;
    public TextView distance;


        public ImageView mMatchImage;

        public shopsviewholder(View itemView) {

            super(itemView);


            title = (TextView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Title);
            mMatchImage = (ImageView) itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Image);
            Rel=(RelativeLayout)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.Rel);
            address =(TextView)itemView.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.adress);
            distance =(TextView)itemView.findViewById(R.id.distance);

        }



        @Override

        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), shopdetails.class);

            Bundle b = new Bundle();

            b.putString("matchId", title.getText().toString());

            intent.putExtras(b);

            view.getContext().startActivity(intent);

        }


}

