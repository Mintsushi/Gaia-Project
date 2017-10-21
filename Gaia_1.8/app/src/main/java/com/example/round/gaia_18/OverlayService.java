package com.example.round.gaia_18;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.example.round.gaia_18.Common.Common;
import com.example.round.gaia_18.Data.DataList;
import com.example.round.gaia_18.Data.Flower;
import com.example.round.gaia_18.Data.OverlayPlant;
import com.example.round.gaia_18.Data.Plant;
import com.example.round.gaia_18.Data.SkillData;
import com.example.round.gaia_18.Data.SkillInfo;
import com.example.round.gaia_18.Data.User;
import com.example.round.gaia_18.Helper.Helper;
import com.example.round.gaia_18.SkillTimer.SkillCoolTime;
import com.example.round.gaia_18.SkillTimer.skillUseTimer_type0;
import com.example.round.gaia_18.SkillTimer.skillUseTimer_type2;
import com.example.round.gaia_18.model.OpenWeatherMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;

import at.markushi.ui.CircleButton;

import static com.example.round.gaia_18.Data.DataList.overlaySkillAdpter;
import static com.example.round.gaia_18.MainActivity.context;
import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.relativeLayout;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.MainActivity.weather;



public class OverlayService extends Service implements View.OnClickListener,View.OnTouchListener,LocationListener{

    private static final String TAG = ".OverlayService";
    private final IBinder mBinder = new LocalBinder();
    private static final float BYTES_PER_PX = 4.0f;

    //사용자 세팅 상태
    public static int[] settingVar = new int[]{1,1,1,1,1};

    public static void nitificationOnOff(boolean type){
        //노티피케이션 실행
        if(type) {
            remoteView.setViewVisibility(remoteView.getLayoutId(),View.INVISIBLE);
            noti.bigContentView = remoteView;
        }else {
            remoteView.setViewVisibility(remoteView.getLayoutId(),View.VISIBLE);
            noti.bigContentView = remoteView;
        }
        notificationManager.notify(notification_id,noti);
    }

    //datalis
    //DataBase -> 후에 overlay로 이동
    public static DataBaseHelper dataBaseHelper;
    public static DataList dataList;
    public static User user;

    //Overlay View Service
    public WindowManager mWindowManager;
    private View toLeftView;

    //view Move
    private float offsetX, offsetY;
    private int originalX, originalY;
    private boolean moving;
    public boolean enalbeOverlayService = false;

    //Notification View
    private static Notification noti;
    private static Notification.Builder builder;
    private static NotificationManager notificationManager;
    private static int notification_id = 1;
    private static RemoteViews remoteView;

    //Location & Weather
    private LocationManager locationManager;
    private String provider;
    private double lat,lng;
    OpenWeatherMap openWeatherMap = new OpenWeatherMap();
    private static String weatherState = "";

    private final int MY_PERMISSION = 0;

    //Screen Click
    public LinearLayout linearLayout;
    private LinearLayout skill;
    private CircleButton open;
    private CircleButton remove;
    private CircleButton click;
    private CircleButton useSkill;
    private TextView seedOverlay;
    private WindowManager.LayoutParams clickLayout;
    private WindowManager.LayoutParams skillWindow;
    private Button removeAll;
    private ListView skillList;
    private LinearLayout.LayoutParams ButtonParams;
    private LinearLayout weatherContainer;
    private ImageView weatherImage;
//    private TextView temp;

    //0 닫혀있는 상태
    //1 열려있는 상태
    private int skillWindowState = 0;
    //0 : click stop
    //1 : click available
    private int clickState = 0;
    //0 : Non Skill Use
    //1: Use Skill
    private int skillUse = 0;

    //Error Solution
    //0: overlayScreen Off
    //1: overlayScreen On
    private int visible = 0;

    //1 : 화면에서 view들을 임시로 remove
    //0 : 임시로 remove한 view들을 다시 overlay로 가지고 옴.
    private int removeState = 0;

    //날씨에 따른 passive / 패널티
    public static ArrayList<Integer> weatherData;

    //Notification Click Event
    private static final String MyOnClick = "overlayView";
    private int removeAllState = 0;

    //Skill 사용
    public static SkillCoolTime skillCoolTime = new SkillCoolTime();
    public static skillUseTimer_type0 type0 = new skillUseTimer_type0();
    public static skillUseTimer_type2 type2 = new skillUseTimer_type2();
    //item 사용
    public static skillUseTimer_type2 item = new skillUseTimer_type2();

    @Override
    public IBinder onBind(Intent intent){ return mBinder; }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("Weather**","Get Location");
        lat = location.getLatitude();
        lng = location.getLongitude();

