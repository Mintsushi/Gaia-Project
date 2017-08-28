package com.example.round.gaia_17;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by 리제 on 2017-08-26.
 */

public class menuFlowerActivity extends Fragment {

    private ArrayList<FlowerInform> mArray = new ArrayList<>();
    private ListView mList;
    private FlowerAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_flower_fragment,container,false);

//        // Button fragment 활성화
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.menuButtonFrameLayout, new menuManagerButtonAcitivity())
//                .commit();

        mAdapter = new FlowerAdapter(getContext(),R.layout.menu_flower_list);
        mList = (ListView)view.findViewById(R.id.menuFlowerList);
        mList.setAdapter(mAdapter);

        mArray.add(new FlowerInform(1,"flower1","EXP Bar","222","콩 콩","22.22B"));
        mArray.add(new FlowerInform(1,"flower2","EXP Bar","650","비 버","399.99A"));
        return view;
    }

    // 플라워 정보 포멧
    public class FlowerInform{

        private int id;
        private String flowerImagePath;
        private String flowerExpBar;
        private String flowerLvText;
        private String flowerNameText;
        private String flowerLvUpText;

        public FlowerInform(int id, String flowerImagePath, String flowerExpBar, String flowerLvText, String flowerNameText, String flowerLvUpText){
             this.id = id;
             this.flowerImagePath = flowerImagePath;
             this.flowerExpBar = flowerExpBar;
             this.flowerLvText = flowerLvText;
             this.flowerNameText = flowerNameText;
             this.flowerLvUpText = flowerLvUpText;
        }

        public int getId(){return this.id;}
        public String getFlowerImagePath(){return this.flowerImagePath;}
        public String getFlowerExpBar(){return this.flowerExpBar;}
        public String getFlowerLvText(){return this.flowerLvText;}
        public String getFlowerNameText(){return this.flowerNameText;}
        public String getFlowerLvUpText(){return this.flowerLvUpText;}
    }

    // 플라워창 리스트 포멧
    static class FlowerViewHolder{
        ImageView flowerImage;
        TextView flowerExpBar;
        TextView flowerLvText;
        TextView flowerNameText;
        ImageButton flowerLvUpButton;
        TextView flowerLvUpText;
    }

    // 플라워 리스트 어뎁터
    public class FlowerAdapter extends ArrayAdapter<FlowerInform> {
        private LayoutInflater mInflater = null;

        public FlowerAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mArray.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent){
            FlowerViewHolder viewHolder;

            if(v == null){
                v=mInflater.inflate(R.layout.menu_flower_list,parent,false);
                viewHolder = new FlowerViewHolder();

                viewHolder.flowerImage=(ImageView) v.findViewById(R.id.flowerImage);
                viewHolder.flowerExpBar=(TextView) v.findViewById(R.id.flowerExpBar);
                viewHolder.flowerLvText=(TextView)v.findViewById(R.id.flowerLvText);
                viewHolder.flowerNameText=(TextView)v.findViewById(R.id.flowerNameText);
                viewHolder.flowerLvUpButton=(ImageButton) v.findViewById(R.id.flowerLvUpButton);
                viewHolder.flowerLvUpText=(TextView)v.findViewById(R.id.flowerLvUpText);

                v.setTag(viewHolder);
            }else{
                viewHolder = (FlowerViewHolder) v.getTag();
            }

            FlowerInform info = mArray.get(position);

            if(info != null){
                int id = info.getId();
                viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                viewHolder.flowerExpBar.setText(info.getFlowerExpBar());
                viewHolder.flowerLvText.setText("LV . " + info.getFlowerLvText());
                viewHolder.flowerNameText.setText(info.getFlowerNameText());
                viewHolder.flowerLvUpButton.setImageResource(R.mipmap.ic_launcher);
                viewHolder.flowerLvUpText.setText(info.flowerLvUpText);
            }

            return v;
        }

    }
}
