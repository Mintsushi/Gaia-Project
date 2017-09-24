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

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.flowerActivityArray;
import static com.example.round.gaia_17.MainActivity.mGoalUserInfomation;
import static com.example.round.gaia_17.MainActivity.plantArray;
import static com.example.round.gaia_17.MainActivity.score;
/**
 * Created by 리제 on 2017-08-26.
 *
 */

public class menuFlowerActivity extends Fragment {



    private static final String TAG = ".menuFlowerActivity";
    private ListView mList;
    private FlowerAdapter mAdapter;
    public static ArrayList<Flowerformatinform> mFlowerArray = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_flower_fragment,container,false);

        mAdapter = new FlowerAdapter(getContext(),R.layout.menu_flower_item);
        mList = (ListView)view.findViewById(R.id.menuFlowerList);
        mList.setAdapter(mAdapter);

        // 모든 꽃 데이터 저장 (서버에서 받아올경우)
        //id, flowerImagePath,flowerLv,flowerName,flowerLvUp,buyType

        for(int i=0; i<5; i++) {
            mFlowerArray.add(new Flowerformatinform(i, flowerActivityArray.get(i).getFlowerImage(), flowerActivityArray.get(i).getFlowerLevel(), flowerActivityArray.get(i).getFlowerName(), flowerActivityArray.get(i).getFlowerCost(), 0));
        }

        //plantArray = 사용자가 가지고 있는 꽃에 대한 정보
        for(int i =0;i<mFlowerArray.size();i++){
            for(int j =0;j<plantArray.size();j++){
                if(plantArray.get(j).getId() == mFlowerArray.get(i).getId()){
                    mFlowerArray.get(i).setBuyType(1);
                }
            }
        }

        return view;
    }

    // 플라워 정보 포멧
    public class Flowerformatinform{

        private int id;
        private String flowerImagePath;
        private int flowerLv;
        private String flowerName;
        private float flowerLvUp;
        private int buyType;

        public Flowerformatinform(int id, String flowerImagePath, int flowerLv, String flowerName, float flowerLvUp, int buyType){
             this.id = id;
             this.flowerImagePath = flowerImagePath;
             this.flowerLv = flowerLv;
             this.flowerName = flowerName;
             this.flowerLvUp = flowerLvUp;
            if(flowerLv!=0){
                this.buyType = 1;
            }
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

        public void setFlowerLvi(int i) {
            this.flowerLv =i;
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
    public class FlowerAdapter extends ArrayAdapter<Flowerformatinform> {
        private LayoutInflater mInflater = null;

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mFlowerArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            final FlowerViewHolder viewHolder;

            DB_Exception.Flower formatformatinfo = flowerActivityArray.get(position);
            final Flowerformatinform formatinfo = mFlowerArray.get(position);

            Log.i(TAG,"이름 : "+formatinfo.getFlowerName()+" , 구매 상태 : "+formatinfo.buyType);
            if(v == null){

                if(formatinfo.buyType == 1){
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

            if(formatformatinfo != null){
                //buyType ==0 이면 잠긴이미지
                if(formatinfo.buyType == 0) {
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.buy);
                }else{
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.levelup);
                }

                //buyType ==0 이면 잠긴이미지
                if(formatinfo.buyType == 0) {
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                }else {
                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                }
                viewHolder.flowerLvText.setText("LV . " + formatinfo.getFlowerLv());
                viewHolder.flowerNameText.setText(formatinfo.getFlowerName());
                viewHolder.flowerLvUpText.setText(Float.toString(formatinfo.flowerLvUp));

                //버튼 이밴트
                viewHolder.flowerLvUpButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int id = formatinfo.getId();
                        Log.i(TAG,"id :"+id);

                        // 스코어로 레벨 올리기
                        if(downScore(formatinfo.getFlowerLvUp())){

                            //9.20 김태우 업적 3,4,5,6,7 증가코드
                            goalLvInc(formatinfo.getId());
                            formatinfo.setFlowerLvUp(formatinfo.getFlowerLvUp()+100);
                            formatinfo.setFlowerLv();
                            viewHolder.flowerExpBar.setProgress(formatinfo.getFlowerLv());
                            if(formatinfo.getBuyType() == 0){

                                //9.20 김태우 업적 2 증가코드
                                mGoalUserInfomation.setGoalNumberArr(2,1);
                                MainActivity.buyNewPlant(formatinfo.getId(),formatinfo.getFlowerLv(),formatinfo.getFlowerName(),formatinfo.getFlowerImagePath());
                                formatinfo.setBuyType(1);
                            }
                            else{
                                MainActivity.updatePlant(formatinfo.getId());
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

    // 김태우
    // 업적
    void goalLvInc(int id){
        switch (id){
            case 0: //민들레
                mGoalUserInfomation.setGoalNumberArr(2,1);
                break;
            case 1: // 나팔꽃
                mGoalUserInfomation.setGoalNumberArr(4,1);
                break;
            case 2: //장미
                mGoalUserInfomation.setGoalNumberArr(6,1);
                break;
            case 3: //철쪽
                mGoalUserInfomation.setGoalNumberArr(5,1);
                break;
            case 4: // 호야
                mGoalUserInfomation.setGoalNumberArr(3,1);
                break;
        }
    }
}
