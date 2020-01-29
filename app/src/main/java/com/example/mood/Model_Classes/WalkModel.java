package com.example.mood.Model_Classes;

public class WalkModel {
    String Title,description;
    int Image;

    public WalkModel(String title, String description, int image) {
        Title = title;
        this.description = description;
        Image = image;
    }

    public String getTitle() {

        return Title;
    }

    public String getDescription() {

        return description;
    }

    public int getImage() {
        return Image;
    }
}
