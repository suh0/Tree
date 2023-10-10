package com.example.tree;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button show1h;
    Button show2h;
    Button show3h;
    ImageView treeImage;
    TextView treeInfo;
    Button prev;
    Button next;
    Button confirm;

    //index는 1부터
    int currentHour = 1;
    int currentTreeIndex = 1;
    final int max_1h = 3;
    final int max_2h = 3;
    final int max_3h = 3;

    // 화면 업데이트
    void updateTree()
    {
        switch(currentHour)
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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show1h = findViewById(R.id.show1h);
        show2h = findViewById(R.id.show2h);
        show3h = findViewById(R.id.show3h);
        treeImage = (ImageView) findViewById(R.id.treeImage);
        treeInfo = findViewById(R.id.treeInfo);
        prev = findViewById(R.id.prev);
        next = findViewById(R.id.next);
        confirm = findViewById(R.id.confirm);
        updateTree();

        // 시간 선택
        show1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour = 1; updateTree(); }
        });
        show2h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour = 2; updateTree(); }
        });
        show3h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { currentHour = 3; updateTree(); }
        });

        // '이전' 버튼
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(currentTreeIndex <= 1)
                {
                    if(currentHour == 1)
                        currentTreeIndex = max_2h;
                    else if(currentHour == 2)
                        currentTreeIndex = max_2h;
                    else if(currentHour == 3)
                        currentTreeIndex = max_3h;
                }
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
                if(currentHour == 1 && currentTreeIndex >= max_1h)
                    currentTreeIndex = 1;
                else if(currentHour == 2 && currentTreeIndex >= max_2h)
                    currentTreeIndex = 1;
                else if(currentHour == 3 && currentTreeIndex >= max_3h)
                    currentTreeIndex = 1;
                else
                    currentTreeIndex++;

                updateTree();
            }
        });
    }

}