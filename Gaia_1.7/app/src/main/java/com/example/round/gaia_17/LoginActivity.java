package com.example.round.gaia_17;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = ".LoginActivity";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //SharedPreference -> 로그인을 성공하였다면, 다음번 실행부터는 로그인 과정 제외
        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        editor.putBoolean("successLogin",true);
        editor.putString("id","1");

        //기존에 로그인 성공
        if(pref.getBoolean("successLogin",false)){
            //바로 MainActivity로 이동, LoginActivity 종료
            //Intent intent = new Intent(LoginActivity.this,MainActivty.class);
            //startActivity(intent);
            //finish();
        }

        else {
            //로그인 없이 게임 시작
            Button normalLoginButton = (Button) findViewById(R.id.start);
            normalLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestID();
                }
            });
        }
    }

    //서버로부터 사용자 Unique Number를 받아오는 함수
    private void requestID( ){

        //사용자 Unique Number를 발급받아오는 URL
        String url = null;

        //사용자 Unique Number 발급
        StringRequest request = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){

                        Log.i(TAG,"getID / onResponse : "+response);

                        //Response = 사용자의 Unique Number(String)
                        //Response = "false" -> Unique Number 발급 실패
                        if(!response.equals("false")){
                            editor.putBoolean("successLogin",true);
                            editor.putString("id",response);

                            //Unique Number 발급 성공 후 MainActivity로 이동
                            //LoginActivity는 종료
                            //Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                            //startActivity(intent);
                            //finish();
                        }
                        else{//Response == false
                            //사용자에게 Login에 실패했다는 메세지를 띄어줌.
                            Toast.makeText(getApplicationContext(),"Failed the Login",Toast.LENGTH_LONG).show();
                        }
                    }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i(TAG,"getID / onErrorResponse : "+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
