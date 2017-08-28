package com.example.round.gaia_17;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Round on 2017-08-28.
 */

public class OverlayService extends Service {

    private static final String TAG =".OverlayService";
    private final IBinder mBinder = new LocalBinder();

    //OverLay Service
    private WindowManager mWindowManager;
    private View topLeftView;

    private float offsetX,offsetY;
    private int originalX, originalY;
    private boolean moving;

    private ArrayList<OverLayPlantInfo> mArray = new ArrayList<OverLayPlantInfo>();

    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    public class LocalBinder extends Binder {
        OverlayService getService(){
            return OverlayService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);

        Log.i(TAG,"onStartCommand");
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Gaia Project")
                .setContentText("Playing the Gaia Project")
                .setSmallIcon(R.drawable.image)
                .build();

        startForeground(startId,notification);

        return START_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        Log.i(TAG,"onCreateService");

        mWindowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);

        topLeftView = new View(this);
        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;

        mWindowManager.addView(topLeftView,topLeftParams);
    }

    public class OverLayPlantInfo implements View.OnTouchListener,View.OnClickListener{

        private WindowManager.LayoutParams params;
        private ImageView plant;
        private int id;
        private MainActivity.PlantInfo plantInfo;


        public OverLayPlantInfo(WindowManager.LayoutParams params, ImageView image, int id, MainActivity.PlantInfo plantInfo){
            Log.i(TAG,"ADD OverLayPlantInfo");
            this.params = params;
            this.plant = image;
            this.plant.setOnTouchListener(this);
            this.plantInfo = plantInfo;
            this.id = id;
        }

        public WindowManager.LayoutParams getParams(){return this.params;}
        public View getPlant(){return this.plant;}
        public int getId(){return this.id;}

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                moving = false;

                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();

                int[] location = new int[2];
                view.getLocationOnScreen(location);

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

                WindowManager.LayoutParams params = (WindowManager.LayoutParams)view.getLayoutParams();

                int newX = (int)(offsetX + x);
                int newY = (int)(offsetY + y);

                if (Math.abs(newX - originalX) < 1 && Math.abs(newY - originalY) < 1 && !moving) {
                    return false;
                }

                params.x = newX - (topLeftLocationOnScreen[0]);
                params.y = newY - (topLeftLocationOnScreen[1]);

                mWindowManager.updateViewLayout(view, params);
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

        public void removeView(){
            mWindowManager.removeView(this.plant);
        }

        public void setView(){
            mWindowManager.addView(this.plant,this.params);
        }
    }

    public void invisible(){
        for(int i = 0 ; i<mArray.size();i++){
            mArray.get(i).removeView();
        }
    }

    public void visible(){
        for(int i =0 ; i<mArray.size();i++){
            mArray.get(i).setView();
        }
    }

    public int getSize(){
        return mArray.size();
    }

    public void removePlant(int id){
        for(int i =0 ;i<mArray.size();i++){
            if(mArray.get(i).getId() == id){
                mArray.remove(i);
            }
        }
    }

    public Boolean onTest(int plantID){
        for(int i =0 ;i<mArray.size();i++){
            if(mArray.get(i).getId() == plantID){
                return false;
            }
        }
        return true;
    }

    public void addPlant(MainActivity.PlantInfo plant){

        View flower = plant.getPlant();
        int []location = new int[2];
        flower.getLocationOnScreen(location);

        ImageView overlayPlant = new ImageView(this);
        overlayPlant.setImageResource((Integer)flower.getTag());

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                flower.getWidth(),flower.getHeight(),
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.LEFT|Gravity.TOP;
        params.x = location[0];
        params.y = location[1];

        mArray.add(new OverLayPlantInfo(params,overlayPlant,plant.getId(),plant));
    }
}
