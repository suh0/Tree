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

public class SelectHour extends AppCompatActivity {

    private Spinner hoursSpinner;
    private Button confirmButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hour);

        Intent intent = getIntent();

        hoursSpinner = findViewById(R.id.hoursSpinner);
        confirmButton = findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedHours = Integer.parseInt(hoursSpinner.getSelectedItem().toString().replace("시간", "")); 
                //hoursSpinner.getSelectedItem() : 스피너에서 현재 선택된 아이템을 가져오기. 이 경우에는 사용자가 선택한 시간이 선택됨.
                //toString() : 선택된 아이템을 문자열로 변환. 이렇게 하면 선택된 시간이 문자열 형태로 저장됨.
                //replace("Hours", "") : 이 부분에서 "Hours"라는 문자열을 빈 문자열로 대체. 즉, "2 Hours"에서 "Hours"를 제거하면 "2 "만 남음.
                //Integer.parseInt(...): 남은 문자열 '2'를 정수로 변환.
                Intent intent = new Intent(SelectHour.this, TimerActivity.class);
                intent.putExtra("selected_hours", selectedHours);   //위에서 초기화 한 정수 넘기기

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
