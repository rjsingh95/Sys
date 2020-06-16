package com.supplyingyourservice.ranjeet.singh.sys;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import com.supplyingyourservice.ranjeet.singh.sys.commonloc.commomloc;


public class news_detail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    private static final int RECOVERY_REQUEST = 1;
    private YouTubePlayerView youTubeView;
    private String link;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail);
        final ImageView image = (ImageView) findViewById(R.id.newsphoto);
        final TextView detail = (TextView) findViewById(R.id.newssub);
        final TextView title = (TextView) findViewById(R.id.news);
        String s = ((commomloc) news_detail.this.getApplication()).getloc();

        youTubeView = (YouTubePlayerView) findViewById(R.id.player);
        youTubeView.initialize(youtubeconfig.getAPI_key(), this);
        String mpost_key = getIntent().getStringExtra("news_id");

        DatabaseReference mUserRef = FirebaseDatabase.getInstance().getReference().child("news_customers").child(s).child(mpost_key);
        mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                news_sample obj=new news_sample();

                obj=dataSnapshot.getValue(news_sample.class);

                if(obj.getImage()!=null){
                    image.setVisibility(View.VISIBLE);
                Picasso.with(news_detail.this).load(obj.getImage()).into(image);
                }else {
                    image.setVisibility(View.GONE);
                }

                if(obj.getVideo()!=null){
                    youTubeView.setVisibility(View.VISIBLE);
                    link=obj.getVideo();
                }
                else {
                    youTubeView.setVisibility(View.GONE);
                }
                detail.setText(obj.getDetail());
                title.setText(obj.getTitle());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(link);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format(("Error"), errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(youtubeconfig.getAPI_key(), this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider() {
        return youTubeView;
    }


}