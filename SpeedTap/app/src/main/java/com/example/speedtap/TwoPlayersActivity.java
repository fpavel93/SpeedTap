package com.example.speedtap;

import android.animation.LayoutTransition;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.Random;

public class TwoPlayersActivity extends AppCompatActivity {

    int lifePlayer1;
    int lifePlayer2;
    boolean play;

    MediaPlayer blop;
    MediaPlayer pling;
    MediaPlayer gameMusicPlayer;

    boolean gameSound;
    boolean gameMusic;

    ArrayList<ColorButton> bubblesList;

    Handler handler;
    String bottomBarColorStringPlayer1;
    String bottomBarColorStringPlayer2;

    String resPlayer1;
    String resPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_players);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RelativeLayout screenLayout = findViewById(R.id.two_player_screen_layout);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.DISAPPEARING);
        screenLayout.setLayoutTransition(layoutTransition);

        handler = new Handler();

        blop = MediaPlayer.create(TwoPlayersActivity.this, R.raw.blop);
        pling = MediaPlayer.create(TwoPlayersActivity.this, R.raw.pling);

        gameSound = getIntent().getBooleanExtra("gameSound", true);
        gameMusic = getIntent().getBooleanExtra("gameMusic", true);

        lifePlayer1 = 3;
        lifePlayer2 = 3;
        bubblesList = new ArrayList();

        bottomBarColorStringPlayer1 = "red";
        bottomBarColorStringPlayer2 = "blue";

        Button btnPlayer1 = findViewById(R.id.bottom_bar_player1);
        btnPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isColorExist = false;
                for (int i = 0; i < bubblesList.size(); i++) {
                    if (bubblesList.get(i).getColor().equals(bottomBarColorStringPlayer1)) {
                        bubblesList.get(i).setVisibility(View.GONE);
                        if(gameSound) {
                            blop.start();
                        }
                        bubblesList.remove(bubblesList.get(i));
                        isColorExist = true;
                        break;
                    }
                }
                if (isColorExist == false)
                {
                    decreaseLifePlayer1();
                }
            }
        });

        Button btnPlayer2 = findViewById(R.id.bottom_bar_player2);
        btnPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Boolean isColorExist = false;
                for (int i = 0; i < bubblesList.size(); i++) {
                    if (bubblesList.get(i).getColor().equals(bottomBarColorStringPlayer2)) {
                        bubblesList.get(i).setVisibility(View.GONE);
                        if(gameSound) {
                            blop.start();
                        }
                        bubblesList.remove(bubblesList.get(i));
                        isColorExist = true;
                        break;
                    }
                }
                if (isColorExist == false)
                {
                    decreaseLifePlayer2();
                }
            }
        });
    }

    void createBubble() {

        Random rand = new Random();
        final ColorButton bubble;

        int colorRand = rand.nextInt(5);

        String colorString = "";

        switch (colorRand) {
            case 0: {
                colorString = "red";
                break;
            }
            case 1: {
                colorString = "blue";
                break;
            }
            case 2: {
                colorString = "green";
                break;
            }
            case 3: {
                colorString = "yellow";
                break;
            }
            case 4: {
                colorString = "purple";
                break;
            }
        }

        bubble = new ColorButton(TwoPlayersActivity.this, colorString);

        final RelativeLayout screenLayout = findViewById(R.id.two_player_screen_layout);

        float p = Resources.getSystem().getDisplayMetrics().density;

        DisplayMetrics display = this.getResources().getDisplayMetrics();

        int width = display.widthPixels;
        int height = display.heightPixels;

        width = width - (int) (20 * p);
        height = height - (int) (300 * p);//////

        int dpLayoutWidth = (int) (width / p);
        int dpLayoutHeight = (int) (height / p);
        int min;

        if (dpLayoutWidth > dpLayoutHeight) {
            min = dpLayoutHeight;
        } else {
            min = dpLayoutWidth;
        }

        if (min > 200) {
            min = min - 150;
        }

        int dpSize = rand.nextInt(min - 50);

        dpSize += 50;

        int size = (int) (dpSize * p);

        bubble.setSize(size);

        int start = rand.nextInt((width - size));
        int top = rand.nextInt((height - size));


        boolean overlapping = false;
        for (int j = 0; j < bubblesList.size(); j++) {

            int x1 = start;
            int y1 = top;

            ViewGroup.MarginLayoutParams lp2 = (ViewGroup.MarginLayoutParams) bubblesList.get(j).getLayoutParams();
            int x2 = lp2.getMarginStart();
            int y2 = lp2.topMargin;

            if (!(x1 > x2 + bubblesList.get(j).getSize()
                    || x2 > x1 + bubble.getSize()
                    || y1 > y2 + bubblesList.get(j).getSize()
                    || y2 > y1 + bubble.getSize()))//if not overlapping
            {
                overlapping = true;
            }
        }

        if (!overlapping) {
            final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(size, size);
            layoutParams.setMargins(0, top, 0, 0);
            layoutParams.setMarginStart(start);

            SynchronousHandler.postAndWait(handler, new Runnable() {
                @Override
                public void run() {

                    bubblesList.add(bubble);
                    screenLayout.addView(bubble, layoutParams);
                }
            });
        }
    }

    void createBubbles() {

        int maxBubbles = 3;
        long timer = 0;
        int timerCounter = 0;
        long timerInterval = 100000000;
        long timerIntervalDecrease = 20000000;
        while (play) {

            timer++;
            if (timer == timerInterval) {
                if (timerCounter > 5 && timerInterval > 5000000 && timerInterval != timerIntervalDecrease) {

                    timerInterval = timerInterval - timerIntervalDecrease;
                    timerCounter = 0;
                }
                if (timerInterval > 5000000 && timerInterval == timerIntervalDecrease) {
                    timerIntervalDecrease = timerIntervalDecrease / 1000;
                }

                removeBubble();
                timer = 0;

                timerCounter++;

            }

            if (bubblesList.size() < maxBubbles)//maxBubbles
            {
                createBubble();
            }
        }
    }

    void removeBubble() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bubblesList.get(0).setVisibility(View.GONE);
                if (bubblesList.get(0).getColor().equals(bottomBarColorStringPlayer1)) {
                    decreaseLifePlayer1();
                }
                else if (bubblesList.get(0).getColor().equals(bottomBarColorStringPlayer2)) {
                    decreaseLifePlayer2();
                }
                bubblesList.remove(0);
            }
        });
    }

    void decreaseLifePlayer1() {
        lifePlayer1--;
        if (gameSound) {
            pling.start();
        }

        switch (lifePlayer1) {
            case 2: {
                ImageView imageView = findViewById(R.id.life1_player1);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 1: {
                ImageView imageView = findViewById(R.id.life2_player1);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 0: {
                ImageView imageView = findViewById(R.id.life3_player1);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                play = false;

                resPlayer1 = getString (R.string.loser);
                resPlayer2 = getString (R.string.winner);

                TwoPlayerDialog twoPlayerDialog = new TwoPlayerDialog(TwoPlayersActivity.this);
                twoPlayerDialog.setCanceledOnTouchOutside(false);
                twoPlayerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                twoPlayerDialog.setCancelable(false);
                twoPlayerDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                twoPlayerDialog.show();

                break;
            }
        }
    }

    void decreaseLifePlayer2() {
        lifePlayer2--;
        if (gameSound) {
            pling.start();
        }

        switch (lifePlayer2) {
            case 2: {
                ImageView imageView = findViewById(R.id.life1_player2);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 1: {
                ImageView imageView = findViewById(R.id.life2_player2);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 0: {
                ImageView imageView = findViewById(R.id.life3_player2);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                play = false;

                resPlayer2 = getString (R.string.loser);
                resPlayer1 = getString (R.string.winner);

                TwoPlayerDialog twoPlayerDialog = new TwoPlayerDialog(TwoPlayersActivity.this);
                twoPlayerDialog.setCanceledOnTouchOutside(false);
                twoPlayerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                twoPlayerDialog.setCancelable(false);
                twoPlayerDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                twoPlayerDialog.show();
                break;
            }
        }
    }

    void newGame() {
        ImageView imageView1 = findViewById(R.id.life1_player1);
        imageView1.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView2 = findViewById(R.id.life2_player1);
        imageView2.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView3 = findViewById(R.id.life3_player1);
        imageView3.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView4 = findViewById(R.id.life1_player2);
        imageView4.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView5 = findViewById(R.id.life2_player2);
        imageView5.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView6 = findViewById(R.id.life3_player2);
        imageView6.setImageResource(R.drawable.ic_favorite_red_24dp);

        RelativeLayout r = findViewById(R.id.two_player_screen_layout);
        r.removeAllViews();

        lifePlayer1 = 3;
        lifePlayer2 = 3;
        play = true;
        bubblesList = new ArrayList();

        new Thread(new Runnable() {
            public void run() {
                createBubbles();
            }
        }).start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        play = false;
        gameMusicPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        playMusic();
        play = true;
        new Thread(new Runnable() {
            public void run() {
                createBubbles();
            }
        }).start();
    }

    void playMusic()
    {
        gameMusicPlayer = MediaPlayer.create(TwoPlayersActivity.this, R.raw.gamemusic);
        if(gameMusic) {
            gameMusicPlayer.setLooping(true);
            gameMusicPlayer.start();
        }
    }
}
