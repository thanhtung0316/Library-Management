package com.hoptb.library_management.model;

import com.hoptb.library_management.base.BaseModel;

public class Reader extends BaseModel {
    private int readerId;
    private int readerName;
    private String studentCode;

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getReaderName() {
        return readerName;
    }

    public void setReaderName(int readerName) {
        this.readerName = readerName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }
}
