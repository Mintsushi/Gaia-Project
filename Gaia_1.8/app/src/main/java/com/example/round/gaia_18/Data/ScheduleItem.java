package com.example.round.gaia_18.Data;

/**
 * Created by 리제 on 2017-10-19.
 */

public class ScheduleItem{
    private String title;
    private String memo;
    private int id;
    private int toYears;
    private int toMonths;
    private int toDays;
    private int alarms;
    private int timeHours;
    private int timeMinutes;

    public ScheduleItem(int id, int year, int month, int day, int timeHours, int timeMinutes, String title, String memo, int alarms){
        this.id = id;
        this.toYears = year;
        this.toMonths = month;
        this.toDays = day;
        this.title = title;
        this.memo = memo;
        this.alarms = alarms;
        this.timeHours = timeHours;
        this.timeMinutes = timeMinutes;
    }
    public ScheduleItem(){}
    public int getAlarms() {
        return alarms;
    }
    public String getMemo() {
        return memo;
    }
    public String getTitle() {
        return title;
    }

    public int getTimeHours() {
        return timeHours;
    }
    public int getTimeMinutes() {
        return timeMinutes;
    }

    public int getId(){ return id;}
    public int getToDays() {
        return toDays;
    }
    public int getToMonths() {
        return toMonths;
    }
    public int getToYears() {
        return toYears;
    }


    public void setId(int id) {
        this.id = id;
    }
    public void setTimeHours(int timeHours) {
        this.timeHours = timeHours;
    }
    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }
    public void setToDays(int toDays) {
        this.toDays = toDays;
    }
    public void setToMonths(int toMonths) {
        this.toMonths = toMonths;
    }
    public void setToYears(int toYears) {
        this.toYears = toYears;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    public void setAlarms(int alarms) {
        this.alarms = alarms;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
