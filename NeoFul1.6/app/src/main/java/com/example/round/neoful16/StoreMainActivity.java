package com.example.round.neoful16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Round on 2017-07-16.
 */

public class StoreMainActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreItemActivity()).commit();

        Button itemButton = (Button)findViewById(R.id.itemStoreButton);
        Button flowerButton = (Button)findViewById(R.id.flowerStoreButton);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreItemActivity()).commit();
            }
        });

        flowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreFlowerActivity()).commit();
            }
        });
    }
}
