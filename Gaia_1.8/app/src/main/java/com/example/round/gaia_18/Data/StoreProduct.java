package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 김태우
 * 아이템 수량 추가. itemList 삭제함
 *
 */

public class StoreProduct {

    private int itemId;
    private int itemCode;
    private String itemName;;
    private ConcurrentHashMap<Integer, Integer> seedCost = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Integer> fruitCost = new ConcurrentHashMap<>();
    private int itemEffectType;
    private String itemExplain;
    private String image;
    private int itemNumber;

    public StoreProduct(){}


    public void setItemExplain(String itemExplain) {
        this.itemExplain = itemExplain;
    }

    public String getItemExplain() {
        return itemExplain;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public int getItemCode() {
        return itemCode;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ConcurrentHashMap<Integer, Integer> getSeedCost() {
        return seedCost;
    }

    public void setSeedCost(int seed) {
        int type=0;
        if(seed!=0) {
            while(true) {

                int newScore = seed %1000;
                this.seedCost.put(seed,newScore);

                seed /= 1000;
                if(seed < 1000){
                    if(seed != 0) this.seedCost.put(type+1,seed);
                    break;
                }
                type++;
            }
        }
    }

    public ConcurrentHashMap<Integer, Integer> getFruitCost() {
        return fruitCost;
    }

    public void setFruitCost(int fruit) {
        this.fruitCost.put(0,fruit);
    }

    public int getItemEffectType() {
        return itemEffectType;
    }

    public void setItemEffectType(int itemEffectType) {
        this.itemEffectType = itemEffectType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int number) {
        this.itemNumber = number;
    }
}