        new GetWeather().execute(Common.apiRequest(String.valueOf(lat), String.valueOf(lng)));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}


    public class LocalBinder extends Binder {
        OverlayService getService(){return OverlayService.this;}
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        super.onStartCommand(intent,flags,startId);

        //Notification Custom View
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        remoteView = new RemoteViews(getPackageName(),R.layout.custom_notification);
        remoteView.setViewVisibility(remoteView.getLayoutId(),View.INVISIBLE);
        builder = new Notification.Builder(getApplicationContext());

        remoteView.setOnClickPendingIntent(R.id.remove,getPendingSelfIntent(this,MyOnClick));
        //후에 application icon으로 변경
        builder.setSmallIcon(R.drawable.close)
                .setContentTitle("Gaia Project");

        //후에 icon은 app logo를 이용
        noti = builder.build();
        noti.bigContentView = remoteView;

        noti.bigContentView.setViewVisibility(remoteView.getLayoutId(),View.INVISIBLE);
        //노티피케이션 실행
        notificationManager.notify(notification_id,noti);


        //DataBase
        dataBaseHelper = new DataBaseHelper(this);
        dataList = new DataList(
                dataBaseHelper.getAllFlowers(),
                dataBaseHelper.getAllFlowerDatas(),
                dataBaseHelper.getAllSkillInfo(),
                dataBaseHelper.getAllStoreProduct(),
                dataBaseHelper.getWaterInform()
        );

        user = new User();

        notificationManager.cancel(notification_id);

        startForeground(startId,noti);
        return START_STICKY;
    }

    private PendingIntent getPendingSelfIntent(Context context, String action){
        Intent intent = new Intent(MyOnClick);
//        intent.setAction(action);

        IntentFilter filter = new IntentFilter();
        filter.addAction(MyOnClick);
        registerReceiver(clickBroadCastReceiver,filter);
        return PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private BroadcastReceiver clickBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub

            if (MyOnClick.equals(intent.getAction())) {
                //create -> remove
                if (removeAllState == 0) {

                    if (removeState == 0) {
                        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                        for (int i = 0; i < plants.size(); i++) {
                            mWindowManager.removeView(plants.get(i).getOverlayPlant());
                        }
                    }

                    if (clickState == 1) {
                        mWindowManager.removeView(linearLayout);
                        click.setImageResource(R.drawable.click);
                        clickState = 0;
                    }
                    mWindowManager.removeView(skill);
                    mWindowManager.removeView(weatherContainer);
                    removeAllState = 1;
                } else { //remove -> create

                    mWindowManager.addView(skill, skill.getLayoutParams());
                    mWindowManager.addView(weatherContainer, weatherContainer.getLayoutParams());

                    if (removeState == 0) {
                        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                        for (int i = 0; i < plants.size(); i++) {
                            mWindowManager.addView(plants.get(i).getOverlayPlant(), plants.get(i).getParams());
                        }
                    }

                    removeAllState = 0;
                }
            }
        }
    };

    @Override
    public void onCreate(){
        super.onCreate();

        //Overlay Service / WindowManager
        mWindowManager = (WindowManager)getSystemService(WINDOW_SERVICE);


        toLeftView = new View(this);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.width = 0;
        params.height = 0;

        mWindowManager.addView(toLeftView, params);
        setLayout();
    }

    public Boolean setLocation(){
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);
        provider = locationManager.getBestProvider(new Criteria(),false);

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(getApplicationContext(),"권한 허가",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(),"권한 거부",Toast.LENGTH_LONG).show();
            }
        };

        new TedPermission(this).setPermissionListener(permissionListener)
                .setRationaleMessage("GPS를 사용하기 위해서는 GPS 접근 권한이 필요합니다.")
                .setDeniedMessage("거부하셨습니다...\n[설정]>[권한]에서 권한을 허용할 수 있습니다.")
                .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
                .check();

        try{

            Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(!isGPSEnabled) return true;
            else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 400, 1, this);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 400, 1, this);
            }
        }catch (SecurityException e){
            Log.i("OverlayService",e.toString());
        }

        enalbeOverlayService = true;
        return false;
    }

    public void setSeed(){
        seedOverlay.setText(dataList.getAllScore(dataList.getScoreHashMap()));
    }

    private void setLayout(){

        WindowManager.LayoutParams weatherParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 500,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );

        weatherParams.gravity= Gravity.LEFT | Gravity.TOP;

        weatherImage = new ImageView(this);

//        temp = new TextView(this);
//        temp.setMinWidth(150);
//        temp.setGravity(View.TEXT_ALIGNMENT_CENTER);
//        temp.setTextSize(15);
//        temp.setTextColor(getResources().getColor(R.color.Black));
//        temp.setBackgroundResource(R.drawable.view_background);

        weatherContainer = new LinearLayout(this);
        weatherContainer.setOrientation(LinearLayout.VERTICAL);
        weatherContainer.setLayoutParams(weatherParams);
        weatherContainer.setOnClickListener(this);
        weatherContainer.setOnTouchListener(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;

        weatherContainer.addView(weatherImage,params);

//        LinearLayout.LayoutParams tempParams = new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        tempParams.gravity=Gravity.CENTER;
//        weatherContainer.addView(temp,tempParams);

        linearLayout = new LinearLayout(this);
        linearLayout.setOnClickListener(this);

        skillList = new ListView(this);
        skillList.setBackgroundColor(getResources().getColor(R.color.White));
        overlaySkillAdpter = new OverlaySkillAdpter(this, R.layout.overlay_skill_item);
        skillList.setAdapter(overlaySkillAdpter);
        skillList.setBackgroundColor(getResources().getColor(R.color.transparent));

        skill = new LinearLayout(this);
        skill.setOrientation(LinearLayout.VERTICAL);

        skillWindow = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );

        skillWindow.gravity = Gravity.LEFT | Gravity.TOP;

        skill.setOnClickListener(this);
        skill.setOnTouchListener(this);

        clickLayout = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_TOAST,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        open = new CircleButton(this);
        loadImage(open, R.drawable.flower);
