package com.example.tree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        return mediaPlayer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        plant_btn = findViewById(R.id.plant_btn);
        record_btn = findViewById(R.id.record_btn); // 버튼 참조
        shop_btn = findViewById(R.id.shop_btn);
        backmusic_start = findViewById(R.id.backmusic_start);
        backmusic_stop = findViewById(R.id.backmusic_stop);


        plant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectHour.class);
                startActivity(intent);
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
                Intent intent = new Intent(MainActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

     /*   backmusic_start.setOnClickListener(new View.OnClickListener() {
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



}*/

  /*  private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_dialog, null);
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

               // Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                //ArrayList<String> selectedMusicArrayList = new ArrayList<>(selectedMusicList);
                //intent.putStringArrayListExtra("selectedMusicList", selectedMusicArrayList);
                //startActivity(intent);

            }
        });
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }*/

   /* private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        backmusic_start.setVisibility(View.VISIBLE);
        backmusic_stop.setVisibility(View.GONE);
    }*/

   /* private void playMusic(String musicFileName) {

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


    }*/


    }
}
