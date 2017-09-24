package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.round.gaia_17.MainActivity.db;
import static com.example.round.gaia_17.MainActivity.fruitScore;
import static com.example.round.gaia_17.MainActivity.itemsSave;
import static com.example.round.gaia_17.MainActivity.mGoalArray;
import static com.example.round.gaia_17.MainActivity.mGoalFormatArray;
import static com.example.round.gaia_17.MainActivity.mGoalUserInfomation;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.updateFruit;
import static com.example.round.gaia_17.MainActivity.updateSeed;

/**
 * Created by 리제 on 2017-09-04.
 */

public class goalListDialog extends Dialog{
    public goalListDialog(Context context){super(context);}

    private static final String TAG = ".GoalActivity";
    private ImageButton back,testButton;
    private ListView mList;
    private GoalAdapter mAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_goal_list);

        mAdapter = new GoalAdapter(getContext(),R.layout.menu_passive_skill_item);
        mList = (ListView)findViewById(R.id.dialogGoalList);
        mList.setAdapter(mAdapter);

        back = (ImageButton)findViewById(R.id.GoalExit);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }

        });

    }

    // 업정 기본데이터 포멧
    public static class GoalFormatInformation{
        private int goalFormatID;
        private String goalFormatName;
        private String goalFormatContent;
        private int goalFormatFinalLevel;
        private int goalFormatNextDrect;
        GoalFormatInformation(int id, String name, String content, int finalLevel,int drect){
            goalFormatID = id;
            goalFormatName = name;
            goalFormatContent = content;
            goalFormatFinalLevel = finalLevel;
            goalFormatNextDrect = drect;
        }

        public int getGoalFormatFinalLevel() {
            return goalFormatFinalLevel;
        }
        public int getGoalFormatID() {
            return goalFormatID;
        }
        public String getGoalFormatContent() {
            return goalFormatContent;
        }
        public String getGoalFormatName() {
            return goalFormatName;
        }
        public int getGoalFormatNextDrect() {
            return goalFormatNextDrect;
        }
    }

    // 업적 레벨별 값 포멧
    public static class GoalInformation{

        private int goalkey;
        private int goalId;
        private int goalLevel;
        private int goalCondition;
        private int goalConditionPower;
        private int goalRewardType;
        private int goaloalReward;
        private int goalRewardPower;

        GoalInformation(int goalkey, int id, int level, int condition,int coditionPower,
                        int rewardType, int reward, int rewardPower){
               goalkey = goalkey;
               goalId = id;
               goalLevel = level;
               goalCondition = condition;
               goalConditionPower = coditionPower;
               goalRewardType = rewardType;
               goaloalReward = reward;
               goalRewardPower = rewardPower;
        }

        public void setGoalLevel(int a) {
            this.goalLevel = a;
        }

        public int getGoalkey() {
            return goalkey;
        }
        public int getGoalCondition() {
            return goalCondition;
        }
        public int getGoalConditionPower() {
            return goalConditionPower;
        }
        public int getGoalId() {
            return goalId;
        }
        public int getGoalLevel() {
            return goalLevel;
        }
        public int getGoaloalReward() {
            return goaloalReward;
        }
        public int getGoalRewardPower() {
            return goalRewardPower;
        }
        public int getGoalRewardType() {
            return goalRewardType;
        }
    }

    // 사용자 업적 데이터 (서버용)
    public static class GoalUserInfomation{
        private int[] goalNumberArr = new int[27];
        private int goalNum14PowerArr;
        GoalUserInfomation(){
            // 서버의 유저 데이터 연동
            goalNumberArr = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            goalNum14PowerArr = 0;
        }

        public int getGoalNum14PowerArr() {
            return goalNum14PowerArr;
        }
        public int getGoalNumberArr(int type) {
            return goalNumberArr[type];
        }

        public void setGoalNum14PowerArr(int goalNum14PowerArr) {
            this.goalNum14PowerArr = goalNum14PowerArr;
        }
        public void setGoalNumberArr(int type, int num) {
            this.goalNumberArr[type] = this.goalNumberArr[type] + num;
        }
    }

    // 업적 정보 포멧
    public class GoalInform{

        private int id;
        private String goalName;
        private String goalContent;
        private String goalCount;
        private int goalDestination;


        public GoalInform(int id,  String goalName, String goalContent, String goalCount, int goalDestination){
            this.id = id;
            this.goalName = goalName;
            this.goalContent = goalContent;
            this.goalCount = goalCount;
            this.goalDestination = goalDestination;
        }

        public int getId(){return this.id;}
        public String getGoalName() {
            return goalName;
        }
        public String getGoalContent() {
            return goalContent;
        }
        public String getGoalCount() {
            return goalCount;
        }
        public int getGoalDestination() {
            return goalDestination;
        }
    }
    //mGoalUserInfomation
    // 업적 리스트 포멧
    static class GoalViewHolder{
        ImageView goalImageView;
        TextView goalNameText;
        TextView goalContentText;
        TextView goalCountText;
        ImageButton goalReward;
        ProgressBar goalProgressBar;
    }

    // 업적 리스트 어뎁터
    public class GoalAdapter extends ArrayAdapter<GoalInform> {
        private LayoutInflater mInflater = null;

        public GoalAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mGoalArray.size();
        }



        @Override
        public View getView(int position, View v, ViewGroup parent){
            final GoalViewHolder viewHolder;
            int progressBarHp=0;
            final GoalInformation info = mGoalArray.get(position);
            final GoalFormatInformation formatinfo = mGoalFormatArray.get(position);

            Log.i(TAG,"이름 : "+formatinfo.getGoalFormatName());
            if(v == null){

                v=mInflater.inflate(R.layout.dialog_goal_item,parent,false);

                viewHolder = new GoalViewHolder();
                viewHolder.goalImageView=(ImageView)v.findViewById(R.id.goalImageView);
                viewHolder.goalNameText=(TextView) v.findViewById(R.id.goalNameText);
                viewHolder.goalContentText=(TextView)v.findViewById(R.id.goalContentText);
                viewHolder.goalCountText=(TextView)v.findViewById(R.id.goalCountText);
                viewHolder.goalReward=(ImageButton) v.findViewById(R.id.goalReward);
                viewHolder.goalProgressBar = (ProgressBar)v.findViewById(R.id.goalProgressBar);
                v.setTag(viewHolder);
            }else{
                viewHolder = (GoalViewHolder) v.getTag();
            }

            if(info != null){
                viewHolder.goalImageView.setImageResource(R.drawable.image);
                viewHolder.goalNameText.setText(formatinfo.getGoalFormatName()+" "+info.getGoalLevel());
                viewHolder.goalContentText.setText(formatinfo.getGoalFormatContent());
                viewHolder.goalCountText.setText(""+mGoalUserInfomation.getGoalNumberArr(formatinfo.getGoalFormatID()-1)+ " / "+""+info.getGoalCondition());
                //viewHolder.goalReward.setBackground(R.drawable.rewoard);
                viewHolder.goalProgressBar.setProgress(mGoalUserInfomation.getGoalNumberArr(formatinfo.getGoalFormatID()-1)*(100 / info.getGoalCondition()));
                Log.i("SQL IF:", ""+info.getGoalId() +" / "+(formatinfo.getGoalFormatNextDrect()+info.getGoalLevel()) +" / "+(formatinfo.getGoalFormatNextDrect()+formatinfo.getGoalFormatFinalLevel()));

                //SKILL의 최대 레벨이 아니면
                if((formatinfo.getGoalFormatNextDrect()+info.getGoalLevel()) < (formatinfo.getGoalFormatNextDrect()+formatinfo.getGoalFormatFinalLevel())) {
                    viewHolder.goalReward.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (info.getGoalCondition() <= mGoalUserInfomation.getGoalNumberArr(formatinfo.getGoalFormatID() - 1)) {
                                mGoalArray.set(formatinfo.getGoalFormatID() - 1, db.getGoalInform(formatinfo.getGoalFormatNextDrect()+info.getGoalLevel()));
                                Toast.makeText(v.getContext(), "업적달성", Toast.LENGTH_LONG).show();

                                final goalCheckDialog dialog = new goalCheckDialog(getContext());
                                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        dialog.setmessageBoxA1("업적 보상 획득!");
                                        dialog.setmessageBoxA2(rewardType(info.getGoalRewardType()));
                                        dialog.setmessageBoxA3(rewardCacul(info.getGoaloalReward(),info.getGoalRewardPower()));
                                        rewardResult(info.getGoalRewardType(),info.getGoaloalReward(),info.getGoalRewardPower());
                                        dialog.setImageBoxA1("image");
                                    }
                                });
                                dialog.show();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    });

                }
                //업적이 최대 레벨
                else if(formatinfo.getGoalFormatNextDrect()+info.getGoalLevel() == (formatinfo.getGoalFormatNextDrect()+formatinfo.getGoalFormatFinalLevel())) {
                    viewHolder.goalReward.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            if (info.getGoalCondition() <= mGoalUserInfomation.getGoalNumberArr(formatinfo.getGoalFormatID() - 1)) {

                                Toast.makeText(v.getContext(), "업적달성", Toast.LENGTH_LONG).show();
                                viewHolder.goalNameText.setText(formatinfo.getGoalFormatName()+" (M)");
                                info.setGoalLevel(info.getGoalLevel()+1);

                                final goalCheckDialog dialog = new goalCheckDialog(getContext());
                                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                    @Override
                                    public void onShow(DialogInterface dialogInterface) {
                                        dialog.setmessageBoxA1("업적 보상 획득!");
                                        dialog.setmessageBoxA2(rewardType(info.getGoalRewardType()));
                                        dialog.setmessageBoxA3(rewardCacul(info.getGoaloalReward(),info.getGoalRewardPower()));
                                        rewardResult(info.getGoalRewardType(),info.getGoaloalReward(),info.getGoalRewardPower());
                                        dialog.setImageBoxA1("image");
                                    }
                                });
                                dialog.show();
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else
                {
                    viewHolder.goalNameText.setText(formatinfo.getGoalFormatName()+" (M)");
                    viewHolder.goalReward.setOnClickListener(null);
                    viewHolder.goalReward.setImageResource(R.drawable.dialog_close);

                }
            }

            return v;
        }

        String rewardType(int type) {
            switch (type) {
                case 1:
                    return "Fruit";
                case 2:
                    return "Seed";
                case 3:
                    return "체력포션 (Item)";
                case 4:
                    return "부활포션 (Item)";
                case 5:
                    return "물 (Item)";
            }
            return "null";
        }

        String rewardCacul(int num, int numPower){
            if(num==2){
                // 점수형식 루틴
                return "x"+String.valueOf(num)+"획득!";
            }
            else {return "x"+String.valueOf(num)+"획득!";}
        }

        void rewardResult(int type, int num, int numPower){
            switch (type) {
                case 1:
                    // "Fruit";
                    fruitScore = fruitScore + num;
                    updateFruit(fruitScore);
                    break;
                case 2:
                    // 점수처리루틴
                    score = score + num;
                    updateSeed(score);
                    break;
                case 3:
                    // "체력포션 (Item)";
                    itemsSave[0] = itemsSave[0] +1;
                    break;
                case 4:
                    // "부활포션 (Item)";
                    itemsSave[1] = itemsSave[1] +1;
                    break;
                case 5:
                    // "물 (Item)";
                    itemsSave[1] = itemsSave[1] +1;
                    break;
            }
        }
    }
}
