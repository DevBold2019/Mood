package com.example.mood.walkThrough;

public class WalkObject  {
    String Title,description;
    int Image;

    public WalkObject(String title, String description, int image) {
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
