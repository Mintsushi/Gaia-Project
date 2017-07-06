package com.example.round.neopul15;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Round on 2017-06-13.
 */

public class PlantManagementActivity extends AppCompatActivity {

    int plantIDS;
    private PlantInfomation plantinfomation = new PlantInfomation();
    private FlowerInfomation flower = new FlowerInfomation();
    private PollenInfomation pollen = new PollenInfomation();

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    //--------------------------꽃 정보 --------------------------
    TextView name;
    TextView level;
    //------------------------------------------------------------
    Button back;
    ImageView effect;
    //---------------------------가로 버튼---------------------------
    Button water;       //물주기 버튼
    Button energy;      //비료 버튼
    Button medicine;    //물약 버튼
    Button ONOFF;       //위젯 on/off 버튼

    //---------------------------세로 버튼---------------------------
    Button music;       //음악 버튼
    Button talk;        //말하기 버튼
    Button scissors;    //가지치기 버튼
    Button touch;       //만지기 버튼

    //---------------------------프로그레스 바---------------------------
    //프로그레스 바
    public ProgressBar hp;
    public ProgressBar exp;

    //현재 프로그레스 바의 수치를 받아오기 위한 변수
    int currenthp;
    int currentexp;

    //프로그레스 바 수치를 나타내는 textview;
    TextView HPtextview;
    TextView EXPtextview;


    //프로그레스 바의 최대 수치
    final int totalProgress = 100;

    //---------------------------기타 보조 변수---------------------------
    int a; //위젯 ON/OFF 변경 용 변수

