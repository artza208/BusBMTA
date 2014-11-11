package com.busbmta.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.AutoCompleteTextView;
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
    EditText editText1,editText2;
    RadioButton rbSearch,rbLook,rbSE;
    TextView txtMain;

    private static final String[] BusWord = new String[] {
            "กรมการรักษาดินแดน", "กรมที่ดิน", "Italy", "Germany", "Spain", "Thailand", "Taiwan"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        rbSearch = (RadioButton)findViewById(R.id.radioSearch);
        rbLook = (RadioButton)findViewById(R.id.radioLook);
        rbSE = (RadioButton)findViewById(R.id.radioSE);
        listView = (ListView)findViewById(R.id.listView1);
        txtMain = (TextView)findViewById(R.id.textMain);

final AutoCompleteTextView autoCompleteTextView1 = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, BusWord);

        autoCompleteTextView1.setAdapter(adapter);

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

                editText2.setVisibility(View.GONE);
                editText1.setText("");
                editText2.setText("a");
                editText1.setHint(R.string.search_word);
                txtMain.setText("ค้นหาจากสายรถเมล์");
                ArrayAdapter<String> adapterDir = new CustomListViewAdapter(MainActivity.this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
                listView.setAdapter(adapterDir);
            }
        });

        //radio box Look Around check
        rbLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMain.setText("ค้นหาสายรถเมล์จากตำแหน่งปัจจุบัน");
                Intent intentL = new Intent(getApplicationContext(),Location_Manager.class);
                startActivityForResult(intentL, 999);
            }

        });

        // radio box Start-End check
        rbSE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMain.setText("ค้นหาสายรถเมล์จากต้นทาง - ปลายทาง");
                editText2.setText("");
                editText2.setVisibility(View.VISIBLE);
                editText1.setHint(R.string.search_start);
            }
        });
        //Show all listview
        ArrayAdapter<String> adapterDir = new CustomListViewAdapter(this, android.R.layout.simple_list_item_1, BusNoArr, BusTimeArr, BusWayArr);
        listView.setAdapter(adapterDir);

        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText2.setText("a");
        editText2.setVisibility(View.GONE);
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
                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        ArrayList<String> BusNoSrc = new ArrayList<String>();
                        ArrayList<String> BusTimeSrc = new ArrayList<String>();
                        ArrayList<String> BusWaySrc = new ArrayList<String>();

                        mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                                + ", " + MyDbHelper.COL_BUSTIME
                                + ", " + MyDbHelper.COL_BUSWAY
                                + " FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSSTART + " LIKE '%" + editText1.getText().toString()
                                + "%' AND " + MyDbHelper.COL_BUSSTART + " LIKE '%" + editText2.getText().toString() + "%' ORDER BY " + MyDbHelper.COL_BUSNO + " ASC", null);

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
                intent.putExtra("WordSearch",editText1.getText().toString());
                intent.putExtra("WordSearch2",editText2.getText().toString());
                startActivity(intent);
            }
        });
    }

    protected void onActivityResult ( int requestCode, int resultCode, Intent data )
    {
        super.onActivityResult ( requestCode, resultCode, data );
        final TextView txtAdd = (TextView) this.findViewById ( R.id.textAdd );
        final ArrayList<String> BusNoSrcs = new ArrayList<String>();
        final ArrayList<String> BusTimeSrcs = new ArrayList<String>();
        final ArrayList<String> BusWaySrcs = new ArrayList<String>();
        if ( resultCode == RESULT_OK && requestCode == 999 )
        {
            final String addressGPS1 = data.getStringExtra("addressGPS1");
            String addressGPS2 = data.getStringExtra("addressGPS2");
            String addressGPS3 = data.getStringExtra("addressGPS3");
            final String[] option={addressGPS1,addressGPS2,addressGPS3};

            //create dialog
            ArrayAdapter<String> adpAddress = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,option);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Select Address");
            builder.setAdapter(adpAddress,new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    txtAdd.setText(option[which]);
                    mCursor = mDb.rawQuery("SELECT " + MyDbHelper.COL_BUSNO
                            + ", " + MyDbHelper.COL_BUSTIME
                            + ", " + MyDbHelper.COL_BUSWAY
                            + " FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSSTART + " LIKE '%" + option[which].toString()
                            + "%' OR " + MyDbHelper.COL_BUSEND + " LIKE '%" + option[which].toString() + "%' ORDER BY " + MyDbHelper.COL_BUSNO + " ASC", null);

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


            });
            AlertDialog alert = builder.create();
            alert.show();

        }

    }
    public void onPause() {
        super.onPause();
    }
}



