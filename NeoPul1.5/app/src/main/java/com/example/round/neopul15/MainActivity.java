package com.example.round.neopul15;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    EditText id,password;
    String userid,userpassword;
    CheckBox autoLogin;
    Boolean loginChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//SharedPreference 사용하기 위한 설정
        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        //사용자가 Login인시 autoLogin을 체크하였다면 SharedPreference에 autoLogin을 true로 set
        //그 다음부터 app을 실행시 자동로그인
//
//        editor.putBoolean("autoLogin", true);
//        editor.putString("nickname","Round");
//        editor.commit();


        if(pref.getBoolean("autoLogin",false)){
            //App의 Main Activity를 실행
            //LoginActivity는 종료
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }

        else{
            setContentView(R.layout.activity_main);

            //사용자가 입력한 id,password 정보와 autoLogin CheckBox check 유무에 대한 정보
            id = (EditText)findViewById(R.id.login_email);
            password = (EditText)findViewById(R.id.login_password);
            autoLogin = (CheckBox)findViewById(R.id.checkBox);

            Button sign =(Button)findViewById(R.id.sign); //회원가입 버튼
            Button login = (Button)findViewById(R.id.login); //로그인 버튼

            //회원가입 버튼을 클릭하였을 때
            //회원가입 Activity 로 이동
            //회원가입 Page 에서 BackButton 을 눌렀을 시 다시 login 화면으로 돌아올수 있도록 Activity 는 종료시키지 않음.
            sign.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SignActivity.class);
                    startActivity(intent);
                }
            });

            //로그인 버튼을 클릭하였을 때
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //사용자가 입력한 id와 password 정보를 가져옴.
                    userid = id.getText().toString();
                    userpassword = password.getText().toString();

                    //server로 id와 password를 전송해 로그인 가능 여부 진단
                    sendUserInform();
                }
            });

            //autoLogin CheckBox
            autoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    //check
                    if(isChecked){
                        loginChecked = true;
                    }

                    //non check
                    else{
                        loginChecked = false;
                        //SharedPreference 초기화
                        editor.clear();
                        editor.commit();
                    }
                }
            });
        }
    }

    //Server로부터 login 가능 여부 진단
    public void sendUserInform(){

        //login 기능을 하는 Server URL
        String registUrl = "http://202.31.200.143/login";

        StringRequest requestLogin = new StringRequest(Request.Method.POST,registUrl,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.i("MainActivity","Response : "+response);

                        editor.putString("id",userid);
                        editor.commit();

                        //login을 성공하였다면
                        //response=wearableID
                        //if(!response.equals("false")){
                        if(!response.equals("false")){

                            editor.putString("nickname", response);

                            //자동로그인이 체크되어있다면
                            if(loginChecked) {
                                //SharedPreference에 사용자의 password와 autoLogin이라는 이름으로 true값 저장
                                editor.putString("password", userpassword);
                                editor.putBoolean("autoLogin", true);
                                editor.commit();
                            }
                            //Login에 성공하였으므로 App의 MainActivity 실행
                            Intent intent = new Intent(MainActivity.this, StartActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            //Login에 실패하였으므로 사용자에게 Login에 실패했다는 메세지를 띄워줌.
                            Toast.makeText(getApplicationContext(),"Failed the Login",Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("MainActivity","onErrorResponse : "+error.toString());
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                //Server로 사용자 정보 전송
                //"email" : 사용자의 id/email
                //"password" : 사용자의 비밀번호
                params.put("email",userid);
                params.put("password",userpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(requestLogin);
    }
}
