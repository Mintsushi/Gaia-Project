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
 * 김태우 패시브 스킬 정보창 구현.
 *  드라이꽃의 info버튼을 눌렀을경우 해당꽃의 정보를 출력해줍니다.
 */

public class menuPassiveSkillInfomationDialog extends Dialog {
    ImageButton back;
    ImageView flower;
    TextView name;
    TextView inform;
    public menuPassiveSkillInfomationDialog(Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_passive_skill_infomation_dialog);

        back = (ImageButton) findViewById(R.id.Diaback);
        flower = (ImageView) findViewById(R.id.DiaflowerImage);
        name = (TextView) findViewById(R.id.Diaflowername);
        inform = (TextView) findViewById(R.id.Diaflowerexplain);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }

        });


        inform.setMovementMethod(new ScrollingMovementMethod());
    }

    public void setname(String str) {
        name.setText(str);
    }

    public void setexplain(String str) {
        inform.setText(str);
    }

    public void setimage(String str) {
        Log.i("path :: ", ":: " + str + "::" + getContext().getPackageName());

        int path = getContext().getResources().getIdentifier(str, "drawable", getContext().getPackageName());
        Log.i("path :: ", ":: " + path);
        //flower.setImageResource(path);
    }

}
