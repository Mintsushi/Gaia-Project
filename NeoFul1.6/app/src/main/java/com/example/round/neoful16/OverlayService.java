package com.example.round.neoful16;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
<<<<<<< HEAD
import com.android.volley.toolbox.JsonObjectRequest;
=======
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.view.WindowManager.LayoutParams;
/**
 * Created by Round on 2017-07-16.
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

    private View topLeftView;
    private ImageView sky;
    private TextView tv,currentSky;

    protected LocationManager locationManager;
    private Boolean isGPSEnabled = false;
    private Boolean isNetworkEnabled = false;

    private int mForecastGridRetry = 0;
    ForecastGrib mForecastGrib;
    private ArrayList<ForecastItem> mForecastGribItems = new ArrayList<ForecastItem>();
    private static final int BASE_SKY_STATUS_END_VALUE = 3;

    private int pageNo = 1;
    private int numOfRows = 10;

<<<<<<< HEAD
=======
    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;

>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
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
                .setSmallIcon(R.drawable.flower)
                .build();

        startForeground(startId, notification);

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG,"onCreate");

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
            mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

            //Acquire a refernce to the system location Manager
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

<<<<<<< HEAD
=======
            requestQueue= Volley.newRequestQueue(this);
            mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getApplicationContext())));
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            Log.i("Onclick","GPS : "+isGPSEnabled+" / Network : "+isNetworkEnabled);
//            if(!isGPSEnabled){
//                setGPS();
//            }
//            else {
<<<<<<< HEAD
                Log.i("onClick","Before Set LocationManager");
                //통지사이의 최소 시간간격 : 100ms
                //통지사이의 최소 변경거리 : 1m
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 1, mLocationListener);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

                Log.i("onClick","Set LocationManager OK");
=======
            Log.i("onClick","Before Set LocationManager");
            //통지사이의 최소 시간간격 : 100ms
            //통지사이의 최소 변경거리 : 1m
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 1, mLocationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

            Log.i("onClick","Set LocationManager OK");
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
//            }
        }catch (SecurityException ex){
            Log.i("OnClick","SecurityException : "+ex.toString());
        }

        Log.i(TAG,"onCreate");
        topLeftView = new View(this);
        WindowManager.LayoutParams topLeftParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_TOAST, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        topLeftParams.gravity = Gravity.LEFT | Gravity.TOP;
        topLeftParams.x = 0;
        topLeftParams.y = 0;
        topLeftParams.width = 0;
        topLeftParams.height = 0;
        mWindowManager.addView(topLeftView, topLeftParams);

        LinearLayout lv = new LinearLayout(this);
        lv.setOrientation(LinearLayout.VERTICAL);
        lv.setBackgroundResource(R.drawable.shape);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
<<<<<<< HEAD
<<<<<<< HEAD
        layoutParams.gravity= Gravity.TOP | Gravity.LEFT;
=======
        layoutParams.gravity= Gravity.TOP | Gravity.RIGHT;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
=======
        layoutParams.gravity= Gravity.TOP | Gravity.CENTER;
>>>>>>> -Gaia_1.7v 로그인 없이 게임 시작 기능 추가
        layoutParams.x = 0;
        layoutParams.y = 20;

        sky = new ImageView(this);
        sky.setImageResource(R.drawable.ic_weather_01);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                700, 500,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);
//        params.gravity = Gravity.LEFT|Gravity.TOP;
//        params.x = 0;
//        params.y = 100;

        tv = new TextView(this);
        tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        tv.setTextSize(15);
        tv.setTextColor(getResources().getColor(R.color.colorWhite));

        currentSky = new TextView(this);
        currentSky.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        currentSky.setTextSize(15);
        currentSky.setTextColor(getResources().getColor(R.color.colorWhite));

        lv.addView(sky,params);
        lv.addView(tv);
        lv.addView(currentSky);

        mWindowManager.addView(lv, layoutParams);

        mArray.add(new PlantInfo(layoutParams,lv,10));

    }

    private void setGPS(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("GPS 사용유무셋팅");
        alertDialog.setMessage("GPS 셋팅이 되지 않았을수도 있습니다.\n설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.i("LocationListener","onLocationChanged : "+location);

            double longitude = location.getLongitude(); //경도
            double latitude = location.getLatitude(); //위도
            double altitude = location.getAltitude(); //고도
            float accuracy = location.getAccuracy(); //정확도
            String provider = location.getProvider(); //위치 제공자

            tv.setText("위치정보 : "+provider+"" +
                    "\n위도 : "+longitude+
                    "\n경도 : "+latitude+
                    "\n고도 : "+altitude+
                    "\n정확도 : "+accuracy+"\n");

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formatDate = format.format(date);

            getWeather(latitude,latitude);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.i("LocationListener","onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.i("LocationListener","onProviderEnabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.i("LocationListener","onProviderDisabled");
        }
    };

    public class PlantInfo implements View.OnTouchListener,View.OnClickListener{

        WindowManager.LayoutParams params;
        View plant;
        int id;

        public PlantInfo(WindowManager.LayoutParams params, View image, int id){
            this.params = params;
            this.plant = image;
            this.plant.setOnTouchListener(this);
            this.id = id;
<<<<<<< HEAD
=======
            Log.i("PlantInfo",Integer.toString(id));
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
            // mWindowManager.addView(this.plant,this.params);
        }

        public WindowManager.LayoutParams getParams(){return this.params;}
        public View getPlant(){return this.plant;}
        public int getId(){return this.id;}

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
<<<<<<< HEAD
            if(0<view.getId() && view.getId()<10){
=======
            Toast.makeText(getApplicationContext(),"ID: "+id,Toast.LENGTH_LONG).show();
<<<<<<< HEAD
            if(-1<id && id<10){
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
=======
>>>>>>> -Gaia_1.7v 로그인 없이 게임 시작 기능 추가
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

    public Boolean onTest(View imageView){
        for(int i =0; i<mArray.size(); i++){
            Log.i(TAG,"*********2  "+mArray.get(i).getId()+"   "+imageView.getId());
            if(mArray.get(i).getId() == imageView.getId())
                return false;
        }
        return true;
    }

    public void onLongClick(final View flower){
        Log.i(TAG,"************1  "+flower.toString()+"/"+mWindowManager.toString());
        int [] location = new int[2];
        flower.getLocationOnScreen(location);
<<<<<<< HEAD
        final ImageView overlayButton = new ImageView(this);
        overlayButton.setImageResource((Integer)flower.getTag());
=======
        final NetworkImageView overlayButton = new NetworkImageView(this);
        overlayButton.setImageUrl(flower.getTag().toString(),mImageLoader);
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                flower.getWidth(), flower.getHeight(),
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
                        mArray.add(new PlantInfo(params,(ImageView)view,flower.getId()));
                        Intent intent = new Intent(OverlayService.this,StartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

                return false;
            }
        });

    }

    private void getWeather(double lap,double lon){
        Log.i(TAG,"기상청 동네예보정보조회서비스 -> ForecastGrid");

        Calendar calendar = Calendar.getInstance();
        String day;
        String url = Constants.WEATHER_SERVER_PREFIX + Constants.WEATHER_SERVICE_GRIB+"?";
//        url += Constants.WEATHER_PARAM_TYPE+"&"+Constants.WEATHER_PARAM_SERVICE_KEY+Constants.WEATHER_OPEN_API_KEY;
        url +=Constants.WEATHER_PARAM_SERVICE_KEY+Constants.WEATHER_OPEN_API_KEY;
//        url +="&"+Constants.WEATHER_PARAM_SERVICE_KEY+"SERVICE_KEY";

//        url +="&"+"nx="+(int)128.392992;
//        url +="&"+"ny="+(int)36.1452891;

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
//
//        if(minutes < 45){
//            if( (hour - 1) <0){
//                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
//            }
//
//            int newHour = (hour - 1) < 0 ? 24 + (hour - 3) : (hour - 3);
//            if(mForecastGridRetry == 2){
//                newHour = (newHour -1) <0 ? 24+(newHour - 3):(newHour - 3);
//            }
//
//            day= "&"+"base_time="+((newHour) < 10 ? "0"+newHour : newHour)+"00";
//        }else{
//            hour = (hour - 1) <0 ? 24+(hour-2) : (hour - 2);
//            day= "&"+"base_time="+((hour)<10 ? "0"+hour:hour)+"00";
//        }

        if( (hour - 1) <0){
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
        }

        int newHour = (hour - 1) < 0 ? 24 + (hour - 2) : (hour - 2);

        if(mForecastGridRetry == 2){
            newHour = (newHour -1) <0 ? 24+(newHour - 2):(newHour - 2);
        }
        day= "&"+"base_time="+((newHour) < 10 ? "0"+newHour : newHour)+"00";

        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat("yyyyMMdd");
        url += "&"+"base_date="+sdf.format(calendar.getTime());
        url += day;
        url +="&"+"nx="+(int)lap;
        url +="&"+"ny="+(int)lon;
        url +="&"+"pageNo="+pageNo;
        url +="&"+"numOfRows="+numOfRows;
        url +="&"+Constants.WEATHER_PARAM_TYPE;

//        url = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastGrib?ServiceKey=hnwENP2C7ReDLarwDMWH79Ji0atrixuwHP4lqvkIDLhU1veM1tIlKoNV23pR5VmOA5PPAVy%2F0Nk3vtxkuV1n4Q%3D%3D&base_date=20170707&base_time=2300&nx=35&ny=127&pageNo=1&numOfRows=1&_type=json";
        if(pageNo == 1){
            Log.i(TAG,">>Request URL : "+url);
        }

        JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response){
                        Log.i(TAG,response.toString());
                        mForecastGrib = new ForecastGrib(response);

                        mForecastGribItems.clear();
                        mForecastGribItems = mForecastGrib.getWeatherItems();

                        updateForecastGribWeatherView();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Log.i(TAG,">>>UpdateForecastGrid Error : "+error.toString());
                        mForecastGridRetry++;
                        if(mForecastGridRetry > 3){
                            mForecastGridRetry = 0;
                            Log.i(TAG,">> 3회차시도 실패.... 더 이상 조회할게 없다.!!");
                            updateForecastGribWeatherView();
                            if(mForecastGribItems == null){

                            }
                        }
                        else{
                            Log.i(TAG,"재시도 Go!!!");
                        }
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }
    private void updateForecastGribWeatherView() {
        Log.d(TAG, ">> updateForecastGribWeatherViewWeatherView().......");

        if (mForecastGribItems == null) {
            return;
        }

        ForecastItem item;
        int skyStatusValue = 1;
        int rainValue = 0;

        Resources res = getResources();
        String[] skyStatusArray = res.getStringArray(R.array.sky_status);

        //하늘상태
        //맑음(1), 구름조금(2), 구름많음(3), 흐림(4)
        item = getForecastItemCategory(mForecastGribItems,ForecastItem.SKY);
        if(item != null){
            skyStatusValue = Integer.parseInt(item.obsrValue);

            //강수형태
            //없음(0), 비(1), 비/눈(2), 눈(3)
            item = getForecastItemCategory(mForecastGribItems, ForecastItem.PTY);

            if(item !=null){
                int rainStaus = Integer.parseInt(item.obsrValue);
                if(rainStaus > 0) {
                    skyStatusValue = BASE_SKY_STATUS_END_VALUE + rainStaus;
                }
                item = getForecastItemCategory(mForecastGribItems,ForecastItem.RN1);
                if (item != null) {
                    try {
                        if (rainStaus > 0)
                            rainValue = Integer.parseInt(item.obsrValue);
                    } catch (Exception e) { }
                }

                item = getForecastItemCategory(mForecastGribItems,ForecastItem.REH);
                if(item != null){
                    try{
                        if(rainStaus == 0)
                            rainValue=Integer.parseInt(item.obsrValue);
                    }catch (Exception e){

                    }
                }
            }

        }

        String skyStatus;
        try {
            skyStatus = skyStatusArray[skyStatusValue];
        } catch (Exception e) {
            skyStatus = skyStatusArray[0];
        }

        if(currentSky != null){
            String rainValueString = "";
            if(skyStatusValue <= BASE_SKY_STATUS_END_VALUE){
                rainValueString = this.getString(R.string.humidity,rainValue+"%");
            }else{
                if(skyStatusValue == skyStatusArray.length -1){
                    rainValueString = this.getString(R.string.snowfall,rainValue);
                }else{
                    rainValueString = this.getString(R.string.rainfall,rainValue);
                }
            }

            currentSky.setText("현재 하늘 상태 : "+this.getString(R.string.sky_status,skyStatus,rainValueString));
        }

        String[] backgroudArray  = res.getStringArray(R.array.sky_status_drawable);
        Log.i(TAG,"SKY Status Value : "+skyStatusValue);
        String name = backgroudArray[skyStatusValue];
        int id = getResources().getIdentifier(name,"drawable",getPackageName());
        sky.setImageResource(id);

    }

    private ForecastItem getForecastItemCategory(ArrayList<ForecastItem> items,String category){
        ForecastItem item = null;

        if(category == null){
            return null;
        }

        for(int i =0 ;i<items.size();i++){
            ForecastItem tmp = items.get(i);
            if(category.equals(tmp.category)){
                item = tmp;
                break;
            }
        }

        return item;
    }
}