package com.example.round.google_login_example;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
    , GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = ".MainActivity";

    private LinearLayout prof_Section;
    private Button signOut;
    private SignInButton SignIn;
    private TextView Name, Email;
    private ImageView prof_pic;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prof_Section = (LinearLayout)findViewById(R.id.prof_section);
        signOut = (Button)findViewById(R.id.btn_logout);
        SignIn = (SignInButton)findViewById(R.id.btn_login);

        Name = (TextView)findViewById(R.id.name);
        Email = (TextView)findViewById(R.id.email);
        prof_pic = (ImageView)findViewById(R.id.prof_pic);

        SignIn.setOnClickListener(this);
        signOut.setOnClickListener(this);
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(
                Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        prof_Section.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.btn_login){
            signIn();
        }
        else if(view.getId() == R.id.btn_logout){
            signOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn(){
        Log.i(TAG,"SignIn");

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent,REQ_CODE);
    }

    private void signOut(){
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });
    }

    private void handleResult(GoogleSignInResult result){
        Log.i(TAG,"Handle Result : "+result.isSuccess());

        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();

            String name = account.getDisplayName();
            String email = account.getEmail();
            String image_url = null;
            if(account.getPhotoUrl() != null)
                image_url = account.getPhotoUrl().toString();

            Name.setText(name);
            Email.setText(email);
            if(image_url != null)
                Glide.with(this).load(image_url).into(prof_pic);
            updateUI(true);
        }
        else{
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin){
        if(isLogin){
            prof_Section.setVisibility(View.VISIBLE);
            SignIn.setVisibility(View.GONE);
        }
        else{
            prof_Section.setVisibility(View.GONE);
            SignIn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        Log.i(TAG,"onActivityResult : "+requestCode);
        if(requestCode == REQ_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);
        }
    }

}
