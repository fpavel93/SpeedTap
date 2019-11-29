package com.example.speedtap;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    MediaPlayer gameMusicPlayer;

    boolean gameMusic;
    boolean gameSounds;

    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        SharedPreferences sp = getSharedPreferences("score", MODE_PRIVATE);
        editor = sp.edit();

        gameMusic = sp.getBoolean("music", true);
        gameSounds = sp.getBoolean("sound", true);

        Button main_game_btn = findViewById(R.id.main_game_btn);
        main_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SinglePlayerActivity.class);
                intent.putExtra("gameSound", gameSounds);
                intent.putExtra("gameMusic", gameMusic);
                startActivity(intent);
            }
        });

        Button towPlayersBtn = findViewById(R.id.two_player_btn);
        towPlayersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TwoPlayersActivity.class);
                intent.putExtra("gameSound", gameSounds);
                intent.putExtra("gameMusic", gameMusic);
                startActivity(intent);
            }
        });

        Button settings = findViewById(R.id.settings_btn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsDialog settings = new SettingsDialog(MainActivity.this);
                settings.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                settings.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                settings.show();
            }
        });

        Button about = findViewById(R.id.about_btn);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AboutDialog aboutDialog = new AboutDialog(MainActivity.this);
                aboutDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                aboutDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                aboutDialog.show();
            }
        });

        Button highScoreBtn = findViewById(R.id.high_score_btn);
        highScoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScoreList.class);
                intent.putExtra("gameMusic", gameMusic);
                startActivity(intent);
            }
        });
    }

    void playMusic()
    {
        gameMusicPlayer = MediaPlayer.create(MainActivity.this, R.raw.gamemusic);
        if(gameMusic) {
            gameMusicPlayer.setLooping(true);
            gameMusicPlayer.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        editor.putBoolean("music", gameMusic);
        editor.putBoolean("sound", gameSounds);
        editor.apply();
        gameMusicPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic();

        ImageView image = findViewById(R.id.sp_img);
        blink(image);
    }

    public void blink(View view){
        ImageView image = findViewById(R.id.sp_img);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.blink);
        image.startAnimation(animation1);
    }
}
