package com.example.round.neoful16;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Round on 2017-07-14.
 */

public class StartActivity extends AppCompatActivity implements View.OnLongClickListener,View.OnClickListener{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private TextView nickname, email, seed, fruit;
    private GridLayout gridLayout;

    private RequestQueue requestQueue;
    private ImageLoader mImageLoader;
    private ArrayList<PlantInfo> mArray = new ArrayList<>();

    public static OverlayService mOverlayService;
    private Intent overLayService;
    public static Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

    private static Boolean nonStopApp = false;

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

        if(mOverlayService.onTest(view)) {
            Log.i("StartActivity",view.toString());
            mOverlayService.onLongClick(view);
            finish();
        }

        return false;
    }

    @Override
    public void onClick(View view){
        for(int i=0;i<mArray.size();i++){
            if(mArray.get(i).getViewID() == view.getId()){
//                Intent intent = new Intent(StartActivity.this,PlantManagementActivity.class);
//                intent.putExtra("plantNewID",String.valueOf(mArray.get(i).getId()));
//                startActivity(intent);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        overLayService = new Intent(StartActivity.this, OverlayService.class);

        requestQueue= Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getApplicationContext())));

        if(!isServiceRunning(OverlayService.class)) {
            startService(overLayService);
            bindService(overLayService, mServiceConnection, BIND_AUTO_CREATE);
        }

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        getUserInform();
        getPlant();

        gridLayout = (GridLayout)findViewById(R.id.grid);
        Button store = (Button)findViewById(R.id.shop);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonStopApp = true;
                Intent intent = new Intent(StartActivity.this,StoreMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager)getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        Log.i("MainActivity","isServiceRunning : "+mOverlayService);
        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if (serviceClass.getName().equals(service.service.getClassName())) {
                if(mOverlayBinder != null){
                    mOverlayService = ((OverlayService.LocalBinder) mOverlayBinder).getService();
                    mConnected = true;
                    return true;
                }
                else{
                    //unbindService(mServiceConnection);
                    //stopService(overLayService);
                    return false;
                }
            }
        }
        Log.i ("MainActivity", "false");
        return false;
    }

    @Override
    protected void onResume(){
        super.onResume();

        Log.i("MainActivity","onResume : "+nonStopApp);
        if(!nonStopApp) {
            if (mConnected) {
                Log.i("MainActivity", "Size : " + mOverlayService.getSize());
                if (mOverlayService.getSize() > 0) {
                    Log.i("MainActivity", "Size : " + mOverlayService.getSize());
                    mOverlayService.invisible();
                }
            }
        }
        else{
            nonStopApp=false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        Log.i("MainActivity","onPause: "+nonStopApp);
        if(!nonStopApp) {
            if (mConnected) {
                mOverlayService.visible();
                //mConnected = false;
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity","onDestroy");
        //mConnected=false;
        //unbindService(mServiceConnection);
    }

    private void getUserInform(){
        String url = "http://202.31.200.143/user/"+pref.getString("id","");

        JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("StartActivity","getUserInform : "+response.toString());

                        nickname =(TextView)findViewById(R.id.userNickname);
                        email = (TextView)findViewById(R.id.userEmail);
                        seed = (TextView)findViewById(R.id.seed);
                        fruit = (TextView)findViewById(R.id.fruit);

                        try{
                            String name = response.getString("nickname");
                            int getSeed = response.getInt("seed");
                            int getFruit = response.getInt("fruit");

                            // f재화 저장
                            editor.putInt("PresentSeed",getSeed);
                            Log.i("startSeed",String.valueOf(getSeed));
                            editor.putInt("PresentFruit",getFruit);
                            editor.commit();

                            nickname.setText(name);
                            email.setText(pref.getString("id",""));
                            seed.setText(Integer.toString(getSeed));
                            fruit.setText(Integer.toString(getFruit));
                        }catch (JSONException e){
                            Log.i("MainActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("MainActivity","getUserInform Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getPlant(){
        String url="http://202.31.200.143/user/plant/"+pref.getString("id","");

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response){
                        Log.i("StartActivity",response.toString());

                        gridLayout.removeAllViews();

                        int column = 3;
                        int row = 3;

                        gridLayout.setColumnCount(column);
                        gridLayout.setRowCount(row);

                        for(int i=0,c=0,r=0;i<response.length();i++,c++){

                            if(c == column){
                                c=0;
                                r++;
                            }
                            try{
                                JSONObject object = response.getJSONObject(i);

                                int id = object.getInt("plantNo");
                                String flower = object.getString("flowerNo");
                                String pollen = object.getString("potNo");
                                String FIP = object.getString("flowerImagePath");
                                String PIP = object.getString("potImagePath");
                                Log.i("StartActivity","plant"+flower+pollen);

                                int plantId = getResources().getIdentifier("plantflowerImage"+Integer.toString(i+1),"id",getPackageName());
                                int plantpotId = getResources().getIdentifier("plantpotImage"+Integer.toString(i+1),"id",getPackageName());

                                mArray.add(new PlantInfo(id,flower,pollen,plantId,FIP,PIP,i));

                                FrameLayout frameLayout = new FrameLayout(StartActivity.this);
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                        100, 100, Gravity.BOTTOM);

                                params.setMargins(300,20,0,0);

                                NetworkImageView imageView = new NetworkImageView(StartActivity.this);

                                String plantPath = FIP+flower+pollen+".png";
                                Log.i("MainActivity","plantPath : "+plantPath);

                                imageView.setImageUrl("http://202.31.200.143/"+plantPath,mImageLoader);
                                imageView.setLayoutParams(new DrawerLayout.LayoutParams(300,300));
                                imageView.setTag("http://202.31.200.143/"+plantPath);
                                imageView.setId(i);

                                frameLayout.addView(imageView);

                                frameLayout.setLayoutParams(params);

                                GridLayout.Spec rowSpan = GridLayout.spec(r);
                                GridLayout.Spec colSpan = GridLayout.spec(c);

                                GridLayout.LayoutParams gridParam = new GridLayout.LayoutParams(rowSpan,colSpan);
                                gridLayout.addView(frameLayout,gridParam);

                                imageView.setOnLongClickListener(StartActivity.this);
                                imageView.setOnClickListener(StartActivity.this);

//                                plant.setOnLongClickListener(StartActivity.this);
//                                plant.setOnClickListener(StartActivity.this);
//                                plantId = getResources().getIdentifier(FIP,"drawable",getPackageName());
//                                int potpath =  getResources().getIdentifier(PIP,"drawable",getPackageName());
//
//                                plant.setImageResource(plantId);
//                                plantpot.setImageResource(potpath);
//                                plant.setTag(plantId);
//                                plant.setTag(plantpot);

                            }catch (JSONException e){
                                Log.i("StartActivity",e.toString());
                            }
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("StartActivity",error.toString());
            }
        });

        requestQueue.add(request);
    }

    public class PlantInfo{

        private int id;
        private String flower;
        private String pollen;
        private String flowerImagePath;
        private String potImagePath;
        private int plantId;
        private int viewID;

        public PlantInfo(int id, String flower,String pollen,int plantId, String FIP, String PIP,int viewID){
            this.id = id;
            this.flower = flower;
            this.pollen = pollen;
            this.plantId = plantId;
            this.flowerImagePath = FIP;
            this.potImagePath = PIP;
            this.viewID = viewID;
        }

        public int getId(){return this.id;}
        public String getFlower(){return this.flower;}
        public String getPollen(){return this.pollen;}
        public int getPlantId(){return this.plantId;}
        public String getFlowerImagePath(){return this.flowerImagePath;}
        public String getPotImagePath(){return this.potImagePath;}
        public int getViewID(){return this.viewID;}
    }
}
