package com.supplyingyourservice.ranjeet.singh.sys;

/**
 * Created by Ranjeet on 25-11-2017.
 */

public class news_sample {

   private  String detail;
   private String video;



    private String title;
    private String image;
    public news_sample(){}

    public news_sample(String detail, String video, String title, String image) {
        this.detail = detail;
        this.video = video;
        this.title = title;
        this.image = image;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
        return "news_sample{" +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                '}';
    }



}