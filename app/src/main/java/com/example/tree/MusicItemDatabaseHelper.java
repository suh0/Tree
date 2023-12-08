package com.example.tree;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MusicItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS =
            "CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, " +
                    "purchased INTEGER DEFAULT 0, " +
                    "price INTEGER DEFAULT 0.0," +
                    "isPlaying INTEGER DEFAULT 0)";

    private static final int DATABASE_VERSION = 4;

    public MusicItemDatabaseHelper(Context context) {
        super(context, TABLE_ITEMS, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_ITEMS);
        addInitialProducts(sqLiteDatabase);
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

    private void addProduct(SQLiteDatabase sqLiteDatabase, String item_name, int purchased, int price) {
        ContentValues values = new ContentValues();
        values.put("item_name", item_name);
        values.put("purchased", purchased);
        values.put("price", price);
        sqLiteDatabase.insert("items", null, values);
    }

    public int applyPurchase(String productName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("purchased", 1);

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

    public long getDatabaseSize() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, "items");
    }

    public ArrayList<ProductBgm> getAllMusic() {
        ArrayList<ProductBgm> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"item_name", "purchased", "price"};
        Cursor cursor = sqLiteDatabase.query("items", columns, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("item_name");
            int purchasedIndex = cursor.getColumnIndex("purchased");
            int priceIndex = cursor.getColumnIndex("price");
            do {
                ProductBgm temp = new ProductBgm();
                temp.setName(cursor.getString(nameIndex));
                if(cursor.getInt(purchasedIndex) == 1)
                    temp.setIsPurchased(true);
                else
                    temp.setIsPurchased(false);
                temp.setPrice(cursor.getInt(priceIndex));

                tempList.add(temp);
            } while (cursor.moveToNext());
        }
        else
            Log.d("MusicItemDataBaseHelper", "getAllTrees: cursor not found");
        cursor.close();

        return tempList;
    }

    public int setPlaying(String item_name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues resetValue = new ContentValues();
        resetValue.put("isPlaying", 0);
        sqLiteDatabase.update("items", resetValue, null, null);

        ContentValues setValue = new ContentValues();
        setValue.put("isPlaying", 1);
        String whereClause = "item_name=?";
        String[] whereArgs = new String[]{item_name};

        return sqLiteDatabase.update("items", setValue, whereClause, whereArgs);
    }

    public ProductBgm getWhatsPlaying() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ProductBgm temp = null;

        String[] columns = {"item_name", "purchased", "price", "isPlaying"};
        String selection = "isPlaying=?";
        String[] selectionArgs = {"1"};
        Cursor cursor = sqLiteDatabase.query("items", columns, selection, selectionArgs, null, null, null);

        if(cursor != null && cursor.moveToFirst() && cursor.getCount() == 1) {
            int nameIndex = cursor.getColumnIndex("item_name");
            int isPurchasedIndex = cursor.getColumnIndex("purchased");
            int priceIndex = cursor.getColumnIndex("price");
            int isPlayingIndex = cursor.getColumnIndex("isPlaying");

            temp = new ProductBgm();
            temp.setName(cursor.getString(nameIndex));
            if(cursor.getInt(isPurchasedIndex) == 1)
                temp.setIsPurchased(true);
            else
                temp.setIsPurchased(false);
            temp.setPrice(cursor.getInt(priceIndex));
            if(cursor.getInt(isPlayingIndex) == 1)
                temp.setIsPlaying(true);
            else
                temp.setIsPlaying(false);
        }
        else
            return null;

        return temp;
    }
}
