package com.zt.recordmanager.model.bean;

public class MainGridBean {
    private String name;
    private int image;
    private int color;
    private Class o;


    public MainGridBean(String name, int image, int color, Class o) {
        this.name = name;
        this.image = image;
        this.color = color;
        this.o = o;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Class getO() {
        return o;
    }

    public void setO(Class o) {
        this.o = o;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
