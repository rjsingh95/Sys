package com.supplyingyourservice.ranjeet.singh.sys;

public class order {
    private String delivery_time;
    private String final_price;
    private String status;
    private long time;
    private String shop_id;

    public order(String delivery_time, String final_price, String status, long time, String shop_id) {
        this.delivery_time = delivery_time;
        this.final_price = final_price;
        this.status = status;
        this.time = time;
        this.shop_id = shop_id;
    }

    public order(){}

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    @Override
    public String toString() {
        return "order{" +
                "delivery_time='" + delivery_time + '\'' +
                ", final_price='" + final_price + '\'' +
                ", status='" + status + '\'' +
                ", time=" + time +
                '}';
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getFinal_price() {
        return final_price;
    }

    public void setFinal_price(String final_price) {
        this.final_price = final_price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
