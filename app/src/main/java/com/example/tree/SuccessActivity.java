package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;
import static com.example.tree.MainActivity.mediaPlayer;
import static com.example.tree.MainActivity.mediaPlayer06;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.tree.MainActivity;

public class SuccessActivity extends AppCompatActivity {

    LottieAnimationView light_lottie;
    TextView txt_success;
    ImageView img_tree;
    ImageView btn_home;
    TextView txt_addMoney;

    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };
    private int receivedHourNumber;
    private int receivedTreeIndex;

    private int pausedPosition = 0; // 멈춘 위치 저장하는 변수 추가


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        light_lottie=findViewById(R.id.lottie_light);
        img_tree=findViewById(R.id.img_tree);
        txt_success=findViewById(R.id.txt_success);
        btn_home=findViewById(R.id.btn_home);
        txt_addMoney=findViewById(R.id.txt_addMoney);

        Animation animScale= AnimationUtils.loadAnimation(this, R.anim.anim_bboyong);
        Animation animTilting=AnimationUtils.loadAnimation(this, R.anim.anim_tilting);
        Animation animButtonScale=AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        receivedHourNumber = getIntent().getIntExtra("currentHourNumber", 1); // 1은 기본값
        receivedTreeIndex = getIntent().getIntExtra("currentTreeIndex", 1); // 1은 기본값
        img_tree.setImageResource(treeImages[receivedHourNumber - 1][receivedTreeIndex - 1]);     //심은 나무에 따라 이미지 다르게 지정

        //txt_addMoney.setText("+ $ "+money);   나무에 따라 돈 얼마나 추가되는지 다르게 표시

        img_tree.startAnimation(animScale);
        txt_success.startAnimation(animTilting);




        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mediaPlayer06.stop(); // 음악 정지
                btn_home.startAnimation(animButtonScale);
                if (mediaPlayer06 != null && mediaPlayer06.isPlaying()) {
                    pausedPosition = mediaPlayer06.getCurrentPosition(); // 현재 재생 위치 저장
                    mediaPlayer06.pause();
                }
                Intent toMain=new Intent(SuccessActivity.this, MainActivity.class);
                //toMain.putExtra("volume_music06", 0.0f);
                toMain.putExtra("paused_position", pausedPosition); // 멈춘 위치를 Intent에 추가
                startActivity(toMain);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}