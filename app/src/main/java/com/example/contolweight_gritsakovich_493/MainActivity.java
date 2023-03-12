package com.example.contolweight_gritsakovich_493;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText ETLogin,ETPassword;
    static public String Token;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ETLogin = findViewById(R.id.editTextLogin);
        ETPassword = findViewById(R.id.editTextPassword);

    }
    public void ClickEnter(View view) //Авторизация
    {

        String Login,Password;
        Login = ETLogin.getText().toString();
        Password = ETPassword.getText().toString();

        ApiHelper apiHelper = new ApiHelper(MainActivity.this)
        {
            public void onSuccess(String res)
            {
                Token= res;
                Toast toast = Toast.makeText(getApplicationContext(),
                        Token, Toast.LENGTH_SHORT);
                toast.show();

                Token =Token.replace("\"", "");
                Intent myIntent = new Intent(MainActivity.this,ListControlWeight.class);
                MainActivity.this.startActivity(myIntent);
            }
        };
        JSONObject object = new JSONObject();
        try {
            object.put("name",Login);
            object.put("secret", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/sign_in",object.toString());

    }
    public void ClintSingOut(View view)
    {
        ApiHelper apiHelper = new ApiHelper(MainActivity.this)
        {
            public void onSuccess(String res)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Успешно вышли из пользователя", Toast.LENGTH_SHORT);
                toast.show();
                Token = "";
            }

            public void onFail(Exception ex)
            {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Нельзя выйти из не авторизованного пользователя", Toast.LENGTH_SHORT);
                toast.show();
            }
        };

        JSONObject object = new JSONObject();
        try {
            object.put("token",Token);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        apiHelper.send("rpc/sign_out",object.toString());
    }
    public void Register(View view)
    {
        Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(myIntent);

    }

}