//        open.setImageResource(R.drawable.open);
        open.setTag(0);

        seedOverlay = new TextView(this);
        seedOverlay.setMinWidth(150);
        seedOverlay.setGravity(View.TEXT_ALIGNMENT_CENTER);
        seedOverlay.setTextSize(15);
        seedOverlay.setTextColor(getResources().getColor(R.color.Black));
        seedOverlay.setBackgroundResource(R.drawable.view_background);

        click = new CircleButton(this);
        click.setImageResource(R.drawable.click);
        click.setScaleType(ImageView.ScaleType.FIT_XY);

        remove = new CircleButton(this);
        remove.setImageResource(R.drawable.remove);
        remove.setScaleType(ImageView.ScaleType.FIT_XY);

        useSkill = new CircleButton(this);
        useSkill.setImageResource(R.drawable.skill);
        useSkill.setScaleType(ImageView.ScaleType.FIT_XY);

        click.setOnClickListener(this);
        remove.setOnClickListener(this);
        useSkill.setOnClickListener(this);

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close - > open
                if((int)view.getTag() == 0){
                    LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(150, 150);
                    skill.addView(click,buttonParams);
                    skill.addView(remove,buttonParams);
                    skill.addView(useSkill,buttonParams);
                    view.setTag(1);
                }
                else{ //open -> close

                    if(skillUse == 1){
                        skill.removeView(skillList);
                    }

                    skill.removeView(click);
                    skill.removeView(remove);
                    skill.removeView(useSkill);

                    view.setTag(0);
                }
            }
        });
        LinearLayout.LayoutParams skillParams = new LinearLayout.LayoutParams(
                150,150);
        skillParams.gravity=Gravity.CENTER;
        skillParams.topMargin=150;
//        buttonParams.bottomMargin = 100;
//        skillParams.bottomMargin = 50;
        skill.addView(open,skillParams);

        LinearLayout.LayoutParams seedParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        seedParams.gravity=Gravity.CENTER;
