package com.example.tree;

public class ProductBgm {

    String title;
    int price;
    Boolean isPurchased=false; // 보유 중인지 아닌지
    int musicResId;


    public ProductBgm(String title, int price,int musicResId){
        this.title=title;
        this.price=price;
        isPurchased=false;
        this.musicResId = musicResId;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public Boolean getIsPurchased(){return isPurchased;}
    public void setIsPurchased(Boolean value){this.isPurchased=value;}

    public int getMusicResId() {
        return musicResId;
    }
    public void setMusicResId(int musicResId) {
        this.musicResId = musicResId;
    }
}
