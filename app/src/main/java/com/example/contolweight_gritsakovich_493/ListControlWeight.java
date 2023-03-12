package com.example.contolweight_gritsakovich_493;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListControlWeight extends AppCompatActivity
{
    public String Token = MainActivity.Token;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_control_weight);
        LoaderListMeasurements();
    }
    void LoaderListMeasurements()
    {
        ApiHelper apiHelper = new ApiHelper(ListControlWeight.this)
        {
            public void onSuccess(String res)
            {
                Array(res);

                Toast toast = Toast.makeText(getApplicationContext(),
                        Token, Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("token",Token);
            object.put("ts_from", "2020-04-12 12:00:00");
            object.put("ts_to", "2023-08-01 08:00:00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/list_measurements",object.toString());
    }

    public void Array(String res)
    {
        List<ListMeasurements> ListMeas = new ArrayList<ListMeasurements>();

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
            double value = 0.0;
            try
            {
                id = actor.getInt("id");
                data = actor.getString("ts");
                value = actor.getDouble("value");

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            ListMeas.add(new ListMeasurements(id,data,value));
        }
    }
}