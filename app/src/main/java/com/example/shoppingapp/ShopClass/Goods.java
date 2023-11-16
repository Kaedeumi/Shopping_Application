package com.example.shoppingapp.ShopClass;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Goods {
    private ArrayList<Bitmap> bitmaps;
    private String title;
    private String price;
    private int icon;
    private String sales;



    public Goods(){
        this.price = "";
        this.title = "";
        this.sales = "";
        this.icon= 700006;

    }


    public Goods(String title, String price, String sales, int icon){
        this();
        this.title = title;
        this.price = price;
        this.sales = sales;
        this.icon = icon;
    }

    public int geticon() {
        return icon;
    }

    public void seticon(int icon) {
        this.bitmaps = bitmaps;
    }

    public String getTitle() {
        return title;
    }

    public void setGoodsName(String goodsName) {
        this.title = goodsName;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String price) {
        this.sales = sales;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public int getPriceInt(){
        String str =price.substring(0,price.length()-1);
        return Integer.valueOf(str);

    }


    public boolean equals(Goods goods){
        return this.getTitle().equals(goods.getTitle())
                && this.getPrice().equals(goods.getPrice())
                && this.getSales().equals(goods.getSales())
                && this.geticon() == goods.geticon();
    }

}
