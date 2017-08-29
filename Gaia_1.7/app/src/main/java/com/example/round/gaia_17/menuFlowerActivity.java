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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.plantArray;
import static com.example.round.gaia_17.MainActivity.relLayout;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.mArray;
/**
 * Created by 리제 on 2017-08-26.
 */

public class menuFlowerActivity extends Fragment {

    //private ArrayList<FlowerInform> mArray = new ArrayList<>();
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

        // 모든 꽃 데이터 저장 (서버에서 받아올경우

        mArray.add(new FlowerInform(0,"flower1","EXP Bar","0","콩 콩","222",0));
        mArray.add(new FlowerInform(1,"flower2","EXP Bar","0","비 버","39",0));
        mArray.add(new FlowerInform(2,"flower2","EXP Bar","0","핑크비버","80",0));
        mArray.add(new FlowerInform(3,"flower2","EXP Bar","0","빨강비버","245",0));
        mArray.add(new FlowerInform(4,"flower2","EXP Bar","0","적색비버","399",0));
        mArray.add(new FlowerInform(5,"flower2","EXP Bar","0","붉은비버","654",0));
        mArray.add(new FlowerInform(6,"flower2","EXP Bar","0","빨간비버","3333",0));

        // 사용자의 꽃정보 받아오기
        mArray.set(0,new FlowerInform(0,"flower1","EXP Bar","222","콩 콩","222",1));

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
        private int buyType;

        public FlowerInform(int id, String flowerImagePath, String flowerExpBar, String flowerLvText, String flowerNameText, String flowerLvUpText, int buyType){
             this.id = id;
             this.flowerImagePath = flowerImagePath;
             this.flowerExpBar = flowerExpBar;
             this.flowerLvText = flowerLvText;
             this.flowerNameText = flowerNameText;
             this.flowerLvUpText = flowerLvUpText;
             this.buyType = buyType;
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
                final int id = info.getId();

                //buyType ==0 이면 잠긴이미지
                if(info.buyType == 0) {
                    viewHolder.flowerImage.setImageResource(R.mipmap.ic_launcher);
                }else {
                    viewHolder.flowerImage.setImageResource(R.drawable.image);
                }
                viewHolder.flowerExpBar.setText(info.getFlowerExpBar());
                viewHolder.flowerLvText.setText("LV . " + info.getFlowerLvText());
                viewHolder.flowerNameText.setText(info.getFlowerNameText());
                viewHolder.flowerLvUpText.setText(info.flowerLvUpText);

                //buyType ==0 이면 잠긴이미지
                if(info.buyType == 0) {
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.buy);
                }else{
                    viewHolder.flowerLvUpButton.setImageResource(R.drawable.levelup);
                }

                //버튼 이밴트
                viewHolder.flowerLvUpButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Log.i("id :", String.valueOf(id));
                        FlowerInform temp = mArray.get(id);

                        // 스코어로 레벨 올리기

                        if(scoreDoun(Float.parseFloat(temp.getFlowerLvUpText()))){
                            Log.i("레벨업 이벤트 발동!" ,"ㅇ");
                            //임의의 숫자로 증가 시켰으나 데이터에 맞게 할꺼임
                            temp.flowerLvUpText = String.valueOf(Float.parseFloat(temp.getFlowerLvUpText()) + 100);
                            temp.flowerLvText = String.valueOf(Integer.parseInt(temp.getFlowerLvText()) + 1);
                            // 새로생긴놈이면 이미지 생성해주기
                            if(temp.buyType==0){ testSource(temp.getFlowerNameText()); }
                            temp.buyType = 1;

                            // 새 정보로 업데이트후 갱신
                            mArray.set(id, temp);
                            mAdapter.notifyDataSetChanged();
                            mList.setAdapter(mAdapter);
                        }
                    }
                });
            }

            return v;
        }

    }

    // 점수 감소 함수
    boolean scoreDoun(float desSeed){
        if(score - desSeed > 0){
            //score 감소
            score = score - desSeed;
            return true;
        }else {
            //scre 감소 실패
            return false;
        }

    }
    public void testSource(String name){

        ImageView imageView = new ImageView(getContext());

        imageView.setImageResource(R.drawable.image);
        imageView.setId(0);
        imageView.setTag(R.drawable.image);

        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(200, 200);

        //위치는 후에 Random 값으로 배치
        relParams.leftMargin = 0;
        relParams.topMargin = 0;

        relLayout.addView(imageView,relParams);
        plantArray.add(new MainActivity.PlantInfo(0,imageView,name));
    }
}
