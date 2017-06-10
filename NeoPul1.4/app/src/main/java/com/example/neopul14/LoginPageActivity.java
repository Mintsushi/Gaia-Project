package com.example.neopul14;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 리제 on 2017-05-24.
 */

// 로그인 페이지

public class LoginPageActivity  extends AppCompatActivity {

    //id, pw 임시저장소
    String idtemp;
    String pwtemp;
    // id, pw 를 입력받는 EditText
    EditText idEditText;
    EditText pwEditText;

    // 다이얼로그 발생 함수
    // (설명문 / 타이틀 / 버튼이름 )
    void AlertDialogMassage(String setmessage, String settitle, String setpositivebutton){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginPageActivity.this);
        builder.setMessage(setmessage);
        builder.setTitle(settitle)
                .setCancelable(false)
                .setPositiveButton(setpositivebutton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(settitle);
        alert.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        idEditText = (EditText) findViewById(R.id.IDinputEditText);
        pwEditText = (EditText) findViewById(R.id.PWinputEditText);

        // 회원가입창으로 가는 버튼
        Button registerButton = (Button)findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(LoginPageActivity.this, RegisterPageActivity.class));
                finish();
            }

        });

        //로그인 버튼 조건을 만족해야 로그인 됨
        Button loginButton = (Button)findViewById(R.id.LoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //두 임시 저장소에 입력값 저장
                idtemp = idEditText.getText().toString();
                pwtemp = pwEditText.getText().toString();

                // 아이디가 Riza, 비밀번호가 0401이여아먄 로그인 가능 틀렸다면 다이얼로그창으로 알림
                if(idtemp.equals("Riza")) {
                    if (pwtemp.equals("0401")) {
                        startActivity(new Intent(LoginPageActivity.this, MainActivity.class));
                        finish();
                    }
                    else{
                        AlertDialogMassage("PW Error","PW is wrong.","OK");
                    }
                }
                else {
                    AlertDialogMassage("ID Error","ID is wrong.","OK");
                }
            }

        });

    }
}