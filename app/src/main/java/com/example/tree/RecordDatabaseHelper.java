package com.example.tree;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "records.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_CREATE =

            "CREATE TABLE records (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, duration INTEGER, random INTEGER DEFAULT 0, hourNum INTEGER, treeIndex INTEGER);";

    private static final String TABLE_TREE_PRODUCTS_CREATE =
            "CREATE TABLE tree_products (_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name TEXT, purchased INTEGER DEFAULT 0, price REAL DEFAULT 0.0);";

    private static final String TABLE_BGM_PRODUCTS_CREATE =
            "CREATE TABLE bgm_products (_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name TEXT, purchased INTEGER DEFAULT 0, price REAL DEFAULT 0.0);";

    public RecordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        addInitialTreeProducts(db);
        addInitialBgmProducts(db);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_TREE_PRODUCTS_CREATE);
        db.execSQL(TABLE_BGM_PRODUCTS_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        db.execSQL("DROP TABLE IF EXISTS tree_products");
        db.execSQL("DROP TABLE IF EXISTS bgm_products");
        onCreate(db);
    }

    private void addInitialTreeProducts(SQLiteDatabase db) {    //1이 구매. 기본품종은 1로 해놈
        addTreeProduct(db, "tree1_30m", 1, 0);
        addTreeProduct(db, "tree2_30m", 1, 0);
        addTreeProduct(db, "tree3_30m", 1, 0);
        addTreeProduct(db, "tree1_1h", 1, 0);
        addTreeProduct(db, "tree2_1h", 1, 0);
        addTreeProduct(db, "tree3_1h", 1, 0);
        addTreeProduct(db, "tree1_2h", 1, 0);
        addTreeProduct(db, "tree2_2h", 1, 0);
        addTreeProduct(db, "tree3_2h", 1, 0);
        addTreeProduct(db, "tree4_30m", 0, 300);
        addTreeProduct(db, "tree4_1h", 0, 500);
        addTreeProduct(db, "tree4_2h", 0, 700);

    }

    private void addInitialBgmProducts(SQLiteDatabase db){
        addBgmProduct(db, "music1", 0, 500);
        addBgmProduct(db, "music2", 0, 500);
        addBgmProduct(db, "music3", 0, 500);
    }

    private void addTreeProduct(SQLiteDatabase db, String productName, int purchased, double price) {
        ContentValues values = new ContentValues();
        values.put("product_name", productName);
        values.put("purchased", purchased);
        values.put("price", price);
        db.insert("tree_products", null, values);
    }
    private void addBgmProduct(SQLiteDatabase db, String productName, int purchased, double price) {
        ContentValues values = new ContentValues();
        values.put("product_name", productName);
        values.put("purchased", purchased);
        values.put("price", price);
        db.insert("bgm_products", null, values);
    }
}
