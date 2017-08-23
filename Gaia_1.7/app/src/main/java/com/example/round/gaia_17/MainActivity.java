package com.example.round.gaia_17;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Round on 2017-08-15.
 */

public class MainActivity extends AppCompatActivity{

    private TextView seed, fruit;
    private Button goal, menu, move;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
