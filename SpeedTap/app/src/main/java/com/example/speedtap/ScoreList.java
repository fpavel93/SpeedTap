package com.example.speedtap;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreList extends ListActivity {

    MediaPlayer gameMusicPlayer;

    boolean gameMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.highscore_list);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameMusic = getIntent().getBooleanExtra("gameMusic", true);

        SharedPreferences sp = getSharedPreferences("score", 0);
        SharedPreferences.Editor editor = sp.edit();

        List<Map<String, Object>> data = new ArrayList<>();

        int imgRes = R.drawable.ic_prize_svgrepo_com;
        int score = this.getIntent().getIntExtra("score", -1);
        String name = this.getIntent().getStringExtra("name");
        int nextScore;
        int tempScore;
        String tempName;
        int i;

        if (score == -1) {
            i = 0;

            nextScore = sp.getInt("score" + i, -1);
            while (nextScore != -1) {

                HashMap<String, Object> currScore = new HashMap<>();
                currScore.put("image", imgRes);
                currScore.put("name", sp.getString("name" + i, null));
                currScore.put("score", nextScore);
                data.add(i, currScore);
                i++;
                nextScore = sp.getInt("score" + i, -1);
            }
        } else {
            i = 0;
            nextScore = sp.getInt("score" + i, -1);
            while (nextScore > score) {
                HashMap<String, Object> currScore = new HashMap<>();
                currScore.put("image", imgRes);
                currScore.put("name", sp.getString("name" + i, null));
                currScore.put("score", nextScore);
                data.add(i, currScore);
                i++;
                nextScore = sp.getInt("score" + i, -1);
            }
            if (i < 10) {
                if (nextScore != -1) {

                    tempScore = nextScore;
                    tempName = sp.getString("name" + i, null);

                    editor.putInt("score" + i, score);
                    editor.putString("name" + i, name);

                    HashMap<String, Object> currScore = new HashMap<>();
                    currScore.put("image", imgRes);
                    currScore.put("name", name);
                    currScore.put("score", score);
                    data.add(i, currScore);
                    i++;
                    while (i < 10 && tempScore != -1) {
                        HashMap<String, Object> currScore1 = new HashMap<>();
                        currScore1.put("image", imgRes);
                        currScore1.put("name", tempName);
                        currScore1.put("score", tempScore);
                        data.add(i, currScore1);

                        score = tempScore;
                        name = tempName;

                        tempScore = sp.getInt("score" + i, -1);
                        ;
                        tempName = sp.getString("name" + i, null);

                        editor.putInt("score" + i, score);
                        editor.putString("name" + i, name);

                        i++;
                    }

                } else {
                    HashMap<String, Object> currScore = new HashMap<>();
                    currScore.put("image", imgRes);
                    currScore.put("name", name);
                    currScore.put("score", score);
                    data.add(i, currScore);

                    editor.putInt("score" + i, score);
                    editor.putString("name" + i, name);

                    i++;
                }
            }

            editor.putInt("minScore", sp.getInt("score"+(i-1),-1));
            editor.commit();
        }

        String[] from = {"image", "name", "score"};
        int[] to = {R.id.score_img, R.id.score_name, R.id.score_val};

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.score_cell, from, to);
        setListAdapter(simpleAdapter);
    }

    void playMusic()
    {
        gameMusicPlayer = MediaPlayer.create(ScoreList.this, R.raw.gamemusic);
        if(gameMusic) {
            gameMusicPlayer.setLooping(true);
            gameMusicPlayer.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        playMusic();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameMusicPlayer.stop();
    }
}
