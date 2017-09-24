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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.round.gaia_17.MainActivity.mGoalUserInfomation;

/**
 * Created by 리제 on 2017-09-20.
 */

public class menuStoreCheckDialog extends Dialog{
    public menuStoreCheckDialog(Context context){ super((context));}

    ImageView storeCheckImage;
    TextView storeCheckNameText;
    TextView storeCheckExplainText;

    TextView diaPreCostTypeText;
    TextView diaPreCostText;
    TextView diaUseCostTypeText;
    TextView diaUseCostText;
    TextView diaFutCostTypeText;
    TextView diaFutCostText;

    ImageButton diaBuyYesButton;
    ImageButton diaBuyNoButton;


    int futCost;
    int preCost;
    int useCost;
    int costType;
    int buySuccess;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.menu_store_check_dialog);

        storeCheckImage = (ImageView)findViewById(R.id.storeCheckImage);
        storeCheckNameText = (TextView)findViewById(R.id.storeCheckNameText);
        storeCheckExplainText = (TextView)findViewById(R.id.storeCheckExplainText);
        diaPreCostTypeText = (TextView)findViewById(R.id.diaPreCostTypeText);
        diaPreCostText = (TextView)findViewById(R.id.diaPreCostText);
        diaUseCostTypeText = (TextView)findViewById(R.id.diaUseCostTypeText);
        diaUseCostText = (TextView)findViewById(R.id.diaUseCostText);
        diaFutCostTypeText = (TextView)findViewById(R.id.diaFutCostTypeText);
        diaFutCostText = (TextView)findViewById(R.id.diaFutCostText);
        diaBuyYesButton = (ImageButton)findViewById(R.id.buyYesButton);
        diaBuyNoButton = (ImageButton)findViewById(R.id.buyNoButton);

        diaBuyYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(0 <= (preCost-useCost)){
                    futCost = getFutCost(preCost-useCost);
                    costType = getFutCost(costType);
                    buySuccess = getFutCost(1);
                    dismiss();
                    Toast.makeText(getContext(), "구매성공"+futCost, Toast.LENGTH_LONG).show();
                    mGoalUserInfomation.setGoalNumberArr(11,1);
                }else {
                    buySuccess = getFutCost(0);
                    dismiss();
                    Toast.makeText(getContext(), "구매실패 잔액부족", Toast.LENGTH_LONG).show();
                }
            }
        });

        diaBuyNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buySuccess = getFutCost(0);
                dismiss();
            }
        });

    }


    int getFutCost( int a){
        return a;
    }

    public void setName(String str) {
        storeCheckNameText.setText(str);
    }
    public void setExplain(String str) {
        storeCheckExplainText.setText(str);
    }
    public void setImage(String str) {
        Log.i("path :: ", ":: " + str + "::" + getContext().getPackageName());
        //storeCheckImage.setImageResource();
    }

    public void setUseCost(int type, int str) {
        useCost = str;
        diaUseCostText.setText(""+str);
        costType = type;
        if(type==1){
            diaPreCostTypeText.setText("현재 보유중인 열매 : ");
            diaUseCostTypeText.setText("구매에 필요한 열매 : ");
            diaFutCostTypeText.setText("구매후 보유중인 열매 : ");
        }else{
            diaPreCostTypeText.setText("현재 보유중인 씨앗 : ");
            diaUseCostTypeText.setText("구매에 필요한 씨앗 : ");
            diaFutCostTypeText.setText("구매후 보유중인 씨앗 : ");
        }
    }
    public void setPreCost(int str) {
        Log.i("str : ",""+str );
            preCost =str;
            diaPreCostText.setText(""+str);
            diaFutCostText.setText(""+(preCost-useCost));
    }

}

/*
.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = gettype(0);
                dismiss();
            }
        });
* */
