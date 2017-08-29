package com.example.round.gaia_17;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import static com.example.round.gaia_17.MainActivity.mOverlayService;
import static com.example.round.gaia_17.MainActivity.plantArray;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuOverlayActivity extends Fragment {

    private static final String TAG =".menuOverlayActivity";

    private ListView flowerList;
    private FlowerAdapter flowerAdpater;
    private AlertDialog alertDialog=null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_overlay_fragment,container,false);
        // Button fragment 활성화

        flowerAdpater = new FlowerAdapter(getContext(),R.layout.menu_overlay_item);
        flowerList = (ListView)view.findViewById(R.id.menuOverlayList);
        flowerList.setAdapter(flowerAdpater);

        return view;
    }

    // 플라워창 리스트 포멧
    static class FlowerViewHolder{
        ImageView flowerImage;
        TextView flowerName;
        Button overlayButton;
    }

    public class FlowerAdapter extends ArrayAdapter<MainActivity.PlantInfo> {

        private LayoutInflater mInflater = null;

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return plantArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            final FlowerViewHolder viewHolder;

            if(v == null){
                v=mInflater.inflate(R.layout.menu_overlay_item,parent,false);
                viewHolder = new FlowerViewHolder();

                viewHolder.flowerImage=(ImageView) v.findViewById(R.id.flowerImage);
                viewHolder.flowerName=(TextView) v.findViewById(R.id.flowerName);
                viewHolder.overlayButton = (Button)v.findViewById(R.id.overlayButton);

                v.setTag(viewHolder);
            }else{
                viewHolder = (FlowerViewHolder) v.getTag();
            }

            final MainActivity.PlantInfo info = plantArray.get(position);

            if(info != null){
                int id = info.getId();
                viewHolder.flowerImage.setImageResource((Integer)info.getPlant().getTag());
                viewHolder.flowerImage.setTag(info.getPlant().getTag());
                viewHolder.flowerName.setText(info.getName());
                if(info.getState() == 1){
                    viewHolder.overlayButton.setText("OFF");
                }
            }

            viewHolder.overlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Overlay View에 없을 때
                    if(info.getState() == 0){
                        if(mOverlayService.onTest(info.getId())){
                            if(mOverlayService.addPlant(info)) {
                                info.setState(1);
                                viewHolder.overlayButton.setText("OFF");
                            }
                            else{
                                setGPS();
                            }
                        }
                    }
                    //Overlay View에 있을 때
                    else{
                        mOverlayService.removePlant(info.getId());
                        info.setState(0);
                        viewHolder.overlayButton.setText("ON");
                    }
                }
            });
            return v;
        }
    }

    public void setGPS(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_gps, null);

        TextView warn = (TextView)dialogView.findViewById(R.id.warn);
        warn.setText("GPS Setting이 되어 있지 않아. 외부기능 사용이 불가능합니다.\n 외부기능을 사용하고 싶으시면 확인버튼을 클릭하여 GPS를 실행시켜주세요.");
        Button cancel = (Button)dialogView.findViewById(R.id.cancel);
        Button submit = (Button)dialogView.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOverlayService.enableOverlayService = true;
                alertDialog.cancel();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        dialogBuilder.setView(dialogView);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
