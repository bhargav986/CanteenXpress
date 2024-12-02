package com.example.canteenxpress.Models;

public class CartHorModel {
    int id;
    private int image;
    private double price;
    private String name;

    public CartHorModel(int id, int image, double price, String name) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.name = name;
    }

    public CartHorModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
