package com.example.round.gaia_17;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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

import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.menuFlowerActivity.mFlowerArray;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuPassiveSkillActivity extends Fragment {

    private static final String TAG = ".PassiveSkillActivity";
    private ListView mList;
    private PassiveSkillAdapter mAdapter;
    private ArrayList<PassiveSkillInform> mPassiveArray = new ArrayList<>();
    // 임시로 꽃정보 저장용
    private FlowerInfomations[] flowerInfomations = new FlowerInfomations[3];

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_passive_skill_fragment,container,false);

        mAdapter = new PassiveSkillAdapter(getContext(),R.layout.menu_passive_skill_item);
        mList = (ListView)view.findViewById(R.id.menuPassiveSkillList);
        mList.setAdapter(mAdapter);

        // 임시로 꽃정보 저장용
        flowerInfomations[0] = new FlowerInfomations("flower1","imaga1","imaga1");
        // 스킬정보 저장 임시
        mPassiveArray.add(new PassiveSkillInform(0,"이미지1","스킬명1","초당 seed 50", 2));
        mPassiveArray.add(new PassiveSkillInform(1,"","","", 1));
        mPassiveArray.add(new PassiveSkillInform(2,"","","", 0));



        /*
        for(int i =0;i<mPassiveArray.size();i++){
            for(int j =0;j<plantArray.size();j++){
                if(plantArray.get(j).getId() == mPassiveArray.get(i).getId()){
                    mPassiveArray.get(i).setBuyType(1);
                }
            }
        }
        */

        return view;
    }



    // 패시브 스킬 정보 포멧
    public class PassiveSkillInform{

        private int id;
        private String passiveSkillImagePath;
        private String passiveSkillName;
        private String passiveSkilleffect;
        private int buyType;

        public PassiveSkillInform(int id, String passiveSkillImagePath, String passiveSkillName, String passiveSkilleffect, int buyType){
            this.id = id;
            this.passiveSkillImagePath = passiveSkillImagePath;
            this.passiveSkilleffect = passiveSkilleffect;
            this.passiveSkillName = passiveSkillName;
            this.buyType = buyType;
        }

        public int getId(){return this.id;}
        public String getPassiveSkillImagePath(){return this.passiveSkillImagePath;}
        public String getPassiveSkilleffect(){return this.passiveSkilleffect;}
        public String getPassiveSkillName(){return this.passiveSkillName;}
        public int getBuyType(){return this.buyType;}

        public void setBuyType(int buyType){
            this.buyType = buyType;
        }
    }

    // 패시브 스킬창 리스트 포멧
    static class PassiveSkillViewHolder{
        ImageView skillImage;
        TextView skillNameText;
        TextView skillEffectText;
        ImageButton addInfomationButton;
    }

    // 패시브 스킬 리스트 어뎁터
    public class PassiveSkillAdapter extends ArrayAdapter<PassiveSkillInform> {
        private LayoutInflater mInflater = null;

        public PassiveSkillAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mPassiveArray.size();
        }

        @Override
        public View getView(int position, View v, final ViewGroup parent) {
            final PassiveSkillViewHolder viewHolder;
            final PassiveSkillInform info = mPassiveArray.get(position);

            Log.i(TAG, "이름 : " + info.getPassiveSkillName() + " , 구매 상태 : " + info.buyType);
            if (v == null) {
                v = mInflater.inflate(R.layout.menu_passive_skill_item, parent, false);
                viewHolder = new PassiveSkillViewHolder();
                viewHolder.skillImage = (ImageView) v.findViewById(R.id.skillImage);
                viewHolder.skillNameText = (TextView) v.findViewById(R.id.skillNameText);
                viewHolder.skillEffectText = (TextView) v.findViewById(R.id.skillEffectText);
                viewHolder.addInfomationButton = (ImageButton) v.findViewById(R.id.addInfomationButton);
                v.setTag(viewHolder);
            } else {
                viewHolder = (PassiveSkillViewHolder) v.getTag();
            }

            if (info != null) {
                //buyType ==0 // 잠긴이미지  //1 사용가능이미지 // 2 사용중이미지
                if (info.buyType == 2) {
                    // 각 메소드 활성화
                    viewHolder.skillImage.setVisibility(View.VISIBLE);
                    viewHolder.skillNameText.setVisibility(View.VISIBLE);
                    viewHolder.skillEffectText.setVisibility(View.VISIBLE);
                    viewHolder.addInfomationButton.setVisibility(View.VISIBLE);

                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.shape);
                    v.setOnClickListener(null);
                    viewHolder.skillImage.setImageResource(R.drawable.image);
                    viewHolder.skillNameText.setText(info.getPassiveSkillName());
                    viewHolder.skillEffectText.setText(info.getPassiveSkilleffect());
                    viewHolder.addInfomationButton.setImageResource(R.drawable.info);

                    // 필요 메소드 이밴트
                    //버튼 이밴트
                    viewHolder.addInfomationButton.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            final int id = info.getId();

                            final menuPassiveSkillInfomationDialog dialog = new menuPassiveSkillInfomationDialog(getContext());
                            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    dialog.setname(flowerInfomations[id].getName());
                                    Log.i("path", "::" + flowerInfomations[id].getImage());
                                    dialog.setimage(flowerInfomations[id].getImage());
                                    dialog.setexplain(flowerInfomations[id].getExplain());
                                }
                            });
                            dialog.show();
                        }
                    });
                } else if (info.buyType == 1) {
                    // 각 메소드 활성화
                    viewHolder.skillImage.setVisibility(View.VISIBLE);
                    viewHolder.skillNameText.setVisibility(View.INVISIBLE);
                    viewHolder.skillEffectText.setVisibility(View.INVISIBLE);
                    viewHolder.addInfomationButton.setVisibility(View.INVISIBLE);

                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.lock_background);
                    v.setOnClickListener(null);
                    viewHolder.skillImage.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    // 필요 메소드 이밴트
                    //버튼 이밴트
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(final View v) {
                            final menuPassiveSkillFlowerChoiceDialog dialog = new menuPassiveSkillFlowerChoiceDialog(getContext());
                            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                }
                            });
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dia) {
                                    int entryid = dialog.getDiaid();
                                    menuFlowerActivity.FlowerInform flowetTemp =  mFlowerArray.get(entryid);
                                    mPassiveArray.set(info.getId(), new PassiveSkillInform(entryid, flowetTemp.getFlowerImagePath(),
                                            flowetTemp.getFlowerName(), String.valueOf(flowetTemp.getFlowerLv()), 2));
                                    mAdapter.notifyDataSetChanged();
                                }
                            });
                            dialog.show();
                        }
                    });
                } else {
                    // 각 메소드 활성화
                    viewHolder.skillImage.setVisibility(View.INVISIBLE);
                    viewHolder.skillNameText.setVisibility(View.INVISIBLE);
                    viewHolder.skillEffectText.setVisibility(View.INVISIBLE);
                    viewHolder.addInfomationButton.setVisibility(View.INVISIBLE);

                    // 각 메소스 값 설정
                    v.setOnClickListener(null);
                    v.setBackgroundResource(R.drawable.lock_map);

                    // 필요 메소드 이밴트
                    //버튼 이밴트
                    v.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            final int id = info.getId();
                        }
                    });
                }
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

    // 임시로 꽃정보 넣을려고 만듬
    public class FlowerInfomations{
        private String name;
        private String explain;
        private String image;

        FlowerInfomations(){}
        FlowerInfomations(String name, String explain, String image){
            this.explain = explain;
            this.image = image;
            this.name = name;
        }

        public String getExplain() {
            return explain;
        }
        public String getImage() {
            return image;
        }
        public String getName() {
            return name;
        }
    }
}

