package com.example.neopul14;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by 리제 on 2017-05-09.
 */

// 상점화면에서 플레그먼트형태로 꽃구매리스트를 만들어주는 코드

public class StoreFlowerActivity extends Fragment
{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // activity_store_flower화면을 프래그먼트에 연결
        View view = inflater.inflate(R.layout.activity_store_flower,container,false);
        // 꽃 데이터들
        final FlowerData flowerdata = new FlowerData();

        // 리스트 Adapter 생성
        ListViewAdapter adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        ListView listView = (ListView)view.findViewById(R.id.flowerList);
        listView.setAdapter(adapter);

        //리스트뷰에 꽃데이터를 표시
        for(int a = 0; flowerdata.getDataCount() > a; a++) {
            adapter.addItem(getActivity().getDrawable(flowerdata.getImages(a)), flowerdata.getnames(a), flowerdata.getnames(a));
        }



        //구매리스트의 아이템 클릭시 이밴트
        // 구매확인창 ( StoreFlowerInfomationActivity )으로 가며 구매한 아이템의 번호를 들고 간다
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> parent, View view, int i,long id){
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FL, new StoreFlowerInfomationActivity(i))
                                .commit();
                    }
                }
        );


        return view;
    }

}