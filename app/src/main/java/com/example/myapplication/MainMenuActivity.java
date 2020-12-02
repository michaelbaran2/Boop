package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class MainMenuActivity extends AppCompatActivity {
    private static String name;
    private WebView mWebview;
    private WebView wvRates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.NAME);

        TextView wifiView = findViewById(R.id.wifiView);
        Utils.showNetworkName(wifiView, this);

        mWebview = findViewById(R.id.webView);
        wvRates = findViewById(R.id.webViewRate);
        MyWebViewClient.showWebViews(mWebview, wvRates);
        TextView textView = findViewById(R.id.gpsView);
        textView.setText(GpsService.currentLocation);


    }

    public void playGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MainActivity.NAME, name);
        startActivity(intent);
    }

    public void clickHighScore(View view) {
        Intent intent = new Intent(this, HighScoresActivity.class);
        intent.putExtra(MainActivity.NAME, name);
        startActivity(intent);
    }

    public void clickLog(View view) {
        Intent intent = new Intent(this, LogActivity.class);
        intent.putExtra(MainActivity.NAME, name);
        startActivity(intent);
    }


}
