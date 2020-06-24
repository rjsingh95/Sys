package com.supplyingyourservice.ranjeet.singh.sys.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.supplyingyourservice.ranjeet.singh.sys.aproduct;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface service {

    @GET("products.json")
    Call<JsonObject> getproducts();

}

