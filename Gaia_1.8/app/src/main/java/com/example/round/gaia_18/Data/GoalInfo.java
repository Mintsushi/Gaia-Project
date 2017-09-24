package com.example.round.gaia_18.Data;

/**
 * Created by Round on 2017-09-23.
 */

public class GoalInfo {

    private int id;
    private String goalName;
    private int goalType;
    private int goalMaxLevel;

    public GoalInfo(){}

    public GoalInfo(int id, String goalName, int goalType, int goalMaxLevel) {
        this.id = id;
        this.goalName = goalName;
        this.goalType = goalType;
        this.goalMaxLevel = goalMaxLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public int getGoalType() {
        return goalType;
    }

    public void setGoalType(int goalType) {
        this.goalType = goalType;
    }

    public int getGoalMaxLevel() {
        return goalMaxLevel;
    }

    public void setGoalMaxLevel(int goalMaxLevel) {
        this.goalMaxLevel = goalMaxLevel;
    }
}
