package com.example.tree;

public class ProductTree {
    int resId;
    int resId2;
    String name;
    int price;

    Boolean isPurchased = false;

    public ProductTree(){}
    public ProductTree(int resId, int resId2, String name, int price) {
        this.name = name;
        this.resId=resId;
        this.resId2 = resId2;
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
    public int getResId2() { return this.resId2; }
    public String getName() { return this.name; }
    public void setName(String new_name) { this.name = new_name; }
    public void setResId(int resId) { this.resId=resId; }
    public void setResId2(int resId2) { this.resId2 = resId2; }

    public void setIsPurchased(Boolean value){isPurchased=value;}
    public Boolean getIsPurchased(){return isPurchased;}

}
