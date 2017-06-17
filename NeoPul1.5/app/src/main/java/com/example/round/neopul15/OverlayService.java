package com.example.round.neopul15;

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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Round on 2017-06-13.
 */

public class OverlayService extends Service {

    private static final String TAG = ".overlayShowingService";

    private final IBinder mBinder = new LocalBinder();
    private WindowManager mWindowManager;

    private ArrayList<PlantInfo> mArray = new ArrayList<PlantInfo>();

    private float offsetX;
    private float offsetY;
    private int originalXPos;
    private int originalYPos;
    private boolean moving;

    View topLeftView;

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
    public int onStartCommand(Intent intent,int flags, int startId){
        super.onStartCommand(intent,flags,startId);
        Log.i(TAG,"StartCommand");
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("Gaia Project")
                .setContentText("Playing the Gaia Project")
                .setSmallIcon(R.drawable.icon_light)
                .build();

        startForeground(startId, notification);

        return START_STICKY;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG,"onCreate");
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        Log.i(TAG,"onCreate");
        topLeftView = new View(this);
        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_TOAST, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;
        mWindowManager.addView(topLeftView, topLeftParams);
    }

    public class PlantInfo implements View.OnTouchListener,View.OnClickListener{

        WindowManager.LayoutParams params;
        ImageView plant;
        int id;

        public PlantInfo(WindowManager.LayoutParams params, ImageView image, int id){
            this.params = params;
            this.plant = image;
            this.plant.setOnTouchListener(this);
            this.plant.setOnClickListener(this);
            this.id = id;
            // mWindowManager.addView(this.plant,this.params);
        }

        public WindowManager.LayoutParams getParams(){return this.params;}
        public ImageView getPlant(){return this.plant;}
        public int getId(){return this.id;}

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
            Log.i(TAG,"*************"+motionEvent);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();

                moving = false;

                int[] location = new int[2];
                //overlayedButton.getLocationOnScreen(location);
                view.getLocationOnScreen(location);

                originalXPos = location[0];
                originalYPos = location[1];

                offsetX = originalXPos - x;
                offsetY = originalYPos - y;

            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                int[] topLeftLocationOnScreen = new int[2];
                topLeftView.getLocationOnScreen(topLeftLocationOnScreen);

                System.out.println("topLeftY="+topLeftLocationOnScreen[1]);
                System.out.println("originalY="+originalYPos);

                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();

                WindowManager.LayoutParams params = (WindowManager.LayoutParams) view.getLayoutParams();

                int newX = (int) (offsetX + x);
                int newY = (int) (offsetY + y);

                if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                    return false;
                }

                params.x = newX - (topLeftLocationOnScreen[0]);
                params.y = newY - (topLeftLocationOnScreen[1]);

                mWindowManager.updateViewLayout(view, params);
                moving = true;
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if (moving) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void onClick(View view){
            Toast.makeText(getApplicationContext(),"Click : "+Integer.toString(this.id), Toast.LENGTH_LONG).show();
        }

        public void removeView(){
            mWindowManager.removeView(this.plant);
        }

        public void setView(){
            mWindowManager.addView(this.plant,this.params);
        }
    }

    public void invisible(){
        Log.i(TAG, "********"+mArray.size());
        for(int i =0; i<mArray.size(); i++){
            mArray.get(i).removeView();
        }
    }

    public void visible(){
        for(int i = 0 ;i<mArray.size(); i++){
            mArray.get(i).setView();
        }
    }

    public int getSize(){
        return mArray.size();
    }

    public void removeView(){
        mArray.clear();
    }

    public Boolean onTest(ImageView imageView){
        for(int i =0; i<mArray.size(); i++){
            Log.i(TAG,"*********2  "+mArray.get(i).getId()+"   "+imageView.getId());
            if(mArray.get(i).getId() == imageView.getId())
                return false;
        }
        return true;
    }
    public void onLongClick(final ImageView imageButton){
        Log.i(TAG,"************1  "+imageButton.toString()+"/"+mWindowManager.toString());
        int [] location = new int[2];
        imageButton.getLocationOnScreen(location);
        final ImageView overlayButton = new ImageView(this);
        overlayButton.setImageResource((Integer)imageButton.getTag());
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                imageButton.getWidth(), imageButton.getHeight(),
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT|Gravity.TOP;
        params.x = location[0];
        params.y = location[1];

        Log.i(TAG,"************1  "+overlayButton.toString()+"/"+mWindowManager.toString());

        mWindowManager.addView(overlayButton,params);

        overlayButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i(TAG,"************"+motionEvent.toString());
                    float x = motionEvent.getRawX();
                    float y = motionEvent.getRawY();

                    moving = false;

                    int[] location = new int[2];
                    //overlayedButton.getLocationOnScreen(location);
                    view.getLocationOnScreen(location);

                    originalXPos = location[0];
                    originalYPos = location[1];

                    offsetX = originalXPos - x;
                    offsetY = originalYPos - y;

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    int[] topLeftLocationOnScreen = new int[2];
                    topLeftView.getLocationOnScreen(topLeftLocationOnScreen);

                    System.out.println("topLeftY="+topLeftLocationOnScreen[1]);
                    System.out.println("originalY="+originalYPos);

                    float x = motionEvent.getRawX();
                    float y = motionEvent.getRawY();

                    WindowManager.LayoutParams params = (WindowManager.LayoutParams) view.getLayoutParams();

                    int newX = (int) (offsetX + x);
                    int newY = (int) (offsetY + y);

                    if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !moving) {
                        return false;
                    }

                    params.x = newX - (topLeftLocationOnScreen[0]);
                    params.y = newY - (topLeftLocationOnScreen[1]);

                    mWindowManager.updateViewLayout(view, params);
                    moving = true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    if (moving) {
                        mArray.add(new PlantInfo(params,(ImageView)view,imageButton.getId()));
                        Intent intent = new Intent(OverlayService.this,StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                return false;
            }
        });

    }
}
