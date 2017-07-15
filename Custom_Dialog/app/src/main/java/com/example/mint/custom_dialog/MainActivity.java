package com.example.mint.custom_dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;



public class MainActivity extends AppCompatActivity {

    TextView helloworld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        helloworld = (TextView)findViewById(R.id.d);

        helloworld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditBoxDialog dialog = new EditBoxDialog(MainActivity.this);
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        dialog.setMode("해바라기2");
                        dialog.setText("설명부분2222222");
                    }
                });
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dia) {
                        helloworld.setText(dialog.getMode());
                    }
                });

                dialog.show();
            }
        });
    }



}

