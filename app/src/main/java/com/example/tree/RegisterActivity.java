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

public class RegisterActivity extends AppCompatActivity {

    ImageView btn_register, btn_back;
    TextInputEditText txt_input_pwd, txt_input_username, txt_input_check_pwd;
    String input_pwd, input_username, input_check_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register=findViewById(R.id.btn_register);
        txt_input_pwd=findViewById(R.id.txt_input_pwd);
        txt_input_username=findViewById(R.id.txt_input_username);
        txt_input_check_pwd=findViewById(R.id.txt_input_check_pwd);
        btn_back=findViewById(R.id.btn_back);
        Animation animButtonScale= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);
        Animation animButtonFail=AnimationUtils.loadAnimation(this, R.anim.anim_no_money);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input_pwd=txt_input_pwd.getText().toString();
                input_username=txt_input_username.getText().toString();
                input_check_pwd=txt_input_check_pwd.getText().toString();

                //overridePendingTransition(R.anim.anim_right_enter, R.anim.anim_none);

                if((input_pwd.equals(input_check_pwd))&&!(input_username.isEmpty())&&!(input_pwd.isEmpty())){ // 공백 없고 비밀번호 확인 제대로 쳤을 때
                    btn_register.startAnimation(animButtonScale);
                    // 뭔가 동작
                    Intent toMain=new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(toMain);

                }
                else{
                    btn_register.startAnimation(animButtonFail);
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin=new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });

    }
}