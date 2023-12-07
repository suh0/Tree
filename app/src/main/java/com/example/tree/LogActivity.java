package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.ArrayList;

public class LogActivity extends AppCompatActivity {

    private ImageView btn_back, btn_clear;
    private RecyclerView recycle_log;

    ArrayList<ItemLog> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        btn_back = findViewById(R.id.btn_back);
        btn_clear = findViewById(R.id.btn_clear);
        recycle_log = findViewById(R.id.recycle_log);

        Animation animButtonEffect = AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        LinearLayoutManager logManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycle_log.setLayoutManager(logManager);
        LogAdapter logAdapter = new LogAdapter(this, itemList);

        RecordDatabaseHelper dbHelper = new RecordDatabaseHelper(this);
        ArrayList<ItemLog> records = dbHelper.getRecords();

        if (records.isEmpty()) {
            // 데이터베이스에서 아무 데이터도 가져오지 못한 경우에 대한 처리 (예: 메시지 표시)
            // 예시: itemList에 기본값으로 더미 데이터 추가
            itemList.add(new ItemLog("날짜 없음", 0));
        } else {
            for (ItemLog record : records) {
                logAdapter.addItem(record);
            }
        }

        recycle_log.setAdapter(logAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toStat = new Intent(LogActivity.this, StatActivity.class);
                startActivity(toStat);
                overridePendingTransition(R.anim.anim_left_enter, R.anim.anim_right_exit);
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_clear.startAnimation(animButtonEffect);
                // 데이터 삭제 코드
            }
        });
    }
}
