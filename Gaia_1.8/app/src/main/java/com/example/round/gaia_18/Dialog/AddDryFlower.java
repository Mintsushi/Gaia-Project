package com.example.round.gaia_18.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.FeatureInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.round.gaia_18.Data.DryFlower;
import com.example.round.gaia_18.Data.Plant;
import com.example.round.gaia_18.R;

import java.util.ArrayList;

import static com.example.round.gaia_18.MainActivity.dataBaseHelper;
import static com.example.round.gaia_18.MainActivity.dataList;

/**
 * Created by Round on 2017-09-22.
 */

public class AddDryFlower extends Dialog implements View.OnClickListener{

    private static final String TAG = ".AddDryFlower";

    private ListView mList;
    private MaxLevelPlantAdapter mAdapter;
    private ArrayList<DryFlower> maxLevel = new ArrayList<>();
    private ArrayList<DryFlower> selectFlower = new ArrayList<>();

    private ImageButton select;
    private ImageButton cancel;

    public AddDryFlower(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.menu_dry_flower_add_dialog_2);

        for(int i =0 ; i<dataList.getPlants().size();i++){

            if(dataList.getPlants().get(i).getLevel() == 400 && !dataList.getPlants().get(i).getDryFlowerSetting()){
                DryFlower maxPlant = dataBaseHelper.getDryFlowerData(dataList.getPlants().get(i).getPlantNo());
                maxPlant.setDryFlowerName(dataList.getPlants().get(i).getFlower().getFlowerName());
                maxPlant.setPlant(dataList.getPlants().get(i));
                maxLevel.add(maxPlant);
            }
        }

        mAdapter = new MaxLevelPlantAdapter(getContext(),R.layout.menu_dry_flower_add_dialog_item);
        mList = (ListView)findViewById(R.id.maxLevelPlant);
        mList.setAdapter(mAdapter);

        select = (ImageButton)findViewById(R.id.select);
        cancel = (ImageButton)findViewById(R.id.cancel);

        select.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        if(view == select){
            for(int i =0;i<maxLevel.size();i++){
                if(maxLevel.get(i).isCheck()){
                    maxLevel.get(i).getPlant().setDryFlowerSetting(true);
                    selectFlower.add(maxLevel.get(i));
                }
            }
        }
        dismiss();
    }

    private class MaxLevelPlantViewHolder{
        ImageView maxLevelImage;
        TextView maxLevelName;
        TextView maxLevelEffect;
        ImageView selectBox;
    }

    private class MaxLevelPlantAdapter extends ArrayAdapter<DryFlower>{
        private LayoutInflater mInflater = null;

        public MaxLevelPlantAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){ return maxLevel.size(); }

        @Override
        public View getView(int position, View view, ViewGroup parent){

            final DryFlower max = maxLevel.get(position);
            final MaxLevelPlantViewHolder viewHolder;

            if(view == null){

                view = mInflater.inflate(R.layout.menu_dry_flower_add_dialog_item,parent,false);

                viewHolder = new MaxLevelPlantViewHolder();
                viewHolder.maxLevelImage = (ImageView)view.findViewById(R.id.maxLevelImage);
                viewHolder.maxLevelName = (TextView)view.findViewById(R.id.maxLevelName);
                viewHolder.maxLevelEffect = (TextView)view.findViewById(R.id.maxLevelEffect);
                viewHolder.selectBox = (ImageView)view.findViewById(R.id.checkBox);

                view.setTag(viewHolder);
            }else{
                viewHolder = (MaxLevelPlantViewHolder)view.getTag();
            }

            if(max != null){

                //후에 식물 이미지로 변경
                viewHolder.maxLevelImage.setImageResource(R.drawable.image);
                viewHolder.maxLevelName.setText(max.getDryFlowerName());
                viewHolder.maxLevelEffect.setText("초당 "+dataList.getAllScore(max.getScore())+" 점수 획득");

                //선택 된 상태
                if(max.isCheck()){
                    viewHolder.selectBox.setVisibility(View.VISIBLE);
                    viewHolder.selectBox.setImageResource(R.drawable.select);
                    viewHolder.selectBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            max.setCheck(false);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                //선택이 안 되있는 상태
                else{
                    viewHolder.selectBox.setImageResource(R.drawable.no_select);
                    viewHolder.selectBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            max.setCheck(true);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }

            return view;
        }
    }

    public ArrayList<DryFlower> getDryFlower(){
        return this.selectFlower;
    }

}
