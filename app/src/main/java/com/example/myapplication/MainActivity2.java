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

    private MediaPlayer mediaPlayer1;
    private MediaPlayer mediaPlayer2;

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

//        for(int i =1; i <= 3; i++) {
//            ImageButton imagePlayButton = findViewById(getResources().getIdentifier("imagePlayButton" + i,"id",getPackageName()));
//            ImageButton imageStopButton = findViewById(getResources().getIdentifier("imageStopButton" + i, "id", getPackageName()));
//            MediaPlayer mediaPlayer = MediaPlayer.create(this, getResources().getIdentifier("my_song" + i, "raw", getPackageName()));
//
//            imagePlayButton.setTag(i);
//            imagePlayButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int buttonIndex = (int) view.getTag();
//                    playMusic(buttonIndex);
//                }
//
//                private void playMusic(int buttonIndex) {
//                    MediaPlayer mediaPlayer = mediaPlayers.get(buttonIndex -1);
//                    mediaPlayer.start();
//
//                    ImageButton imagePlayButton = imagePlayButtons.get(buttonIndex-1);
//                    ImageButton imageStopButton = imageStopButtons.get(buttonIndex-1);
//
//                    imagePlayButton.setVisibility(View.GONE);
//                    imageStopButton.setVisibility(View.VISIBLE);
//
//                }
//            });
//
//            imageStopButton.setTag(i);
//            imageStopButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int buttonIndex = (int) view.getTag();
//                    pauseMusic(buttonIndex);
//                }
//
//                private void pauseMusic(int buttonIndex) {
//                    MediaPlayer mediaPlayer = mediaPlayers.get(buttonIndex - 1);
//                    mediaPlayer.pause();
//
//                    ImageButton imagePlayButton = imagePlayButtons.get(buttonIndex - 1);
//                    ImageButton imageStopButton = imageStopButtons.get(buttonIndex - 1);
//
//                    imagePlayButton.setVisibility(View.VISIBLE);
//                    imageStopButton.setVisibility(View.GONE);
//                }
//            });
//
//            imagePlayButtons.add(imagePlayButton);
//            imageStopButtons.add(imageStopButton);
//            mediaPlayers.add(mediaPlayer);
//        }
//    }
        imagePlayButton1 = findViewById(R.id.imagePlayButton1);
        imageStopButton1 =findViewById(R.id.imageStopButton1);
        imagePlayButton2 = findViewById(R.id.imagePlayButton2);
        imageStopButton2 =findViewById(R.id.imageStopButton2);

        mediaPlayer1 = MediaPlayer.create(this,R.raw.music01);
        mediaPlayer2 = MediaPlayer.create(this,R.raw.music02);


        imagePlayButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playMusic1();
            }
        });

        imageStopButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseMusic1();
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
    }
    public void playMusic1() {
        mediaPlayer1.start();
        imagePlayButton1.setVisibility(View.GONE);
        imageStopButton1.setVisibility(View.VISIBLE);
    }

    public void pauseMusic1(){
        mediaPlayer1.pause();
        imagePlayButton1.setVisibility(View.VISIBLE);
        imageStopButton1.setVisibility(View.GONE);

    }
}