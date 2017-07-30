package com.example.round.neoful16;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

/**
 * Created by Round on 2017-07-16.
 */

public class StoreFlowerInformationActivity extends Fragment {

    private StoreFlowerActivity.FlowerInfo flower;

    private ImageLoader mImageLoader;
    private RequestQueue requestQueue;

    public void setFlower(StoreFlowerActivity.FlowerInfo flower){this.flower = flower;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_flower_inform,container,false);


        requestQueue= Volley.newRequestQueue(getContext());
        requestQueue.start();
        mImageLoader = new ImageLoader(requestQueue,new LruBitmapCache(LruBitmapCache.getCacheSize(getContext())));

        TextView name = (TextView)view.findViewById(R.id.flower_Name);
        NetworkImageView image = (NetworkImageView)view.findViewById(R.id.flower_Image);
        TextView maxHP = (TextView)view.findViewById(R.id.flower_maxHP);
        TextView maxLevel = (TextView)view.findViewById(R.id.flower_maxLevel);
        TextView waterTime = (TextView)view.findViewById(R.id.flower_waterTime);
        TextView fruit = (TextView)view.findViewById(R.id.flower_fruit);
        TextView weather = (TextView)view.findViewById(R.id.flower_weather);
        TextView explain = (TextView)view.findViewById(R.id.flower_explain);
        TextView inform = (TextView)view.findViewById(R.id.flower_inform);
        TextView cost = (TextView)view.findViewById(R.id.flower_Cost);

        maxHP.setText(Integer.toString(flower.getMaxHP()));
        maxLevel.setText(Integer.toString(flower.getMaxLevel()));
        waterTime.setText(Integer.toString(flower.getWaterTime()));
        fruit.setText(Integer.toString(flower.getFruit()));
        weather.setText(flower.getWeather());

        name.setText(flower.getName());
        image.setImageUrl("http://202.31.200.143/"+flower.getPath(),mImageLoader);

        explain.setText(flower.getInfo());
        inform.setText(flower.getExplain());
        inform.setMovementMethod(new ScrollingMovementMethod());

        cost.setText(Integer.toString(flower.getCost()));

        Button buy = (Button)view.findViewById(R.id.flower_buy);
        Button cancel = (Button)view.findViewById(R.id.flower_cancel);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreFlowerPollenActivity flowerActivity = new StoreFlowerPollenActivity();
                flowerActivity.setFlower(flower);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL,flowerActivity)
                        .commit();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
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
}