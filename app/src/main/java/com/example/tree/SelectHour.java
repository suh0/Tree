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
                String selectedTime = hoursSpinner.getSelectedItem().toString();

                long selectedMilliseconds;
                if (selectedTime.equals("5초")) {
                    selectedMilliseconds = 5000; // 5초는 5000밀리초
                } else {
                    int selectedHours = Integer.parseInt(selectedTime.replace("시간", ""));
                    selectedMilliseconds = selectedHours * 60 * 60 * 1000;
                }

                Intent intent = new Intent(SelectHour.this, TimerActivity.class);
                intent.putExtra("selected_milliseconds", selectedMilliseconds); // 변경된 부분

                startActivityForResult(intent, 1);
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