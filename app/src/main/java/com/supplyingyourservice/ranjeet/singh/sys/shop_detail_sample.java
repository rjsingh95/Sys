package com.supplyingyourservice.ranjeet.singh.sys;

public class shop_detail_sample {
    private String shop_name;
    private String description;
    private String photo;
    private String delivery;
    private String photoo;
    private String photooo;
    private String phone_number;

    public shop_detail_sample(String shop_name, String description, String photo, String delivery, String photoo, String photooo, String phone_number, String shop_address, String shop_timmings) {
        this.shop_name = shop_name;
        this.description = description;
        this.photo = photo;
        this.delivery = delivery;
        this.photoo = photoo;
        this.photooo = photooo;
        this.phone_number = phone_number;
        this.shop_address = shop_address;
        this.shop_timmings = shop_timmings;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    private String shop_address;
    private String shop_timmings;

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoo() {
        return photoo;
    }

    public void setPhotoo(String photoo) {
        this.photoo = photoo;
    }

    public String getPhotooo() {
        return photooo;
    }

    public void setPhotooo(String photooo) {
        this.photooo = photooo;
    }

    public shop_detail_sample(){}

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_timmings() {
        return shop_timmings;
    }

    public void setShop_timmings(String shop_timmings) {
        this.shop_timmings = shop_timmings;
    }


}
