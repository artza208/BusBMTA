package com.busbmta.app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

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
    RadioButton rbSearch,rbLook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        rbSearch = (RadioButton)findViewById(R.id.radioSearch);
        rbLook = (RadioButton)findViewById(R.id.radioLook);
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

        //radio box Search check
        rbSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText1.setText("");
                ArrayAdapter<String> adapterDir = new CustomListViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
                listView.setAdapter(adapterDir);
            }
        });

        //radio box Look Around check
        rbLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL = new Intent(getApplicationContext(),Location_Manager.class);
                startActivityForResult(intentL, 999);
            }

        });

        //Show all listview
        ArrayAdapter<String> adapterDir = new CustomListViewAdapter(this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
        listView.setAdapter(adapterDir);

        editText1 = (EditText)findViewById(R.id.editText1);

        //Event text change
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


                    mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                            + ", " + MyDbHelper.COL_BUSTIME
                            + ", " + MyDbHelper.COL_BUSWAY
                            + " FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSSTART + " LIKE '%" + editText1.getText().toString()
                            + "%' OR " + MyDbHelper.COL_BUSEND + " LIKE '%" + editText1.getText().toString() + "%' ORDER BY " + MyDbHelper.COL_BUSNO + " ASC", null);

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
        });

        //Click listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                mCursor.moveToPosition(arg2);

                Intent intent = new Intent(getApplicationContext(),BusDetail.class);
                intent.putExtra("BusId",mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSNO)));
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult ( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult ( requestCode, resultCode, data );
        ArrayList<String> BusNoSrcs = new ArrayList<String>();
        ArrayList<String> BusTimeSrcs = new ArrayList<String>();
        ArrayList<String> BusWaySrcs = new ArrayList<String>();
        if ( resultCode == RESULT_OK && requestCode == 999 )
        {
            String addressGPS = data.getStringExtra("addressGPS");
            TextView txtAdd = (TextView) this.findViewById ( R.id.textAdd );
            txtAdd.setText (addressGPS);
            mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                    + ", " + MyDbHelper.COL_BUSTIME
                    + ", " + MyDbHelper.COL_BUSWAY
                    + " FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSSTART + " LIKE '%" + addressGPS.toString()
                    + "%' OR " + MyDbHelper.COL_BUSEND + " LIKE '%" + addressGPS.toString() + "%' ORDER BY " + MyDbHelper.COL_BUSNO + " ASC", null);

            mCursor.moveToFirst();

            while (!mCursor.isAfterLast()) {
                BusNoSrcs.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSNO)));
                BusTimeSrcs.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTIME)));
                BusWaySrcs.add(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSWAY)));
                mCursor.moveToNext();
            }
            ArrayAdapter<String> adapterDir = new CustomListViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, BusNoSrcs, BusTimeSrcs, BusWaySrcs);
            listView.setAdapter(adapterDir);
        }

    }
    public void onPause() {
        super.onPause();
    }
}



