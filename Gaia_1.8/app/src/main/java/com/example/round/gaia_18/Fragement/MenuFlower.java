package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Data.Flower;
import com.example.round.gaia_18.MainActivity;
import com.example.round.gaia_18.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.MainActivity.clickScore;
import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.seed;

/**
 * Created by Round on 2017-09-06.
 */

public class MenuFlower extends Fragment {

    private static final String TAG = ".MenuFlower";

    //Layout / View
    private ListView flowerList;
    private FlowerAdapter flowerAdapter;

    //Data
    private ArrayList<Flower> flowers = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Fragement 활성화
        View view = inflater.inflate(R.layout.menu_flower_fragment,container,false);

        //flowers : 모든 꽃 종류 / 사용자의 소유 type / level
        flowers = dataList.getFlowers();

        for(int i =1 ;i< flowers.size() ; i++){
            if(flowers.get(i).getFlowerLevel() < flowers.get(i-1).getLevel()){
                flowers.get(i).setBuyPossible(true);
            }
        }

        flowerAdapter = new FlowerAdapter(getContext(),R.layout.menu_flower_item);
        flowerList = (ListView)view.findViewById(R.id.menu_flower_list);
        flowerList.setAdapter(flowerAdapter);

        return view;
    }

    //FloewrListView ViewHolder
    private class FlowerViewHolder{
        LinearLayout background;
        ImageView flowerImage;
        ProgressBar flowerExp;
        TextView flowerLevel;
        TextView flowerName;
        TextView flowerScore;
        ImageButton flowerLevelUp;
        TextView flowerLevelUpScore;
    }

    private class FlowerAdapter extends ArrayAdapter<Flower>{

        private LayoutInflater mInflater = null;
        private FlowerViewHolder viewHolder;

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return flowers.size();
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent){

            final Flower flower = flowers.get(position);

            if(v == null){

                v=mInflater.inflate(R.layout.menu_flower_item,parent,false);

                viewHolder = new FlowerViewHolder();
                viewHolder.background = (LinearLayout)v.findViewById(R.id.background);
                viewHolder.flowerImage=(ImageView) v.findViewById(R.id.flowerImage);
                viewHolder.flowerExp=(ProgressBar) v.findViewById(R.id.flowerExp);
                viewHolder.flowerLevel=(TextView)v.findViewById(R.id.flowerLevel);
                viewHolder.flowerName=(TextView)v.findViewById(R.id.flowerName);
                viewHolder.flowerScore=(TextView)v.findViewById(R.id.flowerScore);
                viewHolder.flowerLevelUp=(ImageButton) v.findViewById(R.id.flowerLevelUp);
                viewHolder.flowerLevelUpScore=(TextView)v.findViewById(R.id.flowerLevelUpScore);

                v.setTag(viewHolder);

            }else{

                viewHolder = (FlowerViewHolder) v.getTag();
            }

            if(flower != null){
                //buyType ==0 이면 잠긴이미지
                if(flower.isBuyType()) {

                    if(flower.getLevel() == 400){
                        viewHolder.flowerLevelUp.setImageResource(R.drawable.complete);
                        viewHolder.background.setBackgroundResource(R.drawable.max_level_background);
                        viewHolder.flowerLevelUpScore.setVisibility(View.INVISIBLE);
                    }else{
                        viewHolder.flowerLevelUp.setImageResource(R.drawable.levelup);
                        viewHolder.background.setBackgroundResource(R.drawable.flower_buy_item);
                        viewHolder.flowerLevelUpScore.setText(dataList.getAllScore(flower.getCost()));
                    }

                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                    viewHolder.flowerScore.setText("클릭 당 점수 : " + dataList.getAllScore(flower.getScore()));
                    viewHolder.flowerLevel.setText("Level . " + flower.getLevel());
                    viewHolder.flowerLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(flower.getLevel()+1 >400){
                                //이 부분은 좀 더 시각적으로 표현하자
                                Toast.makeText(getActivity(), "최대레벨입니다!!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (downScore(flower.getCost())) {

                                dataList.flowerLevelUp(flower);
                                if(position+1 < flowers.size()) {
                                    if (flower.getLevel() == flowers.get(position + 1).getFlowerLevel()) {
                                        flowers.get(position + 1).setBuyPossible(true);
                                    }
                                }
                                MainActivity.updatePlantLevel(flower.getFlowerNo());
                                seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
                                if(dataList.getGoalDataByID(flower.getFlowerNo() + 2).getGoalRate() < dataList.getGoalDataByID(flower.getFlowerNo() + 2).getGoalCondition()) {
                                    dataList.getGoalDataByID(flower.getFlowerNo() + 2).setGoalRate(1);
                                }
                                flowerAdapter.notifyDataSetChanged();
                            }else {
                                //이 부분은 좀 더 시각적으로 표현하자
                                Toast.makeText(getActivity(), "Score가 부족합니다!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else if(flower.isBuyPossible()){
                    viewHolder.flowerLevelUpScore.setText(dataList.getAllScore(flower.getCost()));
                    viewHolder.background.setBackgroundResource(R.drawable.flower_buy_available);
                    viewHolder.flowerLevelUp.setImageResource(R.drawable.buy);
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                    viewHolder.flowerLevel.setText("Level . ???");
                    viewHolder.flowerLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (downScore(flower.getCost())) {
                                dataList.flowerLevelUp(flower);
                                MainActivity.buyPlant(flower);
                                flower.setBuyType(true);
                                seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
                                if(dataList.getGoalDataByID(1).getGoalRate() < dataList.getGoalDataByID(1).getGoalCondition()) {
                                    dataList.getGoalDataByID(1).setGoalRate(1);
                                }
                                flowerAdapter.notifyDataSetChanged();
                            }else {
                                //이 부분은 좀 더 시각적으로 표현하자
                                Toast.makeText(getActivity(), "Score가 부족합니다!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    viewHolder.background.setBackgroundResource(R.drawable.flower_item_lock);
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                    viewHolder.flowerLevel.setText("Level . ???");
                    viewHolder.flowerLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getActivity(), flowers.get(position - 1).getFlowerName() + "의 레벨이 부족합니다.\n" +
                                    "레벨 " + flower.getFlowerLevel() + " 까지 달성한 이후에 시도하세요.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                viewHolder.flowerName.setText(flower.getFlowerName());
                viewHolder.flowerExp.setProgress(flower.getLevel()/4);
            }
            return v;
        }
    }

    // 현재 점수 < 꽃 레벨업(구입)에 필요한 점수 -> false
    //          >                            -> true

    boolean downScore(ConcurrentHashMap<Integer, Integer> cost){

        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(cost);

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = cost.get(key);

            Log.i("BuyFlower",key+" / "+value);
            if(!dataList.minusScore(key,value,dataList.getScoreHashMap())){
                return false;
            }
        }

        return true;

    }


}
