package com.supplyingyourservice.ranjeet.singh.sys.model;

/**
 * Created by Ranjeet on 27-03-2018.
 */

public class category {
   private String category;

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "category{" +
                "category='" + category + '\'' +
                '}';
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public category(String category) {

        this.category = category;
    }

    public category(){}


}
