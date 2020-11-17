package com.hoptb.library_management.model;

import com.hoptb.library_management.base.BaseModel;

public class Book extends BaseModel {

    private int bookId;
    private String bookType;
    private String author;
    private String publisher;
    private String bookName;
    private String image;
    private String description;

    public Book(String bookType, String author, String publisher, String bookName, String image, String description) {
        this.bookType = bookType;
        this.author = author;
        this.publisher = publisher;
        this.bookName = bookName;
        this.image = image;
        this.description = description;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookType() {
        return bookType;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
