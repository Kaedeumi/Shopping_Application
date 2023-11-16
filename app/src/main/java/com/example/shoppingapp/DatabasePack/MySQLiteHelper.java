package com.example.shoppingapp.DatabasePack;





import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public MySQLiteHelper(@Nullable Context context) {
        super(context,"ShoppingApp.db", null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table IF NOT EXISTS person(id varchar(10) primary key,num integer,username varchar(30),password varchar(30),money integer)");
//        sqLiteDatabase.execSQL("create table IF NOT EXISTS shoppingDATA(id varchar(10),goodsName varchar(30),goodsPrice varchar(10),icon INTEGER)");
        sqLiteDatabase.execSQL("CREATE TABLE shoppingDATA (id varchar(10), goodsName varchar(30), goodsPrice varchar(10), icon integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

