package com.example.round.gaia_17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 리제 on 2017-09-06.
 */
// 디비에 꽃정보, 패시브스킬정보, 엑티브 스킬정보 모두 넣었고
    // 모든 데이터는 메인엑티비티에서 처음에 한번에 할당해준다
//
public class DB_Exception extends SQLiteOpenHelper {
    DB_Exception(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }


    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME="Gaia123777";

    //ActiveSkill
    final private static String ACTIVE_SKILL_TABLE_NAME = "ACTIVE_SKILL_TABLE";

    final private static String activeSkillkey = "KEY_KEY";
    final private static String activeSkillId = "KEY_ID";
    final private static String activeSkillLv = "KEY_LV";
    final private static String activeSkillCostType= "KEY_COST_TYPE";
    final private static String activeSkillUseCost = "KEY_USE_COST";
    final private static String activeSkillCostPower = "KEY_USE_COST_POWER";
    final private static String activeSkillEffect = "KEY_EFFECT";

    //ActiveSkillFormat
    final private static String ACTIVE_SKILL_FORMAT_TABLE_NAME = "ACTIVE_SKILL_FORMAT_TABLE";
    final private static String activeSkillFormatId = "KEY_ID";
    final private static String activeSkillFormatName = "KEY_NAME";
    final private static String activeSkillFormatImage = "KEY_IMAGE";
    final private static String activeSkillFormatSkillImage = "KEY_SKILL_IMAGE";
    final private static String activeSkillFormatReuseTime = "KEY_REUSE_TIME";
    final private static String activeSkillFormatOnPassive = "KEY_ON_PASSIVE";
    final private static String activeSkillFormatSkillType = "KEY_SKILL_TYPE";
    final private static String activeSkillFormatSkillExplain = "KEY_SKILL_EXPLAIN";


    //PassiveSkill
    final private static String PASSIVE_SKILL_TABLE_NAME = "PASSIVE_SKILL_TABLE";
    final private static String passiveSkillId = "KEY_ID";
    final private static String passiveSkillName = "KEY_NAME";
    final private static String passiveSkillImage = "KEY_IMAGE";
    final private static String passiveSkillExplain = "KEY_EXPLAIN";
    final private static String passiveSkillSecScore = "KEY_SEC_SCORE";
    final private static String passiveSkillCostPower = "KEY_COST_POWER";

    //StoreProduct
    final private static String STORE_PRODUCT_TABLE_NAME = "STORE_PRODUCT_TABLE";
    final private static String storeProductKey = "KEY_KEY";
    final private static String storeProductId = "KEY_ID";
    final private static String storeProductCode = "KEY_CODE";
    final private static String storeProductName = "KEY_NAME";
    final private static String storeProductSeedCost = "KEY_SEED_COST";
    final private static String storeProductFruitCost = "KEY_FRUIT_COST";
    final private static String storeProductEffect = "KEY_EFFECT";
    final private static String storeProductExplain = "KEY_EXPLAIN";
    final private static String storeProductImagePath = "KEY_IMAGE_PATH";


    //GoalFormat
    final private static String GOAL_FORMAT_TABLE_NAME = "GOAL_FORMAT_TABLE";
    final private static String goalFormatKey = "KEY_KEY";
    final private static String goalFormatId = "KEY_ID";
    final private static String goalFormatName = "KEY_NAME";
    final private static String goalFormatContent = "KEY_CONTENT";
    final private static String goalFormatFinalLevel = "KEY_FINAL_LEVEL";
    final private static String goalFormatLevelDrect= "KEY_LEVEL_DRECT";


    //Goal
    final private static String GOAL_TABLE_NAME = "GOAL_TABLE";
    final private static String goalKey = "KEY_KEY";
    final private static String goalId = "KEY_ID";
    final private static String goalLevel = "KEY_LEVEL";
    final private static String goalCondition = "KEY_CONDITION";
    final private static String goalConditionPower = "KEY_CONDITION_POWER";
    final private static String goalRewardType = "KEY_REWARD_TYPE";
    final private static String goaloalReward = "KEY_REWARD";
    final private static String goalRewardPower = "KEY_REWARD_POWER";

    //Water
    final private static String WATER_TABLE_NAME = "WATER_TABLE";
    final private static String waterKey = "KEY_KEY";
    final private static String waterId = "KEY_ID";
    final private static String waterPeriod = "KEY_PERIOD";
    final private static String waterPenaltyTime = "KEY_PENALTY_TIME";
    final private static String waterPenalty = "KEY_PENALTY";
    final private static String waterNeedWaterNum = "KEY_NEED_WATER_NUM";


    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //del(sqLiteDatabase);
        flowerTable(sqLiteDatabase);
        ActiveSkillTable(sqLiteDatabase);
        ActiveSkillFormatTable(sqLiteDatabase);
        PassiveSkillTable(sqLiteDatabase);
        StoreProductTable(sqLiteDatabase);
        GoalFormatTable(sqLiteDatabase);
        GoalTable(sqLiteDatabase);
        WaterTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private void del(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("delete from " + ACTIVE_SKILL_FORMAT_TABLE_NAME);
        sqLiteDatabase.execSQL("delete from " + ACTIVE_SKILL_TABLE_NAME);
        sqLiteDatabase.execSQL("delete from " + PASSIVE_SKILL_TABLE_NAME);
    }

