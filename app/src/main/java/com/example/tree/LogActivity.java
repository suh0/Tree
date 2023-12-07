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

    ArrayList<ItemLog> itemList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        btn_back=findViewById(R.id.btn_back);
        btn_clear=findViewById(R.id.btn_clear);
        recycle_log=findViewById(R.id.recycle_log);

        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        LinearLayoutManager logManager=new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycle_log.setLayoutManager(logManager);
        LogAdapter logAdapter=new LogAdapter(this, itemList);

        for(int i=0; i<20; i++){ // 테스트용 더미데이터
            logAdapter.addItem(new ItemLog("2023/12/"+i,95+i));
        }
        recycle_log.setAdapter(logAdapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toStat=new Intent(LogActivity.this, StatActivity.class);
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