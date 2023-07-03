package com.example.uas_travel_app;

public class TravelList {
    private String userName;
    private String destinationName;
    private String destinationImage;
    private String destinationImageAlt;
    private String note;
    private int tlID;

    public TravelList(String userName, String destinationName, String destinationImage, String destinationImageAlt, String note, int tlID) {
        this.userName = userName;
        this.destinationName = destinationName;
        this.destinationImage = destinationImage;
        this.destinationImageAlt = destinationImageAlt;
        this.note = note;
        this.tlID = tlID;
    }

    public String getDestinationImageAlt() {
        return destinationImageAlt;
    }

    public void setDestinationImageAlt(String destinationImageAlt) {
        this.destinationImageAlt = destinationImageAlt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationImage() {
        return destinationImage;
    }

    public void setDestinationImage(String destinationImage) {
        this.destinationImage = destinationImage;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getTlID() {
        return tlID;
    }

    public void setTlID(int tvID) {
        this.tlID = tvID;
    }
}
