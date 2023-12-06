package com.example.tree;

public class ItemLog {

    String date, sf; // sf >> success 인지 fail 인지
    long time;

    public ItemLog(String date, String sf, long time){
        this.date=date;
        this.sf=sf;
        this.time=time;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date=date;}

    public long getTime(){return time;}

    public void setTime(long time){this.time=time;}

    public String getSf(){return sf;}

    public void setSf(String sf){this.sf=sf;}
}
