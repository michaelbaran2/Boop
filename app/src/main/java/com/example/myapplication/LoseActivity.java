package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

public class LoseActivity extends AppCompatActivity {
    private String name;
    private int score;
    private WebView mWebview;
    private WebView wvRates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.NAME);
        score = intent.getIntExtra(GameActivity.SCORE, 0);

        TextView tv = findViewById(R.id.finalScore);
        tv.setText(Integer.toString(score));
        TextView textView = findViewById(R.id.gpsView);
        textView.setText(GpsService.currentLocation);

        TextView wifiView = findViewById(R.id.wifiView);
        Utils.showNetworkName(wifiView, this);

        mWebview = findViewById(R.id.webView);
        wvRates = findViewById(R.id.webViewRate);
        MyWebViewClient.showWebViews(mWebview, wvRates);
        updateHighScores(name, score);

    }


    private void updateHighScores(String newName, int newScore) {
        String first, second, third;
        int firstScore, secondScore, thirdScore;
        SharedPreferences firstPlace = getSharedPreferences(MainActivity.FIRST_PLACE, 0);
        SharedPreferences secondPlace = getSharedPreferences(MainActivity.SECOND_PLACE, 0);
        SharedPreferences thirdPlace = getSharedPreferences(MainActivity.THIRD_PLACE, 0);

        SharedPreferences.Editor firstEditor = firstPlace.edit();
        SharedPreferences.Editor secondEditor = secondPlace.edit();
        SharedPreferences.Editor thirdEditor = thirdPlace.edit();

        first = getName(firstPlace);
        second = getName(secondPlace);
        third = getName(thirdPlace);

        firstScore = firstPlace.getInt(first, 0);
        secondScore = secondPlace.getInt(second, 0);
        thirdScore = thirdPlace.getInt(third, 0);

        if (newScore > firstScore) {
            thirdEditor.remove(third);
            thirdEditor.putInt(second, secondScore);
            thirdEditor.apply();
            secondEditor.remove(second);
            secondEditor.putInt(first, firstScore);
            secondEditor.apply();
            firstEditor.remove(first);
            firstEditor.putInt(newName, newScore);
            firstEditor.apply();
        }
        else if (newScore > secondScore) {
            thirdEditor.remove(third);
            thirdEditor.putInt(second, secondScore);
            thirdEditor.apply();
            secondEditor.remove(second);
            secondEditor.putInt(newName, newScore);
            secondEditor.apply();
        }
        else if (newScore > thirdScore) {
            thirdEditor.remove(third);
            thirdEditor.putInt(newName, newScore);
            thirdEditor.apply();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra(MainActivity.NAME, name);
        startActivity(intent);

    }

    public static String getName(SharedPreferences sp) {
        for (String name: sp.getAll().keySet()) {
            if (name != null) {
                return name;
            }
        }
        return "";
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }




}
