package com.supplyingyourservice.ranjeet.singh.sys.elastic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.supplyingyourservice.ranjeet.singh.sys.books.book_sample;

public class booksource {


    @SerializedName("_source")
    @Expose

    private book_sample book;


    public book_sample getBook() {
        return book;
    }

    public void setBook(book_sample post) {
        this.book = post;
    }
}
