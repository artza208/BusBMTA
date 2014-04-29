package com.busbmta.app;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MeePoohz on 27/4/2557.
 */
public class CustomListViewAdapter extends ArrayAdapter<String> {
    ArrayList<String> BusNo;
    ArrayList<String> BusTime;
    ArrayList<String> BusWay;
    LayoutInflater INFLATER;

    public CustomListViewAdapter(Context context,int textViewResourceId,ArrayList<String> BusNoAr,ArrayList<String> BusTimeAr,ArrayList<String> BusWayAr){
        super(context, textViewResourceId, BusNoAr);
        INFLATER = (LayoutInflater)context.getSystemService
                (context.LAYOUT_INFLATER_SERVICE);
        BusNo = BusNoAr;
        BusTime = BusTimeAr;
        BusWay = BusWayAr;
    }

    public View getView(int position,View convertView,ViewGroup parent){
        View row = INFLATER.inflate(R.layout.listview_row, parent, false);
        TextView txtTime = (TextView)row.findViewById(R.id.txtBusTime);
        TextView txtNo = (TextView) row.findViewById(R.id.txtBusNo);
        TextView txtWay = (TextView) row.findViewById(R.id.txtBusWay);
        txtNo.setTextColor(Color.BLACK);
        txtNo.setText(BusNo.get(position));
        txtTime.setTextColor(Color.BLACK);
        txtTime.setText(BusTime.get(position));
        txtWay.setTextColor(Color.BLACK);
        txtWay.setText(BusWay.get(position));

        return row;
    }
}
