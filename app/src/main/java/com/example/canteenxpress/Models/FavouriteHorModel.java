package com.example.canteenxpress.Models;

public class FavouriteHorModel {
    private int image;
    private String name;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FavouriteHorModel(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public FavouriteHorModel() {

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
