package com.example.round.gaia_17;

import android.content.Context;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import static com.example.round.gaia_17.MainActivity.fruitScore;
import static com.example.round.gaia_17.MainActivity.itemsSave;
import static com.example.round.gaia_17.MainActivity.score;
import static com.example.round.gaia_17.MainActivity.updateFruit;
import static com.example.round.gaia_17.MainActivity.updateSeed;

/**
 * 상점창 일부 소스 구현
 */

public class menuStoreActivity extends Fragment {
    private static final String TAG = ".StoreActivity";
    private ListView mList;
    private StoreProductAdapter mAdapter;
    private DB_Exception db;

    //꽃정보 저장용
    private ArrayList<ProductInfomation> mProductArray = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment,container,false);
        db = new DB_Exception(getContext());
        mProductArray = db.getStoreProductInform();
        mAdapter = new StoreProductAdapter(getContext(),R.layout.menu_store_item);
        mList = (ListView)view.findViewById(R.id.menuStoreList);
        mList.setAdapter(mAdapter);

        return view;
    }

    public static class ProductInfomation{
        private int itemId;
        private int itemCode;
        private String itemName;
        private int itemSeedCost;
        private int itemFruitCost;
        private int itemEffect;
        private String itemExplain;
        private String itemImagePath;

        ProductInfomation(int itemId, int itemCode, String itemName, int itemSeedCost, int itemFruitCost, int itemEffect, String itemExplain, String itemImagePath){
            this.itemId = itemId;
            this.itemCode = itemCode;
            this.itemName = itemName;
            this.itemSeedCost = itemSeedCost;
            this.itemFruitCost = itemFruitCost;
            this. itemEffect = itemEffect;
            this.itemExplain = itemExplain;
            this.itemImagePath = itemImagePath;
        }

        public int getItemId() {
            return itemId;
        }
        public String getItemName() {
            return itemName;
        }
        public int getItemCode() {
            return itemCode;
        }
        public int getItemSeedCost() {
            return itemSeedCost;
        }
        public int getItemFruitCost() {
            return itemFruitCost;
        }
        public int getItemEffect() {
            return itemEffect;
        }
        public String getItemExplain() {
            return itemExplain;
        }
        public String getItemImagePath() {
            return itemImagePath;
        }


    }

    // 패시브 스킬창 리스트 포멧
    static class StoreProductViewHolder{
        ImageView productImage;
        TextView productNameText;
        TextView productExplainText;
        TextView productNeedUseCostText;
        TextView productUseCostText;
        ImageButton productBuyButton;
    }

    // 패시브 스킬 리스트 어뎁터
    public class StoreProductAdapter extends ArrayAdapter<ProductInfomation> {
        private LayoutInflater mInflater = null;

        public StoreProductAdapter(Context context, int resource){
            super(context,resource);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount(){
            return mProductArray.size();
        }

        @Override
        public View getView(int position, View v, final ViewGroup parent) {
            final StoreProductViewHolder viewHolder;
            final ProductInfomation info = mProductArray.get(position);

            Log.i(TAG, "이름 : " + info.getItemName() + " , 설명 : " + info.getItemExplain());
            if (v == null) {
                v = mInflater.inflate(R.layout.menu_store_item, parent, false);
                viewHolder = new StoreProductViewHolder();
                viewHolder.productImage = (ImageView) v.findViewById(R.id.productImage);
                viewHolder.productNameText = (TextView) v.findViewById(R.id.productNameText);
                viewHolder.productExplainText = (TextView) v.findViewById(R.id.productExplainText);
                viewHolder.productNeedUseCostText = (TextView) v.findViewById(R.id.needUseCostText);
                viewHolder.productUseCostText = (TextView) v.findViewById(R.id.productUseCostText);
                viewHolder.productBuyButton = (ImageButton) v.findViewById(R.id.productBuyButton);
                v.setTag(viewHolder);
            } else {
                viewHolder = (StoreProductViewHolder) v.getTag();
            }

            if (info != null) {
                viewHolder.productImage.setImageResource(R.drawable.image);
                viewHolder.productNameText.setText(info.getItemName());
                viewHolder.productExplainText.setText(info.getItemExplain());
                viewHolder.productBuyButton.setImageResource(R.drawable.buy);
                if(info.getItemSeedCost() == -1){
                    viewHolder.productNeedUseCostText.setText("Fruit : ");
                    viewHolder.productUseCostText.setText(""+info.getItemFruitCost());
                }else if(info.getItemFruitCost() == -1){
                    viewHolder.productNeedUseCostText.setText("Seed : ");
                    viewHolder.productUseCostText.setText(""+info.getItemSeedCost());
                }else{
                    // 구매 불가능 아이템
                }

                viewHolder.productBuyButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final menuStoreCheckDialog dialog = new menuStoreCheckDialog(getContext());
                        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                dialog.setImage(info.getItemImagePath());
                                dialog.setName(info.getItemName());
                                dialog.setExplain(info.getItemExplain());
                                if(info.getItemSeedCost()== -1){
                                    dialog.setUseCost(1,info.getItemFruitCost());
                                    dialog.setPreCost(fruitScore);
                                }else{
                                    dialog.setUseCost(0,info.getItemSeedCost());
                                    dialog.setPreCost((int)score);
                                }
                            }
                        });
                        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dia) {
                                //예스일경우 재화감소
                                if(dialog.buySuccess==1){
                                    if(dialog.costType==1){
                                        //현금재화 구매
                                        buyProduct(info.getItemId()-1);
                                        fruitScore = dialog.futCost;
                                        updateFruit(fruitScore);
                                    }else{
                                        //게임재화 구매
                                        buyProduct(info.getItemId()-1);
                                        score = dialog.futCost;
                                        updateSeed(score);
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

    void buyProduct(int type) {

        switch (type) {
            case 1:
                // "체력포션 (Item)";
                itemsSave[0] = itemsSave[0] + 1;
                break;
            case 2:
                // "부활포션 (Item)";
                itemsSave[1] = itemsSave[1] + 1;
                break;
            case 3:
                // "물 (Item)";
                itemsSave[2] = itemsSave[2] + 1;
                break;
        }
    }
}
