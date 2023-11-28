package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView btn_timer, btn_user, btn_shop;
    Button record_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_timer = findViewById(R.id.btn_timer);
        record_btn = findViewById(R.id.record_btn); // 버튼 참조
        btn_shop = findViewById(R.id.btn_shop);

        Animation animButtonEffect=AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_timer.startAnimation(animButtonEffect);
                Intent intent = new Intent(MainActivity.this , SelectHour.class);
                startActivity(intent);
            }
        });

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_shop.startAnimation(animButtonEffect);
                Intent intent = new Intent(MainActivity.this , ShopActivity.class);
                startActivity(intent);
            }
        });

        btn_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_user.startAnimation(animButtonEffect);
            }
        });
    }
}
