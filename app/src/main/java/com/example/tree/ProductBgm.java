package com.example.tree;

public class ProductBgm {

    String name;
    int price;
    Boolean isPurchased = false; // 보유 중인지 아닌지
    Boolean isPlaying = false;

    public ProductBgm(){}
    public ProductBgm(String name, int price){
        this.name =name;
        this.price=price;
        isPurchased=false;
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
    public boolean getIsPlaying() { return isPlaying; }
    public void setIsPlaying(boolean isPlaying) { this.isPlaying = isPlaying; }
}
