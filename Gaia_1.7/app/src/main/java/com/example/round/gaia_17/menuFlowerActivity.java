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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.plantArray;
import static com.example.round.gaia_17.MainActivity.relLayout;
import static com.example.round.gaia_17.MainActivity.score;
/**
 * Created by 리제 on 2017-08-26.
 */

public class menuFlowerActivity extends Fragment {

    private static final String TAG = ".menuFlowerActivity";
    private ListView mList;
    private FlowerAdapter mAdapter;
    private ArrayList<FlowerInform> mArray = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_flower_fragment,container,false);

        mAdapter = new FlowerAdapter(getContext(),R.layout.menu_flower_item);
        mList = (ListView)view.findViewById(R.id.menuFlowerList);
        mList.setAdapter(mAdapter);

        // 모든 꽃 데이터 저장 (서버에서 받아올경우)
        //id, flowerImagePath,flowerExp,flowerLv,flowerName,flowerLvUp,buyType
        mArray.add(new FlowerInform(0,"flower1",0,"길냥이",10,0));
        mArray.add(new FlowerInform(1,"flower1",0,"콩 콩",222,0));
        mArray.add(new FlowerInform(2,"flower2",0,"비 버",39,0));
        mArray.add(new FlowerInform(3,"flower2",0,"핑크비버",80,0));
        mArray.add(new FlowerInform(4,"flower2",0,"빨강비버",245,0));
        mArray.add(new FlowerInform(5,"flower2",0,"적색비버",399,0));
        mArray.add(new FlowerInform(6,"flower2",0,"붉은비버",654,0));
        mArray.add(new FlowerInform(7,"flower2",0,"빨간비버",3333,0));

        //plantArray = 사용자가 가지고 있는 꽃에 대한 정보
        for(int i =0;i<mArray.size();i++){
            for(int j =0;j<plantArray.size();j++){
                if(plantArray.get(j).getId() == mArray.get(i).getId()){
                    mArray.get(i).setBuyType(1);
                }
            }
        }

        return view;
    }

    // 플라워 정보 포멧
    public class FlowerInform{

        private int id;
        private String flowerImagePath;
        private int flowerLv;
        private String flowerName;
        private float flowerLvUp;
        private int buyType;

        public FlowerInform(int id, String flowerImagePath, int flowerLv, String flowerName, float flowerLvUp, int buyType){
             this.id = id;
             this.flowerImagePath = flowerImagePath;
             this.flowerLv = flowerLv;
             this.flowerName = flowerName;
             this.flowerLvUp = flowerLvUp;
             this.buyType = buyType;
        }

        public int getId(){return this.id;}
        public String getFlowerImagePath(){return this.flowerImagePath;}
        public int getFlowerLv(){return this.flowerLv;}
        public String getFlowerName(){return this.flowerName;}
        public float getFlowerLvUp(){return this.flowerLvUp;}
        public int getBuyType(){return this.buyType;}

        public void setFlowerLv(){this.flowerLv += 1;}
        public void setFlowerLvUp(float lvUp){this.flowerLvUp = lvUp;}

        public void setBuyType(int buyType){
            this.buyType = buyType;
        }
    }

    // 플라워창 리스트 포멧
    static class FlowerViewHolder{
        ImageView flowerImage;
        ProgressBar flowerExpBar;
        TextView flowerLvText;
        TextView flowerNameText;
        ImageButton flowerLvUpButton;
        TextView flowerLvUpText;
    }

    // 플라워 리스트 어뎁터
    public class FlowerAdapter extends ArrayAdapter<FlowerInform> {
        private LayoutInflater mInflater = null;

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            FlowerViewHolder viewHolder;


            final FlowerInform info = mArray.get(position);

            Log.i(TAG,"이름 : "+info.getFlowerName()+" , 구매 상태 : "+info.buyType);
            if(v == null){

                if(info.buyType == 1){
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

            if(info != null){
                //buyType ==0 이면 잠긴이미지
                if(info.buyType == 0) {
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.buy);
                }else{
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.levelup);
                }

                //buyType ==0 이면 잠긴이미지
                if(info.buyType == 0) {
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                }else {
                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                }
                viewHolder.flowerLvText.setText("LV . " + info.getFlowerLv());
                viewHolder.flowerNameText.setText(info.getFlowerName());
                viewHolder.flowerLvUpText.setText(Float.toString(info.flowerLvUp));

                //버튼 이밴트
                viewHolder.flowerLvUpButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int id = info.getId();
                        Log.i(TAG,"id :"+id);

                        // 스코어로 레벨 올리기
                        if(downScore(info.getFlowerLvUp())){
                            info.setFlowerLvUp(info.getFlowerLvUp()+100);
                            info.setFlowerLv();

                            if(info.getBuyType() == 0){
                                MainActivity.buyNewPlant(info.getId(),info.getFlowerLv(),info.getFlowerName(),info.getFlowerImagePath());
                                info.setBuyType(1);
                            }
                            else{
                                MainActivity.updatePlant(info.getId());
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
