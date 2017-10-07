package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Round on 2017-10-05.
 */

public class TabData {

    private int skillNo = 0;
    private int skillLevel;
    private ConcurrentHashMap<Integer, Integer> score = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Integer> cost = new ConcurrentHashMap<>();

    private int skillEffect = 6;
    private int buyType = 2;
    private Boolean skillBuy = true;

    private int tabScore, scoreType;
    private int tabCost, costType;

    public int getSkillNo() {
        return skillNo;
    }

    public void setSkillNo(int skillNo) {
        this.skillNo = skillNo;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public ConcurrentHashMap<Integer, Integer> getScore() {
        return score;
    }

    public void setScore(int type, int score){
        this.score.put(type,score);
    }

    public ConcurrentHashMap<Integer, Integer> getCost() {
        return cost;
    }

    public void setCost(int type, int cost) {
        this.cost.put(type,cost);
    }

    public int getSkillEffect() {
        return skillEffect;
    }

    public void setSkillEffect(int skillEffect) {
        this.skillEffect = skillEffect;
    }

    public int getBuyType() {
        return buyType;
    }

    public void setBuyType(int buyType) {
        this.buyType = buyType;
    }

    public Boolean getSkillBuy() {
        return skillBuy;
    }

    public void setSkillBuy(Boolean skillBuy) {
        this.skillBuy = skillBuy;
    }
}
