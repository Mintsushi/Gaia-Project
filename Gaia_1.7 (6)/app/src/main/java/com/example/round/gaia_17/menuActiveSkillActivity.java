package com.example.round.gaia_17;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.Timer;
import java.util.TimerTask;

import static com.example.round.gaia_17.MainActivity.db;
import static com.example.round.gaia_17.MainActivity.fruitScore;
import static com.example.round.gaia_17.MainActivity.mActivityArray;
import static com.example.round.gaia_17.MainActivity.mActivityFormatArray;
import static com.example.round.gaia_17.MainActivity.mGoalUserInfomation;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.scoreCalculaters;
import static com.example.round.gaia_17.MainActivity.skill0Effect;
import static com.example.round.gaia_17.MainActivity.skill4Effect;
import static com.example.round.gaia_17.MainActivity.skillup;
import static com.example.round.gaia_17.MainActivity.updateFruit;
import static com.example.round.gaia_17.MainActivity.updateSeed;

/**
 * 김태우 액티브스킬창구현
 * 스킬정보 모두연동
 * 6번 스킬 제외하고 모두 게임점수에 영향을 받게 구현함
 * 스킬 구매시 시드와 열매로 구매 가능하다.
 *
 * */

public class menuActiveSkillActivity extends android.support.v4.app.Fragment {

