package com.example.tree;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class RecordActivity extends AppCompatActivity {

    private RecordDatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        dbHelper = new RecordDatabaseHelper(this);
        LinearLayout recordLayout = findViewById(R.id.recordLayout);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM records WHERE date LIKE ?", new String[]{"%"});

        int durationIndex = cursor.getColumnIndex("duration");
        int hourNumIndex = cursor.getColumnIndex("hourNum");

        int[] durationsCount = new int[4];

        while (cursor.moveToNext()) {
            long duration = cursor.getLong(durationIndex);
            int hourNum = cursor.getInt(hourNumIndex);

            if (duration == 5000) {
                durationsCount[0]++;
            } else if (duration == 1800000) {
                durationsCount[1]++;
            } else if (duration == 3600000) {
                durationsCount[2]++;
            } else if (duration == 7200000) {
                durationsCount[3]++;
            }
        }

        int totalRecordCount = getTotalRecordCount(db);

        TextView totalRecordCountTextView = new TextView(this);
        totalRecordCountTextView.setText("총 데이터 수: " + totalRecordCount);
        recordLayout.addView(totalRecordCountTextView);

        cursor.close();

        TextView summaryTextView = new TextView(this);
        summaryTextView.setText("5초: " + durationsCount[0] + "번, 30분: "
                + durationsCount[1] + "번, 1시간: " + durationsCount[2] + "번, 2시간: "
                + durationsCount[3] + "번");
        recordLayout.addView(summaryTextView);

        long totalDuration = calculateTotalDuration(db);

        TextView totalDurationTextView = new TextView(this);
        totalDurationTextView.setText("총 합계 시간: " + totalDuration + "밀리초");
        recordLayout.addView(totalDurationTextView);

        Button deleteButton = new Button(this);
        deleteButton.setText("데이터 삭제");
        deleteButton.setOnClickListener(v -> deleteAllData());
        recordLayout.addView(deleteButton);
    }

    private long calculateTotalDuration(SQLiteDatabase db) {
        long totalDuration = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(duration) FROM records", null);
        if (cursor.moveToFirst()) {
            totalDuration = cursor.getLong(0);
        }

        cursor.close();
        return totalDuration;
    }
    private int getTotalRecordCount(SQLiteDatabase db) {
        int totalRecordCount = 0;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM records", null);
        if (cursor.moveToFirst()) {
            totalRecordCount = cursor.getInt(0);
        }

        cursor.close();
        return totalRecordCount;
    }

    private void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("records", null, null);
        db.close();
        recreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
