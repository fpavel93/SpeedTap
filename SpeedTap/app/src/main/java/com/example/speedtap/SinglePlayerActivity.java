package com.example.speedtap;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class SinglePlayerActivity extends AppCompatActivity {

    int life;
    int hearts = 0;
    boolean play;
    int score;

    MediaPlayer blop;
    MediaPlayer pling;
    MediaPlayer lifeUp;
    MediaPlayer gameMusicPlayer;

    boolean gameSound;
    boolean gameMusic;

    ArrayList<ColorButton> bubblesList;

    Handler handler;
    String bottomBarColorString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game_screen);
        getSupportActionBar().hide();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        RelativeLayout screenLayout = findViewById(R.id.single_game_screen_layout);
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.DISAPPEARING);
        screenLayout.setLayoutTransition(layoutTransition);

        handler = new Handler();

        blop = MediaPlayer.create(SinglePlayerActivity.this, R.raw.blop);
        pling = MediaPlayer.create(SinglePlayerActivity.this, R.raw.pling);
        lifeUp = MediaPlayer.create(SinglePlayerActivity.this, R.raw.lifeup);

        TextView scoreTV = findViewById(R.id.user_score_textview);
        score = 0;
        scoreTV.setText("" + score);

        gameSound = getIntent().getBooleanExtra("gameSound", true);
        gameMusic = getIntent().getBooleanExtra("gameMusic", true);

        life = 3;
        hearts = 0;
        bubblesList = new ArrayList();
    }

    Boolean createBubble(String str) {

        final Boolean[] isAdd = {false};
        Random rand = new Random();
        final ColorButton bubble;

        if(str.equals("Heart"))
        {
            bubble = new ColorButton(SinglePlayerActivity.this, "Heart");

            bubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    addLife();
                    v.setVisibility(View.GONE);
                    bubblesList.remove(v);
                }
            });
        }
        else {
            if (isOneColorVisible()) {
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

                bubble = new ColorButton(SinglePlayerActivity.this, colorString);
            } else {
                bubble = createButtonBottomBarColor();
            }


            bubble.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout colorLayout = findViewById(R.id.bottom_bar);
                    ColorButton vv = (ColorButton) v;

                    TextViewBottomBar tv = (TextViewBottomBar) colorLayout.getChildAt(0);

                    if (vv.getColor().equals(tv.getColor())) {
                        if (gameSound) {
                            blop.start();
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                TextView scoreTV = findViewById(R.id.user_score_textview);
                                score = score + 10;
                                scoreTV.setText("" + score);
                            }
                        });
                    } else {
                        decreaseLife();
                    }
                    v.setVisibility(View.GONE);
                    bubblesList.remove(v);
                }
            });
        }

        final RelativeLayout screenLayout = findViewById(R.id.single_game_screen_layout);

        float p = Resources.getSystem().getDisplayMetrics().density;

        DisplayMetrics display = this.getResources().getDisplayMetrics();

        int width = display.widthPixels;
        int height = display.heightPixels;

        width = width - (int) (20 * p);
        height = height - (int) (200 * p);

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
                    isAdd[0] = true;
                }
            });


        }
        return isAdd[0];
    }

    void createBubbles() {

        final Random rand = new Random();

        int colorRand = rand.nextInt(5);

        switch (colorRand) {
            case 0: {
                bottomBarColorString = "red";
                break;
            }
            case 1: {
                bottomBarColorString = "blue";
                break;
            }
            case 2: {
                bottomBarColorString = "green";
                break;
            }
            case 3: {
                bottomBarColorString = "yellow";
                break;
            }
            case 4: {
                bottomBarColorString = "purple";
                break;
            }
        }

        final LinearLayout l = findViewById(R.id.bottom_bar);
        handler.post(new Runnable() {
            @Override
            public void run() {
                l.removeAllViews();
            }
        });

        final TextViewBottomBar tvbb = new TextViewBottomBar(SinglePlayerActivity.this, bottomBarColorString);

        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
        tvbb.setLayoutParams(params);
        handler.post(new Runnable() {
            @Override
            public void run() {
                l.addView(tvbb);
            }
        });

        int maxBubbles = 3;
        long timer = 0;
        int maxScore = 100;
        int timerCounter = 0;
        long timerInterval = 100000000;
        long timerIntervalDecrease = 20000000;
        int changeColor = rand.nextInt(100);
        changeColor = changeColor + 100;
        while (play) {

            timer++;
            if (timer == timerInterval) {
                if (score > maxScore && timerInterval > 5000000 && timerInterval != timerIntervalDecrease) {
                    maxScore = maxScore + 100;
                    timerInterval = timerInterval - timerIntervalDecrease;
                }
                if(timerInterval > 5000000 && timerInterval == timerIntervalDecrease)
                {
                    timerIntervalDecrease = timerIntervalDecrease/1000;
                }

                removeBubble();
                timer = 0;

                if(maxBubbles <= 6) // max bubbles on screen that will be reach in game
                {
                    timerCounter++;
                    if(timerCounter == 10){
                        maxBubbles++;
                        timerCounter = 0;
                    }
                }
            }

            if (score > changeColor) {
                changeColor = changeColor + 100 + rand.nextInt(100);

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        int colorRand = rand.nextInt(5);
                        switch (colorRand) {
                            case 0: {
                                bottomBarColorString = "red";
                                break;
                            }
                            case 1: {
                                bottomBarColorString = "blue";
                                break;
                            }
                            case 2: {
                                bottomBarColorString = "green";
                                break;
                            }
                            case 3: {
                                bottomBarColorString = "yellow";
                                break;
                            }
                            case 4: {
                                bottomBarColorString = "purple";
                                break;
                            }
                        }

                        final TextViewBottomBar tv = new TextViewBottomBar(SinglePlayerActivity.this, bottomBarColorString);
                        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1f);
                        tv.setLayoutParams(params);
                        l.removeViewAt(0);
                        l.addView(tv);
                    }
                });
            }

            if (bubblesList.size() < maxBubbles)//maxBubbles
            {
                if (life < 3) {
                    if(hearts == 0) {
                        int chance = rand.nextInt(50);
                        if (chance == 0) {
                            if(createBubble("Heart")) {
                                hearts = 1;
                            }
                        }
                    }
                }
                createBubble("");
            }
        }
    }

    void removeBubble() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                bubblesList.get(0).setVisibility(View.GONE);
                if (bubblesList.get(0).getColor().equals(bottomBarColorString)) {
                    decreaseLife();
                }
                else if(bubblesList.get(0).getColor().equals("Heart"))
                {
                    hearts = 0;
                }
                bubblesList.remove(0);
            }
        });
    }

    boolean isOneColorVisible() {
        String barColor = bottomBarColorString;
        boolean colorPresent = false;

        for (int j = 0; j < bubblesList.size(); j++) {
            if (bubblesList.get(j).getColor().equals(barColor)) {
                colorPresent = true;
                break;
            }
        }
        return colorPresent;
    }

    ColorButton createButtonBottomBarColor() {

        return new ColorButton(SinglePlayerActivity.this, bottomBarColorString);
    }

    void addLife()
    {
        if (gameSound) {
            lifeUp.start();
        }
        hearts = 0;
        life++;
        switch (life) {
            case 3: {
                ImageView imageView = findViewById(R.id.life1);
                imageView.setImageResource(R.drawable.ic_favorite_red_24dp);
                break;
            }
            case 2: {
                ImageView imageView = findViewById(R.id.life2);
                imageView.setImageResource(R.drawable.ic_favorite_red_24dp);
                break;
            }
            case 1: {
                ImageView imageView = findViewById(R.id.life3);
                imageView.setImageResource(R.drawable.ic_favorite_red_24dp);
                break;
            }
        }
    }

    void decreaseLife() {
        life--;
        if (gameSound) {
            pling.start();
        }

        switch (life) {
            case 2: {
                ImageView imageView = findViewById(R.id.life1);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 1: {
                ImageView imageView = findViewById(R.id.life2);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                break;
            }
            case 0: {
                ImageView imageView = findViewById(R.id.life3);
                imageView.setImageResource(R.drawable.ic_favorite_border_red_24dp);
                play = false;

                SharedPreferences sp = getSharedPreferences("score", 0);
                int minScore = sp.getInt("minScore", -1);
                if (score > minScore) {
                    NewHighScoreDialog newHighScoreDialog = new NewHighScoreDialog(SinglePlayerActivity.this);
                    newHighScoreDialog.setCanceledOnTouchOutside(false);
                    newHighScoreDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    newHighScoreDialog.setCancelable(false);
                    newHighScoreDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    newHighScoreDialog.show();
                } else {
                    GameOverDialog gameOverDialog = new GameOverDialog(SinglePlayerActivity.this);
                    gameOverDialog.setCanceledOnTouchOutside(false);
                    gameOverDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    gameOverDialog.setCancelable(false);
                    gameOverDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme;
                    gameOverDialog.show();
                }
                break;
            }
        }
    }

    void newGame() {
        ImageView imageView1 = findViewById(R.id.life1);
        imageView1.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView2 = findViewById(R.id.life2);
        imageView2.setImageResource(R.drawable.ic_favorite_red_24dp);

        ImageView imageView3 = findViewById(R.id.life3);
        imageView3.setImageResource(R.drawable.ic_favorite_red_24dp);

        RelativeLayout r = findViewById(R.id.single_game_screen_layout);
        r.removeAllViews();

        TextView scoreTV = findViewById(R.id.user_score_textview);
        score = 0;
        scoreTV.setText("" + score);

        life = 3;
        hearts = 0;
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
        gameMusicPlayer = MediaPlayer.create(SinglePlayerActivity.this, R.raw.gamemusic);
        if(gameMusic) {
            gameMusicPlayer.setLooping(true);
            gameMusicPlayer.start();
        }
    }
}