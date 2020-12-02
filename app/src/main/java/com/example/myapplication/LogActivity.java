package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import java.util.*;

public class LogActivity extends AppCompatActivity {
    private TextView logsView;
    private String name;
    private WebView mWebview;
    private WebView wvRates;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        Intent intent = getIntent();
        name = intent.getStringExtra(MainActivity.NAME);

        logsView = findViewById(R.id.logs);
        SharedPreferences log = getSharedPreferences(MainActivity.LOG, 0);
        showLog(logsView, log);

        TextView wifiView = findViewById(R.id.wifiView);
        Utils.showNetworkName(wifiView, this);

        mWebview = findViewById(R.id.webView);
        wvRates = findViewById(R.id.webViewRate);
        MyWebViewClient.showWebViews(mWebview, wvRates);
        TextView textView = findViewById(R.id.gpsView);
        textView.setText(GpsService.currentLocation);



    }

    private void showLog(TextView tv, SharedPreferences sp) {
        ArrayList<String> sortedKeys = new ArrayList<>(sp.getAll().keySet()); // not sorted yet
        Collections.sort(sortedKeys); // sorted

        for (String key: sortedKeys) {
            String entry = sp.getString(key,"");
            tv.append(entry + "\n");
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
