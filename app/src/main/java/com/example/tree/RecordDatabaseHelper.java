package com.example.tree;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "records.db";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_CREATE =
            "CREATE TABLE records (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, duration INTEGER, random INTEGER DEFAULT 0);";

    private static final String TABLE_PRODUCTS_CREATE =
            "CREATE TABLE products (_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name TEXT, purchased INTEGER DEFAULT 0);";

    public RecordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
        addInitialProducts(db);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_PRODUCTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }

    private void addInitialProducts(SQLiteDatabase db) {    //1이 구매. 기본품종은 1로 해놈
        addProduct(db, "tree1_30m", 1);
        addProduct(db, "tree2_30m", 1);
        addProduct(db, "tree3_30m", 1);
        addProduct(db, "tree1_1h", 1);
        addProduct(db, "tree2_1h", 1);
        addProduct(db, "tree3_1h", 1);
        addProduct(db, "tree1_2h", 1);
        addProduct(db, "tree2_2h", 1);
        addProduct(db, "tree3_2h", 1);
        addProduct(db, "tree4_30m", 0);
        addProduct(db, "tree4_1h", 0);
        addProduct(db, "tree4_2h", 0);

        addProduct(db, "music1", 0);
        addProduct(db, "music2", 0);
        addProduct(db, "music3", 0);
    }

    private void addProduct(SQLiteDatabase db, String productName, int purchased) {
        ContentValues values = new ContentValues();
        values.put("product_name", productName);
        values.put("purchased", purchased);
        db.insert("products", null, values);
    }
}
