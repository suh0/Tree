package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    }
}