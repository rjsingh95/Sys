package com.supplyingyourservice.ranjeet.singh.sys.retrofit;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.supplyingyourservice.ranjeet.singh.sys.R;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;

import java.util.ArrayList;


public class repoadapter extends RecyclerView.Adapter<repoadapter.ViewHolder>{

    private static final String TAG = "PostListAdapter";
    private static final int NUM_GRID_COLUMNS = 3;

    private ArrayList<aproduct> mPosts=new ArrayList<>();
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView author;

        public ViewHolder(View itemView) {
            super(itemView);
            author = (TextView) itemView.findViewById(R.id.Title);

        }
    }

    public repoadapter(Context context, ArrayList<aproduct> posts) {
        mPosts = posts;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_productlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.author.setText(mPosts.get(position).getTitle()
        );



    }

    @Override
    public int getItemCount() {

        Log.d("chacklist", String.valueOf(mPosts.size()));
        return mPosts.size();
    }


}













