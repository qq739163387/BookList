package com.casper.testdrivendevelopment;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book {
    private String Title;

    public Book(String title, int coverResourceId) {
        setTitle(title);
        setCoverResourceId(coverResourceId);
    }

    private int CoverResourceId;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getCoverResourceId() {
        return CoverResourceId;
    }

    public void setCoverResourceId(int coverResourceId) {
        CoverResourceId = coverResourceId;
    }
}
