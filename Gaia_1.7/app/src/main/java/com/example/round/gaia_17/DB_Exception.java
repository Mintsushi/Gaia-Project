package com.example.round.gaia_17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by 리제 on 2017-09-06.
 */

public class DB_Exception extends SQLiteOpenHelper {
    DB_Exception(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    //임시값
    String imagePath = "image";
    String skillnaeme = "스킬명";

    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME="Gaia";

    final private static String ACTIVE_SKILL_TABLE_NAME = "ACTIVE_SKILL_TABLE";
    final private static String activeSkillId = "KEY_ID";
    final private static String activeSkillName = "KEY_NAME";
    final private static String activeSkillLv = "KEY_LV";
    final private static String activeSkillImage = "KEY_IMAGE";
    final private static String activeSkillSkillImage = "KEY_SKILL_IMAGE";
    final private static String activeSkillCostType= "KEY_COST_TYPE";
    final private static String activeSkillUseCost = "KEY_USE_COST";
    final private static String activeSkillEffect = "KEY_EFFECT";

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        ActiveSkillTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ACTIVE_SKILL_TABLE_NAME);
        onCreate(db);
    }

    private void ActiveSkillTable(SQLiteDatabase sqLiteDatabase){

        String CREATE_TABLE = "CREATE TABLE " + ACTIVE_SKILL_TABLE_NAME + "("
                + activeSkillId + " INTEGER NOT NULL PRIMARY KEY,"
                + activeSkillLv + " INTEGER NOT NULL,"
                + activeSkillCostType + " INTEGER NOT NULL,"
                + activeSkillUseCost + " INTEGER NOT NULL,"
                + activeSkillEffect + " INTEGER NOT NULL" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //+activeSkillName +" TEXT NOT NULL"
        //+activeSkillImage +" TEXT NOT NULL"
        //+activeSkillSkillImage +" TEXT NOT NULL"

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	0	,	2	,	100	,	12));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	1	,	2	,	100	,	12));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	2	,	2	,	500	,	14));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	3	,	2	,	1000	,	16));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	4	,	2	,	5000	,	18));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	5	,	2	,	20000	,	20));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	6	,	2	,	100000	,	22));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	7	,	2	,	500000	,	24));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	8	,	2	,	1000000	,	26));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	9	,	2	,	3000000	,	28));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	10	,	2	,	5000000	,	30));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	11	,	1	,	50	,	33));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	12	,	1	,	50	,	36));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	13	,	1	,	50	,	39));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	14	,	1	,	50	,	42));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	15	,	1	,	50	,	45));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	16	,	1	,	60	,	48));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	17	,	1	,	70	,	51));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	18	,	1	,	80	,	54));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	19	,	1	,	90	,	57));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	20	,	1	,	100	,	60));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	21	,	1	,	110	,	63));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	22	,	1	,	120	,	66));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	23	,	1	,	130	,	69));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	24	,	1	,	140	,	72));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	25	,	1	,	150	,	75));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	26	,	1	,	160	,	78));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	27	,	1	,	170	,	81));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	28	,	1	,	180	,	84));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	29	,	1	,	190	,	87));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(1	,	30	,	1	,	200	,	90));

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	0	,	2	,	100	,	50));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	1	,	2	,	100	,	50));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	2	,	2	,	500	,	100));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	3	,	2	,	1000	,	150));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	4	,	2	,	5000	,	200));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	5	,	2	,	20000	,	250));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	6	,	2	,	100000	,	300));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	7	,	2	,	500000	,	350));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	8	,	2	,	1000000	,	400));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	9	,	2	,	3000000	,	450));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	10	,	2	,	5000000	,	500));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	11	,	1	,	50	,	600));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	12	,	1	,	50	,	700));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	13	,	1	,	50	,	800));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	14	,	1	,	50	,	900));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	15	,	1	,	50	,	1000));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	16	,	1	,	60	,	1100));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	17	,	1	,	70	,	1200));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	18	,	1	,	80	,	1300));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	19	,	1	,	90	,	1400));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	20	,	1	,	100	,	1500));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	21	,	1	,	110	,	1600));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	22	,	1	,	120	,	1700));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	23	,	1	,	130	,	1800));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	24	,	1	,	140	,	1900));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	25	,	1	,	150	,	2000));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	26	,	1	,	160	,	2100));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	27	,	1	,	170	,	2200));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	28	,	1	,	180	,	2300));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	29	,	1	,	190	,	2400));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(2	,	30	,	1	,	200	,	2500));

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	0	,	2	,	100	,	12));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	1	,	2	,	100	,	12));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	2	,	2	,	500	,	14));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	3	,	2	,	1000	,	16));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	4	,	2	,	5000	,	18));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	5	,	2	,	20000	,	20));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	6	,	2	,	100000	,	22));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	7	,	2	,	500000	,	24));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	8	,	2	,	1000000	,	26));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	9	,	2	,	3000000	,	28));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	10	,	2	,	5000000	,	30));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	11	,	1	,	50	,	33));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	12	,	1	,	50	,	36));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	13	,	1	,	50	,	39));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	14	,	1	,	50	,	42));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	15	,	1	,	50	,	45));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	16	,	1	,	60	,	48));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	17	,	1	,	70	,	51));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	18	,	1	,	80	,	54));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	19	,	1	,	90	,	57));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	20	,	1	,	100	,	60));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	21	,	1	,	110	,	63));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	22	,	1	,	120	,	66));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	23	,	1	,	130	,	69));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	24	,	1	,	140	,	72));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	25	,	1	,	150	,	75));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	26	,	1	,	160	,	78));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	27	,	1	,	170	,	81));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	28	,	1	,	180	,	84));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	29	,	1	,	190	,	87));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(3	,	30	,	1	,	200	,	90));

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,   0	,	1	,	50	,	2));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	1	,	1	,	50	,	2));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	2	,	1	,	60	,	4));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	3	,	1	,	70	,	6));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	4	,	1	,	80	,	8));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	5	,	1	,	90	,	10));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	6	,	1	,	100	,	12));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	7	,	1	,	110	,	14));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	8	,	1	,	120	,	16));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	9	,	1	,	130	,	18));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	10	,	1	,	140	,	20));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	11	,	1	,	150	,	22));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	12	,	1	,	160	,	24));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	13	,	1	,	170	,	26));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	14	,	1	,	180	,	28));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	15	,	1	,	190	,	30));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	16	,	1	,	200	,	32));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	17	,	1	,	200	,	34));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	18	,	1	,	200	,	36));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	19	,	1	,	200	,	38));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	20	,	1	,	200	,	40));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	21	,	1	,	200	,	42));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	22	,	1	,	200	,	44));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	23	,	1	,	200	,	46));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	24	,	1	,	200	,	48));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	25	,	1	,	200	,	50));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	26	,	1	,	200	,	52));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	27	,	1	,	200	,	54));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	28	,	1	,	200	,	56));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	29	,	1	,	200	,	58));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(4	,	30	,	1	,	200	,	60));

        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	0	,	1	,	50	,	10));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	1	,	1	,	50	,	10));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	2	,	1	,	60	,	20));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	3	,	1	,	70	,	30));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	4	,	1	,	80	,	40));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	5	,	1	,	90	,	50));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	6	,	1	,	100	,	60));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	7	,	1	,	110	,	70));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	8	,	1	,	120	,	80));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	9	,	1	,	130	,	90));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	10	,	1	,	140	,	100));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	11	,	1	,	150	,	120));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	12	,	1	,	160	,	140));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	13	,	1	,	170	,	160));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	14	,	1	,	180	,	180));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	15	,	1	,	190	,	200));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	16	,	1	,	200	,	220));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	17	,	1	,	200	,	240));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	18	,	1	,	200	,	260));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	19	,	1	,	200	,	280));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	20	,	1	,	200	,	300));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	21	,	1	,	200	,	320));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	22	,	1	,	200	,	340));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	23	,	1	,	200	,	360));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	24	,	1	,	200	,	380));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	25	,	1	,	200	,	400));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	26	,	1	,	200	,	420));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	27	,	1	,	200	,	440));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	28	,	1	,	200	,	460));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	29	,	1	,	200	,	480));
        sqLiteDatabase.insert(ACTIVE_SKILL_TABLE_NAME	,	null	,	getActiveSkillValues(5	,	30	,	1	,	200	,	500));




    }

    //String name, String image, String skillImage, 제외
    private ContentValues getActiveSkillValues(int id,  int lv, int costType, int useCost, int effect){

        ContentValues values = new ContentValues();
        //values.put(activeSkillName,name);
        //values.put(activeSkillImage,image);
        //values.put(activeSkillSkillImage,skillImage);
        values.put(activeSkillId,id);
        values.put(activeSkillLv,lv);
        values.put(activeSkillCostType,costType);
        values.put(activeSkillUseCost,useCost);
        values.put(activeSkillEffect,effect);
        return values;
    }

    public menuActiveSkillActivity.ActiveSkillInform getActiveSkillInform(int id, int lv){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(ACTIVE_SKILL_TABLE_NAME,new String[] {activeSkillId,activeSkillLv,activeSkillCostType,activeSkillUseCost,activeSkillEffect},activeSkillId+"=?",
                new String[] {String.valueOf(id)},null,null,null,null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Log.i(" SQL ", ""+cursor.getInt(0)+" , "+cursor.getInt(1)+ " , "+cursor.getInt(2)+" , "+cursor.getInt(3) + " , "+cursor.getInt(4));

        return new menuActiveSkillActivity.ActiveSkillInform(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getFloat(3), cursor.getInt(4),imagePath, skillnaeme);
    }
}
