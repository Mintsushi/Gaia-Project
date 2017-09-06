package com.example.round.gaia_17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.round.gaia_17.Data.Flower;

import java.util.ArrayList;

/**
 * Created by Round on 2017-09-05.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    //Database
    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "Gaia";

    //Table

    //Flower Table
    private static final String FLOWER_TABLE_NAME = "flower";
    private static final String flowerId ="flowerNo";
    private static final String flowerName ="flowerName";
    private static final String flowerImage = "flowerImage";
    private static final String flowerButtonImage = "flowerButtonImage";
    private static final String flowerCost = "flowerCost";
    private static final String flowerScore = "flowerScore";
    private static final String flowerTab = "flowerTab";
    private static final String flowerLevel = "flowerLevel";
    private static final String waterTime = "waterTime";
    private static final String waterPassiveTime = "waterPassiveTime";
    private static final String passiveRate = "passiveRate";


    public DataBaseHelper(Context context){super(context,DATABASE_NAME,null,DATABASE_VER);}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){

        //flower Table 구축
        flowerTable(sqLiteDatabase);
    }

    private void flowerTable(SQLiteDatabase sqLiteDatabase){
        //Flower Table
        String CREATE_TABLE ="CREATE TABLE "+FLOWER_TABLE_NAME+"("
                +flowerId+" INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,"
                +flowerName+" TEXT NOT NULL,"
                +flowerImage+" TEXT NOT NULL"
                +flowerButtonImage+" TEXT NOT NULL,"
                +flowerCost+" INTEGER NOT NULL,"
                +flowerScore+" INTEGER NOT NULL,"
                +flowerTab+" INTEGER NOT NULL,"
                +flowerLevel+" INTEGER NOT NULL,"
                +waterTime+" INTEGER NOT NULL,"
                +waterPassiveTime+" INTEGER NOT NULL,"
                +passiveRate+" INTEGER NOT NULL"+")";
        sqLiteDatabase.execSQL(CREATE_TABLE);

        //Insert Flower Data
        sqLiteDatabase.insert(FLOWER_TABLE_NAME,null,getFlowerValues(0,"민들레"," "," ",50,1,0,0,300,120,1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME,null,getFlowerValues(1,"나팔꽃"," "," ",2000000,3000,200,200,480,192,1));
        sqLiteDatabase.insert(FLOWER_TABLE_NAME,null,getFlowerValues(2,"장미"," "," ",100000000,100000,400,200,1800,720,3));
    }

    private ContentValues getFlowerValues(int id, String name, String image, String buttonImage,
                                          int cost, int score, int tab, int level, int time, int passiveTime, int passive){

        ContentValues values = new ContentValues();

        values.put(flowerId,id);
        values.put(flowerName,name);
        values.put(flowerImage,image);
        values.put(flowerButtonImage,buttonImage);
        values.put(flowerCost,cost);
        values.put(flowerScore,score);
        values.put(flowerTab,tab);
        values.put(flowerLevel,level);
        values.put(waterTime,time);
        values.put(waterPassiveTime,passiveTime);
        values.put(passiveRate,passive);

        return values;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){}

    //getter
    public ArrayList<Flower> getAllFlowers(){
        ArrayList<Flower> flowers = new ArrayList<>();

        String selectQuery = "SELECT * FROM "+FLOWER_TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Flower flower = new Flower();
                flower.setFlowerNo(cursor.getInt(0));
                flower.setFlowerName(cursor.getString(1));
                flower.setFlowerImage(cursor.getString(2));
                flower.setFlowerButtonImage(cursor.getString(3));
                flower.setFlowerCost(cursor.getInt(4));
                flower.setFlowerScore(cursor.getInt(5));
                flower.setFlowerTab(cursor.getInt(6));
                flower.setFlowerLevel(cursor.getInt(7));

                flowers.add(flower);
            }while (cursor.moveToNext());
        }

        return flowers;
    }
}
