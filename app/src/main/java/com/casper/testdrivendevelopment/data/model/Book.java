package com.casper.testdrivendevelopment.data.model;

import java.io.Serializable;

/**
 * Created by jszx on 2019/9/24.
 */

public class Book implements Serializable {
    private String Title;
    private double price;
    private int pictureId;

    public Book(String title, double price, int pictureId) {
        this.Title = title;
        this.price = price;
        this.pictureId = pictureId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }
}
