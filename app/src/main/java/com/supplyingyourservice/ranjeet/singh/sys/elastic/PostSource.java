package com.supplyingyourservice.ranjeet.singh.sys.elastic;

import com.google.firebase.database.IgnoreExtraProperties;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.supplyingyourservice.ranjeet.singh.sys.addprod;
import com.supplyingyourservice.ranjeet.singh.sys.books.book_sample;

/**
 * Created by Ranjeet on 10/31/2017.
 */

@IgnoreExtraProperties
public class PostSource {

    @SerializedName("_source")
    @Expose
    private addprod post;
private book_sample book;

    public addprod getPost() {
        return post;
    }


    public void setPost(addprod post) {
        this.post = post;
    }
}

