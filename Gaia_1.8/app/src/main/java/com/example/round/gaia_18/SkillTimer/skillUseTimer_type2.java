package com.example.round.gaia_18.SkillTimer;

import com.example.round.gaia_18.Fragement.MenuSkill;

import java.util.TimerTask;

import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.OverlayService.dataList;

/**
 * Created by Round on 2017-09-25.
 */
public class skillUseTimer_type2 extends SkillUse {

    @Override
    public void startSkill(final int skillType, final int time) {

        TimerTask task = new TimerTask() {
            int cool = 0;

            @Override
            public void run() {
                cool++;
                autoClick();
                if (cool == time * 10) {
                    timer.cancel();
                }
            }
        };
        timer.schedule(task, 0, 100);
    }

    private void autoClick() {
        Runnable updater = new Runnable() {
            public void run() {
                if (dataList.getClickView() == relativeLayout) dataList.windowClick();
                else dataList.overlayWindowClick();
                mOverlayService.setSeed();
                seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
            }
        };

        handler.post(updater);
    }
}
