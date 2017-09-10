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

import static com.example.round.gaia_18.MainActivity.dataList;
import static com.example.round.gaia_18.MainActivity.score;

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
                viewHolder.flowerLevelUp=(ImageButton) v.findViewById(R.id.flowerLevelUp);
                viewHolder.flowerLevelUpScore=(TextView)v.findViewById(R.id.flowerLevelUpScore);

                v.setTag(viewHolder);

            }else{

                viewHolder = (FlowerViewHolder) v.getTag();
            }

            if(flower != null){
                //buyType ==0 이면 잠긴이미지
                if(flower.isBuyType()) {
                    viewHolder.background.setBackgroundResource(R.drawable.flower_buy_item);
                    viewHolder.flowerLevelUp.setImageResource(R.drawable.levelup);
                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                    viewHolder.flowerLevel.setText("Level . " + flower.getLevel());
                }else if(flower.isBuyPossible()){
                    viewHolder.background.setBackgroundResource(R.drawable.flower_buy_available);
                    viewHolder.flowerLevelUp.setImageResource(R.drawable.buy);
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                    viewHolder.flowerLevel.setText("Level . ???");
                }else{
                    viewHolder.background.setBackgroundResource(R.drawable.flower_item_lock);
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                    viewHolder.flowerLevel.setText("Level . ???");
                }

                viewHolder.flowerName.setText(flower.getFlowerName());
                viewHolder.flowerLevelUpScore.setText(Float.toString(flower.getFlowerCost()));

                    //버튼 이밴트
                    viewHolder.flowerLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (flower.isBuyPossible()) {

                                // 스코어로 레벨 올리기
                                if (downScore(flower.getFlowerCost())) {
                                    //수식에 맞춰서 고칠것
                                    //표현방식도 수정(A,B,C,D....)
                                    flower.setFlowerCost(flower.getFlowerCost() + 100);
                                    viewHolder.flowerExp.setProgress(flower.getLevel());

                                    //구입하지 않은 꽃은 PlantArray에 추가 및 main view에 추가
                                    if (!flower.isBuyType()) {
                                        MainActivity.buyPlant(flower);
                                        flower.setBuyType(true);
                                        flower.setLevel(1);
                                    } else {
                                        MainActivity.updatePlantLevel(flower.getFlowerNo());
                                        flower.setLevel(flower.getLevel() + 1);
                                        Log.i("Flower",flower.getLevel() + " / "+flowers.get(position+1).getFlowerLevel());
                                        if(flower.getLevel() == flowers.get(position+1).getFlowerLevel()){
                                            flowers.get(position+1).setBuyPossible(true);
                                        }
                                    }

                                    flowerAdapter.notifyDataSetChanged();
                                } else {
                                    //이 부분은 좀 더 시각적으로 표현하자
                                    Toast.makeText(getActivity(), "Score가 부족합니다!!!", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(getActivity(), flowers.get(position - 1).getFlowerName() + "의 레벨이 부족합니다.\n" +
                                        "레벨 " + flower.getFlowerLevel() + " 까지 달성한 이후에 시도하세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }

            return v;
        }
    }

    // 현재 점수 < 꽃 레벨업(구입)에 필요한 점수 -> false
    //          >                            -> true

    boolean downScore(int flowerLevelUpScore){

        Log.i(TAG,"Flowr Level Up Score : "+flowerLevelUpScore);

        if(score - flowerLevelUpScore > 0){
            //score 감소
            score = score - flowerLevelUpScore;
            MainActivity.updateScore(score);
            return true;
        }else {
            //scre 감소 실패
            return false;
        }

    }
}
