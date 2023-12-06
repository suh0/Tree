package com.example.tree;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreeItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS =
            "CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, " +
                    "id INTERGER, " +
                    "duration REAL, " +
                    "purchased INTEGER DEFAULT 0, " +
                    "price INTEGER DEFAULT 0.0);";

    private static final int DATABASE_VERSION = 5;

    public TreeItemDatabaseHelper(Context context) {
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

    private void addInitialProducts(SQLiteDatabase sqLiteDatabase) {    //1이 구매. 기본품종은 1로 해놈
        addProduct(sqLiteDatabase, "tree1_5s", 1, 0, 0, 0);
        addProduct(sqLiteDatabase, "tree2_5s", 2, 0, 0, 100);
        addProduct(sqLiteDatabase, "tree3_5s", 3, 0, 0, 200);
        addProduct(sqLiteDatabase, "tree4_5s", 4, 0, 0, 300);
        // 5 sec trees
        // test purpose

        addProduct(sqLiteDatabase, "tree1_30m", 5, 0.5, 0, 100);
        addProduct(sqLiteDatabase, "tree2_30m", 6, 0.5, 0, 200);
        addProduct(sqLiteDatabase, "tree3_30m", 7, 0.5, 0, 300);
        addProduct(sqLiteDatabase, "tree1_1h", 8, 1, 0, 100);
        addProduct(sqLiteDatabase, "tree2_1h", 9, 1, 0, 200);
        addProduct(sqLiteDatabase, "tree3_1h", 10, 1, 0, 300);
        addProduct(sqLiteDatabase, "tree1_2h", 11, 2, 0, 100);
        addProduct(sqLiteDatabase, "tree2_2h", 12, 2, 0, 200);
        addProduct(sqLiteDatabase, "tree3_2h", 13, 2, 0, 300);
        //
        addProduct(sqLiteDatabase, "tree4_30m", 14, 0.5, 0, 300);
        addProduct(sqLiteDatabase, "tree4_1h", 15, 1, 0, 500);
        addProduct(sqLiteDatabase, "tree4_2h", 16, 2, 0, 700);
    }

    private void addProduct(SQLiteDatabase sqLiteDatabase, String item_name, int id, double duration, int purchased, int price) {
        ContentValues values = new ContentValues();
        values.put("item_name", item_name);
        values.put("id", id);
        values.put("duration", duration);
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

    public int getPurchase(String item_name) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"purchased"};
        String selection = "item_name=?";
        String[] selectionArgs = {item_name};
        Cursor cursor = sqLiteDatabase.query("items", columns, selection, selectionArgs, null, null, null);
        int isPurchased = 0;
        if(cursor.moveToFirst()) {
            int dataIndex = cursor.getColumnIndex("purchased");
            isPurchased = cursor.getInt(dataIndex);
        }
        cursor.close();
        return isPurchased;
    }

    public boolean getPurchaseByIndex(int index) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int result = 0;
        String[] columns = {"purchased"};
        String selection = "id=?";
        String[] selectionArgs = {String.valueOf(index)};
        Cursor cursor = sqLiteDatabase.query("items", columns, selection, selectionArgs, null, null, null);
        if(cursor != null && cursor.moveToFirst()) {
            int dataIndex = cursor.getColumnIndex("purchased");
            result = cursor.getInt(dataIndex);
        }
        if(result == 1)
            return true;
        else
            return false;
    }

    public long getDatabaseSize() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, "items");
    }


    public ArrayList<ProductTree> getAllTrees() {
        ArrayList<ProductTree> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"item_name", "purchased", "price"};
        Cursor cursor = sqLiteDatabase.query("items", columns, null, null, null, null, null);
        int nameIndex = cursor.getColumnIndex("item_name");
        int purchasedIndex = cursor.getColumnIndex("purchased");
        int priceIndex = cursor.getColumnIndex("price");
        if(cursor.moveToFirst()) {
            do {
                ProductTree temp = new ProductTree();
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
            Log.d("TreeItemDataBaseHelper", "getAllTrees: cursor not found");

        Log.d("TreeDatabase", "getAllTrees: current tempList: " + tempList.size());
        cursor.close();
        return tempList;
    }
}
