package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Round on 2017-09-23.
 */

public class GoalData {

    private int goalNo;
    private int goalLevel;
    private int goalCondition;
    private int goalConditionType;
    private int goalRewardType;
    private int goalReward;
    private int goalRewardCostType;
    private int goalRate;
    public GoalData(){}

    public GoalData(int goalNo, int goalLevel, int goalCondition, int goalConditionType, int goalRewardType, int goalReward, int goalRewardCostType, int goalRate) {
        this.goalNo = goalNo;
        this.goalLevel = goalLevel;
        this.goalCondition = goalCondition;
        this.goalConditionType = goalConditionType;
        this.goalRewardType = goalRewardType;
        this.goalReward = goalReward;
        this.goalRewardCostType = goalRewardCostType;
        this.goalRate = goalRate;
    }

    public int getGoalNo() {
        return goalNo;
    }

    public void setGoalNo(int goalNo) {
        this.goalNo = goalNo;
    }

    public int getGoalLevel() {
        return goalLevel;
    }

    public void setGoalLevel(int goalLevel) {
        this.goalLevel = goalLevel;
    }

    public int getGoalCondition() {
        return goalCondition;
    }

    public void setGoalCondition(int goalCondition) {
        this.goalCondition = goalCondition;
    }

    public int getGoalRewardType() {
        return goalRewardType;
    }

    public void setGoalConditionType(int goalConditionType){
        this.goalConditionType = goalConditionType;
    }

    public void setGoalRewardType(int goalRewardType) {
        this.goalRewardType = goalRewardType;
    }

    public int getGoalReward() {
        return goalReward;
    }

    public void setGoalReward(int goalReward) {
        this.goalReward = goalReward;
    }

    public int getGoalRewardCostType() {
        return goalRewardCostType;
    }

    public void setGoalRewardCostType(int goalRewardCostType) {
        this.goalRewardCostType = goalRewardCostType;
    }

    public int getGoalRate() {
        return goalRate;
    }

    public void setGoalRate(int goalRate) {
        this.goalRate += goalRate;
    }

    public ConcurrentHashMap<Integer, Integer> getConditionType(){

        ConcurrentHashMap<Integer, Integer> condition = new ConcurrentHashMap<>();

        condition.put(this.goalConditionType, this.goalCondition);
        return condition;
    }

}
