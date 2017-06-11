package com.example.neopul14;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;

/**
 * Created by Mint on 2017-05-10.
 */


// 상점에 쓰이는 구매목록 아이템 클래스들
// 나중에 디비에서 받아올꺼임 임시야 임시

public class MethodData {
}

class FlowerData{
    FlowerData(){}
    private int dataCount = 2;
    private int[] images = {R.drawable.sunflower,R.drawable.rose};
    private String[] names = {"Sun Flower","Rose"};
    private String[] items = {"This is Sun Flower.","This is Rose."};

    public int getDataCount(){
        return this.dataCount;
    }
    public int getImages(int i) {
        return this.images[i];
    }
    public String getnames(int i) {
        return this.names[i];
    }
    public String getitems(int i) {
        return this.items[i];
    }

}

class PollenData{
    PollenData(){}
    private int dataCount = 2;
    private int[] images = {R.drawable.pollen_brown,R.drawable.pollen_yellow};
    private String[] names = {"Brown","Yellow"};

    public int getDataCount(){
        return this.dataCount;
    }
    public int getImages(int i) {
        return this.images[i];
    }
    public String getnames(int i) {
        return this.names[i];
    }

}

class ItemData{
    ItemData(){}
    private int dataCount = 3;
    private int[] images = {R.drawable.holly_water,R.drawable.fertilizer,R.drawable.pesticide};
    private String[] names = {"Holy Water","Fertilizer","Pesticide"};
    private String[] items = {"It is very holy holy Water!! Yeee!!.","Fertilizer is good","Pleae Don't eat."};

    public int getDataCount(){
        return this.dataCount;
    }
    public int getImages(int i) {
        return this.images[i];
    }
    public String getnames(int i) {
        return this.names[i];
    }
    public String getitems(int i) {
        return this.items[i];
    }
}

class PlantData{
    PlantData(){}
    private int dataCount = 4;
    private int[] images = {R.drawable.sunflower_brown,R.drawable.sunflower_yellow,R.drawable.rose_brown,R.drawable.rose_yellow};
    private String[] names = {"Sun Flower1","Sun Flower1","Rose1","Rose2"};
    private String[] items = {"This is Sun Flower1.","This is Sun Flower2.", "This is Rose1.","This is Rose2."};

    public int getDataCount(){
        return this.dataCount;
    }
    public int getImages(int i) {
        return this.images[i];
    }
    public String getnames(int i) {
        return this.names[i];
    }
    public String getitems(int i) {
        return this.items[i];
    }
}

