package com.supplyingyourservice.ranjeet.singh.sys;

public class aproduct {
    private String title;
    private String productdp;
    private long  price;
    private long rating;
    private String product_id;
    private long level;
private String max_discount;

  public aproduct(){}

    public aproduct(String title, String productdp, long price, long rating, String product_id, long level, String max_discount) {
        this.title = title;
        this.productdp = productdp;
        this.price = price;
        this.rating = rating;
        this.product_id = product_id;
        this.level = level;
        this.max_discount = max_discount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProductdp() {
        return productdp;
    }

    public void setProductdp(String productdp) {
        this.productdp = productdp;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getRating() {
        return rating;
    }

    public void setRating(long rating) {
        this.rating = rating;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public String getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(String max_discount) {
        this.max_discount = max_discount;
    }
}
