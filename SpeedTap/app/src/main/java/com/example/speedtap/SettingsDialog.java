package com.example.speedtap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

public class SettingsDialog extends Dialog {

    public MainActivity c;
    public Dialog d;
    ImageView inner_game, music_game;

    public SettingsDialog(MainActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settings_dialog);
        inner_game = findViewById(R.id.inner_game_sound);
        music_game = findViewById(R.id.game_music);
        initSettings();

        inner_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGameSound();

            }
        });
        music_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGameMusic();
            }
        });

    }

    private void updateGameMusic() {
        if(c.gameMusic) {
            music_game.setImageResource(R.drawable.ic_volume_off_black_24dp);
            c.gameMusic = false;
            c.gameMusicPlayer.stop();
        }
        else {
            c.gameMusic = true;
            c.playMusic();
            music_game.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
    }

    private void updateGameSound() {
        if (c.gameSounds) {
            inner_game.setImageResource(R.drawable.ic_notifications_off_black_24dp);
            c.gameSounds = false;
        }
        else {
            c.gameSounds = true;
            inner_game.setImageResource(R.drawable.ic_notifications_black_24dp);
        }
    }

    void initSettings() {
        if(c.gameMusic) {
            music_game.setImageResource(R.drawable.ic_volume_up_black_24dp);
        }
        else {
            music_game.setImageResource(R.drawable.ic_volume_off_black_24dp);

        }

        if (c.gameSounds) {
            inner_game.setImageResource(R.drawable.ic_notifications_black_24dp);

        }
        else {
            inner_game.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
    }
}


