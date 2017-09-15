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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Common.Common;
import com.example.round.gaia_18.Data.Flower;
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
import java.util.HashMap;
import java.util.Iterator;

import static com.example.round.gaia_18.MainActivity.context;
import static com.example.round.gaia_18.MainActivity.dataBaseHelper;
import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.MainActivity.seed;
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

    //Location & Weather
    private LocationManager locationManager;
    private String provider;
    private double lat,lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    private String weatherState = "";

    private final int MY_PERMISSION = 0;

    //Screen Click
    private LinearLayout linearLayout;
    private LinearLayout skill;
    private Button open;
    private Button click;
    private TextView seedOverlay;
    private WindowManager.LayoutParams clickLayout;
    private WindowManager.LayoutParams skillWindow;
    private Button removeAll;

    //0 닫혀있는 상태
    //1 열려있는 상태
    private int skillWindowState = 0;
    //0 : click stop
    //1 : click available
    private int clickState = 0;

    //Error Solution
    //0: overlayScreen Off
    //1: overlayScreen On
    private int visible = 0;

    //1 : 화면에서 view들을 임시로 remove
    //0 : 임시로 remove한 view들을 다시 overlay로 가지고 옴.
    private int removeState = 0;

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

        setLayout();
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

    public void setSeed(int score){
        seedOverlay.setText(Integer.toString(score));
    }

    private void setLayout(){
        linearLayout = new LinearLayout(this);
        linearLayout.setOnClickListener(this);

        skill = new LinearLayout(this);
        skill.setOrientation(LinearLayout.VERTICAL);
        skill.setBackgroundResource(R.drawable.brown_background);

        seedOverlay = new TextView(this);
//        seedOverlay.setText(Integer.toString(score));

        open = new Button(this);
        open.setText("OPEN");

        click = new Button(this);
        click.setText("Click");

        removeAll = new Button(this);
        removeAll.setText("Remove");

        open.setOnClickListener(this);
        click.setOnClickListener(this);
        removeAll.setOnClickListener(this);

        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        skill.addView(seedOverlay,buttonParams);
        skill.addView(open,buttonParams);
        skill.addView(click,buttonParams);
        skill.addView(removeAll,buttonParams);

        skillWindow = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,200,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );
        skillWindow.gravity = Gravity.RIGHT | Gravity.BOTTOM;

        clickLayout = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

    }

    private void removeLayout(){

    }
    public void invisible(){

        if(visible == 1){

            if(removeState == 0) {
                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.removeView(plants.get(i).getOverlayPlant());
                }
            }

            if (clickState == 1) {
                mWindowManager.removeView(linearLayout);
                clickState = 0;
            }
            mWindowManager.removeView(skill);
            visible = 0;
        }
    }

    public void visible(){

        if(visible == 0) {
            ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

            for (int i = 0; i < plants.size(); i++) {
                mWindowManager.addView(plants.get(i).getOverlayPlant(), plants.get(i).getParams());
            }

            mWindowManager.addView(skill, skillWindow);

            visible = 1;
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
    public void onClick(View view){
        if(view == linearLayout){

//            //후에 식물을 따른 점수들을 계산해서 구현
//            score = score + 1000000;
//            seedOverlay.setText(Integer.toString(score));
//            seed.setText(Integer.toString(score));

        }else if(view == open){

            WindowManager.LayoutParams params = (WindowManager.LayoutParams)skill.getLayoutParams();
            if(skillWindowState == 0){
                params.height = WindowManager.LayoutParams.MATCH_PARENT;
                mWindowManager.updateViewLayout(skill,params);

                open.setText("Close");
                skillWindowState = 1;
            }
            else{
                params.height = 200;
                mWindowManager.updateViewLayout(skill,params);

                open.setText("Open");
                skillWindowState = 0;
            }

        }else if(view == click){

            if(clickState == 0){
                mWindowManager.addView(linearLayout,clickLayout);
                mWindowManager.removeView(skill);
                mWindowManager.addView(skill,(WindowManager.LayoutParams)skill.getLayoutParams());
                click.setText("Stop");
                clickState = 1;
            }
            else{
                mWindowManager.removeView(linearLayout);
                click.setText("Click");
                clickState = 0;
            }

        }else if(view == removeAll){

            if(removeState == 0){
                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.removeView(plants.get(i).getOverlayPlant());
                }

                removeAll.setText("Create");
                removeState = 1;
            }

            else{
                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.addView(plants.get(i).getOverlayPlant(), plants.get(i).getParams());
                }
                removeAll.setText("Remove");
                removeState = 0;
            }
        }
    }

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
            if(s != null){
                if(s.contains("Error: Not found city")){
                    return;
                }

                Gson gson = new Gson();
                Type type = new TypeToken<OpenWeatherMap>(){}.getType();
                openWeatherMap = gson.fromJson(s, type);

                remoteView.setTextViewText(R.id.txtCity,String.format("도시 : %s, 국가 : %s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
                remoteView.setTextViewText(R.id.txtLastUpdate,String.format("Last Updated : %s", Common.getDateNow()));

                remoteView.setTextViewText(R.id.txtHumidity,String.format("습도 : %d%%",openWeatherMap.getMain().getHumidity()));
                remoteView.setTextViewText(R.id.txtTime,String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise())
                        ,Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
                remoteView.setTextViewText(R.id.txtCelsius,String.format("%.2f °C",openWeatherMap.getMain().getTemp()));

                //기존이랑 날씨가 다르면
                if(!weatherState.equals(openWeatherMap.getWeather().get(0).getDescription())){
                    remoteView.setTextViewText(R.id.txtDescription,String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));

                    ArrayList<Integer> weather = dataBaseHelper.getWeatherPassive(openWeatherMap.getWeather().get(0).getDescription());
                    HashMap<Integer, Integer> map = new HashMap<>();

                    for(int i =0; i<dataList.getOverlayPlants().size(); i++){

                            int id = dataList.getOverlayPlants().get(i).getPlant().getPlantNo();
                            Flower flower = dataList.getOverlayPlants().get(i).getPlant().getFlower();
                            //추가 점수
                            if(weather.get(id) > 0){
                                long score = 0;
                                int scoreType = 0;

                                Iterator<Integer> iterator = flower.getScore().keySet().iterator();

                                while(iterator.hasNext()){
                                    int key = iterator.next();
                                    int value = flower.getScore().get(key);

                                    score += value*Math.pow(1000,key);
                                }

                                score = (long)(score*weather.get(i))/100;

                                while(true){
                                    long nameogi = (long)score % 1000;
                                    long mok = (long)score / 1000;

                                    if(nameogi != 0){
                                        dataList.plusScore(scoreType,(int)nameogi,map);
                                    }
                                    if(mok < 1000){
                                        dataList.plusScore(scoreType+1,(int)mok,map);
                                    }

                                    score = mok;
                                    scoreType++;
                                }
                            }
                            //패널티 <- HP 구현 이후에 추가
                            else{

                            }
                        }
                        dataList.setTotalScore(map);
                }

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
}
