package com.example.tree;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private Button exitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerTextView = findViewById(R.id.timerTextView);
        exitButton = findViewById(R.id.exitButton);

        int selectedHours = getIntent().getIntExtra("selected_hours", 1);

        long millisInFuture = selectedHours * 60 * 60 * 1000;

        countDownTimer = new CountDownTimer(millisInFuture, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                long minutes = seconds / 60;
                long hours = minutes / 60;

                String time = String.format("%02d:%02d:%02d", hours % 24, minutes % 60, seconds % 60);
                timerTextView.setText(time);
            }

            @Override
            public void onFinish() {
                timerTextView.setText("타이머 종료");
            }
        };

        countDownTimer.start();

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishWithFailure(); // 종료 버튼 클릭 시 실패 메시지 출력 및 종료
            }
        });
    }

    private void finishWithFailure() {
        Intent intent = new Intent();
        intent.putExtra("result", "실패하였습니다!");
        setResult(RESULT_OK, intent);
        finish();
    }
}
