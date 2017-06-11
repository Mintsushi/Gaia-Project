package com.example.neopul14;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 리제 on 2017-05-10.
 */

// 구매리스트창에서 아이템을 클릭할 경우 구매확인을 위해 이 플래그먼트를 상점의 플레그먼트에 출력

public class StoreFlowerInfomationActivity extends Fragment
{
    private int flowerNumber;
    StoreFlowerInfomationActivity(int i){
        this.flowerNumber = i;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final FlowerData flowerdata = new FlowerData();
        View view = inflater.inflate(R.layout.view_listinfomation,null);
        view.setBackground(getActivity().getDrawable(R.drawable.backg1));

        ImageView flowerImage = (ImageView) view.findViewById(R.id.liFlowerImage);
        //flowerImage.setImageDrawable(getActivity().getDrawable(R.drawable.plant));
        flowerImage.setImageDrawable(getActivity().getDrawable(flowerdata.getImages(flowerNumber)));
        TextView name = (TextView) view.findViewById(R.id.liFlowerName);
        name.setText(flowerdata.getnames(flowerNumber));

        TextView information = (TextView) view.findViewById(R.id.liFlowerInfomation);
        information.setText(flowerdata.getitems(flowerNumber));

        Button buyFlowerButton = (Button) view.findViewById(R.id.buyButton);
        buyFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerPollenActivity(flowerNumber))
                        .commit();
            }
        });

        Button cancelFlowerButton = (Button) view.findViewById(R.id.cancelButton);
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