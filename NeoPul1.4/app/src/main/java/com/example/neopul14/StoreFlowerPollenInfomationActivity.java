package com.example.neopul14;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by 리제 on 2017-05-10.
 */

public class StoreFlowerPollenInfomationActivity extends Fragment
{
     int flowerNumber;
     int pollenNumber;
     int pool;

    StoreFlowerPollenInfomationActivity(int i, int j){
        this.flowerNumber = i;
        this.pollenNumber = j;
        pool = 0;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_listinfomation,null);
        view.setBackground(getActivity().getDrawable(R.drawable.backg1));

        final PlantData plantdata = new PlantData();

        ImageView flowerImage = (ImageView) view.findViewById(R.id.liFlowerImage);
        //flowerNumber + pollenNumber;
        if(flowerNumber!=0){
            pool=flowerNumber+flowerNumber;
        }else {
            pool = flowerNumber;
        }
        flowerImage.setImageDrawable(getActivity().getDrawable(plantdata.getImages(pool+pollenNumber)));

        TextView name = (TextView) view.findViewById(R.id.liFlowerName);
        name.setText(plantdata.getnames(pool+pollenNumber));

        TextView information = (TextView) view.findViewById(R.id.liFlowerInfomation);
        information.setText(plantdata.getitems(pool+pollenNumber));

        Button buyFlowerButton = (Button) view.findViewById(R.id.buyButton);
        buyFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
                builder.setMessage("Buy ?");
                builder.setTitle("Eixt")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int i){
                                //startActivity(new Intent(getActivity(), MainActivity.class));
                                getActivity().finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Do You Buy?");
                alert.show();
            }
        });

        Button cancelFlowerButton = (Button) view.findViewById(R.id.cancelButton);
        cancelFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerPollenActivity(flowerNumber))
                        .commit();
            }
        });

        return view;
    }
}