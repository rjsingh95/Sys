package com.supplyingyourservice.ranjeet.singh.sys;

public class offer_sample {
    private String product_id;
    private String discount;

    public offer_sample(){

    }

    public offer_sample(String product_id, String discount) {
        this.product_id = product_id;
        this.discount = discount;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
