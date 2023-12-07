package com.example.tree;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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


    private RecordDatabaseHelper dbHelper;
    private long selectedMilliseconds;
    private int receivedHourNumber;
    private int receivedTreeIndex;

    private ProgressBar progressBarCircle;

    private ImageView timer_image1;
    private ImageView timer_image2;

    int[][] treeImages = {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };

    //private boolean isMusicPlaying = false;
    //MediaPlayer mediaPlayer = MainActivity.getMediaPlayer();
    //private List<String> selectedMusicList = new ArrayList<>();

    private boolean isMusicPlaying = true;



    List<Map<String, Object>> dialogItemList;
    ImageView backmusic_start;
    ImageView backmusic_stop;


    private int pausedPosition = 0; // 멈춘 위치 저장하는 변수
    private int pausedPosition06 = 0; // mediaPlayer06의 위치



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        timerTextView = findViewById(R.id.timerTextView);
        ImageView exitButton = findViewById(R.id.exitButton);
        progressBarCircle = findViewById(R.id.progressBarCircle);
        timer_image1 = findViewById(R.id.timer_image1);
        timer_image2 = findViewById(R.id.timer_image2);

        backmusic_start = findViewById(R.id.backmusic_start);
        backmusic_stop = findViewById(R.id.backmusic_stop);
        // selectedMusicList = getIntent().getStringArrayListExtra("selectedMusicList");



//>>>>>>> chae_music_copy

        dbHelper = new RecordDatabaseHelper(this);

        selectedMilliseconds = getIntent().getLongExtra("selected_milliseconds", 1000);
        receivedHourNumber = getIntent().getIntExtra("currentHourNumber", 1); // 1은 기본값
        receivedTreeIndex = getIntent().getIntExtra("currentTreeIndex", 1); // 1은 기본값

        timer_image2.setImageResource(treeImages[receivedHourNumber - 1][receivedTreeIndex - 1]);


        Animation animButtonEffect= AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);
        Animation animSprout= AnimationUtils.loadAnimation(this, R.anim.anim_sprout);
        timer_image1.startAnimation(animSprout);


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
                    timer_image1.clearAnimation(); // 무한 반복 애니메이션 중지
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
                CoinDatabaseHelper coinHelper = new CoinDatabaseHelper(TimerActivity.this);
                coinHelper.addBalance(100);

            }
        };

        countDownTimer.start();



        exitButton.setOnClickListener(v -> {
            exitButton.startAnimation(animButtonEffect);
            countDownTimer.cancel();
            finishWithFailure();
        });

        backmusic_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showAlertDialog();
                playMusic();
                playMusic06();

            }
        });

        backmusic_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
                stopMusic06();

            }
        });
    }



    @Override
    public void onBackPressed() {
        //super.onBackPressed();
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
        values.put("hourNum", receivedHourNumber);
        values.put("treeIndex", receivedTreeIndex);


        int randomValue = 0;
        int maxRandomCount = 25;
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
        return (int) (Math.random() * 25) + 1;
    }

    private boolean isRandomValueExistsInDB(SQLiteDatabase db, int randomValue) {
        // 메서드 내용: DB에서 랜덤 값과 오늘의 날짜와 일치하는 값을 확인합니다.
        // 랜덤 값과 날짜 모두 일치하는 경우 true를 반환하고, 그렇지 않으면 false를 반환합니다.
        String query = "SELECT * FROM records WHERE random = ? AND date = ?";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
        String todayDate = dateFormat.format(new Date()); // 오늘 날짜를 가져옵니다.
        Cursor cursor = db.rawQuery(query, new String[]{
                String.valueOf(randomValue),
                todayDate
        });
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }


    private void setupProgressBar() {  //이거는 꽉 채운 상태부터 감소하는 것
                progressBarCircle = findViewById(R.id.progressBarCircle);
                //"timeCountInMilliSeconds"는 원형 프로그래스 바의 최대값을 설정하는 데 사용
                int maxProgress = (int) (selectedMilliseconds / 1000);
                progressBarCircle.setMax(maxProgress);

    }


            private String getCurrentDate() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d", Locale.getDefault());
                Date date = new Date();
                return dateFormat.format(date);
            }

            private void finishWithFailure() {
                Intent intent = new Intent();
                intent.putExtra("result", "실패하였습니다!");
                setResult(RESULT_OK, intent);
                goToFailActivity();
                finish();
            }

            private void returnToMainScreen() {
                Intent intent = new Intent(TimerActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        //dialogItemList = new ArrayList<>();




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


    private void updateTimer(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;
        String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
        timerTextView.setText(time);
        progressBarCircle.setProgress(seconds);
    }







    private void goToSuccessActivity(){
        Intent toSuccess = new Intent(TimerActivity.this, SuccessActivity.class);
        toSuccess.putExtra("currentHourNumber", receivedHourNumber);
        toSuccess.putExtra("currentTreeIndex", receivedTreeIndex);
        startActivity(toSuccess);
    }

    private void goToFailActivity(){
        Intent toFail = new Intent(TimerActivity.this, FailActivity.class);
        startActivity(toFail);
    }
}

