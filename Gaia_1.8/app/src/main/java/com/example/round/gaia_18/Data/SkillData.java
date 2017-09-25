package com.example.round.gaia_18.Data;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.OverlayService.dataList;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.MainActivity.seed;

/**
 * Created by Round on 2017-09-17.
 */

public class SkillData {

    private int skillNo;
    private int skillLevel;
    private ConcurrentHashMap<Integer, Integer> cost = new ConcurrentHashMap<>();
    private int skillEffect = 0;
    private int buyType;
    private Boolean skillBuy;

    public SkillData() {
        cost.put(0,0);
    }

    public SkillData(int skillNo, int skillLevel, int skillCost, int costType, int skillEffect, int buyType) {
        this.skillNo = skillNo;
        this.skillLevel = skillLevel;
        cost.put(costType,skillCost);
        this.skillEffect = skillEffect;
        this.buyType = buyType;
    }

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

    public ConcurrentHashMap<Integer, Integer> getCost(){
        return this.cost;
    }

    public void setCost(int costType, int cost){
        this.cost.clear();
        this.cost.put(costType,cost);
    }

    public int getSkillEffect() {
        return skillEffect;
    }

    public void setSkillEffect(int skillEffect) {
        if(dataList.getSkillInfos().get(this.skillNo).getSkillCase() == 3){
            dataList.levelUpSkillType3();
            dataList.startSkillType3(skillEffect);
        }
        this.skillEffect = skillEffect;
    }

    public int getbuyType() {
        return buyType;
    }

    public void setbuyType(int buyType) {
        this.buyType = buyType;
    }

    public Boolean getSkillBuy() {
        return skillBuy;
    }

    public void setSkillBuy(Boolean skillBuy) {
        if(skillBuy && dataList.getSkillInfos().get(this.skillNo).getSkillCase() == 4) {
            mHandler.sendEmptyMessage(0);
        }
        this.skillBuy = skillBuy;
    }

    private android.os.Handler mHandler = new android.os.Handler() {
        public void handleMessage(Message msg) {

            if(dataList.getClickView()==relativeLayout) dataList.windowClick();
            else dataList.overlayWindowClick();
            mOverlayService.setSeed();
            seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));

            mHandler.sendEmptyMessageDelayed(0,60000/skillEffect);
        }
    };
}
