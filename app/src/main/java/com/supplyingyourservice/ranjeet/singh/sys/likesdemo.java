package com.supplyingyourservice.ranjeet.singh.sys;

public class likesdemo {
    private String like;
public likesdemo(){}
    @Override
    public String toString() {
        return "likesdemo{" +
                "like='" + like + '\'' +
                '}';
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public likesdemo(String like) {

        this.like = like;
    }
}
