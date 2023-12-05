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

        // 해당 월 가져오기 (현재 날짜를 기준으로)
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
        String targetMonth = String.format(Locale.getDefault(), "%04d-%02d", year, month);

        Cursor cursor = db.rawQuery("SELECT * FROM records WHERE date LIKE ?", new String[]{targetMonth + "%"});

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

        cursor.close();

        TextView summaryTextView = new TextView(this);
        summaryTextView.setText(targetMonth + ", 5초: " + durationsCount[0] + "번, 30분: "
                + durationsCount[1] + "번, 1시간: " + durationsCount[2] + "번, 2시간: "
                + durationsCount[3] + "번");
        recordLayout.addView(summaryTextView);

        Button deleteButton = new Button(this);
        deleteButton.setText("데이터 삭제");
        deleteButton.setOnClickListener(v -> deleteAllData());
        recordLayout.addView(deleteButton);
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
