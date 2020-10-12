
package com.supplyingyourservice.ranjeet.singh.sys.map;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


@SuppressWarnings("unused")
public class Location implements Serializable {

    @SerializedName("lat")
    private Double Lat;
    @SerializedName("lng")
    private Double Lng;

    public Location() {
    }

    public Location(Double lat, Double lng) {
        this.Lat = lat;
        this.Lng = lng;
    }

    public Double getLat() {
        return Lat;
    }

    public void setLat(Double lat) {
        Lat = lat;
    }

    public Double getLng() {
        return Lng;
    }

    public void setLng(Double lng) {
        Lng = lng;
    }

}
