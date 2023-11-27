package com.example.tree;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
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

        if (dateIndex >= 0 && durationIndex >= 0) {
            while (cursor.moveToNext()) {
                String date = cursor.getString(dateIndex);
                long duration = cursor.getLong(durationIndex);

                TextView recordTextView = new TextView(this);
                recordTextView.setText("날짜: " + date + ", 시간: " + duration + " 밀리초");
                recordLayout.addView(recordTextView);
            }
        }

        cursor.close();
        dbHelper.close();

        // 데이터 삭제 버튼 추가
        Button deleteDataButton = new Button(this);
        deleteDataButton.setText("데이터 삭제");
        deleteDataButton.setOnClickListener(view -> deleteAllData());
        recordLayout.addView(deleteDataButton);
    }
    private void deleteAllData() {
        dbHelper = new RecordDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("records", null, null);
        dbHelper.close();

        // 데이터 삭제 후 화면 갱신
        refreshRecordLayout();
    }

    private void refreshRecordLayout() {
        LinearLayout recordLayout = findViewById(R.id.recordLayout);
        recordLayout.removeAllViews(); // 기존 데이터 모두 제거

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);

        int dateIndex = cursor.getColumnIndex("date");
        int durationIndex = cursor.getColumnIndex("duration");

        if (dateIndex >= 0 && durationIndex >= 0) {
            while (cursor.moveToNext()) {
                String date = cursor.getString(dateIndex);
                long duration = cursor.getLong(durationIndex);

                TextView recordTextView = new TextView(this);
                recordTextView.setText("날짜: " + date + ", 시간: " + duration + " 밀리초");
                recordLayout.addView(recordTextView);
            }
        }

        cursor.close();
        dbHelper.close();
    }
}