//        buttonParams.bottomMargin = 100;
//        params.bottomMargin = 50;
        skill.addView(seedOverlay,seedParams);

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent){

        Log.i("onTouch","motion : "+motionEvent.toString());
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
            moving = false;

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            int[] location = new int[2];
            view.getLocationOnScreen(location);

            originalX = location[0];
            originalY = location[1];
            offsetX = originalX - x;
            offsetY = originalY - y;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            int[] topLeftLocationOnScreen = new int[2];
            toLeftView.getLocationOnScreen(topLeftLocationOnScreen);

            float x = motionEvent.getRawX();
            float y = motionEvent.getRawY();

            WindowManager.LayoutParams params = (WindowManager.LayoutParams)view.getLayoutParams();

            int newX = (int)(offsetX + x);
            int newY = (int)(offsetY + y);

            if (Math.abs(newX - originalX) < 1 && Math.abs(newY - originalY) < 1 && !moving) {
                return false;
            }

            params.x = newX - (topLeftLocationOnScreen[0]);
            params.y = newY - (topLeftLocationOnScreen[1]);

            mWindowManager.updateViewLayout(view, params);
            moving = true;
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
            if(moving){
                return true;
            }
        }

        return false;
    }

    private class OverlaySkillViewHolder{
        ImageView skillImage;
        TextView coolTime;
        ImageView cantUse;
    }

    public class OverlaySkillAdpter extends ArrayAdapter<SkillInfo> {

        private LayoutInflater inflater = null;

        public OverlaySkillAdpter(Context context, int resourceId){
            super(context,resourceId);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){return dataList.getSkillInfos().size()-1;}

        @Override
        public View getView(int position, View view, ViewGroup parent){

            position +=1;
            final SkillInfo skillInfo = dataList.getSkillInfos().get(position);
            final SkillData skillData = dataList.getSkillDatas().get(position);
            final OverlaySkillViewHolder skillViewHolder;

            Log.i("skillData","skillData : "+position);
            if(view == null){

                view = inflater.inflate(R.layout.overlay_skill_item, parent, false);

                skillViewHolder = new OverlaySkillViewHolder();
                skillViewHolder.skillImage = (ImageView)view.findViewById(R.id.skillImage);
                skillViewHolder.coolTime = (TextView)view.findViewById(R.id.coolTime);
                skillInfo.setSkillCoolTime(skillViewHolder.coolTime);
                skillViewHolder.cantUse = (ImageView)view.findViewById(R.id.cantUse);

                view.setTag(skillViewHolder);
            }else{
                skillViewHolder = (OverlaySkillViewHolder)view.getTag();
                skillInfo.setSkillCoolTime(skillViewHolder.coolTime);
            }


            if(skillInfo != null) {
                int resourceId = getContext().getResources().getIdentifier("skill" + skillInfo.getSkillNo(), "drawable", getContext().getPackageName());
                skillViewHolder.skillImage.setImageResource(resourceId);
//                skillViewHolder.skillImage.setImageResource(R.drawable.image);

                //skill을 구입함
                if (skillData.getSkillBuy()) {
                    skillViewHolder.coolTime.setText("");

                    //skill을 cooltime으로 인해 사용이 불가한 경우
                    if (skillInfo.getSkillUseState()) {
                        skillViewHolder.cantUse.setVisibility(View.VISIBLE);
                        skillViewHolder.cantUse.setBackgroundColor(getResources().getColor(R.color.overlaySkillUse));
                        skillViewHolder.skillImage.setOnClickListener(null);
                    } else { //스킬 사용이 가능한 경우
                        skillViewHolder.cantUse.setVisibility(View.INVISIBLE);
                        skillViewHolder.skillImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                skillViewHolder.coolTime.setVisibility(View.VISIBLE);
                                skillInfo.setSkillCoolTime(skillViewHolder.coolTime);
                                skillCoolTime.skillCoolTime(skillInfo);
                                skillInfo.setSkillUseState(true);
                                skillInfo.setSkillDataChange(true);

                                useSkill(skillInfo.getSkillCase(), skillData.getSkillEffect());

                                if (dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalRate() < dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).getGoalCondition()) {
                                    dataList.getGoalDataByID(17 + 2 * (skillInfo.getSkillNo() - 1) - 1).setGoalRate(1);
                                }

                                overlaySkillAdpter.notifyDataSetChanged();
                            }
                        });
                    }
                } else {//skill을 구입하지 않음
                    skillViewHolder.coolTime.setText("Buy!!");
                    skillViewHolder.cantUse.setVisibility(View.VISIBLE);
                    skillViewHolder.cantUse.setBackgroundColor(getResources().getColor(R.color.overlaySkill));
                }
            }

            return view;
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
                dataList.startSkill_type1(effect,1);
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
            case 5:break;
        }
    }

    private void removeLayout(){

    }
    public void invisible(){

        if(visible == 1){

            if(removeAllState == 0){
                if(removeState == 0) {
                    ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                    for (int i = 0; i < plants.size(); i++) {
                        mWindowManager.removeView(plants.get(i).getOverlayPlant());
                    }
                }

                if (clickState == 1) {
                    mWindowManager.removeView(linearLayout);
                    clickState = 0;
                }
                mWindowManager.removeView(skill);
                mWindowManager.removeView(weatherContainer);
//            mWindowManager.removeView(circleMenu);
            }

            visible = 0;
        }
    }

    public void visible(){

        if(visible == 0) {
            if(removeAllState == 0){

                mWindowManager.addView(weatherContainer,weatherContainer.getLayoutParams());
                mWindowManager.addView(skill, skillWindow);

                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.addView(plants.get(i).getOverlayPlant(), plants.get(i).getParams());
                }

//            mWindowManager.addView(circleMenu,skillWindow);
                setSeed();
            }

            visible = 1;
        }
    }

    public int getSize(){
        return dataList.getOverlayPlants().size();
    }

    public void removePlant(int id){
        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

        for(int i =0 ;i<plants.size();i++){
            if(plants.get(i).getPlant().getPlantNo() == id){
                relativeLayout.addView(plants.get(i).getPlant().getPlantLayout());
                plants.get(i).getPlant().repaintItem(plants.get(i).getItemLayout());
                minusOverlayClickScore(plants.get(i).getPlant());
                plants.remove(i);
            }
        }
    }

    public void setOverlayPlantToDryFlower(int id){

        ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

        for(int i =0 ;i<plants.size();i++){
            if(plants.get(i).getPlant().getPlantNo() == id){
                minusOverlayClickScore(plants.get(i).getPlant());
                plants.remove(i);
            }
        }
    }

    public Boolean onTest(int plantID){
        for(int i =0 ;i<dataList.getOverlayPlants().size();i++){
            if(dataList.getOverlayPlants().get(i).getPlant().getPlantNo() == plantID){
                return false;
            }
        }
        return true;
    }


    public Boolean addPlantToOverlay(final Plant plant){

        if(enalbeOverlayService) {
            relativeLayout.removeView(plant.getPlantLayout());

            ImageView exPlant = plant.getPlant();

            int[] location = new int[2];
            exPlant.getLocationOnScreen(location);

            LinearLayout overlayPlant = new LinearLayout(this);
            overlayPlant.setOrientation(LinearLayout.VERTICAL);
            //overlayPlant.setImageResource(plant.getFlower().getImage());
            //overlayPlant.setImageResource(R.drawable.imageflower);

            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.x = location[0];
            params.y = location[1];

//            overlayPlant.setOnClickListener(this);
//            overlayPlant.setOnTouchListener(this);

            // 식물 이미지
           ImageView plantImage = new ImageView(this);
            //plant.setImageResource(flower.getImage());
//            plantImage.setImageResource(R.drawable.imageflower);
            int resourceId = MainActivity.context.getResources().getIdentifier("flower" + plant.getPlantNo()+plant.getLevelType(), "drawable", MainActivity.context.getPackageName());
            loadImage(plantImage,resourceId);

            LinearLayout.LayoutParams relParams = new LinearLayout.LayoutParams(exPlant.getWidth(),exPlant.getHeight());
//            overlayPlant.addView(plantImage, relParams);

            //plant.setTag(flower.getImage());
//
            int childView = plant.getLinearLayout().getChildCount();

            LinearLayout itemLayout = new LinearLayout(this);
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            overlayPlant.addView(itemLayout);
            overlayPlant.addView(plantImage, relParams);

            for(int i =0 ;i<childView;i++){
                View v = plant.getLinearLayout().getChildAt(i);

                ImageView item = new ImageView(this);
                item.setLayoutParams(v.getLayoutParams());
                item.setImageResource((int)v.getTag());
                item.setTag((int)v.getTag());
                itemLayout.addView(item);
            }
//            for ( int i =0 ;i<childView;i++){
//                View v = plant.getLinearLayout().getChildAt(i);
//                if((int)v.getTag() == 0){
//                    //물주기 버튼
//                    ImageView plantWater = new ImageView(MainActivity.context);
//                    plantWater.setTag(0);
//                    loadImage(plantWater,R.drawable.reward5);

//        plantWater.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // 물의 개수가 물을 줄수있을만큼 있을때.
//                if( dataList.getItemNumber(3) >= water.getWaterNeedWaterNum()) {
//                    Toast.makeText(MainActivity.context, "물 주기 성공", Toast.LENGTH_LONG).show();
//                    dataList.setDesItemNumber(3,water.getWaterNeedWaterNum());
//                    setWaterState(0);
//                    getPlantWater().setVisibility(View.INVISIBLE);
//                }else{
//                    Toast.makeText(MainActivity.context, "물 주기 실패 ㅠㅠ", Toast.LENGTH_LONG).show();
//
//                }
//            }
//        });

//                    // 위치세팅
//                    LinearLayout.LayoutParams waterParams = new LinearLayout.LayoutParams(60, 60);
//                    waterParams.gravity = Gravity.CENTER;
//                    waterParams.setMargins(0,0,20,0);
//                    itemLayout.addView(plantWater,waterParams);
//                }else if((int)v.getTag() == 1){
//                    final ImageView hpButton = new ImageView(MainActivity.context);
//                    loadImage(hpButton,R.drawable.reward4);
//                    hpButton.setTag(1);
//        hpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                linearLayout.removeView(hpButton);
//                hp += 10;
//            }
//        });

//                    // 위치세팅
//                    LinearLayout.LayoutParams HPparams = new LinearLayout.LayoutParams(60, 60);
//                    HPparams.gravity = Gravity.CENTER;
//                    HPparams.setMargins(0,0,10,0);
//                    itemLayout.addView(hpButton,HPparams);
//                }else{
//                    final ImageView saveButton = new ImageView(MainActivity.context);
//                    loadImage(saveButton,R.drawable.item3);
//                    saveButton.setTag(2);
//        hpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                linearLayout.removeView(hpButton);
//                hp += 10;
//            }
//        });

//                    // 위치세팅
//                    LinearLayout.LayoutParams Saveparams = new LinearLayout.LayoutParams(60, 60);
//                    Saveparams.gravity = Gravity.CENTER;
//                    Saveparams.setMargins(0,0,10,0);
//                    itemLayout.addView(saveButton,Saveparams);
//                }
//                itemLayout.addView(v,v.getLayoutParams());
//            }

//            ImageView plantWater = new ImageView(MainActivity.context);
//            plantWater.setImageResource(R.drawable.reward4);
//            plantWater.setVisibility(View.INVISIBLE);
//            plantWater.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    // 물의 개수가 물을 줄수있을만큼 있을때.
//                    if( dataList.getItemNumber(3) >= plant.getWaterInfo().getWaterNeedWaterNum()) {
//                        Toast.makeText(MainActivity.context, "물 주기 성공", Toast.LENGTH_LONG).show();
//                        dataList.setDesItemNumber(3, plant.getWaterInfo().getWaterNeedWaterNum());
//                        plant.setWaterState(0);
//                        plant.getPlantWater().setVisibility(View.INVISIBLE);
//                    }else{
//                        Toast.makeText(MainActivity.context, "물 주기 실패 ㅠㅠ", Toast.LENGTH_LONG).show();
//
//                    }
//                }
//            });
//
//            // 위치세팅
//            RelativeLayout.LayoutParams waterParams = new RelativeLayout.LayoutParams(100, 100);
//            int[] waterLocation = new int[2];
//            overlayPlant.getLocationOnScreen(waterLocation);
//            waterParams.leftMargin =  params.x+100;
//            waterParams.topMargin = params.y+0;
//            overlayPlant.addView(plantWater,waterParams);


//             체력바
            ProgressBar plantHP = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            plantHP.setMax(plant.MAX_HP); // 최대체력 100
            plantHP.setProgress(plant.getHp());

            Drawable myDrawable = this.getResources().getDrawable(R.drawable.hp_progress);
            plantHP.setProgressDrawable(myDrawable);
//            plantHP.setMax(plant.getMaxHp()); // 최대체력 100
//            plantHP.setProgress(plant.getHp());

//            // 위치세팅
            LinearLayout.LayoutParams hpParams = new LinearLayout.LayoutParams(100, 15);
            hpParams.gravity=Gravity.CENTER;
//            int[] hpLocation1 = new int[2];
//            overlayPlant.getLocationOnScreen(hpLocation1);
//            hpParams.leftMargin = params.x+30;
//            hpParams.topMargin = params.y+350;

            overlayPlant.addView(plantHP,hpParams);
//
//            plant.setState(1);
//            Log.i("overlay","plusOverlayClickScore");
            plusOverlayClickScore(plant);

            dataList.addOverlayPlant(new OverlayPlant(plant, itemLayout,plantImage,overlayPlant, params, toLeftView, mWindowManager));

            return true;
        }
        else{
            return false;
        }
    }

    /*
    public Boolean addPlantToOverlay(Plant plant){

        if(enalbeOverlayService) {

            relativeLayout.removeView(plant.getPlantLayout());

            RelativeLayout overlayPlant = plant.getPlantLayout();
            int[] plantLocation = new int[2];

            overlayPlant.getLocationOnScreen(plantLocation);

            WindowManager.LayoutParams plantParams = new WindowManager.LayoutParams(
                    overlayPlant.getWidth(), overlayPlant.getHeight(),
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
            );
            plantParams.gravity = Gravity.LEFT | Gravity.TOP;
            plantParams.x = plantLocation[0];
            plantParams.y = plantLocation[1];

            overlayPlant.setOnClickListener(this);
            overlayPlant.setOnTouchListener(this);








            ImageView exPlant = plant.getPlant();

            int[] location = new int[2];
            exPlant.getLocationOnScreen(location);


            WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                    exPlant.getWidth(), exPlant.getHeight(),
                    WindowManager.LayoutParams.TYPE_TOAST,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                    PixelFormat.TRANSLUCENT
            );

            params.gravity = Gravity.LEFT | Gravity.TOP;
            params.x = location[0];
            params.y = location[1];

            plant.setState(1);
            Log.i("overlay","plusOverlayClickScore");
            plusOverlayClickScore(plant);
            dataList.addOverlayPlant(new OverlayPlant(plant, overlayPlant, plantParams));

            return true;
        }
        else{
            return false;
        }
    }
*/

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent){
//
//        Log.i("onTouch","motion : "+motionEvent.toString());
//        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
//            moving = false;
//
//            float x = motionEvent.getRawX();
//            float y = motionEvent.getRawY();
//
//            int[] location = new int[2];
//            view.getLocationOnScreen(location);
//
//            originalX = location[0];
//            originalY = location[1];
//            offsetX = originalX - x;
//            offsetY = originalY - y;
//        }
//        else if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
//            int[] topLeftLocationOnScreen = new int[2];
//            toLeftView.getLocationOnScreen(topLeftLocationOnScreen);
//
//            float x = motionEvent.getRawX();
//            float y = motionEvent.getRawY();
//
//            WindowManager.LayoutParams params = (WindowManager.LayoutParams)view.getLayoutParams();
//
//            int newX = (int)(offsetX + x);
//            int newY = (int)(offsetY + y);
//
//            if (Math.abs(newX - originalX) < 1 && Math.abs(newY - originalY) < 1 && !moving) {
//                return false;
//            }
//
//            params.x = newX - (topLeftLocationOnScreen[0]);
//            params.y = newY - (topLeftLocationOnScreen[1]);
//
//            mWindowManager.updateViewLayout(view, params);
//            moving = true;
//        }
//        else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
//            if(moving){
//                return true;
//            }
//        }
//
//        return false;
//    }

    @Override
    public void onClick(View view){
        if(view == linearLayout){
            dataList.overlayWindowClick();
            setSeed();
            seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));

            dataList.getGoalDataByID(9).setGoalRate(1);
        }
        else if(view == click){

            //stop -> click
            if(clickState == 0){
                mWindowManager.addView(linearLayout,clickLayout);
                dataList.setClickView(linearLayout);
                //수정필요
                mWindowManager.removeView(skill);
                mWindowManager.addView(skill,(WindowManager.LayoutParams)skill.getLayoutParams());
                click.setImageResource(R.drawable.stop);
                clickState = 1;
            }
            else{ //click -> stop
                mWindowManager.removeView(linearLayout);
                click.setImageResource(R.drawable.click);
                clickState = 0;
            }

        }
        else if(view == remove){

            //create -> remove
            if(removeState == 0){
                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.removeView(plants.get(i).getOverlayPlant());
                }

                remove.setImageResource(R.drawable.create);
                removeState = 1;
            }

            else{ //remove -> create
                ArrayList<OverlayPlant> plants = dataList.getOverlayPlants();

                for (int i = 0; i < plants.size(); i++) {
                    mWindowManager.addView(plants.get(i).getOverlayPlant(), plants.get(i).getParams());
                }

                remove.setImageResource(R.drawable.remove);
                removeState = 0;
            }
        }
        else if(view == useSkill){

            Log.i("UseSkill","use Skill!! : "+useSkill.getTag());
            //Non Use Skill -> Use Skill
            if(skillUse == 0) {
                Log.i("UseSkill","use Skill!!");
                LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(170, 500);
                skill.addView(skillList,buttonParams);
                skillUse = 1;
            }
            else{ //Use Skill -> Non Use Skill
                skill.removeView(skillList);
                skillUse = 0;
            }
        }
    }

    public class GetWeather extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params){
            String stream = null;
            String urlString = params[0];

            Helper http = new Helper();
            stream = http.getHTTPData(urlString);

            return stream;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            if(s != null) {
                if (s.contains("Error: Not found city")) {
                    return;
                }
            }
            Gson gson = new Gson();
            Type type = new TypeToken<OpenWeatherMap>(){}.getType();
            openWeatherMap = gson.fromJson(s, type);

            Log.i("Weather**","Get Weather");

            //기존이랑 날씨가 다르면
            if(!weatherState.equals(openWeatherMap.getWeather().get(0).getDescription()) && settingVar[3]==1){
                Log.i("Weather**","Diff Weather");

                //현재 날씨 setting
                weatherState = openWeatherMap.getWeather().get(0).getDescription();

                //날씨 알림 view setting
                remoteView.setTextViewText(R.id.txtCity,String.format("도시 : %s, 국가 : %s",openWeatherMap.getName(),openWeatherMap.getSys().getCountry()));
                remoteView.setTextViewText(R.id.txtLastUpdate,String.format("Last Updated : %s", Common.getDateNow()));
                remoteView.setTextViewText(R.id.txtHumidity,String.format("습도 : %d%%",openWeatherMap.getMain().getHumidity()));
                remoteView.setTextViewText(R.id.txtTime,String.format("%s/%s",Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunrise())
                            ,Common.unixTimeStampToDateTime(openWeatherMap.getSys().getSunset())));
                remoteView.setTextViewText(R.id.txtCelsius,String.format("%.2f °C",openWeatherMap.getMain().getTemp()));
                remoteView.setTextViewText(R.id.txtDescription,String.format("%s",openWeatherMap.getWeather().get(0).getDescription()));


                Picasso.with(context)
                        .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                        .into(remoteView,R.id.imageView,notification_id,noti);


                notificationManager.notify(notification_id,noti);
                Picasso.with(context)
                        .load(Common.getImage(openWeatherMap.getWeather().get(0).getIcon()))
                        .into(weather);

                //해당 날씨에 따른 영향에 관한 datalist 받아오기
                Log.i("Weather**","Diff Weather :" +openWeatherMap.getWeather().get(0).getDescription());
                ;
                weatherData = dataBaseHelper.getWeatherPassive(openWeatherMap.getWeather().get(0).getDescription());
                Log.i("Weather**","Diff Weather : "+weatherData.toString());
                int resourceId = getResources().getIdentifier("weather" + weatherData.get(0).toString(), "drawable",getPackageName());
                loadImage(weatherImage,resourceId);
//                mWindowManager.addView(weatherImage,weatherImage.getLayoutParams());
//                int resourceId = getResources().getIdentifier("weather0", "drawable",getPackageName());
//                weatherImage.setImageResource(resourceId);
//                temp.setText(String.format("%.2f °C",openWeatherMap.getMain().getTemp()));

                dataList.overlayClickScore.clear();
                for(int i =0; i<dataList.getOverlayPlants().size(); i++){
                    //해당 꽃의 id(flowerNo)
                    int id = dataList.getOverlayPlants().get(i).getPlant().getPlantNo();
                    //해당 꽃의 N%를 계산하기 위해, 현재 꽃의 점수를 가져와야함 -> flower 객체에 있음.
                    Flower flower = dataList.getOverlayPlants().get(i).getPlant().getFlower();
                    final Plant plant = dataList.getOverlayPlants().get(i).getPlant();

                    plant.getOverlayScore().clear();
                    plant.getTimer().cancel();
                    plant.setEffect(0);

                    //날씨에 따른 패널티 / 패시브
                    // 김태우 정보 밀려남에따라 아이디 +2
                    int effect = weatherData.get(id+3);
                    // effect>0일 경우는 추가 점수
                    // effect<0일 경우는 hp 감소
                    if(effect > 0) {

                        //꽃의 현재 점수
                        Iterator<Integer> iterator = flower.getScore().keySet().iterator();

                        while (iterator.hasNext()) {
                            int key = iterator.next();
                            int value = flower.getScore().get(key);

                            int newScore = value + (value * effect) / 100;
                            if(newScore > 999){
                                if(newScore-1000 >0)
                                    dataList.plusScore(key,newScore%1000,plant.getOverlayScore());
                                dataList.plusScore(key+1,newScore/1000,plant.getOverlayScore());
                            }
                            else{
                              dataList.plusScore(key,newScore,plant.getOverlayScore());
                            }
                        }

                        dataList.plusOverlayClickScore(plant.getOverlayScore());
                    }
                    else if(effect == 0){
                        plant.getOverlayScore().clear();
                        dataList.plusOverlayClickScore(flower.getScore());
                        plant.getTimer().cancel();
                        plant.setEffect(0);
                    }
                    else{ //hp 감소

                        plant.setEffect(effect);

                        //점수 증가였다가 HP 증가로 변화
                        if(plant.getEffect()==0){
                            //3분에 한번씩 감소
                            TimerTask task = new TimerTask() {
                                @Override
                                public void run() {
                                    plant.setHp(plant.getEffect());
                                }
                            };

                            plant.getTimer().schedule(task,0,180000);
                        }
                    }

                }
            }
        }
    }

    //꽃을 외부에서 내부로 옮길 때, 점수 삭감 및 hp 감소 취소
    public void minusOverlayClickScore(Plant plant){

        Log.i("OverlayClick","minusOverlayClickScore");

        //해당 꽃의 id(flowerNo)
        int id = plant.getPlantNo();
        //해당 꽃의 N%를 계산하기 위해, 현재 꽃의 점수를 가져와야함 -> flower 객체에 있음.
        Flower flower = plant.getFlower();

        //꽃의 점수 5%값을 임시로 저장
        //날씨에 따른 패널티 / 패시브
        // 김태우 정보 밀려남에따라 아이디 +2

        int effect = weatherData.get(plant.getPlantNo()+2);

        // effect>0일 경우는 추가 점수
        // effect<0일 경우는 hp 감소
        if(effect > 0) {
            dataList.minusOverlayClickScore(plant.getOverlayScore());
        }
        else if(effect == 0){
            dataList.minusOverlayClickScore(flower.getScore());
        }
        else{ //hp 감소 취소
            plant.getTimer().cancel();
        }
    }

    public void plusOverlayClickScore(final Plant plant){

        Flower flower = plant.getFlower();

        // 김태우 정보 밀려남에따라 아이디 +2
        final int effect = weatherData.get(plant.getPlantNo()+2);
        Iterator<Integer> iterator = flower.getScore().keySet().iterator();

        Log.i("OverlayClick", "Flower : "+flower.getFlowerName()+" / effect : "+effect);

        if(effect > 0) {
            while (iterator.hasNext()) {
                int key = iterator.next();
                int value = flower.getScore().get(key);

                Log.i("OverlayClick", "key : " + key + " / value : " + value + " / effect : " + effect);

                int newScore = value + ((value * effect) / 100);
                if (newScore > 999) {
                    if (newScore - 1000 > 0)
                        dataList.plusScore(key, newScore % 1000, plant.getOverlayScore());
                    dataList.plusScore(key + 1, newScore / 1000, plant.getOverlayScore());
                } else {
                    dataList.plusScore(key, newScore, plant.getOverlayScore());
                }
            }

            dataList.plusOverlayClickScore(plant.getOverlayScore());
        }
        else if(effect == 0){
            dataList.plusOverlayClickScore(flower.getScore());
        }
        else { //hp 감소

            plant.setEffect(effect);
            //3분에 한번씩 감소
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    plant.setHp(plant.getEffect());
                }
            };

            plant.getTimer().schedule(task,0,180000);
        }
    }
    private void loadImage(ImageView image,int resourceId){
        if(readBitmapInfo(resourceId) > MemUtils.megabytesAvailable()){
            Log.i("LoadImage","Big Image");
            subImage(32,resourceId,image);
        }else{
            Log.i("LoadImage","Small Image");
            image.setImageResource(resourceId);
        }
    }

    private float readBitmapInfo(int resourceId){
        final Resources res = MainActivity.context.getResources();
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

        final Resources res = MainActivity.context.getResources();
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = powerOf2;

        final Bitmap bitmap = BitmapFactory.decodeResource(res,resourceId,options);
        image.setImageBitmap(bitmap);
    }
}
