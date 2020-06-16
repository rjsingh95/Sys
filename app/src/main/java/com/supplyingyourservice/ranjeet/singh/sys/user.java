package com.supplyingyourservice.ranjeet.singh.sys;

public class user {
    private String shopkeeper_thumb;
    private String name;

    public user(String shopkeeper_thumb, String name) {
        this.shopkeeper_thumb = shopkeeper_thumb;
        this.name = name;

    }


    public String getShopkeeper_thumb() {
        return shopkeeper_thumb;
    }

    public void setShopkeeper_thumb(String shopkeeper_thumb) {
        this.shopkeeper_thumb = shopkeeper_thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    user(){};
}
