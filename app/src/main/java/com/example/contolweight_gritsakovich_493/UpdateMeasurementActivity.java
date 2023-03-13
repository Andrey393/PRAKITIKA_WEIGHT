package com.example.contolweight_gritsakovich_493;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMeasurementActivity extends AppCompatActivity
{
    String Token = MainActivity.Token;
    EditText ETWeihgt,ETData;
    String idString,dataString;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_measurement);
        Intent intent = this.getIntent();
        ETData = findViewById(R.id.editTextData);
        ETWeihgt = findViewById(R.id.editTextWeight);
        if(intent != null)
        {
            idString = intent.getStringExtra("id");
            id=Integer.parseInt(idString);

          String weight = intent.getStringExtra("weight");
          String data = intent.getStringExtra("data");
          ETWeihgt.setText(weight);
            ETData.setText(data);

        }
    }
    public void ClickSave(View view)
    {
        String weightString,dataString;
        weightString = ETWeihgt.getText().toString();

        dataString = ETData.getText().toString();
        int weight =Integer.parseInt(weightString);

        ApiHelper apiHelper = new ApiHelper(UpdateMeasurementActivity.this)
        {
            public void onSuccess(String res)
            {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Успешно сохранена", Toast.LENGTH_SHORT);
                toast.show();

            }
            public void onFail(Exception ex)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Возникли проблемы с обновлением", Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("token",Token);
            object.put("xid", id);
            //object.put("xts", dataString);
            object.put("xvalue", weight);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/update_measurement",object.toString());
    }
    public void ClickDelete(View view)
    {
        ApiHelper apiHelper = new ApiHelper(UpdateMeasurementActivity.this)
        {
            public void onSuccess(String res)
            {

                Toast toast = Toast.makeText(getApplicationContext(),
                        "Успешно удален", Toast.LENGTH_SHORT);
                toast.show();
                Intent i = new Intent(UpdateMeasurementActivity.this, MainActivity.class);
                finish();

            }
            public void onFail(Exception ex)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Возникли проблемы с удалением", Toast.LENGTH_SHORT);
                toast.show();
            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("token",Token);
            object.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/delete_measurement",object.toString());
    }
}