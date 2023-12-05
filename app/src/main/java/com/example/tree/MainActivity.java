package com.example.tree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecordDatabaseHelper dbHelper;
    public CoinDatabaseHelper coinHelper;

    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

    ImageView btn_timer, btn_record, btn_shop;
    ImageView img_frac1, img_frac2, img_frac3, img_frac4, img_cloud1;
    TextView txt_bgm, txt_money;
    TextView txtDate;
    ConstraintLayout img_board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDate = findViewById(R.id.txt_date);
        SimpleDateFormat sdf = new SimpleDateFormat("M월 d일", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        txtDate.setText(currentDate);

        dbHelper = new RecordDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);
        int randomIndex = cursor.getColumnIndex("random");
        int hourNumIndex = cursor.getColumnIndex("hourNum");
        int treeIndexIndex = cursor.getColumnIndex("treeIndex");

        coinHelper = new CoinDatabaseHelper(this);

        ImageView room[] = new ImageView[25];
        img_frac1=findViewById(R.id.img_frac1);
        img_frac2=findViewById(R.id.img_frac2);
        img_frac3=findViewById(R.id.img_frac3);
        img_frac4=findViewById(R.id.img_frac4);
        btn_timer = findViewById(R.id.btn_timer);
        btn_record = findViewById(R.id.btn_record);
        btn_shop = findViewById(R.id.btn_shop);
        txt_bgm=findViewById(R.id.txt_bgm);
        txt_money=findViewById(R.id.txt_money);
        img_board=findViewById(R.id.img_board);
        img_cloud1=findViewById(R.id.img_cloud1);
        updateMoney(); // 돈 업데이트

        Animation animFrac1=AnimationUtils.loadAnimation(this, R.anim.anim_frac);
        Animation animFrac2=AnimationUtils.loadAnimation(this, R.anim.anim_frac2);
        Animation animFrac3=AnimationUtils.loadAnimation(this, R.anim.anim_frac3);
        Animation animBoard=AnimationUtils.loadAnimation(this, R.anim.anim_board);
        Animation animCloudBottom=AnimationUtils.loadAnimation(this, R.anim.anim_cloud);
        img_frac1.startAnimation(animFrac1);
        img_frac2.startAnimation(animFrac2);
        img_frac3.startAnimation(animFrac3);
        img_frac4.startAnimation(animFrac2);
        img_board.startAnimation(animBoard);
        img_cloud1.startAnimation(animCloudBottom);



        for(int i=0; i<25; i++){
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
        }
        cursor.close();

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
    protected void onResume() {
        super.onResume();
        updateMoney(); // 액티비티가 다시 시작될 때 돈 업데이트
    }

    private void updateMoney() {
        txt_money.setText(" " + coinHelper.getCurrentBalance());
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity(); // 모든 액티비티 종료
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); // 대화상자 닫기
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
