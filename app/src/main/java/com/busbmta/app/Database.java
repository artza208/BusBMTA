package com.busbmta.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by MeePoohz on 27/4/2557.
 */
class MyDbHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "BusBMTA";
    private static final int DB_VERSION = 5;
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
    }
    public void onUpgrade(SQLiteDatabase db,int OldVersion,int newVersion){
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME);
        onCreate(db);
    }
}
public class Database {
}
