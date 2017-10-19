package com.example.round.gaia_18.Data;

/**
 * Created by 리제 on 2017-10-06.
 */

public class GameSetting {
    private int vibration;
    private int sound;
    private int alarm;
    private int weather;
    private int hpAparm;
    private int hpAparmGauge;
    GameSetting(){}
    public GameSetting(int s1, int s2, int s3, int s4, int s5, int gauge){

        this.vibration=s1;
        this.sound=s2;
        this.alarm=s3;
        this.weather=s4;
        this.hpAparm=s5;
        this.hpAparmGauge=gauge;
    }

    public int getVibration() {
        return vibration;
    }
    public int getSound() {
        return sound;
    }
    public int getHpAparmGauge() {
        return hpAparmGauge;
    }
    public int getHpAparm() {
        return hpAparm;
    }
    public int getAlarm() {
        return alarm;
    }
    public int getWeather() {
        return weather;
    }

    public void setAlarm(int alarm) {
        this.alarm = alarm;
    }
    public void setHpAparm(int hpAparm) {
        this.hpAparm = hpAparm;
    }
    public void setHpAparmGauge(int hpAparmGauge) {
        this.hpAparmGauge = hpAparmGauge;
    }
    public void setSound(int sound) {
        this.sound = sound;
    }
    public void setVibration(int vibration) {
        this.vibration = vibration;
    }
    public void setWeather(int weather) {
        this.weather = weather;
    }


}
