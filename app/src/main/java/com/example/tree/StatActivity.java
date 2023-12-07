package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StatActivity extends AppCompatActivity {

    ImageView btn_back, btn_log;
    TextView txt_totalTime, txt_totalSuccess, txt_totalFailure;
    RecordDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        btn_back=findViewById(R.id.btn_back);
        btn_log=findViewById(R.id.btn_log);
        txt_totalTime=findViewById(R.id.txt_totalTime);
        txt_totalSuccess=findViewById(R.id.txt_totalSuccess);
        txt_totalFailure=findViewById(R.id.txt_totalFailure);
        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        dbHelper = new RecordDatabaseHelper(this);

        int totalTime = getTotalDuration();
        txt_totalTime.setText(String.valueOf(totalTime));

        int totalRecords = getTotalRecordsCount();
        txt_totalSuccess.setText(String.valueOf(totalRecords));

        int totalFailure = getTotalFailureCount();
        txt_totalFailure.setText(String.valueOf(totalFailure));

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain=new Intent(StatActivity.this, MainActivity.class);
                startActivity(toMain);
                overridePendingTransition(R.anim.anim_left_enter, R.anim.anim_right_exit);
            }
        });

        btn_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_log.startAnimation(animButtonEffect);
                Intent toLog=new Intent(StatActivity.this, LogActivity.class);
                startActivity(toLog);
            }
        });
    }

    private int getTotalDuration() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        int totalTime = 0;
        Cursor cursor = db.rawQuery("SELECT SUM(duration) FROM records", null);

        if (cursor != null && cursor.moveToFirst()) {
            totalTime = cursor.getInt(0); // 첫 번째 열의 값을 가져옴
            cursor.close();
        }

        db.close();
        return totalTime;
    }

    private int getTotalRecordsCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = 0;

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM records", null);

        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return count;
    }

    private int getTotalFailureCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int totalFailure = 0;

        Cursor cursor = db.rawQuery("SELECT SUM(isFailure) FROM failures", null);

        if (cursor != null && cursor.moveToFirst()) {
            totalFailure = cursor.getInt(0);
            cursor.close();
        }

        db.close();
        return totalFailure;
    }
}