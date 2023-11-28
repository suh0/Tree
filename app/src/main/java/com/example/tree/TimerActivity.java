package com.example.tree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private Button exitButton;
    private RecordDatabaseHelper dbHelper;
    private long selectedMilliseconds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerTextView = findViewById(R.id.timerTextView);
        exitButton = findViewById(R.id.exitButton);

        dbHelper = new RecordDatabaseHelper(this);

        selectedMilliseconds = getIntent().getLongExtra("selected_milliseconds", 1000);

        countDownTimer = new CountDownTimer(selectedMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;

                String time = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
                timerTextView.setText(time);
            }

            @Override
            public void onFinish() {
                //timerTextView.setText("성공!");
                saveRecord();
                //returnToMainScreen();
                goToSuccessActivity();
            }
        };

        countDownTimer.start();

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finishWithFailure();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel(); // 액티비티 종료 시 타이머 초기화
        dbHelper.close();
    }

    private void saveRecord() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", getCurrentDate());
        values.put("duration", selectedMilliseconds);
        values.put("random", 1); // 랜덤 값을 생성하여 'random' 열에 넣습니다.

        db.insert("records", null, values);
        db.close();
    }

    private int generateRandomValue() {
        // 랜덤한 값을 생성하여 반환하는 메서드를 만듭니다.
        // 여기서는 1부터 10 사이의 랜덤 값을 생성하도록 하겠습니다.
        return (int) (Math.random() * 10) + 1;
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void finishWithFailure() {
        //Intent intent = new Intent();
        //intent.putExtra("result", "실패하였습니다!");
        //setResult(RESULT_OK, intent);
        goToFailActivity();
        finish();

    }

    private void returnToMainScreen() {
        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void goToSuccessActivity(){
        Intent toSuccess=new Intent(TimerActivity.this, SuccessActivity.class);
        startActivity(toSuccess);
    }

    private void goToFailActivity(){
        Intent toFail=new Intent(TimerActivity.this, FailActivity.class);
        startActivity(toFail);
    }
}
