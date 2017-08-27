package com.example.round.gaia_17;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuManagerButtonAcitivity extends Fragment {

    ImageButton menuFlowerButton;
    ImageButton menuASkillButton;
    ImageButton menuPSkillButton;
    ImageButton menuOverlayButton;
    ImageButton menuStoreButton;
    ImageButton menuDownBuootn;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_manager_button, container, false);

        menuFlowerButton = (ImageButton)view.findViewById(R.id.menuFlowerButton);
        menuASkillButton = (ImageButton)view.findViewById(R.id.menuASkillButton);
        menuPSkillButton = (ImageButton)view.findViewById(R.id.menuPSkillButton);
        menuOverlayButton = (ImageButton)view.findViewById(R.id.menuOverlayButton);
        menuStoreButton = (ImageButton)view.findViewById(R.id.menuStoreButton);
        menuDownBuootn = (ImageButton)view.findViewById(R.id.menuDownButton);


        menuFlowerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuFlowerActivity())
                        .commit();
            }
        });

        menuASkillButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuActiveSkillActivity())
                        .commit();
            }
        });

        menuPSkillButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuPassiveSkillActivity())
                        .commit();
            }
        });

        menuOverlayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuOverlayActivity())
                        .commit();
            }
        });

        menuStoreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.menuFragment, new menuStoreActivity())
                        .commit();
            }
        });

        menuDownBuootn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }
}
