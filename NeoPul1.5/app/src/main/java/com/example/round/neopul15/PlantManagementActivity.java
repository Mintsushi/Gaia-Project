package com.example.round.neopul15;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Round on 2017-06-13.
 */

public class PlantManagementActivity extends AppCompatActivity {
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

    //프로그레스 바 수치 변동을 위한 변수 초기화
    public int CurrentHP = 50;
    public int CurrentEXP = 0;

    //프로그레스 바의 최대 수치
    final int totalProgress = 100;

    //---------------------------기타 보조 변수---------------------------
    int a; //위젯 ON/OFF 변경 용 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_management);

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

    }


}


