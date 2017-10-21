package com.example.round.gaia_18.Data;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.round.gaia_18.Dialog.goalListDialog;
import com.example.round.gaia_18.Fragement.MenuDryFlower;
import com.example.round.gaia_18.Fragement.MenuFlower;
import com.example.round.gaia_18.Fragement.MenuOverlay;
import com.example.round.gaia_18.Fragement.MenuSkill;
import com.example.round.gaia_18.Fragement.MenuStore;
import com.example.round.gaia_18.OverlayService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.round.gaia_18.MainActivity.mOverlayService;
import static com.example.round.gaia_18.MainActivity.seed;
import static com.example.round.gaia_18.OverlayService.dataBaseHelper;
import static com.example.round.gaia_18.OverlayService.weatherData;

public class DataList {

    //flower DataBase
    private static ArrayList<Flower> flowers = new ArrayList<>();
    //사용자가 가지고 있는 식물 정보
    private static ArrayList<Plant> plants = new ArrayList<>();
    //사용자가 가지고 있는 식물들 중 외부화면에 있는 식물 정보
    private static ArrayList<OverlayPlant> overlayPlants = new ArrayList<>();
    //각 식물들의 비용과 점수를 계산하는데 필요한 상수들
    private static ArrayList<FlowerData> flowerDatas = new ArrayList<>();
    //각 스킬의 정보
    private static ArrayList<SkillInfo> skillInfos = new ArrayList<>();
    //각 스킬들에 해당하는 가격, 점수 정보
    private static ArrayList<SkillData> skillDatas = new ArrayList<>();
    //만렙을 한 꽃들 중 dry flower fragement에 들어간 꽃에 대한 정보
    private ConcurrentHashMap<Integer, ArrayList<DryFlower>> dryPlants = new ConcurrentHashMap<>();
    //각 업적에 해당하는 업적 달성 조건, 보상 정보
    private static ArrayList<GoalData> goalDatas = new ArrayList<>();
    //각 업적의 정보
    private ArrayList<GoalInfo> goalInfos = dataBaseHelper.getAllGoalInfo();
    //각 상정 아이템의 정보
    private static ArrayList<StoreProduct> storeProducts = new ArrayList<>();
    // WATER 물주기 정보
    private static ArrayList<Water> waters = new ArrayList<>();


    // 설정정보
    public static GameSetting settings;
    //식물에 따른 클릭 수
    public static ConcurrentHashMap<Integer, Integer> clickScore = new ConcurrentHashMap<>();
    //식물에 따른 클릭 수 + 날씨에 따른 식물의 클릭 수 -> 실제 클릭 수
    public static ConcurrentHashMap<Integer , Integer> overlayClickScore = new ConcurrentHashMap<>();
    //skill type0 사용시 임시로 저장할 click 수
    public static ConcurrentHashMap<Integer, Integer> fake_clickScore = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<Integer, Integer> fase_overlayClickScore = new ConcurrentHashMap<>();
    //업적 보상(점수 누적)
    private static ConcurrentHashMap<Integer, Integer> goalClick = new ConcurrentHashMap<>();
    //스킬타입3(탭당 n% 만큼의 점수 증가
    private static ConcurrentHashMap<Integer, Integer> skillEffectClickScore = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<Integer, Integer> skillEffectOverlayClickScore = new ConcurrentHashMap<>();

    //각 fragement의 Adatper들
    public static MenuDryFlower.DryFlowerAdapter dryFlowerAdapter;
    public static MenuFlower.FlowerAdapter flowerAdapter;
    public static MenuOverlay.PlantAdapter plantAdapter;
    //skillList Adapter
    public static MenuSkill.SkillAdapter mAdapter;
    //overlaySkill Adapter
    public static OverlayService.OverlaySkillAdpter overlaySkillAdpter;
    //storeAdapter
    public static MenuStore.StoreAdapter storeAdapter;
    //업적 Adapter
    public static goalListDialog.GoalAdapter goalAdapter;

    //자동 클릭될 VIEW
    private View clickView;
    //Tab skill Level에 따른 점수 / 가격
    private TabData tabData;

    //사용자가 가지고 있는 점수
    private static ConcurrentHashMap<Integer, Integer> score = new ConcurrentHashMap<>();
    //사용자가 가지고 있는 현금성 재화
    private static ConcurrentHashMap<Integer, Integer> fruit = new ConcurrentHashMap<>();

