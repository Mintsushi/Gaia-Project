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
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Round on 2017-08-15.
 */

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    private static final String TAG = ".MainActivity";

    private static TextView seed, fruit;
    private Button goal, menu, move;
    private ImageButton setting;
    public static RelativeLayout relLayout;
    private LinearLayout linearLayout;


    private ImageButton menuFlowerButton;
    private ImageButton menuASkillButton;
    private ImageButton menuPSkillButton;
    private ImageButton menuOverlayButton;
    private ImageButton menuStoreButton;
    private ImageButton menuDownBuootn;

    // 김태우
    // 메인부에서 디비 불러오기
    ///////////////////DB//////////////////////////////
    public static DB_Exception db;
    public static ArrayList<DB_Exception.Flower> flowerActivityArray = new ArrayList<>();
    public static ArrayList<menuActiveSkillActivity.ActiveSkillInform> mActivityArray = new ArrayList<>();
    public static ArrayList<menuActiveSkillActivity.ActiveSkillFormatInform> mActivityFormatArray = new ArrayList<>();

    public static ArrayList<menuPassiveSkillActivity.DryFlower> dryFlowerList = new ArrayList<>();

    /////////////////////////////////////////////////

    public static ArrayList<PlantInfo> plantArray = new ArrayList<>();


    ////////////// 점수계산을 위한 해쉬함수  /////////////////////
    public static HashMap<Integer, Integer> clickScore = new HashMap<>();
    //private static HashMap<Integer, Integer> score = new HashMap<>();
    /////////////////////////////////////////////////

    // 김태우
    //
    ////////////// 테스트용 임시값  /////////////////////
    public static float skill0Effect = 1;
    public static float skill4Effect = 1;
    public static float skillup = 100;
    public static float score = 1000;
    public static int fruitScore = 200;
    public static float dryFlowerScore = 0;
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

    private android.app.AlertDialog alertDialog = null;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "ServieConnected");
            mOverlayBinder = iBinder;
            mOverlayService = ((OverlayService.LocalBinder) iBinder).getService();
            mConnected = true;
            if (mOverlayService.getGPS()) {
                setGPS("GPS Setting이 되지 않았을 수도 있습니다.\n 설정하시겠습니까?\n" +
                        "(설정하지 않으시면 외부기능 이용에 불편함이 있으실 수 있습니다.)");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "Service DisConnected");
        }
    };

    @Override
    public void onClick(View view) {
        score = score + skill4Effect * ((skillup) * skill0Effect);
        updateSeed(score);
        Log.i(TAG, "Score : " + Float.toString(score));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!nonStopApp) {
            if (mConnected) {
                if (mOverlayService.getSize() > 0) {
                    mOverlayService.invisible();
                }
            }
        } else {
            nonStopApp = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!nonStopApp) {
            if (mConnected) {
                mOverlayService.visible();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mConnected = false;
    }

    private void setGPS(String message) {
        android.app.AlertDialog.Builder dialogBuilder = new android.app.AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_gps, null);

        TextView warn = (TextView) dialogView.findViewById(R.id.warn);
        warn.setText(message);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 김태우
        // 메인부에서 디비 불러오기
        ///////////////////DB//////////////////////////////
        db = new DB_Exception(this);

        mActivityArray.add(db.getActiveSkillInform(155));
        mActivityArray.add(db.getActiveSkillInform(0));
        mActivityArray.add(db.getActiveSkillInform(30));
        mActivityArray.add(db.getActiveSkillInform(60));
        mActivityArray.add(db.getActiveSkillInform(90));
        mActivityArray.add(db.getActiveSkillInform(120));
        mActivityFormatArray = db.getActiveSkillFormatInform();
        flowerActivityArray = db.getAllFlowers();
        dryFlowerList = db.getPassiveSkillFormatInform();

        updateSeed(score);
        updateFruit(fruitScore);
        /////////////////////////////////////////////////////

        relLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        linearLayout = (LinearLayout) findViewById(R.id.menuLayout);
        seed = (TextView) findViewById(R.id.seed);
        fruit = (TextView) findViewById(R.id.fruit);
        context = this.getApplicationContext();

        //Menu Fragement
        menuFlowerButton = (ImageButton) findViewById(R.id.menuFlowerButton);
        menuASkillButton = (ImageButton) findViewById(R.id.menuASkillButton);
        menuPSkillButton = (ImageButton) findViewById(R.id.menuPSkillButton);
        menuOverlayButton = (ImageButton) findViewById(R.id.menuOverlayButton);
        menuStoreButton = (ImageButton) findViewById(R.id.menuStoreButton);
        menuDownBuootn = (ImageButton) findViewById(R.id.menuDownButton);
        setImageButtonClick();

        //OverlayServie
        overlayService = new Intent(MainActivity.this, OverlayService.class);

        if (!isServiceRunning(OverlayService.class)) {
            Log.i(TAG, "Starting Service");
            startService(overlayService);
            bindService(overlayService, mServiceConnection, BIND_AUTO_CREATE);
        }
        if (mOverlayService != null) {
            if (mOverlayService.getGPS()) {
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
                Log.i(TAG, "Timer Task Run");
                //서버로 사용자 정보 서버에 저장
            }
        };

        //1분 후에 mTask를 실행하고, 1분 후에 다시 시작
        mTimer = new Timer();
        mTimer.schedule(mTask, 60000, 60000);

        //식물 이동 버튼
        move = (Button) findViewById(R.id.move);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i(TAG, "OnClick : " + move.getText());
                //버튼의 글씨 변경
                //버튼의 글씨 == "move"
                //연속 클릭 기능 제한 및 식물 이동 기능 ON
                if (move.getText().equals("Move")) {
                    move.setText("Finish");

                    relLayout.setOnClickListener(null);
                    for (int i = 0; i < plantArray.size(); i++) {
                        plantArray.get(i).setTouchistener();
                    }
                }
                //버튼의 글씨 == "finish"
                //식물 이동 제한 및 연속 클릭 기능 ON
                else {
                    move.setText("Move");

                    relLayout.setOnClickListener(MainActivity.this);
                    for (int i = 0; i < plantArray.size(); i++) {
                        plantArray.get(i).clearTouchListener();
                    }
                }
            }
        });

        plantArray.clear();
        testSource();
        scoreCalculaters(123456789, 0);
        scoreCalculaters(123456, 3);
        scoreCalculaters(123, 6);

        // 메뉴버튼 활성화
        menu = (Button) findViewById(R.id.menu);
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

        // 김태우
        // 업정버튼, 설정버튼 활성화

        //업적버튼 활성화
        goal = (Button) findViewById(R.id.goal);
        goal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final goalListDialog dialog = new goalListDialog(v.getContext());
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                    }
                });
                dialog.show();
            }
        });

        // 설정버튼 활성화
        setting = (ImageButton) findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final SettingDialog dialog = new SettingDialog(v.getContext());
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                    }
                });
                dialog.show();
            }
        });
    }


    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                if (mOverlayBinder != null) {
                    mOverlayService = ((OverlayService.LocalBinder) mOverlayBinder).getService();
                    mConnected = true;
                    return true;
                } else return false;
            }
        }
        return false;
    }

    private void setImageButtonClick() {
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

    private void testSource() {

        ImageView imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.image);
        imageView.setId(0);
        imageView.setTag(R.drawable.image);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(imageView, relParams);
        plantArray.add(new PlantInfo(0, imageView, "길냥이", 0));
    }

    //bitmap으로 image 용량 관리
    //id : 이미지 리소스의 id 값, size : 이미지의 1/size 만큼 이미지를 줄여서 Decoding 하기위한 값
    //width, height : 이미지 크기
    private Bitmap getResizedBimap(Resources resources, int id, int size, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = size;
        Bitmap src = BitmapFactory.decodeResource(resources, id, options);
        Bitmap resized = Bitmap.createScaledBitmap(src, width, height, true);
        return resized;
    }

    protected static class PlantInfo implements View.OnTouchListener, View.OnClickListener {
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

        public PlantInfo(int id, ImageView plant, String name, int lv) {

            mHandler.sendEmptyMessage(0);

            this.id = id;
            this.lv = lv;
            this.plant = plant;
            this.name = name;
            //서버에서 받아온 정보로 수정
            this.state = 0;
            if (state == 1) {
                mOverlayService.addPlant(this);
            }

            //나중에 식물 정보 받아오면 그 때 넣자
            time = 600000;
            water = new ImageView(context);
            water.setImageResource(R.drawable.image);

            water.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG, "물을 준다.");
                    //물을 준다.
                    waterState = 0;
                    water.setVisibility(View.INVISIBLE);
                }
            });

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            int[] location = new int[2];
            plant.getLocationOnScreen(location);
            params.leftMargin = location[0] + 100;
            params.topMargin = location[1] - 20;

            relLayout.addView(water, params);
            water.setVisibility(View.INVISIBLE);
        }


        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public ImageView getPlant() {
            return this.plant;
        }

        public int getState() {
            return this.state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public void setLv() {
            this.lv += 1;
        }

        public void remove() {
            relLayout.removeView(this.plant);
            relLayout.removeView(this.water);
            state = 1;
        }

        public void setView() {
            relLayout.addView(this.plant);
            relLayout.addView(this.water);
        }

        public void setTouchistener() {
            plant.setOnTouchListener(this);
            plant.setOnClickListener(this);
        }

        public void clearTouchListener() {
            plant.setOnTouchListener(null);
        }

        public void updateWaterLocation() {

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            int[] location = new int[2];
            plant.getLocationOnScreen(location);
            params.leftMargin = location[0] + 100;
            params.topMargin = location[1] - 170;

            relLayout.updateViewLayout(water, params);
        }

        @Override
        public void onClick(View view) {
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                Log.i(TAG, "ImageVeiw Touch Down");

                moving = false;

                int[] location = new int[2];
                view.getLocationOnScreen(location);

                originalXPos = location[0];
                originalYPos = location[1];
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                moving = true;

                Log.i(TAG, "ImageView Touch Move");

                int x = (int) motionEvent.getRawX();
                int y = (int) motionEvent.getRawY();

                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();

                if (Math.abs(x - originalXPos) < 1 && Math.abs(y - originalYPos) < 1 && !moving) {
                    return false;
                }

                params.leftMargin = x - 170;
                params.topMargin = y - 150;
                relLayout.updateViewLayout(view, params);
                updateWaterLocation();

            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

                Log.i(TAG, "ImageView Touch Up");
                moving = false;
            }
            return false;
        }

        private android.os.Handler mHandler = new android.os.Handler() {
            public void handleMessage(Message msg) {
                Log.i(TAG, "Handler Message : " + waterState);

                //나중에 image가 확립되면 좀더 세부적으로 위치 조정
                if (waterState == 0) {
                    water.setVisibility(View.VISIBLE);
                    waterState = 1;
                } else {
                    //시간 내에 물을 주지 않으면
                    Toast.makeText(context, "물 줘 이뇬아!", Toast.LENGTH_LONG).show();
                }
                mHandler.sendEmptyMessageDelayed(0, time);
            }
        };
    }

    public static void buyNewPlant(int id, int lv, String name, String imagePath) {
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

        relLayout.addView(plant, relParams);
        plantArray.add(new PlantInfo(id, plant, name, lv));
    }

    public static void updatePlant(int id) {
        for (int i = 0; i < plantArray.size(); i++) {
            if (plantArray.get(i).getId() == id) {
                plantArray.get(i).setLv();
            }
        }
    }


    // 김태우
    // 메인부 점수관련 들 밑으로 쭉
    public static void updateSeed(float score) {
        seed.setText(scoreCalculaters((int) score, 0));

    }

    public static void updateFruit(float num) {
        fruit.setText(Float.toString(num));
    }

    public static String scoreCalculaters(int preScore, int preScorePower) {
        int[] arr = new int[7];
        int count = 0;
        char ast = ' ';
        switch (preScorePower) {
            case 0:
                ast = '@';
                break;
            case 3:
                ast = 'A';
                break;
            case 6:
                ast = 'B';
                break;
            case 9:
                ast = 'C';
                break;
            case 12:
                ast = 'D';
                break;

        }

        StringBuilder str = new StringBuilder();
        while (true) {
            if (preScore < 1000) {
                arr[count] = preScore;
                Log.i("setText", "" + arr[count]);
                break;
            }
            arr[count] = preScore % 1000;
            preScore = preScore / 1000;
            Log.i("setText", "" + arr[count]);

            count++;
        }

        for (int j = 0; count >= 0; count--) {
            str.append(String.valueOf(arr[count]));
            str.append((char) (ast + (count)));
            str.append(" ");
            j++;
        }
        if (str.indexOf("@") != -1) {
            str.replace(str.length() - 2, str.length(), "");
        }
        String reStr = String.valueOf(str);
        Log.i("setTest", "" + reStr + " / " + preScore + " / " + preScorePower + " / " + ast);


        return reStr;
    }

    public static String scoreCalculater(String preScore, String manScore, int type) {


        return "String";
    }

    public static void UpScoreTotal() {

        score = score + dryFlowerScore + skill4Effect * ((skillup) * skill0Effect);
    }

    public String getType(int type) {
        String typeName = "";

        switch (type) {
            case 1:
                typeName = "A";
                break;//a
            case 2:
                typeName = "B";
                break;//b
            case 3:
                typeName = "C";
                break;//c
            case 4:
                typeName = "D";
                break;//d
            case 5:
                typeName = "E";
                break;//e
            case 6:
                typeName = "F";
                break;//f
            case 7:
                typeName = "G";
                break;//g
            case 8:
                typeName = "H";
                break;//h
            case 9:
                typeName = "I";
                break;//i
            case 10:
                typeName = "J";
                break;//j
            case 11:
                typeName = "K";
                break;//k
            case 12:
                typeName = "L";
                break;//l
            case 13:
                typeName = "M";
                break;//m
            case 14:
                typeName = "N";
                break;//n
            case 15:
                typeName = "O";
                break;//o
            case 16:
                typeName = "P";
                break;//p
            case 17:
                typeName = "Q";
                break;//q
            case 18:
                typeName = "R";
                break;//r
            case 19:
                typeName = "S";
                break;//s
            case 20:
                typeName = "T";
                break;//t
            case 21:
                typeName = "U";
                break;//u
            case 22:
                typeName = "V";
                break;//v
            case 23:
                typeName = "W";
                break;//w
            case 24:
                typeName = "X";
                break;//x
            case 25:
                typeName = "Y";
                break;//y
            case 26:
                typeName = "Z";
                break;//z
        }

        return typeName;
    }
