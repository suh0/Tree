package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RecordDatabaseHelper dbHelper;
    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

    ImageView btn_timer, btn_record, btn_shop;

    TextView txt_bgm, txt_money;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new RecordDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);
        int randomIndex = cursor.getColumnIndex("random");
        int hourNumIndex = cursor.getColumnIndex("hourNum");
        int treeIndexIndex = cursor.getColumnIndex("treeIndex");

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
        while (cursor.moveToNext()) {
            int random = cursor.getInt(randomIndex);
            int hourNum = cursor.getInt(hourNumIndex);
            int treeIndex = cursor.getInt(treeIndexIndex);

            if (random != 0) {
                room[random-1].setImageResource(treeImages[hourNum - 1][treeIndex - 1]);
                room[random-1].setVisibility(View.VISIBLE);
            }
        } cursor.close();
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

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
