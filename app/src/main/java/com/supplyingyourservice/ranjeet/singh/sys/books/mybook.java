package com.supplyingyourservice.ranjeet.singh.sys.books;

public class mybook {


    private String price;
    private String quality;
    private long time;
    public mybook(){}

    public mybook(String price, String quality, long time) {
        this.price = price;
        this.quality = quality;
        this.time = time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public mybook(String price) {

        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }


}
