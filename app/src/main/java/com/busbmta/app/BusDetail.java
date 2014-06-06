package com.busbmta.app;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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

       Button btnMap = (Button)findViewById(R.id.btnMap);
       btnMap.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(mCursor.getString(mCursor.getColumnIndex(MyDbHelper.COL_BUSMAP))));
               startActivity(Intent.createChooser(intent, "Open with"));
           }
       });

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
