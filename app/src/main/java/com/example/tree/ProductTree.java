package com.example.tree;

public class ProductTree {
    int resId;
    String name;
    int price;

    Boolean isPurchased = false;

    public ProductTree(){}
    public ProductTree(int resId, String name, int price) {
        this.name = name;
        this.resId=resId;
        this.price=price;
    }

    public int getPrice(){
        return price;
    }
    public void setPrice(int price){
        this.price=price;
    }
    public int getResId(){
        return resId;
    }
    public String getName() { return this.name; }
    public void setName(String new_name) { this.name = new_name; }
    public void setResId(int resId) { this.resId=resId; }
    public void setIsPurchased(Boolean value){isPurchased=value;}
    public Boolean getIsPurchased(){return isPurchased;}

}
