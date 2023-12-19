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
    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

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
        int hourNumIndex = cursor.getColumnIndex("hourNum");
        int treeIndexIndex = cursor.getColumnIndex("treeIndex");



        while (cursor.moveToNext()) {
            String date = cursor.getString(dateIndex);
            long duration = cursor.getLong(durationIndex);
            int random = cursor.getInt(randomIndex);
            int hourNum = cursor.getInt(hourNumIndex);
            int treeIndex = cursor.getInt(treeIndexIndex);

            TextView recordTextView = new TextView(this);
            recordTextView.setText("날짜: " + date + ", 시간: " + duration + " 초, 랜덤: "
                    + random + ", hourNum: " + hourNum + ", treeIndex: " + treeIndex);
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
