package com.example.sasha.finalsoftware;

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
