package com.example.round.gaia_18.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.R;

import java.util.Collections;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.user;

/**
 * Created by Round on 2017-09-22.
 */

public class AddDryFlowerItemDialog extends Dialog{

    private TextView cost;
    private final ConcurrentHashMap<Integer, Integer> ADD_FLOWER_ITEM = new ConcurrentHashMap<>();

    public AddDryFlowerItemDialog(Context context) {
        super(context);
        ADD_FLOWER_ITEM.put(0,100);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setContentView(R.layout.menu_dry_flower_add_dialog);

        cost = (TextView)findViewById(R.id.cost);
        cost.setText(" X "+dataList.getAllScore(ADD_FLOWER_ITEM));
        ImageButton buy = (ImageButton)findViewById(R.id.buy);
        ImageButton cancel = (ImageButton)findViewById(R.id.cancel);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(addDryFlowerItem()){
                    user.setDryFlowerItem(user.getDryFlowerItem()+1);
                    dismiss();
                }
                else{
                    Toast.makeText(getContext(),"Fruit이 부족합니다!!!",Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private Boolean addDryFlowerItem(){
        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(ADD_FLOWER_ITEM);

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = ADD_FLOWER_ITEM.get(key);

            Log.i("BuyFlower",key+" / "+value);
            if(!dataList.minusScore(key,value,dataList.getFruitHashMap())){
                return false;
            }
        }

        return true;

    }

}
