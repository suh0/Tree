package com.example.tree;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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

        int randomValue = 0;
        int maxRandomCount = 3;
        boolean isDuplicate = false;

        //random값 만들기
        for (int randomCount = 0; randomCount < maxRandomCount; randomCount++) {
            randomValue = generateRandomValue(); // 랜덤 값 생성

            if (isRandomValueExistsInDB(db, randomValue)) {
                isDuplicate = true; // 중복 발생
            } else {
                isDuplicate = false; // 중복 없음
                break; // 중복이 없으면 반복문 종료
            }
        }
        if (isDuplicate) {
            randomValue = 0; // 중복이 발생했을 때 randomValue를 0으로 설정
        }

        values.put("random", randomValue);
        db.insert("records", null, values);
        db.close();
    }
    private int generateRandomValue() {
        // 1부터 25 사이의 랜덤 값을 생성하여 반환합니다.
        return (int) (Math.random() * 3) + 1; //1~3
    }

    private boolean isRandomValueExistsInDB(SQLiteDatabase db, int randomValue) {
        // DB에서 동일한 랜덤 값이 있는지 확인하는 메서드
        // 만약 있으면 true, 없으면 false 반환
        // 여기서는 "records" 테이블에서 "random" 열을 조사합니다.
        String query = "SELECT * FROM records WHERE random = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(randomValue)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
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
