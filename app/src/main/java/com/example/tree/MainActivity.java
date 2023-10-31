package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button test;
    ImageView room[] = new ImageView[25];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=0; i<25; i++){
            // xml 파일의 레이아웃과 room 배열의 원소들과 바인딩
            room[i]=findViewById(getResources().getIdentifier("room"+ i, "id", "com.example.tree"));

        }

        // 2023.10.12 버튼 클릭 시 5개의 공간 중 무작위의 공간에 나무 이미지 생성 (이미지 설정, visible로 변경)
        //      10.15 버튼 클릭 시 한 번에 촥
        test=findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int room_num, which_tree, tree_num;
                Random random= new Random();
                tree_num=8; // 상수로 지정, 테스트용, 얼마나 심을지

                for(int count=0; count<tree_num; ){ // 중복 처리 때문에 바로 10개가 안 심어짐 >> 문제 해결!
                    room_num=random.nextInt(10); // 어디에 심을지
                    which_tree=random.nextInt(5); // 어떤 품종 심을지 결정. 테스트용


                    if(room[room_num].getVisibility()==View.INVISIBLE){
                        count++;
                        // which_tree 값에 따라 심는 품종 달라짐
                        switch(which_tree){
                            case 0:
                                room[room_num].setImageResource(R.drawable.tree_26_shadow);
                                break;

                            case 1:
                                room[room_num].setImageResource(R.drawable.tree_28_shadow);
                                break;

                            case 2:
                                room[room_num].setImageResource(R.drawable.tree_29_shadow);
                                break;

                            case 3:
                                room[room_num].setImageResource(R.drawable.tree_32_shadow);
                                break;

                            case 4:
                                room[room_num].setImageResource(R.drawable.tree_36);
                                break;
                        }
                        room[room_num].setVisibility(View.VISIBLE);
                    }

                }


            }
        });
    }
}