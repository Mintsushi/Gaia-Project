package com.example.round.gaia_18.Callendar.cal;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.round.gaia_18.Callendar.MConfig;
import com.example.round.gaia_18.R;

import java.util.Calendar;

import static com.example.round.gaia_18.Callendar.cal.OneMonthView.monthschLists;

/**
 * View to display a day
 * @author Brownsoo
 *
 */
public class OneDayView extends RelativeLayout {
 
    private static final String TAG = MConfig.TAG;
    private static final String NAME = "OneDayView";
    private final String CLASS = NAME + "@" + Integer.toHexString(hashCode());

    private RelativeLayout day_linear;

    private ImageView eventImage;
    /** number text field */
    private TextView dayTv;
    /** message text field*/
    private TextView msgTv;
    /** Weather icon */
    private ImageView weatherIv;
    /** Value object for a day info */
    private com.example.round.gaia_18.Callendar.cal.OneDayData one;

    /**
     * OneDayView constructor
     * @param context context
     */
    public OneDayView(Context context) {
        super(context);
        init(context);
 
    }

    /**
     * OneDayView constructor for xml
     * @param context context
     * @param attrs AttributeSet
     */
    public OneDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
 
    private void init(Context context)
    {

        View v = View.inflate(context, R.layout.cal_oneday, this);
        day_linear = (RelativeLayout)v.findViewById(R.id.day_linear);
        eventImage = (ImageView)v.findViewById(R.id.onday_event);
        eventImage.setVisibility(INVISIBLE);
        dayTv = (TextView) v.findViewById(R.id.onday_dayTv);
        weatherIv = (ImageView) v.findViewById(R.id.onday_weatherIv);
        weatherIv.setVisibility(INVISIBLE);
        msgTv = (TextView) v.findViewById(R.id.onday_msgTv);
        one = new com.example.round.gaia_18.Callendar.cal.OneDayData();
    }
    
    /**
     * Set the day to display
     * @param year 4 digits of year
     * @param month Calendar.JANUARY ~ Calendar.DECEMBER
     * @param day day of month
     */
    public void setDay(int year, int month, int day) {
        this.one.cal.set(year, month, day);
    }

    /**
     * Set the day to display
     * @param cal Calendar instance
     */
    public void setDay(Calendar cal) {
        this.one.setDay((Calendar) cal.clone());
    }

    /**
     * Set the day to display
     * @param one OneDayData instance
     */
    public void setDay(com.example.round.gaia_18.Callendar.cal.OneDayData one) {
        this.one = one;
        Log.i("todays oneDayView: ",""+one.getDay().getTime().getMonth() + " / "+one.getDay().getTime().getDate());
        for(int i =0; i < monthschLists.size(); i++){
            if(monthschLists.get(i).getToDays() == one.getDay().getTime().getDate()){
                setEventDraw(1);
                Log.i("todays Event Day : ",""+one.getDay().getTime().getMonth() + " / "+one.getDay().getTime().getDate());
            }
        }
    }
    
    /**
     * Get the day to display
     * @return OneDayData instance
     */
    public com.example.round.gaia_18.Callendar.cal.OneDayData getDay() {
        return one;
    }

    /**
     * Set the message to display
     * @param msg message
     */
    public void setMessage(String msg){
        one.setMessage(msg);
    }

    /**
     * Get the message
     * @return message
     */
    public CharSequence getMessage(){
        return  one.getMessage();
    }

    /**
     * Same function with {@link Calendar#get(int)}<br>
     * <br>
     * Returns the value of the given field after computing the field values by
     * calling {@code complete()} first.
     * 
     * @param field Calendar.YEAR or Calendar.MONTH or Calendar.DAY_OF_MONTH
     *
     * @throws IllegalArgumentException
     *                if the fields are not set, the time is not set, and the
     *                time cannot be computed from the current field values.
     * @throws ArrayIndexOutOfBoundsException
     *                if the field is not inside the range of possible fields.
     *                The range is starting at 0 up to {@code FIELD_COUNT}.
     */
    public int get(int field) throws IllegalArgumentException, ArrayIndexOutOfBoundsException {
        return one.get(field);
    }

    /**
     * Set weather
     * @param weather Weather instance
     */
    public void setWeather(com.example.round.gaia_18.Callendar.cal.WeatherInfo.Weather weather) {
        this.one.setWeather(weather);
    }
    
    /**
     * Updates UI upon the value object.
     */
    public void refresh() {

        
        dayTv.setText(String.valueOf(one.get(Calendar.DAY_OF_MONTH)));

        if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dayTv.setTextColor(Color.RED);
        }
        else if(one.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayTv.setTextColor(Color.BLUE);
        }
        else {
            dayTv.setTextColor(Color.BLACK);
        }

        msgTv.setText((one.getMessage()==null)?"":one.getMessage());
        switch(one.weather) {
        case CLOUDY :
        case SUN_CLOUDY:
            weatherIv.setImageResource(R.drawable.cal_cloudy);
            break;
        case RAINY:
            weatherIv.setImageResource(R.drawable.cal_rainy);
            break;
        case SNOW :
            weatherIv.setImageResource(R.drawable.cal_snowy);
            break;
        case SUNSHINE :
            weatherIv.setImageResource(R.drawable.cal_sunny);
            break;
        }
        
    }

    public RelativeLayout getDay_linear() {
        return day_linear;
    }

    public void setViewDraw(int type){
        if(type==1){
            day_linear.setBackgroundResource(R.drawable.cal_lineframe_null);
        }
        else if(type == 2){
            day_linear.setBackgroundResource(R.drawable.cal_lineframe_direct);
        }
        else{
            day_linear.setBackgroundResource(R.drawable.cal_lineframe);
        }
    }

    public void setEventDraw(int type){
        if(type==1){
            eventImage.setVisibility(VISIBLE);
        }else{
            eventImage.setVisibility(INVISIBLE);
        }
    }

    public void setWeatherDraw(int type){
        if(type==0){
            weatherIv.setVisibility(INVISIBLE);
        }
        else if(type==1){
            eventImage.setVisibility(VISIBLE);
        }
    }
}