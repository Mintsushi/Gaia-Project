package com.example.round.neoful16;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
<<<<<<< HEAD
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
=======
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Round on 2017-07-14.
 */

public class StartActivity extends AppCompatActivity
        implements View.OnLongClickListener,View.OnClickListener,View.OnTouchListener{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private TextView nickname, email, seed, fruit;
    private RelativeLayout relativeLayout;

    private RequestQueue requestQueue;
<<<<<<< HEAD
=======
    private ImageLoader mImageLoader;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
    private ArrayList<PlantInfo> mArray = new ArrayList<>();

    public static OverlayService mOverlayService;
    private Intent overLayService;
    public static Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

<<<<<<< HEAD
=======
    private static Boolean nonStopApp = false;

<<<<<<< HEAD
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
=======
    private Boolean move = false;
    private int originalXPos, originalYPos;
    private float offsetX, offsetY;

>>>>>>> -Gaia_1.7v 로그인 없이 게임 시작 기능 추가
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

        Log.i("onTouch","onLongClick Event");
//
//        if(mOverlayService.onTest(view) && !move) {
//            Log.i("StartActivity",view.toString());
//            mOverlayService.onLongClick(view);
//            finish();
//        }

        return false;
    }

    @Override
    public void onClick(View view){
        for(int i=0;i<mArray.size();i++){
<<<<<<< HEAD
            if(mArray.get(i).getPlantId() == view.getId()){
                Intent intent = new Intent(StartActivity.this,PlantManagementActivity.class);
                intent.putExtra("plantNewID",String.valueOf(mArray.get(i).getId()));
                startActivity(intent);
=======
            if(mArray.get(i).getViewID() == view.getId()){
<<<<<<< HEAD
//                Intent intent = new Intent(StartActivity.this,PlantManagementActivity.class);
//                intent.putExtra("plantNewID",String.valueOf(mArray.get(i).getId()));
//                startActivity(intent);
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
=======
                nonStopApp = true;
                Intent intent = new Intent(StartActivity.this,PlantManagementActivity.class);
                intent.putExtra("plantNewID",String.valueOf(mArray.get(i).getId()));
                startActivity(intent);
                finish();
>>>>>>> -Gaia_1.7v 로그인 없이 게임 시작 기능 추가
            }
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            Log.i("onTouch","ImageView Touch Down");

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            move = false;

            int [] location = new int[2];
            view.getLocationOnScreen(location);

            originalXPos = location[0];
            originalYPos = location[1];

            offsetX = originalXPos - x;
            offsetY = originalYPos - y;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            Log.i("onTouch","ImageView Touch Move");

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

            int newX = (int)(x);
            int newY = (int)(y);

            if (Math.abs(newX - originalXPos) < 1 && Math.abs(newY - originalYPos) < 1 && !move) {
                return false;
            }

            params.leftMargin = newX-170;
            params.topMargin = newY-500;

            relativeLayout.updateViewLayout(view,params);

            move = true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            Log.i("onTouch","ImageView Touch Up");
            move = false;
        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        overLayService = new Intent(StartActivity.this, OverlayService.class);

<<<<<<< HEAD
=======
        requestQueue= Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getApplicationContext())));

>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
        if(!isServiceRunning(OverlayService.class)) {
            startService(overLayService);
            bindService(overLayService, mServiceConnection, BIND_AUTO_CREATE);
        }

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

<<<<<<< HEAD
<<<<<<< HEAD
        requestQueue= Volley.newRequestQueue(this);

=======
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
        getUserInform();
        getPlant();
=======
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

        //getUserInform();
        //getPlant();
        testSource();
>>>>>>> -Gaia_1.7v 로그인 없이 게임 시작 기능 추가

        Button store = (Button)findViewById(R.id.shop);
        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
<<<<<<< HEAD
                Intent intent = new Intent(StartActivity.this,StoreMainActivity.class);
                startActivity(intent);
                finish();
