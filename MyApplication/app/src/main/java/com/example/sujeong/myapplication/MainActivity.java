package com.example.sujeong.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

        //---------------------------가로 버튼---------------------------
        water = (Button)findViewById(R.id.btnWater);
        energy = (Button)findViewById(R.id.btnEnergy);
        medicine = (Button)findViewById(R.id.btnMedicine);
        ONOFF = (Button)findViewById(R.id.ONOFF);

        //---------------------------세로 버튼---------------------------
        music = (Button)findViewById(R.id.btnMusic);
        talk = (Button)findViewById(R.id.btnTalk);
        scissors = (Button)findViewById(R.id.btnScissors);
        touch = (Button)findViewById(R.id.btnTouch);


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





        //---------------------------기타 보조 변수---------------------------
        a = 0;




        //---------------------------onClick 함수들---------------------------
        //가로 버튼
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){

                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);

                }
            }//onClick
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){

                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);

                }
            }//onClick
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){

                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);

                }
            }//onClick
        });

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




        //세로 버튼
        music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==totalProgress){

                }
                else {
                    currenthp++;
                    hp.setProgress(currenthp);
                    HPtextview.setText(""+currenthp);

                }
            }//onClick
        });

        talk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==totalProgress){

                }
                else {
                    currenthp++;
                    hp.setProgress(currenthp);
                    HPtextview.setText(""+currenthp);

                }
            }//onClick
        });

        scissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==totalProgress){

                }
                else {
                    currenthp++;
                    hp.setProgress(currenthp);
                    HPtextview.setText(""+currenthp);

                }
            }//onClick
        });

        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currenthp==totalProgress){

                }
                else {
                    currenthp++;
                    hp.setProgress(currenthp);
                    HPtextview.setText(""+currenthp);

                }
            }//onClick
        });





    }
}
