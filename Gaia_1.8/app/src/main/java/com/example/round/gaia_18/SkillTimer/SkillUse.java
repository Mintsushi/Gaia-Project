package com.example.round.gaia_18.SkillTimer;

import android.os.Handler;

import java.util.Timer;

/**
 * Created by Round on 2017-09-25.
 */
abstract class SkillUse{

    Handler handler= new Handler();
    Timer timer = new Timer();

    protected abstract void startSkill(final int skillType, final int time);
}