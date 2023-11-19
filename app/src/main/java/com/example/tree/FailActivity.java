package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class FailActivity extends AppCompatActivity {

    TextView txt_fail, txt_tryAgain;
    Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);


        txt_fail=findViewById(R.id.txt_fail);
        txt_tryAgain=findViewById(R.id.txt_tryAgain);
        btn_home=findViewById(R.id.btn_home);

        Animation animScale= AnimationUtils.loadAnimation(this, R.anim.anim_bboyong);
        Animation animTilting=AnimationUtils.loadAnimation(this, R.anim.anim_tilting);
        txt_fail.startAnimation(animTilting);
        txt_tryAgain.startAnimation(animScale);
        
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain=new Intent(FailActivity.this, MainActivity.class);
                startActivity(toMain);
            }
        });

    }
}