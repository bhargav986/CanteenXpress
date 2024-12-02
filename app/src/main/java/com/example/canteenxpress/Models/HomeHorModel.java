package com.example.canteenxpress.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeHorModel implements Parcelable {
    private int image;
    private double price;
    private String name;
    private boolean isLiked; // New field

    public HomeHorModel(int image, double price, String name) {
        this.image = image;
        this.price = price;
        this.name = name;
        this.isLiked = false; // Default to false
    }

    protected HomeHorModel(Parcel in) {
        image = in.readInt();
        price = in.readDouble();
        name = in.readString();
        isLiked = in.readByte() != 0; // Read boolean value
    }

    public static final Creator<HomeHorModel> CREATOR = new Creator<HomeHorModel>() {
        @Override
        public HomeHorModel createFromParcel(Parcel in) {
            return new HomeHorModel(in);
        }

        @Override
        public HomeHorModel[] newArray(int size) {
            return new HomeHorModel[size];
        }
    };

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

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeDouble(price);
        dest.writeString(name);
        dest.writeByte((byte) (isLiked ? 1 : 0)); // Write boolean value
    }
}
