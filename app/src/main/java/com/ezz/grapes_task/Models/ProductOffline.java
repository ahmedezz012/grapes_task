package com.ezz.grapes_task.Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


@Table(name = "ProductOffline")
public class ProductOffline extends Model {

    @Column(name = "productid")
    int productid;
    @Column(name = "price")
    double price;
    @Column(name = "width")
    int width;
    @Column(name = "height")
    int height;
    @Column(name = "description")
    String description;
    @Column(name = "image")
    byte[] image;

    public ProductOffline() {
    }

    public ProductOffline(int productid, double price, String description, int width, int height,byte[] image) {
        this.productid = productid;
        this.price = price;
        this.description = description;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getProductid() {
        return productid;
    }

    public void setProductid(int productid) {
        this.productid = productid;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
