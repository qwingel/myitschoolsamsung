package com.example.myitschoolsamsung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button for_test;
    SharedPreferences sPref;
    public boolean isConnectingToNetwork(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return !(ni == null);
    }

    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            return wifiInfo.getNetworkId() != -1; // Not connected to an access poin
// Connected to an access point
        }
        else return false; // Wi-Fi adapter is OFF
    }

    public void startTimer(int startMillis, int finishMillis){
        new CountDownTimer(startMillis, finishMillis){
            @Override
            public void onTick(long l) {
            }
            @Override
            public void onFinish() {
                sPref = getSharedPreferences("account", MODE_PRIVATE);
                String savedText = sPref.getString("phone", null);
                Intent intent;
                if(savedText == null) {
                    intent = new Intent(getApplicationContext(), Log_in_window.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                } else {
                    intent = new Intent(getApplicationContext(), Welcome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                }
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }.start();
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        if(!isConnectingToNetwork(getApplicationContext()) && !checkWifiOnAndConnected()){
            Intent intent = new Intent(getApplicationContext(), NotConnected.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        } else {
        //^^^^^^^^^^^^ проверка на подключение к интернету ^^^^^^^^^^^^^^^^^

            setContentView(R.layout.activity_main);
            getSupportActionBar().hide();

            startTimer(300, 1000);
        }
    }
}
