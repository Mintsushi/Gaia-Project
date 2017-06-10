package com.example.neopul14;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by 리제 on 2017-05-09.
 */

public class StoreItemActivity extends Fragment
{
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.activity_store_item,null);
        final ItemData itemdata = new ItemData();

        // Adapter 생성
        ListViewAdapter adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        ListView listView = (ListView)view.findViewById(R.id.itemList);
        listView.setAdapter(adapter);

        for(int a = 0; itemdata.getDataCount() > a; a++) {
            adapter.addItem(getActivity().getDrawable(itemdata.getImages(a)), itemdata.getnames(a), itemdata.getnames(a));
        }


        //아이템 클릭시 이밴트
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view, int i,long id){
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FL, new StoreItemInfomationActivity(i))
                                .commit();

                    }
                }
        );

        return view;
    }
}
