package com.example.myapplication;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.TextView;

class Utils {
    static String NO_WIFI_CONNECTION = "No wifi connection.";

    static String getWifiName(Context context) {
        WifiManager manager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (manager.isWifiEnabled()) {
            WifiInfo wifiInfo = manager.getConnectionInfo();
            if (wifiInfo != null) {
                NetworkInfo.DetailedState state = WifiInfo.getDetailedStateOf(wifiInfo.getSupplicantState());
                if (state == NetworkInfo.DetailedState.CONNECTED || state == NetworkInfo.DetailedState.OBTAINING_IPADDR) {
                    return wifiInfo.getSSID();
                }
            }
        }
        return null;
    }

    static void showNetworkName(TextView wifiView, Context context) {
        String networkName = getWifiName(context);
        if (networkName != null) {
            wifiView.setText(networkName);
        }
        else {
            wifiView.setText(NO_WIFI_CONNECTION);
        }
    }
}
