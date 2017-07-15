package com.example.round.neoful16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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

    private static final String TAG=".MainActivity";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText email;
    private EditText password;
    private String userid;
    private String userpassword;

    Boolean loginChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        editor.putBoolean("autoLogin", true);
        editor.putString("nickname","Round");
        editor.commit();

        if(pref.getBoolean("autoLogin",false)){
            //App의 Main Activity를 실행
            //LoginActivity는 종료
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            setContentView(R.layout.activity_main);

            ImageView startGame = (ImageView)findViewById(R.id.startGame);
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.i(TAG,"OnClick");
                    showLoginAlert();
                }
            });

            ImageView signUp = (ImageView)findViewById(R.id.signUp);
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, SignActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    protected void showLoginAlert(){
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        ImageView login = (ImageView)findViewById(R.id.login);
        CheckBox autoLogin = (CheckBox)findViewById(R.id.checkBox);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid = email.getText().toString();
                userpassword = password.getText().toString();

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
