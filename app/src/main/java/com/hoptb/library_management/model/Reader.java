package com.hoptb.library_management.model;

import com.hoptb.library_management.base.BaseModel;

public class Reader extends BaseModel {
    private int readerId;
    private String readerName;
    private String studentCode;

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public String getReaderName() {
        return readerName;
    }

    public void setReaderName(String readerName) {
        this.readerName = readerName;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    @Override
    public String toString() {
        return readerName;
    }
}
