package com.example.round.gaia_17;

import android.app.Dialog;
import android.content.Context;
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

import java.util.ArrayList;

/**
 * Created by 리제 on 2017-09-04.
 */

public class goalListDialog extends Dialog{
    public goalListDialog(Context context){super(context);}

    private static final String TAG = ".GoalActivity";
    private ListView mList;
    private GoalAdapter mAdapter;
    private ArrayList<GoalInform> mGoalArray = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_goal_list);

        mAdapter = new GoalAdapter(getContext(),R.layout.menu_passive_skill_item);
        mList = (ListView)findViewById(R.id.dialogGoalList);
        mList.setAdapter(mAdapter);

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

    // 꽃선택창 리스트 포멧
    static class GoalViewHolder{
        ImageView goalImageView;
        TextView goalNameText;
        TextView goalContentText;
        TextView goalCountText;
        ImageButton goalReward;
        ProgressBar goalProgressBar;
    }

    // 꽃선택창 리스트 어뎁터
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


            final GoalInform info = mGoalArray.get(position);

            Log.i(TAG,"이름 : "+info.getGoalName());
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
                viewHolder.goalNameText.setText(info.getGoalName());
                viewHolder.goalContentText.setText(info.getGoalContent());
                viewHolder.goalCountText.setText(info.getGoalCount());
                viewHolder.goalReward.setImageResource(R.mipmap.ic_launcher);
                viewHolder.goalReward.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),"업적달성",Toast.LENGTH_LONG).show();
                    }
                });
            }

            return v;
        }

    }
}
