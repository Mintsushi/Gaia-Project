package com.example.round.gaia_18.Data;

/**
 * Created by 리제 on 2017-09-27.
 */
public class Water {
    private int flowerId;
    private int waterPeriod ;
    private int waterPenaltyTime ;
    private int waterPenalty;
    private int waterNeedWaterNum ;

    public Water(){}

    public Water(int id, int period, int penaltyTime, int penalty, int neddWaterNum){
        flowerId = id;
        waterPeriod  = period;
        waterPenaltyTime  = penaltyTime;
        waterPenalty = penalty;
        waterNeedWaterNum  = neddWaterNum;
    }

    public int getFlowerId() {
        return flowerId;
    }
    public int getWaterNeedWaterNum() {
        return waterNeedWaterNum;
    }
    public int getWaterPenalty() {
        return waterPenalty;
    }
    public int getWaterPenaltyTime() {
        return waterPenaltyTime;
    }
    public int getWaterPeriod() {
        return waterPeriod;
    }

    public void setFlowerId(int flowerId) {
        this.flowerId = flowerId;
    }

    public void setWaterPeriod(int waterPeriod) {
        this.waterPeriod = waterPeriod;
    }

    public void setWaterPenaltyTime(int waterPenaltyTime) {
        this.waterPenaltyTime = waterPenaltyTime;
    }

    public void setWaterPenalty(int waterPenalty) {
        this.waterPenalty = waterPenalty;
    }

    public void setWaterNeedWaterNum(int waterNeedWaterNum) {
        this.waterNeedWaterNum = waterNeedWaterNum;
    }
}
