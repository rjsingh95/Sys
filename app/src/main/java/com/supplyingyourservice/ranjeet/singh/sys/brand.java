package com.supplyingyourservice.ranjeet.singh.sys;

public class brand {
    private String brand;
    private String brand_show;

    public brand(String brand, String brand_show) {
        this.brand = brand;
        this.brand_show = brand_show;
    }

    brand(){}

    public String getBrand_show() {
        return brand_show;
    }

    public void setBrand_show(String brand_show) {
        this.brand_show = brand_show;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "brand{" +
                "brand='" + brand + '\'' +
                '}';
    }
}
