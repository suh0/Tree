package com.example.tree;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
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

    private void addInitialProducts(SQLiteDatabase sqLiteDatabase) {    //1이 구매. 기본품종은 1로 해놈
        addProduct(sqLiteDatabase, "tree1_5s", 0, 1, 0);
        addProduct(sqLiteDatabase, "tree2_5s", 0, 1, 100);
        addProduct(sqLiteDatabase, "tree3_5s", 0, 1, 200);
        addProduct(sqLiteDatabase, "tree4_5s", 0, 1, 300);
        // 5 sec trees
        // test purpose

        addProduct(sqLiteDatabase, "tree1_30m", 0.5, 1, 100);
        addProduct(sqLiteDatabase, "tree2_30m", 0.5, 0, 200);
        addProduct(sqLiteDatabase, "tree3_30m", 0.5, 0, 300);
        addProduct(sqLiteDatabase, "tree1_1h", 1, 1, 100);
        addProduct(sqLiteDatabase, "tree2_1h", 1, 0, 200);
        addProduct(sqLiteDatabase, "tree3_1h", 1, 0, 300);
        addProduct(sqLiteDatabase, "tree1_2h", 2, 1, 100);
        addProduct(sqLiteDatabase, "tree2_2h", 2, 0, 200);
        addProduct(sqLiteDatabase, "tree3_2h", 2, 0, 300);
        //
        addProduct(sqLiteDatabase, "tree4_30m", 0.5, 0, 300);
        addProduct(sqLiteDatabase, "tree4_1h", 1, 0, 500);
        addProduct(sqLiteDatabase, "tree4_2h", 2, 0, 700);
    }

    private void addProduct(SQLiteDatabase sqLiteDatabase, String item_name, double duration, int purchased, int price) {
        ContentValues values = new ContentValues();
        values.put("item_name", item_name);
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

    public long getDatabaseSize() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(sqLiteDatabase, "items");
    }

    public Cursor getShopData() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] data = {"item_name", "purchased", "price"};
        Cursor cursor = sqLiteDatabase.query("items", data, null, null, null, null, null);
        return cursor;
    }

    public ArrayList<ProductTree> getAllTrees() {
        ArrayList<ProductTree> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"item_name", "purchased", "price"};
        Cursor cursor = sqLiteDatabase.query("items", columns, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex("item_name");
            int purchasedIndex = cursor.getColumnIndex("purchased");
            int priceIndex = cursor.getColumnIndex("price");
            do {
                ProductTree temp = new ProductTree();
                temp.setName(cursor.getString(nameIndex));
                if(cursor.getInt(cursor.getInt(purchasedIndex)) == 1)
                    temp.setIsPurchased(true);
                else
                    temp.setIsPurchased(false);
                temp.setPrice(cursor.getInt(priceIndex));

                tempList.add(temp);
            } while (cursor.moveToNext());
        }
        else
            Log.d("TreeItemDataBaseHelper", "getAllTrees: cursor not found");

        cursor.close();
        return tempList;
    }
}
