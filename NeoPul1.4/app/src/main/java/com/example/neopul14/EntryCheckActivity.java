package com.example.neopul14;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by 리제 on 2017-05-28.
 */

// 화분을 다 키웠을시 나오는 응모 Fragment창 자바 코드
// view_entry

public class EntryCheckActivity extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 화면을 프래그먼트에 연결
        View view = inflater.inflate(R.layout.view_entry, container, false);

        // OK 버튼 화분삭제 및 응모 > 정원창으로

        Button entryOkButton = (Button) view.findViewById(R.id.entryokButton);
        entryOkButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        // No 버튼 화분을 놔두고 정원창으로
        Button entryNoButton = (Button) view.findViewById(R.id.entrynoButton);
        entryNoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }
}