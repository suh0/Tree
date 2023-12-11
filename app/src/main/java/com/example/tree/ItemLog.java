package com.example.tree;

public class ItemLog {

    String date; // sf >> success 인지 fail 인지
    long time;

    public ItemLog(String date, long time){
        this.date=date;
        this.time=time;
    }

    public String getDate(){return date;}

    public void setDate(String date){this.date=date;}

    public long getTime(){return time;}

    public void setTime(long time){this.time=time;}

}
