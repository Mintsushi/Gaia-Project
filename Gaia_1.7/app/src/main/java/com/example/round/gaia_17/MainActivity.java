package com.example.round.gaia_17;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Round on 2017-08-15.
 */

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    private static final String TAG = ".MainActivity";

    private static TextView seed, fruit;
    private Button goal, menu, move;
    public static RelativeLayout relLayout;
    private LinearLayout linearLayout;

    private ImageButton menuFlowerButton;
    private ImageButton menuASkillButton;
    private ImageButton menuPSkillButton;
    private ImageButton menuOverlayButton;
    private ImageButton menuStoreButton;
    private ImageButton menuDownBuootn;

    public static ArrayList<PlantInfo> plantArray = new ArrayList<>();

    ////////////// 테스트용 임시값  /////////////////////
    public static float score = 1000;
    public static int fruitScore = 200;
    /////////////////////////////////////////////////////

    private TimerTask mTask;
    private Timer mTimer;

    //Overlay Service
    public static OverlayService mOverlayService;
    private Intent overlayService;
    public static Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

    private Boolean nonStopApp = false;
    public static Context context;

    private android.app.AlertDialog alertDialog=null;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG,"ServieConnected");
            mOverlayBinder = iBinder;
            mOverlayService = ((OverlayService.LocalBinder)iBinder).getService();
            mConnected = true;
            if(mOverlayService.getGPS()){
                setGPS("GPS Setting이 되지 않았을 수도 있습니다.\n 설정하시겠습니까?\n" +
                        "(설정하지 않으시면 외부기능 이용에 불편함이 있으실 수 있습니다.)");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG,"Service DisConnected");
        }
    };

    @Override
    public void onClick(View view){
        score= score+1000;
        seed.setText(Float.toString(score));
        Log.i(TAG,"Score : "+Float.toString(score));
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(!nonStopApp){
            if(mConnected){
                if(mOverlayService.getSize() > 0){
                    mOverlayService.invisible();
                }
            }
        }
        else{
            nonStopApp = false;
        }
    }

    @Override
    protected void onPause(){
        super.onPause();

        if(!nonStopApp){
            if(mConnected){
                mOverlayService.visible();
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        mConnected = false;
    }

    private void setGPS(String message){
       android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_gps, null);

        TextView warn = (TextView)dialogView.findViewById(R.id.warn);
        warn.setText(message);
        Button cancel = (Button)dialogView.findViewById(R.id.cancel);
        Button submit = (Button)dialogView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOverlayService.enableOverlayService = true;
                alertDialog.cancel();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        linearLayout = (LinearLayout)findViewById(R.id.menuLayout);
        seed = (TextView)findViewById(R.id.seed);
        fruit = (TextView)findViewById(R.id.fruit);
        context = this.getApplicationContext();

        updateSeed(score);
        updateFruit(fruitScore);

        //Menu Fragement
        menuFlowerButton = (ImageButton)findViewById(R.id.menuFlowerButton);
        menuASkillButton = (ImageButton)findViewById(R.id.menuASkillButton);
        menuPSkillButton = (ImageButton)findViewById(R.id.menuPSkillButton);
        menuOverlayButton = (ImageButton)findViewById(R.id.menuOverlayButton);
        menuStoreButton = (ImageButton)findViewById(R.id.menuStoreButton);
        menuDownBuootn = (ImageButton)findViewById(R.id.menuDownButton);
        setImageButtonClick();

        //OverlayServie
        overlayService = new Intent(MainActivity.this, OverlayService.class);

        if(!isServiceRunning(OverlayService.class)){
            Log.i(TAG,"Starting Service");
            startService(overlayService);
            bindService(overlayService,mServiceConnection,BIND_AUTO_CREATE);
        }
        if(mOverlayService != null){
            if(mOverlayService.getGPS()){
                setGPS("GPS Setting이 되지 않았을 수도 있습니다.\n 설정하시겠습니까?\n" +
                        "(설정하지 않으시면 외부기능 이용에 불편함이 있으실 수 있습니다.)");
            }
        }

        //서버 구축 이후에는 사용자 데이터에서 정보 받아오기
        //화면 클릭
        relLayout.setOnClickListener(this);

        //getUserInform();

        mTask = new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG,"Timer Task Run");
                //서버로 사용자 정보 서버에 저장
            }
        };

        //1분 후에 mTask를 실행하고, 1분 후에 다시 시작
        mTimer = new Timer();
        mTimer.schedule(mTask,60000,60000);

        //식물 이동 버튼
        move = (Button)findViewById(R.id.move);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG,"OnClick : "+move.getText());
                //버튼의 글씨 변경
                //버튼의 글씨 == "move"
                //연속 클릭 기능 제한 및 식물 이동 기능 ON
                if(move.getText().equals("Move")) {
                    move.setText("Finish");

                    relLayout.setOnClickListener(null);
                    for(int i = 0 ; i<plantArray.size();i++){
                        plantArray.get(i).setTouchistener();
                    }
                }
                //버튼의 글씨 == "finish"
                //식물 이동 제한 및 연속 클릭 기능 ON
                else {
                    move.setText("Move");

                    relLayout.setOnClickListener(MainActivity.this);
                    for(int i = 0 ; i<plantArray.size();i++){
                        plantArray.get(i).clearTouchListener();
                    }
                }
            }
        });

        plantArray.clear();
        testSource();

        // 메뉴버튼 활성화
        menu = (Button)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
                params.height = 900;
                linearLayout.setLayoutParams(params);

                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_layout, new menuFlowerActivity())
                        .commit();
            }
        });

        //업적버튼 활성화
        goal = (Button)findViewById(R.id.goal);
        goal.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final goalListDialog dialog = new goalListDialog(v.getContext());
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                    }
                });
                dialog.show();
            }
        });
    }

    private boolean isServiceRunning(Class<?> serviceClass){
        ActivityManager manager = (ActivityManager)getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        for(ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)){
            if(serviceClass.getName().equals(service.service.getClassName())){
                if(mOverlayBinder != null){
                    mOverlayService = ((OverlayService.LocalBinder)mOverlayBinder).getService();
                    mConnected = true;
                    return true;
                }
                else return false;
            }
        }
        return false;
    }

    private void setImageButtonClick(){
        menuFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_layout, new menuFlowerActivity())
                        .commit();
            }
        });

        menuASkillButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_layout, new menuActiveSkillActivity())
                        .commit();
            }
        });

        menuPSkillButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_layout, new menuPassiveSkillActivity())
                        .commit();
            }
        });

        menuOverlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_layout, new menuOverlayActivity())
                        .commit();
            }
        });

        menuStoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity.this.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.list_layout, new menuStoreActivity())
                        .commit();
            }
        });

        menuDownBuootn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ViewGroup.LayoutParams params = linearLayout.getLayoutParams();
                params.height = 0;
                linearLayout.setLayoutParams(params);
            }
        });
    }
    private void testSource(){

        ImageView imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.image);
        imageView.setId(0);
        imageView.setTag(R.drawable.image);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(imageView,relParams);
        plantArray.add(new PlantInfo(0,imageView,"길냥이",0));
    }

    //bitmap으로 image 용량 관리
    //id : 이미지 리소스의 id 값, size : 이미지의 1/size 만큼 이미지를 줄여서 Decoding 하기위한 값
    //width, height : 이미지 크기
    private Bitmap getResizedBimap(Resources resources, int id, int size, int width, int height){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap src = BitmapFactory.decodeResource(resources,id,options);
        Bitmap resized = Bitmap.createScaledBitmap(src,width,height,true);
        return resized;
    }

    protected static class PlantInfo implements View.OnTouchListener,View.OnClickListener{
        private int id;
        private ImageView plant;
        private ImageView water;
        private String name;
        private int lv;
        private int time; //물을 줘야하는 시간
        //state == 0 : overlayview에 없음
        //state == 1 : overlayview에 있음
        private int state;
        //waterState == 0 : 물을 준 상태
        //waterState == 1 : 물을 주지 않은 상태
        private int waterState = 0;

        private Boolean moving = false;
        private int originalXPos, originalYPos;

        public PlantInfo(int id, ImageView plant, String name,int lv){

            mHandler.sendEmptyMessage(0);

            this.id = id;
            this.lv = lv;
            this.plant = plant;
            this.name = name;
            //서버에서 받아온 정보로 수정
            this.state = 0;
            if(state == 1){
                mOverlayService.addPlant(this);
            }

            //나중에 식물 정보 받아오면 그 때 넣자
            time = 600000;
            water = new ImageView(context);
            water.setImageResource(R.drawable.image);

            water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG,"물을 준다.");
                    //물을 준다.
                    waterState = 0;
                    water.setVisibility(View.INVISIBLE);
                }
            });

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            int [] location = new int[2];
            plant.getLocationOnScreen(location);
            params.leftMargin = location[0]+100;
            params.topMargin = location[1]-20;

            relLayout.addView(water,params);
            water.setVisibility(View.INVISIBLE);
        }


        public int getId(){return this.id;}
        public String getName(){return this.name;}
        public ImageView getPlant(){return this.plant;}
        public int getState(){return this.state;}
        public void setState(int state){
            this.state = state;
        }
        public void setLv(){this.lv += 1;}

        public void remove(){
            relLayout.removeView(this.plant);
            relLayout.removeView(this.water);
            state = 1;
        }

        public void setView(){
            relLayout.addView(this.plant);
            relLayout.addView(this.water);
        }

        public void setTouchistener(){
            plant.setOnTouchListener(this);
            plant.setOnClickListener(this);
        }

        public void clearTouchListener(){
            plant.setOnTouchListener(null);
        }

        public void updateWaterLocation(){

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            int [] location = new int[2];
            plant.getLocationOnScreen(location);
            params.leftMargin = location[0]+100;
            params.topMargin = location[1]-170;

            relLayout.updateViewLayout(water,params);
        }

        @Override
        public void onClick(View view){}

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent){
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
                relLayout.updateViewLayout(view,params);
                updateWaterLocation();

            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                Log.i(TAG,"ImageView Touch Up");
                moving = false;
            }
            return false;
        }

        private android.os.Handler mHandler = new android.os.Handler(){
            public void handleMessage(Message msg){
                Log.i(TAG,"Handler Message : "+waterState);

                //나중에 image가 확립되면 좀더 세부적으로 위치 조정
                if(waterState == 0) {
                    water.setVisibility(View.VISIBLE);
                    waterState = 1;
                }
                else{
                    //시간 내에 물을 주지 않으면
                    Toast.makeText(context,"물 줘 이뇬아!",Toast.LENGTH_LONG).show();
                }
                mHandler.sendEmptyMessageDelayed(0,time);
            }
        };
    }

    public static void buyNewPlant(int id, int lv, String name,String imagePath){
        ImageView plant = new ImageView(context);
        //이부분은 추후에 imagePath로 변경
        plant.setImageResource(R.drawable.image);
        plant.setId(id);
        //이부분은 추후에 imagePath로 변경
        plant.setTag(R.drawable.image);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(plant,relParams);
        plantArray.add(new PlantInfo(id,plant,name,lv));
    }

    public static void updatePlant(int id){
        for(int i =0;i<plantArray.size();i++){
            if(plantArray.get(i).getId() == id){
                plantArray.get(i).setLv();
            }
        }
    }

    public static void updateSeed(float score){
        seed.setText(Float.toString(score));
    }

    public static void updateFruit(float num){
        fruit.setText(Float.toString(num));
    }

    public static void usePassiveSkill(final int incScore){

        Timer mTimer = new Timer();
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                while (true) {
                    Log.i("had : ", "");
                    score = score + incScore;
                   // updateSeed(score);
                }
            }
        };
        mTimer.scheduleAtFixedRate(mTask,0,1000);

    }
}
