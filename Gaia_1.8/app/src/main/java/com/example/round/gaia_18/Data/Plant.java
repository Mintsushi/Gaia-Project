package com.example.round.gaia_18.Data;

import android.content.Context;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.MainActivity;
import com.example.round.gaia_18.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.OverlayService.dataList;

/**
 * Created by Round on 2017-09-06.
 */

public class Plant{
    private static final int MAX_HP = 100;

    private int plantNo;
    private int level;
    private Flower flower;
    private RelativeLayout plantLayout;
    private ImageView plant;
    private int hp;
    private Timer timer = new Timer();
    private ListView itemList;
    // 김태우 물주기추가, 프래그먼트 추가, 버튼추가
    private ProgressBar plantHP;
    private ImageButton plantItemBtn;
    private ImageButton itemListRemoveButton;
    private ImageView plantWater;
    private final ArrayList<Water> waterInfo;

    private Timemer timemer;
    //state == 0 : overlayview에 없음
    //state == 1 : overlayview에 있음
    private int state;
    //waterState == 0 : 물을 준 상태
    //waterState == 1 : 물을 주지 않은 상태
    private int waterState;

    //현재 날씨로 얻을 수 있는 패널티 / 패시브
    private int effect;
    private Boolean dryFlowerSetting = false;

    //외부에서 얻을 수 있는 점수
    private ConcurrentHashMap<Integer, Integer> overlayScore = new ConcurrentHashMap<>();

    //위치 이동
    private Boolean moving = false;
    private int originalXPos, originalYPos;

