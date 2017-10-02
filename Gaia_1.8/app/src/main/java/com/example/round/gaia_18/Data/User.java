package com.example.round.gaia_18.Data;

public class User {

    private String userName;
    private String userEmail;
    private String userImage;
    private int DryFlowerItem;

    public User(){ }

    public int getDryFlowerItem() {
        return DryFlowerItem;
    }

    public void setDryFlowerItem(int dryFlowerItem) {
        DryFlowerItem = dryFlowerItem;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }
}
