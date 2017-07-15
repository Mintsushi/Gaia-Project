package com.example.round.neopul15;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.round.neopul15.R.drawable.fertilizer;

/**
 * Created by Round on 2017-06-12.
 */

public class StartActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnLongClickListener,View.OnClickListener{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    TextView nickname, email, seed, fruit;
    View header;NavigationView navigationView;

    GridLayout grid;

    private OverlayService mOverlayService;
    private Intent overLayService;

    private ArrayList<PlantInfo> mArray = new ArrayList<>();
    private Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

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
            Log.i("StartActivity",view.toString());
            mOverlayService.onLongClick((ImageView) findViewById(view.getId()));
            finish();
        }

        return false;
    }

    @Override
    public void onClick(View view){

        for(int i=0;i<mArray.size();i++){
            if(mArray.get(i).getPlantId() == view.getId()){
                Intent intent = new Intent(StartActivity.this,PlantManagementActivity.class);
                intent.putExtra("plantNewID",String.valueOf(mArray.get(i).getId()));
                startActivity(intent);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        overLayService = new Intent(StartActivity.this, OverlayService.class);

        if(!isServiceRunning(OverlayService.class)) {
            bindService(overLayService, mServiceConnection, BIND_AUTO_CREATE);
            startService(overLayService);
        }

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        grid=(GridLayout)findViewById(R.id.grid);

        navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        getUserInform();
        getPlant();
/*
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/
        Button setBtn = (Button)findViewById(R.id.setButton);
        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                finish();

            }
        });

        Button storeButton = (Button)findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            //if(mOverlayService.getSize() == 0 && mClear == false)
                //unbindService(mServiceConnection);
            //unregisterReceiver(restartService);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.logOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("LogOut??");
            builder.setTitle("LogOut")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            editor.clear();
                            editor.commit();
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            return;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("LogOut");
            alert.show();
        } else if (id == R.id.seting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void getUserInform(){

        String url="http://202.31.200.143/user/"+pref.getString("id","");

        final JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("StartActivity","getUserInform : "+response.toString());

                        nickname =(TextView)findViewById(R.id.userNickNameText);
                        email = (TextView)header.findViewById(R.id.emailText);
                        seed = (TextView)findViewById(R.id.userseedNumText);
                        fruit = (TextView)findViewById(R.id.userfruitNumText);

                        Menu menu = navigationView.getMenu();
                        MenuItem medicine = menu.findItem(R.id.nav_medicine);
                        MenuItem fertilizer = menu.findItem(R.id.nav_fertilizer);
                        MenuItem pesticide = menu.findItem(R.id.nav_pesticide);

                        try{
                            String name = response.getString("nickname");
                            int getSeed = response.getInt("seed");
                            int getFruit = response.getInt("fruit");
                            int getMedicine = response.getInt("waterNum");
                            int getFerilizer = response.getInt("ferilizerNum");
                            int getPesticideNum = response.getInt("pesticideNum");

                            // f재화 저장
                            editor.putInt("PresentSeed",getSeed);
                            Log.i("startSeed",String.valueOf(getSeed));
                            editor.putInt("PresentFruit",getFruit);
                            editor.commit();

                            nickname.setText(name);
                            email.setText(pref.getString("id",""));
                            seed.setText(Integer.toString(getSeed));
                            fruit.setText(Integer.toString(getFruit));
                            medicine.setTitle("Medicine     x"+Integer.toString(getMedicine));
                            fertilizer.setTitle("Fertilizer   x"+Integer.toString(getFerilizer));
                            pesticide.setTitle("Pesticide    x"+Integer.toString(getPesticideNum));

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

    public class PlantInfo{

        private int id;
        private String flower;
        private String pollen;
        private String flowerImagePath;
        private String potImagePath;
        private int plantId;

        public PlantInfo(int id, String flower,String pollen,int plantId, String FIP, String PIP){
            this.id = id;
            this.flower = flower;
            this.pollen = pollen;
            this.plantId = plantId;
            this.flowerImagePath = FIP;
            this.potImagePath = PIP;
        }

        public int getId(){return this.id;}
        public String getFlower(){return this.flower;}
        public String getPollen(){return this.pollen;}
        public int getPlantId(){return this.plantId;}
        public String getFlowerImagePath(){return this.flowerImagePath;}
        public String getPotImagePath(){return this.potImagePath;}
    }

    private void getPlant(){

        String url="http://202.31.200.143/user/plant/"+pref.getString("id","");

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response){
                        Log.i("StartActivity",response.toString());

                        for(int i=0;i<response.length();i++){

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

                                mArray.add(new PlantInfo(id,flower,pollen,plantId,FIP,PIP));

                                ImageView plant = (ImageView)findViewById(plantId);
                                ImageView plantpot = (ImageView)findViewById(plantpotId);

                                plant.setOnLongClickListener(StartActivity.this);
                                plant.setOnClickListener(StartActivity.this);
                                plantId = getResources().getIdentifier(FIP,"drawable",getPackageName());
                                int potpath =  getResources().getIdentifier(PIP,"drawable",getPackageName());

                                plant.setImageResource(plantId);
                                plantpot.setImageResource(potpath);
                                plant.setTag(plantId);
                                plant.setTag(plantpot);

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

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

}
