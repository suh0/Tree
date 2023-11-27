package com.example.tree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button plant_btn;
    Button record_btn; // "기록" 버튼 추가

    Button shop_btn;
    private ImageView backmusic_start, backmusic_stop;


    private static final String TAG_TEXT = "music";
    private boolean isMusicPlaying = false;
    private List<String> selectedMusicList = new ArrayList<>();


    List<Map<String, Object>> dialogItemList;
    String[] musicFiles = {"music03.mp3", "music04.mp3", "music05.mp3"};
    static MediaPlayer mediaPlayer;

    TextView txt_currentBgm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plant_btn = findViewById(R.id.plant_btn);
        record_btn = findViewById(R.id.record_btn); // 버튼 참조
        shop_btn = findViewById(R.id.shop_btn);
        backmusic_start = findViewById(R.id.backmusic_start);
        backmusic_stop = findViewById(R.id.backmusic_stop);

        mediaPlayer = MediaPlayer.create(this, R.raw.music06);
        mediaPlayer.setLooping(true); // 반복 재생 설정
        mediaPlayer.start(); // 음악 재생
        plant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mediaPlayer.stop();
                Intent intent = new Intent(MainActivity.this, SelectHour.class);
                startActivity(intent);
                finish();
            }
        });

        record_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        shop_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backmusic_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        backmusic_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        dialogItemList = new ArrayList<>();


    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog, null);

        Drawable dialogBackground = getResources().getDrawable(R.drawable.area_dialog);//다이얼로그 배경을 다른 이미지로 변환
        view.setBackground(dialogBackground);

        builder.setView(view);


        final ListView listview = (ListView) view.findViewById(R.id.listview_alterdialog_list);
        final AlertDialog dialog = builder.create();

        // dialogItemList 초기화 및 데이터 추가
        dialogItemList = new ArrayList<>();
        for (String music : musicFiles) {
            Map<String, Object> item = new HashMap<>();
            item.put(TAG_TEXT, music);
            dialogItemList.add(item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(MainActivity.this, dialogItemList,
                R.layout.dialog_row,
                new String[]{TAG_TEXT},
                new int[]{R.id.alertDialogItemTextView});
        listview.setAdapter(simpleAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int resId = 0;

                String selectedMusic = musicFiles[position]; // 선택된 음악 파일 이름 가져오기
                playMusic(selectedMusic); // 음악 재생 코드 추가
                dialog.dismiss(); // 다이얼로그 닫기
                backmusic_start.setVisibility(View.GONE);
                backmusic_stop.setVisibility(View.VISIBLE);

                // 선택한 음악 파일 이름에 따라 리소스 ID 설정
                if (selectedMusic.equals("music03.mp3")) {
                    resId = R.raw.music03;
                } else if (selectedMusic.equals("music04.mp3")) {
                    resId = R.raw.music04;
                } else if (selectedMusic.equals("music05.mp3")) {
                    resId = R.raw.music05;
                }
                // resId를 리스트에 추가
                String resIdString = String.valueOf(resId);
                selectedMusicList.add(resIdString);
                saveSelectedMusicName(selectedMusic);// 선택한 음악 저장

                TextView txt_currentBgm = findViewById(R.id.txt_currentBgm); // 선택한 음악 이름으로 TextView 업데이트
                txt_currentBgm.setText(": " + selectedMusic); // 선택한 음악 파일 이름으로 TextView 설정

                // Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                //ArrayList<String> selectedMusicArrayList = new ArrayList<>(selectedMusicList);
                //intent.putStringArrayListExtra("selectedMusicList", selectedMusicArrayList);
                //startActivity(intent);

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        backmusic_start.setVisibility(View.VISIBLE);
        backmusic_stop.setVisibility(View.GONE);
    }

    private void playMusic(String musicFileName) {

        if (mediaPlayer != null) {  // MediaPlayer 사용 전에 먼저 release() 호출
            mediaPlayer.reset();
        } else {
            mediaPlayer = new MediaPlayer();
        }
        int resId = 0; // 여기에 리소스 ID를 직접 지정

        // 음악 파일 이름에 따라 리소스 ID 설정
        if (musicFileName.equals("music03.mp3")) {
            resId = R.raw.music03;
        } else if (musicFileName.equals("music04.mp3")) {
            resId = R.raw.music04;
        } else if (musicFileName.equals("music05.mp3")) {
            resId = R.raw.music05;
        }

        try {
            AssetFileDescriptor afd = getResources().openRawResourceFd(resId);
            if (afd != null) {
                mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();

                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        backmusic_start.setVisibility(View.GONE);
        backmusic_stop.setVisibility(View.VISIBLE);
        isMusicPlaying = true;


    }

    @Override
    protected void onResume() {
        super.onResume();

        txt_currentBgm = findViewById(R.id.txt_currentBgm);
        String selectedMusicName = getSelectedMusicName(); // 선택한 음악 이름 가져오기
        txt_currentBgm.setText(": " + selectedMusicName); // 선택한 음악 파일 이름으로 TextView 설정
    }

    private String getSelectedMusicName() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("selectedMusic", "No Music"); // 기본값 설정

    }

    private void saveSelectedMusicName(String musicName) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("selectedMusic", musicName);
        editor.apply();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // SharedPreferences 값 초기화
        saveSelectedMusicName("No music");
    }

    @Override
    public void onBackPressed() { //앱 나가면 음악 멈추게 함
        stopMusic();
        super.onBackPressed();
    }

    @Override
    protected void onStart() { //앱 나갔다 들어와도 배경음악 실행됨
        super.onStart();
        mediaPlayer = MediaPlayer.create(this, R.raw.music06);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

    }
}

