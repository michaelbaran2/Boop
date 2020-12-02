package com.example.myapplication;

import android.webkit.WebView;
import android.webkit.WebViewClient;



class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    static void showWebViews(WebView currentTimeWebView, WebView ratesWebView) {
        currentTimeWebView.setWebViewClient(new MyWebViewClient());
        currentTimeWebView.getSettings().setJavaScriptEnabled(true);
        currentTimeWebView.loadUrl("http://192.168.3.12:8080/index.html");
        ratesWebView.setWebViewClient(new MyWebViewClient());
        ratesWebView.getSettings().setJavaScriptEnabled(true);
        ratesWebView.loadUrl("https://free.currconv.com/api/v7/convert?apiKey=8ce1f30dd1217843b532&q=USD_ILS&compact=y");
    }
}