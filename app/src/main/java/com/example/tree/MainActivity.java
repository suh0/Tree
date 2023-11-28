package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView btn_timer, btn_record, btn_shop;

    TextView txt_bgm, txt_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView room[] = new ImageView[25];
        btn_timer = findViewById(R.id.btn_timer);
        btn_record = findViewById(R.id.btn_record); // 버튼 참조
        btn_shop = findViewById(R.id.btn_shop);
        txt_bgm=findViewById(R.id.txt_bgm);
        txt_money=findViewById(R.id.txt_money);

        for(int i=0; i<25; i++){
            // xml 파일의 레이아웃과 room 배열의 원소들과 바인딩
            room[i]=findViewById(getResources().getIdentifier("room"+ i, "id", "com.example.tree"));

        }
        // setImageResource() , setVisibility()
        Animation animButtonEffect=AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_timer.startAnimation(animButtonEffect);
                Intent intent = new Intent(MainActivity.this , SelectHour.class);
                startActivity(intent);
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
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

    }
}
