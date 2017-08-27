package com.example.round.gaia_17;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

    private ArrayList<PlantInfo> plantArray = new ArrayList<>();
    //클릭 개수 : 0~999
    private float score;
    //클릭 개수 : A,B,C,D.....
    private int clickLevel;

    private TimerTask mTask;
    private Timer mTimer;

    Button menuUpButton;

    @Override
    public void onClick(View view){
        score++;
        seed.setText(Float.toString(score));
        Log.i(TAG,"Score : "+Float.toString(score));
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        seed = (TextView)findViewById(R.id.seed);

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
                Toast.makeText(getApplicationContext(),"서버에 저장되었습니다.",Toast.LENGTH_LONG).show();
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

        testSource();

        // 메뉴버튼 활성화
        menuUpButton = (Button)findViewById(R.id.menu);
        menuUpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuFlowerActivity())
                        .commit();
            }
        });
    }

    private void testSource(){

        ImageView imageView = new ImageView(this);

        imageView.setImageResource(R.drawable.image);
        imageView.setId(0);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(imageView,relParams);
        plantArray.add(new PlantInfo(0,imageView));
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

        private Boolean moving = false;
        private int originalXPos, originalYPos;

        public PlantInfo(int id, ImageView plant){
            this.id = id;
            this.plant = plant;
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

//            if (Math.abs(x - originalXPos) < 1 && Math.abs(y - originalYPos) < 1 && !moving) {
//                return false;
//            }

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
