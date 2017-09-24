package com.example.round.gaia_18.Fragement;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.round.gaia_18.R;

public class MenuStore extends Fragment{

    private ListView storeList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment,container,false);

        storeList = (ListView)view.findViewById(R.id.storeList);
        return view;
    }
}
