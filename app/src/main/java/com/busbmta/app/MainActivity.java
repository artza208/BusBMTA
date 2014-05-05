package com.busbmta.app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    SQLiteDatabase mDb;
    MyDbHelper mHelper;
    Cursor mCursor;
    ListView listView;
    ArrayList<String> BusNoArr = new ArrayList<String>();
    ArrayList<String> BusTimeArr = new ArrayList<String>();
    ArrayList<String> BusWayArr = new ArrayList<String>();
    EditText editText1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView1);

        mHelper = new MyDbHelper(this);
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                + ", "  + MyDbHelper.COL_BUSTIME
                + ", " + MyDbHelper.COL_BUSWAY
                + " FROM " + MyDbHelper.TABLE_NAME + " ORDER BY " + MyDbHelper.COL_BUSNO + " ASC",null);

        mCursor.moveToFirst();

        while ( !mCursor.isAfterLast() ){
            BusNoArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSNO)));
            BusTimeArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTIME)));
            BusWayArr.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSWAY)));
            mCursor.moveToNext();
        }

        ArrayAdapter<String> adapterDir = new CustomListViewAdapter(this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
        listView.setAdapter(adapterDir);

        editText1 = (EditText)findViewById(R.id.editText1);
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayList<String> BusNoSrc = new ArrayList<String>();
                ArrayList<String> BusTimeSrc = new ArrayList<String>();
                ArrayList<String> BusWaySrc = new ArrayList<String>();
                if (editText1.getText().toString().equals("")) {
                    ArrayAdapter<String> adapterDir = new CustomListViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
                    listView.setAdapter(adapterDir);
                }
                else {

                    mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                            + ", " + MyDbHelper.COL_BUSTIME
                            + ", " + MyDbHelper.COL_BUSWAY
                            + " FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSWAY + " LIKE '%" + editText1.getText().toString() + "%' ORDER BY " + MyDbHelper.COL_BUSNO + " ASC", null);

                    mCursor.moveToFirst();

                    while (!mCursor.isAfterLast()) {
                        BusNoSrc.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSNO)));
                        BusTimeSrc.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTIME)));
                        BusWaySrc.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSWAY)));
                        mCursor.moveToNext();
                    }
                    ArrayAdapter<String> adapterDir = new CustomListViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, BusNoSrc, BusTimeSrc, BusWaySrc);
                    listView.setAdapter(adapterDir);
                }
            }
        });
    }

    public void onPause() {
        super.onPause();
        mHelper.close();
        mDb.close();
    }
}



