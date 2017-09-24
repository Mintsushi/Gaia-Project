package com.example.round.gaia_17;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 상점창 일부 소스 구현
 */

public class menuStoreActivity extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // fragment 화면 활성화
        View view = inflater.inflate(R.layout.menu_store_fragment,container,false);

        return view;
    }

    class productInfomation{
        private int itemId;
        private String itemCode;
        private String itemName;
        private int itemSeedCost;
        private int itemFruitCost;
        private int itemEffect;
        private String itemExplain;
        private String itemImagePath;

        productInfomation(int itemId, String itemCode, String itemName, int itemSeedCost, int itemFruitCost, int itemEffect, String itemExplain, String itemImagePath){
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
        public String getItemCode() {
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
}
