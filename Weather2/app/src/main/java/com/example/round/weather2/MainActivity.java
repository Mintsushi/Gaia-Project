package com.example.round.weather2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity.";
    private int mForecastGridRetry = 0;
    private int pageNo = 1;
    private int numOfRows = 10;
    TextView text;
    ForecastGrib mForecastGrib;
    private ArrayList<ForecastItem> mForecastGribItems = new ArrayList<ForecastItem>();
    private static final int BASE_SKY_STATUS_END_VALUE = 3;

    private TextView tv;
    private ToggleButton tb;
    protected LocationManager locationManager;
    private Boolean isGPSEnabled = false;
    private Boolean isNetworkEnabled = false;
    private TextView td;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        td = (TextView)findViewById(R.id.dateNow);

        tv = (TextView)findViewById(R.id.textView2);
        tv.setText("위치정보 미수신중");

        tb = (ToggleButton)findViewById(R.id.toggle1);
        tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    if(tb.isChecked()){
                        //Acquire a refernce to the system location Manager
                        locationManager = (LocationManager)MainActivity.this.getSystemService(Context.LOCATION_SERVICE);

                        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                        Log.i("Onclick","GPS : "+isGPSEnabled+" / Network : "+isNetworkEnabled);
                        if(!isGPSEnabled){
                            setGPS();
                        }
                        if(!isNetworkEnabled){
                            setNetwork();
                        }
                        else {
                            tv.setText("수신중.....");

                            Log.i("onClick","Before Set LocationManager");
                            //통지사이의 최소 시간간격 : 100ms
                            //통지사이의 최소 변경거리 : 1m
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, mLocationListener);
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, mLocationListener);

                            Log.i("onClick","Set LocationManager OK");
                        }
                    }
                    else{
                        tv.setText("위치정보 미수신중");
                        locationManager.removeUpdates(mLocationListener);
                    }
                }catch (SecurityException ex){
                    Log.i("OnClick","SecurityException : "+ex.toString());
                }
            }
        });
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
                    "\n정확도 : "+accuracy);

            long now = System.currentTimeMillis();
            Date date = new Date(now);
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String formatDate = format.format(date);

            td.setText(formatDate);
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

    private void setNetwork(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("NetWork 사용유무셋팅");
        alertDialog.setMessage("NetWork 셋팅이 되지 않았을수도 있습니다.\n설정창으로 가시겠습니까?");

        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
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

    private void getWeather(double lap,double lon){
        Log.i(TAG,"기상청 동네예보정보조회서비스 -> ForecastGrid");

        Calendar calendar = Calendar.getInstance();
        text=(TextView)findViewById(R.id.text);
        String day;
        String url = Constants.WEATHER_SERVER_PREFIX + Constants.WEATHER_SERVICE_GRIB+"?";
//        url += Constants.WEATHER_PARAM_TYPE+"&"+Constants.WEATHER_PARAM_SERVICE_KEY+Constants.WEATHER_OPEN_API_KEY;
        url +=Constants.WEATHER_PARAM_SERVICE_KEY+Constants.WEATHER_OPEN_API_KEY;
//        url +="&"+Constants.WEATHER_PARAM_SERVICE_KEY+"SERVICE_KEY";

//        url +="&"+"nx="+(int)128.392992;
//        url +="&"+"ny="+(int)36.1452891;

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        if(minutes < 45){
            if( (hour - 1) <0){
                calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)-1);
            }

            int newHour = (hour - 1) < 0 ? 24 + (hour - 2) : (hour - 2);
            if(mForecastGridRetry == 2){
                newHour = (newHour -1) <0 ? 24+(newHour - 2):(newHour - 2);
            }

            day= "&"+"base_time="+((newHour) < 10 ? "0"+newHour : newHour)+"00";
        }else{
            hour = (hour - 1) <0 ? 24+(hour-1) : (hour - 1);
            day= "&"+"base_time="+((hour)<10 ? "0"+hour:hour)+"00";
        }

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

            TextView sky = (TextView)findViewById(R.id.SKY);
            sky.setText(skyStatusArray[skyStatusValue-1]);

            //강수형태
            //없음(0), 비(1), 비/눈(2), 눈(3)
            item = getForecastItemCategory(mForecastGribItems, ForecastItem.PTY);

            if(item !=null){
                int rainStaus = Integer.parseInt(item.obsrValue);
                Log.i(TAG,"SKY : "+skyStatusArray[skyStatusValue-1]);
                TextView rain = (TextView) findViewById(R.id.PTV);
                if(rainStaus > 0) {
                    skyStatusValue = BASE_SKY_STATUS_END_VALUE + rainStaus;
                    rain.setText(skyStatusArray[skyStatusValue]);
                }
                item = getForecastItemCategory(mForecastGribItems,ForecastItem.RN1);
                TextView rn1 = (TextView)findViewById(R.id.RN1);
                rn1.setText(item.obsrValue);
                if (item != null) {
                    try {
                        if (rainStaus > 0)
                            rainValue = Integer.parseInt(item.obsrValue);
                    } catch (Exception e) { }
                }

                item = getForecastItemCategory(mForecastGribItems,ForecastItem.REH);
                TextView reh = (TextView)findViewById(R.id.REH);
                reh.setText(item.obsrValue);
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

        TextView mCurrentSkyStatus = (TextView)findViewById(R.id.CurSKY);
        if(mCurrentSkyStatus != null){
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

            mCurrentSkyStatus.setText(this.getString(R.string.sky_status,skyStatus,rainValueString));
        }

        item = getForecastItemCategory(mForecastGribItems,ForecastItem.T1H);
        TextView t1h = (TextView)findViewById(R.id.T1H);
        t1h.setText(item.obsrValue);

        item = getForecastItemCategory(mForecastGribItems,ForecastItem.WSD);
        TextView wsd = (TextView)findViewById(R.id.WSD);
        wsd.setText(item.obsrValue);

        String[] backgroudArray = res.getStringArray(R.array.sky_status_bg_port_drawable);
        String name = backgroudArray[skyStatusValue];
        Log.i(TAG,"id : "+name);
        int id = getResources().getIdentifier(name,"drawable",getPackageName());
        LinearLayout layout = (LinearLayout)findViewById(R.id.activity_main);
        layout.setBackgroundResource(id);

        backgroudArray = res.getStringArray(R.array.sky_status_drawable);
        name = backgroudArray[skyStatusValue];
        id = getResources().getIdentifier(name,"drawable",getPackageName());
        ImageView image = (ImageView)findViewById(R.id.imageView2);
        image.setImageResource(id);

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