/*
    public HashMap<Integer, Integer> getScoreHashMap() {

    }
*/

    public static void setScore(HashMap<Integer, Integer> score) {

    }

    public int getScore(int type){
        return 0;
    }
//후
/*
    public HashMap<Integer, Integer> getScoreHashMap() {
        return score;
    }

    public static void setScore(HashMap<Integer, Integer> score) {
        DataList.score = score;
    }

    public int getScore(int type){
        return score.get(type);
    }

    public void setScore(int type, int score) {
        if(score == 0 && this.score.containsKey(type)){
            this.score.remove(type);
        }
        this.score.put(type,score);
    }

    public String getAllScore(HashMap<Integer, Integer> hashMap){

        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(hashMap);
        int index = 0;

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        String scoreString = "";

        iterator.hasNext();
        while(true){
            int type = iterator.next();
            int score = treeMap.get(type);

            scoreString += Integer.toString(score)+getType(type);

            if(index == 0 && iterator.hasNext()){
                scoreString += " + ";
                index = 1;
            }
            else{
                break;
            }
        }

        return scoreString;
    }

    public Boolean minusScore(int type, int score,HashMap<Integer, Integer> hashMap){

        HashMap<Integer, Integer> fakeScore = hashMap;

        Log.i("BuyFlower","Score Contain Key: " + fakeScore.containsKey(type));
        //해당 타입에서 구입할 수 있을 때
        if(fakeScore.containsKey(type)) {
            if (fakeScore.get(type) >= score) {
                Log.i("BuyFlower",type+" : "+fakeScore.get(type));
                int newScore = fakeScore.get(type) - score;
                hashMap.put(type,newScore);
                return true;
            }
        }
        //해당 타입에서 구입할 수 없을 때 경우는 2가지가 있음.
        //1. 해당 타입보다 높은 타입에 금액이 있을 때
        //2. 해당 타입이 가장 높고 점수가 없을 때
        Iterator<Integer> iterator = fakeScore.keySet().iterator();

        while(iterator.hasNext()){
            int scoreType = iterator.next();
            int value = fakeScore.get(scoreType);
            if(scoreType > type){
                if(value-1 < 1){
                    Log.i("BuyFlower","Value-1 = 0 -> value : "+value);
                    hashMap.remove(scoreType);
                }
                else{
                    Log.i("BuyFlower","Value-1 != 0 -> value : "+value);
                    hashMap.put(scoreType,value-1);
                }

                while(true){
                    if(scoreType - 1 == type){
                        int newScore = 1000+hashMap.get(type)-score;
                        hashMap.put(type,newScore);
                        break;
                    }
                    else{
                        hashMap.put(scoreType-1,999);
                        scoreType --;
                    }
                }
                return true;
            }
        }

        return false;
    }

    public void plusScore(int type, int score, HashMap<Integer, Integer> hashMap){

        if(score == 0){
            return;
        }
        if (hashMap.containsKey(type)) {
            if (hashMap.get(type) + score > 999) {
                while (true) {
                    int mok = (hashMap.get(type) + score) / 1000;
                    int nameogi = (hashMap.get(type) + score) % 1000;

                    hashMap.put(type, nameogi);
                    type ++;
                    if (hashMap.containsKey(type)) {
                        int newScore = hashMap.get(type) + mok;
                        if (newScore <= 999) {
                            hashMap.put(type, newScore);
                            return;
                        }
                    } else {
                        hashMap.put(type, mok);
                        return;
                    }
                }
            } else {
                int newScore = hashMap.get(type) + score;
                hashMap.put(type, newScore);
            }
        } else {
            hashMap.put(type, score);
        }
    }

    public void clickScore(){

        Iterator<Integer> iterator = clickScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = clickScore.get(type);

            plusScore(type,score,this.score);
        }
    }

    private String getType(int type){
        String typeName = "";

        switch (type){
            case 1: typeName="A";break;//a
            case 2: typeName="B";break;//b
            case 3: typeName="C";break;//c
            case 4: typeName="D";break;//d
            case 5: typeName="E";break;//e
            case 6: typeName="F";break;//f
            case 7: typeName="G";break;//g
            case 8: typeName="H";break;//h
            case 9: typeName="I";break;//i
            case 10: typeName="J";break;//j
            case 11: typeName="K";break;//k
            case 12: typeName="L";break;//l
            case 13: typeName="M";break;//m
            case 14: typeName="N";break;//n
            case 15: typeName="O";break;//o
            case 16: typeName="P";break;//p
            case 17: typeName="Q";break;//q
            case 18: typeName="R";break;//r
            case 19: typeName="S";break;//s
            case 20: typeName="T";break;//t
            case 21: typeName="U";break;//u
            case 22: typeName="V";break;//v
            case 23: typeName="W";break;//w
            case 24: typeName="X";break;//x
            case 25: typeName="Y";break;//y
            case 26: typeName="Z";break;//z
        }

        return typeName;
    }

    public void ActiveSkillLevelUp(menuActiveSkillActivity.ActiveSkillInform activeSkillInform){

        //Iterator<Integer> iterator = activeSkillInform.getActiveUseCost().keySet().iterator();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = flower.getScore().get(key);

            minusScore(key,value,this.clickScore);
        }

        Log.i("LevelUp","******************************");
        newCost(flower);
        newScore(flower);

        iterator = flower.getScore().keySet().iterator();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = flower.getScore().get(key);

            plusScore(key,value,this.clickScore);
        }
    }

    private void newCost(menuActiveSkillActivity.ActiveSkillInform activeSkillInform){

        int type = 0;
        FlowerData flowerData = flowerDatas.get(flower.getFlowerNo());
        double num1 = Math.pow(2,Math.ceil((flower.getLevel()+flowerData.getNum1())/50));
        double num2 = Math.ceil((flower.getLevel()+flowerData.getNum2()) / flowerData.getNum3());
        double num3 = (flower.getLevel()+flowerData.getNum5()) / flowerData.getNum6();

        if(num1 == (double)0) num1 = 1;
        if(num2 == (double)0) num2 = 1;
        if(num3 == (double)0) num3 = 1;

        double num4 = num1*num2*flowerData.getNum4()*num3;

        while(true){
            double nameogi = num4 % 1000;
            double mok = num4 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,flower.getCost());
            }
            if(mok <1000){
                plusScore(type + 1, (int)mok, flower.getCost());
                break;
            }

            num4 = mok;
            type ++;
        }
    }

    private void newScore(menuActiveSkillActivity.ActiveSkillInform activeSkillInformr){
        int type = 0;
        FlowerData flowerData = flowerDatas.get(flower.getFlowerNo());
        double num1 = Math.pow(2,Math.floor((flower.getLevel()+flowerData.getNum7())/50));
        double num2 = Math.ceil((flower.getLevel()+flowerData.getNum8()) / flowerData.getNum9());

        double num3 = num1*num2/(flowerData.getNum9()*flowerData.getNum10());

        while(true){
            double nameogi = num3 %1000;
            double mok = num3 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,flower.getScore());
            }
            if(mok <1000){
                if(mok != 0)
                    plusScore(type+1,(int)mok,flower.getCost());
                break;
            }

            num3 = mok;
            type ++;
        }
    }


*/
}