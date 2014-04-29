package com.busbmta.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    SQLiteDatabase mDb;
    MyDbHelper mHelper;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.listView1);

        mHelper = new MyDbHelper(this);
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                + ", "  + MyDbHelper.COL_BUSTIME
                + ", " + MyDbHelper.COL_BUSWAY
                + " FROM " + MyDbHelper.TABLE_NAME + " ORDER BY " + MyDbHelper.COL_BUSNO + " ASC",null);

        ArrayList<String> BusNoArr = new ArrayList<String>();
        ArrayList<String> BusTimeArr = new ArrayList<String>();
        ArrayList<String> BusWayArr = new ArrayList<String>();
        mCursor.moveToFirst();

        while ( !mCursor.isAfterLast() ){
            BusNoArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSNO)));
            BusTimeArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTIME)));
            BusWayArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSWAY)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new CustomListViewAdapter(this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
        listView.setAdapter(adapterDir);
    }

    public void onPause() {
        super.onPause();
        mHelper.close();
        mDb.close();
    }
}



