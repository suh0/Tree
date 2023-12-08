package com.example.tree;

public class ProductBgm {

    String name;
    int price;
    Boolean isPurchased=false; // 보유 중인지 아닌지
    int musicResId;

    // 기본 생성자 추가
    public ProductBgm() { //오류난 부분 수정
        this.name = "";
        this.price = 0;
        this.isPurchased = false;
        this.musicResId = 0;
    }

    public ProductBgm(String title, int price,int musicResId, boolean isPurchased){
        this.name=title;
        this.price=price;
        //isPurchased=false;
        this.isPurchased = isPurchased;
        this.musicResId = musicResId;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
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
