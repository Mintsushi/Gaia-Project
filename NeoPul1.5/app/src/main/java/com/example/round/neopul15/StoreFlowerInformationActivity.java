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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Round on 2017-06-13.
 */

public class StoreFlowerInformationActivity extends Fragment{

    private StoreFlowerActivity.FlowerInfo flower;

    public void setFlower(StoreFlowerActivity.FlowerInfo flower){this.flower = flower;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_flower_inform,container,false);

        TextView name = (TextView)view.findViewById(R.id.flower_Name);
        ImageView image = (ImageView)view.findViewById(R.id.flower_Image);
        TextView inform = (TextView)view.findViewById(R.id.flower_inform);
        TextView cost = (TextView)view.findViewById(R.id.flower_Cost);

        name.setText(flower.getName());
        int id = getResources().getIdentifier(flower.getPath(),"drawable",getActivity().getPackageName());
        image.setImageResource(id);
        inform.setText(flower.getInfo());
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
