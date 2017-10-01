package com.example.round.gaia_18;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.SignInButton;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    //login Button
    private Button startGame;
    private SignInButton googleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        editor.putString("LoginType","Google");
        editor.putString("Email","riza_e41@gmail.com");
        editor.putBoolean("autoLogin", true);
        editor.putString("nickname","Round");
        editor.commit();

        if(pref.getBoolean("autoLogin",false)) {
            //자동 로그인
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            setContentView(R.layout.activity_login);

            startGame = (Button)findViewById(R.id.startGame);
            startGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //사용자 unique ID 발급
                    //requestUniqueId();
                }
            });
        }
    }
}
