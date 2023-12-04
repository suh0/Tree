package com.example.tree;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
    final int max_tree_index = 4;
    boolean readyToGo = false;

    // dbHelper
    RecordDatabaseHelper dbHelper;
    TreeItemDatabaseHelper treeHelper;

    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

    /*
    String[][] treeTexts = {
            {"나무 1-1", "나무 1-2", "나무 1-3"},
            {"나무 2-1", "나무 2-2", "나무 2-3"},
            {"나무 3-1", "나무 3-2", "나무 3-3"},
            {"나무 4-1", "나무 4-2", "나무 4-3"}
    };
    */

    String[][] treeTexts = {
            {"tree1_5s", "tree2_5s", "tree3_5s", "tree4_5s"},
            {"tree1_30m", "tree2_30m", "tree3_30m", "tree4_30m"},
            {"tree1_1h", "tree2_1h", "tree3_1h", "tree4_1h"},
            {"tree1_2h", "tree2_2h", "tree3_2h", "tree4_2h"},
    };

    void updateTree() {
        if(currentTreeIndex >= 4) {
            treeImage.setImageResource(R.drawable.img_tree2); // placeholder for trees over index 4
        }
        else {
            treeImage.setImageResource(treeImages[currentHour_number - 1][currentTreeIndex - 1]);
        }
        treeInfo.setText(treeTexts[currentHour_number - 1][currentTreeIndex - 1]);
        readyToGo = true;
    }

    void showNoItemAvailable() {
        treeImage.setImageResource(treeImages[0][0]); // placeholder for non-purchased trees
        treeInfo.setText("미보유");
        readyToGo = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hour);

        dbHelper = new RecordDatabaseHelper(this);
        treeHelper = new TreeItemDatabaseHelper(this);

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
            public void onClick(View view) { currentHour_number = 1; currentTreeIndex = 1; updateTree(); }
        });
        show30m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 2; currentTreeIndex = 1; updateTree(); }
        });
        show1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 3; currentTreeIndex = 1; updateTree(); }
        });
        show2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour_number = 4; currentTreeIndex = 1; updateTree(); }
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

                if(treeHelper.getPurchase(treeTexts[currentHour_number-1][currentTreeIndex-1]) == 1)
                    updateTree();
                else
                    showNoItemAvailable();
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

                if(treeHelper.getPurchase(treeTexts[currentHour_number-1][currentTreeIndex-1]) == 1)
                    updateTree();
                else
                    showNoItemAvailable();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!readyToGo) {
                    Toast.makeText(getApplicationContext(), "보유하지 않은 나무입니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    long selected_milliseconds = 0;
                    switch (currentHour_number) {
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