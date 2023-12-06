package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class StatActivity extends AppCompatActivity {

    ImageView btn_back, btn_log;
    TextView txt_totalTime, txt_totalSuccess, txt_totalFailure, txt_totalMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        btn_back=findViewById(R.id.btn_back);
        btn_log=findViewById(R.id.btn_log);
        txt_totalTime=findViewById(R.id.txt_totalTime);
        txt_totalSuccess=findViewById(R.id.txt_totalSuccess);
        txt_totalFailure=findViewById(R.id.txt_totalFailure);
        txt_totalMoney=findViewById(R.id.txt_totalMoney);
        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

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
}