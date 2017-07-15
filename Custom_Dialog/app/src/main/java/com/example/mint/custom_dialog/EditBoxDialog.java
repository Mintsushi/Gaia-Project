package com.example.mint.custom_dialog;

/**
 * Created by Mint on 2017-07-13.
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class EditBoxDialog extends Dialog {

    public EditBoxDialog(Context context) {
        super(context);
    }

    private String mode="";
    private String text="";
    TextView explain;
    TextView name;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_edit_box);

        explain = (TextView)findViewById(R.id.explain);
        name = (TextView)findViewById(R.id.name);

        explain.setMovementMethod(new ScrollingMovementMethod());

    }

}

