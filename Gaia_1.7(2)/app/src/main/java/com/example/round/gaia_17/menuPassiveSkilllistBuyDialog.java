package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 김태우 패시브 스킬 드라이창 구매 구현.
 *  드라이창을 fruit으로 구매하게 해둡니다.
 */


public class menuPassiveSkilllistBuyDialog extends Dialog{
    public menuPassiveSkilllistBuyDialog(Context context){ super((context));}

    TextView textView;
    int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_passive_skill_lsit_buy_dialog);
        textView = (TextView)findViewById(R.id.butCostText);
        ImageButton yes = (ImageButton)findViewById(R.id.yes);
        ImageButton no = (ImageButton)findViewById(R.id.no);
        Log.i("dd", ""+num);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = gettype(0);
                dismiss();
            }
        });

    }


    int gettype(int a){
        return a;
    }

    public void setBuyCost(int costFruitScore, int fruitScore) {
        textView.setText("구매에 필요한 열매 : "+costFruitScore);
        Log.i("dd1",""+costFruitScore);// how could type the korean?
        if(costFruitScore<=fruitScore){
            num = 1;
        }else{
            num = 0;
        }
    }
}

