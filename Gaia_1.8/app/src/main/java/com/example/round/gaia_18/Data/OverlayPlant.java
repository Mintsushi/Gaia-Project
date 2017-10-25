package com.example.round.gaia_18.Data;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.round.gaia_18.MainActivity;
import com.example.round.gaia_18.OverlayService;

/**
 * Created by Round on 2017-09-06.
 */

public class OverlayPlant implements View.OnClickListener,View.OnTouchListener{

    private Plant plant;
    private WindowManager.LayoutParams params;
    private LinearLayout overlayPlant;
    private LinearLayout itemLayout;
    private View topLeftView;
    private WindowManager windowManager;
    private ImageView plantImage;
    private ProgressBar overlayPlantHP;
    private ImageView overlayPlantWater;
    private RelativeLayout  overlayPlantLayout;
    private LinearLayout alarm;
    private Context context;

    private float offsetX, offsetY;
    private int originalX, originalY;
    private boolean moving;

    public OverlayPlant(Plant plant, Context context, LinearLayout itemLayout, LinearLayout alarm,ImageView plantImage,
                        LinearLayout overlayPlant, WindowManager.LayoutParams params,
                        View topLeftView, WindowManager windowManager) {
        this.plant = plant;
        this.overlayPlant = overlayPlant;
        this.itemLayout = itemLayout;
        this.alarm = alarm;
        this.context = context;

        plantImage.setOnClickListener(this);
        plantImage.setOnTouchListener(this);

        this.topLeftView = topLeftView;
        this.windowManager = windowManager;

        this.params = params;

    }

    public LinearLayout getItemLayout(){return this.itemLayout;}
    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public LinearLayout getOverlayPlant() {
        return overlayPlant;
    }

    public void setOverlayPlant(LinearLayout overlayPlant) {
        this.overlayPlant = overlayPlant;
    }

    public WindowManager.LayoutParams getParams() {
        return params;
    }

    public void setParams(WindowManager.LayoutParams params) {
        this.params = params;
    }

    public void setAlarm(String title, String memo){
        TextView mail = new TextView(context);
        mail.setText(title+"\n"+memo);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        this.alarm.addView(mail,params);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){

        Log.i("onTouch","motion : "+motionEvent.toString());
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            moving = false;

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            int[] location = new int[2];
            overlayPlant.getLocationOnScreen(location);

            originalX = location[0];
            originalY = location[1];
            offsetX = originalX - x;
            offsetY = originalY - y;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            int[] topLeftLocationOnScreen = new int[2];
            topLeftView.getLocationOnScreen(topLeftLocationOnScreen);

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            WindowManager.LayoutParams params = (WindowManager.LayoutParams)overlayPlant.getLayoutParams();

            int newX = (int)(offsetX + x);
            int newY = (int)(offsetY + y);

            if (Math.abs(newX - originalX) < 1 && Math.abs(newY - originalY) < 1 && !moving) {
                return false;
            }

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            windowManager.updateViewLayout(overlayPlant, params);
            moving = true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            if(moving){
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View view){}
}
