package com.example.round.gaia_18.Data;

import com.example.round.gaia_18.MainActivity;

/**
 * Created by Round on 2017-09-06.
 */

public class Flower {

    private int flowerNo;
    private String flowerName;
    private String flowerImage, flowerButtonImage;
    private int flowerCost, flowerScore;
    private int flowerTab, flowerLevel;
    private int cost, score;
    private boolean buyType;
    private boolean buyPossible;
    private int level = 0;

    public Flower(){

    }

    public Flower(int flowerNo, String flowerName, String flowerImage, String flowerButtonImage, int flowerCost,
                  int flowerScore, int flowerTab, int flowerLevel,int cost,int score) {
        this.flowerNo = flowerNo;
        this.flowerName = flowerName;
        this.flowerImage = flowerImage;
        this.flowerButtonImage = flowerButtonImage;
        this.flowerCost = flowerCost;
        this.flowerScore = flowerScore;
        this.flowerTab = flowerTab;
        this.flowerLevel = flowerLevel;
        this.cost = cost;
        this.score = score;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isBuyPossible() {
        return buyPossible;
    }

    public void setBuyPossible(boolean buyPossible) {
        this.buyPossible = buyPossible;
    }
}
