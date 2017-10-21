package com.example.round.gaia_18.Callendar;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.round.gaia_18.Data.ScheduleItem;
import com.example.round.gaia_18.R;

import java.util.Calendar;

/**
 * Created by 리제 on 2017-10-11.
 */

public class ScheduleDialog extends Dialog{

    public ScheduleDialog(Context context){
        super(context);
    }
    Button schAlarmSettingButton, schTimeSettingButton, schSaveButton, schDismissButton;
    TextView schAlarmSettingText, schTimeSettingText, schAlarmThisDayText;
    EditText schTitleEditText, schMemoEditText;
    private int alarmType = 1, dismissType = 0;
    private ScheduleItem scheduleItem;
    private String today, title, memo;
    private int yy, mm, dd;
    private int hhTime,mmTime;
    private String appmStr;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.cal_schedule_add_view);

        schAlarmSettingButton = (Button)findViewById(R.id.schAlarmSettingButton);
        schAlarmSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlarmSettingDialog dialog = new AlarmSettingDialog(view.getContext());
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.setAlarmType(alarmType);
                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        alarmType = dialog.getAlarmType();
                        schAlarmSettingText = (TextView)findViewById(R.id.schAlarmSettingText);
                        schAlarmSettingText.setText(""+dialog.getAlarmText());
                    }
                });
                dialog.show();
            }

        });

        Calendar calendar = Calendar.getInstance();
        hhTime = calendar.get(calendar.HOUR_OF_DAY);
        mmTime = calendar.get(calendar.MINUTE);

        schTimeSettingText = (TextView)findViewById(R.id.schTimeSettingText);
        schTimeSettingText.setText(""+hhTime +" 시  " + mmTime + " 분");

        schTimeSettingButton = (Button)findViewById(R.id.schATimeSettingButton);
        schTimeSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final com.example.round.gaia_18.Callendar.TimeSettingDialog dialog = new com.example.round.gaia_18.Callendar.TimeSettingDialog(view.getContext());
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {

                    }
                });

                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        hhTime = dialog.getHh();
                        if(dialog.getMm() < 10){
                            //표기시 바꿔주자
                            mmTime =0 + dialog.getMm();
                        }
                        else{
                            mmTime = dialog.getMm();
                        }
                        schTimeSettingText.setText(""+dialog.getHh() +" 시  " + dialog.getMm() + " 분");
                    }
                });
                dialog.show();

            }

        });


        schSaveButton = (Button)findViewById(R.id.schSaveButton);
        schSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissType = 1;
                schTitleEditText = (EditText)findViewById(R.id.schTitleEditText);
                schMemoEditText = (EditText)findViewById(R.id.schMemoEditText);
                title = ""+schTitleEditText.getText();
                memo = ""+schMemoEditText.getText();
                if(title.equals("")){
                    title = "제목없음";
                }
                if(memo.equals("")){
                    memo = "내용없음";
                }
                scheduleItem = new ScheduleItem(3,yy,mm,dd, hhTime, mmTime, title, memo, alarmType);
                dismiss();
            }
        });

        schDismissButton = (Button)findViewById(R.id.schDismissButton);
        schDismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissType = 0;
                dismiss();
            }
        });
    }


    public void setToday(int yy, int mm, int dd){
        setYy(yy); setMm(mm); setDd(dd);
        today = ""+yy+"년 "+mm+"월 "+dd+"일";
        schAlarmThisDayText = (TextView)findViewById(R.id.thisDayText);
        schAlarmThisDayText.setText(""+today+" 일정");
    }

    public ScheduleItem getScheduleISet(){
        return scheduleItem;
    }

    public int getDismissType(){
        return dismissType;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }
    public void setMm(int mm) {
        this.mm = mm;
    }
    public void setDd(int dd) {
        this.dd = dd;
    }


}