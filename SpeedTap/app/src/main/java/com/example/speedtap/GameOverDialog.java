package com.example.speedtap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class GameOverDialog extends Dialog {

    public SinglePlayerActivity c;

    public GameOverDialog(SinglePlayerActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_over_dialog);
        TextView dialogScoreTV = findViewById(R.id.game_over_score);
        String scoreText = c.getString(R.string.your_score_text);
        dialogScoreTV.setText(scoreText+c.score);

        Button exit_btn = findViewById(R.id.exit_game_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finish();
            }
        });

        Button new_game_btn = findViewById(R.id.new_game_btn);
        new_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.newGame();
                GameOverDialog.this.cancel();
            }
        });
    }
}
