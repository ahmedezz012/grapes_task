
package com.ezz.grapes_task.Models;


public class Product {

    private int id;
    private String productDescription;
    private ImageObject image;
    private double price;

    public Product() {
    }

    public Product(int id, ImageObject image, double price, String productDescription) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.productDescription = productDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public ImageObject getImage() {
        return image;
    }

    public void setImage(ImageObject image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
