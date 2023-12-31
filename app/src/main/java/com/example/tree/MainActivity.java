package com.example.tree;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecordDatabaseHelper dbHelper;

    private CoinDatabaseHelper coinHelper;
    private TreeItemDatabaseHelper treeHelper;
    int[][] treeImages;
    /*= {
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9},
            {R.drawable.img_tree1, R.drawable.img_tree2, R.drawable.img_tree3},
            {R.drawable.img_tree4, R.drawable.img_tree5, R.drawable.img_tree6},
            {R.drawable.img_tree7, R.drawable.img_tree8, R.drawable.img_tree9}
    };*/

    ImageView btn_timer, btn_stat, btn_shop;
    ImageView img_frac1, img_frac2, img_frac3, img_frac4, img_cloud1, img_cloud2;
    Button btn_record;

    TextView txt_bgm, txt_money;
    TextView txtDate;
    ConstraintLayout img_board;

    final int max_tree_index = 4;
    final int max_hour_index = 4;
    ArrayList<ProductTree> allTrees;
    private static final String TAG_TEXT = "music";
    private boolean isMusicPlaying = false;
    private List<String> selectedMusicList = new ArrayList<>();


    List<Map<String, Object>> dialogItemList;
    String[] musicFiles = {"music03.mp3", "music04.mp3", "music05.mp3"};
    static MediaPlayer mediaPlayer;
    static MediaPlayer mediaPlayer06;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDate = findViewById(R.id.txt_date);
        SimpleDateFormat sdf = new SimpleDateFormat("M월 d일", Locale.getDefault());
        String currentDate = sdf.format(new Date());
        txtDate.setText(currentDate);

        dbHelper = new RecordDatabaseHelper(this);
        coinHelper = new CoinDatabaseHelper(this);
        treeHelper = new TreeItemDatabaseHelper(this);

        treeImages = new int[max_hour_index][max_tree_index];
        allTrees = treeHelper.getAllTrees();
        for(int i = 0; i < max_hour_index; i++) {
            for(int j = 0; j < max_tree_index; j++) {
                treeImages[i][j] = allTrees.get(i * max_hour_index + j).getResId();
            }
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM records", null);
        int randomIndex = cursor.getColumnIndex("random");
        int hourNumIndex = cursor.getColumnIndex("hourNum");
        int treeIndexIndex = cursor.getColumnIndex("treeIndex");

        coinHelper = new CoinDatabaseHelper(this);

        ImageView room[] = new ImageView[25];
        img_frac1=findViewById(R.id.img_frac1);
        img_frac2=findViewById(R.id.img_frac2);
        img_frac3=findViewById(R.id.img_frac3);
        img_frac4=findViewById(R.id.img_frac4);
        btn_timer = findViewById(R.id.btn_timer);
        btn_record = findViewById(R.id.btn_record);
        btn_shop = findViewById(R.id.btn_shop);
        btn_stat=findViewById(R.id.btn_stat);
        txt_bgm=findViewById(R.id.txt_bgm);
        txt_money=findViewById(R.id.txt_money);
        img_cloud1=findViewById(R.id.img_cloud1);
        img_cloud2=findViewById(R.id.img_cloud2);

        img_board=findViewById(R.id.img_board);

        Animation animFrac1=AnimationUtils.loadAnimation(this, R.anim.anim_frac);
        Animation animFrac2=AnimationUtils.loadAnimation(this, R.anim.anim_frac2);
        Animation animFrac3=AnimationUtils.loadAnimation(this, R.anim.anim_frac3);
        Animation animFrac4=AnimationUtils.loadAnimation(this, R.anim.anim_frac4);
        Animation animBoard=AnimationUtils.loadAnimation(this, R.anim.anim_board);
        Animation animCloud1=AnimationUtils.loadAnimation(this, R.anim.anim_cloud1);
        Animation animCloud2=AnimationUtils.loadAnimation(this, R.anim.anim_cloud2);
        img_frac1.startAnimation(animFrac1);
        img_frac2.startAnimation(animFrac2);
        img_frac3.startAnimation(animFrac3);
        img_frac4.startAnimation(animFrac4);
        img_board.startAnimation(animBoard);
        img_cloud1.startAnimation(animCloud1);
        img_cloud2.startAnimation(animCloud2);

        txt_money.setText(" " + coinHelper.getCurrentBalance());

        for(int i=0; i<25; i++){
            room[i]=findViewById(getResources().getIdentifier("room"+ i, "id", "com.example.tree"));
        }
        while (cursor.moveToNext()) {
            int random = cursor.getInt(randomIndex);
            int hourNum = cursor.getInt(hourNumIndex);
            int treeIndex = cursor.getInt(treeIndexIndex);

            if (random != 0) {
                room[random-1].setImageResource(treeImages[hourNum - 1][treeIndex - 1]);
                room[random-1].setVisibility(View.VISIBLE);
            }
        }
        cursor.close();

        Animation animButtonEffect=AnimationUtils.loadAnimation(this, R.anim.anim_btn_effect);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("paused_position")) {
            int pausedPosition = intent.getIntExtra("paused_position", 0); // 멈춘 위치 가져오기

            if (mediaPlayer06 != null) {
                mediaPlayer06.release(); // 이전 mediaPlayer06 해제
            }

            mediaPlayer06 = MediaPlayer.create(this, R.raw.music06);
            mediaPlayer06.setLooping(true); // 반복 재생 설정
            mediaPlayer06.seekTo(pausedPosition); // 멈춘 지점으로 이동

            // 만약 mediaPlayer가 재생 중이라면 mediaPlayer06의 볼륨을 0으로 설정
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer06.setVolume(0.0f, 0.0f);
            }
            mediaPlayer06.start(); // 음악 재생
        } else {
            // 기본 설정: 볼륨 1.0f로 music06 재생
            mediaPlayer06 = MediaPlayer.create(this, R.raw.music06);
            mediaPlayer06.setLooping(true); // 반복 재생 설정
            mediaPlayer06.start(); // 음악 재생
        }



        btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_timer.startAnimation(animButtonEffect);
                Intent intent = new Intent(MainActivity.this , SelectHour.class);
                startActivity(intent);
                //finish();
            }
        });

        btn_stat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_stat.startAnimation(animButtonEffect);
                Intent toStat=new Intent(MainActivity.this, StatActivity.class);
                startActivity(toStat);
            }
        });

        btn_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordActivity.class);
                startActivity(intent);
            }
        });

        btn_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer06 != null) {
                    mediaPlayer06.stop();
                }
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }

                btn_shop.startAnimation(animButtonEffect);
                Intent intent = new Intent(MainActivity.this , ShopActivity.class);
                startActivity(intent);

            }
        });
        txt_bgm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });


        dialogItemList = new ArrayList<>();

    }

    private void showAlertDialog() {
        // mediaPlayer06.stop();

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

                TextView txt_bgm = findViewById(R.id.txt_bgm); // 선택한 음악 이름으로 TextView 업데이트
                txt_bgm.setText(": " + selectedMusic); // 선택한 음악 파일 이름으로 TextView 설정

                // Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                //ArrayList<String> selectedMusicArrayList = new ArrayList<>(selectedMusicList);
                //intent.putStringArrayListExtra("selectedMusicList", selectedMusicArrayList);
                //startActivity(intent);

                if (mediaPlayer06 != null && mediaPlayer06.isPlaying()) {
                    mediaPlayer06.stop(); // 다이얼로그에서 음악 선택 시, mediaPlayer06을 중지
                }

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true); // 다이얼로그 외부 터치 시 닫히도록 설정
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }

    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }

    }

    private void playMusic(String musicFileName) {
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
            if (mediaPlayer != null) {  // 기존 MediaPlayer가 있다면 정지하고 새로운 음악 재생
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
                Log.d("playMusic","true");
            }

            mediaPlayer = MediaPlayer.create(this, resId);
            mediaPlayer.setLooping(true); // 반복 재생 설정



            mediaPlayer.start(); // 음악 재생
        } catch (Exception e) {
            e.printStackTrace();
        }

        isMusicPlaying = true;


    }

    @Override
    protected void onStart() {
        super.onStart();
        txt_money.setText(" " + coinHelper.getCurrentBalance());
    }
    @Override
    protected void onResume() {
        super.onResume();
        String selectedMusicName = getSelectedMusicName(); // 선택한 음악 이름 가져오기
        txt_bgm.setText(": " + selectedMusicName); // 선택한 음악 파일 이름으로 TextView 설정

        if (mediaPlayer06 != null && !mediaPlayer06.isPlaying()) {
            mediaPlayer06.start();
        }
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
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("앱을 종료하시겠습니까?")
                .setCancelable(false)
                .setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finishAffinity(); // 모든 액티비티 종료
                        if (mediaPlayer06 != null) {
                            mediaPlayer06.stop();
                        }
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                    }
                })
                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); // 대화상자 닫기
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // SharedPreferences 값 초기화
        saveSelectedMusicName("No music");
    }
}
