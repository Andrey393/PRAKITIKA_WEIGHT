package com.example.contolweight_gritsakovich_493;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PersonListAdapter extends ArrayAdapter<ListMeasurements> {

    private static  final String TAG = "PersonListAdapter";
    private Context mContex;
    int  mResoure;

    public PersonListAdapter(@NonNull Context context, int resource, List<ListMeasurements> object)
    {
        super(context, resource, object);
        mContex =context;
        mResoure=  resource;
    }
    int id;
    String date;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater layoutInflater= LayoutInflater.from(mContex);
        convertView = layoutInflater.inflate((mResoure),parent,false);

        TextView txtId=convertView.findViewById(R.id.textViewId);
        TextView txtData= convertView.findViewById(R.id.textViewData);
        TextView txtWeight= convertView.findViewById(R.id.textViewWeight);

        Date(getItem(position).getTs());

        id=getItem(position).getId();

        txtData.setText("Дата: " + date);
        int val = getItem(position).getValue();
        txtWeight.setText("Вес: "+ Integer.toString(val) + " кг");

        return  convertView;
    }
    void Date(String datestring)
    {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date date2 = format.parse(datestring);
            DateFormat format2=new SimpleDateFormat("yyyy-MM-dd");
            date = format2.format(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}



