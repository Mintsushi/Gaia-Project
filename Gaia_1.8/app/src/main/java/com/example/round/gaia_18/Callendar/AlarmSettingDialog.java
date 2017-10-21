package com.example.round.gaia_18.Callendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.round.gaia_18.R;

/**
 * Created by 리제 on 2017-10-11.
 */

public class AlarmSettingDialog extends Dialog{

    public AlarmSettingDialog(Context context){
        super(context);
    }

    private int alarmType;
    private String alarmText;
    RadioGroup alarmSettingGroup;
    RadioButton radio;
    Button alamrSettingButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.cal_alarm_setting_dialog);

        alarmSettingGroup = (RadioGroup)findViewById(R.id.alarmSettingGroup);
        alarmSettingGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                alarmType = alarmSettingGroup.getCheckedRadioButtonId();
                Log.i("test set", ""+alarmType);
            }
        });

        alamrSettingButton = (Button)findViewById(R.id.alarmSettingButton);
        alamrSettingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                alarmType = AlarmTypeChange();
                dismiss();
            }
        });
    }

    public void setAlarmType(int type){
        this.alarmType = type;
        Log.i("test : ",""+alarmType);
        switch (alarmType){
            case 1:
                radio = (RadioButton)findViewById(R.id.alarmSetting1);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting1;
                break;
            case 2:
                radio = (RadioButton)findViewById(R.id.alarmSetting2);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting2;
                break;
            case 3:
                radio = (RadioButton)findViewById(R.id.alarmSetting3);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting3;
                break;
            case 4:
                radio = (RadioButton)findViewById(R.id.alarmSetting4);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting4;
                break;
            case 5:
                radio = (RadioButton)findViewById(R.id.alarmSetting5);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting5;
                break;
            case 6:
                radio = (RadioButton)findViewById(R.id.alarmSetting5);
                radio.setChecked(true);
                alarmType = R.id.alarmSetting6;
                break;
        }
    }

    public int AlarmTypeChange(){
        switch (alarmType){
            case R.id.alarmSetting1:
                radio = (RadioButton)findViewById(R.id.alarmSetting1);
                alarmText = ""+radio.getText();
                return 1;
            case R.id.alarmSetting2:
                radio = (RadioButton)findViewById(R.id.alarmSetting2);
                alarmText = ""+radio.getText();
                return 2;
            case R.id.alarmSetting3:
                radio = (RadioButton)findViewById(R.id.alarmSetting3);
                alarmText = ""+radio.getText();
                return 3;
            case R.id.alarmSetting4:
                radio = (RadioButton)findViewById(R.id.alarmSetting4);
                alarmText = ""+radio.getText();
                return 4;
            case R.id.alarmSetting5:
                radio = (RadioButton)findViewById(R.id.alarmSetting5);
                alarmText = ""+radio.getText();
                return 5;
            case R.id.alarmSetting6:
                radio = (RadioButton)findViewById(R.id.alarmSetting6);
                alarmText = ""+radio.getText();
                return 6;
            default:
                return 0;
        }

    }

    public int getAlarmType() {
        return alarmType;
    }

    public String getAlarmText() {
        return alarmText;
    }
}
