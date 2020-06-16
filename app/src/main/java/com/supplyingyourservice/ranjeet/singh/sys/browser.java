package com.supplyingyourservice.ranjeet.singh.sys;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.ArrayList;
public class browser extends AppCompatActivity{
    private String[] TITLEG = {
            "Television",
            "ACs",
            "Refrigerators",
            "Air Coolers",
            "Washing Machine",
            "Inverters",
            "Water Purifier",
            "small home appliances",

};
    private ArrayList<BeanlistGrooming> Bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.all_categories);

        RecyclerView rv = (RecyclerView) findViewById(R.id.recyclerView);
        EditText showbrand = (EditText) findViewById(R.id.showbrand);
        showbrand.setVisibility(View.GONE);
        ProgressBar progressrv = (ProgressBar) findViewById(R.id.progressBar2);
        progressrv.setVisibility(View.GONE);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(mLayoutManager);
        Bean = new ArrayList<BeanlistGrooming>();

        for (String aTITLEG : TITLEG) {

            BeanlistGrooming bean = new BeanlistGrooming(aTITLEG);
            Bean.add(bean);

        }


        GroomingRecyclerViewAdapter baseAdapter = new GroomingRecyclerViewAdapter(this, browser.this, Bean,"browser") {
        };
        rv.setAdapter(baseAdapter);

    }


}