=======
                nonStopApp = true;
                Intent intent = new Intent(StartActivity.this,StoreMainActivity.class);
                startActivity(intent);
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
            }
        });
    }

    private void testSource(){

        ImageView imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.plant12);
        imageView.setId(0);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 random으로 바꾸자
        relParams.leftMargin = 0;
        relParams.topMargin = 0;
        imageView.setOnTouchListener(this);
        imageView.setOnLongClickListener(this);

        relativeLayout.addView(imageView,relParams);
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

<<<<<<< HEAD
        Log.i("MainActivity","onResume : "+mConnected);
        if(mConnected){
            Log.i("MainActivity","Size : "+mOverlayService.getSize());
            if(mOverlayService.getSize() >0){
                Log.i("MainActivity","Size : "+mOverlayService.getSize());
                mOverlayService.invisible();
            }
        }
=======
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
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
    }

    @Override
    protected void onPause(){
        super.onPause();

<<<<<<< HEAD
        Log.i("MainActivity","onPause");
        if(mConnected) {
            mOverlayService.visible();
            mConnected=false;
=======
        Log.i("MainActivity","onPause: "+nonStopApp);
        if(!nonStopApp) {
            if (mConnected) {
                mOverlayService.visible();
                //mConnected = false;
            }
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i("MainActivity","onDestroy");
<<<<<<< HEAD
        mConnected=false;
=======
        //mConnected=false;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
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

                        relativeLayout.removeAllViews();

                        for(int i=0; i<response.length();i++){

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

<<<<<<< HEAD
                                mArray.add(new PlantInfo(id,flower,pollen,plantId,FIP,PIP));
=======
                                mArray.add(new PlantInfo(id,flower,pollen,plantId,FIP,PIP,i));
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c

                                FrameLayout frameLayout = new FrameLayout(StartActivity.this);
                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                        100, 100, Gravity.BOTTOM);

                                params.setMargins(300,20,0,0);

<<<<<<< HEAD
                                ImageView imageView = new ImageView(StartActivity.this);
                                imageView.setImageResource(R.drawable.plant12);
                                imageView.setLayoutParams(new DrawerLayout.LayoutParams(300,300));
                                imageView.setTag(R.drawable.plant22);
=======
                                NetworkImageView imageView = new NetworkImageView(StartActivity.this);

                                String plantPath = FIP+flower+pollen+".png";
                                Log.i("MainActivity","plantPath : "+plantPath);

                                imageView.setImageUrl("http://202.31.200.143/"+plantPath,mImageLoader);
                                imageView.setLayoutParams(new DrawerLayout.LayoutParams(300,300));
                                imageView.setTag("http://202.31.200.143/"+plantPath);
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
                                imageView.setId(i);

                                frameLayout.addView(imageView);

                                frameLayout.setLayoutParams(params);
                                frameLayout.setOnTouchListener(StartActivity.this);

                                ConstraintLayout.LayoutParams conParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                conParams.editorAbsoluteX = 0;
                                conParams.editorAbsoluteY = 0;

                                relativeLayout.addView(frameLayout,conParams);

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
<<<<<<< HEAD

        public PlantInfo(int id, String flower,String pollen,int plantId, String FIP, String PIP){
=======
        private int viewID;

        public PlantInfo(int id, String flower,String pollen,int plantId, String FIP, String PIP,int viewID){
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
            this.id = id;
            this.flower = flower;
            this.pollen = pollen;
            this.plantId = plantId;
            this.flowerImagePath = FIP;
            this.potImagePath = PIP;
<<<<<<< HEAD
=======
            this.viewID = viewID;
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
        }

        public int getId(){return this.id;}
        public String getFlower(){return this.flower;}
        public String getPollen(){return this.pollen;}
        public int getPlantId(){return this.plantId;}
        public String getFlowerImagePath(){return this.flowerImagePath;}
        public String getPotImagePath(){return this.potImagePath;}
<<<<<<< HEAD
=======
        public int getViewID(){return this.viewID;}
>>>>>>> 36d62bcdd2c85009bbc462f81d4a19be8e5fca9c
    }
}