    public Plant(int plantNo, int level, Flower flower, ImageView plant, RelativeLayout plantLayout, int hp) {

        this.plantNo = plantNo;
        this.level = level;
        this.flower = flower;
        this.plant = plant;
        this.plantLayout = plantLayout;
        this.hp = hp;
        this.waterState = 0;
        waterInfo = DataList.getWaters();
        this.timemer = new Timemer(waterInfo.get(plantNo).getWaterPeriod(),waterInfo.get(plantNo).getWaterPenaltyTime());


        //물달라는 이미지뷰
        plantWater = new ImageView(MainActivity.context);
        plantWater.setImageResource(R.drawable.reward4);
        plantWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 물의 개수가 물을 줄수있을만큼 있을때.
                if( dataList.getItemNumber(3) >= waterInfo.get(getPlantNo()).getWaterNeedWaterNum()) {
                    Toast.makeText(MainActivity.context, "물 주기 성공", Toast.LENGTH_LONG).show();
                    dataList.setDesItemNumber(3,waterInfo.get(getPlantNo()).getWaterNeedWaterNum());
                    setWaterState(0);
                    getPlantWater().setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(MainActivity.context, "물 주기 실패 ㅠㅠ", Toast.LENGTH_LONG).show();

                }
            }
        });
        // 위치세팅
        RelativeLayout.LayoutParams waterParams = new RelativeLayout.LayoutParams(100, 100);
        int[] waterLocation = new int[2];
        plantLayout.getLocationOnScreen(waterLocation);
        waterParams.leftMargin = 100;
        waterParams.topMargin = 0;
        plantLayout.addView(plantWater,waterParams);


        // 체력바
        plantHP = new ProgressBar(MainActivity.context, null, android.R.attr.progressBarStyleHorizontal);
        plantHP.setMax(MAX_HP); // 최대체력 100
        plantHP.setProgress(getHp());

        // 위치세팅
        RelativeLayout.LayoutParams hpParams = new RelativeLayout.LayoutParams(200, 40);
        int[] hpLocation1 = new int[2];
        plantLayout.getLocationOnScreen(hpLocation1);
        hpParams.leftMargin = 30;
        hpParams.topMargin = 350;
        plantLayout.addView(plantHP, hpParams);

        // 아이템버튼
        plantItemBtn = new ImageButton(MainActivity.context);
        plantItemBtn.setImageResource(R.drawable.open);

        // 위치세팅
        final RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(60, 60);
        int[] btnLocation = new int[2];
        plantLayout.getLocationOnScreen(btnLocation);
        btnParams.leftMargin =230;
        btnParams.topMargin = 350;
        plantLayout.addView(plantItemBtn,btnParams);

        plantItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //아이템 리스트 만들어 주는 함수
                open_item_list();
            }
        });
    }

    public void open_item_list(){
        // 미니 아이템 리스트 구현
        itemList = new ListView(MainActivity.context);
        MiniItemAdapter mAdapter = new MiniItemAdapter(MainActivity.context, R.layout.mini_item);
        itemList.setAdapter(mAdapter);

        final RelativeLayout.LayoutParams listParams = new RelativeLayout.LayoutParams(300, 400);
        int[] listLocation = new int[2];
        plantLayout.getLocationOnScreen(listLocation);
        listParams.leftMargin =listLocation[0]+ 220;
        listParams.topMargin = listLocation[1] - 300;
        relativeLayout.addView(itemList,listParams);

        // 리스트 종료버튼.. 클릭시 리스트와 버튼 둘다 레이아웃에서 제거
        itemListRemoveButton = new ImageButton(MainActivity.context);
        itemListRemoveButton.setImageResource(R.drawable.close);
        itemListRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relativeLayout.removeView(itemList);
                relativeLayout.removeView(itemListRemoveButton);
            }
        });

        final RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(40, 40);
        int[] btnLocation = new int[2];
        plantLayout.getLocationOnScreen(btnLocation);
        btnParams.leftMargin =btnLocation[0]+ 520;
        btnParams.topMargin = btnLocation[1] - 310;
        relativeLayout.addView(itemListRemoveButton,btnParams);
    }

    // 아이템 리스트뷰 포멧
    public class MiniItemViewHolder {
        ImageView miniItemImage;
        TextView miniItemNameText;
        TextView miniItemNumText;

    }

    // 아이템 리스트 어뎁터
    public class MiniItemAdapter extends ArrayAdapter<StoreProduct> {
        private LayoutInflater mInflater = null;

        public MiniItemAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dataList.getMiniItemListProducts().size();
        }

        @Override
        public View getView(int position, View v, final ViewGroup parent) {
            final MiniItemViewHolder viewHolder;
            final StoreProduct info = dataList.getMiniItemListProducts().get(position);
            if (v == null) {
                //뷰 활성화
                v = mInflater.inflate(R.layout.mini_item, parent, false);
                viewHolder = new MiniItemViewHolder();
                viewHolder.miniItemImage = (ImageView) v.findViewById(R.id.miniItemImage);
                viewHolder.miniItemNameText = (TextView) v.findViewById(R.id.miniItemNameText);
                viewHolder.miniItemNumText = (TextView) v.findViewById(R.id.miniItemNumText);

                v.setTag(viewHolder);
            } else {
                viewHolder = (MiniItemViewHolder) v.getTag();
            }

            if (info != null) {
                // 뷰에 이미지 그리기
                viewHolder.miniItemImage.setImageResource(R.drawable.reward3);
                viewHolder.miniItemNameText.setText(info.getItemName());
                viewHolder.miniItemNumText.setText("수량 : "+dataList.getItemNumber(info.getItemId() -1));
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // 아이템 사용시 효과발동 및 리스트와 리스트종료버튼 삭제
                        useItem(info.getItemId());
                        relativeLayout.removeView(itemList);
                        relativeLayout.removeView(itemListRemoveButton);

                    }
                });
            }

            return v;
        }
    }

    // 아이템 사용
    public void useItem(int type){
        switch (type){
            case 1:
                // 1 체력물약
                if(getHp()!=100){
                    Toast.makeText(MainActivity.context, "체력 물약 사용", Toast.LENGTH_LONG).show();
                    setHp(MAX_HP/2);
                }else{
                    Toast.makeText(MainActivity.context, "아이템을 사용할 수 없습니다. ", Toast.LENGTH_LONG).show();
                }
                break;

            case 2:
                // 2 부활물약
                if(getHp()==0){
                    Toast.makeText(MainActivity.context, "부활 물약 사용. ", Toast.LENGTH_LONG).show();
                    setHp(MAX_HP);
                    // 부활효과들

                }else{
                    Toast.makeText(MainActivity.context, "아이템을 사용할 수 없습니다. ", Toast.LENGTH_LONG).show();
                }
                break;

            case 3:
                // 3 물.. 필요할까?
                if(getHp()!=100){
                    Toast.makeText(MainActivity.context, "물 사용", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(MainActivity.context, "아이템을 사용할 수 없습니다. ", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    public int getPlantNo() {
        return plantNo;
    }

    public void setPlantNo(int plantNo) {
        this.plantNo = plantNo;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Flower getFlower() {
        return flower;
    }

    public void setFlower(Flower flower) {
        this.flower = flower;
    }

    public int getWaterState() {
        return waterState;
    }

    public void setWaterState(int waterState) {
        if(waterState==1){
            getPlantWater().setVisibility(View.VISIBLE);
        }
        else {
            getPlantWater().setVisibility(View.INVISIBLE);
        }
        this.waterState = waterState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public ImageView getPlant() {
        return plant;
    }

    public void setPlant(ImageView plant) {
        this.plant = plant;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public Boolean getDryFlowerSetting() {
        return dryFlowerSetting;
    }

    public void setDryFlowerSetting(Boolean dryFlowerSetting) {
        this.dryFlowerSetting = dryFlowerSetting;
    }

    public int getHp() {
        return hp;
    }

    //프로그래스바 및 체력 관리
    public void setHp(int hp) {
        this.hp += hp;
        if(this.hp > 100){
            this.hp = 100;
        }
        this.plantHP.setProgress(this.hp );
    }

    public void desHp(int hp) {
        this.hp -= hp;
        if(this.hp < 0){
            this.hp = 0;
        }
        this.plantHP.setProgress(this.hp );
    }



    public ImageView getPlantWater() {
        return plantWater;
    }

    public ConcurrentHashMap<Integer, Integer> getOverlayScore() {
        return overlayScore;
    }

    public void setOverlayScore(ConcurrentHashMap<Integer, Integer> overlayScore) {
        this.overlayScore = overlayScore;
    }

    public RelativeLayout getPlantLayout() {
        return plantLayout;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

// 물주기 타이머
    class Timemer {
        Timer timer;
        private int type=0;
        private TimerTask second;
        private int times;
        private int waterTime;
        private int penaltytime;

        Timemer(int waterTime, int penaltytime){
            this.waterTime = waterTime;
            this.penaltytime = penaltytime;
            this.times = 0;
            handlerStart();
        }
        public int getPenaltytime() {
            return penaltytime;
        }
        public int getWaterTime() {
            return waterTime;
        }
        public int getType() {
            return type;
        }
        public void setType(int type) {
            this.type = type;
        }

        public int getTimes() {
            return times;
        }
        public void setTimes(int times) {
            if(times == 0){
                this.times = 0;
            }
            else{
                this.times += times;
            }
        }

        private final Handler handler = new Handler();

    // 메인타이머
        public void handlerStart(){
            timer = new Timer();
            second = new TimerTask() {
                @Override
                public void run() {

                    Log.i("Timer","if");
                    if(getWaterState()==1){
                        Log.i("Timer","penaltyUpdate");
                        penaltyUpdate();
                    }
                    else{
                        Log.i("Timer","waterUpdate");
                        waterUpdate();
                    }
                }
            };
            timer.schedule(second, 0, 1000);
        }

        // 물 주기 이밴트
        protected void waterUpdate() {
            Runnable updater = new Runnable() {
                public void run() {
                    if(getWaterTime() <= getTimes()){
                        // 스테이트 업데이트
                        Log.i("Timer","waterUpdate State UP");
                        setWaterState(1);
                        getPlantWater().setVisibility(View.VISIBLE);
                        setTimes(0);
                    }
                    else{
                        setTimes(1);
                    }
                }
            };
            handler.post(updater);
        }

        // 물 패널티 이밴트
        protected void penaltyUpdate() {
            Runnable updater = new Runnable() {
                public void run() {
                    if(getPenaltytime() <= getTimes()){
                        // 체력감소
                        desHp(1);
                        Log.i("Timer","penaltyUpdate Hp down");
                        setTimes(0);
                    }
                    else{
                        setTimes(1);
                    }
                }
            };
            handler.post(updater);
        }

        public void handleEnd(){
            timer.cancel();
            timer = null;
        }
    }
}
