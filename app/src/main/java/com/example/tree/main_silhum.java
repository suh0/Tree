package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class main_silhum extends AppCompatActivity {

    private Button add_tree;
    ImageView room[]=new ImageView[5]; // room[] : 나무가 들어갈 이미지뷰들의 배열 (지금은 테스트로 5개만)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_silhum);

        for(int i=0; i<5; i++){
            // xml 파일의 레이아웃과 room 배열의 원소들과 바인딩
            room[i]=(ImageView) findViewById(getResources().getIdentifier("room"+ i, "id", "com.example.a1011_2236"));
            // 2023.10.12 xml 파일에 room1~5로 id를 정해서 getIdentifier("room"+ i+1, ~~~) 라고 작성했을 때, 제대로 바인딩 되지 않아(null값) 40라인에서 문제발생. >> 수정완료
            // 아직 아무 나무도 생성되지 않음 >> 이미지뷰가 모두 보이지 않도록 처리
        }

        // 버튼 클릭 시 5개의 공간 중 무작위의 공간에 나무 이미지 생성 (이미지 설정, visible로 변경)
        add_tree=findViewById(R.id.add_tree);
        add_tree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int room_num;
                Random random= new Random();
                room_num=random.nextInt(5); // 1~5 범위의 난수 생성

                // 중복된 자리 처리 필요

                room[room_num].setImageResource(R.drawable.tree_29_shadow);
                room[room_num].setVisibility(View.VISIBLE);

            }
        });
    }
}

/*
* 1. 버튼은 DB 구축 이후 없애야함
* 2. 사용자가 심은 나무에 따라 40라인에 이미지를 다르게 지정해줘야함
* 3. 중복된 자리는 안 들어가게
* 4. 뷰 크기를 수치로 고정해둬서 눈에 보이는 나무 크기가 들쑥날쑥 할 수 있음. >>
*
* */