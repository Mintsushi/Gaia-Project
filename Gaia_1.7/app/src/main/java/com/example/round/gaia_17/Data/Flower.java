package com.example.round.gaia_17.Data;

/**
 * Created by Round on 2017-09-05.
 */

public class Flower {

    private int flowerNo;
    private String flowerName;
    private String flowerImage, flowerButtonImage;
    private int flowerCost, flowerScore;
    private int flowerTab, flowerLevel;
    private boolean buyType;
    private int time; //물을 줘야하는 시간
    private int waterPassiveTime; //물을 주지 않을 시 패시브
    private int passiveRate;
    private int level;

    public Flower(){

    }

    public Flower(int flowerNo, String flowerName, String flowerImage, String flowerButtonImage, int flowerCost,
                  int flowerScore, int flowerTab, int flowerLevel, int time, int waterPassiveTime, int passiveRate) {
        this.flowerNo = flowerNo;
        this.flowerName = flowerName;
        this.flowerImage = flowerImage;
        this.flowerButtonImage = flowerButtonImage;
        this.flowerCost = flowerCost;
        this.flowerScore = flowerScore;
        this.flowerTab = flowerTab;
        this.flowerLevel = flowerLevel;
        this.time = time;
        this.waterPassiveTime = waterPassiveTime;
        this.passiveRate = passiveRate;
    }

    public int getFlowerNo() {
        return flowerNo;
    }

    public void setFlowerNo(int flowerNo) {
        this.flowerNo = flowerNo;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getFlowerImage() {
        return flowerImage;
    }

    public void setFlowerImage(String flowerImage) {
        this.flowerImage = flowerImage;
    }

    public String getFlowerButtonImage() {
        return flowerButtonImage;
    }

    public void setFlowerButtonImage(String flowerButtonImage) {
        this.flowerButtonImage = flowerButtonImage;
    }

    public int getFlowerCost() {
        return flowerCost;
    }

    public void setFlowerCost(int flowerCost) {
        this.flowerCost = flowerCost;
    }

    public int getFlowerScore() {
        return flowerScore;
    }

    public void setFlowerScore(int flowerScore) {
        this.flowerScore = flowerScore;
    }

    public int getFlowerTab() {
        return flowerTab;
    }

    public void setFlowerTab(int flowerTab) {
        this.flowerTab = flowerTab;
    }

    public int getFlowerLevel() {
        return flowerLevel;
    }

    public void setFlowerLevel(int flowerLevel) {
        this.flowerLevel = flowerLevel;
    }

    public boolean isBuyType() {
        return buyType;
    }

    public void setBuyType(boolean buyType) {
        this.buyType = buyType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWaterPassiveTime() {
        return waterPassiveTime;
    }

    public void setWaterPassiveTime(int waterPassiveTime) {
        this.waterPassiveTime = waterPassiveTime;
    }

    public int getPassiveRate() {
        return passiveRate;
    }

    public void setPassiveRate(int passiveRate) {
        this.passiveRate = passiveRate;
    }
}
