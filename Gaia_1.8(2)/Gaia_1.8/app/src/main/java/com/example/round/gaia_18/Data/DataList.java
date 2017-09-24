package com.example.round.gaia_18.Data;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Round on 2017-09-06.
 */

public class DataList {

    private static ArrayList<Flower> flowers = new ArrayList<>();
    private static ArrayList<Plant> plants = new ArrayList<>();
    private static ArrayList<OverlayPlant> overlayPlants = new ArrayList<>();
    private static ArrayList<FlowerData> flowerDatas = new ArrayList<>();

    public static HashMap<Integer, Integer> clickScore = new HashMap<>();
    private static HashMap<Integer, Integer> score = new HashMap<>();

    public DataList(ArrayList<Flower> flowers, ArrayList<FlowerData> flowerDatas) {
        this.flowers = flowers;
        this.flowerDatas = flowerDatas;
    }

    public ArrayList<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(ArrayList<Flower> flowers) {
        this.flowers = flowers;
    }

    public ArrayList<Plant> getPlants() {
        return plants;
    }

    public void setPlants(ArrayList<Plant> plants) {
        this.plants = plants;
    }

    public void addPlant(Plant plant){
        this.plants.add(plant);
    }

    public ArrayList<OverlayPlant> getOverlayPlants() {
        return overlayPlants;
    }

    public void setOverlayPlants(ArrayList<OverlayPlant> overlayPlants) {
        DataList.overlayPlants = overlayPlants;
    }

    public void addOverlayPlant(OverlayPlant overlayPlant){
        overlayPlants.add(overlayPlant);
    }

    public void compareFlowers( ){
        for(int i =0 ; i< plants.size() ; i++){
            for(int j = 0; j <flowers.size() ; j++){
                if(plants.get(i).getPlantNo() == flowers.get(j).getFlowerNo()){
                    flowers.get(j).setBuyType(true);
                    flowers.get(j).setLevel(plants.get(i).getLevel());
                }
            }
        }
    }

    public void setBuyPossible(){

        flowers.get(0).setBuyPossible(true);

        for(int i =1; i < flowers.size();i++){
            if(flowers.get(i-1).getLevel() == flowers.get(i).getFlowerLevel()){
                flowers.get(i).setBuyPossible(true);
            }
            else{
                return;
            }
        }
    }

    public static ArrayList<FlowerData> getFlowerDatas() {
        return flowerDatas;
    }

    public static void setFlowerDatas(ArrayList<FlowerData> flowerDatas) {
        DataList.flowerDatas = flowerDatas;
    }

    public HashMap<Integer, Integer> getScoreHashMap() {
        return score;
    }

    public static void setScore(HashMap<Integer, Integer> score) {
        DataList.score = score;
    }

    public int getScore(int type){
        return score.get(type);
    }

    public void setScore(int type, int score) {
        if(score == 0 && this.score.containsKey(type)){
            this.score.remove(type);
        }
        this.score.put(type,score);
    }

    public String getAllScore(HashMap<Integer, Integer> hashMap){

        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(hashMap);
        int index = 0;

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        String scoreString = "";

        iterator.hasNext();
        while(true){
            int type = iterator.next();
            int score = treeMap.get(type);

            scoreString += Integer.toString(score)+getType(type);

            if(index == 0 && iterator.hasNext()){
                scoreString += " + ";
                index = 1;
            }
            else{
                break;
            }
        }

        return scoreString;
    }

    public Boolean minusScore(int type, int score,HashMap<Integer, Integer> hashMap){

        HashMap<Integer, Integer> fakeScore = hashMap;

        Log.i("BuyFlower","Score Contain Key: " + fakeScore.containsKey(type));
        //해당 타입에서 구입할 수 있을 때
        if(fakeScore.containsKey(type)) {
            if (fakeScore.get(type) >= score) {
                Log.i("BuyFlower",type+" : "+fakeScore.get(type));
                int newScore = fakeScore.get(type) - score;
                hashMap.put(type,newScore);
                return true;
            }
        }
        //해당 타입에서 구입할 수 없을 때 경우는 2가지가 있음.
        //1. 해당 타입보다 높은 타입에 금액이 있을 때
        //2. 해당 타입이 가장 높고 점수가 없을 때
        Iterator<Integer> iterator = fakeScore.keySet().iterator();

        while(iterator.hasNext()){
            int scoreType = iterator.next();
            int value = fakeScore.get(scoreType);
            if(scoreType > type){
                if(value-1 < 1){
                    Log.i("BuyFlower","Value-1 = 0 -> value : "+value);
                    hashMap.remove(scoreType);
                }
                else{
                    Log.i("BuyFlower","Value-1 != 0 -> value : "+value);
                    hashMap.put(scoreType,value-1);
                }

                while(true){
                    if(scoreType - 1 == type){
                        int newScore = 1000+hashMap.get(type)-score;
                        hashMap.put(type,newScore);
                        break;
                    }
                    else{
                        hashMap.put(scoreType-1,999);
                        scoreType --;
                    }
                }
                return true;
            }
        }

        return false;
    }

