package com.example.uas_travel_app;

public class Profile {

    private String username;
    private String email;
    private String region;
    private String phoneNum;
    private int userID;

    public Profile(String username, String email, String region, String phoneNum, int userID) {
        this.username = username;
        this.email = email;
        this.region = region;
        this.phoneNum = phoneNum;
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
}