    private void PassiveSkillTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + PASSIVE_SKILL_TABLE_NAME + "("
                + passiveSkillId + " INTEGER NOT NULL PRIMARY KEY,"
                + passiveSkillName + " TEXT NOT NULL,"
                + passiveSkillImage + " TEXT NOT NULL,"
                + passiveSkillExplain + " TEXT NOT NULL,"
                + passiveSkillSecScore + " INTEGER NOT NULL,"
                + passiveSkillCostPower + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(PASSIVE_SKILL_TABLE_NAME,null,getPassiveSkillValues(0,"민들레","image","민들레 이다",89400,0));
        sqLiteDatabase.insert(PASSIVE_SKILL_TABLE_NAME,null,getPassiveSkillValues(1,"나팔꽃","image","나팔꽃 이다",2250,3));
        sqLiteDatabase.insert(PASSIVE_SKILL_TABLE_NAME,null,getPassiveSkillValues(2,"장미","image","장미 이다",49095,3));
        sqLiteDatabase.insert(PASSIVE_SKILL_TABLE_NAME,null,getPassiveSkillValues(3,"철쭉","image","철쭉 이다",1,9));
        sqLiteDatabase.insert(PASSIVE_SKILL_TABLE_NAME,null,getPassiveSkillValues(4,"호야","image","호야 이다",19750,6));
    }

    private void ActiveSkillTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE " + ACTIVE_SKILL_TABLE_NAME + "("
                + activeSkillkey + " INTEGER NOT NULL PRIMARY KEY,"
                + activeSkillId + " INTEGER NOT NULL,"
                + activeSkillLv + " INTEGER NOT NULL,"
                + activeSkillCostType + " INTEGER NOT NULL,"
                + activeSkillUseCost + " INTEGER NOT NULL,"
                + activeSkillEffect + " INTEGER NOT NULL,"
                + activeSkillCostPower + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(0,1,1,2,100,12,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(1,1,2,2,500,14,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(2,1,3,2,1,16,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(3,1,4,2,5,18,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(4,1,5,2,20,20,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(5,1,6,2,100,22,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(6,1,7,2,500,24,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(7,1,8,2,1,26,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(8,1,9,2,3,28,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(9,1,10,2,5,30,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(10,1,11,1,50,33,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(11,1,12,1,50,36,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(12,1,13,1,50,39,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(13,1,14,1,50,42,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(14,1,15,1,50,45,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(15,1,16,1,60,48,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(16,1,17,1,70,51,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(17,1,18,1,80,54,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(18,1,19,1,90,57,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(19,1,20,1,100,60,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(20,1,21,1,110,63,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(21,1,22,1,120,66,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(22,1,23,1,130,69,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(23,1,24,1,140,72,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(24,1,25,1,150,75,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(25,1,26,1,160,78,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(26,1,27,1,170,81,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(27,1,28,1,180,84,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(28,1,29,1,190,87,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(29,1,30,1,200,90,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(30,2,1,2,100,50,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(31,2,2,2,500,100,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(32,2,3,2,1,150,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(33,2,4,2,5,200,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(34,2,5,2,20,250,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(35,2,6,2,100,300,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(36,2,7,2,500,350,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(37,2,8,2,1,400,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(38,2,9,2,3,450,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(39,2,10,2,5,500,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(40,2,11,1,50,600,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(41,2,12,1,50,700,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(42,2,13,1,50,800,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(43,2,14,1,50,900,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(44,2,15,1,50,1000,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(45,2,16,1,60,1100,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(46,2,17,1,70,1200,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(47,2,18,1,80,1300,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(48,2,19,1,90,1400,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(49,2,20,1,100,1500,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(50,2,21,1,110,1600,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(51,2,22,1,120,1700,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(52,2,23,1,130,1800,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(53,2,24,1,140,1900,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(54,2,25,1,150,2000,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(55,2,26,1,160,2100,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(56,2,27,1,170,2200,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(57,2,28,1,180,2300,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(58,2,29,1,190,2400,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(59,2,30,1,200,2500,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(60,3,1,2,100,12,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(61,3,2,2,500,14,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(62,3,3,2,1,16,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(63,3,4,2,5,18,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(64,3,5,2,20,20,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(65,3,6,2,100,22,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(66,3,7,2,500,24,3));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(67,3,8,2,1,26,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(68,3,9,2,3,28,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(69,3,10,2,5,30,6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(70,3,11,1,50,33,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(71,3,12,1,50,36,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(72,3,13,1,50,39,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(73,3,14,1,50,42,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(74,3,15,1,50,45,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(75,3,16,1,60,48,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(76,3,17,1,70,51,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(77,3,18,1,80,54,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(78,3,19,1,90,57,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(79,3,20,1,100,60,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(80,3,21,1,110,63,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(81,3,22,1,120,66,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(82,3,23,1,130,69,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(83,3,24,1,140,72,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(84,3,25,1,150,75,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(85,3,26,1,160,78,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(86,3,27,1,170,81,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(87,3,28,1,180,84,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(88,3,29,1,190,87,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(89,3,30,1,200,90,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(90,4,1,1,50,2,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(91,4,2,1,60,4,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(92,4,3,1,70,6,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(93,4,4,1,80,8,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(94,4,5,1,90,10,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(95,4,6,1,100,12,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(96,4,7,1,110,14,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(97,4,8,1,120,16,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(98,4,9,1,130,18,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(99,4,10,1,140,20,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(100,4,11,1,150,22,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(101,4,12,1,160,24,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(102,4,13,1,170,26,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(103,4,14,1,180,28,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(104,4,15,1,190,30,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(105,4,16,1,200,32,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(106,4,17,1,200,34,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(107,4,18,1,200,36,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(108,4,19,1,200,38,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(109,4,20,1,200,40,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(110,4,21,1,200,42,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(111,4,22,1,200,44,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(112,4,23,1,200,46,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(113,4,24,1,200,48,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(114,4,25,1,200,50,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(115,4,26,1,200,52,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(116,4,27,1,200,54,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(117,4,28,1,200,56,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(118,4,29,1,200,58,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(119,4,30,1,200,60,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(120,5,1,1,50,10,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(121,5,2,1,60,20,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(122,5,3,1,70,30,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(123,5,4,1,80,40,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(124,5,5,1,90,50,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(125,5,6,1,100,60,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(126,5,7,1,110,70,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(127,5,8,1,120,80,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(128,5,9,1,130,90,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(129,5,10,1,140,100,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(130,5,11,1,150,120,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(131,5,12,1,160,140,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(132,5,13,1,170,160,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(133,5,14,1,180,180,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(134,5,15,1,190,200,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(135,5,16,1,200,220,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(136,5,17,1,200,240,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(137,5,18,1,200,260,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(138,5,19,1,200,280,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(139,5,20,1,200,300,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(140,5,21,1,200,320,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(141,5,22,1,200,340,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(142,5,23,1,200,360,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(143,5,24,1,200,380,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(144,5,25,1,200,400,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(145,5,26,1,200,420,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(146,5,27,1,200,440,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(147,5,28,1,200,460,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(148,5,29,1,200,480,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(149,5,30,1,200,500,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(150,6,1,1,50,30,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(151,6,2,1,60,50,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(152,6,3,1,70,80,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(153,6,4,1,80,120,0));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(154,6,5,1,100,160,0));

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME   ,   null   ,   getActiveSkillValues(155,0,1,2,1000,12,0));





    }

    private void ActiveSkillFormatTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + "ACTIVE_SKILL_FORMAT_TABLE" + "("
                + activeSkillFormatId + " INTEGER NOT NULL PRIMARY KEY,"
                + activeSkillFormatName + " TEXT NOT NULL,"
                + activeSkillFormatImage + " TEXT NOT NULL,"
                + activeSkillFormatSkillImage + " TEXT NOT NULL,"
                + activeSkillFormatReuseTime + " INTEGER NOT NULL,"
                + activeSkillFormatOnPassive + " INTEGER NOT NULL,"
                + activeSkillFormatSkillType + " INTEGER NOT NULL,"
                + activeSkillFormatSkillExplain + " TEXT NOT NULL" +")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(1,"","","",300,0,0,""));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(0,"기본","a","a",300,0,0,"획득 점수 증가"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(1,"쓰다듬기","a","a",300,0,0,"일정 시간 얻는 점수 2배(레벨업시 시간 증가)"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(2,"가지치기","a","a",300,0,1,"점수 획득(레벨업시 배율 증가)"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(3,"노래 들려주기","a","a",300,0,2,"초당 10회 자동 탭(레벨업시 시간 증가)"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(4,"말하기","a","a",0,1,3,"탭 당 점수 증가(레벨업시 % 증가)"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(5,"바라보기","a","a",0,1,4,"분당 일정 횟수 자동 탭(레벨업시 횟수 증가)"));
        sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(6,"물받기","a","a",86400,0,5,"날씨가 비 일시" + "일정량의 아이템(물)을 획득(레벨업시 획득량 증가)"));

    }



    private ContentValues getActiveSkillValues(int key, int id,  int lv, int costType, int useCost, int effect, int costPower){

        ContentValues values = new ContentValues();
        values.put(activeSkillkey,key);
        values.put(activeSkillId,id);
        values.put(activeSkillLv,lv);
        values.put(activeSkillCostType,costType);
        values.put(activeSkillUseCost,useCost);
        values.put(activeSkillEffect,effect);
        values.put(activeSkillCostPower,costPower);
        return values;
    }

    private ContentValues getActiveSkillFormatValues(int id,  String naeme, String imagePath, String SkillImagePath,
                                                     int reuseTime, int onPassive, int skillType, String skillExplain){

        ContentValues values = new ContentValues();
        values.put(activeSkillId,id);
        values.put(activeSkillFormatName,naeme);
        values.put(activeSkillFormatImage,imagePath);
        values.put(activeSkillFormatSkillImage,SkillImagePath);
        values.put(activeSkillFormatReuseTime,reuseTime);
        values.put(activeSkillFormatOnPassive,onPassive);
        values.put(activeSkillFormatSkillType,skillType);
        values.put(activeSkillFormatSkillExplain,skillExplain);
        return values;
    }

    private ContentValues getPassiveSkillValues(int id, String name, String image, String explain, int secScose, int costPower){

        ContentValues values = new ContentValues();
        values.put(activeSkillId,id);
        values.put(passiveSkillName,name);
        values.put(passiveSkillImage,image);
        values.put(passiveSkillExplain,explain);
        values.put(passiveSkillSecScore,secScose);
        values.put(passiveSkillCostPower,costPower);
        return values;
    }


    public menuActiveSkillActivity.ActiveSkillInform getActiveSkillInform(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ACTIVE_SKILL_TABLE_NAME,new String[] {activeSkillkey,activeSkillId,activeSkillLv,activeSkillCostType,activeSkillUseCost,activeSkillEffect,activeSkillCostPower},activeSkillkey+"=?",
                new String[] {String.valueOf(id)},null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Log.i(" SQL ",  ""+ cursor.getInt(0) +"/"+cursor.getInt(1) +"/" +cursor.getInt(2) +"/" + cursor.getInt(3) +"/" + cursor.getInt(4)+"/" + cursor.getInt(5)+"/" + cursor.getInt(6));

        menuActiveSkillActivity.ActiveSkillInform activeSkillInform = new menuActiveSkillActivity.ActiveSkillInform(
                cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6));
        return activeSkillInform;
    }

    public ArrayList<menuActiveSkillActivity.ActiveSkillFormatInform> getActiveSkillFormatInform(){

        ArrayList<menuActiveSkillActivity.ActiveSkillFormatInform> ASFI_List = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ACTIVE_SKILL_FORMAT_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                menuActiveSkillActivity.ActiveSkillFormatInform activeSkillFormatInform = new menuActiveSkillActivity.ActiveSkillFormatInform(cursor.getInt(0), cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7));
                ASFI_List.add(activeSkillFormatInform);

                Log.i(" SQL ", ""+cursor.getInt(0)+" , "+cursor.getString(1)+ " , "+cursor.getString(2)+" , "+cursor.getString(3) + " , "+cursor.getInt(4) + " , "+cursor.getInt(5) + " , "+cursor.getInt(6) + " , "+cursor.getString(7));
            } while (cursor.moveToNext());
        }

        return ASFI_List;
    }

    public ArrayList<menuPassiveSkillActivity.DryFlower> getPassiveSkillFormatInform(){
        ArrayList<menuPassiveSkillActivity.DryFlower> DF = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+PASSIVE_SKILL_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                menuPassiveSkillActivity.DryFlower dryFlower = new menuPassiveSkillActivity.DryFlower(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4), cursor.getInt(5));
                DF.add(dryFlower);

                Log.i(" SQL ", ""+cursor.getString(0) +" : "+ cursor.getString(1) +" : "+ cursor.getString(2) +" : "+ cursor.getString(3) +" : "+ cursor.getInt(4) +" : "+ cursor.getInt(5));
            } while (cursor.moveToNext());
        }

        return DF;
    }



    //////////9.18 김태우 디비 추가 /////////////////

    private ContentValues getStoreProductValues(int key, int id, int code, String name, int seedCost, int fruitCost, int effect,
                                                String explain, String iamge){

        ContentValues values = new ContentValues();
        values.put(storeProductKey,key);
        values.put(storeProductId,id);
        values.put(storeProductCode,code);
        values.put(storeProductName,name);
        values.put(storeProductSeedCost,seedCost);
        values.put(storeProductFruitCost,fruitCost);
        values.put(storeProductEffect,effect);
        values.put(storeProductExplain,explain);
        values.put(storeProductImagePath,iamge);
        return values;
    }

    private ContentValues getGoalFormatValues(int key, int id, String name, String content,int finalLevel, int drect ){

        ContentValues values = new ContentValues();
        values.put(goalFormatKey,key);
        values.put(goalFormatId,id);
        values.put(goalFormatName,name);
        values.put(goalFormatContent,content);
        values.put(goalFormatFinalLevel,finalLevel);
        values.put(goalFormatLevelDrect,drect);
        return values;
    }

    private ContentValues getGoalValues(int key, int id, int level, int condition,int coditionPower, int rewardType, int reward, int rewardPower){

        ContentValues values = new ContentValues();
        values.put(goalKey,key);
        values.put(goalId,id);
        values.put(goalLevel,level);
        values.put(goalCondition,condition);
        values.put(goalConditionPower,coditionPower);
        values.put(goalRewardType,rewardType);
        values.put(goaloalReward,reward);
        values.put(goalRewardPower,rewardPower);
        return values;
    }

    private ContentValues getWaterValues(int key, int id, int period, int penaltyTime, int penalty, int neddWaterNum){


        ContentValues values = new ContentValues();
        values.put(goalFormatKey,key);
        values.put(goalFormatId,id);
        values.put(waterPeriod,period);
        values.put(waterPenaltyTime,penaltyTime);
        values.put(waterPenalty,penalty);
        values.put(waterNeedWaterNum,neddWaterNum);
        return values;
    }


    private void StoreProductTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + STORE_PRODUCT_TABLE_NAME + "("
                + storeProductKey + " INTEGER NOT NULL PRIMARY KEY,"
                + storeProductId + " INTEGER NOT NULL,"
                + storeProductCode + " INTEGER NOT NULL,"
                + storeProductName + " TEXT NOT NULL,"
                + storeProductSeedCost + " INTEGER NOT NULL,"
                + storeProductFruitCost + " INTEGER NOT NULL,"
                + storeProductEffect + " INTEGER NOT NULL,"
                + storeProductExplain + " TEXT NOT NULL,"
                + storeProductImagePath + " TEXT NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(STORE_PRODUCT_TABLE_NAME,null,getStoreProductValues(0,1,0000001,"자동 클릭",-1,300,1,"보유 시 초당 10회 자동 탭.","ImagePath"));
        sqLiteDatabase.insert(STORE_PRODUCT_TABLE_NAME,null,getStoreProductValues(1,2,0000002,"체력 포션",-1,15,2,"체력 20% 회복.","ImagePath"));
        sqLiteDatabase.insert(STORE_PRODUCT_TABLE_NAME,null,getStoreProductValues(2,3,0000003,"부활 포션",-1,100,3,"체력이 0%가 되어 시들어버린 꽃을 부활시킴.","ImagePath"));
        sqLiteDatabase.insert(STORE_PRODUCT_TABLE_NAME,null,getStoreProductValues(3,4,0000004,"물",-1,3,4,"화면에 띄워놓은 꽃에 물주기 옵션이 활성화되면 물을 줄 때 사용하는 아이템","ImagePath"));
        sqLiteDatabase.insert(STORE_PRODUCT_TABLE_NAME,null,getStoreProductValues(4,4,0000004,"물",10000,-1,4,"화면에 띄워놓은 꽃에 물주기 옵션이 활성화되면 물을 줄 때 사용하는 아이템","ImagePath"));

    }

    private void GoalFormatTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + GOAL_FORMAT_TABLE_NAME + "("
                + goalFormatKey + " INTEGER NOT NULL PRIMARY KEY,"
                + goalFormatId + " INTEGER NOT NULL,"
                + goalFormatName + " TEXT NOT NULL,"
                + goalFormatContent + " TEXT NOT NULL,"
                + goalFormatFinalLevel +" INTEGER NOT NULL,"
                + goalFormatLevelDrect +" INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(0,1,"꽃과 친해지기","로그인 일 수(누적)",11,0));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(1,2,"꽃 향기가나","꽃 구매(누적)",5,11));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(2,3,"홀씨 날리기 작전","민들레 레벨업",8,16));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(3,4,"별이 피는 바람에","호야 레벨업",8,24));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(4,5,"자주색 치맛자락","나팔꽃 레벨업",8,32));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(5,6,"진달래 아니에요","철쭉 레벨업",8,40));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(6,7,"상처받아도 난 몰라요","장미 레벨업",8,48));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(7,8,"애청자","광고 시청",20,56));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(8,9,"액정의 안부를 묻다","터치 레벨업",12,76));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(9,10,"불타는 액정","터치 수",20,88));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(10,11,"함께 산책가요","꽃을 밖에 내놓기",1,108));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(11,12,"부자 되세요","아이템 구매",20,109));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(12,13,"아낌 없이 주는","아이템 사용",20,129));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(13,14,"비나이다. 비나이다. 씨 뿌리기!","누적 점수(씨앗)",20,149));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(14,15,"영원히 보고싶어","드라이 꽃 만들기",10,169));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(15,16,"Touch My Body","쓰다듬기 레벨업",8,179));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(16,17,"어멋! 지금 어딜 만져요!","쓰다듬기 사용 수",20,187));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(17,18,"이글아이","바라보기 레벨업",8,207));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(18,19,"꽃이랑 눈싸움","바라보기 사용 수",20,215));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(19,20,"꼿꼿하게 핀 허리에","가지치기 레벨업",8,235));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(20,21,"새침한 똑 단발","가지치기 사용 수",20,243));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(21,22,"국어능력인증시험","말하기 레벨업",8,263));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(22,23,"대화가 필요해","말하기 사용 수",20,271));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(23,24,"진정한 소리꾼","노래들려주기 레벨업",8,291));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(24,25,"가수해도 되겠어요","노래들려주기 사용 수",20,299));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(25,26,"비구름을 그릴게요","물받기 레벨업",5,319));
        sqLiteDatabase.insert(GOAL_FORMAT_TABLE_NAME,null,getGoalFormatValues(26,27,"비가 오려나.. 무릎이 쑤셔","물받기 사용 수",5,324));

    }

    private void GoalTable(SQLiteDatabase sqLiteDatabase) {


        String CREATE_TABLE = "CREATE TABLE " + GOAL_TABLE_NAME + "("
                + goalKey + " INTEGER NOT NULL PRIMARY KEY,"
                + goalId + " INTEGER NOT NULL,"
                + goalLevel + " INTEGER NOT NULL,"
                + goalCondition + " INTEGER NOT NULL,"
                + goalConditionPower + " INTEGER NOT NULL,"
                + goalRewardType + " INTEGER NOT NULL,"
                + goaloalReward + " INTEGER NOT NULL,"
                + goalRewardPower + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(0,1,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(1,1,2,2,0,1,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(2,1,3,5,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(3,1,4,10,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(4,1,5,15,0,1,60,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(5,1,6,20,0,1,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(6,1,7,25,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(7,1,8,35,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(8,1,9,50,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(9,1,10,70,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(10,1,11,100,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(11,2,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(12,2,2,2,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(13,2,3,3,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(14,2,4,4,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(15,2,5,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(16,3,1,50,0,2,150,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(17,3,2,100,0,2,750,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(18,3,3,150,0,2,3,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(19,3,4,200,0,2,25,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(20,3,5,250,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(21,3,6,300,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(22,3,7,350,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(23,3,8,400,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(24,4,1,50,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(25,4,2,100,0,2,200,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(26,4,3,150,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(27,4,4,200,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(28,4,5,250,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(29,4,6,300,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(30,4,7,350,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(31,4,8,400,0,3,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(32,5,1,50,0,2,400,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(33,5,2,100,0,2,1,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(34,5,3,150,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(35,5,4,200,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(36,5,5,250,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(37,5,6,300,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(38,5,7,350,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(39,5,8,400,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(40,6,1,50,0,2,100,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(41,6,2,100,0,5,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(42,6,3,150,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(43,6,4,200,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(44,6,5,250,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(45,6,6,300,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(46,6,7,350,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(47,6,8,400,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(48,7,1,50,0,2,5,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(49,7,2,100,0,2,500,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(50,7,3,150,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(51,7,4,200,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(52,7,5,250,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(53,7,6,300,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(54,7,7,350,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(55,7,8,400,0,4,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(56,8,1,1,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(57,8,2,2,0,5,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(58,8,3,3,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(59,8,4,5,0,1,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(60,8,5,8,0,1,20,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(61,8,6,13,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(62,8,7,21,0,2,10,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(63,8,8,34,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(64,8,9,55,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(65,8,10,89,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(66,8,11,144,0,1,60,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(67,8,12,233,0,1,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(68,8,13,377,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(69,8,14,610,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(70,8,15,987,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(71,8,16,1597,0,2,30,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(72,8,17,2584,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(73,8,18,4181,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(74,8,19,6765,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(75,8,20,10946,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(76,9,1,100,0,2,500,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(77,9,2,200,0,2,77,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(78,9,3,300,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(79,9,4,400,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(80,9,5,500,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(81,9,6,600,0,2,30,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(82,9,7,700,0,2,1,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(83,9,8,800,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(84,9,9,900,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(85,9,10,1000,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(86,9,11,1100,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(87,9,12,1200,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(88,10,1,50,0,2,500,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(89,10,2,100,0,2,5,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(90,10,3,250,0,2,20,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(91,10,4,700,0,2,60,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(92,10,5,2000,0,2,200,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(93,10,6,7500,0,2,1,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(94,10,7,30000,0,2,25,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(95,10,8,100000,0,2,600,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(96,10,9,185000,0,2,5,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(97,10,10,300000,0,2,40,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(98,10,11,450000,0,4,2,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(99,10,12,620000,0,2,2,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(100,10,13,830000,0,2,12,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(101,10,14,1070000,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(102,10,15,1300000,0,2,300,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(103,10,16,1590000,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(104,10,17,1900000,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(105,10,18,2340000,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(106,10,19,3000000,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(107,10,20,5000000,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(108,11,1,1,0,2,1,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(109,12,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(110,12,2,2,0,2,5,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(111,12,3,5,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(112,12,4,11,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(113,12,5,21,0,2,5,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(114,12,6,36,0,5,20,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(115,12,7,57,0,2,100,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(116,12,8,85,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(117,12,9,121,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(118,12,10,166,0,3,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(119,12,11,221,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(120,12,12,287,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(121,12,13,365,0,2,50,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(122,12,14,456,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(123,12,15,561,0,3,12,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(124,12,16,681,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(125,12,17,817,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(126,12,18,970,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(127,12,19,1141,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(128,12,20,1330,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(129,13,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(130,13,2,5,0,2,5,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(131,13,3,14,0,2,50,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(132,13,4,30,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(133,13,5,55,0,2,3,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(134,13,6,91,0,5,20,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(135,13,7,140,0,2,100,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(136,13,8,204,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(137,13,9,285,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(138,13,10,385,0,3,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(139,13,11,506,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(140,13,12,650,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(141,13,13,819,0,2,40,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(142,13,14,983,0,3,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(143,13,15,1208,0,1,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(144,13,16,1464,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(145,13,17,1753,0,4,2,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(146,13,18,2077,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(147,13,19,2438,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(148,13,20,2838,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(149,14,1,1,1,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(150,14,2,5,1,2,500,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(151,14,3,25,1,2,3,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(152,14,4,100,1,2,10,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(153,14,5,500,1,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(154,14,6,3700,1,2,400,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(155,14,7,10500,1,2,1,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(156,14,8,32,2,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(157,14,9,79,2,1,75,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(158,14,10,185,2,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(159,14,11,391,2,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(160,14,12,900,2,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(161,14,13,2750,2,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(162,14,14,15,3,2,1,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(163,14,15,91500,2,2,9,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(164,14,16,777777,2,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(165,14,17,1,4,2,100,4));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(166,14,18,5150,3,4,2,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(167,14,19,24,4,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(168,14,20,170,4,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(169,15,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(170,15,2,2,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(171,15,3,3,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(172,15,4,4,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(173,15,5,5,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(174,15,6,7,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(175,15,7,10,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(176,15,8,15,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(177,15,9,20,0,1,250,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(178,15,10,30,0,1,300,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(179,16,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(180,16,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(181,16,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(182,16,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(183,16,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(184,16,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(185,16,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(186,16,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(187,17,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(188,17,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(189,17,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(190,17,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(191,17,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(192,17,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(193,17,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(194,17,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(195,17,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(196,17,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(197,17,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(198,17,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(199,17,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(200,17,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(201,17,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(202,17,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(203,17,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(204,17,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(205,17,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(206,17,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(207,18,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(208,18,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(209,18,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(210,18,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(211,18,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(212,18,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(213,18,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(214,18,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(215,19,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(216,19,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(217,19,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(218,19,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(219,19,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(220,19,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(221,19,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(222,19,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(223,19,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(224,19,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(225,19,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(226,19,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(227,19,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(228,19,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(229,19,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(230,19,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(231,19,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(232,19,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(233,19,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(234,19,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(235,20,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(236,20,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(237,20,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(238,20,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(239,20,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(240,20,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(241,20,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(242,20,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(243,21,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(244,21,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(245,21,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(246,21,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(247,21,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(248,21,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(249,21,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(250,21,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(251,21,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(252,21,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(253,21,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(254,21,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(255,21,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(256,21,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(257,21,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(258,21,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(259,21,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(260,21,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(261,21,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(262,21,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(263,22,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(264,22,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(265,22,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(266,22,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(267,22,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(268,22,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(269,22,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(270,22,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(271,23,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(272,23,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(273,23,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(274,23,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(275,23,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(276,23,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(277,23,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(278,23,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(279,23,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(280,23,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(281,23,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(282,23,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(283,23,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(284,23,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(285,23,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(286,23,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(287,23,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(288,23,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(289,23,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(290,23,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(291,24,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(292,24,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(293,24,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(294,24,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(295,24,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(296,24,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(297,24,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(298,24,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(299,25,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(300,25,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(301,25,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(302,25,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(303,25,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(304,25,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(305,25,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(306,25,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(307,25,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(308,25,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(309,25,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(310,25,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(311,25,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(312,25,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(313,25,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(314,25,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(315,25,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(316,25,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(317,25,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(318,25,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(319,26,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(320,26,2,2,0,1,20,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(321,26,3,3,0,1,30,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(322,26,4,4,0,1,40,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(323,26,5,5,0,1,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(324,27,1,1,0,5,10,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(325,27,2,5,0,5,50,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(326,27,3,15,0,4,1,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(327,27,4,30,0,3,5,0));
        sqLiteDatabase.insert(GOAL_TABLE_NAME,null,getGoalValues(328,27,5,50,0,4,3,0));


    }

    private void WaterTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + WATER_TABLE_NAME + "("
                + waterKey + " INTEGER NOT NULL PRIMARY KEY,"
                + waterId + " INTEGER NOT NULL,"
                + waterPeriod + " INTEGER NOT NULL,"
                + waterPenaltyTime + " INTEGER NOT NULL,"
                + waterPenalty + " INTEGER NOT NULL,"
                + waterNeedWaterNum + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(0,1,300,120,1,1));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(1,2,480,192,1,2));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(2,3,900,360,2,4));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(3,4,1200,480,3,6));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(4,5,1800,720,3,10));
    }


    public ArrayList<menuStoreActivity.ProductInfomation> getStoreProductInform(){
        ArrayList<menuStoreActivity.ProductInfomation> SP = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+STORE_PRODUCT_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                menuStoreActivity.ProductInfomation productInfomations = new menuStoreActivity.ProductInfomation(cursor.getInt(1),cursor.getInt(2),cursor.getString(3),
                        cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7),cursor.getString(8));
                SP.add(productInfomations);

                Log.i(" SQL ", ""+cursor.getInt(1)+" : "+cursor.getInt(2)+" : "+cursor.getString(3)+" : "+
                        cursor.getInt(4)+" : "+cursor.getInt(5)+" : "+cursor.getInt(6)+" : "+cursor.getString(7)+" : "+cursor.getString(8));

            } while (cursor.moveToNext());
        }

        return SP;
    }

    public ArrayList<goalListDialog.GoalFormatInformation> getGoalFormatInform(){
        ArrayList<goalListDialog.GoalFormatInformation> GF = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+GOAL_FORMAT_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
               goalListDialog.GoalFormatInformation goalFormatInformation = new goalListDialog.GoalFormatInformation(cursor.getInt(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5));
                GF.add(goalFormatInformation);

                Log.i(" SQL ", ""+cursor.getInt(1)+" : "+cursor.getString(2)+" : "+cursor.getString(3)+" : "+" : "+cursor.getInt(4)+" : "+cursor.getInt(5));

            } while (cursor.moveToNext());
        }

        return GF;
    }

    public goalListDialog.GoalInformation getGoalInform(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(GOAL_TABLE_NAME,new String[] {goalKey,goalId,goalLevel,goalCondition,goalConditionPower,goalRewardType,goaloalReward,goalRewardPower},goalKey+"=?",
                new String[] {String.valueOf(id)},null,null,null,null);
        if(cursor != null){
            cursor.moveToFirst();
        }

        Log.i(" SQL ",  ""+ cursor.getInt(0) +"/"+cursor.getInt(1) +"/" +cursor.getInt(2) +"/" + cursor.getInt(3) +"/" + cursor.getInt(4)+"/" + cursor.getInt(5)+"/" + cursor.getInt(6)+"/" + cursor.getInt(7));

        goalListDialog.GoalInformation goalInform = new goalListDialog.GoalInformation(
                cursor.getInt(0), cursor.getInt(1),cursor.getInt(2),cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7));

        return goalInform;
    }

    public ArrayList<MainActivity.Water> getWaterInform(){
        ArrayList<MainActivity.Water> W = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+WATER_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                MainActivity.Water water = new MainActivity.Water(cursor.getInt(0),cursor.getInt(1), cursor.getInt(3), cursor.getInt(2),cursor.getInt(4),cursor.getInt(5));
                W.add(water);

                Log.i(" SQL ", ""+cursor.getInt(0)+" : "+cursor.getInt(1)+" : "+cursor.getInt(2)+" : "+cursor.getInt(3)+" : "+
                        cursor.getInt(4)+" : "+cursor.getInt(5));

            } while (cursor.moveToNext());
        }

        return W;
    }

    /**


     밑으로 테스트용 1.8 데이터
     */


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
    private static final String waterTime = "waterTime";
    private static final String waterPassiveTime = "waterPassiveTime";
    private static final String passiveRate = "passiveRate";

    private void flowerTable(SQLiteDatabase sqLiteDatabase) {
        //Flower Table
        String CREATE_TABLE = "CREATE TABLE " + FLOWER_TABLE_NAME + "("
                + flowerId + " INTEGER NOT NULL PRIMARY KEY,"
                + flowerName + " TEXT NOT NULL,"
                + flowerImage + " TEXT NOT NULL,"
                + flowerButtonImage + " TEXT NOT NULL,"
                + flowerCost + " DOUBLE NOT NULL,"
                + flowerScore + " INTEGER NOT NULL,"
                + flowerTab + " INTEGER NOT NULL,"
                + flowerLevel + " INTEGER NOT NULL,"
                + waterTime + " INTEGER NOT NULL,"
                + waterPassiveTime + " INTEGER NOT NULL,"
                + passiveRate + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //Insert Flower Data
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(0, "민들레", " ", " ", 50, 1, 0, 400, 300, 120, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(1, "나팔꽃", " ", " ", 50, 3000, 200, 0, 480, 192, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(2, "장미", " ", " ", 50, 100000, 400, 0,900, 360, 2));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(3, "철쭉", " ", " ", 50, 3000, 200, 0, 480, 192, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(4, "호야", " ", " ", 999999999, 100000, 300, 400,900, 360, 2));
    }

    private ContentValues getFlowerValues(int id, String name, String image, String buttonImage,
                                          long cost, int score, int tab, int level, int time, int passiveTime, int passive) {

        ContentValues values = new ContentValues();

        values.put(flowerId, id);
        values.put(flowerName, name);
        values.put(flowerImage, image);
        values.put(flowerButtonImage, buttonImage);
        values.put(flowerCost, cost);
        values.put(flowerScore, score);
        values.put(flowerTab, tab);
        values.put(flowerLevel, level);
        values.put(waterTime, time);
        values.put(waterPassiveTime, passiveTime);
        values.put(passiveRate, passive);

        return values;

    }

    public class Flower {

        private int flowerNo;
        private String flowerName;
        private String flowerImage, flowerButtonImage;
        private int flowerCost, flowerScore;
        private int flowerTab, flowerLevel;
        private boolean buyType;
        private int time; //물을 줘야하는 시간
        private int waterPassiveTime; //물을 주지 않을 시 패시브
        private int passiveRate;
        private int level;

        public Flower(){

        }

        public Flower(int flowerNo, String flowerName, String flowerImage, String flowerButtonImage, int flowerCost,
                      int flowerScore, int flowerTab, int flowerLevel, int time, int waterPassiveTime, int passiveRate) {
            this.flowerNo = flowerNo;
            this.flowerName = flowerName;
            this.flowerImage = flowerImage;
            this.flowerButtonImage = flowerButtonImage;
            this.flowerCost = flowerCost;
            this.flowerScore = flowerScore;
            this.flowerTab = flowerTab;
            this.flowerLevel = flowerLevel;
            this.time = time;
            this.waterPassiveTime = waterPassiveTime;
            this.passiveRate = passiveRate;
        }

        public int getFlowerNo() {
            return flowerNo;
        }

        public void setFlowerNo(int flowerNo) {
            this.flowerNo = flowerNo;
        }

        public String getFlowerName() {
            return flowerName;
        }

        public void setFlowerName(String flowerName) {
            this.flowerName = flowerName;
        }

        public String getFlowerImage() {
            return flowerImage;
        }

        public void setFlowerImage(String flowerImage) {
            this.flowerImage = flowerImage;
        }

        public String getFlowerButtonImage() {
            return flowerButtonImage;
        }

        public void setFlowerButtonImage(String flowerButtonImage) {
            this.flowerButtonImage = flowerButtonImage;
        }

        public int getFlowerCost() {
            return flowerCost;
        }

        public void setFlowerCost(int flowerCost) {
            this.flowerCost = flowerCost;
        }

        public int getFlowerScore() {
            return flowerScore;
        }

        public void setFlowerScore(int flowerScore) {
            this.flowerScore = flowerScore;
        }

        public int getFlowerTab() {
            return flowerTab;
        }

        public void setFlowerTab(int flowerTab) {
            this.flowerTab = flowerTab;
        }

        public int getFlowerLevel() {
            return flowerLevel;
        }

        public void setFlowerLevel(int flowerLevel) {
            this.flowerLevel = flowerLevel;
        }

        public boolean isBuyType() {
            return buyType;
        }

        public void setBuyType(boolean buyType) {
            this.buyType = buyType;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getWaterPassiveTime() {
            return waterPassiveTime;
        }

        public void setWaterPassiveTime(int waterPassiveTime) {
            this.waterPassiveTime = waterPassiveTime;
        }

        public int getPassiveRate() {
            return passiveRate;
        }

        public void setPassiveRate(int passiveRate) {
            this.passiveRate = passiveRate;
        }
    }

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
                flower.setFlowerCost(cursor.getInt(4));
                flower.setFlowerScore(cursor.getInt(5));
                flower.setFlowerTab(cursor.getInt(6));
                flower.setFlowerLevel(cursor.getInt(7));

                Log.i(" SQL ", ""+cursor.getInt(0) +" : "+ cursor.getString(1) +" : "+ cursor.getString(2) +" : "+ cursor.getString(3) +" : "+ cursor.getInt(4)+" : "+ cursor.getInt(5)+" : "+ cursor.getInt(6)+" : "+ cursor.getInt(7));

                flowers.add(flower);
            } while (cursor.moveToNext());
        }

        return flowers;
    }
}
