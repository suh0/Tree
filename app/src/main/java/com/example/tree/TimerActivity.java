package com.example.tree;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class TimerActivity extends AppCompatActivity {

    private TextView timerTextView;
    private CountDownTimer countDownTimer;

    private ImageView exitButton;
    private RecordDatabaseHelper dbHelper;
    private long selectedMilliseconds;
    private ProgressBar progressBarCircle;

    private ImageView timer_image1;
    private ImageView timer_image2;
    private ImageView backmusic_start, backmusic_stop;

    private boolean isMusicPlaying = false;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerTextView = findViewById(R.id.timerTextView);
        exitButton = findViewById(R.id.exitButton);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        timer_image1 = findViewById(R.id.timer_image1);
        timer_image2 = findViewById(R.id.timer_image2);
        backmusic_start = findViewById(R.id.backmusic_start);
        backmusic_stop = findViewById(R.id.backmusic_stop);


        dbHelper = new RecordDatabaseHelper(this);

        selectedMilliseconds = getIntent().getLongExtra("selected_milliseconds", 1000);


        countDownTimer = new CountDownTimer(selectedMilliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //long seconds = millisUntilFinished / 1000;
                int seconds = (int) (millisUntilFinished / 1000);
                int totalSeconds = (int) (selectedMilliseconds / 1000);
                int remainingSeconds = (int) (millisUntilFinished / 1000);

                updateTimer(seconds);
                progressBarCircle.setProgress(seconds);
                setupProgressBar();
                updateTimer(remainingSeconds);
                progressBarCircle.setProgress(remainingSeconds);

                if (remainingSeconds <= totalSeconds / 2) {
                    // 타이머 시간이 절반이 지나면 timer_image1을 INVISIBLE로 설정
                    timer_image1.setVisibility(View.INVISIBLE);

                    // timer_image2를 VISIBLE로 설정
                    timer_image2.setVisibility(View.VISIBLE);
                }
            }
            //String time = String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
            //timerTextView.setText(time);


            @Override
            public void onFinish() {
                //timerTextView.setText("성공!");
                saveRecord();
                //returnToMainScreen();
                goToSuccessActivity();
            }
        };

        countDownTimer.start();



        backmusic_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMusic();

            }
        });



        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                finishWithFailure();
            }
        });
        backmusic_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playMusic();
            }
        });
    }


    private void playMusic() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // MediaPlayer 객체 해제
            mediaPlayer = null; // MediaPlayer 객체 초기화
        }

        backmusic_start.setVisibility(View.VISIBLE);
        backmusic_stop.setVisibility(View.GONE);
        //isMusicPlaying = true;
    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release(); // MediaPlayer 객체 해제
            mediaPlayer = null; // MediaPlayer 객체 초기화
        }
        backmusic_start.setVisibility(View.GONE);
        backmusic_stop.setVisibility(View.VISIBLE);
    }

    private void setupProgressBar() {  //이거는 꽉 채운 상태부터 감소하는 것
                progressBarCircle = findViewById(R.id.progressBarCircle);
                //"timeCountInMilliSeconds"는 원형 프로그래스 바의 최대값을 설정하는 데 사용

                int maxProgress = (int) (selectedMilliseconds / 1000);

                progressBarCircle.setMax(maxProgress);
            }

            private void updateTimer(int seconds) {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                timerTextView.setText(time);
                progressBarCircle.setProgress(seconds);
            }

            @Override
            protected void onDestroy() {
                if (mediaPlayer != null) {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
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




