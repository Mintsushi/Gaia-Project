package com.example.round.neopul15;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
 * Created by Round on 2017-06-13.
 */

public class StoreItemInformationActivity extends Fragment{

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private StoreItemActivity.ItemInfo item;

    public void setItem(StoreItemActivity.ItemInfo item){this.item = item;}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View view = inflater.inflate(R.layout.activity_item_inform,container,false);

        pref = getContext().getSharedPreferences("Login",getContext().MODE_PRIVATE);
        editor = pref.edit();

        TextView name = (TextView)view.findViewById(R.id.item_Name);
        ImageView image = (ImageView)view.findViewById(R.id.item_Image);
        TextView inform = (TextView)view.findViewById(R.id.item_inform);
        TextView cost = (TextView)view.findViewById(R.id.item_Cost);

        name.setText(item.getName());
        int id = getResources().getIdentifier(item.getPath(),"drawable",getActivity().getPackageName());
        image.setImageResource(id);
        inform.setText(item.getInfo());
        cost.setText(Integer.toString(item.getCost()));

        Button buy = (Button)view.findViewById(R.id.buy);
        Button cancel = (Button)view.findViewById(R.id.cancel);

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("You Really Buy this Item?")
                        .setTitle("Buy Item")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                buyItem();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getActivity().getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.FL,new StoreItemActivity())
                                        .commit();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FL, new StoreItemActivity())
                        .commit();
            }
        });

        return view;
    }

    private void buyItem(){

        String url="http://202.31.200.143/buy/item";

        StringRequest request = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response){
                        Log.i("StoreItemActivity","Response : "+response);


                            if (response.equals("true")) {
                                Toast.makeText(getContext(), "Success Buy Item", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(getContext().getApplicationContext(), StartActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            } else {
                                Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                            }


                }
                },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.i("StoreItemActivity","onErrorResponse : "+error.toString());
            }
        }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();

                params.put("email",pref.getString("id",""));
                params.put("item",Integer.toString(item.getId()));
                //추가코드
                Log.i("massage OK","tq");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }
//추가코드
    boolean CostCalculate(int type){
        int TotalCost = item.getCost();
        int CostType = type;
        int money;

        // Seed구매
        if(CostType == 0){
            money = pref.getInt("PresentSeed",0);

            if(TotalCost > money){
                Log.e("Cost","Be short of Seed ");
                return false;
            }else{
                Log.i("Cost","Be Seed OK ");
                return true;
            }
        }
        // fruit구매
        else{
            money = pref.getInt("PresentFruit",0);

            if(TotalCost > money){
                Log.e("Cost","Be short of Fruit ");
                return false;
            }else{
                money = money - TotalCost;
                editor.putInt("PresentFruit",money);
                editor.commit();
                return true;
            }
        }
    }
}
