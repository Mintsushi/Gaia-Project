package com.example.round.gaia_18.Fragement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.round.gaia_18.R;

import static com.example.round.gaia_18.MainActivity.settingVar;
import static com.example.round.gaia_18.MainActivity.weatherOnOff;

/**
 * Created by 리제 on 2017-09-15.
 * 김태우 설정창 구현 // 각 필요요소에 이밴트 추가해서 사용할것
 * 아직 정해진 설정값이 없어 토스트로 대체
 * 9.22 날씨알람 연동
 * main의
 * settingVar 변수와
 * weatherOnOff로 날씨알람 제어
 */

public class settingDialog extends Dialog {
    public settingDialog(Context context){super(context);}
    private ImageButton back;
    private Button save, load;
    // 이름 사용시 수정할 것
    private Switch switch1,switch2,switch3,switch4;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_setting);

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
        switch1 = (Switch)findViewById(R.id.switch1);
        if(settingVar[0]==1){switch1.setChecked(true);}
        switch1.setText("진동");
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    settingVar[0]=1;
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    settingVar[0]=0;
                }
            }
        });

        switch2 = (Switch)findViewById(R.id.switch2);
        if(settingVar[1]==1){switch2.setChecked(true);}
        switch2.setText("소리");
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    settingVar[1]=1;
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    settingVar[1]=0;
                }
            }
        });

        switch3 = (Switch)findViewById(R.id.switch3);

        if(settingVar[2]==1){switch3.setChecked(true);}
        switch3.setText("알람");
        switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    settingVar[2]=1;
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    settingVar[2]=0;
                }
            }
        });

        switch4 = (Switch)findViewById(R.id.switch4);
        if(settingVar[3]==1){switch4.setChecked(true);}
        switch4.setText("날씨이팩트");
        switch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true){
                    weatherOnOff(true);
                    Toast.makeText(getContext().getApplicationContext(), "On", Toast.LENGTH_SHORT).show();
                    settingVar[3]=1;
                } else {
                    weatherOnOff(false);
                    Toast.makeText(getContext().getApplicationContext(), "Off", Toast.LENGTH_SHORT).show();
                    settingVar[3]=0;
                }
            }
        });


    }

}
