package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class FailActivity extends AppCompatActivity {

    TextView txt_fail, txt_tryAgain;
    ImageView btn_home;
    private RecordDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail);

        txt_fail=findViewById(R.id.txt_fail);
        txt_tryAgain=findViewById(R.id.txt_tryAgain);
        btn_home=findViewById(R.id.btn_home);

        Animation animScale= AnimationUtils.loadAnimation(this, R.anim.anim_bboyong);
        Animation animTilting=AnimationUtils.loadAnimation(this, R.anim.anim_tilting);
        Animation animButtonScale=AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);
        txt_fail.startAnimation(animTilting);
        txt_tryAgain.startAnimation(animScale);

        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_home.startAnimation(animButtonScale);
                Intent toMain=new Intent(FailActivity.this, MainActivity.class);
                startActivity(toMain);
            }
        });
        dbHelper = new RecordDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("isFailure", 1);
        db.insert("failures", null, values);
        db.close();


    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}