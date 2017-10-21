package com.example.round.gaia_18;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.round.gaia_18.Data.DryFlower;
import com.example.round.gaia_18.Data.Flower;
import com.example.round.gaia_18.Data.FlowerData;
import com.example.round.gaia_18.Data.GoalData;
import com.example.round.gaia_18.Data.GoalInfo;
import com.example.round.gaia_18.Data.SkillData;
import com.example.round.gaia_18.Data.SkillInfo;
import com.example.round.gaia_18.Data.StoreProduct;
import com.example.round.gaia_18.Data.TabData;
import com.example.round.gaia_18.Data.Water;

import java.util.ArrayList;

import static com.example.round.gaia_18.OverlayService.dataList;

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

    //Temp Table
    private static final String FLOWER_TEMP = "temp";
    private static final String MAX_TEMP = "max";
    private static final String MIN_TEMP = "min";
    private static final String PASSIVE = "passive";

    //Weather Table
    private static final String WEATHER_TABLE = "weather";
    private static final String WEATHER_ID = "weatherNo";
    private static final String WEATHER_NAME = "weatherName";

    //skill info
    private static final String SKILL_INFO_TABLE_NAME = "skillInfo";
    private static final String skillId = "skillNo";
    private static final String skillName = "skillName";
    private static final String skillImage = "skillImage";
    private static final String skillButtonImage = "skillButtonImage";
    private static final String skillCoolTime = "coolTime";
    private static final String passive = "activeSkill";
    private static final String skillType = "skillCase";
    private static final String skillMaxLevel = "skillMaxLevel";

    //skill data
    private static final String SKILL_DATA_TABLE_NAME = "skillData";
    private static final String skillPrimaryKey ="primaryKey";
    private static final String skillLevel = "level";
    private static final String skillBuyType = "buyType";
    private static final String skillCost = "cost";
    private static final String skillEffect = "effect";
    private static final String skillCostType = "type";

    //Dry Flower
    private static final String DRY_FLOWER_TABLE_NAME = "dryFlower";
    private static final String dryFlowerNo = "dryFlowerNo";
    private static final String dryFlowerScore = "dryFlowerScore";
    private static final String dryFlowerScoreType = "dryFlowerScoreType";

    //Goal Info
    private static final String GOAL_INFO_TABLE_NAME="goalInfo";
    private static final String goalNo = "goalNo";
    private static final String goalName ="goalName";
    private static final String goalType ="goalType";
    private static final String goalMaxLevel = "goalMaxLevel";

    //Goal Data
    private static final String GOAL_DATA_TABLE_NAME = "goalData";
    private static final String goalPrimaryKey = "primaryKey";
    private static final String goalLevel = "level";
    private static final String goalCondition = "goalCondition";
    private static final String goalConditionType = "goalType";
    private static final String goalRewardType="rewardType";
    private static final String goalReward = "reward";
    private static final String goalRewardCostType = "rewardCostType";

    //Store DataBase
    private static final String STORE_TABLE_NAME ="store";
    private static final String storeNo="productId";
    private static final String storeName="productName";
    private static final String storeBuyType ="productSeed";
    private static final String storeCost="productFruit";
    private static final String storeEffectType="productEffectType";
    //Water
    final private static String WATER_TABLE_NAME = "WATER_TABLE";
    final private static String waterKey = "KEY_KEY";
    final private static String waterId = "KEY_ID";
    final private static String waterPeriod = "KEY_PERIOD";
    final private static String waterPenaltyTime = "KEY_PENALTY_TIME";
    final private static String waterPenalty = "KEY_PENALTY";
    final private static String waterNeedWaterNum = "KEY_NEED_WATER_NUM";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FLOWER_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+FLOWER_ALGORITHM_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+WEATHER_TABLE);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SKILL_INFO_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+SKILL_DATA_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DRY_FLOWER_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GOAL_INFO_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+GOAL_DATA_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+STORE_TABLE_NAME);
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+WATER_TABLE_NAME);
        //flower Table 구축
        flowerTable(sqLiteDatabase);
        //flower 수식 data DB 구축
        flowerDateTable(sqLiteDatabase);
        //weather Table DataBase 구축
        weatherTable(sqLiteDatabase);
        //Skill Information DataBase 구축
        SkillInfoTable(sqLiteDatabase);
        //Skill Data DataBase 구축
        SkillDataTable(sqLiteDatabase);
        //DryFlower DataBase 구축
        DryFlowerTable(sqLiteDatabase);
        //GoalInfo DataBase 구축
        getGoalInfoTable(sqLiteDatabase);
        //GoalData DataBase 구축
        getGoalDataTable(sqLiteDatabase);
        //Store DataBase 구축
        getStoreTable(sqLiteDatabase);
        // 물주기테이블 구축
        WaterTable(sqLiteDatabase);
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
                + flowerTab + " INTEGER NOT NULL," // 선행스킬(TAB)
                + flowerLevel + " INTEGER NOT NULL," //앞 꽃의 레벨
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

    //flower 수식과 관련된 DB 구축
    private void weatherTable(SQLiteDatabase sqLiteDatabase){
        //Flower Table
        String CREATE_TABLE = "CREATE TABLE " + WEATHER_TABLE + "("
                + WEATHER_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
                + WEATHER_NAME + " TEXT NOT NULL,"
                + "imageType"+" INTEGER NOT NULL, "
                + "rain" + " INTEGER NOT NULL,"
                + "disaster" + " INTEGER NOT NULL,"
                + "flower1" + " INTEGER NOT NULL,"
                + "flower2" + " INTEGER NOT NULL,"
                + "flower3" + " INTEGER NOT NULL,"
                + "flower4" + " INTEGER NOT NULL,"
                + "flower5" + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(0,"thunderstorm with light rain ",1,1,0,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(1,"thunderstorm with rain",1,1,0,3,0,0,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(2,"thunderstorm with heavy rain",1,1,1,0,-3,-3,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(3,"light thunderstorm",1,0,1,3,0,0,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(4,"thunderstorm",1,0,1,-3,-5,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(5,"heavy thunderstorm",1,0,1,-5,-5,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(6,"ragged thunderstorm",1,0,1,-3,-5,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(7,"thunderstorm with light drizzle",1,0,0,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(8,"thunderstorm with drizzle",1,0,1,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(9,"thunderstorm with heavy drizzle",1,0,1,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(10,"light intensity drizzle",2,0,0,5,5,5,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(11,"drizzle",2,0,0,7,7,5,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(12,"heavy intensity drizzle",2,0,0,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(13,"light intensity drizzle rain",1,1,0,7,7,5,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(14,"drizzle rain",1,1,0,5,5,5,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(15,"heavy intensity drizzle rain",1,1,0,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(16,"shower rain and drizzle",1,1,0,5,5,5,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(17,"heavy shower rain and drizzle",1,1,0,5,3,3,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(18,"shower drizzle",1,0,0,5,5,5,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(19,"light rain",1,1,0,7,7,7,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(20,"moderate rain",1,1,0,5,5,5,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(21,"heavy intensity rain",1,1,0,5,3,0,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(22,"very heavy rain",1,1,0,3,0,-3,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(23,"extreme rain",1,1,1,-3,-3,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(24,"freezing rain",1,1,1,-5,-5,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(25,"light intensity shower rain",1,1,0,7,5,5,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(26,"shower rain",1,1,0,7,5,5,5,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(27,"heavy intensity shower rain",1,1,0,3,3,3,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(28,"ragged shower rain ",1,1,0,3,3,3,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(29,"light snow",3,0,0,0,-5,-3,-5,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(30,"snow",3,0,1,-3,-7,-3,-5,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(31,"heavy snow",3,0,1,-3,-7,-3,-5,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(32,"sleet",3,0,1,-5,-5,-3,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(33,"shower sleet",3,0,0,-5,-7,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(34,"light rain and snow",3,1,0,3,0,0,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(35,"rain and snow",3,1,0,3,-3,0,-3,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(36,"light shower snow",3,0,0,0,-7,-3,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(37,"shower snow",3,0,1,-5,-7,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(38,"heavy shower snow ",3,0,1,-7,-10,-5,-7,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(39,"mist",2,0,0,7,7,5,7,10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(40,"smoke",2,0,0,0,-3,-7,-3,-7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(41,"haze",2,0,0,0,0,0,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(42,"sand, dust whirls",5,0,1,-5,-10,-10,-10,-10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(43,"fog",2,0,0,5,5,3,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(44,"sand",5,0,1,-3,-5,-5,-5,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(45,"dust",5,0,1,-5,-7,-10,-5,-7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(46,"volcanic ash",5,0,1,-10,-15,-15,-10,-15));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(47,"squalls",1,0,0,-3,-5,-7,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(48,"tornado",4,0,1,-5,-7,-10,-3,-5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(49,"clear sky",0,0,0,10,7,7,7,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(50,"few clouds",0,0,0,7,3,3,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(51,"scattered clouds",0,0,0,7,3,3,3,7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(52,"broken clouds",0,0,0,5,0,0,3,7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(53,"overcast clouds",2,0,0,5,-3,-3,3,10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(54,"tropical storm",4,0,1,-7,-10,-10,-7,-10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(55,"hurricane",4,0,1,-7,-10,-10,-7,-10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(56,"cold",0,0,0,3,0,0,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(57,"hot",0,0,0,0,3,0,3,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(58,"windy",0,0,0,5,0,0,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(59,"hail",1,0,0,-3,-3,-5,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(60,"calm",0,0,0,5,5,5,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(61,"light breeze",5,0,0,7,5,5,5,7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(62,"gentle breeze",5,0,0,7,5,5,5,7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(63,"moderate breeze",5,0,0,5,3,3,5,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(64,"fresh breeze",5,0,0,5,3,3,3,5));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(65,"strong breeze",5,0,0,5,0,0,3,3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(66,"high wind, near gale",5,0,0,3,-3,-3,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(67,"gale",5,0,0,0,-3,-3,0,0));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(68,"severe gale",5,0,0,-3,-3,-3,-3,-3));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(69,"storm",1,0,1,-5,-5,-7,-5,-7));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(70,"violent storm",1,0,1,-7,-10,-10,-7,-10));
        sqLiteDatabase.insert(WEATHER_TABLE, null, getWeather(71,"hurricane ",4,0,1,-7,-10,-10,-7,-10));
    }

    private ContentValues getWeather(int id, String name,int image, int rain, int disaster, int flower1, int flower2, int flower3, int flower4, int flower5) {

        ContentValues values = new ContentValues();

        values.put(WEATHER_ID, id);
        values.put(WEATHER_NAME,name);
        values.put("imageType",image);
        values.put("rain",rain);
        values.put("disaster",disaster);
        values.put("flower1",flower1);
        values.put("flower2",flower2);
        values.put("flower3",flower3);
        values.put("flower4",flower4);
        values.put("flower5",flower5);

        return values;
    }

    public ArrayList<Integer> getWeatherPassive(String weather){

        ArrayList<Integer> passive = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + WEATHER_TABLE+" WHERE "+WEATHER_NAME+" = '"+weather+"'";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                passive.add(cursor.getInt(2));
                passive.add(cursor.getInt(3));
                passive.add(cursor.getInt(4));
                passive.add(cursor.getInt(5));
                passive.add(cursor.getInt(6));
                passive.add(cursor.getInt(7));
                passive.add(cursor.getInt(8));
                passive.add(cursor.getInt(9));
            } while (cursor.moveToNext());
        }

        return passive;
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
                if(cursor.getInt(7) == 0) flower.setBuyPossible(true);
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

    public ArrayList<SkillInfo> getAllSkillInfo(){

        ArrayList<SkillInfo> skillInfos = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+SKILL_INFO_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                SkillInfo skillInfo = new SkillInfo();
                skillInfo.setSkillNo(cursor.getInt(0));
                skillInfo.setSkillName(cursor.getString(1));
                skillInfo.setSkillImage(cursor.getString(2));
                skillInfo.setSkillButtonImage(cursor.getString(3));
                skillInfo.setCoolTime(cursor.getInt(4));
                skillInfo.setPassive(cursor.getInt(5));
                skillInfo.setSkillCase(cursor.getInt(6));
                skillInfo.setSkillMaxLevel(cursor.getInt(7));

                skillInfos.add(skillInfo);
            }while(cursor.moveToNext());
        }

        return skillInfos;
    }

    private void SkillInfoTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE "+SKILL_INFO_TABLE_NAME+"("
                +skillId+" INTEGER NOT NULL PRIMARY KEY,"
                +skillName + " TEXT NOT NULL,"
                +skillImage + " TEXT NOT NULL,"
                +skillButtonImage + " TEXT NOT NULL,"
                +skillCoolTime + " INTEGER NOT NULL,"
                +passive + " INTEGER NOT NULL,"
                +skillType + " INTEGER NOT NULL,"
                +skillMaxLevel+ " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //sqLiteDatabase.insert(ACTIVE_SKILL_FORMAT_TABLE_NAME,null,getActiveSkillFormatValues(1,"","","",300,0,0,""));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(0,"기본 탭","","",300,1,6,1200));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(1,"쓰다듬기","","",300,0,0,30));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(2,"가지치기","","",300,0,1,30));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(3,"노래 들려주기","","",300,0,2,30));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(4,"말하기","","",0,1,3,30));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(5,"바라보기","","",0,1,4,30));
        sqLiteDatabase.insert(SKILL_INFO_TABLE_NAME,null,getSkillInfo(6,"물받기","","",86400,0,5,5));
    }

    private ContentValues getSkillInfo(int id, String name, String image, String buttonImage, int coolTime, int passive, int skillType,int skillMaxLevel){

        ContentValues values = new ContentValues();

        values.put(skillId,id);
        values.put(skillName,name);
        values.put(skillImage,image);
        values.put(skillButtonImage,buttonImage);
        values.put(skillCoolTime,coolTime);
        values.put(this.passive,passive);
        values.put(this.skillType,skillType);
        values.put(this.skillMaxLevel,skillMaxLevel);

        return values;
    }

    private void SkillDataTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE "+SKILL_DATA_TABLE_NAME+"("
                +skillPrimaryKey+" INTEGER NOT NULL PRIMARY KEY,"
                +skillId + " INTEGER NOT NULL,"
                +skillLevel + " INTEGER NOT NULL,"
                +skillBuyType + " INTEGER NOT NULL,"
                +skillCost + " INTEGER NOT NULL,"
                +skillEffect + " INTEGER NOT NULL,"
                +skillCostType + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(0,-1,1,2,100,10,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(1,0,1,2,100,12,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(2,0,2,2,500,14,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(3,0,3,2,1,16,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(4,0,4,2,5,18,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(5,0,5,2,20,20,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(6,0,6,2,100,22,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(7,0,7,2,500,24,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(8,0,8,2,1,26,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(9,0,9,2,3,28,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(10,0,10,2,5,30,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(11,0,11,1,50,33,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(12,0,12,1,50,36,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(13,0,13,1,50,39,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(14,0,14,1,50,42,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(15,0,15,1,50,45,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(16,0,16,1,60,48,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(17,0,17,1,70,51,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(18,0,18,1,80,54,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(19,0,19,1,90,57,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(20,0,20,1,100,60,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(21,0,21,1,110,63,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(22,0,22,1,120,66,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(23,0,23,1,130,69,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(24,0,24,1,140,72,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(25,0,25,1,150,75,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(26,0,26,1,160,78,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(27,0,27,1,170,81,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(28,0,28,1,180,84,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(29,0,29,1,190,87,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(30,0,30,1,200,90,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(31,1,1,2,100,50,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(32,1,2,2,500,100,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(33,1,3,2,1,150,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(34,1,4,2,5,200,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(35,1,5,2,20,250,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(36,1,6,2,100,300,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(37,1,7,2,500,350,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(38,1,8,2,1,400,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(39,1,9,2,3,450,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(40,1,10,2,5,500,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(41,1,11,1,50,600,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(42,1,12,1,50,700,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(43,1,13,1,50,800,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(44,1,14,1,50,900,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(45,1,15,1,50,1000,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(46,1,16,1,60,1100,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(47,1,17,1,70,1200,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(48,1,18,1,80,1300,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(49,1,19,1,90,1400,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(50,1,20,1,100,1500,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(51,1,21,1,110,1600,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(52,1,22,1,120,1700,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(53,1,23,1,130,1800,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(54,1,24,1,140,1900,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(55,1,25,1,150,2000,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(56,1,26,1,160,2100,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(57,1,27,1,170,2200,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(58,1,28,1,180,2300,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(59,1,29,1,190,2400,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(60,1,30,1,200,2500,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(61,2,1,2,100,12,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(62,2,2,2,500,14,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(63,2,3,2,1,16,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(64,2,4,2,5,18,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(65,2,5,2,20,20,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(66,2,6,2,100,22,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(67,2,7,2,500,24,1));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(68,2,8,2,1,26,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(69,2,9,2,3,28,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(70,2,10,2,5,30,2));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(71,2,11,1,50,33,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(72,2,12,1,50,36,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(73,2,13,1,50,39,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(74,2,14,1,50,42,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(75,2,15,1,50,45,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(76,2,16,1,60,48,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(77,2,17,1,70,51,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(78,2,18,1,80,54,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(79,2,19,1,90,57,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(80,2,20,1,100,60,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(81,2,21,1,110,63,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(82,2,22,1,120,66,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(83,2,23,1,130,69,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(84,2,24,1,140,72,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(85,2,25,1,150,75,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(86,2,26,1,160,78,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(87,2,27,1,170,81,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(88,2,28,1,180,84,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(89,2,29,1,190,87,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(90,2,30,1,200,90,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(91,3,1,1,50,2,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(92,3,2,1,60,4,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(93,3,3,1,70,6,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(94,3,4,1,80,8,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(95,3,5,1,90,10,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(96,3,6,1,100,12,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(97,3,7,1,110,14,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(98,3,8,1,120,16,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(99,3,9,1,130,18,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(100,3,10,1,140,20,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(101,3,11,1,150,22,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(102,3,12,1,160,24,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(103,3,13,1,170,26,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(104,3,14,1,180,28,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(105,3,15,1,190,30,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(106,3,16,1,200,32,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(107,3,17,1,200,34,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(108,3,18,1,200,36,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(109,3,19,1,200,38,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(110,3,20,1,200,40,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(111,3,21,1,200,42,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(112,3,22,1,200,44,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(113,3,23,1,200,46,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(114,3,24,1,200,48,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(115,3,25,1,200,50,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(116,3,26,1,200,52,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(117,3,27,1,200,54,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(118,3,28,1,200,56,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(119,3,29,1,200,58,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(120,3,30,1,200,60,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(121,4,1,1,50,10,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(122,4,2,1,60,20,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(123,4,3,1,70,30,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(124,4,4,1,80,40,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(125,4,5,1,90,50,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(126,4,6,1,100,60,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(127,4,7,1,110,70,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(128,4,8,1,120,80,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(129,4,9,1,130,90,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(130,4,10,1,140,100,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(131,4,11,1,150,120,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(132,4,12,1,160,140,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(133,4,13,1,170,160,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(134,4,14,1,180,180,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(135,4,15,1,190,200,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(136,4,16,1,200,220,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(137,4,17,1,200,240,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(138,4,18,1,200,260,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(139,4,19,1,200,280,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(140,4,20,1,200,300,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(141,4,21,1,200,320,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(142,4,22,1,200,340,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(143,4,23,1,200,360,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(144,4,24,1,200,380,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(145,4,25,1,200,400,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(146,4,26,1,200,420,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(147,4,27,1,200,440,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(148,4,28,1,200,460,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(149,4,29,1,200,480,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(150,4,30,1,200,500,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(151,5,1,1,50,30,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(152,5,2,1,60,50,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(153,5,3,1,70,80,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(154,5,4,1,80,120,0));
        sqLiteDatabase.insert(SKILL_DATA_TABLE_NAME   ,   null   ,   getSkillData(155,5,5,1,100,160,0));

    }

    private ContentValues getSkillData(int primaryKey,int id, int level, int buyType, int cost, int effect, int costType){

        ContentValues values = new ContentValues();
        values.put(skillPrimaryKey,primaryKey);
        values.put(skillId, id+1);
        values.put(skillLevel, level);
        values.put(skillBuyType, buyType);
        values.put(skillCost, cost);
        values.put(skillEffect, effect);
        values.put(skillCostType, costType);

        return values;
    }

    public void getAllSkillData(int skillNo, int Level) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

        if(Level > 0) {
            selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=" + skillNo + " and " + skillLevel + "=" + Level;

            SkillData skillData = new SkillData();
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    skillData.setSkillNo(cursor.getInt(1));
                    skillData.setSkillLevel(cursor.getInt(2));
                    skillData.setSkillEffect(cursor.getInt(5));
                } while (cursor.moveToNext());
            }

            selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=" + skillNo + " and " + skillLevel + "=" + (Level+1);
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    skillData.setbuyType(cursor.getInt(3));
                    skillData.setCost(cursor.getInt(6), cursor.getInt(4));
                } while (cursor.moveToNext());
            }
            skillData.setSkillBuy(true);

            dataList.putSkillData(skillNo,skillData);
        }
        else{ //skill을 구입하지 않음.
            SkillData skillData = new SkillData();
            skillData.setSkillNo(skillNo);
            skillData.setSkillLevel(Level);
            skillData.setSkillBuy(false);

            selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=" + skillNo + " and " + skillLevel + "=" + (Level+1);
            cursor = db.rawQuery(selectQuery, null);

            if (cursor.moveToFirst()) {
                do {
                    skillData.setbuyType(cursor.getInt(3));
                    skillData.setCost(cursor.getInt(6), cursor.getInt(4));
                } while (cursor.moveToNext());
            }

            dataList.putSkillData(skillNo,skillData);
        }

    }

    public void getTabSkillData(int level){
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

        selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=0";

        TabData skillData = new TabData();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                skillData.setSkillLevel(level);
                skillData.setBuyType(cursor.getInt(3));
                skillData.setCost(cursor.getInt(6),cursor.getInt(4));
                skillData.setScore(0,cursor.getInt(5));
            } while (cursor.moveToNext());
        }

        dataList.setTabData(skillData);
    }

    public SkillData getSkill(int id, int level){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

        selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=" + id + " and " + skillLevel + "=" + level;

        SkillData skillData = new SkillData();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                skillData.setSkillNo(cursor.getInt(1));
                skillData.setSkillLevel(cursor.getInt(2));
                skillData.setSkillEffect(cursor.getInt(5));
            } while (cursor.moveToNext());
        }

        selectQuery = "SELECT * FROM " + SKILL_DATA_TABLE_NAME + " WHERE " + skillId + "=" + id + " and " + skillLevel + "=" + (level+1);
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                skillData.setbuyType(cursor.getInt(3));
                skillData.setCost(cursor.getInt(6), cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        skillData.setSkillBuy(true);

        return skillData;
    }

    private void DryFlowerTable(SQLiteDatabase sqLiteDatabase){
        String CREATE_TABLE = "CREATE TABLE "+DRY_FLOWER_TABLE_NAME+"("
                +dryFlowerNo+" INTEGER NOT NULL PRIMARY KEY,"
                +dryFlowerScore + " INTEGER NOT NULL,"
                +dryFlowerScoreType + " INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(DRY_FLOWER_TABLE_NAME   ,   null   ,   getDryFlower(0,89400,0));
        sqLiteDatabase.insert(DRY_FLOWER_TABLE_NAME   ,   null   ,   getDryFlower(1,2250,1));
        sqLiteDatabase.insert(DRY_FLOWER_TABLE_NAME   ,   null   ,   getDryFlower(2,49095,1));
        sqLiteDatabase.insert(DRY_FLOWER_TABLE_NAME   ,   null   ,   getDryFlower(3,1,3));
        sqLiteDatabase.insert(DRY_FLOWER_TABLE_NAME   ,   null   ,   getDryFlower(4,19750,2));
    }

    private ContentValues getDryFlower(int id, int score, int scoreType){

        ContentValues values = new ContentValues();
        values.put(dryFlowerNo,id);
        values.put(dryFlowerScore, score);
        values.put(dryFlowerScoreType, scoreType);

        return values;
    }

    public DryFlower getDryFlowerData(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

        selectQuery = "SELECT * FROM " + DRY_FLOWER_TABLE_NAME + " WHERE " + dryFlowerNo + "=" + id;

        cursor = db.rawQuery(selectQuery, null);

        DryFlower dryFlower= new DryFlower();

        if (cursor.moveToFirst()) {
            do {
                dryFlower.setDryFlowerNo(cursor.getInt(0));
                dryFlower.setScore(cursor.getInt(2),cursor.getInt(1));
            } while (cursor.moveToNext());
        }

        return dryFlower;
    }

    private void getGoalInfoTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE " + GOAL_INFO_TABLE_NAME + "("
                + goalNo + " INTEGER NOT NULL PRIMARY KEY,"
                + goalName + " TEXT NOT NULL,"
                + goalType + " INTEGER NOT NULL,"
                + goalMaxLevel + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(0,"꽃과 친해지기",0,11));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(1,"꽃 향기가나",1,5));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(2,"홀씨 날리기 작전",2,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(3,"자주색 치맛자락",3,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(4,"상처받아도 난 몰라요",4,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(5,"진달래 아니에요",5,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(6,"별이 피는 바람에",6,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(7,"애청자",7,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(8,"액정의 안부를 묻다",8,12));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(9,"불타는 액정",9,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(10,"함께 산책가요",10,1));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(11,"부자 되세요",11,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(12,"아낌 없이 주는",12,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(13,"비나이다. 비나이다. 씨 뿌리기!",13,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(14,"영원히 보고싶어",14,10));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(15,"Touch My Body",15,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(16,"어멋! 지금 어딜 만져요!",16,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(17,"꼿꼿하게 핀 허리에",17,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(18,"새침한 똑 단발",18,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(19,"진정한 소리꾼",19,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(20,"가수해도 되겠어요",20,20));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(21,"국어능력인증시험",21,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(22,"이글아이",23,8));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(23,"비구름을 그릴게요",25,5));
        sqLiteDatabase.insert(GOAL_INFO_TABLE_NAME,null,getGoalInfo(24,"비가 오려나.. 무릎이 쑤셔",26,5));
    }

    private ContentValues getGoalInfo(int id, String name, int type, int maxLevel){

        ContentValues values = new ContentValues();
        values.put(goalNo,id);
        values.put(goalName,name);
        values.put(goalType,type);
        values.put(goalMaxLevel,maxLevel);

        return values;
    }

    public ArrayList<GoalInfo> getAllGoalInfo(){
        ArrayList<GoalInfo> goalInfos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery;
        Cursor cursor;

        selectQuery = "SELECT * FROM " + GOAL_INFO_TABLE_NAME;

        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                GoalInfo goal = new GoalInfo();
                goal.setId(cursor.getInt(0));
                goal.setGoalName(cursor.getString(1));
                goal.setGoalType(cursor.getInt(2));
                goal.setGoalMaxLevel(cursor.getInt(3));

                goalInfos.add(goal);

            } while (cursor.moveToNext());
        }

        return goalInfos;
    }

    private void getGoalDataTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE " + GOAL_DATA_TABLE_NAME + "("
                + goalPrimaryKey + " INTEGER NOT NULL PRIMARY KEY,"
                + goalNo + " INTEGER NOT NULL,"
                + goalLevel + " INTEGER NOT NULL,"
                + goalCondition + " INTEGER NOT NULL,"
                + goalConditionType + " INTEGER NOT NULL,"
                + goalRewardType + " INTEGER NOT NULL,"
                + goalReward + " INTEGER NOT NULL,"
                + goalRewardCostType + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(0,1,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(1,1,2,2,0,1,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(2,1,3,5,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(3,1,4,10,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(4,1,5,15,0,1,60,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(5,1,6,20,0,1,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(6,1,7,25,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(7,1,8,35,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(8,1,9,50,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(9,1,10,70,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(10,1,11,100,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(11,2,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(12,2,2,2,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(13,2,3,3,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(14,2,4,4,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(15,2,5,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(16,3,1,50,0,2,150,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(17,3,2,100,0,2,750,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(18,3,3,150,0,2,3,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(19,3,4,200,0,2,25,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(20,3,5,250,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(21,3,6,300,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(22,3,7,350,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(23,3,8,400,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(32,4,1,50,0,2,400,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(33,4,2,100,0,2,1,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(34,4,3,150,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(35,4,4,200,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(36,4,5,250,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(37,4,6,300,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(38,4,7,350,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(39,4,8,400,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(48,5,1,50,0,2,5,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(49,5,2,100,0,2,500,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(50,5,3,150,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(51,5,4,200,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(52,5,5,250,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(53,5,6,300,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(54,5,7,350,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(55,5,8,400,0,4,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(40,6,1,50,0,2,100,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(41,6,2,100,0,5,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(42,6,3,150,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(43,6,4,200,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(44,6,5,250,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(45,6,6,300,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(46,6,7,350,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(47,6,8,400,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(24,7,1,50,0,2,50,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(25,7,2,100,0,2,200,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(26,7,3,150,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(27,7,4,200,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(28,7,5,250,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(29,7,6,300,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(30,7,7,350,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(31,7,8,400,0,3,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(56,8,1,1,0,5,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(57,8,2,2,0,5,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(58,8,3,3,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(59,8,4,5,0,1,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(60,8,5,8,0,1,20,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(61,8,6,13,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(62,8,7,21,0,2,10,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(63,8,8,34,0,3,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(64,8,9,55,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(65,8,10,89,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(66,8,11,144,0,1,60,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(67,8,12,233,0,1,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(68,8,13,377,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(69,8,14,610,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(70,8,15,987,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(71,8,16,1597,0,2,30,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(72,8,17,2584,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(73,8,18,4181,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(74,8,19,6765,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(75,8,20,10946,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(76,9,1,100,0,2,500,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(77,9,2,200,0,2,77,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(78,9,3,300,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(79,9,4,400,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(80,9,5,500,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(81,9,6,600,0,2,30,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(82,9,7,700,0,2,1,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(83,9,8,800,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(84,9,9,900,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(85,9,10,1000,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(86,9,11,1100,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(87,9,12,1200,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(88,10,1,50,0,2,500,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(89,10,2,100,0,2,5,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(90,10,3,250,0,2,20,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(91,10,4,700,0,2,60,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(92,10,5,2000,0,2,200,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(93,10,6,7500,0,2,1,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(94,10,7,30000,0,2,25,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(95,10,8,100000,0,2,600,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(96,10,9,185000,0,2,5,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(97,10,10,300000,0,2,40,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(98,10,11,450000,0,4,2,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(99,10,12,620000,0,2,2,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(100,10,13,830000,0,2,12,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(101,10,14,1070000,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(102,10,15,1300000,0,2,300,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(103,10,16,1590000,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(104,10,17,1900000,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(105,10,18,2340000,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(106,10,19,3000000,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(107,10,20,5000000,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(108,11,1,1,0,2,1,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(109,12,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(110,12,2,2,0,2,5,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(111,12,3,5,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(112,12,4,11,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(113,12,5,21,0,2,5,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(114,12,6,36,0,5,20,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(115,12,7,57,0,2,100,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(116,12,8,85,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(117,12,9,121,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(118,12,10,166,0,3,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(119,12,11,221,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(120,12,12,287,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(121,12,13,365,0,2,50,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(122,12,14,456,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(123,12,15,561,0,3,12,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(124,12,16,681,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(125,12,17,817,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(126,12,18,970,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(127,12,19,1141,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(128,12,20,1330,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(129,13,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(130,13,2,5,0,2,5,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(131,13,3,14,0,2,50,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(132,13,4,30,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(133,13,5,55,0,2,3,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(134,13,6,91,0,5,20,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(135,13,7,140,0,2,100,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(136,13,8,204,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(137,13,9,285,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(138,13,10,385,0,3,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(139,13,11,506,0,5,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(140,13,12,650,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(141,13,13,819,0,2,40,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(142,13,14,983,0,3,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(143,13,15,1208,0,1,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(144,13,16,1464,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(145,13,17,1753,0,4,2,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(146,13,18,2077,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(147,13,19,2438,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(148,13,20,2838,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(149,14,1,1,1,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(150,14,2,5,1,2,500,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(151,14,3,25,1,2,3,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(152,14,4,100,1,2,10,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(153,14,5,500,1,2,50,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(154,14,6,3700,1,2,400,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(155,14,7,10500,1,2,1,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(156,14,8,32,2,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(157,14,9,79,2,1,75,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(158,14,10,185,2,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(159,14,11,391,2,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(160,14,12,900,2,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(161,14,13,2750,2,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(162,14,14,15,3,2,1,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(163,14,15,91500,2,2,9,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(164,14,16,777777,2,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(165,14,17,1,4,2,100,4));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(166,14,18,5150,3,4,2,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(167,14,19,24,4,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(168,14,20,170,4,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(169,15,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(170,15,2,2,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(171,15,3,3,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(172,15,4,4,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(173,15,5,5,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(174,15,6,7,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(175,15,7,10,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(176,15,8,15,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(177,15,9,20,0,1,250,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(178,15,10,30,0,1,300,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(179,16,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(180,16,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(181,16,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(182,16,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(183,16,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(184,16,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(185,16,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(186,16,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(187,17,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(188,17,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(189,17,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(190,17,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(191,17,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(192,17,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(193,17,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(194,17,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(195,17,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(196,17,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(197,17,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(198,17,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(199,17,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(200,17,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(201,17,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(202,17,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(203,17,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(204,17,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(205,17,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(206,17,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(235,18,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(236,18,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(237,18,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(238,18,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(239,18,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(240,18,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(241,18,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(242,18,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(243,19,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(244,19,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(245,19,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(246,19,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(247,19,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(248,19,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(249,19,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(250,19,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(251,19,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(252,19,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(253,19,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(254,19,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(255,19,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(256,19,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(257,19,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(258,19,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(259,19,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(260,19,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(261,19,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(262,19,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(291,20,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(292,20,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(293,20,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(294,20,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(295,20,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(296,20,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(297,20,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(298,20,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(299,21,1,1,0,2,10,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(300,21,2,2,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(301,21,3,4,0,5,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(302,21,4,8,0,3,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(303,21,5,15,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(304,21,6,25,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(305,21,7,40,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(306,21,8,60,0,2,50,2));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(307,21,9,88,0,5,80,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(308,21,10,120,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(309,21,11,155,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(310,21,12,191,0,2,25,3));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(311,21,13,235,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(312,21,14,288,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(313,21,15,349,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(314,21,16,415,0,5,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(315,21,17,488,0,4,3,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(316,21,18,567,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(317,21,19,658,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(318,21,20,760,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(263,22,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(264,22,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(265,22,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(266,22,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(267,22,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(268,22,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(269,22,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(270,22,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(207,23,1,1,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(208,23,2,3,0,2,100,1));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(209,23,3,5,0,1,70,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(210,23,4,7,0,1,100,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(211,23,5,10,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(212,23,6,15,0,1,120,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(213,23,7,20,0,1,150,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(214,23,8,30,0,1,200,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(319,24,1,1,0,1,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(320,24,2,2,0,1,20,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(321,24,3,3,0,1,30,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(322,24,4,4,0,1,40,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(323,24,5,5,0,1,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(324,25,1,1,0,5,10,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(325,25,2,5,0,5,50,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(326,25,3,15,0,4,1,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(327,25,4,30,0,3,5,0));
        sqLiteDatabase.insert(GOAL_DATA_TABLE_NAME,null, getGoalData(328,25,5,50,0,4,3,0));

    }

    private ContentValues getGoalData(int key, int id, int level, int condition, int coditionPower,
                                      int rewardType, int reward, int rewardPower){
        ContentValues values = new ContentValues();
        values.put(goalPrimaryKey,key);
        values.put(goalNo,id);
        values.put(goalLevel,level);
        values.put(goalCondition,condition);
        values.put(goalConditionType,coditionPower);
        values.put(goalRewardType,rewardType);
        values.put(goalReward,reward);
        values.put(goalRewardCostType,rewardPower);
        return values;
    }

    public GoalData getGoalDataByID(int id, int level) {

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery;
        Cursor cursor;

        GoalData goalData = new GoalData();

        selectQuery = "SELECT * FROM " + GOAL_DATA_TABLE_NAME + " WHERE " + goalNo + "=" + (id+1) + " and " + goalLevel + "=" + level;
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                goalData.setGoalNo(cursor.getInt(1));
                goalData.setGoalLevel(cursor.getInt(2));
                goalData.setGoalCondition(cursor.getInt(3));
                goalData.setGoalConditionType(cursor.getInt(4));
                goalData.setGoalRewardType(cursor.getInt(5));
                goalData.setGoalReward(cursor.getInt(6));
                goalData.setGoalRewardCostType(cursor.getInt(7));
            } while (cursor.moveToNext());
        }

        return goalData;
    }
    private void getStoreTable(SQLiteDatabase sqLiteDatabase){


        String CREATE_TABLE = "CREATE TABLE " + STORE_TABLE_NAME + "("
                + storeNo + " INTEGER NOT NULL PRIMARY KEY,"
                + storeName + " TEXT NOT NULL,"
                + storeBuyType + " INTEGER NOT NULL,"
                + storeCost + " INTEGER NOT NULL,"
                + storeEffectType + " INTEGER NOT NULL"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(STORE_TABLE_NAME,null,getStoreProductValues(0,"자동 클릭",0,300,1));
        sqLiteDatabase.insert(STORE_TABLE_NAME,null,getStoreProductValues(1,"체력 포션",0,15,2));
        sqLiteDatabase.insert(STORE_TABLE_NAME,null,getStoreProductValues(2,"부활 포션",0,100,3));
        sqLiteDatabase.insert(STORE_TABLE_NAME,null,getStoreProductValues(3,"물",0,3,4));
        sqLiteDatabase.insert(STORE_TABLE_NAME,null,getStoreProductValues(4,"물",1,10000,4));

    }

    private ContentValues getStoreProductValues(int id, String name, int buyType, int cost, int effect){

        ContentValues values = new ContentValues();
        values.put(storeNo,id);
        values.put(storeName,name);
        values.put(storeBuyType,buyType);
        values.put(storeCost,cost);
        values.put(storeEffectType,effect);
        return values;
    }

    public ArrayList<StoreProduct> getAllStoreProduct(){
        ArrayList<StoreProduct> storeProducts = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + STORE_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                StoreProduct storeProduct = new StoreProduct();
                storeProduct.setItemId(cursor.getInt(0));
                storeProduct.setItemName(cursor.getString(1));
                storeProduct.setBuyType(cursor.getInt(2));
                storeProduct.setCost(cursor.getInt(3));
                storeProduct.setItemEffectType(cursor.getInt(4));

                storeProducts.add(storeProduct);
            } while (cursor.moveToNext());
        }

        return storeProducts;
    }

    public ArrayList<Water> getWaterInform(){
        ArrayList<Water> waters = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+WATER_TABLE_NAME,null);
        if (cursor.moveToFirst()) {
            do {
                Water water = new Water();
                water.setFlowerId(cursor.getInt(0));
                water.setWaterPeriod(cursor.getInt(1));
                water.setWaterPenaltyTime(cursor.getInt(2));
                water.setWaterPenalty(cursor.getInt(3));
                water.setWaterNeedWaterNum(cursor.getInt(4));

                waters.add(water);

            } while (cursor.moveToNext());
        }

        return waters;
    }

    private void WaterTable(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE TABLE " + WATER_TABLE_NAME + "("
                + flowerId + " INTEGER NOT NULL,"
                + waterPeriod + " INTEGER NOT NULL,"
                + waterPenaltyTime + " INTEGER NOT NULL,"
                + waterPenalty + " INTEGER NOT NULL,"
                + waterNeedWaterNum + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(0,300,120,1,1));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(1,480,192,1,2));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(2,900,360,2,4));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(3,1200,480,3,6));
        sqLiteDatabase.insert(WATER_TABLE_NAME,null,getWaterValues(4,1800,720,3,10));
    }

    private ContentValues getWaterValues(int id, int period, int penaltyTime, int penalty, int neddWaterNum){
        ContentValues values = new ContentValues();
        values.put(flowerId,id);
        values.put(waterPeriod,period);
        values.put(waterPenaltyTime,penaltyTime);
        values.put(waterPenalty,penalty);
        values.put(waterNeedWaterNum,neddWaterNum);
        return values;
    }

}

