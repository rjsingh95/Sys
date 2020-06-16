package com.supplyingyourservice.ranjeet.singh.sys.books;

public  class book {

    private  String price;
    private String customer_thumb;
    private String name;
    private String quality;
    private String phone;
    private String time;

    public book(String price, String customer_thumb, String name, String quality, String phone, String time, String customer_key, String address) {
        this.price = price;
        this.customer_thumb = customer_thumb;
        this.name = name;
        this.quality = quality;
        this.phone = phone;
        this.time = time;
        this.customer_key = customer_key;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String customer_key;
    private String address;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomer_key() {
        return customer_key;
    }

    public void setCustomer_key(String customer_key) {
        this.customer_key = customer_key;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public book(String price, String customer_thumb, String name, String quality) {
        this.price = price;
        this.customer_thumb = customer_thumb;
        this.name = name;
        this.quality = quality;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomer_thumb() {
        return customer_thumb;
    }

    public void setCustomer_thumb(String customer_thumb) {
        this.customer_thumb = customer_thumb;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public book() {
    }
}
