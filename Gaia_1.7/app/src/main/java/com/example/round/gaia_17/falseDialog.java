package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 리제 on 2017-09-09.
 */

public class falseDialog extends Dialog {

    TextView massageBoxA1;
    TextView massageBoxA2;
    public falseDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.false_dialog);

        massageBoxA1 = (TextView)findViewById(R.id.massageA1);
        massageBoxA2 = (TextView)findViewById(R.id.massageA2);

    }

    public void setMassageBoxA1(String str) {
        massageBoxA1.setText(str);
    }

    public void setMassageBoxA2(String str) {
        massageBoxA2.setText(str);
    }

}
