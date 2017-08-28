package com.example.round.gaia_17;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = ".MainActivity";

    private TextView seed, fruit;
    private Button goal, menu, move;
    private RelativeLayout relLayout;
    private LinearLayout linearLayout;

    private ImageButton menuFlowerButton;
    private ImageButton menuASkillButton;
    private ImageButton menuPSkillButton;
    private ImageButton menuOverlayButton;
    private ImageButton menuStoreButton;
    private ImageButton menuDownBuootn;

    public static ArrayList<PlantInfo> plantArray = new ArrayList<>();
    //클릭 개수 : 0~999
    private float score;
    //클릭 개수 : A,B,C,D.....
    private int clickLevel;

    private TimerTask mTask;
    private Timer mTimer;

    //Overlay Service
    public static OverlayService mOverlayService;
    private Intent overlayService;
    public static Boolean mConnected = false;
    private Boolean mClear = false;
    private static IBinder mOverlayBinder;

    private Boolean nonStopApp = false;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG,"ServieConnected");
            mOverlayBinder = iBinder;
            mOverlayService = ((OverlayService.LocalBinder)iBinder).getService();
            mConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG,"Service DisConnected");
        }
    };

    @Override
    public void onClick(View view){
        score++;
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

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        linearLayout = (LinearLayout)findViewById(R.id.menuLayout);
        seed = (TextView)findViewById(R.id.seed);

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

        //서버 구축 이후에는 사용자 데이터에서 정보 받아오기
        score = 0;

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
        plantArray.add(new PlantInfo(0,imageView,"길냥이"));
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

    protected class PlantInfo implements View.OnTouchListener,View.OnClickListener{
        private int id;
        private ImageView plant;
        private String name;
        //state == 0 : overlayview에 없음
        //state == 1 : overlayview에 있음
        private int state;

        private Boolean moving = false;
        private int originalXPos, originalYPos;

        public PlantInfo(int id, ImageView plant, String name){
            this.id = id;
            this.plant = plant;
            this.name = name;
            //서버에서 받아온 정보로 수정
            this.state = 0;
            if(state == 1){
                mOverlayService.addPlant(this);
            }
        }

        public int getId(){return this.id;}
        public String getName(){return this.name;}
        public ImageView getPlant(){return this.plant;}
        public int getState(){return this.state;}
        public void setState(int state){
            this.state = state;
        }

        public void setTouchistener(){
            plant.setOnTouchListener(this);
            plant.setOnClickListener(this);
        }

        public void clearTouchListener(){
            plant.setOnTouchListener(null);
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

            }
            else if(motionEvent.getAction() == MotionEvent.ACTION_UP){

                Log.i(TAG,"ImageView Touch Up");
                moving = false;
            }
            return false;
        }
    }
}
