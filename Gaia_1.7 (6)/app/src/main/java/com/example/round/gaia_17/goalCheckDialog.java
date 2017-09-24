package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 리제 on 2017-09-22.
 */

public class goalCheckDialog extends Dialog {

    TextView messageBoxA1;
    TextView messageBoxA2;
    TextView messageBoxA3;
    ImageView ImageBoxA1;
    ImageButton yes;

    public goalCheckDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_goal_reaward_check);

        messageBoxA1 = (TextView)findViewById(R.id.messageBox1);
        messageBoxA2 = (TextView)findViewById(R.id.messageBox2);
        messageBoxA3 = (TextView)findViewById(R.id.messageBox3);
        ImageBoxA1 = (ImageView)findViewById(R.id.ImageBox1);
        yes = (ImageButton)findViewById(R.id.yes);
        yes.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                dismiss();
            }
        });
    }

    public void setmessageBoxA1(String str) {
        messageBoxA1.setText(str);
    }

    public void setmessageBoxA2(String str) {
        messageBoxA2.setText(str);
    }
    public void setmessageBoxA3(String str) {
        messageBoxA3.setText(str);
    }
    public void setImageBoxA1(String str) {
        ImageBoxA1.setImageResource(R.drawable.image);
        //ImageBoxA1.setImageResource(R.drawable.str);
    }

}
