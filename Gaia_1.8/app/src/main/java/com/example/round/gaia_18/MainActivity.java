package com.example.round.gaia_18;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
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
import com.example.round.gaia_18.Fragement.MenuFlower;
import com.example.round.gaia_18.Fragement.MenuOverlay;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ".MainActivity";
    public static Context context;

    //DataBase
    public DataBaseHelper dataBaseHelper;
    public static DataList dataList;

    //Layout / View
    public static RelativeLayout relativeLayout;
    private LinearLayout linearLayout;
    private Button goal, menu;
    private static TextView seed, fruit;
    public static ImageView weather;

    //FragementButton
    private ImageButton menuFlowerButton;
    private ImageButton menuASkillButton;
    private ImageButton menuPSkillButton;
    private ImageButton menuOverlayButton;
    private ImageButton menuStoreButton;
    private ImageButton menuDownButton;

    //Data
    public static int score, gameMoney;

    //Fragment Activity
    private MenuFlower menuFlower = new MenuFlower();
    private MenuOverlay menuOverlay = new MenuOverlay();

    //Overlay Service
    public static OverlayService mOverlayService;
    private Intent overlayService;

    //ImageView(Plant) Moving
    private static Boolean moving;
    private static int originalXPos,originalYPos;

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

            if(mOverlayService.getSize() > 0){
                mOverlayService.invisible();
            }
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(mOverlayService != null){
            mOverlayService.visible();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setContext from Static Function
        context = this.getApplicationContext();

        //DataBase
        dataBaseHelper = new DataBaseHelper(this);
        dataList = new DataList(dataBaseHelper.getAllFlowers());

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

        //Function / 데이터 초기화
        //1. 사용자 정보 받아오기
        getUserInfo();
        //2. Fragement Button setOnClickListener
        setImageButtonClick();
        //3. menu 버튼 활성화
        menu.setOnClickListener(this);
        //4. 화면 클릭을 통한 점수 획득
        relativeLayout.setOnClickListener(this);
        //5. OverlayService
        overlayService = new Intent(MainActivity.this, OverlayService.class);

        if(!isServiceRunning(OverlayService.class)){
            startService(overlayService);
            bindService(overlayService,mServiceConnection,BIND_AUTO_CREATE);
        }
        Log.i(TAG,dataBaseHelper.getAllFlowers().toString());
    }

    private void getUserInfo(){

        ArrayList<Plant> plants = new ArrayList<>();

        //여기서부터 json을 읽어드리는 for문 시작
        //Server가 구축되면 Volley를 사용

        score = 1000;
        gameMoney = 1000;

        seed.setText(Float.toString(score));
        fruit.setText(Float.toString(gameMoney));

        Boolean already = false;
        int plantNo = 0;
        int plantLevel = 10;

        ArrayList<Flower> flowers = dataList.getFlowers();
        ArrayList<OverlayPlant> overlayPlants = dataList.getOverlayPlants();

        for(int i =0 ; i<overlayPlants.size() ; i++){
            if(overlayPlants.get(i).getPlant().getPlantNo() == plantNo){
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
                    plants.add(new Plant(plantNo, plantLevel, flowers.get(i), plant));
                    break;
                }
            }
        }

        //flowerArray(모든 꽃 종류에 대한 데이터)에서 꽃의 소유여부, 레벨을 초기화
        dataList.setPlants(plants);
        dataList.compareFlowers();
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

        }else if(view == menuPSkillButton){

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
            //후에 식물을 따른 점수들을 계산해서 구현
            score = score + 1000000;
            seed.setText(Integer.toString(score));
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
        dataList.addPlant(new Plant(flower.getFlowerNo(), 1, flower,plant));
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
