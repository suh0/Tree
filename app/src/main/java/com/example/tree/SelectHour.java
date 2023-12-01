package com.example.tree;

import static com.example.tree.MainActivity.mediaPlayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    Button show30m;
    Button show1h;
    Button show2h;
    ImageView treeImage;
    TextView treeInfo;
    Button prev;
    Button next;
    Button confirm;

    //index는 1부터
    int currentHour_number = 1;
    int currentTreeIndex = 1;
    final int max_tree_index = 3;

    // 볼륨 값 가져오기
    Intent intent = getIntent();
    //float volumeMusic06 = intent.getFloatExtra("volume_music06", 0.0f); // music06의 기본 볼륨은 1.0f (최대 볼륨)

    // 화면 업데이트
    void updateTree()
    {
        switch(currentHour_number)
        {
            case 1:
                if(currentTreeIndex == 1)
                {
                    treeImage.setImageResource(R.drawable.tree_1_1);
                    treeInfo.setText("나무 1-1");
                }
                else if(currentTreeIndex == 2)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 1-2");
                }
                else if(currentTreeIndex == 3)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 1-3");
                }
                break;

            case 2:
                if(currentTreeIndex == 1)
                {
                    treeImage.setImageResource(R.drawable.tree_1_1);
                    treeInfo.setText("나무 2-1");
                }
                else if(currentTreeIndex == 2)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 2-2");
                }
                else if(currentTreeIndex == 3)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 2-3");
                }
                break;

            case 3:
                if(currentTreeIndex == 1)
                {
                    treeImage.setImageResource(R.drawable.tree_1_1);
                    treeInfo.setText("나무 3-1");
                }
                else if(currentTreeIndex == 2)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 3-2");
                }
                else if(currentTreeIndex == 3)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 3-3");
                }
                break;

            case 4:
                if(currentTreeIndex == 1)
                {
                    treeImage.setImageResource(R.drawable.tree_1_1);
                    treeInfo.setText("나무 4-1");
                }
                else if(currentTreeIndex == 2)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 4-2");
                }
                else if(currentTreeIndex == 3)
                {
                    treeImage.setImageResource(R.drawable.tree_1_2);
                    treeInfo.setText("나무 4-3");
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hour);

        show5s = findViewById(R.id.show5s);
        show30m = findViewById(R.id.show30m);
        show1h = findViewById(R.id.show1h);
        show2h = findViewById(R.id.show2h);
        treeImage = (ImageView) findViewById(R.id.treeImage);
        treeInfo = findViewById(R.id.treeInfo);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        confirm = findViewById(R.id.confirm);
        updateTree();

        // 시간 선택
        show5s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 1; updateTree(); }
        });
        show30m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 2; updateTree(); }
        });
        show1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 3; updateTree(); }
        });
        show2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 4; updateTree(); }
        });

        // '이전' 버튼
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
                Log.d("SelectHour", "Confirm button clicked");
                Intent resultIntent = new Intent();
                resultIntent.putExtra("stop_music", true);
                setResult(RESULT_OK, resultIntent);

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
               // mediaPlayer.stop();
                Intent intent = new Intent(SelectHour.this, TimerActivity.class);
                intent.putExtra("selected_milliseconds", selected_milliseconds); // 변경된 부분
                intent.putExtra("stop_music", true);
                startActivityForResult(intent, 1); //넘기기
                finish();

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