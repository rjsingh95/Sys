package com.supplyingyourservice.ranjeet.singh.sys;

public class product_detail_sample {
    private String title;
    private String image;
    private String imagee;
    private String imageee;
    private String description;
    private long price;
    private long rating;

    public product_detail_sample(String title, String image, String imagee, String imageee, String description, long price, long rating) {
        this.title = title;
        this.image = image;
        this.imagee = imagee;
        this.imageee = imageee;
        this.description = description;
        this.price = price;
        this.rating = rating;
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

    public product_detail_sample(){

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

    public String getImagee() {
        return imagee;
    }

    public void setImagee(String imagee) {
        this.imagee = imagee;
    }

    public String getImageee() {
        return imageee;
    }

    public void setImageee(String imageee) {
        this.imageee = imageee;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
