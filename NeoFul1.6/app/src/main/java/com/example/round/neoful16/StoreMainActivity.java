package com.example.round.neoful16;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Round on 2017-07-16.
 */

public class StoreMainActivity extends AppCompatActivity{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private TextView nickname, email, seed, fruit;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_main);

        pref = getApplicationContext().getSharedPreferences("Login",getApplicationContext().MODE_PRIVATE);
        editor = pref.edit();

        getUserInform();

        getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreItemActivity()).commit();

        Button itemButton = (Button)findViewById(R.id.itemStoreButton);
        Button flowerButton = (Button)findViewById(R.id.flowerStoreButton);

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreItemActivity()).commit();
            }
        });

        flowerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.FL,new StoreFlowerActivity()).commit();
            }
        });
    }
    private void getUserInform(){
        String url = "http://202.31.200.143/user/"+pref.getString("id","");

        JsonObjectRequest request = new JsonObjectRequest(url,null,
                new Response.Listener<JSONObject>(){

                    @Override
                    public void onResponse(JSONObject response){
                        Log.i("StartActivity","getUserInform : "+response.toString());

                        nickname =(TextView)findViewById(R.id.storeNickname);
                        email = (TextView)findViewById(R.id.storeEmail);
                        seed = (TextView)findViewById(R.id.storeseed);
                        fruit = (TextView)findViewById(R.id.storefruit);

                        try{
                            String name = response.getString("nickname");
                            int getSeed = response.getInt("seed");
                            int getFruit = response.getInt("fruit");

                            // f재화 저장
                            editor.putInt("PresentSeed",getSeed);
                            Log.i("startSeed",String.valueOf(getSeed));
                            editor.putInt("PresentFruit",getFruit);
                            editor.commit();

                            nickname.setText(name);
                            email.setText(pref.getString("id",""));
                            seed.setText(Integer.toString(getSeed));
                            fruit.setText(Integer.toString(getFruit));
                        }catch (JSONException e){
                            Log.i("MainActivity","JSONException :"+e.toString());
                        }
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("MainActivity","getUserInform Error"+error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
