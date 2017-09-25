package com.example.round.gaia_18.SkillTimer;

import android.util.Log;

import java.util.TimerTask;

import static com.example.round.gaia_18.OverlayService.dataList;

/**
 * Created by Round on 2017-09-25.
 */

public class skillUseTimer_type0 extends SkillUse{

    @Override
    public void startSkill(final int skillType, final int time){

        dataList.effectSkill(skillType);

        TimerTask task = new TimerTask() {
            int cool = 0;
            @Override
            public void run() {
                cool++;
                if(cool == time) {
                    Log.i("Time","Time Finished");
                    timer.cancel();
                    dataList.finishSkill(skillType);
                }
            }
        };
        timer.schedule(task, 0,1000);
    }
}