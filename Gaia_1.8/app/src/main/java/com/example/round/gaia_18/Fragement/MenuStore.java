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

import java.util.Timer;
import java.util.TimerTask;

import static com.example.round.gaia_18.Data.DataList.getScore;
import static com.example.round.gaia_18.Data.DataList.storeAdapter;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.OverlayService.dataList;


public class MenuStore extends Fragment {

    private static final String TAG = ".StoreProduct";
    private ListView storeList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment, container, false);

        storeAdapter = new StoreAdapter(getContext(), R.layout.menu_store_item);
        storeList = (ListView) view.findViewById(R.id.storeList);
        storeList.setAdapter(storeAdapter);

        return view;
    }

    private class StoreViewHolder{
        ImageView productImage;
        TextView productName;
        TextView productExplain;
        TextView productNum;
        TextView productBuyScore;
        ImageButton productBuyButton;
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
        public View getView(int position, View view, final ViewGroup parent) {
            final StoreProduct storeProduct = dataList.getStoreProducts().get(position);
            final StoreViewHolder viewholder;

            if(view == null){

                view = mInflater.inflate(R.layout.menu_store_item,parent,false);

                viewholder = new StoreViewHolder();
                viewholder.productImage = (ImageView)view.findViewById(R.id.productImage);
                viewholder.productName = (TextView)view.findViewById(R.id.productName);
                viewholder.productExplain = (TextView)view.findViewById(R.id.productExplain);
                viewholder.productNum = (TextView)view.findViewById(R.id.storeProductNum);
                viewholder.productBuyScore = (TextView)view.findViewById(R.id.productBuyCost);
                viewholder.productBuyButton = (ImageButton)view.findViewById(R.id.productBuyButton);

                view.setTag(viewholder);
            }else{
                viewholder = (StoreViewHolder)view.getTag();
            }

            if(storeProduct != null){

                //후에 아이템 이미지로 변경
                viewholder.productImage.setImageResource(R.drawable.image);
                viewholder.productName.setText(storeProduct.getItemName());
                Log.i("getString","product" + Integer.toString(storeProduct.getBuyType()));
                int resourceId = getContext().getResources().getIdentifier("product" + Integer.toString(storeProduct.getItemEffectType()), "string", getContext().getPackageName());
                viewholder.productExplain.setText(getResources().getText(resourceId));
                viewholder.productNum.setText(Integer.toString(storeProduct.getItemNumber()));

                //현금성 재화로 구매
                if(storeProduct.getBuyType() == 0){
                    viewholder.productBuyButton.setImageResource(R.drawable.fruit);
                }
                else{ //현금성 재화로 구매
                    viewholder.productBuyButton.setImageResource(R.drawable.seed);
                }

                viewholder.productBuyScore.setText(dataList.getAllScore(storeProduct.getCost()));
                viewholder.productBuyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final StoreCheckDialog dialog = new StoreCheckDialog(getContext());
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {

                                dialog.setImage(storeProduct.getImage());
                                dialog.setName(storeProduct.getItemName());

                                // 구매에 사용하는 재화의 정보 보내기
                                if (storeProduct.getBuyType() == 0) {
                                    dialog.setUseCost(1,storeProduct.getCost());
                                    dialog.setPreCost(1,dataList.getFruitHashMap());
                                } else {
                                    dialog.setUseCost(0,storeProduct.getCost());
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
                                    dataList.setIncItemNumber(storeProduct.getItemId(), 1);

                                    // 만약 1번 아이템이면 10분동안 점수획득후 아이템 감소 하게됨
                                    if(storeProduct.getItemId()==0){
                                        itemskillTimemer itemskillTimemer = new itemskillTimemer(600);
                                        itemskillTimemer.handlerStart();
                                    }

                                    storeAdapter.notifyDataSetChanged();
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

            return view;
        }
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
