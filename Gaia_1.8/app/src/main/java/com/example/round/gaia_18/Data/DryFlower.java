package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Round on 2017-09-22.
 */

public class DryFlower {

    private int dryFlowerNo;
    private String dryFlowerName;
    private ConcurrentHashMap<Integer, Integer> score = new ConcurrentHashMap<>();
    private boolean check = false;
    private Plant plant;

    public DryFlower(){}

    public DryFlower(int dryFlowerNo, ConcurrentHashMap<Integer, Integer> score) {
        this.dryFlowerNo = dryFlowerNo;
        this.score = score;
    }

    public int getDryFlowerNo() {
        return dryFlowerNo;
    }

    public void setDryFlowerNo(int dryFlowerNo) {
        this.dryFlowerNo = dryFlowerNo;
    }

    public ConcurrentHashMap<Integer, Integer> getScore() {
        return score;
    }

    public String getDryFlowerName() {
        return dryFlowerName;
    }

    public void setDryFlowerName(String dryFlowerName) {
        this.dryFlowerName = dryFlowerName;
    }

    public void setScore(int type, int score) {

        while(true) {

            int newScore = score %1000;
            this.score.put(type,newScore);

            score /= 1000;
            if(score < 1000){
                if(score != 0) this.score.put(type+1,score);
                break;
            }
            type++;
        }
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }
}
