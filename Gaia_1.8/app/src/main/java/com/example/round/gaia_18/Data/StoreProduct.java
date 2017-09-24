package com.example.round.gaia_18.Data;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Round on 2017-09-24.
 */

public class StoreProduct {

    private int itemId;
    private String itemName;
    private ConcurrentHashMap<Integer, Integer> seedCost = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Integer, Integer> fruitCost = new ConcurrentHashMap<>();
    private int itemEffectType;
    private String image;
    private int number;

    public StoreProduct(){}

    public StoreProduct(int itemId, String itemName, ConcurrentHashMap<Integer, Integer> seedCost, ConcurrentHashMap<Integer, Integer> fruitCost, int itemEffectType, String image) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.seedCost = seedCost;
        this.fruitCost = fruitCost;
        this.itemEffectType = itemEffectType;
        this.image = image;
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
        int type=0;
        if(fruit!=0) {
            while(true) {

                int newScore = fruit %1000;
                this.fruitCost.put(fruit,newScore);

                fruit /= 1000;
                if(fruit < 1000){
                    if(fruit != 0) this.fruitCost.put(type+1,fruit);
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
