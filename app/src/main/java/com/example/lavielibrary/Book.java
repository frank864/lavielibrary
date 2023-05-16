package com.example.lavielibrary;

public class Book {
    private  int id;
    private String name;
    private String author;
    private String imageurl;
    private String description;

    public Book( String name, String author, String imageurl, String description) {

        this.name = name;
        this.author = author;
        this.imageurl = imageurl;
        this.description = description;
    }


    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
