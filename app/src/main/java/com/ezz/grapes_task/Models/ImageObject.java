
package com.ezz.grapes_task.Models;


public class ImageObject {

    private int width;
    private int height;
    private String url;
    private byte[] image;

    public ImageObject() {
    }

    public ImageObject(int width, int height, byte[] image) {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public ImageObject(String url, int width, int height, byte[] image) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
