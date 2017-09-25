package com.example.round.gaia_18;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.round.gaia_18.Data.DataList;
import com.example.round.gaia_18.Data.Flower;
import com.example.round.gaia_18.Data.OverlayPlant;
import com.example.round.gaia_18.Data.Plant;
import com.example.round.gaia_18.Data.User;
import com.example.round.gaia_18.Dialog.goalListDialog;
import com.example.round.gaia_18.Fragement.MenuDryFlower;
import com.example.round.gaia_18.Fragement.MenuFlower;
import com.example.round.gaia_18.Fragement.MenuOverlay;
import com.example.round.gaia_18.Fragement.MenuSkill;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ".MainActivity";
    public static Context context;

    //DataBase -> 후에 overlay로 이동
    public static DataBaseHelper dataBaseHelper;
    public static DataList dataList;
    public static User user;

    //Layout / View
    public static RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private Button goal, menu;
    public static TextView seed, fruit;
    public static ImageView weather;

    //FragementButton
    private ImageButton menuFlowerButton;
    private ImageButton menuASkillButton;
    private ImageButton menuPSkillButton;
    private ImageButton menuOverlayButton;
    private ImageButton menuStoreButton;
    private ImageButton menuDownButton;

    //Data
    public int gameMoney;

    //Fragment Activity
    private MenuFlower menuFlower = new MenuFlower();
    private MenuOverlay menuOverlay = new MenuOverlay();
    private MenuSkill menuSkill = new MenuSkill();
    private MenuDryFlower menuDryFlower = new MenuDryFlower();

    //Overlay Service
    public static OverlayService mOverlayService;
    private Intent overlayService;

    //ImageView(Plant) Moving
    private static Boolean moving;
    private static int originalXPos,originalYPos;

    //Click Score
    public static HashMap<Integer, Integer> clickScore;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG,"ServieConnected");
            mOverlayService = ((OverlayService.LocalBinder)iBinder).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG,"Service DisConnected");
        }
    };

    @Override
    protected void onResume(){
        super.onResume();

        if(mOverlayService != null){
            mOverlayService.invisible();
            dataList.setClickView(relativeLayout);
            relativeLayout.setOnClickListener(this);

            for(int i =0;i<dataList.getSkillInfos().size();i++){
                if(dataList.getSkillInfos().get(i).getSkillCoolTimeInApp() != null){
                    dataList.getSkillInfos().get(i).setSkillCoolTime(dataList.getSkillInfos().get(i).getSkillCoolTimeInApp());
                }
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(mOverlayService != null){
            mOverlayService.visible();
            relativeLayout.setOnClickListener(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //5. OverlayService
        overlayService = new Intent(MainActivity.this, OverlayService.class);

        if(!isServiceRunning(OverlayService.class)){
            startService(overlayService);
            bindService(overlayService,mServiceConnection,BIND_AUTO_CREATE);
        }

        //setContext from Static Function
        context = this.getApplicationContext();

        //Layout / View
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        linearLayout = (LinearLayout)findViewById(R.id.menuLayout);
        seed = (TextView)findViewById(R.id.seed);
        fruit = (TextView)findViewById(R.id.fruit);
        menu = (Button)findViewById(R.id.menu);
        goal = (Button)findViewById(R.id.goal);
        weather = (ImageView)findViewById(R.id.weather);

        //Fragement Button
        menuFlowerButton = (ImageButton)findViewById(R.id.menuFlowerButton);
        menuASkillButton = (ImageButton)findViewById(R.id.menuASkillButton);
        menuPSkillButton = (ImageButton)findViewById(R.id.menuPSkillButton);
        menuOverlayButton = (ImageButton)findViewById(R.id.menuOverlayButton);
        menuStoreButton = (ImageButton)findViewById(R.id.menuStoreButton);
        menuDownButton = (ImageButton)findViewById(R.id.menuDownButton);


        //DataBase
        dataBaseHelper = new DataBaseHelper(this);
        dataList = new DataList(
                dataBaseHelper.getAllFlowers(),
                dataBaseHelper.getAllFlowerDatas(),
                dataBaseHelper.getAllSkillInfo(),
                dataBaseHelper.getAllStoreProduct()
        );

        user = new User();


        //Function / 데이터 초기화
        //1. 사용자 정보 받아오기
        getUserInfo();
        //2. Fragement Button setOnClickListener
        setImageButtonClick();
        //3. menu 버튼 활성화
        menu.setOnClickListener(this);
        //4. 화면 클릭을 통한 점수 획득
        relativeLayout.setOnClickListener(this);
        //6. goal(업적) 버튼 활성화
        goal.setOnClickListener(this);

        Log.i(TAG,dataBaseHelper.getAllFlowers().toString());
    }

    private void getUserInfo(){

        ArrayList<Plant> plants = new ArrayList<>();

        //여기서부터 json을 읽어드리는 for문 시작
        //Server가 구축되면 Volley를 사용

        dataList.setScore(0,50);
        dataList.setFruit(0,450);
        dataList.setFruit(1,100);

        dataList.setNumber(0,0);
        dataList.setNumber(1,0);
        dataList.setNumber(2,0);
        dataList.setNumber(3,2);

        user.setDryFlowerItem(2);

        ArrayList<Integer> skill = new ArrayList<>();
        skill.add(0,1);
        skill.add(1,1);
        skill.add(2,1);
        skill.add(3,0);
        skill.add(4,0);
        skill.add(5,0);
        skill.add(6,0);

        for(int i =0 ;i<skill.size();i++){
            dataBaseHelper.getAllSkillData(i,skill.get(i));
        }

        dataList.setGoalDatas(0,1,0);
        dataList.setGoalDatas(1,1,0);
        dataList.setGoalDatas(2,1,0);
        dataList.setGoalDatas(3,1,0);
        dataList.setGoalDatas(4,1,0);
        dataList.setGoalDatas(5,1,0);
        dataList.setGoalDatas(6,1,0);
        dataList.setGoalDatas(7,1,0);
        dataList.setGoalDatas(8,1,0);
        dataList.setGoalDatas(9,1,0);
        dataList.setGoalDatas(10,1,0);
        dataList.setGoalDatas(11,1,0);
        dataList.setGoalDatas(12,1,0);
        dataList.setGoalDatas(13,1,0);
        dataList.setGoalDatas(14,1,0);
        dataList.setGoalDatas(15,1,0);
        dataList.setGoalDatas(16,1,0);
        dataList.setGoalDatas(17,1,0);
        dataList.setGoalDatas(18,1,0);
        dataList.setGoalDatas(19,1,0);
        dataList.setGoalDatas(20,1,0);
        dataList.setGoalDatas(21,1,0);
        dataList.setGoalDatas(22,1,0);
        dataList.setGoalDatas(23,1,0);
        dataList.setGoalDatas(24,1,0);

        seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
        fruit.setText(dataList.getAllScore(dataList.getFruitHashMap()));

        Boolean already = false;
        int plantNo = 0;
        int plantLevel = 399;
        int plantHP = 40;

        ArrayList<Flower> flowers = dataList.getFlowers();
        ArrayList<OverlayPlant> overlayPlants = dataList.getOverlayPlants();

        for (int i = 0; i < overlayPlants.size(); i++) {
            if (overlayPlants.get(i).getPlant().getPlantNo() == plantNo) {
                plants.add(overlayPlants.get(i).getPlant());
                already = true;
            }
        }


        if(!already) {
            //plantArray(사용자가 소유하고 이름 꽃의 정보)에 데이터 추가
            for (int i = 0; i < flowers.size(); i++) {
                if (flowers.get(i).getFlowerNo() == plantNo) {

                    ImageView plant = new ImageView(this);

                    //plant.setImageResource(flower.get(i).getImage())
                    plant.setImageResource(R.drawable.image);

                    RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

                    //위치는 후에 Random 값으로 배치
                    relParams.leftMargin = 0;
                    relParams.topMargin = 0;

                    plant.setOnLongClickListener(onLongClick);
                    plant.setOnTouchListener(onTouch);

                    relativeLayout.addView(plant, relParams);
                    plants.add(new Plant(plantNo, plantLevel, flowers.get(i), plant,plantHP));
                    break;
                }
            }
        }

        //flowerArray(모든 꽃 종류에 대한 데이터)에서 꽃의 소유여부, 레벨을 초기화
        dataList.setPlants(plants);
        dataList.compareFlowers();
        dataList.setBuyPossible();
    }

    private void setImageButtonClick(){
        menuFlowerButton.setOnClickListener(this);
        menuASkillButton.setOnClickListener(this);
        menuPSkillButton.setOnClickListener(this);
        menuOverlayButton.setOnClickListener(this);
        menuStoreButton.setOnClickListener(this);
        menuDownButton.setOnClickListener(this);
    }

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager)getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                if(mOverlayService != null){
                   return true;
                }
                else return false;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view){
        if(view == menuFlowerButton){
            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_layout,menuFlower)
                    .commit();
        }else if(view == menuASkillButton){

            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_layout,menuSkill)
                    .commit();

        }else if(view == menuPSkillButton){

            MainActivity.this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.list_layout,menuDryFlower)
                    .commit();

        }else if(view == menuOverlayButton){

            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_layout, menuOverlay)
                    .commit();

        }else if(view == menuStoreButton){

        }else if(view == menuDownButton){

            ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
            params.height = 0;
            linearLayout.setLayoutParams(params);

        }else if(view == menu){

            ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
            params.height = 900;
            linearLayout.setLayoutParams(params);

            MainActivity.this.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.list_layout,menuFlower)
                    .commit();
        }else if(view == relativeLayout){
            dataList.windowClick();
            seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));

            dataList.getGoalDataByID(9).setGoalRate(1);

        }else if(view == goal){
            goalListDialog dialog = new goalListDialog(view.getContext());
            dialog.show();
        }
    }

    public static void buyPlant( Flower flower ){

        ImageView plant = new ImageView(context);

        //plant.setImageResource(flower.getImage());
        plant.setImageResource(R.drawable.image);
        //plant.setTag(flower.getImage());

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200,200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        plant.setOnLongClickListener(onLongClick);
        plant.setOnTouchListener(onTouch);

        relativeLayout.addView(plant,relParams);
        dataList.addPlant(new Plant(flower.getFlowerNo(), 1, flower,plant,100));
    }

    public static void updatePlantLevel(int plantNo){

        ArrayList<Plant> plants = dataList.getPlants();

        //후에 level에 따른 이미지 변화도 추가
        for(int i =0 ;i < plants.size() ; i++){
            if(plants.get(i).getPlantNo() == plantNo){
                plants.get(i).setLevel(plants.get(i).getLevel()+1);
            }
        }
    }

    public static void updateScore(int score){
        seed.setText(Integer.toString(score));
    }

    private static View.OnTouchListener onTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                Log.i(TAG,"ImageVeiw Touch Down");

                moving = false;

                int [] location = new int[2];
                view.getLocationOnScreen(location);

                originalXPos = location[0];
                originalYPos = location[1];
            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){

                moving = true;

                Log.i(TAG,"ImageView Touch Move");

                int x = (int)motionEvent.getRawX();
                int y = (int)motionEvent.getRawY();

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();

                if (Math.abs(x - originalXPos) < 1 && Math.abs(y - originalYPos) < 1 && !moving) {
                    return false;
                }

                params.leftMargin = x-170;
                params.topMargin = y-150;

                relativeLayout.updateViewLayout(view,params);
            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                Log.i(TAG,"ImageView Touch Up");
                moving = false;
            }
            return false;
        }
    };

    private static View.OnLongClickListener onLongClick = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    };
}
