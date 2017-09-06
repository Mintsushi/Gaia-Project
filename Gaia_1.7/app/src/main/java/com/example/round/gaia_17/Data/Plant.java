package com.example.round.gaia_17.Data;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.round.gaia_17.MainActivity;

/**
 * Created by Round on 2017-09-05.
 */

public class Plant implements View.OnTouchListener, View.OnClickListener{

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


    public void setTouchistener(){
        plant.setOnTouchListener(this);
        plant.setOnClickListener(this);
    }

    public void clearTouchListener(){
        plant.setOnTouchListener(null);
    }


    @Override
    public void onClick(View view){}

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            moving = false;

            int [] location = new int[2];
            view.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){

            moving = true;

            int x = (int)motionEvent.getRawX();
            int y = (int)motionEvent.getRawY();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

            if (Math.abs(x - originalXPos) < 1 && Math.abs(y - originalYPos) < 1 && !moving) {
                return false;
            }

            params.leftMargin = x-170;
            params.topMargin = y-150;
            MainActivity.relLayout.updateViewLayout(view,params);
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

            moving = false;
        }
        return false;
    }

}
