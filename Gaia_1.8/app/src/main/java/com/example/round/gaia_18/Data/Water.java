package com.example.round.gaia_18.Data;

/**
 * Created by 리제 on 2017-09-27.
 */
public class Water {
    private int waterKey;
    private int waterId;
    private int waterPeriod ;
    private int waterPenaltyTime ;
    private int waterPenalty;
    private int waterNeedWaterNum ;

    public Water(int key, int id, int period, int penaltyTime, int penalty, int neddWaterNum){
        waterKey = key;
        waterId = id;
        waterPeriod  = period;
        waterPenaltyTime  = penaltyTime;
        waterPenalty = penalty;
        waterNeedWaterNum  = neddWaterNum;
    }

    public int getWaterId() {
        return waterId;
    }
    public int getWaterKey() {
        return waterKey;
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
