package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    ImageView img_logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        img_logo=findViewById(R.id.img_logo);
        Animation splashLogo = AnimationUtils.loadAnimation(this, R.anim.anim_splash_logo);
        img_logo.startAnimation(splashLogo);

        toLogin(2);
    }

    private void toLogin(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //new Intent(현재 context, 이동할 activity)
                Intent toLogin = new Intent(getApplicationContext(), LoginActivity.class);

                startActivity(toLogin);	//intent 에 명시된 액티비티로 이동
                finish();	//현재 액티비티 종료
                overridePendingTransition(R.anim.anim_fadein, R.anim.anim_fadeout); // 화면 전환시 효과
            }
        }, 1000 * sec); // sec초 정도 딜레이를 준 후 시작
    }
}