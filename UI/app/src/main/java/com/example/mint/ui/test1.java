package com.example.mint.ui;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Mint on 2017-07-06.
 */

public class test1 extends AppCompatActivity {
    Button ONOFF;       //위젯 on/off 버튼
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        ONOFF = (Button) findViewById(R.id.ONOFF);



    }

}
