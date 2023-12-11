package com.example.tree;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RecordDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "records.db";
    private static final int DATABASE_VERSION = 3;

    // 테이블 생성 쿼리
    private static final String TABLE_CREATE =
            "CREATE TABLE records (_id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, duration INTEGER, random INTEGER DEFAULT 0, hourNum INTEGER, treeIndex INTEGER);";
    private static final String TABLE_FAILURE =
            "CREATE TABLE failures (record_id INTEGER PRIMARY KEY, isFailure INTEGER DEFAULT 0);";
    private static final String TABLE_PRODUCTS_CREATE =
            "CREATE TABLE products (_id INTEGER PRIMARY KEY AUTOINCREMENT, product_name TEXT, purchased INTEGER DEFAULT 0, price REAL DEFAULT 0.0);";


    public RecordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLE_FAILURE);
        db.execSQL(TABLE_PRODUCTS_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records");
        db.execSQL("DROP TABLE IF EXISTS failures");
        db.execSQL("DROP TABLE IF EXISTS products");
        onCreate(db);
    }
    public ArrayList<ItemLog> getRecords() {
        ArrayList<ItemLog> recordsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT date, duration FROM records", null);

        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                int duration = cursor.getInt(cursor.getColumnIndex("duration"));
                recordsList.add(new ItemLog(date, duration));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return recordsList;
    }
}