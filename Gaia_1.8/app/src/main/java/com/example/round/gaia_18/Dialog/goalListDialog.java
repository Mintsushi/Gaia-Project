package com.example.round.gaia_18.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.round.gaia_18.Data.GoalData;
import com.example.round.gaia_18.Data.GoalInfo;
import com.example.round.gaia_18.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.round.gaia_18.MainActivity.dataBaseHelper;
import static com.example.round.gaia_18.MainActivity.dataList;

/**
 * Created by Round on 2017-09-23.
 */

public class goalListDialog extends Dialog {

    private static final String TAG =".GoalActivity";

    private ArrayList<GoalInfo> goalInfos;
    private ListView goalList;
    private GoalAdapter mAdapter;


    public goalListDialog(Context context){super(context);}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.goal_dialog);

        goalInfos = dataBaseHelper.getAllGoalInfo();

        mAdapter = new GoalAdapter(getContext(),R.layout.goal_dialog_item);
        goalList = (ListView)findViewById(R.id.goal_list);
        goalList.setAdapter(mAdapter);
    }

    private class GoalViewHolder{
        ImageView goalImageView;
        TextView goalName;
        TextView goalExplain;
        TextView goalCount;
        ProgressBar goalProgress;
        Button goalReward;
    }

    public class GoalAdapter extends ArrayAdapter<GoalInfo>{

        private LayoutInflater mInflater = null;

        public GoalAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){return goalInfos.size();}

        @Override
        public View getView(int position, View view, ViewGroup parent){

            final GoalViewHolder viewHolder;
            final GoalInfo goalInfo = goalInfos.get(position);
            final GoalData goalData = dataList.getAllGoalData().get(position);

            if(view == null){

                view = mInflater.inflate(R.layout.goal_dialog_item,parent,false);

                viewHolder = new GoalViewHolder();

                viewHolder.goalImageView = (ImageView)view.findViewById(R.id.goalImage);
                viewHolder.goalName = (TextView)view.findViewById(R.id.goalName);
                viewHolder.goalExplain = (TextView)view.findViewById(R.id.goalExplain);
                viewHolder.goalCount = (TextView)view.findViewById(R.id.goalCount);
                viewHolder.goalProgress = (ProgressBar) view.findViewById(R.id.goalProgress);
                viewHolder.goalProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                viewHolder.goalReward = (Button)view.findViewById(R.id.goalReward);

                view.setTag(viewHolder);
            }
            else{
                viewHolder = (GoalViewHolder)view.getTag();
            }

            if(goalInfo != null){

                //후에 업적이미지로 변경
                viewHolder.goalImageView.setImageResource(R.drawable.image);

                viewHolder.goalName.setText(goalInfo.getGoalName());
                int resourceId = getContext().getResources().getIdentifier("goalCase" + goalInfo.getGoalType(), "string", getContext().getPackageName());

                if(goalData.getGoalLevel() == goalInfo.getGoalMaxLevel()+1){
                    viewHolder.goalReward.setVisibility(View.INVISIBLE);
                    viewHolder.goalCount.setVisibility(View.INVISIBLE);
                    viewHolder.goalProgress.setVisibility(View.INVISIBLE);
                    viewHolder.goalExplain.setVisibility(View.INVISIBLE);
                }
                else{
                    //14번째 업적(점수 계산 필요)
                    if(goalInfo.getId() == 13){
                        viewHolder.goalExplain.setText(String.format(getContext().getString(resourceId),dataList.getAllScore(goalData.getConditionType())));
                        //얻은 점수가 얻어야 하는 점수보다 클 떄
                        if(!dataList.calculateGoal(dataList.getGoalClick(),goalData.getConditionType())){
                            viewHolder.goalReward.setVisibility(View.VISIBLE);
                            viewHolder.goalCount.setVisibility(View.INVISIBLE);
                            viewHolder.goalProgress.setVisibility(View.INVISIBLE);

                            //보상받기
                            viewHolder.goalReward.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });

                        }else{ //얻은 점수가 아직 얻어야 하는 점수보다 작을 때
                            viewHolder.goalReward.setVisibility(View.INVISIBLE);
                            viewHolder.goalCount.setVisibility(View.VISIBLE);
                            viewHolder.goalProgress.setVisibility(View.VISIBLE);
                            viewHolder.goalCount.setText(dataList.getAllScore(dataList.getGoalClick())+" / "+dataList.getAllScore(goalData.getConditionType()));
                        }
                    }
                    else{ //14번쨰를 제외한 업적
                        viewHolder.goalExplain.setText(String.format(getContext().getString(resourceId),goalData.getGoalCondition()));
                        //얻은 점수가 얻어야 하는 점수보다 클 떄 -> 보상 받을 수 있음.
                        if(goalData.getGoalRate() == goalData.getGoalCondition()){
                            viewHolder.goalReward.setVisibility(View.VISIBLE);
                            viewHolder.goalCount.setVisibility(View.INVISIBLE);
                            viewHolder.goalProgress.setVisibility(View.INVISIBLE);

                            //보상받기
                            viewHolder.goalReward.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });

                        }else{ //얻은 점수가 아직 얻어야 하는 점수보다 작을 때
                            viewHolder.goalReward.setVisibility(View.INVISIBLE);
                            viewHolder.goalCount.setVisibility(View.VISIBLE);
                            viewHolder.goalProgress.setVisibility(View.VISIBLE);
                            viewHolder.goalCount.setText(goalData.getGoalRate()+" / "+goalData.getGoalCondition());
                        }
                    }
                }

            }

            return view;
        }
    }
}
