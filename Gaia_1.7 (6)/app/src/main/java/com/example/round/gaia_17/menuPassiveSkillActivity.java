package com.example.round.gaia_17;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.round.gaia_17.MainActivity.dryFlowerList;
import static com.example.round.gaia_17.MainActivity.flowerActivityArray;
import static com.example.round.gaia_17.MainActivity.fruitScore;
import static com.example.round.gaia_17.MainActivity.mGoalUserInfomation;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.scoreCalculaters;
import static com.example.round.gaia_17.MainActivity.updateSeed;
import static com.example.round.gaia_17.menuFlowerActivity.mFlowerArray;

/**
 * 김태우 패시브 스킬 구현.
 * 1. flowerActivityArray에서 키우는 꽃의 정보를 받아와 mFlowerArray에 넣고
 * 2. 패시브스킬(드라이꽃)을 설정하면 dryFlowerList에 있는 정보에맞게 꽃을 패시브 창에 넣어준다
 * 3. 꽃의 레벨은 1로 변한다.
 */

public class menuPassiveSkillActivity extends Fragment {

    private static final String TAG = ".PassiveSkillActivity";
    private ListView mList;
    private PassiveSkillAdapter mAdapter;


    //꽃정보 저장용
    private ArrayList<PassiveSkillFormatInform> mPassiveArray = new ArrayList<>();
    int bin_cost = 100;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_passive_skill_fragment,container,false);

        mPassiveArray.add(new PassiveSkillFormatInform(0,"","","","",1));
        mPassiveArray.add(new PassiveSkillFormatInform(1,"","","","",1));
        mPassiveArray.add(new PassiveSkillFormatInform(2,"","","","",0));
        mPassiveArray.add(new PassiveSkillFormatInform(3,"","","","",0));
        mPassiveArray.add(new PassiveSkillFormatInform(4,"","","","",0));
        mAdapter = new PassiveSkillAdapter(getContext(),R.layout.menu_passive_skill_item);
        mList = (ListView)view.findViewById(R.id.menuPassiveSkillList);
        mList.setAdapter(mAdapter);

        /*
        int size = flowerActivityArray.size();
        for(int i =0; i<size; i++){
            DB_Exception.Flower flowertemp = flowerActivityArray.get(i);
            if(flowertemp.getFlowerLevel() == 400){
                dryFlowerList.add(db.getPassiveSkillFormatInform(flowertemp.getFlowerLevel()));
            }
        }
        */


        return view;
    }


    // 드라이꽃 정보
    public static class DryFlower{
        private int id;
        private String name;
        private String explain;
        private String image;
        private int incScore;
        private int costPower;

        DryFlower(int id, String name, String explain, String image, int incScore, int costPower){
            this.id = id;
            this.explain = explain;
            this.image = image;
            this.name = name;
            this.incScore = incScore;
            this.costPower = costPower;
        }

        public int getId() {
            return id;
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
        public int getIncScore() {
            return incScore;
        }
        public int getCostPower(){ return costPower; }
    }

    // 패시브 정보
    public static class PassiveSkillFormatInform{
        private int id;
        private int buyType;
        private String name;
        private String explain;
        private String image;
        private String incScore;


        PassiveSkillFormatInform(int id, String name, String explain, String image, String incScore,int buyType){
            this.id = id;
            this.explain = explain;
            this.image = image;
            this.name = name;
            this.incScore = incScore;
            this.buyType = buyType;
        }

        public int getId() {
            return id;
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
        public String getIncScore() {
            return incScore;
        }
        public int getBuyType() {
            return buyType;
        }

        public void setBuyType(int buyType) {
            this.buyType = buyType;
        }
        public void setExplain(String explain) {
            this.explain = explain;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setImage(String image) {
            this.image = image;
        }
        public void setIncScore(String incScore) {
            this.incScore = incScore;
        }
        public void setName(String name) {
            this.name = name;
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
    public class PassiveSkillAdapter extends ArrayAdapter<PassiveSkillFormatInform> {
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
            final PassiveSkillFormatInform info = mPassiveArray.get(position);

            Log.i(TAG, "이름 : " + info.getName() + " , 구매 상태 : " + info.buyType);
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
                    //usePassiveSkill(arrSocre[info.getInsScore()]);
                    viewHolder.skillImage.setVisibility(View.VISIBLE);
                    viewHolder.skillNameText.setVisibility(View.VISIBLE);
                    viewHolder.skillEffectText.setVisibility(View.VISIBLE);
                    viewHolder.addInfomationButton.setVisibility(View.VISIBLE);

                    // 각 메소스 값 설정
                    v.setBackgroundResource(R.drawable.shape);
                    v.setOnClickListener(null);
                    viewHolder.skillImage.setImageResource(R.drawable.image);
                    viewHolder.skillNameText.setText(info.getName());
                    viewHolder.skillEffectText.setText(info.getIncScore());
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
                                    dialog.setname(info.getName());
                                    dialog.setimage(info.getImage());
                                    dialog.setexplain(info.getExplain());
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
                                    if(dialog.getDiaid()!=-1) {
                                        int entryid = dialog.getDiaid();
                                        mPassiveArray.set(info.getId(),new PassiveSkillFormatInform(info.getId(),dryFlowerList.get(entryid).getName(),
                                                dryFlowerList.get(entryid).getImage(), dryFlowerList.get(entryid).getExplain(),
                                                "초당 "+ scoreCalculaters(dryFlowerList.get(entryid).getIncScore(),dryFlowerList.get(entryid).getCostPower()) + "획득.",2));
                                       // 9.20 김태우 업적 증가코드
                                        mGoalUserInfomation.setGoalNumberArr(10,1);
                                        mAdapter.notifyDataSetChanged();
                                        // 꽃정보 초기화
                                        flowerActivityArray.get(entryid).setFlowerLevel(1);
                                        mFlowerArray.get(entryid).setFlowerLvi(1);
                                        //flowerActivityArray.notifyAll();
                                        dryupScore((dryFlowerList.get(entryid).getIncScore()*1000));

                                    }
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
                        public void onClick(final View v) {
                            final int id = info.getId();

                            final menuPassiveSkilllistBuyDialog dialog = new menuPassiveSkilllistBuyDialog(getContext());
                            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                @Override
                                public void onShow(DialogInterface dialogInterface) {
                                    dialog.setBuyCost(bin_cost,fruitScore);
                                }
                            });
                            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dia) {

                                    if(dialog.num==1) {
                                        Log.i("id",""+id);
                                        mPassiveArray.set(id, new PassiveSkillFormatInform(id, "", "", "", "",1));
                                        fruitScore = fruitScore - bin_cost;
                                        MainActivity.updateFruit(fruitScore);
                                        mAdapter.notifyDataSetChanged();
                                    }else {
                                        final falseDialog dialog = new falseDialog(getContext());
                                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                                            @Override
                                            public void onShow(DialogInterface dialogInterface) {
                                                dialog.setMassageBoxA1("Fruit 가 부족합니다.");
                                                dialog.setMassageBoxA2("Fruit을 더 구매해 주세요");
                                            }
                                        });
                                    }
                                }
                            });
                            dialog.show();
                        }
                    });

                }
            }

            return v;
        }
    }

    final Handler handler = new Handler();
    int timer_sec = 0;
    public void dryupScore(final int scores){
        timer_sec = 0;
        TimerTask second;
        final Timer timer = new Timer();

        second = new TimerTask() {
            @Override
            public void run() {
                Log.i("Timer ::", "dryup!");
                timer_sec++;
                dryupScoreUpdate(scores);
            }
        };
        timer.schedule(second, 0, 1000);
    }
    public void dryupScoreUpdate(final int upSco) {

        Runnable updater = new Runnable() {

            public void run() {

                Log.i("Timer ::", "dryup!"+ ""+upSco+" / "+ score+upSco);
                score = score + upSco;
                updateSeed(score);
            }

        };

        handler.post(updater);

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