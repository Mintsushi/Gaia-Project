package com.example.round.weather2;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ForecastItem {
    public static final String TMX = "TMX";     // 일최고기온
    public static final String TMN = "TMN";     // 일최저기온
    public static final String REH = "REH";     // 습도
    public static final String SKY = "SKY";     // 하늘상태
    public static final String POP = "POP";     // 강수확률
    public static final String PTY = "PTY";     // 강수형태
    public static final String RN1 = "RN1";     // 1시간강수
    public static final String T3H = "T3H";     // 3시간기온
    public static final String T1H = "T1H";     // 1시간기온 - 실황에포함됨.
    public static final String LGT="LGT";
    public static final String WSD = "WSD";

    public int baseDate;
    public int baseTime;
    public String fcstDate;
    public String fcstTime;
    public String category;
    public String fcstValue;
    public String obsrValue;
    public int nx;
    public int ny;

    public ForecastItem(JSONObject item){
        try {
            baseDate = item.getInt("baseDate");
            baseTime = item.getInt("baseTime");
            category = item.getString("category");
            nx = item.getInt("nx");
            ny = item.getInt("ny");
            obsrValue = Integer.toString(item.getInt("obsrValue"));
        }catch (JSONException e){

        }
    }
}
