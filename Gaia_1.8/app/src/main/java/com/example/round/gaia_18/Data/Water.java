package com.example.round.gaia_18.Data;

/**
 * Created by 리제 on 2017-09-27.
 */
public class Water {
    private int waterId;
    private int waterPeriod ;
    private int waterPenaltyTime ;
    private int waterPenalty;
    private int waterNeedWaterNum ;

    public Water(int id, int period, int penaltyTime, int penalty, int neddWaterNum){
        waterId = id;
        waterPeriod  = period;
        waterPenaltyTime  = penaltyTime;
        waterPenalty = penalty;
        waterNeedWaterNum  = neddWaterNum;
    }

    public int getWaterId() {
        return waterId;
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

}
