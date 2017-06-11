package com.example.neopul14;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 리제 on 2017-05-24.
 */

public class RegisterPageActivity extends AppCompatActivity {

    // count는 로그인 조건의 부함할경우를 확인하는데 사용
    int[] count = new int[] {0,0,0,0};
    // 입력받는창 //존나많네
    EditText idEditText;
    EditText pwEditText;
    EditText repwEditText;
    EditText nicnameEditText;
    EditText emailEditText;
    String idtemp;
    String pwtemp;
    String repwtemp;
    String nictemp;
    String emailtemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpage);

        // 활성화
         idEditText = (EditText)findViewById(R.id.IDinputEditText);
         pwEditText = (EditText)findViewById(R.id.PWinputEditText);
         repwEditText = (EditText)findViewById(R.id.RePWEditText);
         nicnameEditText = (EditText)findViewById(R.id.nicnameEditText);
         emailEditText = (EditText)findViewById(R.id.maillEditText);


        Button idButton = (Button)findViewById(R.id.IDButton);
        idButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                idtemp = idEditText.getText().toString();
                // 다르면 if 같으면 else
                if(!(idtemp.equals("Riza"))){
                    AlertDialogMassage("ID Error","Duplicate ID is duplicated.","OK");
                }
                else{
                    AlertDialogMassage("ID OK","Use the following ID : " + idtemp,"OK");
                    count[0] = 1;
                    //입력그만받기 넣기
                }
            }

        });

        Button nicnameButton = (Button)findViewById(R.id.nicnameButton);
        nicnameButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                nictemp = nicnameEditText.getText().toString();
                // 다르면 if 같으면 else
                if(!(nictemp.equals("Riza"))){
                    AlertDialogMassage("NicName Error","Duplicate NicName is duplicated.","OK");
                }
                else{
                    AlertDialogMassage("NicName OK","Use the following NicName. \n" +" : "+ nictemp,"OK");
                    count[2] = 1;
                    //입력그만받기 넣기
                }
            }

        });



        Button nextButton = (Button)findViewById(R.id.joinButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
            if(count[0]==1){
                PWcheck();
                if(count[1]==1){
                    if(count[2]==1){
                        Emailcheck();
                        if(count[3]==1){
                            AlertDialogMassage("Regester OK","Wellcome to Join","OK");
                            startActivity(new Intent(RegisterPageActivity.this, LoginPageActivity.class));
                            finish();
                        }
                        else {
                            AlertDialogMassage("EMail Check Error","Not in E-Mail format.","OK");
                        }
                    }
                    else{
                        AlertDialogMassage("NicName Check Error","Plese Check NicName.","OK");
                    }
                }
                else {
                    AlertDialogMassage("PW Check Error","PW and RePW is different.","OK");
                }
            }
            else {
                AlertDialogMassage("ID Check Error","Plese Check ID.","OK");
            }


            }

        });

        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                startActivity(new Intent(RegisterPageActivity.this, LoginPageActivity.class));
                finish();
            }

        });
    }

    void PWcheck(){
        pwtemp = pwEditText.getText().toString();
        repwtemp = repwEditText.getText().toString();
        if(pwtemp.equals(repwtemp)){
            count[1] = 1;
        }
        else{

        }
    }

    void Emailcheck(){
        emailtemp = emailEditText.getText().toString();
        if(emailtemp.indexOf("@")!=0){
            if(emailtemp.indexOf(".")!=0){
                count[3]=0;
            }
        }
    }
    void AlertDialogMassage(String setmessage, String settitle, String setpositivebutton){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPageActivity.this);
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
}