    //-------------------환경 변수----------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_management);
        pref = getApplicationContext().getSharedPreferences("Login", getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        //getPlant();
        plantinfomation.setInfo(0,0,0,50,0,1);
        currenthp = plantinfomation.HP;
        currentexp =  plantinfomation.EXP;
        //getUserInform();
        //getFlowerInform(plantinfomation.getFlower());
        //getPollenInform(plantinfomation.getPollen());


        //--------------------------꽃 정보 --------------------------
        name = (TextView) findViewById(R.id.PlantName);
        level = (TextView) findViewById(R.id.PlantLV);
        level.setText(plantinfomation.getLV());
        //------------------------------------------------------------------
        effect = (ImageView) findViewById(R.id.buttonEffectImage);
        effect.setVisibility(View.INVISIBLE);
        back = (Button) findViewById(R.id.button);
        //---------------------------가로 버튼---------------------------
        water = (Button) findViewById(R.id.btnWater);
        energy = (Button) findViewById(R.id.btnEnergy);
        medicine = (Button) findViewById(R.id.btnMedicine);
        ONOFF = (Button) findViewById(R.id.ONOFF);

        //---------------------------세로 버튼---------------------------
        music = (Button) findViewById(R.id.btnMusic);
        talk = (Button) findViewById(R.id.btnTalk);
        scissors = (Button) findViewById(R.id.btnScissors);
        touch = (Button) findViewById(R.id.btnTouch);

        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        ImageView plant = (ImageView) findViewById(R.id.plantImage);
        int plantId = getResources().getIdentifier(path, "drawable", getPackageName());
        plant.setImageResource(plantId);
        Log.i("plantID : ", Integer.toString(plantId));
        plantIDS = plantId;

        //intent.putExtra("path","plant"+mArray.get(i).getFlower()+mArray.get(i).getPollen());

        //---------------------------프로그레스 바---------------------------
        //프로그레스 바 ID 받아옴
        hp = (ProgressBar)findViewById(R.id.HPBar);
        exp = (ProgressBar)findViewById(R.id.EXPBar);

        //프로그레스 바 수치를 나타내는 textview ID 받아옴
        HPtextview = (TextView)findViewById(R.id.currentHP);
        EXPtextview = (TextView)findViewById(R.id.currentEXP);

        //프로그레스 바의 현재 수치를 int 변수에 받아옴
        currenthp = Integer.parseInt(HPtextview.getText().toString());
        currentexp = Integer.parseInt(EXPtextview.getText().toString());

        //프로그레스 바를 현재 수치 값으로 갱신하여 보여줌
        hp.setProgress(currenthp);
        exp.setProgress(currentexp);
        //-----------------------버튼 이벤트 --------------------------------------

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code
            }
        });

        //가로 버튼
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currenthp++;
                hp.setProgress(currenthp);
                HPtextview.setText(""+currenthp);
                //setPlant("HP",currenthp);
                effect.setImageResource(R.drawable.water);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenthp++;
                hp.setProgress(currenthp);
                HPtextview.setText(""+currenthp);
               //setPlant("HP",currenthp);
                effect.setImageResource(R.drawable.energy);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currenthp++;
                hp.setProgress(currenthp);
                HPtextview.setText(""+currenthp);
                //setPlant("HP",currenthp);
                effect.setImageResource(R.drawable.water);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        ONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp=currentexp+10;
                exp.setProgress(currentexp);
                EXPtextview.setText(""+currentexp);
                //setPlant("EXP",currentexp);
                if (a==0){
                    a=1;
                    ONOFF.setBackgroundResource(R.drawable.off);
                }
                else if(a==1){
                    a=0;
                    ONOFF.setBackgroundResource(R.drawable.on);
                }
            }

        });




        //세로 버튼
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    LevelUp();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    //setPlant("EXP",currentexp);
                    effect.setImageResource(R.drawable.music);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    LevelUp();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    //setPlant("EXP",currentexp);
                    effect.setImageResource(R.drawable.talk);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    LevelUp();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    //setPlant("EXP",currentexp);
                    effect.setImageResource(R.drawable.scissor);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    LevelUp();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    //setPlant("EXP",currentexp);
                    effect.setImageResource(R.drawable.touch);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });
    }

    void delayA() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                effect.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    void LevelUp(){
        if(plantinfomation.LV < 4) {
            setPlant("level", plantinfomation.LV + 1);
            setPlant("EXP",0);
            getPlant();
            currentexp =  plantinfomation.EXP;
            level.setText(plantinfomation.getLV());
        }else{
            // 모든성장완료 응모창 활성화
        }
    }

    class PlantInfomation{
        int PlantNo ;
        int HP;
        int EXP;
        int LV;
        int flower;
        int pollen;

        public void setInfo(int PlantNo, int FlowerNo, int PollenNo, int HP, int EXP, int LV){
            this.PlantNo = PlantNo;
            this.HP = HP;
            this.EXP = EXP;
            this.LV = LV;
            this.flower = FlowerNo;
            this.pollen = PollenNo;
        }

        public int getPlantNo(){return this.PlantNo;}
        public int getHP(){return this.HP;}
        public int getEXP(){return this.EXP;}
        public int getLV(){return this.LV;}
        public int getFlower(){return this.flower;}
        public int getPollen(){return this.pollen;}
    }

    public class FlowerInfomation{

        private int id;
        private String[] flowerImagePath = new String[4];
        private int maxHP;
        private int maxEXP;
        private int maxLevel;
        private int waterTime;
        private int flowerEffect;
        private int fruitTerm;      //열매맺는시간
        private int fruitCost;      //열매가격

        public void setFlowerInfomation(int id, String[] name, int maxHP, int maxEXP, int maxLevel,
                                int waterTime, int flowerEffect, int fruitTerm, int fruitCost){
            this.id = id;
            this.maxHP = maxHP;
            this.maxEXP = maxEXP;
            this.maxLevel = maxLevel;
            this.waterTime = waterTime;
            this.flowerEffect = flowerEffect;
            this.fruitTerm = fruitTerm;
            this.fruitCost = fruitCost;
            this.flowerImagePath = flowerImagePath;
        }

        public int getId(){ return this.id; }
        public int getMaxHP(){ return this.maxHP; }
        public int getMaxEXP(){ return this.maxEXP; }
        public int getMaxLevel(){ return this.maxLevel; }
        public int getWaterTime(){ return this.waterTime; }
        public int getFlowerEffect(){ return this.flowerEffect; }
        public int getFruitTerm(){ return this.fruitTerm; }
        public int getFruitCost(){ return this.fruitCost; }
    }

    public class PollenInfomation{

        private int id;
        private int effect;
        private String pollenImagePath;

        public void setPollenInfomation(int id, int effect, String pollenImagePath){
            this.id = id;
            this.effect = effect;
            this.pollenImagePath = pollenImagePath;
        }

        public String getPollenImagePath(){ return this.pollenImagePath; }
        public int getId(){ return this.id; }
        public int getEffect(){ return this.effect; }
    }

    private void setPlant(final String str, final int num) {

        String url = "http://202.31.200.143/user/setplant/" + String.valueOf(plantIDS);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PlantManagementActivity", "setPlant : " + response);

                        if (response.equals("true")) {
                            Toast.makeText(getApplicationContext(), "Success Plant Infomation", Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Failed Plant Infomatiom", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("PlantManagementActivity", "onErrorResponse : " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put( str, Integer.toString(num));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void getPlant(){

        String url = "http://202.31.200.143/user/setplant/" + String.valueOf(plantIDS);

        final JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("PlantManagementActivity","getPlant : "+response.toString());

                        try{
                            int plantID = response.getInt("plantNo");
                            int flower = response.getInt("flowrNo");
                            int pollen = response.getInt("potNo");
                            int PlantHP = response.getInt("HP");
                            int PlantEXP = response.getInt("EXP");
                            int PlantLV = response.getInt("level");
                            Log.i("Plantmanagement","plant"+flower+pollen);

                            plantinfomation.setInfo(plantID,flower,pollen,PlantHP,PlantEXP,PlantLV);

                        }catch (JSONException e){
                            Log.i("PlantManagementActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("PlantManagementActivity","getPlant Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    private void getFlowerInform(int flowerNo){

        String url = "http://202.31.200.143/user/setflowerinform/" + String.valueOf(flowerNo);

        final JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("PlantManagementActivity","getFlowerInform : "+response.toString());

                        try{
                            int id = response.getInt("flowrNo");
                            int maxHP = response.getInt("maxHP");
                            int maxEXP = response.getInt("maxEXP");
                            int maxLevel = response.getInt("maxLevel");
                            int waterTime = response.getInt("waterTime");
                            int flowerEffect = response.getInt("flowerEffect");
                            int fruitTerm = response.getInt("fruitTerm");
                            int fruitCost = response.getInt("fruitCost");
                            String[] flowerImagePath = new String[4];
                            flowerImagePath[0] = response.getString("flowerImagePath1");
                            flowerImagePath[1] = response.getString("flowerImagePath2");
                            flowerImagePath[2] = response.getString("flowerImagePath3");
                            flowerImagePath[3] = response.getString("flowerImagePath4");

                            flower.setFlowerInfomation(id,flowerImagePath,maxHP,maxEXP,maxLevel,waterTime,flowerEffect,fruitTerm,fruitCost);
                        }catch (JSONException e){
                            Log.i("PlantManagementActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("PlantManagementActivity","getFlowerInform Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getPollenInform(int pollenNo){

        String url = "http://202.31.200.143/user/setpolleninform/" + String.valueOf(pollenNo);

        final JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("PlantManagementActivity","getPollenInform : "+response.toString());

                        try{
                            int id = response.getInt("pollenNo");
                            int effect = response.getInt("effect");
                            String pollenImagepath = response.getString("maxEXP");
                            pollen.setPollenInfomation(id,effect,pollenImagepath);

                        }catch (JSONException e){
                            Log.i("PlantManagementActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("PlantManagementActivity","getPollenInform Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }



    private void getUserInform(){

        String url="http://202.31.200.143/user/"+pref.getString("id","");

        final JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("PlantManagementActivity","getUserInform : "+response.toString());

                        try{

                            int getMedicine = response.getInt("water");
                            int getFerilizer = response.getInt("ferilizer");
                            int getPesticideNum = response.getInt("pesticideNum");

                            // 아이템 연동
                        }catch (JSONException e){
                            Log.i("PlantManagementActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("PlantManagementActivity","getUserInform Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
