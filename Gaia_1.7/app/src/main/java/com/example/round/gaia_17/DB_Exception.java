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
    private static final String DATABASE_NAME="Gaia123459";

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
    final private static String
    final private static String
    final private static String
    final private static String
    final private static String
    final private static String
    final private static String
    
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        //del(sqLiteDatabase);
        flowerTable(sqLiteDatabase);
        ActiveSkillTable(sqLiteDatabase);
        ActiveSkillFormatTable(sqLiteDatabase);
        PassiveSkillTable(sqLiteDatabase);
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

    //String name, String image, String skillImage, 제외
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
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(0, "민들레", " ", " ", 50, 1, 0, 0, 300, 120, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(1, "나팔꽃", " ", " ", 900000000, 3000, 200, 400, 480, 192, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(2, "장미", " ", " ", 999999999, 100000, 400, 400,900, 360, 2));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(3, "철쭉", " ", " ", 900000000, 3000, 200, 330, 480, 192, 1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME, null, getFlowerValues(4, "호야", " ", " ", 999999999, 100000, 400, 390,900, 360, 2));
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