    public void plusScore(int type, int score, HashMap<Integer, Integer> hashMap){

        if(score == 0){
            return;
        }
            if (hashMap.containsKey(type)) {
                if (hashMap.get(type) + score > 999) {
                    while (true) {
                        int mok = (hashMap.get(type) + score) / 1000;
                        int nameogi = (hashMap.get(type) + score) % 1000;

                        hashMap.put(type, nameogi);
                        type ++;
                        if (hashMap.containsKey(type)) {
                            int newScore = hashMap.get(type) + mok;
                            if (newScore <= 999) {
                                hashMap.put(type, newScore);
                                return;
                            }
                        } else {
                            hashMap.put(type, mok);
                            return;
                        }
                    }
                } else {
                    int newScore = hashMap.get(type) + score;
                    hashMap.put(type, newScore);
                }
            } else {
                hashMap.put(type, score);
            }
    }

    public void clickScore(){

        Iterator<Integer> iterator = clickScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = clickScore.get(type);

            plusScore(type,score,this.score);
        }
    }

    private String getType(int type){
        String typeName = "";

        switch (type){
            case 1: typeName="A";break;//a
            case 2: typeName="B";break;//b
            case 3: typeName="C";break;//c
            case 4: typeName="D";break;//d
            case 5: typeName="E";break;//e
            case 6: typeName="F";break;//f
            case 7: typeName="G";break;//g
            case 8: typeName="H";break;//h
            case 9: typeName="I";break;//i
            case 10: typeName="J";break;//j
            case 11: typeName="K";break;//k
            case 12: typeName="L";break;//l
            case 13: typeName="M";break;//m
            case 14: typeName="N";break;//n
            case 15: typeName="O";break;//o
            case 16: typeName="P";break;//p
            case 17: typeName="Q";break;//q
            case 18: typeName="R";break;//r
            case 19: typeName="S";break;//s
            case 20: typeName="T";break;//t
            case 21: typeName="U";break;//u
            case 22: typeName="V";break;//v
            case 23: typeName="W";break;//w
            case 24: typeName="X";break;//x
            case 25: typeName="Y";break;//y
            case 26: typeName="Z";break;//z
        }

        return typeName;
    }

    public void flowerLevelUp(Flower flower){

        Iterator<Integer> iterator = flower.getScore().keySet().iterator();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = flower.getScore().get(key);

            minusScore(key,value,this.clickScore);
        }

        Log.i("LevelUp","******************************");
        newCost(flower);
        newScore(flower);

        iterator = flower.getScore().keySet().iterator();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = flower.getScore().get(key);

            plusScore(key,value,this.clickScore);
        }
    }

    private void newCost(Flower flower){

        int type = 0;
        FlowerData flowerData = flowerDatas.get(flower.getFlowerNo());
        double num1 = Math.pow(2,Math.ceil((flower.getLevel()+flowerData.getNum1())/50));
        double num2 = Math.ceil((flower.getLevel()+flowerData.getNum2()) / flowerData.getNum3());
        double num3 = (flower.getLevel()+flowerData.getNum5()) / flowerData.getNum6();

        if(num1 == (double)0) num1 = 1;
        if(num2 == (double)0) num2 = 1;
        if(num3 == (double)0) num3 = 1;

        double num4 = num1*num2*flowerData.getNum4()*num3;

        while(true){
            double nameogi = num4 % 1000;
            double mok = num4 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,flower.getCost());
            }
            if(mok <1000){
                plusScore(type + 1, (int)mok, flower.getCost());
                break;
            }

            num4 = mok;
            type ++;
        }
    }

    private void newScore(Flower flower){
        int type = 0;
        FlowerData flowerData = flowerDatas.get(flower.getFlowerNo());
        double num1 = Math.pow(2,Math.floor((flower.getLevel()+flowerData.getNum7())/50));
        double num2 = Math.ceil((flower.getLevel()+flowerData.getNum8()) / flowerData.getNum9());

        double num3 = num1*num2/(flowerData.getNum9()*flowerData.getNum10());

        while(true){
            double nameogi = num3 %1000;
            double mok = num3 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,flower.getScore());
            }
            if(mok <1000){
                if(mok != 0)
                    plusScore(type+1,(int)mok,flower.getCost());
                break;
            }

            num3 = mok;
            type ++;
        }
    }
}