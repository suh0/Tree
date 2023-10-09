package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity2 extends AppCompatActivity {

    private Button tree_button;

//    private ArrayList<ImageButton> imagePlayButtons = new ArrayList<>();
//    private ArrayList<ImageButton> imageStopButtons = new ArrayList<>();
//    private ArrayList<MediaPlayer> mediaPlayers =new ArrayList<>();
    private ImageButton imagePlayButton1;
    private ImageButton imageStopButton1;
    private ImageButton imagePlayButton2;
    private ImageButton imageStopButton2;
    private ImageButton imagePlayButton3;
    private ImageButton imageStopButton3;
    private ImageButton imagePlayButton4;
    private ImageButton imageStopButton4;
    private ImageButton imagePlayButton5;
    private ImageButton imageStopButton5;
    private ImageButton imagePlayButton6;
    private ImageButton imageStopButton6;

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;
    private MediaPlayer mediaPlayer3;
    private MediaPlayer mediaPlayer4;
    private MediaPlayer mediaPlayer5;
    private MediaPlayer mediaPlayer6;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tree_button =findViewById(R.id.tree_button);
        tree_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                startActivity(intent);
            }
        });

        imagePlayButton1 = findViewById(R.id.imagePlayButton1);
        imageStopButton1 = findViewById(R.id.imageStopButton1);
        imagePlayButton2 = findViewById(R.id.imagePlayButton2);
        imageStopButton2 = findViewById(R.id.imageStopButton2);
        imagePlayButton3 = findViewById(R.id.imagePlayButton3);
        imageStopButton3 = findViewById(R.id.imageStopButton3);
        imagePlayButton4 = findViewById(R.id.imagePlayButton4);
        imageStopButton4 = findViewById(R.id.imageStopButton4);
        imagePlayButton5 = findViewById(R.id.imagePlayButton5);
        imageStopButton5 = findViewById(R.id.imageStopButton5);
        imagePlayButton6 = findViewById(R.id.imagePlayButton6);
        imageStopButton6 = findViewById(R.id.imageStopButton6);


        mediaPlayer1 = MediaPlayer.create(this,R.raw.music03);
        mediaPlayer2 = MediaPlayer.create(this,R.raw.music04);
        mediaPlayer3 = MediaPlayer.create(this,R.raw.music05);
        mediaPlayer4 = MediaPlayer.create(this,R.raw.music06);
        mediaPlayer5 = MediaPlayer.create(this,R.raw.music07);
        mediaPlayer6 = MediaPlayer.create(this,R.raw.music08);


        imagePlayButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic1();
            }
            public void playMusic1() {
                mediaPlayer1.start();
                imagePlayButton1.setVisibility(View.GONE);
                imageStopButton1.setVisibility(View.VISIBLE);
            }
        });


        imageStopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic1();
            }
            public void pauseMusic1(){
                mediaPlayer1.pause();
                imagePlayButton1.setVisibility(View.VISIBLE);
                imageStopButton1.setVisibility(View.GONE);

            }
        });

        imagePlayButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic2();
            }

            private void playMusic2() {
                mediaPlayer2.start();
                imagePlayButton2.setVisibility(View.GONE);
                imageStopButton2.setVisibility(View.VISIBLE);
            }
        });

        imageStopButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic2();
            }

            private void pauseMusic2() {
                mediaPlayer2.pause();
                imagePlayButton2.setVisibility(View.VISIBLE);
                imageStopButton2.setVisibility(View.GONE);
            }
        });

        imagePlayButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic3();
            }

            private void playMusic3() {
                mediaPlayer3.start();
                imagePlayButton3.setVisibility(View.GONE);
                imageStopButton3.setVisibility(View.VISIBLE);
            }
        });

        imageStopButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic3();
            }

            private void pauseMusic3() {
                mediaPlayer3.pause();
                imagePlayButton3.setVisibility(View.VISIBLE);
                imageStopButton3.setVisibility(View.GONE);
            }
        });

        imagePlayButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic4();
            }

            private void playMusic4() {
                mediaPlayer4.start();
                imagePlayButton4.setVisibility(View.GONE);
                imageStopButton4.setVisibility(View.VISIBLE);
            }
        });

        imageStopButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic4();
            }

            private void pauseMusic4() {
                mediaPlayer4.pause();
                imagePlayButton4.setVisibility(View.VISIBLE);
                imageStopButton4.setVisibility(View.GONE);
            }
        });

        imagePlayButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic5();
            }

            private void playMusic5() {
                mediaPlayer5.start();
                imagePlayButton5.setVisibility(View.GONE);
                imageStopButton5.setVisibility(View.VISIBLE);
            }
        });

        imageStopButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic5();
            }

            private void pauseMusic5() {
                mediaPlayer5.pause();
                imagePlayButton5.setVisibility(View.VISIBLE);
                imageStopButton5.setVisibility(View.GONE);
            }
        });

        imagePlayButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic6();
            }

            private void playMusic6() {
                mediaPlayer6.start();
                imagePlayButton6.setVisibility(View.GONE);
                imageStopButton6.setVisibility(View.VISIBLE);
            }
        });

        imageStopButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic6();
            }

            private void pauseMusic6() {
                mediaPlayer6.pause();
                imagePlayButton6.setVisibility(View.VISIBLE);
                imageStopButton6.setVisibility(View.GONE);
            }
        });

    }

}
