package com.example.tree;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TreeItemDatabaseHelper extends SQLiteOpenHelper {

    private static final String TABLE_ITEMS =
            "CREATE TABLE items (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "item_name TEXT, " +
                    "resId INTERGER, " +
                    "resId2 INTEGER, " +
                    "purchased INTEGER DEFAULT 0, " +
                    "price INTEGER DEFAULT 0.0);";

    private static final int DATABASE_VERSION = 7;

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

    private void addInitialProducts(SQLiteDatabase sqLiteDatabase) {    // 1이 구매. 기본품종은 1로 해놈
        addProduct(sqLiteDatabase, "tree1_5s", R.drawable.img_tree4, R.drawable.img_tree4_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree2_5s", R.drawable.img_tree3, R.drawable.img_tree3_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree3_5s", R.drawable.img_tree2, R.drawable.img_tree2_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree4_5s", R.drawable.img_tree1, R.drawable.img_tree1_gray, 0, 100);

        addProduct(sqLiteDatabase, "tree1_30m", R.drawable.img_tree1, R.drawable.img_tree1_gray, 1,  0);
        addProduct(sqLiteDatabase, "tree2_30m", R.drawable.img_tree2, R.drawable.img_tree2_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree3_30m", R.drawable.img_tree3, R.drawable.img_tree3_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree4_30m", R.drawable.img_tree4, R.drawable.img_tree4_gray, 0, 200);

        addProduct(sqLiteDatabase, "tree1_1h", R.drawable.img_tree5, R.drawable.img_tree5_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree2_1h", R.drawable.img_tree6, R.drawable.img_tree6_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree3_1h", R.drawable.img_tree7, R.drawable.img_tree7_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree4_1h", R.drawable.img_tree8, R.drawable.img_tree8_gray, 0, 300);

        addProduct(sqLiteDatabase, "tree1_2h", R.drawable.img_tree9, R.drawable.img_tree9_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree2_2h", R.drawable.img_tree10, R.drawable.img_tree10_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree3_2h", R.drawable.img_tree11, R.drawable.img_tree11_gray, 1, 0);
        addProduct(sqLiteDatabase, "tree4_2h", R.drawable.img_tree12, R.drawable.img_tree12_gray, 0, 400);
    }

    private void addProduct(SQLiteDatabase sqLiteDatabase, String item_name, int resId, int resId2, int purchased, int price) {
        ContentValues values = new ContentValues();
        values.put("item_name", item_name);
        values.put("resId", resId);
        values.put("resId2", resId2);
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

    public int getDatabaseSize() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(sqLiteDatabase, "items");
    }

    /*
    public int getColumnCount() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int columnCount = 0;

        Cursor cursor = sqLiteDatabase.rawQuery("PRAGMA table_info(items)", null);
        if(cursor != null) {
            columnCount = cursor.getCount();
            cursor.close();
        }
        return columnCount;
    }
    */


    public ArrayList<ProductTree> getAllTrees() {
        ArrayList<ProductTree> tempList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String[] columns = {"item_name", "resId", "resId2", "purchased", "price"};
        Cursor cursor = sqLiteDatabase.query("items", columns, null, null, null, null, null);
        int nameIndex = cursor.getColumnIndex("item_name");
        int resIdIndex = cursor.getColumnIndex("resId");
        int resId2Index = cursor.getColumnIndex("resId2");
        int purchasedIndex = cursor.getColumnIndex("purchased");
        int priceIndex = cursor.getColumnIndex("price");
        if(cursor.moveToFirst()) {
            do {
                ProductTree temp = new ProductTree();
                temp.setName(cursor.getString(nameIndex));
                temp.setResId(cursor.getInt(resIdIndex));
                temp.setResId2(cursor.getInt(resId2Index));
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
