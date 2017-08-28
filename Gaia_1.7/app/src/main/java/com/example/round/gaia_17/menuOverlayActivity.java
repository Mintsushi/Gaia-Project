package com.example.round.gaia_17;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
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
        MainActivity.PlantInfo info;

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

            info = plantArray.get(position);

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
                    if(info.getState() == 0){
                        if(mOverlayService.onTest(info.getId())){
                            mOverlayService.addPlant(info);
                            info.setState(1);
                            viewHolder.overlayButton.setText("OFF");
                        }
                    }
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
}
