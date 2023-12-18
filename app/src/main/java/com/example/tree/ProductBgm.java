package com.example.tree;

import android.util.Log;

public class ProductBgm {

    String name;
    int price;
    Boolean isPurchased = false; // 보유 중인지 아닌지
    int musicResId;


    public ProductBgm() {
    }

    public ProductBgm(String name, int price, int musicResId) {
        this.name = name;
        this.price = price;
        isPurchased = false;
        this.musicResId = musicResId;
        Log.d("ProductBgm", "Set Music Resource ID: " + this.musicResId); // 로그 추가
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsPurchased() {
        return isPurchased;
    }

    public void setIsPurchased(Boolean value) {
        this.isPurchased = value;
    }


    public int getMusicResId() {
        Log.d("ProductBgm", "Retrieved Music Resource ID: " + musicResId);
        return musicResId;

    }

    public void setMusicResId(int musicResId) {
        Log.d("ProductBgm", "Set Music Resource ID: " + musicResId);
        if (musicResId != 0) { // 0이 아닌 경우에만 값을 설정하도록 변경
            this.musicResId = musicResId;
        } else {
            Log.e("ProductBgm", "Invalid Music Resource ID: " + musicResId);
        }
    }
}

