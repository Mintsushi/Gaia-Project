package com.example.round.gaia_18;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.round.gaia_18.Data.OverlayPlant;
import com.example.round.gaia_18.Data.Plant;

import java.util.ArrayList;

import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.relativeLayout;

public class OverlayService extends Service implements View.OnClickListener,View.OnTouchListener{

    private static final String TAG = ".OverlayService";
    private final IBinder mBinder = new LocalBinder();

    //Overlay View Service
    private WindowManager mWindowManager;
    private View toLeftView;

    //view Move
    private float offsetX, offsetY;
    private int originalX, originalY;
    private boolean moving;
    private boolean enalbeOverlayService = false;

    @Override
    public IBinder onBind(Intent intent){ return mBinder; }

    public class LocalBinder extends Binder {
        OverlayService getService(){ return OverlayService.this; }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);

        //후에 icon은 app logo를 이용
        Notification noti = new NotificationCompat.Builder(this)
                .setContentTitle("Gaia Project")
                .setContentText("Playing the Gaia Project")
                .setSmallIcon(R.drawable.image)
                .build();

        startForeground(startId,noti);
        return START_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);

        toLeftView = new View(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.width = 0;
        params.height = 0;

        mWindowManager.addView(toLeftView, params);
    }

    public void invisible(){
        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

        for(int i = 0 ; i<plants.size();i++){
            mWindowManager.removeView(plants.get(i).getOverlayPlant());
        }
    }

    public void visible(){
        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

        for(int i =0 ; i<plants.size();i++){
            mWindowManager.addView(plants.get(i).getOverlayPlant(),plants.get(i).getParams());
        }
    }

    public int getSize(){
        return dataList.getOverlayPlants().size();
    }

    public void removePlant(int id){
        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

        for(int i =0 ;i<plants.size();i++){
            if(plants.get(i).getPlant().getPlantNo() == id){
                relativeLayout.addView(plants.get(i).getPlant().getPlant());
                plants.remove(i);
            }
        }
    }

    public void addPlantToOverlay(Plant plant){

        relativeLayout.removeView(plant.getPlant());

        ImageView exPlant = plant.getPlant();

        int[] location = new int[2];
        exPlant.getLocationOnScreen(location);

        ImageView overlayPlant = new ImageView(this);
        //overlayPlant.setImageResource(plant.getFlower().getImage());
        overlayPlant.setImageResource(R.drawable.image);

        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                exPlant.getWidth(),exPlant.getHeight(),
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = location[0];
        params.y = location[1];

        overlayPlant.setOnClickListener(this);
        overlayPlant.setOnTouchListener(this);

        plant.setState(1);
        dataList.addOverlayPlant(new OverlayPlant(plant,overlayPlant, params));
    }

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
            toLeftView.getLocationOnScreen(topLeftLocationOnScreen);

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

}
