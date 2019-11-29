package com.example.speedtap;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NewHighScoreDialog extends Dialog {

    public SinglePlayerActivity c;

    public NewHighScoreDialog(SinglePlayerActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_highscore_dialog);

        TextView dialogScoreTV = findViewById(R.id.new_highScore_score);
        String scoreText = c.getString(R.string.your_score_text);
        dialogScoreTV.setText(scoreText+c.score);

        final EditText nameTV = findViewById(R.id.high_score_name_tv);

        Button add_highScore_btn = findViewById(R.id.add_highScore_btn);
        add_highScore_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, ScoreList.class);
                intent.putExtra("score", c.score);
                intent.putExtra("name", nameTV.getText().toString());
                intent.putExtra("gameMusic", c.gameMusic);
                c.startActivity(intent);
                c.finish();
            }
        });

        Button exit_btn = findViewById(R.id.exit_game_hs_btn);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finish();
            }
        });

        Button new_game_btn = findViewById(R.id.new_game_hs_btn);
        new_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.newGame();
                NewHighScoreDialog .this.cancel();
            }
        });

    }
}