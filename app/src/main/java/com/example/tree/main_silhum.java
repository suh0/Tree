package com.example.tree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import java.util.Random;

public class main_silhum extends AppCompatActivity {

    private Button test;
    ImageView room[]=new ImageView[10]; // room[] : 나무가 들어갈 이미지뷰들의 배열 (지금은 테스트로 10개만)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_silhum);

        for(int i=0; i<10; i++){
            // xml 파일의 레이아웃과 room 배열의 원소들과 바인딩
            room[i]=findViewById(getResources().getIdentifier("room"+ i, "id", "com.example.tree"));
            // 2023.10.12 xml 파일에 room1~5로 id를 정해서 getIdentifier("room"+ i+1, ~~~) 라고 작성했을 때, 제대로 바인딩 되지 않아(null값) 40라인에서 문제발생. >> 수정완료
            // 아직 아무 나무도 생성되지 않음 >> 이미지뷰가 모두 보이지 않도록 처리
        }


        // 2023.10.12 버튼 클릭 시 5개의 공간 중 무작위의 공간에 나무 이미지 생성 (이미지 설정, visible로 변경)
        //      10.15 버튼 클릭 시 한 번에 촥
        test=findViewById(R.id.add_tree);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int room_num, which_tree;
                Random random= new Random();

                for(int i=0; i<9; i++){
                    room_num=random.nextInt(10); // 어디에 심을지
                    which_tree=random.nextInt(5); // 어떤 품종 심을지 결정. 테스트용
                    if(room[room_num].getVisibility()==View.INVISIBLE){

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

/*
* 1. 버튼은 DB 구축 이후 없애야함
*
*
* 4. 뷰 크기를 수치로 고정해둬서 눈에 보이는 나무 크기가 들쑥날쑥 할 수 있음. >>
*
* */