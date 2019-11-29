package com.example.speedtap;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class TwoPlayerDialog extends Dialog {

    public TwoPlayersActivity c;

    public TwoPlayerDialog(TwoPlayersActivity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.two_player_game_over_dialog);

        TextView res1 = findViewById(R.id.gameres1);
        res1.setText(c.resPlayer1);

        TextView res2 = findViewById(R.id.gameres2);
        res2.setText(c.resPlayer2);

        Button exit_btn = findViewById(R.id.exit_game_btn_2);
        exit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.finish();
            }
        });

        Button new_game_btn = findViewById(R.id.new_game_btn_2);
        new_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c.newGame();
                TwoPlayerDialog.this.cancel();
            }
        });
    }
}
