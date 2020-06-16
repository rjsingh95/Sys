package com.supplyingyourservice.ranjeet.singh.sys.books;

public class book_sample {
    private String title;
    private String author;
    private String book_image;
    private String book_id;

    public book_sample(String title, String author, String book_image, String book_id) {
        this.title = title;
        this.author = author;
        this.book_image = book_image;
        this.book_id = book_id;
    }

    book_sample(){}

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }
}
