package com.example.round.neopul15;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    ImageView effect;
    int maxLevel;

    ImageView levelupview;
    //-------------------기타 버튼--------------------------------
    Button back;
    ImageButton inform;
    ImageButton entry;

    //-------------------가로 세로 관리 버튼 -------------------
    ImageButton itemMB;
    ImageButton touchMB;
    LinearLayout item11;
    LinearLayout EXPget11;

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

    //프로그레스 바 최대치
    TextView maxHPtextview;
    TextView maxEXPtextview;

    //현재 아이템 수량
    TextView waterNumtextview;
    TextView energyNumtextview;
    TextView medicienNumtextview;

    //프로그레스 바의 최대 수치 ()
    int maxHPProgress;
    int maxEXPProgress;
    //프로그레스 바의 체력당비율
    int setProgressResult;
    //---------------------------기타 보조 변수---------------------------
    int a; //위젯 ON/OFF 변경 용 변수
    int informup[] = {0,0};
    //-------------------아이템 변수----------------------------------------
    int waternum = 0;
    int energynum = 0;
    int mediciennum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_management);
        pref = getApplicationContext().getSharedPreferences("Login", getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        // -----------------인턴트----------------------
        Intent intent = getIntent();
        String IDID = intent.getStringExtra("plantNewID");
        Log.i("plantID : ", IDID);
        plantIDS = Integer.parseInt(IDID);

        // --------------데이터갱신---------------------
        getPlant();
        getUserInform();
        //--------------------------꽃 정보 --------------------------
        name = (TextView) findViewById(R.id.PlantName);
        level = (TextView) findViewById(R.id.PlantLV);

        //------------------------------------------------------------------
        effect = (ImageView) findViewById(R.id.buttonEffectImage);
        effect.setVisibility(View.INVISIBLE);

        //------------------아이템/이밴트----------------------------------
        itemMB = (ImageButton) findViewById(R.id.bagButton);
        touchMB = (ImageButton) findViewById(R.id.touchButton);
        item11 = (LinearLayout)findViewById(R.id.ITEM11);
        EXPget11 = (LinearLayout)findViewById(R.id.EXPGET11);
        item11.setVisibility(View.INVISIBLE);
        EXPget11.setVisibility(View.INVISIBLE);

        //------------------기타 버튼----------------
        levelupview = (ImageView)findViewById(R.id.levelupview);
        levelupview.setVisibility(View.INVISIBLE);
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

        //------- 정보,응모버튼-----------------------

        inform = (ImageButton)findViewById(R.id.flowerInfoBut);
        entry= (ImageButton)findViewById(R.id.flowerEntryBtn);
        entry.setVisibility(View.INVISIBLE);

        //---------------------------프로그레스 바---------------------------
        //프로그레스 바 ID 받아옴
        hp = (ProgressBar)findViewById(R.id.HPbar);
        exp = (ProgressBar)findViewById(R.id.EXPbar);

        // 체력 경험치 최대치 나타내는 textview ID 받아옴
        maxHPtextview = (TextView)findViewById(R.id.maxHP);
        maxEXPtextview = (TextView)findViewById(R.id.maxEXP);

        //프로그레스 바 수치를 나타내는 textview ID 받아옴
        HPtextview = (TextView)findViewById(R.id.currentHP);
        EXPtextview = (TextView)findViewById(R.id.currentEXP);

        // -------------아이템 수량------------------------
        waterNumtextview = (TextView)findViewById(R.id.waterNumText);
        energyNumtextview = (TextView)findViewById(R.id.energyNumText);
        medicienNumtextview = (TextView)findViewById(R.id.medicineNumText);
        //-----------------------버튼 이벤트 --------------------------------------


        //관리버튼
        itemMB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item11.getVisibility()==View.VISIBLE){
                    item11.setVisibility(View.INVISIBLE);
                }
                else{
                    item11.setVisibility(View.VISIBLE);
                }
            }//onClick
        });

        //관리버튼
        touchMB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(EXPget11.getVisibility()==View.VISIBLE){
                    EXPget11.setVisibility(View.INVISIBLE);
                }
                else{
                    EXPget11.setVisibility(View.VISIBLE);
                }
            }//onClick
        });

        // 온오프버튼
        ONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //가로 버튼
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==maxHPProgress){
                    Toast.makeText(getApplicationContext(), "Pull P", Toast.LENGTH_LONG).show();
                }
                else {
                    if(waternum <= 0){
                        return;
                    }
                    else {
                        setItem(1,1);

                        currenthp++;
                        hp.setProgress(currenthp / setProgressResult);
                        HPtextview.setText("" + currenthp);
                        setPlant(1, String.valueOf(currenthp));
                        effect.setImageResource(R.drawable.water);
                        effect.setVisibility(View.VISIBLE);
                        delayA();
                    }
                }
            }//onClick
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( currenthp == maxHPProgress){
                    Toast.makeText(getApplicationContext(), "Pull P", Toast.LENGTH_LONG).show();
                }
                else {
                    if(energynum <= 0){
                        return;
                    }
                    else {
                        setItem(2,2);

                        //체력 5증가
                        if (currenthp + 5 > maxHPProgress) {
                            currenthp = maxHPProgress;
                        } else {
                            currenthp = currenthp + 5;
                        }

                        hp.setProgress(currenthp / setProgressResult);
                        HPtextview.setText("" + currenthp);
                        setPlant(1, String.valueOf(currenthp));
                        effect.setImageResource(R.drawable.energy);
                        effect.setVisibility(View.VISIBLE);
                        delayA();
                    }
                }
            }//onClick
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==maxHPProgress){
                    Toast.makeText(getApplicationContext(), "Pull P", Toast.LENGTH_LONG).show();
                }
                else {
                    if(mediciennum <= 0){
                        return;
                    }
                    else {
                        setItem(3,3);
                        //체력 10증가
                        if (currenthp + 10 > maxHPProgress) {
                            currenthp = maxHPProgress;
                        } else {
                            currenthp = currenthp + 10;
                        }
                        hp.setProgress(currenthp / setProgressResult);
                        HPtextview.setText("" + currenthp);
                        setPlant(1, String.valueOf(currenthp));
                        effect.setImageResource(R.drawable.water);
                        effect.setVisibility(View.VISIBLE);
                        delayA();
                    }
                }
            }//onClick
        });

        //세로 버튼
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp++;
                if(maxEXPProgress<= currentexp){
                    LevelUp(currentexp-maxEXPProgress);
                }
                else{
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    setPlant(2,String.valueOf(currentexp));
                }
                effect.setImageResource(R.drawable.music);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp = currentexp + 5;
                if(maxEXPProgress<= currentexp){
                    LevelUp(currentexp-maxEXPProgress);
                }
                else{
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    setPlant(2,String.valueOf(currentexp));
                }
                effect.setImageResource(R.drawable.talk);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp = currentexp + 10;
                if(maxEXPProgress<= currentexp){
                    LevelUp(currentexp-maxEXPProgress);
                }
                else{
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    setPlant(2,String.valueOf(currentexp));
                }
                effect.setImageResource(R.drawable.scissor);
                effect.setVisibility(View.VISIBLE);
                delayA();
            }//onClick
        });

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp = currentexp + 50;

                if(maxEXPProgress<= currentexp){
                    LevelUp(currentexp-maxEXPProgress);

                }
                else{
                    exp.setProgress(currentexp);
                    Log.i("EXPview else",""+currentexp);
                    EXPtextview.setText(""+currentexp);
                    setPlant(2,String.valueOf(currentexp));
                    Log.i("Up else "," "+currentexp);
                }
                effect.setImageResource(R.drawable.touch);
                effect.setVisibility(View.VISIBLE);
                delayA();
                Log.i("Up if "," ----------------------");
            }//onClick
        });

        //----------기타버튼-------------------------
        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PlantManagementInformActivity dialog = new PlantManagementInformActivity(PlantManagementActivity.this);
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.setname(flower.getName());
                        Log.i("path","::"+flower.getRealImage());
                        dialog.setimage(flower.getRealImage());
                        dialog.setexplain(flower.getDexp());
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        //helloworld.setText(dialog.getMode());
                    }
                });
                dialog.show();
            }
        });

        entry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final PlantmanagementEntryActivity dialog = new PlantmanagementEntryActivity(PlantManagementActivity.this);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        int entrytype;
                    }
                });
                dialog.show();
            }
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

    void delayB() {
        Handler handler = new Handler();
        levelupview.setVisibility(View.VISIBLE);
        handler.postDelayed(new Runnable() {
            public void run() {
                levelupview.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    void LevelUpGrowth() {
        int[] step = new int[3];
        step = flower.getGrowthStep();
        Log.i("step", ":: "+step[0]);
        Log.i("step", ":: "+step[1]);
        Log.i("step", ":: "+step[2]);
        if (plantinfomation.LV-1 == step[0]) {
            setPlant(4,flower.getflowerImagePath()+"1");
            Log.i("ImageChainge", "Path:: "+flower.getflowerImagePath()+"1");
            Log.i("ImageChainge", "LV:: "+plantinfomation.LV);
            return;
        }else if (plantinfomation.LV-1 == step[1]) {
            setPlant(4, flower.getflowerImagePath() + "2");
            Log.i("ImageChainge", "Path:: "+flower.getflowerImagePath()+"2");
            Log.i("ImageChainge", "LV:: "+plantinfomation.LV);
            return;
        }else if (plantinfomation.LV-1 == step[2]) {
            setPlant(4, flower.getflowerImagePath() + "3");
            Log.i("ImageChainge", "Path:: "+flower.getflowerImagePath()+"3");
            Log.i("ImageChainge", "LV:: "+plantinfomation.LV);
            return;
        }else {
            return;
        }
    }

    void LevelUp(int a){
        if(plantinfomation.LV < maxLevel) {
            delayB();
            exp.setProgress(a);
            Log.i("Up setOK "," "+a);
            EXPtextview.setText(""+a);
            Log.i("EXPview 1",""+currentexp);
            setPlant(3, String.valueOf(plantinfomation.LV + 1));
            setPlant(2, String.valueOf(a));
            LevelUpGrowth();
            getPlant();
            Log.i("Up setOK "," "+plantinfomation.LV);
        }else{
            exp.setProgress(maxEXPProgress);
            EXPtextview.setText(""+maxEXPProgress);
            setPlant(2,String.valueOf(maxEXPProgress));
            Toast.makeText(getApplicationContext(), "응모창주세여 뀨우", Toast.LENGTH_LONG).show();
        }
    }

     class PlantInfomation{
        int PlantNo ;
        int HP;
        int EXP;
        int LV;
        int flower;
        int pollen;
        String flowerImagePath;
         String potImagePath;


        public void setInfo(int PlantNo, int FlowerNo, int PollenNo, int HP, int EXP, int LV,String FIP,String PIP ){
            this.PlantNo = PlantNo;
            this.HP = HP;
            this.EXP = EXP;
            this.LV = LV;
            this.flower = FlowerNo;
            this.pollen = PollenNo;
            this.flowerImagePath = FIP;
            this.potImagePath = PIP;
        }

        public int getPlantNo(){return this.PlantNo;}
        public int getHP(){return this.HP;}
        public int getEXP(){return this.EXP;}
        public int getLV(){return this.LV;}
        public int getFlower(){return this.flower;}
        public int getPollen(){return this.pollen;}
         public String getFlowerImagePath(){return this.flowerImagePath;}
         public String getPotImagePath(){return this.potImagePath;}
    }

     class FlowerInfomation{

        private int id;
        private String flowerImagePath;
         private int[] flowerGrowthStep = new int[3];
        private int maxHP;
        private int maxEXP;
        private int maxLevel;
        private int waterTime;
        private int flowerEffect;
        private int fruitTerm;      //열매맺는시간
        private int fruitCost;      //열매가격

         private String name;
         private String Dexp;
        private String realImage;


        public void setFlowerInfomation(int id, String flowerImagePath, int maxHP, int maxEXP, int maxLevel,
                                int waterTime, int flowerEffect, int fruitTerm, int fruitCost, String name, String realImage,
                                        String dexp, int[] step){
            this.id = id;
            this.maxHP = maxHP;
            this.maxEXP = maxEXP;
            this.maxLevel = maxLevel;
            this.waterTime = waterTime;
            this.flowerEffect = flowerEffect;
            this.fruitTerm = fruitTerm;
            this.fruitCost = fruitCost;
            this.flowerImagePath = flowerImagePath;
            this.name = name;
            this.Dexp = dexp;
            this.realImage = realImage;
            this.flowerGrowthStep = step;
        }

        public int getId(){ return this.id; }
        public int getMaxHP(){ return this.maxHP; }
        public int getMaxEXP(){ return this.maxEXP; }
        public int getMaxLevel(){ return this.maxLevel; }
        public int getWaterTime(){ return this.waterTime; }
        public int getFlowerEffect(){ return this.flowerEffect; }
        public int getFruitTerm(){ return this.fruitTerm; }
        public int getFruitCost(){ return this.fruitCost; }
         public String getflowerImagePath(){return this.flowerImagePath;}
         public String getName(){return this.name;}
         public String getDexp(){return this.Dexp;}
         public String getRealImage(){return this.realImage;}
         public int[] getGrowthStep(){return this.flowerGrowthStep;}
    }

     class PollenInfomation{

        private int id;
        private int HPeffect;
         private int EXPeffect;
        private String pollenImagePath;

        public void setPollenInfomation(int id, int HPeffect, int EXPeffect, String pollenImagePath){
            this.id = id;
            this.HPeffect = HPeffect;
            this.EXPeffect = EXPeffect;
            this.pollenImagePath = pollenImagePath;
        }

        public String getPollenImagePath(){ return this.pollenImagePath; }
        public int getId(){ return this.id; }
        public int getHPEffect(){ return this.HPeffect; }
         public int getEXPEffect(){ return this.EXPeffect; }
    }

    private void setPlant(final int type, final String num) {

        String url = "http://202.31.200.143/user/getplant";

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
                params.put( "plant", Integer.toString(plantIDS));
                params.put( "type", Integer.toString(type));
                params.put( "num", num);
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
                            int flower = response.getInt("flowerNo");
                            int pollen = response.getInt("potNo");
                            int PlantHP = response.getInt("HP");
                            int PlantEXP = response.getInt("EXP");
                            int PlantLV = response.getInt("level");
                            String flowerImagePath = response.getString("flowerImagePath");
                            String potImagePath = response.getString("potImagePath");

                            plantinfomation.setInfo(plantID,flower,pollen,PlantHP,PlantEXP,PlantLV,flowerImagePath,potImagePath);

                            ImageView plantflower = (ImageView) findViewById(R.id.plantflowerImage);
                            ImageView plantpot = (ImageView) findViewById(R.id.plantpotImage);

                            int plantId = getResources().getIdentifier(flowerImagePath, "drawable", getPackageName());
                            int plantpotId = getResources().getIdentifier(potImagePath, "drawable", getPackageName());
                            Log.i("tlqkf exp ",""+PlantEXP);
                            Log.i("tlqkf lv ",""+PlantLV);
                            Log.i("tlqkf setflowetpath ",flowerImagePath);
                            plantflower.setImageResource(plantId);
                            plantpot.setImageResource(plantpotId);

                            currenthp = plantinfomation.getHP();
                            currentexp =  plantinfomation.getEXP();

                            if(informup[0]==0){  getFlowerInform(plantinfomation.getFlower()); };
                            if(informup[1]==0){  getPollenInform(plantinfomation.getPollen()); };

                            level.setText("LV " + String.valueOf(plantinfomation.getLV()));
                            Log.i("plantinfomation.LV()","::"+plantinfomation.getLV());
                            if(30== plantinfomation.getLV()){
                                Log.i("plantinfomation.EXP()","::"+plantinfomation.getEXP());
                                if(100 == plantinfomation.getEXP()) {
                                    entry.setVisibility(View.VISIBLE);
                                }
                            }
                            name.setText(plantinfomation.getFlowerImagePath());

                            HPtextview.setText(String.valueOf(plantinfomation.getHP()));
                            EXPtextview.setText(String.valueOf(plantinfomation.getEXP()));
                            Log.i("EXPview 2",""+plantinfomation.getEXP());
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
                            int id = response.getInt("flowerNo");
                            int maxHP = response.getInt("maxHP");
                            int Level = response.getInt("maxLevel");
                            int waterTime = response.getInt("waterTime");
                            int flowerEffect = response.getInt("purchaseType");
                            int fruitTerm = response.getInt("fruitTime");
                            int fruitCost = response.getInt("HowMuchFruit");
                            String name = response.getString("flowerName");
                            String realImage = response.getString("flowerRealImagePath");
                            String detexplain = response.getString("flowerDetailedExplain");

                            String growStep = response.getString("flowerGrowthStep");

                            int[] growthStep = new int[3];
                            growthStep[0] = Integer.parseInt(growStep.substring(0,2));
                            growthStep[1] = Integer.parseInt(growStep.substring(2,4));
                            growthStep[2] = Integer.parseInt(growStep.substring(4,6));

                            Log.i("growthStep ", ":: "+growthStep[0]);
                            Log.i("growthStep ", ":: "+growthStep[1]);
                            Log.i("growthStep ", ":: "+growthStep[2]);
                            String flowerImagePath = response.getString("flowerImagePath");
                            flower.setFlowerInfomation(id,flowerImagePath,maxHP,100,maxLevel,waterTime,flowerEffect,fruitTerm,fruitCost,name,realImage,detexplain,growthStep);
                            maxLevel = Level;
                            setProgressResult = flower.getMaxHP()/100;
                            maxHPProgress = flower.getMaxHP();
                            maxEXPProgress = flower.getMaxEXP();
                            progressset();
                            informup[0] =1;

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
                            int id = response.getInt("potNo");
                            int HPeffect = response.getInt("HPEffect");
                            int EXPeffect = response.getInt("EXPEffect");
                            String pollenImagepath = response.getString("potImagePath");

                            pollen.setPollenInfomation(id,HPeffect,EXPeffect,pollenImagepath);
                            informup[1] = 1;
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

                            waternum = response.getInt("waterNum");
                            energynum = response.getInt("ferilizerNum");
                            mediciennum  = response.getInt("pesticideNum");

                            waterNumtextview.setText(String.valueOf(waternum));
                            energyNumtextview.setText(String.valueOf(energynum));
                            medicienNumtextview.setText(String.valueOf(mediciennum));

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

    private void setItem(final int itemID, final int type) {

        String url = "http://202.31.200.143/user/item";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("PlantManagementActivity", "setitem : " + response);

                        if (response.equals("true")) {
                            Toast.makeText(getApplicationContext(), "Success item Infomation", Toast.LENGTH_LONG).show();
                            getUserInform();

                        } else {
                            Toast.makeText(getApplicationContext(), "Failed item Infomatiom", Toast.LENGTH_LONG).show();
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
                params.put("email",pref.getString("id",""));
                params.put("itemID",Integer.toString(itemID));
                    params.put("Type",Integer.toString(type));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }

    private void progressset(){

        // 체력 경험치 최대치 설정
        maxHPtextview.setText("/ " + maxHPProgress);
        maxEXPtextview.setText("/ " + maxEXPProgress);

        //프로그레스 바의 현재 수치를 int 변수에 받아옴
        currenthp = Integer.parseInt(HPtextview.getText().toString());
        currentexp = Integer.parseInt(EXPtextview.getText().toString());
        Log.i("EXPview 3",""+currentexp);
        //프로그레스 바를 현재 수치 값으로 갱신하여 보여줌
        hp.setProgress(currenthp/setProgressResult);
        exp.setProgress(currentexp);
    }
}
