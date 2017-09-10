package com.example.round.neoful16;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by Round on 2017-07-16.
 */

public class PlantmanagementEntryActivity extends Dialog {
    public PlantmanagementEntryActivity(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_plant_entry);

        ImageButton yes = (ImageButton)findViewById(R.id.yes);
        ImageButton no = (ImageButton)findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext().getApplicationContext(), "yes", Toast.LENGTH_SHORT).show();
                gettype(1);
                dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext().getApplicationContext(), "no", Toast.LENGTH_SHORT).show();
                gettype(0);
                dismiss();
            }
        });

    }


    int gettype(int a){
        if(a==1){
            return 1;
        }
        else{
            return 0;
        }
    }

}