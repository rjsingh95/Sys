package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;
    private FirebaseAuth mAuth;
    private Context ctx;

    public MessageAdapter(List<Messages> mMessageList,Context ctx) {

        this.mMessageList = mMessageList;
        this.ctx=ctx;

    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(com.supplyingyourservice.ranjeet.singh.sys.R.layout.message_single_layout ,parent, false);

        return new MessageViewHolder(v);

    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        private final RelativeLayout card;
        private final RelativeLayout rel;
        private final TextView time_text;
        private final TextView time_image;
        public TextView messageText;

        public TextView displayName;
        public ImageView messageImage;

        public MessageViewHolder(View view) {

            super(view);

            messageText = (TextView) view.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.message_text_layout);

            displayName = (TextView) view.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.name_text_layout);
            messageImage = (ImageView) view.findViewById(com.supplyingyourservice.ranjeet.singh.sys.R.id.message_image_layout);
            card=(RelativeLayout)view.findViewById(R.id.rel);
            rel=(RelativeLayout)view.findViewById(R.id.message_single_layout);
            time_text = (TextView) view.findViewById(R.id.time_text_layout);
            time_image = (TextView) view.findViewById(R.id.time_image);


        }
    }

    @Override
    public void onBindViewHolder(final MessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);

        String from_user = c.getFrom();
        String message_type = c.getType();
        mAuth=FirebaseAuth.getInstance();
        ;

    if (from_user.equals(mAuth.getCurrentUser().getUid())) {
        viewHolder.displayName.setTextColor(Color.parseColor("#ffffff"));

        viewHolder.messageText.setTextColor(Color.parseColor("#ffffff"));
        viewHolder.card.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_blue));

        viewHolder.rel.setGravity(Gravity.END);

    mUserDatabase = FirebaseDatabase.getInstance().getReference().child("customers").child(from_user);

    mUserDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

    if (dataSnapshot.hasChild("name")) {
      String name = dataSnapshot.child("name").getValue().toString();
      viewHolder.displayName.setText(name);
  } }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    if (message_type.equals("text")) {
        viewHolder.messageText.setVisibility(View.VISIBLE);
        viewHolder.messageText.setText(c.getMessage());

        viewHolder.displayName.setVisibility(View.VISIBLE);

        viewHolder.messageImage.setVisibility(View.GONE);

        String time = String.valueOf(c.getTime());
        viewHolder.time_text.setVisibility(View.VISIBLE);
        viewHolder.time_image.setVisibility(View.GONE);
        if(time!=null) {
            long lastTime = Long.parseLong(time);
            GetTimeAgo getTimeAgo = new GetTimeAgo();

            String lastSeenTime = getTimeAgo.getTimeAgo(lastTime,ctx );
            viewHolder.time_text.setText(lastSeenTime);
        }


        viewHolder.displayName.setVisibility(View.VISIBLE);
        viewHolder.messageImage.setVisibility(View.GONE);




    } else {
        viewHolder.messageText.setVisibility(View.GONE);
        viewHolder.displayName.setVisibility(View.GONE);
        viewHolder.time_text.setVisibility(View.GONE);
        viewHolder.time_image.setVisibility(View.VISIBLE);
        viewHolder.messageImage.setVisibility(View.VISIBLE);
        viewHolder.card.setPadding(5,5,5,5);
        String time = String.valueOf(c.getTime());
        if(time!=null) {
            long lastTime = Long.parseLong(time);
            GetTimeAgo getTimeAgo = new GetTimeAgo();

            String lastSeenTime = getTimeAgo.getTimeAgo(lastTime,ctx );
            viewHolder.time_image.setText(lastSeenTime);
        }

        Picasso.with(viewHolder.messageImage.getContext()).load(c.getMessage())
                .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);




    }
}else {

        viewHolder.card.setBackground(ContextCompat.getDrawable(ctx, R.drawable.round_grey));

        viewHolder.displayName.setTextColor(Color.parseColor("#677174"));
        viewHolder.messageText.setTextColor(Color.parseColor("#616e7f"));

        viewHolder.rel.setGravity(Gravity.START);
    mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(from_user);

    mUserDatabase.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
          user obj=new user();
            obj = dataSnapshot.getValue(user.class);

            assert obj != null;
            if(obj.getName()!=null){
            viewHolder.displayName.setText(obj.getName());
            }else {
                viewHolder.displayName.setText("No Profile Name");

            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });

    if (message_type.equals("text")) {
        viewHolder.messageText.setVisibility(View.VISIBLE);
        viewHolder.displayName.setVisibility(View.VISIBLE);

        viewHolder.messageText.setText(c.getMessage());
        viewHolder.messageImage.setVisibility(View.GONE);

        viewHolder.time_text.setVisibility(View.VISIBLE);
        viewHolder.time_image.setVisibility(View.GONE);

        String time = String.valueOf(c.getTime());
        if(time!=null) {
            long lastTime = Long.parseLong(time);
            GetTimeAgo getTimeAgo = new GetTimeAgo();

            String lastSeenTime = getTimeAgo.getTimeAgo(lastTime,ctx );
            viewHolder.time_text.setText(lastSeenTime);
        }

    } else { viewHolder.card.setPadding(3,3,3,3);

        viewHolder.messageText.setVisibility(View.GONE);
        viewHolder.displayName.setVisibility(View.GONE);
        viewHolder.messageImage.setVisibility(View.VISIBLE);

        Picasso.with(viewHolder.messageImage.getContext()).load(c.getMessage())
                .placeholder(R.drawable.default_avatar).into(viewHolder.messageImage);
        viewHolder.time_text.setVisibility(View.GONE);
        viewHolder.time_image.setVisibility(View.VISIBLE);

        String time = String.valueOf(c.getTime());
        if(time!=null) {
            long lastTime = Long.parseLong(time);
            GetTimeAgo getTimeAgo = new GetTimeAgo();

            String lastSeenTime = getTimeAgo.getTimeAgo(lastTime,ctx );
            viewHolder.time_text.setText(lastSeenTime);
        }
    }

}

    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }






}
