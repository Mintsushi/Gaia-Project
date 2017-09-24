package com.example.round.gaia_18.Data;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.HashMap;

/**
 * Created by Round on 2017-09-06.
 */

public class Plant{

    private int plantNo;
    private int level;
    private Flower flower;
    private ImageView plant;

    //state == 0 : overlayview에 없음
    //state == 1 : overlayview에 있음
    private int state;
    //waterState == 0 : 물을 준 상태
    //waterState == 1 : 물을 주지 않은 상태
    private int waterState = 0;

    //현재 날씨로 얻을 수 있는 패널티 / 패시브
    private int effect;
    private Boolean dryFlowerSetting = false;

    //위치 이동
    private Boolean moving = false;
    private int originalXPos, originalYPos;

    public Plant(int plantNo, int level, Flower flower, ImageView plant) {
        this.plantNo = plantNo;
        this.level = level;
        this.flower = flower;
        this.plant = plant;
    }

    public int getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(int plantNo) {
        this.plantNo = plantNo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ImageView getPlant() {
        return plant;
    }

    public void setPlant(ImageView plant) {
        this.plant = plant;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public Boolean getDryFlowerSetting() {
        return dryFlowerSetting;
    }

    public void setDryFlowerSetting(Boolean dryFlowerSetting) {
        this.dryFlowerSetting = dryFlowerSetting;
    }
}
