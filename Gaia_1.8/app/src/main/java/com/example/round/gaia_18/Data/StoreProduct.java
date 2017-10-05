package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 김태우
 * 아이템 수량 추가. itemList 삭제함
 *
 */

public class StoreProduct {

    private int itemId;
    private String itemName;;
    private ConcurrentHashMap<Integer,Integer> cost = new ConcurrentHashMap<>();
    private int itemEffectType;
    private String image;
    private int itemNumber = 0;
    private int buyType;

    public StoreProduct(){cost.put(0,0);}

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

    public ConcurrentHashMap<Integer, Integer> getCost() {
        return cost;
    }

    public void setCost(int seed) {
        int type=0;
        if(seed!=0) {
            while(true) {

                int newScore = seed %1000;
                this.cost.put(seed,newScore);

                seed /= 1000;
                if(seed < 1000){
                    if(seed != 0) this.cost.put(type+1,seed);
                    break;
                }
                type++;
            }
        }
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

    public void setBuyType(int buyType){this.buyType = buyType;}
    public int getBuyType(){return this.buyType;}

}
