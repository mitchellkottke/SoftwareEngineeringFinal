package com.example.sasha.finalsoftware;

import android.util.Log;
import android.widget.RelativeLayout;

public class ExampleItem {

    private String mName;
    private String mSex;
    private String mAnswer;
    private int mYear;
    private double mPercent;

    private RelativeLayout mrelLayout;

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

    public ExampleItem(String name, String sex, String answer){
        mName = name;
        mSex = sex;
        mAnswer = answer;
        Log.d("ExampleItem", "Item has been created with name: "+mName);
    }

    public String getmName(){ return mName; }

    public String getmSex(){
        return mSex;
    }

    public String getmAnswer() {return mAnswer;}

    public int getmYear(){
        return mYear;
    }

    public double getmPercent(){
        return mPercent;
    }

    //NEW
    public RelativeLayout getMrelLayout() { return  mrelLayout;}

}
