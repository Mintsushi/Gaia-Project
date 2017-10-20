package com.example.round.gaia_18.Callendar.cal;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.round.gaia_18.Callendar.MConfig;
import com.example.round.gaia_18.Data.ScheduleItem;

import java.util.ArrayList;
import java.util.Calendar;

import static com.example.round.gaia_18.Callendar.CallendarMainActivity.schLists;

/**
 * View to display a month
 */
public class OneMonthView extends LinearLayout implements View.OnClickListener {

    public static ArrayList<ScheduleItem> monthschLists = new ArrayList<>();
    private static final String TAG = MConfig.TAG;
    private static final String NAME = "OneMonthView";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

    public interface OnClickDayListener {
        void onClick(com.example.round.gaia_18.Callendar.cal.OneDayView odv);
    }

    private int mYear;
    private int mMonth;
    private ArrayList<LinearLayout> weeks = null;
    private ArrayList<com.example.round.gaia_18.Callendar.cal.OneDayView> dayViews = null;
    private OnClickDayListener onClickDayListener;
    private final OnClickDayListener dummyClickDayListener = new OnClickDayListener() {
        @Override
        public void onClick(com.example.round.gaia_18.Callendar.cal.OneDayView odv) {
                   }
    };

    public void setOnClickDayListener(OnClickDayListener onClickDayListener) {
        if (onClickDayListener != null) {
            this.onClickDayListener = onClickDayListener;
        }
        else {
            this.onClickDayListener = dummyClickDayListener;
        }
    }


    public OneMonthView(Context context) {
        this(context, null);
    }

    public OneMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneMonthView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOrientation(LinearLayout.VERTICAL);
        onClickDayListener = dummyClickDayListener;

        //Prepare many day-views enough to prevent recreation.
        if(weeks == null) {

            weeks = new ArrayList<>(6); //Max 6 weeks in a month
            dayViews = new ArrayList<>(42); // 7 days * 6 weeks = 42 days

            LinearLayout ll = null;
            for(int i=0; i<42; i++) {

                if(i % 7 == 0) {
                    //Create new week layout
                    ll = new LinearLayout(context);
                    LinearLayout.LayoutParams params
                            = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
                    params.weight = 1;
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    ll.setLayoutParams(params);
                    ll.setWeightSum(7);

                    weeks.add(ll);
                }

                LinearLayout.LayoutParams params
                        = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
                params.weight = 1;

                com.example.round.gaia_18.Callendar.cal.OneDayView ov = new OneDayView(context);
                ov.setLayoutParams(params);
                ov.setOnClickListener(this);

                ll.addView(ov);
                dayViews.add(ov);
            }
        }
        
        //for Preview of Graphic editor
        if(isInEditMode()) {
            Calendar cal = Calendar.getInstance();
            make(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
        }
    }

    /**
     * Get current year
     * @return 4 digits number of year
     */
    public int getYear() {
        return mYear;
    }

    /**
     * Get current month
     * @return 0~11 (Calendar.JANUARY ~ Calendar.DECEMBER)
     */
    public int getMonth() {
        return mMonth;
    }


    /**
     * Any layout manager that doesn't scroll will want this.
     */
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }


    /**
     * Make a Month view
     * @param year year of this month view (4 digits number)
     * @param month month of this month view (0~11)
     */
    public void make(int year, int month)
    {
        if(mYear == year && mMonth == month) {
            return;
        }
        
        long makeTime = System.currentTimeMillis();
        
        this.mYear = year;
        this.mMonth = month;

        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//Sunday is first day of week in this sample
        
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);//Get day of the week in first day of this month
        int maxOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//Get max day number of this month
        ArrayList<com.example.round.gaia_18.Callendar.cal.OneDayData> oneDayDataList = new ArrayList<>();
        
        cal.add(Calendar.DAY_OF_MONTH, Calendar.SUNDAY - dayOfWeek);//Move to first day of first week

        /* add previous month */
        int seekDay;
        for(;;) {
            seekDay = cal.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek == seekDay) break;

            com.example.round.gaia_18.Callendar.cal.OneDayData one = new OneDayData();
            one.setDay(cal);
            oneDayDataList.add(one);
            //하루 증가
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

      /* add this month */
        for(int i=0; i < maxOfMonth; i++) {
            com.example.round.gaia_18.Callendar.cal.OneDayData one = new OneDayData();
            one.setDay(cal);
            oneDayDataList.add(one);
            //add one day
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        /* add next month */
        for(;;) {
            if(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                com.example.round.gaia_18.Callendar.cal.OneDayData one = new OneDayData();
                one.setDay(cal);
                oneDayDataList.add(one);
            } 
            else {
                break;
            }
            //add one day
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        
        if(oneDayDataList.size() == 0) return;

        //Remove all day-views
        this.removeAllViews();

        monthschLists.clear();
        for( int i = 0; i<schLists.size(); i++){

            Log.i("todays equle : ",""+schLists.get(i).getToMonths()+ " / " + mMonth);
            if(schLists.get(i).getToYears()==mYear && schLists.get(i).getToMonths()==(mMonth+1) ){
                monthschLists.add(schLists.get(i));
            }

        }
        int count = 0;
        for(com.example.round.gaia_18.Callendar.cal.OneDayData one : oneDayDataList) {
            
            if(count % 7 == 0) {
                addView(weeks.get(count / 7));
            }
            com.example.round.gaia_18.Callendar.cal.OneDayView ov = dayViews.get(count);
            ov.setDay(one);
            ov.setMessage("");
            ov.refresh();
            count++;
        }

        //Set the weight-sum of LinearLayout to week counts
        this.setWeightSum(getChildCount());



    }


    protected String doubleString(int value) {

        String temp;
 
        if(value < 10){
            temp = "0"+ String.valueOf(value);
             
        }else {
            temp = String.valueOf(value);
        }
        return temp;
    }
 
    @Override
    public void onClick(View v) {

        com.example.round.gaia_18.Callendar.cal.OneDayView odv = (OneDayView) v;
        this.onClickDayListener.onClick(odv);

    }

}