package com.example.round.gaia_18.Fragement;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.round.gaia_18.Data.StoreProduct;
import com.example.round.gaia_18.Dialog.StoreCheckDialog;
import com.example.round.gaia_18.R;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.Data.DataList.getScore;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.OverlayService.dataList;


public class MenuStore extends Fragment {

    private static final String TAG = ".StoreProduct";
    private ListView storeList;

    private StoreAdapter mAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment, container, false);
        mAdapter = new StoreAdapter(getContext(), R.layout.menu_store_item);
        storeList = (ListView) view.findViewById(R.id.storeList);
        storeList.setAdapter(mAdapter);

        return view;
    }

    // 상점 리스트뷰 포멧
    public class StoreProductViewHolder {
        ImageView productImage;
        TextView productNameText;
        TextView productExplainText;
        TextView productNeedUseCostText;
        TextView productUseCostText;
        ImageButton productBuyButton;
        TextView itemNumText;
    }

    // 상점 리스트 어뎁터
    public class StoreAdapter extends ArrayAdapter<StoreProduct> {
        private LayoutInflater mInflater = null;

        public StoreAdapter(Context context, int resource) {
            super(context, resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return dataList.getStoreProducts().size();
        }

        @Override
        public View getView(int position, View v, final ViewGroup parent) {
            final StoreProductViewHolder viewHolder;
            final StoreProduct info = dataList.getStoreProducts().get(position);


            Log.i("StoreProduct", "이름 : " + info.getItemName() + " , 설명 : " + info.getItemExplain());

            if (v == null) {
                //뷰 활성화
                v = mInflater.inflate(R.layout.menu_store_item, parent, false);
                viewHolder = new StoreProductViewHolder();
                viewHolder.productImage = (ImageView) v.findViewById(R.id.productImage);
                viewHolder.productNameText = (TextView) v.findViewById(R.id.productNameText);
                viewHolder.productExplainText = (TextView) v.findViewById(R.id.productExplainText);
                viewHolder.productNeedUseCostText = (TextView) v.findViewById(R.id.needUseCostText);
                viewHolder.productUseCostText = (TextView) v.findViewById(R.id.productUseCostText);
                viewHolder.productBuyButton = (ImageButton) v.findViewById(R.id.productBuyButton);
                viewHolder.itemNumText = (TextView) v.findViewById(R.id.itemNum);
                v.setTag(viewHolder);
            } else {
                viewHolder = (StoreProductViewHolder) v.getTag();
            }

            if (info != null) {
                // 뷰에 이미지 그리기
                viewHolder.productImage.setImageResource(R.drawable.image);
                viewHolder.productNameText.setText(info.getItemName());
                viewHolder.productExplainText.setText(info.getItemExplain());
                viewHolder.productBuyButton.setImageResource(R.drawable.buy);
                viewHolder.itemNumText.setText("수량 : " + dataList.getItemNumber(info.getItemCode() -1));

                Log.i("integer-1", "" + info.getSeedCost().get(-1));
                Log.i("integer-2", "" + info.getFruitCost());
                if(info.getSeedCost().get(-1) != null && info.getFruitCost().get(-1) != null) {
                    //구매불가능
                }

                else if(info.getSeedCost().get(-1) != null){   //현금재화로 구매하는경우info.getSeedCost() == -1
                    viewHolder.productNeedUseCostText.setText("Fruit : ");
                    viewHolder.productUseCostText.setText("" + dataList.getAllScore(info.getFruitCost()));
                } else {    //게임재화로 구매하는경우info.getFruitCost() == -1
                    viewHolder.productNeedUseCostText.setText("Seed : ");
                    viewHolder.productUseCostText.setText("" + dataList.getAllScore(info.getSeedCost()));
                }

                // 구매버튼
                viewHolder.productBuyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final StoreCheckDialog dialog = new StoreCheckDialog(getContext());
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.setImage(info.getImage());
                                dialog.setName(info.getItemName());
                                dialog.setExplain(info.getItemExplain());

                                // 구매에 사용하는 재화의 정보 보내기
                                if (info.getSeedCost().get(-1) != null) {
                                    dialog.setUseCost(1,info.getFruitCost());
                                    dialog.setPreCost(1,dataList.getFruitHashMap());
                                } else {
                                    dialog.setUseCost(0,info.getSeedCost());
                                    dialog.setPreCost(0,dataList.getScoreHashMap());
                                }
                            }
                        });

                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dia) {
                                // 구매 성공시..
                                if (dialog.buySuccess == 1) {

                                    dataList.getGoalDataByID(7).setGoalRate(1);
                                    dataList.setIncItemNumber(info.getItemCode() - 1, 1);

                                    // 만약 1번 아이템이면 10분동안 점수획득후 아이템 감소 하게됨
                                    if(info.getItemId()==0){
                                        itemskillTimemer itemskillTimemer = new itemskillTimemer(600);
                                        itemskillTimemer.handlerStart();
                                    }

                                    mAdapter.notifyDataSetChanged();
                                    Toast.makeText(getActivity(), "구매성공", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    if(dialog.costType == -1){
                                        //취소버튼 누른경우
                                    }
                                    else if(dialog.costType ==1){
                                        Toast.makeText(getActivity(), "열매가 부족해요!", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getActivity(), "씨앗이 부족해요!", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }
                        });
                        dialog.show();

                    }
                });

            }

            return v;
        }
    }

    //게임 재화로 구매
    private Boolean buyForScore(ConcurrentHashMap<Integer, Integer> seed) {

        Iterator<Integer> iterator = seed.keySet().iterator();

        while (iterator.hasNext()) {
            int key = iterator.next();
            int value = seed.get(key);

            if (!dataList.minusScore(key, value, dataList.getScoreHashMap())) {
                return false;
            }
        }
        return true;
    }

    //현금성 재화로 구매
    private Boolean buyForFruit(ConcurrentHashMap<Integer, Integer> fruit) {

        Iterator<Integer> iterator = fruit.keySet().iterator();

        while (iterator.hasNext()) {
            int key = iterator.next();
            int value = fruit.get(key);

            if (!dataList.minusScore(key, value, dataList.getFruitHashMap())) {
                return false;
            }
        }

        return true;
    }

    // 0번 아이템용
    class itemskillTimemer {
        Timer timer;
        private TimerTask second;
        private int times;
        private int collTime;

        itemskillTimemer(int coolTime){
            this.collTime = coolTime;

        }

        public int getTimes() {
            return times;
        }
        public void setTimes(int times) {
            if(times == 0){
                this.times = 0;
            }
            else{
                this.times += times;
            }
        }

        public int getCollTime() {
            return collTime;
        }

        private final Handler handler = new Handler();

        public void handlerStart(){
            timer = new Timer();
            second = new TimerTask() {
                @Override
                public void run() {

                    // CoolTime은 사용자입력.
                    Log.i("Timer","if");
                    if(getTimes() < getCollTime()){
                        Log.i("Timer","itemskill run");
                        // 점수증가 run
                        Update();
                    }
                    else{
                        Log.i("Timer","itemskill end");
                        // 종료
                        handleEnd();
                    }
                }
            };
            timer.schedule(second, 0, 1000);
        }


        protected void Update() {
            Runnable updater = new Runnable() {
                public void run() {
                    // 점수증가
                    // 10A씩 증가로 해둠
                    dataList.plusScore(1,10,getScore());

                    seed.setText(dataList.getAllScore(dataList.getScoreHashMap()));
                }
            };
            handler.post(updater);
        }


        public void handleEnd(){
            timer.cancel();
            timer = null;
            // 사용후 아이템 감소
            dataList.setDesItemNumber(0,1);
        }
    }
}
