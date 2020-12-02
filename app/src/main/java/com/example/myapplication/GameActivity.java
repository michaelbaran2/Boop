package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private static String INIT_MESSAGE = "To start boop the red button.";
    private static int score = -1; // -1 on init, genButton resets it to 0 when game starts.
    private static long timeInterval; // needs to be 3 secs at starts and drop by 5% for every click
    private static CountDownTimer timer;
    private static String name;
    protected static String SCORE = "score";
    private WebView mWebview;
    private WebView wvRates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.NAME);
        score = -1;


        TextView wifiView = findViewById(R.id.wifiView);
        Utils.showNetworkName(wifiView, this);
        TextView textView = findViewById(R.id.gpsView);
        textView.setText(GpsService.currentLocation);


        mWebview = findViewById(R.id.webView);
        wvRates = findViewById(R.id.webViewRate);
        MyWebViewClient.showWebViews(mWebview, wvRates);


        Toast toast = Toast.makeText(getApplicationContext(), INIT_MESSAGE, Toast.LENGTH_SHORT);
        toast.show();
    }




    public void genButton(View view) {
        final ConstraintLayout layout = findViewById(R.id.frame_layout);
        Random rand = new Random();
        Button button = findViewById(R.id.red_button);
        int diameter = button.getWidth();
        int maxHeight = layout.getMeasuredHeight() - diameter;
        int maxWidth = layout.getMeasuredWidth() - diameter;
        int genHeight = rand.nextInt(maxHeight);
        int genWidth = rand.nextInt(maxWidth);
        button.setY(genHeight);
        button.setX(genWidth);
        if (score < 0) {
            init();
            updateLogGameStart();
        }
        else {
            updateLog(genWidth, genHeight);
            updateScore();
            updateTimer((long) (0.95 * timeInterval));
        }
    }



    private void updateScore() {
        score++;
        TextView textView = findViewById(R.id.show_score);
        if (score < 10) {
            textView.setText("Score: 0" + score);
        }
        else {
            textView.setText("Score: " + score);
        }
    }



    private void init() {
        score = 0;
        initTimer(3000);
    }

    private void initTimer(long interval) {
        timeInterval = interval;
        timer = new CountDownTimer(interval, 10) {
            @Override
            public void onTick(long millisUntilFinished) {
                TextView textView = findViewById(R.id.timerView);
                textView.setText("Seconds left: " + formatTimeLeft(millisUntilFinished));
            }

            @Override
            public void onFinish() {
                Intent i = new Intent(GameActivity.this, LoseActivity.class);
                i.putExtra(MainActivity.NAME, name);
                i.putExtra(SCORE, score);
                updateLogGameEnd();
                startActivity(i);
            }
        };
        timer.start();
    }

    private static String formatTimeLeft(long millisUntilFinish) {
        int secs, centiSecs, milliSecs;
        secs = (int) (millisUntilFinish / 1000);
        milliSecs = (int)(millisUntilFinish) % 1000; // millisecs left in addition to secs
        centiSecs = milliSecs / 10;
        if (centiSecs <= 9) {
            return "0" + secs + ".0" + centiSecs;
        }
        return "0" + secs + "." + centiSecs;
    }

    private void updateTimer(long newInterval) {
        timeInterval = newInterval;
        timer.cancel();
        initTimer(newInterval);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(MainActivity.NAME, name);
        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    private void updateLog(int x, int y) {
        SharedPreferences log = getSharedPreferences(MainActivity.LOG, 0);
        SharedPreferences.Editor editor = log.edit();
        String key = String.valueOf(System.currentTimeMillis());
        editor.putString(key, name + " booped on " + x + ", " + y);
        editor.apply();
    }

    private void updateLogGameStart() {
        SharedPreferences log = getSharedPreferences(MainActivity.LOG, 0);
        SharedPreferences.Editor editor = log.edit();
        String key = String.valueOf(System.currentTimeMillis());
        editor.putString(key, name + "'s game start");
        editor.apply();
    }

    private void updateLogGameEnd() {
        SharedPreferences log = getSharedPreferences(MainActivity.LOG, 0);
        SharedPreferences.Editor editor = log.edit();
        String key = String.valueOf(System.currentTimeMillis());
        editor.putString(key, name + "'s game end");
        editor.apply();
    }


}
