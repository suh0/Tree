package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class LogActivity extends AppCompatActivity {

    ImageView btn_back, btn_clear;
    RecyclerView recycle_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        btn_back=findViewById(R.id.btn_back);
        btn_clear=findViewById(R.id.btn_clear);
        recycle_log=findViewById(R.id.recycle_log);

        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

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