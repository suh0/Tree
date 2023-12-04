package com.example.tree;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS =
            "CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, " +
                    "purchased INTEGER DEFAULT 0, " +
                    "price REAL DEFAULT 0.0);";

    private static final int DATABASE_VERSION = 1;

    public MusicItemDatabaseHelper(Context context) {
        super(context, TABLE_ITEMS, null, DATABASE_VERSION);
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        addInitialProducts(sqLiteDatabase);
        sqLiteDatabase.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_ITEMS);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS items");
        onCreate(sqLiteDatabase);
    }

    private void addInitialProducts(SQLiteDatabase sqLiteDatabase) {
        addProduct(sqLiteDatabase, "music1", 0, 500);
        addProduct(sqLiteDatabase, "music2", 0, 500);
        addProduct(sqLiteDatabase, "music3", 0, 500);
    }

    private void addProduct(SQLiteDatabase sqLiteDatabase, String productName, int purchased, double price) {
        ContentValues values = new ContentValues();
        values.put("item_name", productName);
        values.put("purchased", purchased);
        values.put("price", price);
        sqLiteDatabase.insert("products", null, values);
    }

    public int applyPurchase(String productName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(productName, 1);

        String whereClause = "item_name=?";
        String[] whereArgs = new String[]{productName};

        return sqLiteDatabase.update("items", values, whereClause, whereArgs);
    }

    public int getPurchase(String productName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"purchased"};
        String selection = "item_name=?";
        String[] selectionArgs = {productName};
        Cursor cursor = sqLiteDatabase.query("items", columns, selection, selectionArgs, null, null, null);
        int isPurchased = 0;
        if(cursor.moveToFirst()) {
            int dataIndex = cursor.getColumnIndex("purchased");
            isPurchased = cursor.getInt(dataIndex);
        }
        cursor.close();
        return isPurchased;
    }
}
