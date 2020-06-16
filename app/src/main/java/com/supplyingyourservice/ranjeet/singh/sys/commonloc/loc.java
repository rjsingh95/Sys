package com.supplyingyourservice.ranjeet.singh.sys.commonloc;

public class loc {
    private String cloc;

    public loc() {

    }

    @Override
    public String toString() {
        return "loc{" +
                "cloc='" + cloc + '\'' +
                '}';
    }

    public String getCloc() {
        return cloc;
    }

    public void setCloc(String cloc) {
        this.cloc = cloc;
    }

    public loc(String cloc) {

        this.cloc = cloc;
    }
}
