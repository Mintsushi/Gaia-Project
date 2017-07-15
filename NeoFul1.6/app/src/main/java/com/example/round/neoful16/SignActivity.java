package com.example.round.neoful16;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

/**
 * Created by Round on 2017-07-14.
 */

public class SignActivity extends AppCompatActivity {

    private EditText id, username, password;
    private String userid, nickname, userpassword;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        id = (EditText)findViewById(R.id.signEmail);
        username = (EditText)findViewById(R.id.nickname);
        password = (EditText)findViewById(R.id.signPassword);

        //회원가입 버튼
        ImageView signUp = (ImageView)findViewById(R.id.Register);
        //회원가입 버튼 클릭시
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //사용자가 입력한 id, nickname,password에 대한 정보를 가져옴.
                userid = id.getText().toString();
                nickname = username.getText().toString();
                userpassword = password.getText().toString();

                //사용자의 정보를 server로 전송하여, 회원가입 가능 여부 진단
                signUpRequest();
            }
        });
    }

    //Server로부터 회원가입 가능 여부 진단
    private void signUpRequest(){

        //회원가입 기능을 하는 Server URL
        String url = "http://202.31.200.143/signup";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>(){

                    @Override
                    public void onResponse(String response){
                        //회원가입에 성공하였다면 response="true"
                        if(response.equals("true")){
                            //사용자에게 회원가입에 성공했다는 메세지를 띄워줌.
                            Toast.makeText(getApplicationContext(),"Success Sign Up",Toast.LENGTH_LONG).show();
                            //회원가입에 성공하였으므로, 로그인 page로 돌아감.
                            Intent intent = new Intent(SignActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            //회원가입에 실패하였으므로, 사용자에게 실패 메세지를 띄어줌.
                            Toast.makeText(getApplicationContext(),"회원가입에 실패하였습니다.",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("SignActivity","onErrorResponse : "+error.toString());
            }
        }){
            @Override
            protected Map<String, String > getParams(){

                Map<String, String> params = new HashMap<String, String>();

                //사용자가 입력한 userid, nickname, password를 server로 전송
                params.put("email",userid);
                params.put("nickname",nickname);
                params.put("password",userpassword);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
