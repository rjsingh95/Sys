package com.supplyingyourservice.ranjeet.singh.sys.elastic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class hitlis {
    @SerializedName("hits")
    @Expose
    private List<booksource> postIndex;


    public List<booksource> getPostIndex() {
        return postIndex;
    }

    public void setPostIndex(List<booksource> postIndex) {
        this.postIndex = postIndex;
    }
}