    public DataList(ArrayList<Flower> flowers, ArrayList<FlowerData> flowerDatas, ArrayList<SkillInfo> skillInfos,ArrayList<StoreProduct> storeProducts, ArrayList<Water> waters) {
        this.flowers = flowers;
        this.flowerDatas = flowerDatas;
        this.skillInfos = skillInfos;
        this.storeProducts = storeProducts;
        this.waters = waters;
        this.skillDatas.add(0,null);
    }

    public void setTabData(TabData tabData){this.tabData = tabData;}
    public TabData getTabData(){return this.tabData;}

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

    public void delsPlant(int x){

        Log.i("list ", ""+this.plants.get(x).getPlantNo());
        Log.i("list ", "1: "+this.plants.size());
        this.plants.remove(x);
        Log.i("list ", "2: "+this.plants.size());
    }

    public ArrayList<OverlayPlant> getOverlayPlants() {
        return overlayPlants;
    }

    public void setOverlayPlants(ArrayList<OverlayPlant> overlayPlants) {
        DataList.overlayPlants = overlayPlants;
    }

    public void setGoalDatas(int id, int level, int rate){

        GoalData goalData = dataBaseHelper.getGoalDataByID(id, level);
        goalData.setGoalRate(rate);

        goalDatas.add(id,goalData);
    }

    public void goalLevelUp(int id){

        int level = goalDatas.get(id).getGoalLevel();

        Log.i("LevelUp","id : "+id+" / goal Level : "+level);
        goalDatas.remove(id);

        GoalData goalData = dataBaseHelper.getGoalDataByID(id, level+1);
        goalData.setGoalRate(0);

        goalDatas.add(id, goalData);
    }

    public GoalData getGoalDataByID(int id){
        return this.goalDatas.get(id);
    }

    public ArrayList<GoalData> getAllGoalData(){return this.goalDatas;}

    public ArrayList<GoalInfo> getGoalInfos() {
        return goalInfos;
    }

    public static ArrayList<StoreProduct> getMiniItemListProducts() {
        ArrayList<StoreProduct> miniList = new ArrayList<>();
        miniList.add(storeProducts.get(1));
        miniList.add(storeProducts.get(2));
        miniList.add(storeProducts.get(3));
        return miniList;
    }

    public static ArrayList<StoreProduct> getStoreProducts() {
        return storeProducts;
    }

    //    public static void setStoreProducts(ArrayList<StoreProduct> storeProducts) {
//        DataList.storeProducts = storeProducts;
//    }
//
//
    public void setItemNumber(int id, int num){
        this.storeProducts.get((id-1)).setItemNumber(num);
        if( (id-1) == 3){
            this.storeProducts.get((id)).setItemNumber(num);
        }
    }
    //
    public void setIncItemNumber(int id, int num){
        int temp = this.storeProducts.get(id).getItemNumber();
        if( (id) == 3 || id==4){
            this.storeProducts.get(3).setItemNumber(temp + num);
            this.storeProducts.get(4).setItemNumber(temp + num);
        }
        else{
            this.storeProducts.get(id).setItemNumber(temp + num);
        }
    }

    public void setDesItemNumber(int id, int num){
        int temp = this.storeProducts.get(id).getItemNumber();
        if( (id) == 3 || id==4){
            this.storeProducts.get(3).setItemNumber(temp - num);
            this.storeProducts.get(4).setItemNumber(temp - num);
        }
        else{
            this.storeProducts.get(id).setItemNumber(temp - num);
        }
    }

    public int getItemNumber(int id){
        return this.storeProducts.get(id).getItemNumber();
    }


    public static ArrayList<Water> getWaters() {
        return waters;
    }


    public void resetFlower(int id){

        Log.i("RESETFLower","id : "+id);
        Flower flower = null;
        for(int i =0 ;i<flowers.size();i++){
            if(flowers.get(i).getFlowerNo() == id) flower = flowers.get(i);
        }

        Iterator<Integer> iterator = flower.getScore().keySet().iterator();

        if(flower.getWhere() == 0){
            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                minusScore(key,value,clickScore);
            }
        }
        else{
            ConcurrentHashMap<Integer, Integer> fakeScore = new ConcurrentHashMap<>();

            int effect = weatherData.get(flower.getFlowerNo());
            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                int newScore = value + (value * effect) / 100;
                if(newScore > 999){
                    if(newScore-1000 >0)
                        plusScore(key,newScore%1000,fakeScore);
                    plusScore(key+1,newScore/1000,fakeScore);
                }
                else{
                    plusScore(key,value,fakeScore);
                }
            }

