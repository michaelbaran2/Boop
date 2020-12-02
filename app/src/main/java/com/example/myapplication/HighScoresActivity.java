package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;



public class HighScoresActivity extends AppCompatActivity {
    private TextView first;
    private TextView second;
    private TextView third;
    private String name;
    private WebView mWebview;
    private WebView wvRates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.NAME);

        first = findViewById(R.id.firstPlace);
        second = findViewById(R.id.secondPlace);
        third = findViewById(R.id.thirdPlace);

        SharedPreferences firstPlace = getSharedPreferences(MainActivity.FIRST_PLACE, 0);
        SharedPreferences secondPlace = getSharedPreferences(MainActivity.SECOND_PLACE, 0);
        SharedPreferences thirdPlace = getSharedPreferences(MainActivity.THIRD_PLACE, 0);

        showHighScore(first, firstPlace, "1st");
        showHighScore(second, secondPlace, "2nd");
        showHighScore(third, thirdPlace, "3rd");

        TextView wifiView = findViewById(R.id.wifiView);
        Utils.showNetworkName(wifiView, this);

        mWebview = findViewById(R.id.webView);
        wvRates = findViewById(R.id.webViewRate);
        MyWebViewClient.showWebViews(mWebview, wvRates);
        TextView textView = findViewById(R.id.gpsView);
        textView.setText(GpsService.currentLocation);

    }


    private void showHighScore(TextView tv, SharedPreferences sp, String place) {
        String name = LoseActivity.getName(sp);
        if (name == null) {
            tv.setText("");
        }
        else if (name.equals("")) {
            tv.setText("");
        }
        else {
            int score = sp.getInt(name, 0);
            tv.setText(place + ": " + name + ", " + score);
        }
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


}
