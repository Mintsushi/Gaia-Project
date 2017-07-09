package com.example.round.neopul15;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 리제 on 2017-07-09.
 */

public class PlantManagementInformActivity extends Fragment{

    ImageButton back;
    ImageView flower;
    TextView name;
    TextView inform;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_infomation_view, container, false);

        back = (ImageButton) getActivity().findViewById(R.id.informBackBtn);
        flower = (ImageView) getActivity().findViewById(R.id.informflowerImage);
        name = (TextView) getActivity().findViewById(R.id.informflowerNameText);
        inform = (TextView) getActivity().findViewById(R.id.informflowerInfoText);


        return view;
    }
}
