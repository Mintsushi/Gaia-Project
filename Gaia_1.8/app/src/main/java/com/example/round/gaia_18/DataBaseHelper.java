package com.example.round.gaia_18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.round.gaia_18.Data.Flower;
import com.example.round.gaia_18.Data.FlowerData;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "Gaia";

    //Table

    //Flower Table
    private static final String FLOWER_TABLE_NAME = "flower";
    private static final String flowerId = "flowerNo";
    private static final String flowerName = "flowerName";
    private static final String flowerImage = "flowerImage";
    private static final String flowerButtonImage = "flowerButtonImage";
    private static final String flowerCost = "flowerCost";
    private static final String flowerScore = "flowerScore";
    private static final String flowerTab = "flowerTab";
    private static final String flowerLevel = "flowerLevel";
    private static final String flowerCostType = "flowerCostType";
    private static final String flowerScoreType = "flowerScoreType";

    //flowerAlgorithm Table
    private static final String FLOWER_ALGORITHM_NAME = "flowerData";
    private static final String num1 = "one";
    private static final String num2 = "two";
    private static final String num3 = "three";
    private static final String num4 = "four";
    private static final String num5 = "five";
    private static final String num6 = "six";
    private static final String num7 = "seven";
    private static final String num8 = "eight";
    private static final String num9 = "nine";
    private static final String num10 = "ten";

    //skill info data 1
    private static final String SKILL_INFO_TABLE_NAME = "skillInfo";
    private static final String skillId = "skillNo";
    private static final String skillName = "skillName";
    private static final String skillImage = "skillImage";
    private static final String skillButtonImage = "skillButtonImage";
    private static final String skillCoolTime = "coolTime";
    private static final String activeSkill = "activeSkill";
    private static final String skillCase = "skillCase";

    //skill info data 2
    private static final String SKILL_DATA_TABLE_NAME = "skillData";
    private static final String skillLevel = "level";
    private static final String skillBuyType = "buyType";
    private static final String cost = "cost";
    private static final String effect = "effect";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FLOWER_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FLOWER_ALGORITHM_NAME);
        //flower Table 구축
        flowerTable(sqLiteDatabase);
        //flower 수식 data DB 구축
        flowerDateTable(sqLiteDatabase);
    }

    private void flowerTable(SQLiteDatabase sqLiteDatabase) {
        //Flower Table
        String CREATE_TABLE = "CREATE TABLE " + FLOWER_TABLE_NAME + "("
                + flowerId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + flowerName + " TEXT NOT NULL,"
                + flowerImage + " TEXT NOT NULL,"
                + flowerButtonImage + " TEXT NOT NULL,"
                + flowerCost + " INTEGER NOT NULL,"
                + flowerScore + " INTEGER NOT NULL,"
                + flowerTab + " INTEGER NOT NULL,"
                + flowerLevel + " INTEGER NOT NULL,"
                + flowerCostType + " INTEGER NOT NULL,"
                + flowerScoreType + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //Insert Flower Data
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(0,"민들레", " ", " ", 50, 1, 0, 0,0,0));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(1,"나팔꽃", " ", " ", 2000, 3000, 200, 200,1,0));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(2,"장미", " ", " ", 100000, 100000, 400, 200,1,0));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(3,"철쭉", " ", " ", 12000, 2500000, 600, 200,2,0));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(4,"호야", " ", " ", 500000, 50000, 800, 200,2,1));
    }

    private ContentValues getFlowerValues(int id,String name, String image, String buttonImage,
                                          long cost, int score, int tab, int level,int costType,int scoreType) {

        ContentValues values = new ContentValues();

        values.put(flowerId, id);
        values.put(flowerName, name);
        values.put(flowerImage, image);
        values.put(flowerButtonImage, buttonImage);
        values.put(flowerCost, cost);
        values.put(flowerScore, score);
        values.put(flowerTab, tab);
        values.put(flowerLevel, level);
        values.put(flowerCostType,costType);
        values.put(flowerScoreType,scoreType);

        return values;

    }

    //flower 수식과 관련된 DB 구축
    private void flowerDateTable(SQLiteDatabase sqLiteDatabase){
        //Flower Table
        String CREATE_TABLE = "CREATE TABLE " + FLOWER_ALGORITHM_NAME + "("
                + flowerId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + num1 + " INTEGER NOT NULL,"
                + num2 + " INTEGER NOT NULL,"
                + num3 + " INTEGER NOT NULL,"
                + num4 + " INTEGER NOT NULL,"
                + num5 + " INTEGER NOT NULL,"
                + num6 + " INTEGER NOT NULL,"
                + num7 + " INTEGER NOT NULL,"
                + num8 + " INTEGER NOT NULL,"
                + num9 + " INTEGER NOT NULL,"
                + num10 + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(FLOWER_ALGORITHM_NAME, null, getFlowerData(0,0,0,25,5,-1,2,0,0,1,1));
        sqLiteDatabase.insert(FLOWER_ALGORITHM_NAME, null, getFlowerData(1,100,100,50,1,100,20,200,200,1,1));
        sqLiteDatabase.insert(FLOWER_ALGORITHM_NAME, null, getFlowerData(2,200,100,25,1,200,10,400,400,1,1));
        sqLiteDatabase.insert(FLOWER_ALGORITHM_NAME, null, getFlowerData(3,200,200,50,1,10000,5000,600,600,1,1));
        sqLiteDatabase.insert(FLOWER_ALGORITHM_NAME, null, getFlowerData(4,400,400,25,1,10000,9500,400,200,400,400));

    }

    private ContentValues getFlowerData(int id, int n1, int n2, int n3, int n4, int n5, int n6, int n7,
                                        int n8, int n9, int n10) {

        ContentValues values = new ContentValues();

        values.put(flowerId, id);
        values.put(num1, n1);
        values.put(num2, n2);
        values.put(num3, n3);
        values.put(num4, n4);
        values.put(num5, n5);
        values.put(num6, n6);
        values.put(num7, n7);
        values.put(num8,n8);
        values.put(num9,n9);
        values.put(num10,n10);

        return values;

    }

    private void skillInfoTable(SQLiteDatabase sqLiteDatabase){
        //Skill Information Table
        String CREATE_TABLE = "CREATE TABLE " +  SKILL_INFO_TABLE_NAME + "("
                + skillId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + skillName + " TEXT NOT NULL,"
                + skillImage + " TEXT NOT NULL,"
                + skillButtonImage + " TEXT NOT NULL,"
                + skillCoolTime + " INTEGER NOT NULL,"
                + activeSkill + " INTEGER NOT NULL,"
                + skillCase + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"쓰다듬기","","",300,0, 0));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"가지치기","","",300,0, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"꽃에게 노래 들려주기","","",300,0, 2));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"꽃에게 말걸어주기","","",0,1, 3));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"꽃 바라봐주기","","",0,1,4));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getSkillValues(0,"물 받아놓기","","",86400,0,5));
    }

    private ContentValues getSkillValues(int id,String name, String image, String buttonImage,
                                          int cooltime, int active,int skillCase) {

        ContentValues values = new ContentValues();

        values.put(skillId, id);
        values.put(skillName, name);
        values.put(skillImage, image);
        values.put(skillButtonImage, buttonImage);
        values.put(skillCoolTime, cooltime);
        values.put(activeSkill, active);
        values.put(this.skillCase,skillCase);

        return values;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FLOWER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //getter
    public ArrayList<Flower> getAllFlowers() {
        ArrayList<Flower> flowers = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FLOWER_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Flower flower = new Flower();
                flower.setFlowerNo(cursor.getInt(0));
                flower.setFlowerName(cursor.getString(1));
                flower.setFlowerImage(cursor.getString(2));
                flower.setFlowerButtonImage(cursor.getString(3));
                flower.setFlowerTab(cursor.getInt(6));
                flower.setFlowerLevel(cursor.getInt(7));
                flower.setCost(cursor.getInt(8),cursor.getInt(4));
                flower.setScore(cursor.getInt(9),cursor.getInt(5));

                flowers.add(flower);
            } while (cursor.moveToNext());
        }

        return flowers;
    }

    //getter
    public ArrayList<FlowerData> getAllFlowerDatas() {
        ArrayList<FlowerData> flowerDatas = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + FLOWER_ALGORITHM_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FlowerData flowerData = new FlowerData();
                flowerData.setFlowerNo(cursor.getInt(0));
                flowerData.setNum1(cursor.getInt(1));
                flowerData.setNum2(cursor.getInt(2));
                flowerData.setNum3(cursor.getInt(3));
                flowerData.setNum4(cursor.getInt(4));
                flowerData.setNum5(cursor.getInt(5));
                flowerData.setNum6(cursor.getInt(6));
                flowerData.setNum7(cursor.getInt(7));
                flowerData.setNum8(cursor.getInt(8));
                flowerData.setNum9(cursor.getInt(9));
                flowerData.setNum10(cursor.getInt(10));

                flowerDatas.add(flowerData);
            } while (cursor.moveToNext());
        }

        return flowerDatas;
    }

    private ContentValues getSkillDataValues(int id, int level, int buyType,int cost, int effect) {

        ContentValues values = new ContentValues();

        values.put(skillId,id);
        values.put(skillLevel, level);
        values.put(skillBuyType, buyType);
        values.put(this.cost, cost);
        values.put(this.effect, effect);

        return values;

    }

    private void skillDataTable(SQLiteDatabase sqLiteDatabase){
        //Skill Information Table
        String CREATE_TABLE = "CREATE TABLE " +  SKILL_DATA_TABLE_NAME + "("
                + skillId + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + skillLevel + " INTEGER NOT NULL,"
                + skillBuyType + " INTEGER NOT NULL,"
                + cost + " INTEGER NOT NULL,"
                + effect + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //Insert Flower Data
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,1,2,100,12));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,2,2,500,14));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,3,2,1000,16));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,4,2,5000,18));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,5,2,20000,20));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,6,2,100000,22));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,7,2,500000,24));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,8,2,1000000,26));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,9,2,3000000,28));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,10,2,5000000,30));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,11,2,50,33));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,12,2,50,36));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,13,2,50,39));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,14,2,50,42));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,15,2,50,45));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,16,2,60,48));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,17,2,70	,51));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,18,2,80	,54));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,19,2,90	,57));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,20,2,100,60));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,21,2,110,63));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,22,2,120,66));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,23,2,130,69));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,24,2,140,72));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,25,2,150,75));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,26,2,160,78));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,27,2,170,81));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,28,2,180,84));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,29,2,190,87));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(0,30,2,200,90));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,1,2,100,50));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,2,2,500	,100));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,3,2,1000,150));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,4,2,5000,200));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,5,2,20000,250));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,6,2,100000,300));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,7,2,500000,350));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,8,2,1000000,400));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,9,2,3000000,450));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,10,2,5000000,500));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,11,2,50	,600));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,12,2,50	,700));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,13,2,50	,800));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,14,2,50	,900));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,15,2,50	,1000));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,16,2,60	,1100));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,17,2,70	,1200));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,18,2,80	,1300));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,19,2,90	,1400));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,20,2,100,1500));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,21,2,110,1600));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,22,2,120,1700));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,23,2,130,1800));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,24,2,140,1900));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,25,2,150,2000));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,26,2,160,2100));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,27,2,170,2200));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,28,2,180,2300));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,29,2,190,2400));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(1,30,2,200,2500));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,1,2,100,12));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,2,2,500,14));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,3,2,1000,16));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,4,2,5000,18));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,5,2,20000,20));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,6,2,100000,22));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,7,2,500000,24));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,8,2,1000000,26));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,9,2,3000000,28));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,10,2,5000000,30));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,11,2,50,33));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,12,2,50,36));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,13,2,50,39));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,14,2,50,42));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,15,2,50,45));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,16,2,60,48));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,17,2,70	,51));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,18,2,80	,54));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,19,2,90	,57));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,20,2,100,60));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,21,2,110,63));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,22,2,120,66));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,23,2,130,69));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,24,2,140,72));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,25,2,150,75));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,26,2,160,78));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,27,2,170,81));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,28,2,180,84));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,29,2,190,87));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(2,30,2,200,90));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,1,2,50,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,2,2,60,4));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,3,2,70,6));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,4,2,80,8));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,5,2,90,10));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,6,2,100,12));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,7,2,110,14));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,8,2,120,16));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,9,2,130,18));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,10,2,140,20));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,11,2,150,22));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,12,2,160,24));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,13,2,170,26));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,14,2,180,28));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,15,2,190,30));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,16,2,200,32));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,17,2,200,34));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,18,2,200,36));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,19,2,200,38));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,20,2,200,40));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,21,2,200,42));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,22,2,200,44));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,23,2,200,46));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,24,2,200,48));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,25,2,200,50));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,26,2,200,52));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,27,2,200,54));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,28,2,200,56));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,29,2,200,58));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(3,30,2,200,60));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,1,2,50,10));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,2,2,60,20));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,3,2,70,30));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,4,2,80,40));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,5,2,90,50));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,6,2,100,60));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,7,2,110,70));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,8,2,120,80));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,9,2,130,90));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,10,2,140,100));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,11,2,150,120));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,12,2,160,140));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,13,2,170,160));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,14,2,180,180));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,15,2,190,200));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,16,2,200,220));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,17,2,200,240));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,18,2,200,260));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,19,2,200,280));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,20,2,200,300));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,21,2,200,320));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,22,2,200,340));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,23,2,200,360));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,24,2,200,380));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,25,2,200,400));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,26,2,200,420));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,27,2,200,440));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,28,2,200,460));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,29,2,200,480));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME, null, getSkillDataValues(4,30,2,200,500));
    }

}
