package com.example.neopul14;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    View header;

    TextView nicknameText;
    TextView emailText;
    TextView seedText;
    TextView fruitnumText;

    // 뒤로가기 터치 이밴트 변수
    BackPressCloseHandler backPressCloseHandler;
    //임시저장소
    int temp;

    // 뷰의 롱터치 이밴트를위한 변수
    int touchtype = 0;

    // 꽃이미지뷰 9개 변수
    ImageView plantImage[];

    // 9개의 이미지뷰중 어느 뷰에 꽃이 있는지 알려주기 위한 변수
    int imageopen[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //네비게이션드로우
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        // 회원정보 네비게이션드로우
        nicknameText = (TextView)header.findViewById(R.id.nicknameText);
        nicknameText = (TextView)header.findViewById(R.id.nicknameText);
        emailText = (TextView)header.findViewById(R.id.emailText);
        seedText = (TextView)header.findViewById(R.id.seednumText);
        fruitnumText = (TextView)header.findViewById(R.id.fruitnumText);



        // 뒤로가기 터치 관리자
        backPressCloseHandler = new BackPressCloseHandler(this);

        //튤바 활성화
        // 솔직히 잘 모름
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        // 상점으로 가는 버튼 >> StoreMainActivity.class
        Button storeButton = (Button) findViewById(R.id.storeButton);
        storeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StoreMainActivity.class));
                //finish();
            }

        });
        //활성화
       final int imageopen[] = new int[9];

        // 정원창에 9개의 이미지뷰 생성
        plantImage = new ImageView[9];
        plantImage[0] = (ImageView) findViewById(R.id.plantImage1);
        plantImage[1] = (ImageView) findViewById(R.id.plantImage2);
        plantImage[2] = (ImageView) findViewById(R.id.plantImage3);
        plantImage[3] = (ImageView) findViewById(R.id.plantImage4);
        plantImage[4] = (ImageView) findViewById(R.id.plantImage5);
        plantImage[5] = (ImageView) findViewById(R.id.plantImage6);
        plantImage[6] = (ImageView) findViewById(R.id.plantImage7);
        plantImage[7] = (ImageView) findViewById(R.id.plantImage8);
        plantImage[8] = (ImageView) findViewById(R.id.plantImage9);

        // 9개의 창 모두 비활성화
        //View.VISIBLE / View.INVISIBLE
        //활성   / 비활성 /
        for(int a =0; 9>a; a++){
            plantImage[a].setVisibility(View.INVISIBLE);
            imageopen[a]=0;
        }

        //3번 이미지뷰에 꽃이 있다는 정보 입력
        imageopen[3]=1;

        // imageopen의 정보를 받아 해당 번호의 화분 활성화
        // 화면 활성화시 이런 식으로 변경 해야함
        for(int a = 0; a<9; a++) {
            if (imageopen[a] == 1) {
                plantImage[a].setVisibility(View.VISIBLE);
                plantImage[a].setImageResource(R.drawable.rose_brown);
                imageopen[a] = 1;
            }
        }

        // 이미지를 누르면 관리창으로 가는 버튼
        for(int a =0; 9>a; a++){
            plantImage[a].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), PlantManagementActivity.class));
                    //finish();
                }
            });

            //이미지 롱 클릭 이밴트 ( 화분 자리바꾸기에 쓸 예정 =)
            // ++++ 수정 ++++ 오버레이에 사용할 예정 임시로 화분자리 바꾸기로 남겨둠
            plantImage[a].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    touchtype = 1;
                    // 해당 아이디를 찾음
                    for(int i=0; i<9; i++){
                        if(plantImage[i].getId()==v.getId()){ temp=i; }
                    }
                    //클릭된거 이미지 투명화
                    plantImage[temp].setVisibility(View.INVISIBLE);

                    // 빈공간의 이미지 활성화
                    for(int a =0; 9>a; a++){
                        if(imageopen[a]==0) {
                            plantImage[a].setVisibility(View.VISIBLE);
                        }
                    }

                    // 이미지 클릭시 클릭한 곳의 이미지 활성화
                    for(int a =0; 9>a; a++) {
                        if(imageopen[a]==0) {
                            plantImage[a].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    touchtype=0;
                                    //이전 이미지 0
                                    imageopen[temp]=0;
                                    //새이미지 버튼입히기
                                    for(int i=0; i<9; i++){
                                        if(plantImage[i].getId()==v.getId()){ temp=i; }
                                    }
                                    plantImage[temp].setImageResource(R.drawable.rose_brown);
                                    imageopen[temp]=1;

                                    // 화면 비활성화
                                    for (int a = 0; 9 > a; a++) {
                                        if (imageopen[a] == 0) {
                                            plantImage[a].setVisibility(View.INVISIBLE);
                                            plantImage[a].setImageResource(R.drawable.plant);
                                        }
                                    }
                                    for(int a =0; 9>a; a++) {
                                        plantImage[a].setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                startActivity(new Intent(getApplicationContext(), PlantManagementActivity.class));
                                                finish();
                                            }
                                        });
                                    }
                                }
                            });
                        }

                    }
                    return true;
                }

            });

        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    // 이 밑으로는 클릭 이밴트들
    // 왼쪽 슬라이드 캘랜더로 가계해주고

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
            finish();
        } else if (id == R.id.logOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("LogOut??");
            builder.setTitle("LogOut")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            startActivity(new Intent(getApplicationContext(), LoginPageActivity.class));
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            return;
                        }
                    });
            AlertDialog alert = builder.create();
            alert.setTitle("LogOut");
            alert.show();
        } else if (id == R.id.seting) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    // 메인 뒤로가기터치
    // 뒤로가기 터치시
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }

}

class BackPressCloseHandler {

    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public BackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(activity,
                "\'Back\'Press the button once more to exit.", Toast.LENGTH_SHORT);
        toast.show();
    }
}
