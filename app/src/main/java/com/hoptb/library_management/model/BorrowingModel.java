package com.hoptb.library_management.model;

import com.hoptb.library_management.base.BaseModel;

public class BorrowingModel extends BaseModel {
    private int brId;
    private int readerId;
    private int bookId;
    private int amount;
    private String brDate;
    private String rtDate;


    public int getBrId() {
        return brId;
    }

    public void setBrId(int brId) {
        this.brId = brId;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBrDate() {
        return brDate;
    }

    public void setBrDate(String brDate) {
        this.brDate = brDate;
    }

    public String getRtDate() {
        return rtDate;
    }

    public void setRtDate(String rtDate) {
        this.rtDate = rtDate;
    }
}
