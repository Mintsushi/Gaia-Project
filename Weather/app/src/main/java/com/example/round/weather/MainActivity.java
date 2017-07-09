package com.example.round.weather;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

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

}
