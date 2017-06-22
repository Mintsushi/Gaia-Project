package com.example.round.neopul15;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Round on 2017-06-13.
 */

public class StoreFlowerPollenActivity extends Fragment{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private StoreFlowerActivity.FlowerInfo flower;
    private ArrayList<PollenInfo> mArray = new ArrayList<>();
    ImageView flowerImage,pollenImage;
    TextView name, cost;

    private int count = 0;

    public void setFlower(StoreFlowerActivity.FlowerInfo flower){this.flower = flower;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.activity_store_flower_pollen,null);

        pref = getContext().getSharedPreferences("Login",getContext().MODE_PRIVATE);
        editor = pref.edit();

        requestPollen();

        flowerImage = (ImageView)view.findViewById(R.id.poFlowerImage);
        pollenImage = (ImageView)view.findViewById(R.id.poPollenImage);
        name = (TextView)view.findViewById(R.id.pollenName);
        cost = (TextView)view.findViewById(R.id.pollenCost);

        int id = getResources().getIdentifier(flower.getPath(),"drawable",getActivity().getPackageName());
        flowerImage.setImageResource(id);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You Really Buy this Flower?")
                        .setTitle("Buy Item")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                buyFlower();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.FL,new StoreItemActivity())
                                        .commit();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
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

    private void buyFlower(){

        String url="http://202.31.200.143/buy/flower";

        StringRequest request = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.i("MainActivity","Response : "+response);

                        if(response.equals("true")){
                            Toast.makeText(getContext(),"Success Buy Flower",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext().getApplicationContext(),StartActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else{
                            Toast.makeText(getContext(),"Failed Buy Flower",Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("MainActivity","onErrorResponse : "+error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("email",pref.getString("id",""));

                // 토탈코스트 만큼 빼면될듯
                int TotalCost = flower.getCost() + mArray.get(count).getCost();

                params.put("flower",Integer.toString(flower.getId()));
                params.put("pollen",Integer.toString(mArray.get(count).getId()));

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void setImage(){
        int pollenId = getResources().getIdentifier(mArray.get(count).getPath(),"drawable",getActivity().getPackageName());
        pollenImage.setImageResource(pollenId);
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

                                int id = object.getInt("pollenNo");
                                String name = object.getString("pollenName");
                                String path = object.getString("pollenPath");
                                int cost = object.getInt("pollenCost");
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
