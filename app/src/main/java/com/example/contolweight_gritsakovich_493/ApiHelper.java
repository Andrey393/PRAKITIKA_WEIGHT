package com.example.contolweight_gritsakovich_493;

import android.app.Activity;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiHelper
{

    Activity ctx;
    String base;
    public ApiHelper(Activity ctx)
    {
        base = "http://spbcoit.ru/lab/weight/api";
        this.ctx = ctx;
    }
    public void onSuccess(String res) {

    }
    public void onFail(Exception ex)
    {

    }

    String httpGet(String req,String body) throws Exception
    {

        URL url = new URL(req);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        BufferedOutputStream out = new BufferedOutputStream(connection.getOutputStream());
        out.write(body.getBytes());
        out.flush();
        BufferedInputStream inp = new BufferedInputStream(connection.getInputStream());
        byte[] buf = new byte[512];
        String res = "";
        while (true)
        {
            int num = inp.read(buf);
            if (num < 0) break;
            res += new String(buf,0,num);
        }
        connection.disconnect();
        return res;
    }

    public class NetOp implements Runnable
    {
        public String req;
        public String body;

        @Override
        public void run() {
            try{
                final String res = httpGet(base+"/"+req,body);
                ctx.runOnUiThread(()->{
                    onSuccess(res);
                });
            }
            catch (Exception ex)
            {
                ctx.runOnUiThread(()-> {
                    onFail(ex);
                });

            }
        }
    }

    public void send(String req, String body)
    {
        NetOp netOp = new NetOp();
        netOp.body = body;
        netOp.req = req;
        Thread thread = new Thread(netOp);
        thread.start();
    }
}

