package com.example.neopul14;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

/**
 * Created by 리제 on 2017-05-09.
 */

public class StoreFlowerPollenActivity extends Fragment
{
    int countA = 0;
    int flowerNumber;
    int pollenNumber = 0;
    StoreFlowerPollenActivity(int i){
        this.flowerNumber = i;
    }

    public int imgaecounts(int max, int num, int type){
        // tpye == 0 / Right  , tpye == 0 / Left
        if(type == 0){
            num ++;
            if(num >= max){
                return 0;
            }
            else{
                return num;
            }
        }
        else{
            num --;
            if(num < 0){
                return (max-1);
            }
            else{
                return num;
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_store_flower_pollen,null);
        final PollenData pollendata = new PollenData();
        final FlowerData flowerdata = new FlowerData();
        final ImageView flowerImage = (ImageView) view.findViewById(R.id.poFlowerImage);
        final ImageView pollenImage = (ImageView) view.findViewById(R.id.poPollenImage);

        flowerImage.setImageResource(flowerdata.getImages(flowerNumber));

        pollenImage.setImageResource(pollendata.getImages(countA));
        ImageButton RButton = (ImageButton) view.findViewById(R.id.poRButton);
        RButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                countA = imgaecounts(2,countA,0);
                pollenImage.setImageResource(pollendata.getImages(countA));
                pollenNumber = countA;
            }
        });

        ImageButton LButton = (ImageButton) view.findViewById(R.id.poLBoutton);
        LButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                countA = imgaecounts(2,countA,1);
                pollenImage.setImageResource(pollendata.getImages(countA));
                pollenNumber = countA;
            }
        });



        Button buyPollenButton = (Button) view.findViewById(R.id.buyPollenButton);
        buyPollenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerPollenInfomationActivity(flowerNumber,pollenNumber))
                        .commit();
            }
        });

        Button cancelFlowerButton = (Button) view.findViewById(R.id.cancelpollenButton);
        cancelFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerActivity())
                        .commit();
            }
        });

        return view;
    }
}