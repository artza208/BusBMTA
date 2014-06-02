package com.busbmta.app;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by MeePoohz on 11/5/2557.
 */
public class BusDetail extends Activity {
    SQLiteDatabase mDb;
    MyDbHelper mHelper;
    Cursor mCursor;
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.busdetail);

        Bundle bundle = getIntent().getExtras();
        String BusID = bundle.getString("BusId");

        TextView txtBusN = (TextView)findViewById(R.id.txtBusN);
        TextView txtBusZ = (TextView)findViewById(R.id.txtBusZ);
        TextView txtBusW = (TextView)findViewById(R.id.txtBusW);
        TextView txtBusTy = (TextView)findViewById(R.id.txtBusTy);
        TextView txtBusTi = (TextView)findViewById(R.id.txtBusTi);
        TextView txtBusS = (TextView)findViewById(R.id.txtBusS);
        TextView txtBusE = (TextView)findViewById(R.id.txtBusE);

        mHelper = new MyDbHelper(this);
        mDb = mHelper.getWritableDatabase();
        mCursor = mDb.rawQuery("SELECT * FROM " + MyDbHelper.TABLE_NAME + " WHERE " + MyDbHelper.COL_BUSNO + " = " + BusID.toString() + " ORDER BY " + MyDbHelper.COL_BUSNO + " ASC",null);
        mCursor.moveToFirst();
        txtBusN.setText("- " + BusID + " -");
        txtBusZ.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSZONE)));
        txtBusW.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSWAY)));
        txtBusTy.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTYPE)));
        txtBusTi.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSTIME)));
        txtBusS.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSSTART)));
        txtBusE.setText(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSEND)));
    }

    public void onPause() {
        super.onPause();
    }
}
