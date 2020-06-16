package com.supplyingyourservice.ranjeet.singh.sys;

import com.firebase.geofire.GeoLocation;

/**
 * Created by Ranjeet on 11-03-2018.
 */

public class shopkeeper_info {
    private String display_name;
    private String photo_thumb;
    private String shop_id;
    private String city;
    private GeoLocation loc;

    public shopkeeper_info(String display_name, String photo_thumb, String shop_id, String city, GeoLocation loc) {
        this.display_name = display_name;
        this.photo_thumb = photo_thumb;
        this.shop_id = shop_id;
        this.city = city;
        this.loc = loc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public GeoLocation getLoc() {
        return loc;
    }

    public void setLoc(GeoLocation loc) {
        this.loc = loc;
    }

    public shopkeeper_info(){}




    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getDisplay_name() {
        return display_name;
    }


    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    public String getPhoto_thumb() {
        return photo_thumb;
    }

    public void setPhoto_thumb(String photo_thumb) {
        this.photo_thumb = photo_thumb;
    }
    @Override
    public String toString() {
        return "shopkeeper_info{" +
                "display_name='" + display_name + '\'' +
                ", photo_thumb='" + photo_thumb + '\'' +
                ", shop_id='" + shop_id + '\'' +
                '}';
    }
}
