package com.supplyingyourservice.ranjeet.singh.sys.elastic;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Query;


public interface booksearchapi {

    @GET("_search/")
    Call<bookobject> search(
            @HeaderMap Map<String, String> headers,
            @Query("default_operator") String operator, //1st query (prepends '?')
            @Query("q") String query //second query (prepends '&')

    );
}
