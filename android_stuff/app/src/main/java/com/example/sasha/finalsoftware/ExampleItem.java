package com.example.sasha.finalsoftware;

import android.util.Log;

public class ExampleItem {

    private String mName;
    private String mSex;
    private int mYear;
    private double mPercent;

    public ExampleItem(String name, String sex, int year, double percent){
        mName = name;
        mSex = sex;
        mYear = year;
        mPercent = percent;
        Log.d("ExampleItem", "Item has been created with name: "+mName);
    }

    public ExampleItem(String name, String sex){
        mName = name;
        mSex = sex;
        Log.d("ExampleItem", "Item has been created with name: "+mName);
    }

    public String getmName(){ return mName; }

    public String getmSex(){
        return mSex;
    }

    public int getmYear(){
        return mYear;
    }

    public double getmPercent(){
        return mPercent;
    }

}
