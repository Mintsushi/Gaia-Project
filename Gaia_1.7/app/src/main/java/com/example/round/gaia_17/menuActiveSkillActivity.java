package com.example.round.gaia_17;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.round.gaia_17.MainActivity.plantArray;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.updateSeed;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuActiveSkillActivity extends Fragment {
    DB_Exception db;

    private int mHandleControl;
    private static final int SEND_THREAD_RUN = 1;
    private static final int SEND_THREAD_STOP = 0;

    private static final String TAG = ".ActiveSkillActivity";
    private ListView mList;
    private ActiveSkillAdapter mAdapter;
    private ArrayList<ActiveSkillInform> mActivityArray = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_active_skill_fragment,container,false);

        mAdapter = new ActiveSkillAdapter(getContext(),R.layout.menu_active_skill_item);
        mList = (ListView)view.findViewById(R.id.menuActiveSkillList);
        mList.setAdapter(mAdapter);

        // 스킬정보 저장 임시
        db = new DB_Exception(getActivity());

        mActivityArray.add(db.getActiveSkillInform(1,0));
        mActivityArray.add(db.getActiveSkillInform(2,0));
        mActivityArray.add(db.getActiveSkillInform(3,0));
        mActivityArray.add(db.getActiveSkillInform(4,0));
        mActivityArray.add(db.getActiveSkillInform(5,0));

        /*
        for(int i =0;i<mActivityArray.size();i++){
            for(int j =0;j<plantArray.size();j++){
                if(plantArray.get(j).getId() == mActivityArray.get(i).getId()){
                    mActivityArray.get(i).setBuyType(1);
                }
            }
        }
        */

        return view;
    }

    // 엑티브스킬 정보 포멧
    public static class ActiveSkillInform{

        private int id;
        private int activeSkillLv;
        private float activeUseCost;
        private int costType;
        private float activeSkillEffect;
        private String activeSkillName;
        private String activeSkillImagePath;

        private int buyType;

        public ActiveSkillInform(int id, int activeSkillLv, int costType, float activeUseCost, float activeEffect, String activeSkillName,  String activeSkillImagePath){
            this.id = id;
            this.activeSkillLv = activeSkillLv;
            this.activeUseCost = activeUseCost;
            this.costType = costType;
            this.activeSkillEffect = activeEffect;
            this.activeSkillName = activeSkillName;
            this.activeSkillImagePath = activeSkillImagePath;
            if(activeSkillLv ==0) {
                this.buyType = 0;
            }
            else{
                this.buyType = 1;
            }
        }

        public int getId(){return this.id;}
        public String getActiveSkillImagePath(){return this.activeSkillImagePath;}
        public int getActiveSkillLv(){return this.activeSkillLv;}
        public String getActiveSkillName(){return this.activeSkillName;}
        public float getactiveUseCost(){return this.activeUseCost;}
        public int getBuyType(){return this.buyType;}

        public void setActiveSkillLv(){this.activeSkillLv += 1;}
        public void setactiveUseCost(float lvUp){this.activeUseCost = lvUp;}

        public void setBuyType(int buyType){
            this.buyType = buyType;
        }
    }

    // 엑티브스킬창 리스트 포멧
    static class ActiveSkillViewHolder{
        ImageView skillImage;
        ProgressBar skillExpBar;
        TextView skillLvText;
        TextView skillNameText;
        ImageButton useSkillButton;
        TextView useTimeText;
        ImageButton skillLvUpButton;
        TextView skillLvUpText;
    }

    // 플라워 리스트 어뎁터
    public class ActiveSkillAdapter extends ArrayAdapter<ActiveSkillInform> {
        private LayoutInflater mInflater = null;

        public ActiveSkillAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mActivityArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            final ActiveSkillViewHolder viewHolder;
            final ActiveSkillInform info = mActivityArray.get(position);

            Log.i(TAG,"이름 : "+info.getActiveSkillName()+" , 구매 상태 : "+info.buyType);

            if(v == null){

                v=mInflater.inflate(R.layout.menu_active_skill_item,parent,false);
                viewHolder = new ActiveSkillViewHolder();
                viewHolder.skillImage=(ImageView) v.findViewById(R.id.skillImage);
                viewHolder.skillExpBar=(ProgressBar) v.findViewById(R.id.skillExpBar);
                viewHolder.skillLvText=(TextView)v.findViewById(R.id.skillLvText);
                viewHolder.skillNameText=(TextView)v.findViewById(R.id.skillNameText);
                viewHolder.useSkillButton=(ImageButton) v.findViewById(R.id.useSkillButton);
                viewHolder.useTimeText = (TextView)v.findViewById(R.id.useTimeText);
                viewHolder.skillLvUpButton=(ImageButton) v.findViewById(R.id.skillLvUpButton);
                viewHolder.skillLvUpText=(TextView)v.findViewById(R.id.skillLvUpText);

                viewHolder.useSkillButton.setImageResource(R.drawable.use);

                v.setTag(viewHolder);
            }else{
                viewHolder = (ActiveSkillViewHolder) v.getTag();
            }

            if(info != null){
                //buyType ==0 // 잠긴이미지
                if(info.buyType == 1) {
                    // 각 메소드 활성화
                    viewHolder.useSkillButton.setVisibility(View.VISIBLE);
                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.shape);
                    viewHolder.skillLvUpButton.setImageResource(R.drawable.levelup);
                    viewHolder.skillImage.setImageResource(R.drawable.image);


                }else{
                    // 각 메소드 활성화
                    viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.lock_background);
                    viewHolder.skillLvUpButton.setImageResource(R.drawable.buy);
                    viewHolder.skillImage.setImageResource(R.mipmap.ic_launcher);

                }


                // 첫번째 물주기 아이템은 사용하기 버튼이 없음
                if(info.getId()==1){
                    viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                }

                viewHolder.useTimeText.setVisibility(View.INVISIBLE);
                //viewHolder.useSkillButton.setImageResource(R.drawable.use);
                viewHolder.skillLvText.setText("LV . " + info.getActiveSkillLv());
                viewHolder.skillNameText.setText(info.getActiveSkillName());
                viewHolder.skillLvUpText.setText(Float.toString(info.getactiveUseCost()));

                viewHolder.useSkillButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // 정보에 맞는 시간동안 정보에맞는 스코어가 초당 증가
                        viewHolder.useSkillButton.setImageResource(R.drawable.nullimage);
                        viewHolder.useTimeText.setVisibility(View.VISIBLE);
                        useActiveSkill(10,1000,viewHolder.useTimeText);
                    }
                });


                //버튼 이밴트
                viewHolder.skillLvUpButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int id = info.getId();
                        Log.i(TAG,"id :"+id);

                        // 스코어로 레벨 올리기
                        if(downScore(info.getactiveUseCost())){
                            mActivityArray.set(info.getId(),db.getActiveSkillInform(info.getId(),info.getActiveSkillLv()+1));
                            if(info.getActiveSkillLv()==0){
                                info.setBuyType(1);
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

    void useActiveSkill(final int times, final int incScore, final TextView textView){

        int timeCount = 0;
        Timer mTimer = new Timer();
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                int timeCount = 0;
                mHandleControl = 1;
                while (timeCount < times) {
                    Log.i("had : ", "" + timeCount + "cont" + mHandleControl);
                    mHandler.sendEmptyMessage(incScore);
                    timeCount++;
                }
                mHandleControl = 0;
                mHandler.sendEmptyMessage(0);
                cancel();
            }
        };
        mTimer.schedule(mTask,0,1000);
    }

    private android.os.Handler mHandler = new android.os.Handler(){
        public void handleMessage(Message msg){

            //나중에 image가 확립되면 좀더 세부적으로 위치 조정
            if(msg.what != 0) {
                Log.i(TAG,"Handler Message : 1");
                score = score + msg.what;

            }
            else{
                Log.i(TAG,"Handler Message : 0");
            }
        }
    };

/*
    private android.os.Handler mHandler = new android.os.Handler(){
        public void handleMessage(Message msg){
            Log.i(TAG,"Handler Message : "+msg);
            TextView textView = (TextView)msg.obj;
            if(msg.what == SEND_THREAD_RUN) {
                Log.i("had","on");
                score = score + msg.arg2;
                updateSeed(score);
                textView.setText(String.valueOf(msg.arg1));
            }
            else{

                textView.setVisibility(View.INVISIBLE);
            }
        }
    };
*/

}
