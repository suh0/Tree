package com.example.tree;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

public class CoinDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "records_coin.db";
    private static final int DATABASE_VERSION = 8;
    // private static final int DEFAULT_BALANCE = 1000;

    private static final String TABLE_CREATE =
            "CREATE TABLE records_coin ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "balance INTEGER DEFAULT 1000)";

    public CoinDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        ContentValues initialValue = new ContentValues();
        initialValue.put("balance", 1000);
        db.insert("records_coin", null, initialValue);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS records_coin");
        onCreate(db);
    }

    public int getCurrentBalance() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {"balance"};
        Cursor cursor = db.query("records_coin", projection, null, null, null, null, null);
        int currentBalance = 0;
        if (cursor.moveToFirst()) {
            int balanceIndex = cursor.getColumnIndex("balance");
            currentBalance = cursor.getInt(balanceIndex);
        }
        cursor.close();
        return currentBalance;
    }

    public void addBalance(int amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("balance", getCurrentBalance() + amount);
        db.update("records_coin", values, null, null);
        db.close();
    }
}
