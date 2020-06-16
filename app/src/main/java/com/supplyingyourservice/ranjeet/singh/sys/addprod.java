package com.supplyingyourservice.ranjeet.singh.sys;

/**
 * Created by Ranjeet on 25-11-2017.*/

public class addprod {

    private long price;
    private String title;
    private String product_id;
    private String c_b;
    private String brand;
    private String category;
    private long level;
    private long product_code;
    private String max_discount;
    private String search_tags;
    private String productdp;
    private String rating;

    @Override
    public String toString() {
        return "addprod{" +
                "price=" + price +
                ", title='" + title + '\'' +
                ", product_id='" + product_id + '\'' +
                ", c_b='" + c_b + '\'' +
                ", brand='" + brand + '\'' +
                ", category='" + category + '\'' +
                ", level=" + level +
                ", product_code=" + product_code +
                ", max_discount='" + max_discount + '\'' +
                ", search_tags='" + search_tags + '\'' +
                ", productdp='" + productdp + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getLevel() {
        return level;
    }

    public void setLevel(long level) {
        this.level = level;
    }

    public long getProduct_code() {
        return product_code;
    }

    public void setProduct_code(long product_code) {
        this.product_code = product_code;
    }

    public String getSearch_tags() {
        return search_tags;
    }

    public void setSearch_tags(String search_tags) {
        this.search_tags = search_tags;
    }

    public addprod(long price, String title, String product_id, String c_b, String brand, String category, long level, long product_code, String max_discount, String search_tags, String productdp, String rating) {
        this.price = price;
        this.title = title;
        this.product_id = product_id;
        this.c_b = c_b;
        this.brand = brand;
        this.category = category;
        this.level = level;
        this.product_code = product_code;
        this.max_discount = max_discount;
        this.search_tags = search_tags;
        this.productdp = productdp;
        this.rating = rating;
    }

    public String getProductdp() {
        return productdp;
    }

    public void setProductdp(String productdp) {
        this.productdp = productdp;
    }

    public String getC_b() {
        return c_b;
    }

    public void setC_b(String c_b) {
        this.c_b = c_b;
    }

    public String getMax_discount() {
        return max_discount;
    }

    public void setMax_discount(String max_discount) {
        this.max_discount = max_discount;
    }


    public String getRating() {
        return rating;

    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public addprod(){}

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}