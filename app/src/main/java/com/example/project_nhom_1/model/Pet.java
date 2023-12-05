package com.example.project_nhom_1.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Pet implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("image")
    private String image;

    @SerializedName("color")
    private String color;
    @SerializedName("description")
    private String description;
    @SerializedName("breed")
    private String breed;

    @SerializedName("price")
    private int price;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
