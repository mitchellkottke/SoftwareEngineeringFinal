package com.example.sasha.finalsoftware;

public class ExampleItem {

    private String mName;
    private String mSex;
    private String mYear;
    private String mPercent;

    public ExampleItem(String question, String type, String reasonReport, String user){
        mName = question;
        mSex = type;
        mYear = reasonReport;
        mPercent = user;
    }

    public String getmName(){ return mName; }

    public String getmSex(){
        return mSex;
    }

    public String getmYear(){
        return mYear;
    }

    public String getmPercent(){
        return mPercent;
    }

}