    private static final String TAG = ".ActiveSkillActivity";
    private ListView mList;
    private ActiveSkillAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_active_skill_fragment,container,false);
        mAdapter = new ActiveSkillAdapter(getContext(),R.layout.menu_active_skill_item);
        mList = (ListView)view.findViewById(R.id.menuActiveSkillList);
        mList.setAdapter(mAdapter);
        // 스킬정보 저장 임시

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
    public static class ActiveSkillFormatInform{

        private int id;
        private String activeSkillFormatName;
        private String activeSkillFormatImagePath;
        private String activeSkillFormatButtonImagePath;
        private int activeSkillFormatReuseTime;
        private int activeSkillFormatOnPassive;
        private int activeSkillFormatSkillType;
        private String activeSkillFormatSkillExplain;

        public ActiveSkillFormatInform(int id, String activeSkillFormatName,  String activeSkillFomatImagePath, String activeSkillFormatButtonImagePath
                ,int activeSkillFormatReuseTime,int activeSkillFormatOnPassive,int activeSkillFormatSkillType, String activeSkillFormatSkillExplain){
            this.id = id;
            this.activeSkillFormatName = activeSkillFormatName;
            this.activeSkillFormatImagePath = activeSkillFomatImagePath;
            this.activeSkillFormatButtonImagePath = activeSkillFormatButtonImagePath;
            this.activeSkillFormatReuseTime = activeSkillFormatReuseTime;
            this.activeSkillFormatOnPassive = activeSkillFormatOnPassive;
            this.activeSkillFormatSkillType = activeSkillFormatSkillType;
            this.activeSkillFormatSkillExplain = activeSkillFormatSkillExplain;

        }

        public int getId(){return this.id;}
        public String getActiveSkillFormatName() {
            return activeSkillFormatName;
        }
        public String getActiveSkillFormatImagePath() {
            return activeSkillFormatImagePath;
        }
        public String getActiveSkillFormatButtonImagePath() {
            return activeSkillFormatButtonImagePath;
        }
        public int getActiveSkillFormatOnPassive() {
            return activeSkillFormatOnPassive;
        }
        public int getActiveSkillFormatReuseTime() {
            return activeSkillFormatReuseTime;
        }
        public String getActiveSkillFormatSkillExplain() {
            return activeSkillFormatSkillExplain;
        }
        public int getActiveSkillFormatSkillType() {
            return activeSkillFormatSkillType;
        }

    }

    // 엑티브스킬 정보 포멧
    public static class ActiveSkillInform{

        private int id;
        private int activeSkillLv;
        private int activeUseCost;
        private int costType;
        private float activeSkillEffect;
        private int activeUseCostPower;
        private int buyType;

        public ActiveSkillInform(int id, int activeSkillLv, int costType, int activeUseCost, float activeEffect, int activeUseCostPower){
            this.id = id;
            this.activeSkillLv = activeSkillLv;
            this.costType = costType;
            this.activeUseCost = activeUseCost;
            this.activeSkillEffect = activeEffect;
            this.activeUseCostPower = activeUseCostPower;
            if(activeSkillLv ==0) {
                this.buyType = 0;
            }
            else{
                this.buyType = 1;
            }
        }

        public int getId(){return this.id;}
        public int getActiveSkillLv(){return this.activeSkillLv;}
        public float getActiveSkillEffect() {
            return activeSkillEffect;
        }
        public int getActiveUseCost() {
            return activeUseCost;
        }
        public int getCostType() {
            return costType;
        }
        public int getBuyType(){return this.buyType;}
        public int getActiveUseCostPower() {
            return activeUseCostPower;
        }

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
            final ActiveSkillFormatInform fomatinfo = mActivityFormatArray.get(position);
            Log.i(TAG,"이름 : "+fomatinfo.getActiveSkillFormatName()+" , 레벨 : "+info.getActiveSkillLv() +" , 구매 상태 : "+info.buyType);

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

                    // 지속스킬인경우 버튼 빼기
                    if(fomatinfo.getActiveSkillFormatOnPassive()==1){
                        viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                        skillEffect(fomatinfo.activeSkillFormatSkillType,(int)info.getActiveSkillEffect(),(int)info.getActiveSkillEffect());
                    }else{

                        viewHolder.useSkillButton.setVisibility(View.VISIBLE);
                        viewHolder.useSkillButton.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                // 정보에 맞는 시간동안 정보에맞는 스코어가 초당 증가
                                viewHolder.useTimeText.setVisibility(View.VISIBLE);
                                viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                                Timemer cooldownTimemer = new Timemer();
                                skillEffect(fomatinfo.activeSkillFormatSkillType,
                                        info.getActiveSkillLv()*(int)info.getActiveSkillEffect(),
                                        info.getActiveSkillLv()*(int)info.getActiveSkillEffect());
                                cooldownTimemer.skillCooldown(fomatinfo.activeSkillFormatReuseTime, viewHolder.useTimeText, viewHolder.useSkillButton);

                            }
                        });
                    }
                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.shape);
                    viewHolder.skillLvUpButton.setImageResource(R.drawable.levelup);
                    //viewHolder.skillImage.setImageResource("R.drawable."+fomatinfo.getActiveSkillFormatImagePath());
                    viewHolder.skillImage.setImageResource(R.drawable.image);

                }else{
                    // 각 메소드 활성화
                    viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.lock_background);
                    viewHolder.skillLvUpButton.setImageResource(R.drawable.buy);
                    //viewHolder.skillImage.setImageResource("R.drawable."+fomatinfo.getActiveSkillFormatImagePath());
                    viewHolder.skillImage.setImageResource(R.drawable.image);

                }




                // 첫번째 물주기 아이템은 사용하기 버튼이 없음
                if(info.getId()==0){
                    viewHolder.useSkillButton.setVisibility(View.INVISIBLE);
                }

                viewHolder.useTimeText.setVisibility(View.INVISIBLE);
                viewHolder.useSkillButton.setImageResource(R.drawable.use);
                viewHolder.skillLvText.setText("LV . " + info.getActiveSkillLv());
                viewHolder.skillNameText.setText(fomatinfo.getActiveSkillFormatName());

                if(info.getCostType()==2){

                    viewHolder.skillLvUpText.setText((scoreCalculaters(info.getActiveUseCost(),info.activeUseCostPower)));
                    //버튼 이밴트
                    viewHolder.skillLvUpButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int id = info.getId();
                            Log.i(TAG,"id :"+id);


                            // 스코어로 레벨 올리기
                            if(downScore(info.getActiveUseCost())){
                                //업적
                                goalLvInc(info.getId());
                                int nextLV = ((info.getId()-1)*30)+(info.getActiveSkillLv());
                                if(info.getActiveSkillLv()==0){
                                    mActivityArray.set(info.getId(),db.getActiveSkillInform(nextLV));
                                }else {
                                    mActivityArray.set(info.getId(), db.getActiveSkillInform(nextLV));
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else{
                    viewHolder.skillLvUpText.setText(scoreCalculaters(info.getActiveUseCost(),info.activeUseCostPower));
                    //버튼 이밴트
                    viewHolder.skillLvUpButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            int id = info.getId();

                            // 열매로로 레벨 올리기
                            if(downFruit(info.getActiveUseCost())){
                                // 업적
                                goalLvInc(info.getId());
                                int nextLV = ((info.getId()-1)*30)+(info.getActiveSkillLv());
                                Log.i(TAG,"id :"+id +nextLV);
                                if(info.getActiveSkillLv()==0){
                                    mActivityArray.set(info.getId(),db.getActiveSkillInform(nextLV));
                                }else {
                                    mActivityArray.set(info.getId(), db.getActiveSkillInform(nextLV));
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }

            }
            return v;
        }

    }

    // 점수 감소 함수
    boolean downScore(float desSeed){
        if(score - desSeed >= 0){
            //score 감소
            score = score - desSeed;
            updateSeed(score);
            Log.i("downScore","True");
            return true;
        }else {
            //scre 감소 실패
            Log.i("downScore","False");
            return false;
        }

    }
    boolean downFruit(int desFruit){
        if(fruitScore - desFruit >= 0){
            //score 감소
            fruitScore = fruitScore - desFruit;
            updateFruit(fruitScore);
            return true;
        }else {
            //scre 감소 실패
            return false;
        }

    }

//goalUseInc
    void skillEffect(int type, int time,int incScore){
        switch (type){
            case 0:
                Timemer case0 = new Timemer();
                goalUseInc(1);
                case0.skill0start(time);
                break;
            case 1:
                skill1start(incScore);
                goalUseInc(2);
                break;
            case 2:
                Timemer case2 = new Timemer();
                goalUseInc(3);
                case2.skill2start(time);
                break;
            case 3:
                goalUseInc(4);
                skill3start(incScore);
                break;
            case 4:
                Timemer case4 = new Timemer();
                goalUseInc(5);
                case4.skill4start(60000/time);
                break;

            default:
        }
    }

    void skill1start(float incScore) {
        score = score + incScore;
        updateSeed(score);
    }
    void skill3start(int incScore) {
        skill4Effect = (float)1+((float)incScore/(float)100);
        Log.i("skill4Effect",""+skill4Effect +"/"+incScore);
    }

    public static class Timemer {
        // 시간관련함수
        private TimerTask second;
        private final Handler handler = new Handler();
        public int getTimer_sec() {
            return timer_sec;
        }
        int timer_sec = 0;

        public void skillCooldown(final int time, final TextView textView, final ImageButton button) {
            timer_sec = 0;
            final Timer timer = new Timer();

            second = new TimerTask() {
                @Override
                public void run() {
                    Log.i("Timer ::", "skillCooldown");
                    Update(time,textView,button);
                    timer_sec++;
                    if(timer_sec == time) {
                        timer.cancel();
                    }
                }
            };
            timer.schedule(second, 0, 1000);


        }
        protected void Update(final int time, final TextView textView, final ImageButton button) {

            Runnable updater = new Runnable() {

                public void run() {
                    textView.setText(String.valueOf(time - getTimer_sec()));
                    if(time - getTimer_sec()==0) {
                        textView.setVisibility(View.INVISIBLE);
                        button.setVisibility(View.VISIBLE);
                    }
                }

            };

            handler.post(updater);

        }

        public void skill0start(final int time){
            timer_sec = 0;
            final Timer timer = new Timer();

            second = new TimerTask() {
                @Override
                public void run() {

                    Log.i("Timer ::", "skill0start");
                    skill0Update(1);
                    timer_sec++;
                    if(timer_sec == time) {
                        skill0Update(0);
                        timer.cancel();
                    }
                }
            };
            timer.schedule(second, 0, 1000);
        }
        public void skill0Update(int type){
            if(type ==1){
                skill0Effect = 2;
            }else {
                skill0Effect = 1;
            }
        }

        public void skill2start(final int time){
            timer_sec = 0;
            final Timer timer = new Timer();

            second = new TimerTask() {
                @Override
                public void run() {
                    Log.i("Timer ::", "skill2start");
                    timer_sec++;
                    skill2Update();
                    if(timer_sec == time*10) {
                        timer.cancel();
                    }
                }
            };
            timer.schedule(second, 0, 100);
        }
        public void skill2Update() {

            Runnable updater = new Runnable() {

                public void run() {
                    score = score + skill4Effect*((skillup)*skill0Effect);
                    updateSeed(score);
                }

            };

            handler.post(updater);

        }

        public void skill4start(final int time){
            timer_sec = 0;
            final Timer timer = new Timer();

            second = new TimerTask() {
                @Override
                public void run() {
                    Log.i("Timer ::", "skill4start");
                    timer_sec++;
                    skill4Update();
                }
            };
            timer.schedule(second, 0, time);     //1분 60초 60000에 time만큼 이면 60000 / 10  6000
        }
        public void skill4Update() {

            Runnable updater = new Runnable() {

                public void run() {
                    score = score + skill4Effect*((skillup)*skill0Effect);
                    updateSeed(score);
                }

            };

            handler.post(updater);

        }

        public void dryupScore(final int scores){
            timer_sec = 0;
            final Timer timer = new Timer();

            second = new TimerTask() {
                @Override
                public void run() {
                    Log.i("Timer ::", "skill4start");
                    timer_sec++;
                    dryupScoreUpdate(scores);
                }
            };
            timer.schedule(second, 0, 1000);     //1분 60초 60000에 time만큼 이면 60000 / 10  6000
        }
        public void dryupScoreUpdate(final int scores) {

            Runnable updater = new Runnable() {

                public void run() {
                    score = score + scores;
                    updateSeed(score);
                }

            };

            handler.post(updater);

        }

    }

    // if(info.getId()==0)
    void goalLvInc(int id){
        switch (id){
            case 0:
                mGoalUserInfomation.setGoalNumberArr(8,1);
                break;
            case 1:
                mGoalUserInfomation.setGoalNumberArr(15,1);
                break;
            case 2:
                mGoalUserInfomation.setGoalNumberArr(17,1);
                break;
            case 3:
                mGoalUserInfomation.setGoalNumberArr(19,1);
                break;
            case 4:
                mGoalUserInfomation.setGoalNumberArr(21,1);
                break;
            case 5:
                mGoalUserInfomation.setGoalNumberArr(23,1);
                break;
            case 6:
                mGoalUserInfomation.setGoalNumberArr(25,1);
                break;
        }
    }

    void goalUseInc(int id){
        switch (id) {
            case 1:
                mGoalUserInfomation.setGoalNumberArr(16, 1);
                break;
            case 2:
                mGoalUserInfomation.setGoalNumberArr(18, 1);
                break;
            case 3:
                mGoalUserInfomation.setGoalNumberArr(20, 1);
                break;
            case 4:
                mGoalUserInfomation.setGoalNumberArr(22, 1);
                break;
            case 5:
                mGoalUserInfomation.setGoalNumberArr(24, 1);
                break;
            case 6:
                mGoalUserInfomation.setGoalNumberArr(26, 1);
                break;
        }
    }
}


