package com.example.round.gaia_18.Data;

import android.util.Log;

import com.example.round.gaia_18.MainActivity;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Round on 2017-09-06.
 */

public class Flower {

    private int flowerNo;
    private String flowerName;
    private String flowerImage, flowerButtonImage;
    //꽃을 사려면 필요한 탭 점수랑, 이전 꽃의 레벨
    private int flowerTab, flowerLevel;
    //현재 꽃의 cost와 score
    private ConcurrentHashMap<Integer,Integer> cost = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer,Integer> score = new ConcurrentHashMap<>();

    //꽃 레벨1이 되기 위해 필요한 cost, costType
    private int flowerCost, costType;
    //꽃 레벨1일 때 score, scoreType
    private int flowerScore, scoreType;

    private boolean buyType;
    private boolean buyPossible;
    private int level = 0;

    //overlayView or MainView
    private int where;

    public Flower(){

    }

    public Flower(int flowerNo, String flowerName, String flowerImage, String flowerButtonImage, int flowerCost,
                  int flowerScore, int flowerTab, int flowerLevel,int cost,int score) {
        this.flowerNo = flowerNo;
        this.flowerName = flowerName;
        this.flowerImage = flowerImage;
        this.flowerButtonImage = flowerButtonImage;
        this.flowerTab = flowerTab;
        this.flowerLevel = flowerLevel;
        this.cost.put(cost,flowerCost);
        this.score.put(score,flowerScore);
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

    public boolean isBuyPossible() {
        return buyPossible;
    }

    public void setBuyPossible(boolean buyPossible) {
        this.buyPossible = buyPossible;
    }

    public int getFlowerCost() {
        return flowerCost;
    }

    public void setFlowerCost(int flowerCost) {
        this.flowerCost = flowerCost;
    }

    public int getCostType() {
        return costType;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public int getFlowerScore() {
        return flowerScore;
    }

    public void setFlowerScore(int flowerScore) {
        this.flowerScore = flowerScore;
    }

    public int getScoreType() {
        return scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public void setCost(int type, int cost){
        this.costType = type;
        this.flowerCost = cost;

        int fakeType = 0;
        while(true){
            if(((cost % Math.pow(1000,fakeType+1)) / Math.pow(1000,fakeType)) != 0) {
                this.cost.put(type, (int) (cost % Math.pow(1000, fakeType + 1) / Math.pow(1000, fakeType)));
            }
            if(cost / Math.pow(1000,fakeType)<1000) {
                if((int)(cost / Math.pow(1000,fakeType+1)) !=0)
                    this.cost.put(type + 1, (int)(cost / Math.pow(1000,fakeType+1)));
                break;
            }
            fakeType++;
            type++;
        }
    }

    public ConcurrentHashMap<Integer, Integer> getCost(){return this.cost;}

    public void setScore(int type, int score){
        this.scoreType = type;
        this.flowerScore = score;

        int fakeType = 0;
        while(true) {
            if ((score % Math.pow(1000, fakeType + 1) / Math.pow(1000, fakeType)) != 0)
                this.score.put(type, (int) (score % Math.pow(1000, fakeType + 1) / Math.pow(1000, fakeType)));
            if (score / Math.pow(1000, fakeType + 1) < 1000) {
                if ((int) (score / Math.pow(1000, fakeType + 1)) != 0)
                    this.score.put(type + 1, (int) (score / Math.pow(1000, fakeType + 1)));
                break;
            }
            fakeType++;
            type++;
        }
    }

    public ConcurrentHashMap<Integer, Integer> getScore(){return this.score;}

    public void reset(){
        setCost(this.costType,this.flowerCost);
        setScore(this.scoreType,this.flowerScore);
    }

    public int getWhere() {
        return where;
    }

    public void setWhere(int where) {
        this.where = where;
    }
}
