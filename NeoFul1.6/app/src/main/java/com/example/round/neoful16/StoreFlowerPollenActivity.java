package com.example.round.neoful16;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Round on 2017-07-17.
 */

public class StoreFlowerPollenActivity extends Fragment {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private static StoreFlowerActivity.FlowerInfo flower;
    private ArrayList<PollenInfo> mArray = new ArrayList<>();
    NetworkImageView flowerImage,pollenImage;
    TextView name, cost;

    private ImageLoader mImageLoader;
    private RequestQueue requestQueue;

    private int count = 0;

    public void setFlower(StoreFlowerActivity.FlowerInfo flower){this.flower = flower;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_store_flower_pollen,null);

        pref = getContext().getSharedPreferences("Login",getContext().MODE_PRIVATE);
        editor = pref.edit();

        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.start();
        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getContext())));

        requestPollen();

        pollenImage = (NetworkImageView)view.findViewById(R.id.poPollenImage);
        name = (TextView)view.findViewById(R.id.pollenName);
        cost = (TextView)view.findViewById(R.id.pollenCost);

        ImageButton right = (ImageButton)view.findViewById(R.id.poRButton);
        ImageButton left = (ImageButton)view.findViewById(R.id.poLBoutton);

        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if(count > mArray.size()-1)
                    count = mArray.size()-1;
                setImage();
                // 각각 화분명 가격 정보 변화 필요
            }
        });

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count--;
                if(count<0)
                    count=0;
                setImage();
                // 각각 화분명 가격 정보 변화 필요
            }
        });

        Button buyPollenButton = (Button)view.findViewById(R.id.buyPollenButton);
        Button cancelPollenButton = (Button)view.findViewById(R.id.cancelpollenButton);

        buyPollenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StorePlantSelectActivity flowerActivity = new StorePlantSelectActivity();
                flowerActivity.setPlant(flower,mArray.get(count));
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL,flowerActivity)
                        .commit();
            }
        });

        cancelPollenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerActivity())
                        .commit();
            }
        });
        return view;
    }


    //추가코드
    boolean CostCalculate(int type){
        int TotalCost =  flower.getCost() + mArray.get(count).getCost();
        int CostType = type;
        int money;

        // Seed구매
        if(CostType == 0){
            money = pref.getInt("PresentSeed",0);

            if(TotalCost > money){
                editor.putInt("PresentSeed",0);
                editor.commit();
                Log.e("Cost","Be short of Seed ");
                return false;
            }else{
                editor.putInt("PresentSeed",TotalCost);
                editor.commit();
                Log.i("Cost2 : ",String.valueOf(pref.getInt("PresentSeed",0)));
                return true;
            }
        }
        // fruit구매
        else{
            money = pref.getInt("PresentFruit",0);

            if(TotalCost > money){
                Log.e("Cost","Be short of Fruit ");
                return false;
            }else{
                money = money - TotalCost;
                editor.putInt("PresentFruit",TotalCost);
                editor.commit();
                return true;
            }
        }
    }

    private void setImage(){

        String pollenPath = mArray.get(count).getPath();
        pollenImage.setImageUrl("http://202.31.200.143/"+pollenPath,mImageLoader);
        name.setText(mArray.get(count).getName());
        cost.setText(Integer.toString(mArray.get(count).getCost()));
    }

    public class PollenInfo{

        private int id;
        private String name;
        private String path;
        private int cost;

        public PollenInfo(int id,String name, String path,int cost){
            this.id = id;
            this.name = name;
            this.path = path;
            this.cost = cost;
        }

        public String getName(){return this.name;}
        public String getPath(){return this.path;}
        public int getCost(){return this.cost;}
        public int getId(){return this.id;}
    }

    private void requestPollen(){

        String url="http://202.31.200.143/pollen";

        JsonArrayRequest request = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response){
                        Log.i("StoreItemActivity",response.toString());

                        for(int i=0;i<response.length();i++){

                            try{
                                JSONObject object = response.getJSONObject(i);

                                int id = object.getInt("potNo");
                                String name = object.getString("potName");
                                String path = object.getString("potImagePath");
                                int cost = object.getInt("seedPrice");
                                mArray.add(new PollenInfo(id,name,path,cost));

                            }catch (JSONException e){
                                Log.i("StoreItemActivity",e.toString());
                            }
                        }

                        setImage();
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("StoreItemActivity",error.toString());
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
}
