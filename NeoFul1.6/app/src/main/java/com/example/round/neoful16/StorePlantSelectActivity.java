package com.example.round.neoful16;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Round on 2017-07-17.
 */

public class StorePlantSelectActivity extends Fragment{

    private StoreFlowerActivity.FlowerInfo flower;
    private StoreFlowerPollenActivity.PollenInfo pollen;
    private ImageLoader mImageLoader;
    private RequestQueue requestQueue;
    private View view;

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public void setPlant(StoreFlowerActivity.FlowerInfo flower, StoreFlowerPollenActivity.PollenInfo pollen){
        this.flower = flower;
        this.pollen = pollen;

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        view = inflater.inflate(R.layout.activity_plantselect,container,false);

        pref = getContext().getSharedPreferences("Login",getContext().MODE_PRIVATE);
        editor = pref.edit();

        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.start();

        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getContext())));

        NetworkImageView plant = (NetworkImageView)view.findViewById(R.id.plant_image);
        String plantPath = flower.getOrigianlPath()+flower.getId()+pollen.getId()+".png";
        Log.i("StorePlant",plantPath);
        plant.setImageUrl("http://202.31.200.143/"+plantPath,mImageLoader);

        Button buyPollenButton = (Button)view.findViewById(R.id.plant_buy);
        Button cancelPollenButton = (Button)view.findViewById(R.id.plant_cancel);

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
                        .replace(R.id.FL, new StoreFlowerPollenActivity())
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


                        if (response.equals("true")) {
                            Toast.makeText(getContext(), "Success Buy Flower", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getContext().getApplicationContext(), StartActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                        else {Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();                            }

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

                params.put("flower",Integer.toString(flower.getId()));
                params.put("pollen",Integer.toString(pollen.getId()));
                //추가코드
                params.put("seed",String.valueOf(flower.getCost() + pollen.getCost()));
                //원래 코드
                //params.put("flowerImagePath",flower.getOrigianlPath()+"1.png");
                params.put("flowerImagePath",flower.getOrigianlPath());
                params.put("potImagePath",pollen.getPath());
                params.put("fruit",Integer.toString(pref.getInt("PresentFruit",0)));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

}
