package com.example.uas_travel_app;

public class Destination {

    private String name;
    private String price;
    private String image;
    private String imageAlt;
    private double rating;
    private int destinationID;
    private String Description;

    public Destination(){

    }

    public Destination(String name, String price, String image, double rating, int destinationID) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.rating = rating;
        this.destinationID = destinationID;
    }

    public Destination(String name, String price, String image, String imageAlt, double rating, String Description) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.imageAlt = imageAlt;
        this.rating = rating;
        this.Description = Description;
    }

    public String getImageAlt() {
        return imageAlt;
    }

    public void setImageAlt(String imageAlt) {
        this.imageAlt = imageAlt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(int destinationID) {
        this.destinationID = destinationID;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
