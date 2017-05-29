package com.example.round.overlay2;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener{

    private OverlayService mOverlayService;
    private Intent overLayService;

    private Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

    private MotionEvent motionEvent;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("MainActivity","onServiceConnection");
            mOverlayBinder = iBinder;
            mOverlayService = ((OverlayService.LocalBinder) iBinder).getService();
            mConnected = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("MainActivity","onServiceDIsconencted");
            //mConnected = false;
        }
    };

    @Override
    public boolean onLongClick(View view){

        if(mOverlayService.onTest((ImageView)findViewById(view.getId()))) {
            mOverlayService.onLongClick((ImageView) findViewById(view.getId()));
            finish();
        }

        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overLayService = new Intent(MainActivity.this, OverlayService.class);
        if(!isServiceRunning(OverlayService.class)) {
            bindService(overLayService, mServiceConnection, BIND_AUTO_CREATE);
            startService(overLayService);
        }

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mConnected){
                    if(mOverlayService.getSize() >0){
                        mOverlayService.removeView();
                        mClear = true;
                    }
                }
            }
        });

        ImageView imageButton1 = (ImageView)findViewById(R.id.imageButton1);
        imageButton1.setTag(R.drawable.icon_light);
        imageButton1.setOnLongClickListener(this);

        ImageView imageButton2 = (ImageView)findViewById(R.id.imageButton2);
        imageButton2.setTag(R.drawable.icon_logout);
        imageButton2.setOnLongClickListener(this);

        ImageView imageButton3 = (ImageView)findViewById(R.id.imageButton3);
        imageButton3.setTag(R.drawable.icon_mode);
        imageButton3.setOnLongClickListener(this);

        ImageView imageButton4 = (ImageView)findViewById(R.id.imageButton4);
        imageButton4.setTag(R.drawable.icon_setting);
        imageButton4.setOnLongClickListener(this);

        ImageView imageButton5 = (ImageView)findViewById(R.id.imageButton5);
        imageButton5.setTag(R.drawable.icon_smartwatch);
        imageButton5.setOnLongClickListener(this);
    }

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager)getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        Log.i("MainActivity","isServiceRunning : "+mOverlayService);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("MainActivity", true+"");
                Log.i("MainActivity","*************"+mOverlayBinder);
                mOverlayService = ((OverlayService.LocalBinder) mOverlayBinder).getService();
                mConnected = true;
                return true;
            }
        }
        Log.i ("MainActivity", false+"");
        return false;
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i("MainActivity","onResume");
        if(mConnected){
            if(mOverlayService.getSize() >0){
                mOverlayService.invisible();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.i("MainActivity","onPause");
        if(mConnected) {
            mOverlayService.visible();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity","onDestroy");
        if(mConnected) {
            if(mOverlayService.getSize() == 0 && mClear == false)
                unbindService(mServiceConnection);
            //unregisterReceiver(restartService);
        }
    }
}
