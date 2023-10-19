package com.example.main_timer;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main_TimerActivity extends AppCompatActivity implements View.OnClickListener {

    private long timeCountInMilliSeconds = 60 * 60 *1000; //1시간에 해당하는 밀리초
    private ImageView imageViewStart;


    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    private TimerStatus timerStatus = TimerStatus.STOPPED;
    private Button exitButton;

    private ProgressBar progressBarCircle;
    private TextView textViewTime;
    private CountDownTimer countDownTimer;
    private RecordDatabaseHelper dbHelper;
    private long selectedMilliseconds = 0;

    private NumberPicker hourPicker;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_timer);

        textViewTime = findViewById(R.id.textViewTime);
        imageViewStart = findViewById(R.id.imageViewStart);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        exitButton = findViewById(R.id.exitButton);
        imageViewStart.setOnClickListener(this);

        dbHelper = new RecordDatabaseHelper(this);


        selectedMilliseconds = getIntent().getLongExtra("selected_milliseconds", 3600000);

        countDownTimer = new CountDownTimer(selectedMilliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) { //onTick 메서드는 카운트다운 타이머가 각 틱(일정 시간 간격)마다 호출하는 콜백 메서드-타이머가 감소하면서 남은 시간을 화면에 표시하고 업데이트하는 역할

                long seconds = millisUntilFinished / 1000; //seconds 변수는 남은 밀리초를 초로 변환한 값
                String time = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
                textViewTime.setText(time); //timeTextView라는 TextView 위젯에 time 문자열을 설정- 남은 시간 표시
                int progress = (int) ((selectedMilliseconds - millisUntilFinished) * 100 / selectedMilliseconds);
                //circle_bar.setProgress(progress);
            }

            @Override
            public void onFinish() { //onFinish 메서드는 카운트다운 타이머가 종료될 때 호출되는 콜백 메서드
                textViewTime.setText("성공!");
                //saveRecord(); // 타이머가 성공했을 때 실행되어, 기록을 저장하는 역할
                //returnToMainScreen(); //이 메서드는 타이머가 종료된 후 메인 화면으로 돌아가는 역할


            }
        };

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
                textViewTime.setText(formattedTime);

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

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finishWithFailure();
            }
        });

    }

    private String formatTime(String selectedTime) {
        if (selectedTime.equals("1시간")) {
            return "01:00:00";
        } else if (selectedTime.equals("2시간")) {
            return "02:00:00";
        } else if (selectedTime.equals("3시간")) {
            return "03:00:00";
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
            }


        timeCountInMilliSeconds = selectedMilliseconds;
        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                updateTimer((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                updateTimer(0);
                timerStatus = TimerStatus.STOPPED;
            }
        }.start();

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
        textViewTime.setText(time);
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
        Intent intent = new Intent(Main_TimerActivity.this, Main_TimerActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}


































