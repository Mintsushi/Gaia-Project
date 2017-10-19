package com.example.round.gaia_18.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Data.GameSetting;
import com.example.round.gaia_18.OverlayService;
import com.example.round.gaia_18.R;

import static com.example.round.gaia_18.MainActivity.weatherOnOff;
import static com.example.round.gaia_18.OverlayService.dataList;

public class Setting extends Dialog {


    public Setting(Context context){
        super(context);
    }
    private ImageButton back;
    private Button save, load, logout;
    private TextView nameText, emailText, hpGaugeText;
    private SeekBar hpGaugeBar;
    private ImageView logtypeImage;
    // 이름 사용시 수정할 것
    private Switch switch1,switch2,switch3,switch4,switch5;
    private GameSetting setting = dataList.getSetting();

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.setting_dialog);

        // 로그인에 사용된 pref정보를 찾아온다.
        pref = getContext().getSharedPreferences("Login",getContext().MODE_PRIVATE);
        editor = pref.edit();

        String str = pref.getString("nickname","");
        boolean type = pref.getBoolean("autoLogin",false);
        Log.i("pref : ",""+ str + "  /  " + type);

        // pref정보 로딩
        emailText = (TextView)findViewById(R.id.emailText);
        emailText.setText(pref.getString("Email","NULL"));
        nameText = (TextView)findViewById(R.id.nameText);
        nameText.setText(pref.getString("nickname","NULL"));
        logtypeImage = (ImageView)findViewById(R.id.loginTypeImage);

        // 로그인타입이 구글이면.. 임시로 구글이미지 넣음
        // 로그인 페이지  구현과 적절한 상의후 추가 구현 필요
        if(pref.getString("LoginType","null").equals("Google")){
            logtypeImage.setImageResource(R.drawable.google);
        }

        back = (ImageButton)findViewById(R.id.settingExit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }

        });

        save = (Button)findViewById(R.id.saveButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save Event
                Toast.makeText(getContext().getApplicationContext(), "Save", Toast.LENGTH_SHORT).show();
            }

        });

        load = (Button)findViewById(R.id.loadButton);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Load Event
                Toast.makeText(getContext().getApplicationContext(), "Load", Toast.LENGTH_SHORT).show();
            }

        });

        // 로그아웃. pref정보를 모두 삭제한후 어플 재시작.
        logout = (Button)findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Load Event
                Toast.makeText(getContext().getApplicationContext(), "어플을 재시작해주세요.", Toast.LENGTH_SHORT).show();
                editor.clear();
                editor.commit();
                android.os.Process.killProcess(android.os.Process.myPid());
            }

        });

        hpGaugeText = (TextView)findViewById(R.id.hpGaugeText);
        hpGaugeText.setText(""+setting.getHpAparmGauge() + " / 100");
        hpGaugeBar = (SeekBar)findViewById(R.id.hpGaugeBar);
        hpGaugeBar.setMax(100);
        hpGaugeBar.setProgress(setting.getHpAparmGauge());
        hpGaugeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                hpGaugeText.setText(""+setting.getHpAparmGauge() + " / 100");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                hpGaugeText.setText(""+setting.getHpAparmGauge() + " / 100");
            }

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setting.setHpAparmGauge(progress);
                hpGaugeText.setText(""+setting.getHpAparmGauge() + " / 100");
            }
        });


        switch1 = (Switch)findViewById(R.id.switch1);
        if(setting.getVibration()==1){switch1.setChecked(true);}
        switch1.setText("진동");
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    setting.setVibration(1);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    setting.setVibration(0);
                }
            }
        });

        switch2 = (Switch)findViewById(R.id.switch2);
        if(setting.getSound()==1){switch2.setChecked(true);}
        switch2.setText("소리");
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();

                    setting.setSound(1);
                } else {

                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    setting.setSound(0);
                }
            }
        });

        switch3 = (Switch)findViewById(R.id.switch3);

        if(setting.getAlarm()==1){switch3.setChecked(true);}
        switch3.setText("알람");
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    setting.setAlarm(1);
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    setting.setAlarm(0);
                }
            }
        });

        switch4 = (Switch)findViewById(R.id.switch4);
        if( setting.getWeather()==1){switch4.setChecked(true);}
        switch4.setText("날씨알림");
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    weatherOnOff(true);
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();

                    setting.setWeather(1);
                } else {
                    weatherOnOff(false);
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();

                    setting.setWeather(0);
                }
            }
        });

        switch5 = (Switch)findViewById(R.id.switch5);
        if(setting.getHpAparm()==1){
            switch5.setChecked(true);
            hpGaugeText.setVisibility(View.VISIBLE);
            hpGaugeBar.setVisibility(View.VISIBLE);
        }
        else {
            hpGaugeText.setVisibility(View.INVISIBLE);
            hpGaugeBar.setVisibility(View.INVISIBLE);
        }

        switch5.setText("HP경고");
        switch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    OverlayService.nitificationOnOff(true);
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    setting.setHpAparm(1);
                    hpGaugeText.setVisibility(View.VISIBLE);
                    hpGaugeBar.setVisibility(View.VISIBLE);
                } else {
                    OverlayService.nitificationOnOff(false);
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    setting.setHpAparm(0);
                    hpGaugeText.setVisibility(View.INVISIBLE);
                    hpGaugeBar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

}
