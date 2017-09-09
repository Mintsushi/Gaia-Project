package com.example.round.gaia_18;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.round.gaia_18.Common.Common;
import com.example.round.gaia_18.Data.OverlayPlant;
import com.example.round.gaia_18.Data.Plant;
import com.example.round.gaia_18.Helper.Helper;
import com.example.round.gaia_18.model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.example.round.gaia_18.MainActivity.context;
import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.MainActivity.weather;

public class OverlayService extends Service implements View.OnClickListener,View.OnTouchListener,LocationListener{

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

    //Notification View
    private Notification noti;
    private Notification.Builder builder;
    private NotificationManager notificationManager;
    private int notification_id = 1;
    private RemoteViews remoteView;

    //Location
    private LocationManager locationManager;
    private String provider;
    private double lat,lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();

    private final int MY_PERMISSION = 0;

    @Override
    public IBinder onBind(Intent intent){ return mBinder; }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}


    public class LocalBinder extends Binder {
        OverlayService getService(){ return OverlayService.this; }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);

        //Notification Custom View
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        remoteView = new RemoteViews(getPackageName(),R.layout.custom_notification);

        builder = new Notification.Builder(getApplicationContext());
        //후에 application icon으로 변경
        builder.setSmallIcon(R.drawable.image)
                .setContentTitle("Gaia Project");

        //후에 icon은 app logo를 이용
        noti = builder.build();
        noti.bigContentView = remoteView;

        notificationManager.notify(notification_id,noti);

        startForeground(startId,noti);
        return START_STICKY;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        setLocation();

        //Overlay Service / WindowManager
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

    private void setLocation(){
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(),false);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(),"권한 허가",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(),"권한 거부",Toast.LENGTH_LONG).show();
            }
        };

        new TedPermission(this).setPermissionListener(permissionListener)
                .setRationaleMessage("GPS를 사용하기 위해서는 GPS 접근 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다...\n[설정]>[권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        try{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,400,1,this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,400,1,this);
        }catch (SecurityException e){
            Log.i("OverlayService",e.toString());
        }

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

    public class GetWeather extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params){
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);

            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s.contains("Error: Not found city")){
                return;
            }

            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, type);

            remoteView.setTextViewText(R.id.txtCity,String.format("도시 : %s, 국가 : %s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
            remoteView.setTextViewText(R.id.txtLastUpdate,String.format("Last Updated : %s", Common.getDateNow()));
            remoteView.setTextViewText(R.id.txtDescription,String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));
            remoteView.setTextViewText(R.id.txtHumidity,String.format("습도 : %d%%",openWeatherMap.getMain().getHumidity()));
            remoteView.setTextViewText(R.id.txtTime,String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise())
                    ,Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
            remoteView.setTextViewText(R.id.txtCelsius,String.format("%.2f °C",openWeatherMap.getMain().getTemp()));

            Picasso.with(context)
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(remoteView,R.id.imageView,notification_id,noti);
            noti.contentView = remoteView;

            notificationManager.notify(notification_id,noti);

            Picasso.with(context)
                    .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                    .into(weather);
        }
    }

}
