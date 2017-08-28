package com.example.round.gaia_17;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuStoreActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment,container,false);

        return view;
    }
}
