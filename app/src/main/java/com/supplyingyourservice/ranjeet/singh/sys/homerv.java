package com.supplyingyourservice.ranjeet.singh.sys;

public class homerv {
    private String rv;
    public homerv(){}

    @Override
    public String toString() {
        return "homerv{" +
                "rv='" + rv + '\'' +
                '}';
    }

    public String getRv() {
        return rv;
    }

    public void setRv(String rv) {
        this.rv = rv;
    }

    public homerv(String rv) {

        this.rv = rv;
    }
}
