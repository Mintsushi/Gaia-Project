package com.example.round.weather2;


import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ForecastGrib {

    private Header header;
    private Body body;
    private JSONObject response;

    public ArrayList<ForecastItem> items;

    ForecastGrib(JSONObject object){
        try {
            this.response = object.getJSONObject("response");
            header = new Header(response.getJSONObject("header"));
            body = new Body(response.getJSONObject("body"));
            Log.i("ForecastGrib","Success Create ForecastGirb/"+header.toString()+"/"+body.toString());
        }catch(JSONException e){
            Log.i("ForecastGrib","JSONException Error : "+e.toString());
        }
    }
    public String getResultCode() {
        return (response != null && header != null) ? header.resultCode : "-1";
    }
    public String getResultMessage() {
        return (response != null && header != null) ? header.resultMsg : null;
    }
    public ArrayList<ForecastItem> getWeatherItems() {
        return (response != null && body != null && body.itemRoot != null) ? body.itemRoot.items : null;
    }
    public int getTotalCount() {
        return (response != null && body != null) ? body.totalCount : null;
    }
    public int getRowsPerPage() {
        return (response != null && body != null) ? body.rowsPerPage : null;
    }
    public int getNumOfPage() {
        return (response != null && body != null) ? body.pageNo : 0;
    }
    public Body getBody() {
        return (response != null) ? body : null;
    }

    public class Header {
        public String resultCode;
        public String resultMsg;

        public Header(JSONObject header){
            try {
                this.resultCode = header.getString("resultCode");
                this.resultMsg = header.getString("resultMsg");
                Log.i("Header","Success Header : "+"/"+resultCode+"/"+resultMsg);
            }catch (JSONException e){
                Log.i("Header","JSONException : "+e.toString());
            }
        }

    }

    public class Body {
        public int pageNo;
        public int rowsPerPage;
        public int totalCount;
        public Item itemRoot;

        public Body(JSONObject body){
            try {
                pageNo = body.getInt("pageNo");
                rowsPerPage = body.getInt("numOfRows");
                totalCount = body.getInt("totalCount");
                itemRoot = new Item(body.getJSONObject("items"));
                Log.i("Body","Success Body : "+"/"+pageNo+"/"+rowsPerPage+"/"+totalCount+"/"+itemRoot.toString());
            }catch (JSONException e){
                Log.i("Body","JSONException : "+e.toString());
            }
        }
    }

    public class Item {
        public ArrayList<ForecastItem> items = new ArrayList<>();

        public Item(JSONObject items){
            try{
                JSONArray item = items.getJSONArray("item");
                for(int i=0;i<item.length();i++){
                    try {
                        ForecastItem forecastItem = new ForecastItem(item.getJSONObject(i));
                        this.items.add(forecastItem);
                        Log.i("Item","Success Body : "+forecastItem.toString());
                    }catch (JSONException e){
                        Log.i("Item","JSONException : "+e.toString());
                    }
                }
            }catch (JSONException e){
                Log.i("Item","JSONException : "+e.toString());
            }
        }
    }
}
