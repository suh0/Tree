package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    ImageView btn_login, btn_register;
    String input_username, input_pwd;

    TextInputEditText txt_input_username, txt_input_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login=findViewById(R.id.btn_login);
        btn_register=findViewById(R.id.btn_register);
        txt_input_username=findViewById(R.id.txt_input_username);
        txt_input_pwd=findViewById(R.id.txt_input_pwd);
        Animation animButtonScale= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btn_login.startAnimation(animButtonScale);
                input_username=txt_input_username.getText().toString();
                input_pwd=txt_input_pwd.getText().toString(); // 입력받은 문자열 저장
                // db 동작

                Intent toMain=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(toMain);
                
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_register.startAnimation(animButtonScale);
                Intent toRegister=new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(toRegister);
                overridePendingTransition(R.anim.anim_left_exit, R.anim.anim_right_enter);
                finish();
            }
        });
    }
}