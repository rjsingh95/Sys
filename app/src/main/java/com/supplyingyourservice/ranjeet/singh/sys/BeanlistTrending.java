package com.supplyingyourservice.ranjeet.singh.sys;

/**
 * Created by apple on 15/03/16.
 */
public class BeanlistTrending {

    private String categories;
    private String brand;
    private String details;
    private String price;
    private String product_id;
    private String subcategories;
    private String title;
    private String image;

    public BeanlistTrending(String categories, String brand, String details, String price, String productid, String subcategories, String title, String image) {
        this.categories = categories;
        this.brand = brand;
        this.details = details;
        this.price = price;
        this.product_id = productid;
        this.subcategories = subcategories;
        this.title = title;
        this.image = image;
    }
    public BeanlistTrending(){}

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String productid) {
        this.product_id = product_id;
    }

    public String getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(String subcategories) {
        this.subcategories = subcategories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "BeanlistTrending{" +
                "categories='" + categories + '\'' +
                ", brand='" + brand + '\'' +
                ", details='" + details + '\'' +
                ", price='" + price + '\'' +
                ", productid='" + product_id + '\'' +
                ", subcategories='" + subcategories + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

}