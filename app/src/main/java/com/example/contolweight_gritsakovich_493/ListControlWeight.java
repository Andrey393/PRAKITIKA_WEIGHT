package com.example.contolweight_gritsakovich_493;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListControlWeight extends AppCompatActivity
{
    public ArrayList<ListMeasurements> ListMeas = new ArrayList<ListMeasurements>();
    public String Token = MainActivity.Token;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_control_weight);
        listView = findViewById(R.id.ListV);

    }

    public void ClickAdd(View view)
    {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        String date2 = df.format(Calendar.getInstance().getTime());

        String date =date2.toString();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Введите свой вес");

        EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton)
            {
                int weight = Integer.parseInt(input.getText().toString());

                ApiHelper apiHelper = new ApiHelper(ListControlWeight.this)
                {
                    public void onSuccess(String res)
                    {

                        ListMeas.clear();
                        LoaderListMeasurements();
                    }
                };
                JSONObject object = new JSONObject();
                try {
                    object.put("token",Token);
                    object.put("ts", date);
                    object.put("value",weight );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                apiHelper.send("rpc/add_measurement",object.toString());

            }
        });

        alert.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        alert.show();


    }
    @Override
    protected void onStart()
    {
        super.onStart();
        ListMeas.clear();
        LoaderListMeasurements();
    }

    void LoaderListMeasurements()
    {
        ApiHelper apiHelper = new ApiHelper(ListControlWeight.this)
        {
            public void onSuccess(String res)
            {
                Array(res);

            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("token",Token);
            object.put("ts_from", "1900-04-12 12:00:00");
            object.put("ts_to", "2023-08-13 08:00:00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/list_measurements",object.toString());
    }

    public void Array(String res)
    {
        JSONArray jsonArr = null;
        try {
            jsonArr = new JSONArray(res);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        for (int i=0; i<jsonArr.length(); i++) {
            JSONObject actor = null;
            try {
                actor = jsonArr.getJSONObject(i);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



            int id = 0;
            String data =null;
            int value = 0;
            try
            {
                id = actor.getInt("id");
                data = actor.getString("ts");
                value = actor.getInt("value");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            ListMeas.add(new ListMeasurements(Token,id,data,value));
        }
        //
        //
        PersonListAdapter listAdapter =new PersonListAdapter(this,R.layout.listviewarray,ListMeas);
        listView.setAdapter(listAdapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent a = new Intent(ListControlWeight.this,UpdateMeasurementActivity.class);

                String id = String.valueOf( ListMeas.get(i).id);
                String value = String.valueOf( ListMeas.get(i).value);

                a.putExtra("token",Token);
                a.putExtra("id",id);
                a.putExtra("weight",value);
                a.putExtra("data",ListMeas.get(i).ts);
                ListControlWeight.this.startActivity(a);
            }
        });
        listView.setClickable(true);
    }
}