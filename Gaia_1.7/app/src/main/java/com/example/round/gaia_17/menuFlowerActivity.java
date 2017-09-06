package com.example.round.gaia_17;

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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.content.pm.PackageManager;

import com.example.round.gaia_17.Data.DataList;
import com.example.round.gaia_17.Data.Flower;

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.plantArray;
import static com.example.round.gaia_17.MainActivity.score;
/**
 * Created by 리제 on 2017-08-26.
 */

public class menuFlowerActivity extends Fragment {

    private static final String TAG = ".menuFlowerActivity";
    private ListView mList;
    private FlowerAdapter mAdapter;
    private DataList dataList = MainActivity.dataList;
    private ArrayList<Flower> flowers = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_flower_fragment,container,false);

        flowers = dataList.getFlowers();

        mAdapter = new FlowerAdapter(getContext(),R.layout.menu_flower_item);
        mList = (ListView)view.findViewById(R.id.menuFlowerList);
        mList.setAdapter(mAdapter);

        return view;
    }

    public class FlowerAdapter extends ArrayAdapter<Flower>{

        private LayoutInflater mInflater = null;
        private Flower flower;
        private FlowerViewHolder viewHolder;

        class FlowerViewHolder{
            ImageView flowerImage;
            ProgressBar flowerExpBar;
            TextView flowerLvText;
            TextView flowerNameText;
            ImageButton flowerLvUpButton;
            TextView flowerLvUpText;
        }

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return flowers.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){

            flower = flowers.get(position);

            if(v == null){

                if(flower.isBuyType()){
                    v=mInflater.inflate(R.layout.menu_flower_item,parent,false);
                }
                else{
                    v=mInflater.inflate(R.layout.menu_flower_item_lock,parent,false);
                }

                viewHolder = new FlowerViewHolder();
                viewHolder.flowerImage=(ImageView) v.findViewById(R.id.flowerImage);
                viewHolder.flowerExpBar=(ProgressBar) v.findViewById(R.id.flowerExpBar);
                viewHolder.flowerLvText=(TextView)v.findViewById(R.id.flowerLvText);
                viewHolder.flowerNameText=(TextView)v.findViewById(R.id.flowerNameText);
                viewHolder.flowerLvUpButton=(ImageButton) v.findViewById(R.id.flowerLvUpButton);
                viewHolder.flowerLvUpText=(TextView)v.findViewById(R.id.flowerLvUpText);

                v.setTag(viewHolder);

            }else{
                viewHolder = (FlowerViewHolder) v.getTag();
            }

            if(flower != null){
                //buyType ==0 이면 잠긴이미지
                if(flower.isBuyType()) {
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.levelup);
                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                }else{
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.buy);
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                }

                viewHolder.flowerLvText.setText("LV . " + flower.getLevel());
                viewHolder.flowerNameText.setText(flower.getFlowerName());
                viewHolder.flowerLvUpText.setText(Float.toString(flower.getFlowerCost()));

                //버튼 이밴트
                viewHolder.flowerLvUpButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int id = flower.getFlowerNo();
                        Log.i(TAG,"id :"+id);

                        // 스코어로 레벨 올리기
                        if(downScore(flower.getFlowerCost())){
                            //수식에 맞춰서 고칠것
                            flower.setFlowerCost(flower.getFlowerCost()+100);
                            flower.setLevel(flower.getLevel()+1);
                            viewHolder.flowerExpBar.setProgress(flower.getLevel());

                            if(!flower.isBuyType()){
                                MainActivity.buyNewPlant(flower);
                                flower.setBuyType(true);
                            }
                            else{
                                MainActivity.updatePlant(flower.getFlowerNo());
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });

            }

            return v;
        }

    }
    // 점수 감소 함수
    boolean downScore(float desSeed){
        if(score - desSeed > 0){
            //score 감소
            score = score - desSeed;
            MainActivity.updateSeed(score);
            return true;
        }else {
            //scre 감소 실패
            return false;
        }

    }
}
