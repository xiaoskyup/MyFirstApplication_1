package com.jnu.student.data;

import java.io.Serializable;

public class Book implements Serializable {
    public String getTitle() {
        return Title;
    }

    public int getCoverResourceId() {
        return CoverResourceId;
    }

    private String Title;         //书名
    private final int CoverResourceId;      //封面图片

    public Book(String Title_, int CoverResourceId_) {
        this.Title=Title_;
        this.CoverResourceId=CoverResourceId_;
    }


    public void setTitle(String title) {
        this.Title = title;
    }
}