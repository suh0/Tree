package com.example.main_timer;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private long timeCountInMilliSeconds = 60 * 60 *1000; //1시간에 해당하는 밀리초
    private ImageView imageViewStart;


    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private Button exitButton;

    private ProgressBar progressBarCircle;
    private TextView timerTextView;
    private CountDownTimer countDownTimer;
    private RecordDatabaseHelper dbHelper;
    private long selectedMilliseconds = 0;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_timer);

        timerTextView = findViewById(R.id.timerTextView);
        imageViewStart = findViewById(R.id.imageViewStart);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        exitButton = findViewById(R.id.exitButton);
        imageViewStart.setOnClickListener(this);

        dbHelper = new RecordDatabaseHelper(this);


       //selectedMilliseconds = getIntent().getLongExtra("selected_milliseconds", 3600000);

        Spinner spinner = findViewById(R.id.spinnerTime);

        //문자열 배열 리소스를 가져와서 어댑터에 연결
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.time_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                String selectedTime = parentView.getItemAtPosition(position).toString();
                String formattedTime = formatTime(selectedTime);
                timerTextView.setText(formattedTime);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        });

        // 뷰 초기화
        initViews();

        // 리스너 초기화
        initListeners();

    }

    private String formatTime(String selectedTime) {
        if (selectedTime.equals("1시간")) {
            return "01:00:00";
        } else if (selectedTime.equals("2시간")) {
            return "02:00:00";
        } else if (selectedTime.equals("3시간")) {
            return "03:00:00";
        } else if (selectedTime.equals("5초")) {
            return "00:00:05";
        } else {
            return ""; // 기본값
        }

    }

    /**
     * 뷰 초기화
     */
    private void initViews() {
        progressBarCircle.setMax((int) timeCountInMilliSeconds / 1000);
        progressBarCircle.setProgress((int) timeCountInMilliSeconds / 1000);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageViewStart) {
            if (timerStatus == TimerStatus.STOPPED) {
                startTimer();
            }
        }
    }

    private void startTimer() {
        if (timerStatus == TimerStatus.STOPPED) {
            long selectedMilliseconds = timeCountInMilliSeconds; // 초기 값은 timeCountInMilliSeconds

            // 스피너에서 선택한 시간에 따라 타이머 시간 설정
            Spinner spinner = findViewById(R.id.spinnerTime);
            String selectedTime = spinner.getSelectedItem().toString();
            if (selectedTime.equals("1시간")) {
                selectedMilliseconds = 1 * 60 * 60 * 1000; // 1시간
            } else if (selectedTime.equals("2시간")) {
                selectedMilliseconds = 2 * 60 * 60 * 1000; // 2시간
            } else if (selectedTime.equals("3시간")) {
                selectedMilliseconds = 3 * 60 * 60 * 1000; // 3시간
            } else if (selectedTime.equals("5초")) {
                selectedMilliseconds = 5*1000;

            }
            //Log.d("Timer", "Selected Milliseconds: " + selectedMilliseconds); // 로그 추가


        timeCountInMilliSeconds = selectedMilliseconds;
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                String endTime = getCurrentDate();

                timerStatus = TimerStatus.STOPPED;
                timerTextView.setText("성공!");
                Log.d("MyTimer", "onFinish called");
                saveRecord();
               // returnToMainScreen();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //timerTextView.setText("성공!");
                        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }, 2000);
                //updateTimer(0); 이거 쓰면 성공 안뜸

            }
        }.start();
            exitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countDownTimer.cancel();
                    finishWithFailure();
                }
            });

        timerStatus = TimerStatus.STARTED;
    }
 }


    /**
     * 리스너 초기화
     */
    private void initListeners() {
        imageViewStart.setOnClickListener(this);

    }

    private void updateTimer(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
        timerTextView.setText(time);
        progressBarCircle.setProgress(seconds);
    }

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
        db.insert("records", null, values);
        db.close();
        Log.d("SaveRecord", "Record saved: date=" + getCurrentDate() + ", duration=" + selectedMilliseconds);
    }
    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private void finishWithFailure() {
        Intent intent = new Intent();
        intent.putExtra("result", "실패하였습니다!");
        setResult(RESULT_OK, intent);
        finish();
    }

    private void returnToMainScreen() {
        Intent intent = new Intent(TimerActivity.this, TimerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}


































