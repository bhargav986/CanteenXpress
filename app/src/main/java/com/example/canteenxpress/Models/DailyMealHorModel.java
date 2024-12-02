package com.example.canteenxpress.Models;

public class DailyMealHorModel {
    private int image;
    private String name;
    private double price;

    public DailyMealHorModel(int image, String name,double price) {
        this.image = image;
        this.name = name;
        this.price = price;
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
