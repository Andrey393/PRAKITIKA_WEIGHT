package com.example.contolweight_gritsakovich_493;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity
{
    EditText ETAddLogin,ETPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ETAddLogin = findViewById(R.id.editTextAddLogin);
        ETPassword = findViewById(R.id.editTextAddPassword);
    }
    public void ClickCreate(View view)
    {
        String Login,Password;

        Login = ETAddLogin.getText().toString();
        Password = ETPassword.getText().toString();

        ApiHelper apiHelper = new ApiHelper(RegisterActivity.this)
        {
            public void onSuccess(String res)
            {
                Toats(1);
            }

            public void onFail(Exception ex)
            {
                Toats(-1);
            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("name",Login);
            object.put("secret", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/register_account",object.toString());

    }
    void Toats(int i)
    {
        if(i == 1)
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Аккаунт был успешно создан", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Аккаунт уже существует", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}