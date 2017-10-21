package com.example.round.gaia_18.Callendar;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.round.gaia_18.R;

/**
 * Created by 리제 on 2017-10-14.
 */

public class TimeSettingDialog extends Dialog implements TimePicker.OnTimeChangedListener{

    public TimeSettingDialog(Context context){
        super(context);
    }

    private int hh, mm;
    private TimePicker timePicker;
    private Button timeSettingButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.cal_time_setting_dialog);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        timePicker.setOnTimeChangedListener(this);

        timeSettingButton = (Button) findViewById(R.id.timeSettingButton);
        timeSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public int getHh() {
        return hh;
    }
    public int getMm() {
        return mm;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        hh = hourOfDay;
        mm = minute;
    }
}