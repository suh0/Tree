package com.example.tree;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecordActivity extends AppCompatActivity {

    private RecordDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        dbHelper = new RecordDatabaseHelper(this);
        LinearLayout recordLayout = findViewById(R.id.recordLayout);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);

        int dateIndex = cursor.getColumnIndex("date");
        int durationIndex = cursor.getColumnIndex("duration");
        int randomIndex = cursor.getColumnIndex("random");

        while (cursor.moveToNext()) {
            String date = cursor.getString(dateIndex);
            long duration = cursor.getLong(durationIndex);
            int random = cursor.getInt(randomIndex);

            TextView recordTextView = new TextView(this);
            recordTextView.setText("날짜: " + date + ", 시간: " + duration + " 밀리초, 랜덤: " + random);
            recordLayout.addView(recordTextView);
        }

        cursor.close();
        dbHelper.close();
    }
}
