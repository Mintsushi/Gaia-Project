package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Data.SkillData;
import com.example.round.gaia_18.Data.SkillInfo;
import com.example.round.gaia_18.Data.TabData;
import com.example.round.gaia_18.MemUtils;
import com.example.round.gaia_18.R;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.Data.DataList.mAdapter;
import static com.example.round.gaia_18.MainActivity.fruit;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.OverlayService.dataList;
import static com.example.round.gaia_18.OverlayService.weatherData;

public class MenuSkill extends Fragment {

    private static final String TAG = ".ActiveSkillActivity";
    //skill을 띄어줄 list
    private ListView skillList;
    private static final float BYTES_PER_PX = 4.0f;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        //Fragement 화면 활성화
        View view = inflater.inflate(R.layout.menu_skill_fragment, container,false);

        mAdapter = new SkillAdapter(getContext(),R.layout.menu_skill_item);
        skillList = (ListView)view.findViewById(R.id.menuSkillList);
        skillList.setAdapter(mAdapter);

        return view;
    }

    public class SkillViewHolder{
        LinearLayout background;
        ImageView skillImage;
        ProgressBar skillExpBar;
        TextView skillLevel;
        TextView skillName;
        TextView coolTime;
        RelativeLayout skillUse;
        ImageView skillUseButton;
        LinearLayout skillLevelUp;
        ImageView skillLevelUpType;
        TextView skillLevelUpScore;
        TextView skillExplain;
    }

    public class SkillAdapter extends ArrayAdapter<SkillInfo> {
        private LayoutInflater mInflater = null;

        public SkillAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() { return dataList.getSkillInfos().size(); }

        @Override
        public View getView(int position, View view, ViewGroup parent) {

            SkillInfo skillInfo = dataList.getSkillInfos().get(position);
            SkillData skillData = null;
            TabData tabData = null;
            int buyType = 0;
            if(skillInfo.getSkillNo() == 0){
                tabData = dataList.getTabData();
                buyType = tabData.getBuyType();
            }else{
                skillData = dataList.getSkillDatas().get(position);
                buyType = skillData.getbuyType();
            }
            final SkillViewHolder skillViewHolder;

            Log.i("onResume","skill id : "+skillInfo.getSkillNo()+" / skill Use State : "+skillInfo.getSkillUseState());
            if (skillInfo.getSkillView() == null) {

                view = mInflater.inflate(R.layout.menu_skill_item, parent, false);
                skillViewHolder = new SkillViewHolder();

                skillViewHolder.background = (LinearLayout) view.findViewById(R.id.skillBackground);
                skillViewHolder.skillImage = (ImageView) view.findViewById(R.id.skillImage);
                skillViewHolder.skillExpBar = (ProgressBar) view.findViewById(R.id.skillExp);
                skillViewHolder.skillLevel = (TextView) view.findViewById(R.id.skillLevel);
                skillViewHolder.skillName = (TextView) view.findViewById(R.id.skillName);
                skillViewHolder.coolTime = (TextView) view.findViewById(R.id.skillCoolTime);
                skillInfo.setSkillCoolTime(skillViewHolder.coolTime);
                skillInfo.setSkillCoolTimeInApp(skillViewHolder.coolTime);
                skillViewHolder.skillUse = (RelativeLayout) view.findViewById(R.id.skillUse);
                skillViewHolder.skillUseButton = (ImageView) view.findViewById(R.id.skillUseButton);
                skillViewHolder.skillLevelUp = (LinearLayout) view.findViewById(R.id.skillLevelUpButton);
                skillViewHolder.skillLevelUpType = (ImageView) view.findViewById(R.id.skillLevelUpType);


                skillViewHolder.skillLevelUpScore = (TextView) view.findViewById(R.id.skillLevelUpScore);
                skillViewHolder.skillExplain = (TextView) view.findViewById(R.id.skillExplain);

                if(skillInfo.getSkillNo() == 0){
                    skillViewHolder.skillUseButton.setTag(tabData.getSkillNo());
                    skillViewHolder.skillLevelUp.setTag(tabData.getSkillNo());
                }else{
                    skillViewHolder.skillUseButton.setTag(skillData.getSkillNo());
                    skillViewHolder.skillLevelUp.setTag(skillData.getSkillNo());
                }

                view.setTag(skillViewHolder);
                skillInfo.setSkillView(view);

            } else {
                Log.i("SkillViewHolder","skill ViewHolder Setting");
                skillViewHolder = (SkillViewHolder)skillInfo.getSkillView().getTag();
                skillInfo.setSkillCoolTime(skillViewHolder.coolTime);
            }


            if (skillInfo != null && skillInfo.getSkillDataChange()) {

                skillInfo.setSkillDataChange(false);
                //후에 skill Image로 변경
                int resourceId = getContext().getResources().getIdentifier("skill" + skillInfo.getSkillNo(), "drawable", getContext().getPackageName());
                loadImage(skillViewHolder.skillImage,resourceId);
//                skillViewHolder.skillImage.setImageResource(R.drawable.image);
                skillViewHolder.skillName.setText(skillInfo.getSkillName());
                resourceId = getContext().getResources().getIdentifier("skillCase" + skillInfo.getSkillCase(), "string", getContext().getPackageName());

                skillViewHolder.skillLevelUp.setBackgroundResource(R.drawable.buy_background);

                if (skillInfo.getSkillNo() == 0) {
                    skillViewHolder.skillExpBar.setProgress(tabData.getSkillLevel() * 4);
                    skillViewHolder.skillLevel.setText(Integer.toString(tabData.getSkillLevel()));
                    skillViewHolder.skillExplain.setText(String.format(getString(resourceId),dataList.getAllScore(tabData.getScore())));

                } else {
                    skillViewHolder.skillExpBar.setProgress(skillData.getSkillLevel() * 4);
                    skillViewHolder.skillLevel.setText(Integer.toString(skillData.getSkillLevel()));
                    skillViewHolder.skillExplain.setText(String.format(getString(resourceId), skillData.getSkillEffect()));
                }

                if (buyType == 1) {
//                    skillViewHolder.skillLevelUp.setImageResource(R.drawable.fruit);
                    loadImage(skillViewHolder.skillLevelUpType,R.drawable.reward1);
                } else {
//                    skillViewHolder.skillLevelUp.setImageResource(R.drawable.seed);
                    loadImage(skillViewHolder.skillLevelUpType,R.drawable.reward2);
                }

                if (skillInfo.getSkillNo() == 0) {

                    if(tabData.getSkillLevel() == skillInfo.getSkillMaxLevel()){
                        skillViewHolder.skillLevelUpType.setImageResource(R.drawable.complete);
                        skillViewHolder.background.setBackgroundResource(R.drawable.max_level_background);
                        skillViewHolder.skillLevelUpScore.setText("*MAX*");
                    }else {//skill이 최대 레벨이 아닐때
                        skillViewHolder.skillLevelUpScore.setText("X"+dataList.getAllScore(tabData.getCost()));
                        skillViewHolder.background.setBackgroundResource(R.drawable.flower_buy_available);
                    }

                    skillViewHolder.skillUse.setVisibility(View.INVISIBLE);
                    skillViewHolder.skillLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SkillInfo skillInfo = dataList.getSkillInfos().get((int)view.getTag());
                            TabData tabData = dataList.getTabData();

                            if(buyForScore(tabData.getCost())) {
                                dataList.tabSkillLevelUp();
                                seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));

                                skillInfo.setSkillDataChange(true);

                                if (dataList.getGoalDataByID(8).getGoalRate() < dataList.getGoalDataByID(8).getGoalCondition()) {
                                    dataList.getGoalDataByID(8).setGoalRate(1);
                                }

                                mAdapter.notifyDataSetChanged();
                            }else{
                                //이 부분은 좀 더 시각적으로 표현하자
                                Toast.makeText(getActivity(), "Score가 부족합니다!!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    //skill을 구입함.
                    if (skillData.getSkillBuy()) {

                        //skill이 최대 레벨일 때
                        if (skillData.getSkillLevel() == skillInfo.getSkillMaxLevel()) {
                            skillViewHolder.skillLevelUpType.setImageResource(R.drawable.complete);
                            skillViewHolder.background.setBackgroundResource(R.drawable.max_level_background);
                            skillViewHolder.skillLevelUpScore.setText("*MAX*");
                        } else {//skill이 최대 레벨이 아닐때
                            skillViewHolder.skillLevelUpScore.setText("X"+dataList.getAllScore(skillData.getCost()));
                            skillViewHolder.background.setBackgroundResource(R.drawable.flower_buy_available);
                        }

                        //스킬이 cooltime으로 인해 사용이 불가한 경우
                        if (skillInfo.getSkillUseState()) {
                            skillViewHolder.coolTime.setVisibility(View.VISIBLE);
                            skillViewHolder.skillUseButton.setVisibility(View.INVISIBLE);
                            skillViewHolder.skillUseButton.setOnClickListener(null);
                            skillViewHolder.background.setBackgroundResource(R.drawable.flower_buy_item);
                        } else {
                            skillViewHolder.coolTime.setVisibility(View.INVISIBLE);
                            //지속스킬일 경우
                            if (skillInfo.getPassive() == 1) {
                                skillViewHolder.skillUse.setVisibility(View.INVISIBLE);
                            } else { //지속스킬이 아닐 경우
//                                skillViewHolder.skillUseButton.setImageResource(R.drawable.use);
                                skillViewHolder.skillUseButton.setVisibility(View.VISIBLE);
                                skillViewHolder.skillUse.setVisibility(View.VISIBLE);

                                skillViewHolder.skillUseButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        int id = (int) view.getTag();

                                        SkillInfo skillInfo = dataList.getSkillInfos().get(id);
                                        SkillData skillData = dataList.getSkillDatas().get(id);

                                        // 비가 오지않을경우 에러포기
                                        if(weatherData.get(0)!=1 && skillInfo.getSkillNo() == 6){
                                            Toast.makeText(getContext(), "비가 오지 않았어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        skillViewHolder.coolTime.setVisibility(View.VISIBLE);
                                        mOverlayService.skillCoolTime.skillCoolTime(skillInfo);

                                        skillInfo.setSkillUseState(true);
                                        //Skill Cool Time동안은 사용 불가능
                                        skillViewHolder.skillUseButton.setVisibility(View.INVISIBLE);
                                        skillViewHolder.background.setBackgroundResource(R.drawable.flower_buy_item);

                                        useSkill(skillInfo.getSkillCase(), skillData.getSkillEffect());

                                        Log.i("skillInfo.getSkillNo()",""+skillInfo.getSkillNo());
                                        // 수식 에러로 터져서 주석처리
                                        // 업적에서 터지고. 이유는 업적 2개(지속스킬 사용횟수)의 삭제로 밀려서 그렇게 됨.]
                                        if(6==skillInfo.getSkillNo()){
                                            if (dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 3).getGoalRate() < dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 3).getGoalCondition()) {
                                                dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 3).setGoalRate(1);
                                            }
                                        }else{
                                            if (dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalRate() < dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalCondition()) {
                                                dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).setGoalRate(1);
                                            }
                                        }
                                    }
                                });
                            }
                        }
                    } else {//skill을 구입하지 않음.

                        skillViewHolder.skillUseButton.setVisibility(View.INVISIBLE);
                        skillViewHolder.background.setBackgroundResource(R.drawable.flower_item_lock);
                        skillViewHolder.skillUse.setVisibility(View.INVISIBLE);
                        skillViewHolder.skillLevelUpScore.setText("X"+dataList.getAllScore(skillData.getCost()));
                    }

                    skillViewHolder.skillLevelUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = (int) view.getTag();

                            SkillInfo skillInfo = dataList.getSkillInfos().get(id);
                            SkillData skillData = dataList.getSkillDatas().get(id);

                            if (skillData.getSkillLevel() + 1 > skillInfo.getSkillMaxLevel()) {
                                Toast.makeText(getActivity(), "최대 레벨입니다!!!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //게임 재화로 구입
                            if (skillData.getbuyType() == 2) {
                                if (buyForScore(skillData.getCost())) { //구입완료
                                    dataList.replaceSkillData(skillData.getSkillNo(), skillData.getSkillLevel() + 1);
                                    seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));

                                    skillInfo.setSkillDataChange(true);

                                    if (dataList.getGoalDataByID(16 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalRate() < dataList.getGoalDataByID(16 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalCondition()) {
                                        dataList.getGoalDataByID(16 + 2 * (skillInfo.getSkillNo() - 1) - 1).setGoalRate(1);
                                    }

                                    mAdapter.notifyDataSetChanged();
                                } else { //재화부족으로 구매 실패
                                    //이 부분은 좀 더 시각적으로 표현하자
                                    Toast.makeText(getActivity(), "Score가 부족합니다!!!", Toast.LENGTH_SHORT).show();
                                }
                            }  else {//현금성 재화로 구입
                                if (buyForFruit(skillData.getCost())) {
                                    dataList.replaceSkillData(skillData.getSkillNo(), skillData.getSkillLevel() + 1);
                                    fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));

                                    skillInfo.setSkillDataChange(true);

                                    if(skillData.getSkillNo() != 4 && skillData.getSkillNo() != 5) {
                                        // 수식 에러로 터져서 주석처리
                                        // 업적에서 터지고. 이유는 업적 2개(지속스킬 사용횟수)의 삭제로 밀려서 그렇게 됨.
                                        if(6==skillInfo.getSkillNo()){
                                            Log.i("skill test",""+skillInfo.getSkillNo());
                                            if(dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-3).getGoalRate() <  dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-3).getGoalCondition()){

                                                Log.i("skill test",""+(16+2*(skillInfo.getSkillNo()-1)-3));
                                                dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-3).setGoalRate(1);
                                            }
                                        }else{
                                            if(dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-1).getGoalRate() <  dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-1).getGoalCondition()){
                                                dataList.getGoalDataByID(16+2*(skillInfo.getSkillNo()-1)-1).setGoalRate(1);
                                            }
                                        }

                                    }else{
                                        Log.i("BuySkill","skill id : "+dataList.getGoalDataByID(18 + skillData.getSkillNo()-1).getGoalNo()+" / skillGoalRate : "+dataList.getGoalDataByID(18 + skillData.getSkillNo()-1).getGoalRate());

                                        if (dataList.getGoalDataByID(18 + skillData.getSkillNo()-1).getGoalRate() < dataList.getGoalDataByID(18 + skillData.getSkillNo()-1).getGoalCondition()) {
                                            dataList.getGoalDataByID(18 + skillData.getSkillNo() - 1).setGoalRate(1);
                                        }

                                    }

                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    //이 부분은 좀 더 시각적으로 표현하자
                                    Toast.makeText(getActivity(), "Fruit이 부족합니다!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
                }
            }
            return skillInfo.getSkillView();
        }
    }

    private void useSkill(int skillType, int effect){

        switch (skillType){

            //스킬 유형1 : 일정 시간 얻는 점수 2배
            case 0:
                mOverlayService.type0.startSkill(skillType,effect);
                break;
            //스킬 유형2 : 점수 획득
            case 1:
                dataList.startSkill_type1(effect,0);
                seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
                mOverlayService.setSeed();
                break;
            //스킬 유형3 : 초당 10회 자동 탭
            case 2:
                mOverlayService.type2.startSkill(skillType,effect);
                break;
            //스킬 유형3 : 탭 당 점수 증가
//            case 3:break;
//            //스킬 유형4 : 분당 일정 획수 자동 탭
//            case 4:break;

            //스킬 유형6 : 날씨가 비 일시 일정량의 물 획득
            case 5:

                Log.i("weaterData",""+ effect);
                Log.i("weaterData",""+weatherData.get(0));
                // 날씨정보 얻어오기.
                if(weatherData.get(1)==1) {
                    dataList.setIncItemNumber(3, effect);
                    Toast.makeText(getContext(), "빗물을 열심히 받았어요!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "비가 오지 않았어요 ㅠㅠ", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //게임 재화로 구매
    private Boolean buyForScore(ConcurrentHashMap<Integer, Integer> seed){

        Iterator<Integer> iterator = seed.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = seed.get(key);

            if(!dataList.minusScore(key,value,dataList.getScoreHashMap())){
                return false;
            }
        }
        return true;
    }

    //현금성 재화로 구매
    private Boolean buyForFruit(ConcurrentHashMap<Integer,Integer> fruit){

        Iterator<Integer> iterator = fruit.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = fruit.get(key);

            if(!dataList.minusScore(key,value,dataList.getFruitHashMap())){
                return false;
            }
        }

        return true;
    }

    private void loadImage(ImageView image,int resourceId){
        if(readBitmapInfo(resourceId) * 100> MemUtils.megabytesAvailable()){
            Log.i("LoadImage","Big Image");
            subImage(32,resourceId,image);
        }else{
            Log.i("LoadImage","Small Image");
            image.setImageResource(resourceId);
        }
    }

    private float readBitmapInfo(int resourceId){
        final Resources res = getContext().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res,resourceId,options);

        final float imageHeight = options.outHeight;
        final float imageWidth = options.outWidth;
        final String imageMimeType = options.outMimeType;

        return imageWidth*imageHeight*BYTES_PER_PX / MemUtils.BYTE_IN_MB;
    }

    private void subImage(int powerOf2,int resourceId,ImageView image){
        if(powerOf2 < 1 || powerOf2 > 32){
            return;
        }

        final Resources res = getContext().getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = powerOf2;

        final Bitmap bitmap = BitmapFactory.decodeResource(res,resourceId,options);
        image.setImageBitmap(bitmap);
    }
}
