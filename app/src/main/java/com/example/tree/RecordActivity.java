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

        // 데이터 삭제 버튼 생성
        Button deleteButton = new Button(this);
        deleteButton.setText("데이터 삭제");
        deleteButton.setOnClickListener(v -> deleteAllData());
        recordLayout.addView(deleteButton);
    }
    // 데이터 삭제 메서드 구현
    private void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("records", null, null);
        db.close(); // 데이터베이스 연결 닫기
        recreate(); // 현재 액티비티 다시 생성하여 변경 사항 반영
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close(); // 액티비티가 종료될 때 dbHelper 닫기
    }
}
