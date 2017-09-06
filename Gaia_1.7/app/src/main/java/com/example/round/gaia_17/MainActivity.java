package com.example.round.gaia_17;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
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

import com.example.round.gaia_17.Data.DataList;
import com.example.round.gaia_17.Data.Flower;
import com.example.round.gaia_17.Data.Plant;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.id;

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

    public static ArrayList<Plant> plantArray = new ArrayList<>();

    ////////////// 테스트용 임시값  /////////////////////
    public static float score = 0;
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

    //SQLite DataBase
    private static DataBaseHelper database;
    public static DataList dataList;

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

    private void getUserInfo(){

        //Server가 구축되면 Volley를 사용해서 사용자 정보 받아오기
        int plantNo = 0;

        for(int i =0;i<dataList.getFlowers().size() ; i++){
            if(dataList.getFlowers().get(i).getFlowerNo() == plantNo){

                ImageView imageView = new ImageView(this);

                imageView.setImageResource(R.drawable.image);
                imageView.setId(0);
                imageView.setTag(R.drawable.image);

                RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

                //위치는 후에 Random 값으로 배치
                relParams.leftMargin = 0;
                relParams.topMargin = 0;

                relLayout.addView(imageView,relParams);

                plantArray.add(new Plant(0,12,dataList.getFlowers().get(i),imageView));
                break;
            }
        }

        //for문을 전부 돌면
        dataList.setPlants(plantArray);
        dataList.compareFlowers();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database  = new DataBaseHelper(context);
        dataList  = new DataList(database.getAllFlowers());
        getUserInfo();

        relLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        linearLayout = (LinearLayout)findViewById(R.id.menuLayout);
        seed = (TextView)findViewById(R.id.seed);
        context = this.getApplicationContext();

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

    public static void buyNewPlant(Flower flower){
        ImageView plant = new ImageView(context);
        //이부분은 추후에 imagePath로 변경
        plant.setImageResource(R.drawable.image);
        plant.setId(flower.getFlowerNo());
        //이부분은 추후에 imagePath로 변경
        plant.setTag(R.drawable.image);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(plant,relParams);
        plantArray.add(new Plant(id,1,flower,plant));
    }

    public static void updatePlant(int id){
        for(int i =0;i<plantArray.size();i++){
            if(plantArray.get(i).getPlantNo() == id){
                plantArray.get(i).setLevel(plantArray.get(i).getLevel()+1);
            }
        }
    }

    public static void updateSeed(float score){
        seed.setText(Float.toString(score));
    }
}
