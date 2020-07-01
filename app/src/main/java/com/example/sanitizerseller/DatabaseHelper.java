package com.example.sanitizerseller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static final String TABLE_NAME="registration";
    public static final String COL_1="ID";
    public static final String COL_2="Name";
    public static final String COL_3="Phone";
    public static final String COL_4="Gmail";
    public static final String COL_5="Password";
   public DatabaseHelper(Context context)
    {
        super(context,"MarketwinResturantApp.sqlite",null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
          String sql="create table item(id integer primary key AUTOINCREMENT,item_name varchar(100),item_mrp varchar(20),item_outprice varchar(20),item_weight varchar(20),item_stock varchar(20),item_category varchar(100),item_description varchar(500),item_image BLOB)";
          sqLiteDatabase.execSQL(sql);
          //sql="create table thali(id integer primary key AUTOINCREMENT,thali_id varchar(100),thali_name varchar(100),thali_price varchar(20),thali_type varchar(100), thali_category varchar(100),thali_time varchar(100))";
          //sqLiteDatabase.execSQL(sql);
          sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone TEXT,Gmail TEXT,Password TEXT)");
          //sql="create table package_item(id integer primary key AUTOINCREMENT,package_id varchar(100),item_name varchar(100),item_price varchar(20),item_type varchar(100), item_category varchar(100),item_image varchar(100),item_quantity varchar(100),item_type2 varchar(100))";
          //sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
