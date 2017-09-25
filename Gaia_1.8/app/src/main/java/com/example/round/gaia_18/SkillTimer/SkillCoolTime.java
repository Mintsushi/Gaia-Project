package com.example.round.gaia_18.SkillTimer;

import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.round.gaia_18.Data.SkillInfo;
import com.example.round.gaia_18.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.OverlayService.dataList;

/**
 * Created by Round on 2017-09-25.
 */
public class SkillCoolTime{

    private final Handler handler= new Handler();
    Timer timer = new Timer();

    public SkillCoolTime(){}

    public void skillCoolTime(final SkillInfo skillInfo){

        final int time = skillInfo.getCoolTime();

        TimerTask task = new TimerTask() {
            int cool = 0;

            @Override
            public void run() {
                cool++;
                int newTime = time-cool;
                int hour = newTime /3600;
                newTime %= 3600;
                int min = newTime / 60;
                int sec = newTime % 60;
                updateSec(hour,sec,min,skillInfo.getSkillCoolTime());
                if(cool == time+1) {
                    skillInfo.setSkillUseState(false);
                    timer.cancel();
                    if(dataList.mAdapter != null){
                        dataList.mAdapter.notifyDataSetChanged();
                    }
                    if(dataList.overlaySkillAdpter != null){
                        dataList.overlaySkillAdpter.notifyDataSetChanged();
                    }
                }
            }
        };

        timer.schedule(task, 0,1000);
    }

    protected void updateSec(final int hour, final int sec, final int min, final TextView coolTime){
        Runnable updater = new Runnable() {

            public void run() {
                coolTime.setText(min+":"+sec);
                if(min == 0 && sec == 0){
                    coolTime.setVisibility(View.INVISIBLE);
                }
            }

        };
        handler.post(updater);
    }
}