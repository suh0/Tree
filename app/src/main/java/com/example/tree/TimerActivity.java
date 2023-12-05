package com.example.tree;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.net.Uri;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.tree.MainActivity.mediaPlayer;
import static com.example.tree.MainActivity.mediaPlayer06;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

    //private boolean isMusicPlaying = false;
    //MediaPlayer mediaPlayer = MainActivity.getMediaPlayer();
    //private List<String> selectedMusicList = new ArrayList<>();

    private boolean isMusicPlaying = true;



    List<Map<String, Object>> dialogItemList;


    private int pausedPosition = 0; // 멈춘 위치 저장하는 변수
    private int pausedPosition06 = 0; // mediaPlayer06의 위치


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
        // selectedMusicList = getIntent().getStringArrayListExtra("selectedMusicList");




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
                //showAlertDialog();
                playMusic();
                playMusic06();

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
                stopMusic();
                stopMusic06();

            }
        });
        dialogItemList = new ArrayList<>();
    }



    private void playMusic() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(pausedPosition); // 멈췄을 때의 위치로 이동
            mediaPlayer.start();
        }

        backmusic_start.setVisibility(View.GONE);
        backmusic_stop.setVisibility(View.VISIBLE);
        isMusicPlaying = true;


    }

    private void playMusic06() {
        if (mediaPlayer06 != null && !mediaPlayer06.isPlaying()) {
            mediaPlayer06.seekTo(pausedPosition06); // 멈췄을 때의 위치로 이동
            mediaPlayer06.start();
        }

        backmusic_start.setVisibility(View.GONE);
        backmusic_stop.setVisibility(View.VISIBLE);
        isMusicPlaying = true;


    }



    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            pausedPosition = mediaPlayer.getCurrentPosition(); // 음악이 멈춘 위치 저장
            mediaPlayer.pause();
        }
        backmusic_start.setVisibility(View.VISIBLE);
        backmusic_stop.setVisibility(View.GONE);
    }

    private void stopMusic06() {
        if (mediaPlayer06 != null && mediaPlayer06.isPlaying()) {
            pausedPosition06 = mediaPlayer06.getCurrentPosition(); // 음악이 멈춘 위치 저장
            mediaPlayer06.pause();
        }
        backmusic_start.setVisibility(View.VISIBLE);
        backmusic_stop.setVisibility(View.GONE);
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
        //finish();
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