            minusOverlayClickScore(fakeScore);
        }

        flower.setLevel(0);
        flower.getCost().clear();
        flower.getScore().clear();
        flower.setCost(flower.getCostType(),flower.getFlowerCost());
        flower.setScore(flower.getScoreType(),flower.getFlowerScore());
        flower.setBuyType(false);
    }

    public static ArrayList<SkillInfo> getSkillInfos() {
        return skillInfos;
    }

    public static void setSkillInfos(ArrayList<SkillInfo> skillInfos) {
        DataList.skillInfos = skillInfos;
    }

    public static ArrayList<SkillData> getSkillDatas() {
        return skillDatas;
    }

    public static void setSkillDatas(ArrayList<SkillData> skillDatas) {
        DataList.skillDatas = skillDatas;
    }

    public void putSkillData(int skillNo,SkillData skillData){
        skillDatas.add(skillNo,skillData);
    }

    public void replaceSkillData(int id, int level){
        skillDatas.remove(id);
        skillDatas.add(id,dataBaseHelper.getSkill(id, level));
    }

    public static ConcurrentHashMap<Integer, Integer> getClickScore() {
        return clickScore;
    }

    public static void setClickScore(ConcurrentHashMap<Integer, Integer> clickScore) {
        DataList.clickScore = clickScore;
    }

    public static ConcurrentHashMap<Integer, Integer> getOverlayClickScore() {
        return overlayClickScore;
    }

    public static void setOverlayClickScore(ConcurrentHashMap<Integer, Integer> overlayClickScore) {
        DataList.overlayClickScore = overlayClickScore;
    }

    public static ConcurrentHashMap<Integer, Integer> getScore() {
        return score;
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

    public ConcurrentHashMap<Integer, Integer> getScoreHashMap() {
        return score;
    }

    public ConcurrentHashMap<Integer, ArrayList<DryFlower>> getDryPlats() {
        return dryPlants;
    }

    public DryFlower getDryFlower(int position){

        Iterator<Integer> iterator = this.dryPlants.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            //해당 key set에 있음.
            if(dryPlants.get(key).size() > position){
                return dryPlants.get(key).get(position);
            }
            //해당 key set에 없음.
            position -= dryPlants.get(key).size();
        }

        return null;
    }

    public void setDryPlats(DryFlower plant) {

        dryFlowerClick dryFlowerClick = new dryFlowerClick(plant.getScore());

        //해당 id의 꽃이 이미 있음.
        if(dryPlants.containsKey(plant.getDryFlowerNo())){
            dryPlants.get(plant.getDryFlowerNo()).add(plant);
        }
        else{ //해당 id의 꽃을 처음으로 넣은것
            ArrayList<DryFlower> dryFlowers = new ArrayList<>();
            dryFlowers.add(plant);
            dryPlants.put(plant.getDryFlowerNo(),dryFlowers);
        }

        dryFlowerClick.startSkill();
    }

    private class dryFlowerClick {

        android.os.Handler handler = new Handler();
        private final ConcurrentHashMap<Integer, Integer> dryFlowerScore;

        public dryFlowerClick(ConcurrentHashMap<Integer, Integer> score){
            this.dryFlowerScore = score;
        }

        public void startSkill(){

            Timer timer = new Timer();

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    autoClick();
                }
            };
            timer.schedule(task, 0,1000);
        }

        private void autoClick(){
            Runnable updater = new Runnable() {
                public void run() {
                    Log.i("click","click+click");
                    Iterator<Integer> iterator = dryFlowerScore.keySet().iterator();

                    while(iterator.hasNext()){
                        int key = iterator.next();
                        int value = dryFlowerScore.get(key);

                        plusScore(key,value,score);
                    }

                    seed.setText(getAllScore(score));
                    mOverlayService.setSeed();
                }
            };
            handler.post(updater);
        }
    }

    public int getDryPlantSize(){

        Iterator<Integer> iterator = this.dryPlants.keySet().iterator();
        int size = 0;
        while(iterator.hasNext()){
            size += dryPlants.get(iterator.next()).size();
        }

        return size;
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

    public void setFruit(int type, int fruit){
        if(fruit == 0 && this.fruit.containsKey(type)){
            this.fruit.remove(type);
        }
        this.fruit.put(type,fruit);
    }

    public ConcurrentHashMap<Integer, Integer> getGoalClick(){return this.goalClick;}

    public ConcurrentHashMap<Integer, Integer> getFruitHashMap() {
        return this.fruit;
    }

    public View getClickView() {
        return clickView;
    }

    public void setClickView(View clickView) {
        this.clickView = clickView;
    }

    public String getAllScore(ConcurrentHashMap<Integer, Integer> hashMap){

        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(hashMap);
        int index = 0;

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        String scoreString = "";

        if(iterator.hasNext()){
            while(true){
                int type = iterator.next();
                int score = treeMap.get(type);

                if(score != 0) scoreString += Integer.toString(score)+getType(type);

                if(index == 0 && iterator.hasNext()){
                    scoreString += ".";
                    index = 1;
                }
                else{
                    break;
                }
            }
        }

        if(scoreString.equals("")) scoreString="0";
        return scoreString;
    }

    public Boolean minusScore(int type, int score, ConcurrentHashMap<Integer, Integer> hashMap){

        ConcurrentHashMap<Integer, Integer> fakeScore = hashMap;

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
            if(scoreType > type && value>0){
                if(value-1 < 1){
                    hashMap.remove(scoreType);
                }
                else{
                    hashMap.put(scoreType,value-1);
                }

                while(true){
                    if(scoreType - 1 == type){
                        int newScore;
                        if(hashMap.containsKey(type)){
                            newScore = 1000+hashMap.get(type)-score;
                        }
                        else{
                            newScore = 1000 - score;
                        }
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

    public void plusScore(int type, int score, ConcurrentHashMap<Integer, Integer> hashMap){

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

    public void windowClick(){

        Iterator<Integer> iterator = clickScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = clickScore.get(type);

            plusScore(type,score,this.score);
            plusScore(type,score,this.goalClick);
        }
    }

    public void overlayWindowClick(){
        Log.i("OverlayClick", "Click : "+overlayClickScore.toString());

        Iterator<Integer> iterator = overlayClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = overlayClickScore.get(type);

            plusScore(type,score,this.score);
            plusScore(type,score,this.goalClick);
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

        levelUpFlowerResetSkillType3();

        if(flower.getWhere() == 0){
            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                minusScore(key,value,clickScore);
            }
        }
        else{
            ConcurrentHashMap<Integer, Integer> fakeScore = new ConcurrentHashMap<>();

            int effect = weatherData.get(flower.getFlowerNo());
            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                int newScore = value + (value * effect) / 100;
                if(newScore > 999){
                    if(newScore-1000 >0)
                        plusScore(key,newScore%1000,fakeScore);
                    plusScore(key+1,newScore/1000,fakeScore);
                }
                else{
                    plusScore(key,value,fakeScore);
                }
            }

            minusOverlayClickScore(fakeScore);
        }

        Log.i("LevelUp","******************************");
        flower.setLevel(flower.getLevel() + 1);
        newCost(flower);
        newScore(flower);

        iterator = flower.getScore().keySet().iterator();

        if(flower.getWhere() == 0){
            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                plusScore(key,value,clickScore);
            }
        }
        else{

            int effect = weatherData.get(flower.getFlowerNo());

            while (iterator.hasNext()){
                int key = iterator.next();
                int value = flower.getScore().get(key);

                int newScore = value + (value * effect) / 100;
                if(newScore > 999){
                    if(newScore-1000 >0)
                        plusScore(key,newScore%1000,overlayClickScore);
                    plusScore(key+1,newScore/1000,overlayClickScore);
                }
                else{
                    plusScore(key,value,overlayClickScore);
                }
            }
        }

        levelUpFlowerSetSkillType3();
    }

    private void newCost(Flower flower){

        int type = 0;
        FlowerData flowerData = flowerDatas.get(flower.getFlowerNo());
        long num1 = (long)Math.pow(2,Math.ceil((flower.getLevel()+flowerData.getNum1())/50));
        long num2 = (long)Math.ceil((flower.getLevel()+flowerData.getNum2()) / flowerData.getNum3());
        long num3 = (long)(flower.getLevel()+flowerData.getNum5()) / flowerData.getNum6();

        if(num1 == (long)0) num1 = 1;
        if(num2 == (long)0) num2 = 1;
        if(num3 == (long)0) num3 = 1;

        double num4 = num1*num2*flowerData.getNum4()*num3;

        while(true){
            long nameogi = (long)num4 % 1000;
            long mok = (long)num4 / 1000;

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
        long num1 = (long)Math.pow(2,Math.floor((flower.getLevel()+flowerData.getNum7())/50));
        long num2 = (long)Math.ceil((flower.getLevel()+flowerData.getNum8()) / flowerData.getNum9());

        long num3 = (long)num1*num2/(flowerData.getNum9()*flowerData.getNum10());

        while(true){
            long nameogi =(long)num3 %1000;
            long mok = (long)num3 / 1000;

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

    public void plusClickScore(ConcurrentHashMap<Integer, Integer> newScore){
        Iterator<Integer> iterator = newScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = newScore.get(type);

            plusScore(type,score,clickScore);
        }
    }

    public void minusClickScore(ConcurrentHashMap<Integer, Integer> newScore){

        Iterator<Integer> iterator = newScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = newScore.get(type);

            minusScore(type,score,clickScore);
        }
    }

    public void plusOverlayClickScore(ConcurrentHashMap<Integer, Integer> newScore){
        Iterator<Integer> iterator = newScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = newScore.get(type);

            plusScore(type,score,overlayClickScore);
        }
    }

    public void minusOverlayClickScore(ConcurrentHashMap<Integer, Integer> newScore){
        Iterator<Integer> iterator = newScore.keySet().iterator();

        while(iterator.hasNext()){
            int type = iterator.next();
            int score = newScore.get(type);

            minusScore(type,score,overlayClickScore);
        }
    }

    public void effectSkill(int skillType){

        Log.i("SKILL","skillType : "+skillType);
        if(skillType == 0){

            fake_clickScore.putAll(clickScore);
            fase_overlayClickScore.putAll(overlayClickScore);

            startSkill_type0(clickScore);
            startSkill_type0(overlayClickScore);
        }
    }

    private void startSkill_type0(ConcurrentHashMap<Integer, Integer> score){
        Iterator<Integer> iterator = score.keySet().iterator();

        while (iterator.hasNext()) {
            int key = iterator.next();
            int value = score.get(key);

            plusScore(key, value, score);
        }
    }

    public void finishSkill(int skillType){
        if(skillType == 0){
            clickScore.clear();
            overlayClickScore.clear();

            clickScore.putAll(fake_clickScore);
            overlayClickScore.putAll(fase_overlayClickScore);

            fake_clickScore.clear();
            fase_overlayClickScore.clear();
        }
    }

    //goal Score도 같이 올려줄것
    public void startSkill_type1(int effect, int where){
        Iterator<Integer> iterator;

        //App 내부에서 스킬 사용
        if(where == 0){
            iterator= clickScore.keySet().iterator();

            while(iterator.hasNext()){
                int key = iterator.next();
                int value = clickScore.get(key);

                int newScore = (int)(value * effect);
                if(newScore%1000 != 0) plusScore(key, newScore%1000,score);
                newScore /=1000;
                do{
                    key++;
                    plusScore(key,newScore,score);
                    newScore /= 1000;
                }while(newScore > 0);
            }
        }

        //외부화면에서 스킬 사용
        else{
            iterator = overlayClickScore.keySet().iterator();

            while(iterator.hasNext()){
                int key = iterator.next();
                int value = overlayClickScore.get(key);

                int newScore = value * effect;
                if(newScore%1000 != 0) plusScore(key, newScore%1000,score);
                newScore /=1000;
                do{
                    key++;
                    plusScore(key,newScore,score);
                    newScore /= 1000;
                }while(newScore > 0);
            }
        }
    }

    public Boolean calculateGoal(ConcurrentHashMap<Integer, Integer> rate,ConcurrentHashMap<Integer, Integer> goal){

        ConcurrentHashMap<Integer, Integer> fakeGoal = new ConcurrentHashMap<>();
        fakeGoal.putAll(goal);

        Iterator<Integer> iterator = rate.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = rate.get(key);

            if(!minusScore(key,value,fakeGoal)){
                return false;
            }
        }

        return true;
    }

    //n%만큼의 점수를 계싼해서 clickScore와 overlayClickScore에 ++
    public void startSkillType3(int effect){
        Iterator<Integer> iterator = clickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = clickScore.get(key);

            int newScore = (int)(value * effect)/100;
            if(newScore%1000 != 0) {
                plusScore(key, newScore%1000,clickScore);
                plusScore(key,newScore%1000,skillEffectClickScore);
            }
            newScore /=1000;
            do{
                key++;
                plusScore(key,newScore,score);
                plusScore(key,newScore,skillEffectClickScore);
                newScore /= 1000;
            }while(newScore > 0);
        }

        iterator = overlayClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = overlayClickScore.get(key);

            int newScore = value * effect;
            if(newScore%1000 != 0) {
                plusScore(key, newScore % 1000, overlayClickScore);
                plusScore(key,newScore%1000,skillEffectOverlayClickScore);
            }
            newScore /=1000;
            do{
                key++;
                plusScore(key,newScore,score);
                plusScore(key,newScore,skillEffectOverlayClickScore);
                newScore /= 1000;
            }while(newScore > 0);
        }
    }

    public void levelUpSkillType3(){

        Iterator<Integer> iterator = skillEffectClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectClickScore.get(key);

            minusScore(key, value,clickScore);
        }
        skillEffectClickScore.clear();

        iterator = skillEffectOverlayClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectOverlayClickScore.get(key);

            minusScore(key,value,overlayClickScore);
        }
        skillEffectOverlayClickScore.clear();


    }

    private void levelUpFlowerResetSkillType3(){
        Iterator<Integer> iterator = skillEffectClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectClickScore.get(key);

            minusScore(key, value,clickScore);
        }

        iterator = skillEffectOverlayClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectOverlayClickScore.get(key);

            minusScore(key,value,overlayClickScore);
        }

    }

    private void levelUpFlowerSetSkillType3(){
        Iterator<Integer> iterator = skillEffectClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectClickScore.get(key);

            plusScore(key, value,clickScore);
        }

        iterator = skillEffectOverlayClickScore.keySet().iterator();

        while(iterator.hasNext()){
            int key = iterator.next();
            int value = skillEffectOverlayClickScore.get(key);

            plusScore(key,value,overlayClickScore);
        }
    }

    public int getPartScore(ConcurrentHashMap<Integer, Integer> score){

        int partScore = 0;

        TreeMap<Integer, Integer> treeMap = new TreeMap<Integer, Integer>(Collections.<Integer>reverseOrder());
        treeMap.putAll(score);
        int index = 0;

        Iterator<Integer> iterator = treeMap.keySet().iterator();

        if(iterator.hasNext()){
            int key = iterator.next();
            int value = score.get(key);

            if(key >0)
                partScore+= value*1000;
            else {
                partScore += value;
            }
        }
        if(iterator.hasNext()){
            int key = iterator.next();
            int value = score.get(key);

            partScore+= value;
        }

        return partScore;
    }

    public void tabSkillLevelUp(){

        Iterator<Integer> iterator = tabData.getScore().keySet().iterator();

        levelUpFlowerResetSkillType3();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = tabData.getScore().get(key);

            minusScore(key,value,clickScore);
            minusScore(key,value,overlayClickScore);
        }
        Log.i("LevelUp","******************************");
        tabData.setSkillLevel(tabData.getSkillLevel() + 1);

        //tab skill의 새로운 점수 / 가격 계산
        //점수
        int type = 0;
        long num1 = (long)Math.pow(2,Math.ceil((tabData.getSkillLevel()/50)));
        long num2 = (long)Math.ceil((tabData.getSkillLevel()/25));
        long num3 = (long)(5*(tabData.getSkillLevel()-1));

        if(num1 == (long)0) num1 = 1;
        if(num2 == (long)0) num2 = 1;
        if(num3 == (long)0) num3 = 1;

        double num4 = num1 * num2 * num3;

        while(true){
            long nameogi = (long)num4 % 1000;
            long mok = (long)num4 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,tabData.getScore());
            }
            if(mok <1000){
                plusScore(type + 1, (int)mok, tabData.getScore());
                break;
            }

            num4 = mok;
            type ++;
        }
        //가격
        type = 0;

        num3 = num1 * num2 * 2;

        while(true){
            long nameogi =(long)num3 %1000;
            long mok = (long)num3 / 1000;

            if(nameogi != 0){
                plusScore(type,(int)nameogi,tabData.getCost());
            }
            if(mok <1000){
                if(mok != 0)
                    plusScore(type+1,(int)mok,tabData.getCost());
                break;
            }

            num3 = mok;
            type ++;
        }

        iterator = tabData.getScore().keySet().iterator();

        while (iterator.hasNext()){
            int key = iterator.next();
            int value = tabData.getScore().get(key);

            plusScore(key,value,clickScore);
            plusScore(key,value,overlayClickScore);
        }
    }


    public void setSetting(GameSetting set){
        this.settings = set;
        this.settings.setAlarm(set.getAlarm());
        this.settings.setHpAparm(set.getHpAparm());
        this.settings.setHpAparmGauge(set.getHpAparmGauge());
        this.settings.setSound(set.getSound());
        this.settings.setVibration(set.getVibration());
        this.settings.setWeather(set.getWeather());
    }

    public GameSetting getSetting(){
        return this.settings;
    }

}