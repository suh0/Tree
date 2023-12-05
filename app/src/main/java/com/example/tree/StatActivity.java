package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StatActivity extends AppCompatActivity {

    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        btn_back=findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toMain=new Intent(StatActivity.this, MainActivity.class);
                startActivity(toMain);
                overridePendingTransition(R.anim.anim_left_enter, R.anim.anim_right_exit);
            }
        });
    }
}