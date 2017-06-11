package com.example.neopul14;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by 리제 on 2017-05-09.
 */

// 관리창 코드

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

    // 화면을 1초동안 딜래이 시키는 함수
    void delayA() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                effect.setVisibility(View.INVISIBLE);
            }
        }, 1000);
    }

    // EntryView (응모창)을 출현시키는 함수
    void EntryViewActivition(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.entryview, new EntryCheckActivity())
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_management);

        effect = (ImageView)findViewById(R.id.buttonEffectImage);
        effect.setVisibility(View.INVISIBLE);
        back = (Button)findViewById(R.id.button);
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


        if(currentexp==totalProgress) {
            EntryViewActivition();
        }
        //---------------------------onClick 함수들---------------------------
        // 함수의동작은 ONOFF Button 제외하고 모두 비슷함
        // 버튼이 클릭될경우 최대값이라면 가로버튼은 응모창을 띠워주고 세로버튼은 아무일도 없다
        //                   최대값이 아니라면 가로세로버튼 모두 해당 이미지(애니매이션)을 보여주며 1증가된다
        //
        // ONOFF의 경우 on이 된다면 오버레이뷰 활성화
        //              off가 된다면 오버레이뷰 비활성화
        //  따라서 나중에 오버레이뷰 활성여부도 저장시켜야함
        // 일단 테스트를위해 온오프버튼에 경험치증가 10을 해놨으니 나중에 삭제후 해당내용의 주석을 지워주세요


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }//onClick
        });



        //가로 버튼
        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( currentexp==totalProgress){
                    EntryViewActivition();
                }
                else {

                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    effect.setImageResource(R.drawable.water);
                    effect.setVisibility(View.VISIBLE);
                    delayA();

                }
            }//onClick
        });

        energy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    EntryViewActivition();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    effect.setImageResource(R.drawable.energy);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( currentexp==totalProgress){
                    EntryViewActivition();
                }
                else {
                    currentexp++;
                    exp.setProgress(currentexp);
                    EXPtextview.setText(""+currentexp);
                    effect.setImageResource(R.drawable.medicine);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });

        ONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentexp=currentexp+10;
                exp.setProgress(currentexp);
                EXPtextview.setText(""+currentexp);
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
                    effect.setImageResource(R.drawable.music);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
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
                    effect.setImageResource(R.drawable.talk);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
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
                    effect.setImageResource(R.drawable.scissor);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
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
                    effect.setImageResource(R.drawable.touch);
                    effect.setVisibility(View.VISIBLE);
                    delayA();
                }
            }//onClick
        });





    }

}



