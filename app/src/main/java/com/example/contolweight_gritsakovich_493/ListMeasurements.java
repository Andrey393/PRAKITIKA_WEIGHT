package com.example.contolweight_gritsakovich_493;

public class ListMeasurements
{
    public String token;
    public int id;
    public String ts;
    public int value;
    public ListMeasurements(String token, int id, String ts, int value)
    {
        this.token = token;
        this.id = id;
        this.ts = ts;
        this.value = value;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
