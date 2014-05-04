package com.busbmta.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by MeePoohz on 27/4/2557.
 */
class MyDbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "BusBMTA";
    private static final int DB_VERSION = 6;
    public static final String TABLE_NAME = "BusData";
    public static final String COL_BUSNO = "BusNo";
    public static final String COL_BUSZONE = "BusZone";
    public static final String COL_BUSWAY = "BusWay";
    public static final String COL_BUSTYPE = "BusType";
    public static final String COL_BUSTIME = "BusTime";

    public MyDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT," + COL_BUSNO + " TEXT, " + COL_BUSZONE + " TEXT, " + COL_BUSWAY + " TEXT, " + COL_BUSTYPE + " TEXT, " + COL_BUSTIME + " TEXT);");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                   + " VALUES ('29','1','มหาวิทยาลัยธรรมศาสตร์รังสิต - หัวลำโพง','รถธรรมดา (ครีม-แดง)','บริการตลอดคืน');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('11','2','ประเวศร์ - มาบุญครอง','รถธรรมดา (ครีม-แดง)','04:30 - 22:00');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('34','1','หมู่บ้านรัตนโกสินทร์ 200 ปี - หัวลำโพง','รถธรรมดา (ครีม-แดง)','บริการตลอดคืน');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('59','1','รังสิต - สนามหลวง','รถธรรมดา (ครีม-แดง)','บริการตลอดคืน');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('95','1','อู่บางเขน - มหาวิทยาลัยรามคำแหง','รถธรรมดา (ครีม-แดง)','04:30 - 22:00');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('107','1','อู่บางเขน - คลองเตย','รถธรรมดา (ครีม-แดง)','04:30 - 21:45');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('129','1','อู่บางเขน - สำโรง','รถธรรมดา (ครีม-แดง)','04:10 - 22:00');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('185','1','รังสิต - คลองเตย','รถธรรมดา (ครีม-แดง)','04:15 - 21:30');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('503','1','หมู่บ้านรัตนโกสินทร์ 200 ปี - สนามหลวง','รถปรับอากาศ (ยูโรทู)','04:00 - 21:00');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('510','1','ตลาดไท - อนุสาวรีย์ชัยสมรภูมิ','รถปรับอากาศ (ยูโรทู)','บริการตลอดคืน');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('520','1','ตลาดไท - แฮปปี้แลนด์','รถปรับอากาศ (ยูโรทู)','04:00 - 21:00');");
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COL_BUSNO + "," + COL_BUSZONE + "," + COL_BUSWAY + "," + COL_BUSTYPE + "," + COL_BUSTIME + ")"
                + " VALUES ('522','1','รังสิต - อนุสาวรีย์ชัยฯ','รถปรับอากาศ (ยูโรทู)','04:00 - 21:00');");
    }
    public void onUpgrade(SQLiteDatabase db,int OldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
public class Database {
}
