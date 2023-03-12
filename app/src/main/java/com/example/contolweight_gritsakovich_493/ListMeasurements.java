package com.example.contolweight_gritsakovich_493;

public class ListMeasurements
{
    public int  id;
    public String ts;
    public double value;

    public ListMeasurements(int id, String data, double value)
    {
        this.id=id;
        this.ts=data;
        this.value = value;
    }
}
