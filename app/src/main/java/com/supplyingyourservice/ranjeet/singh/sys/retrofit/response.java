package com.supplyingyourservice.ranjeet.singh.sys.retrofit;

import com.google.gson.JsonObject;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;

import java.util.ArrayList;
import java.util.List;

public class response {
    private ArrayList<JsonObject> items;

    public response(ArrayList<JsonObject> items) {
        this.items = items;
    }

    public ArrayList<JsonObject> getItems() {
        return items;
    }

    public void setItems(ArrayList<JsonObject> items) {
        this.items = items;
    }
}
