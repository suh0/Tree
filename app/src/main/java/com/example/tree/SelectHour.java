package com.example.tree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class SelectHour extends AppCompatActivity {

    Button show5s;
    ImageView show30m;
    ImageView show1h;
    ImageView show2h;
    ImageView treeImage;
    TextView treeInfo;
    ImageView prev;
    ImageView next;
    ImageView confirm;
    ImageView btn_back;


    //index는 1부터
    int currentHour_number = 1;
    int currentTreeIndex = 1;
    final int max_tree_index = 3;

    // dbHelper
    RecordDatabaseHelper dbHelper;

    // 화면 업데이트
    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}, //5초 test
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

    String[][] treeTexts = {
            {"나무 1-1", "나무 1-2", "나무 1-3"}, //5초
            {"+ $100", "+ $100", "+ $100"}, //30분
            {"+ $150", "+ $150", "+ $150"}, //1시간
            {"+ $200", "+ $200", "+ $200"} //2시간
    };


    void updateTree() {
        treeImage.setImageResource(treeImages[currentHour_number - 1][currentTreeIndex - 1]);

        //treeImage.setAlpha(0.5f); // 예시: 0.5는 이미지뷰의 투명도를 50%로 설정합니다.
        treeInfo.setText(treeTexts[currentHour_number - 1][currentTreeIndex - 1]);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hour);

        dbHelper = new RecordDatabaseHelper(this);

        show5s = findViewById(R.id.show5s);
        show30m = findViewById(R.id.show30m);
        show1h = findViewById(R.id.show1h);
        show2h = findViewById(R.id.show2h);
        treeImage = (ImageView) findViewById(R.id.treeImage);
        treeInfo = findViewById(R.id.treeInfo);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        confirm = findViewById(R.id.confirm);
        btn_back=findViewById(R.id.btn_back);
        updateTree();

        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toMain=new Intent(SelectHour.this, MainActivity.class);
                startActivity(toMain);
                overridePendingTransition(R.anim.anim_left_enter, R.anim.anim_right_exit);

            }
        });

        // 시간 선택
        show5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 1; updateTree(); }
        });
        show30m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show30m.startAnimation(animButtonEffect);
                currentHour_number = 2; updateTree(); }
        });
        show1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show1h.startAnimation(animButtonEffect);
                currentHour_number = 3; updateTree(); }
        });
        show2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show2h.startAnimation(animButtonEffect);
                currentHour_number = 4; updateTree(); }
        });

        // '이전' 버튼
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                prev.startAnimation(animButtonEffect);
                if(currentTreeIndex <= 1)
                    currentTreeIndex = max_tree_index;
                else
                    currentTreeIndex--;

                updateTree();
            }
        });
        // '다음' 버튼
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                next.startAnimation(animButtonEffect);
                if(currentTreeIndex >= max_tree_index)
                    currentTreeIndex = 1;
                else
                    currentTreeIndex++;

                updateTree();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.startAnimation(animButtonEffect);
                long selected_milliseconds = 0;
                switch (currentHour_number){
                    case 1:
                        selected_milliseconds = 5000; //1000(1초) * 5
                        break;
                    case 2:
                        selected_milliseconds = 1000 * 60 * 30; //30분 1000(1초) * 60(1분) * 30
                        break;
                    case 3:
                        selected_milliseconds = 1000 * 60 * 60; //1시간
                        break;
                    case 4:
                        selected_milliseconds = 1000 * 60 * 60 * 2; //2시간
                        break;
                }

                Intent intent = new Intent(SelectHour.this, TimerActivity.class);
                intent.putExtra("selected_milliseconds", selected_milliseconds); // 변경된 부분
                intent.putExtra("currentHourNumber", currentHour_number);
                intent.putExtra("currentTreeIndex", currentTreeIndex);

                startActivityForResult(intent, 1); //넘기기
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("result");
                if (result.equals("실패하였습니다!")) {
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show(); // 실패 메시지를 토스트로 출력
                }
            }
        }
    }
}