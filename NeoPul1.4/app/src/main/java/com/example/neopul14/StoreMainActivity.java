package com.example.neopul14;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by 리제 on 2017-05-09.
 */

// 상점 코드

public class StoreMainActivity extends AppCompatActivity
{
    StoreMainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);

        // 기본적으로 아이템 구매리스트 (StoreItemActivity)이 플레그먼트형태로 상점화면에 보여줌
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.FL, new StoreItemActivity())
                .commit();

        // 뒤로가기버튼
        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        });

        // 클릭시 아이템 구매리스트 (StoreItemActivity)이 플레그먼트형태로 상점화면에 보여줌
        Button itemButton = (Button)findViewById(R.id.itemStoreButton);
        itemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreItemActivity())
                        .commit();
            }
        });

        // 기본적으로 꽃 구매리스트 (StoreFlowerActivity)이 플레그먼트형태로 상점화면에 보여줌
        Button flowerButton = (Button)findViewById(R.id.flowerStoreButton);
        flowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreFlowerActivity())
                        .commit();
            }
        });


    }



}


