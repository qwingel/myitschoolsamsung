package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class NotConnected extends AppCompatActivity {

    Button toConnectAgain;
    public boolean isConnectingToNetwork(Context applicationContext){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return !(ni == null);
    }

    public boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    public void startTimer(int startMillis, int finishMillis){
        new CountDownTimer(startMillis, finishMillis){
            @Override
            public void onTick(long l) {
                if(isConnectingToNetwork(getApplicationContext()) || checkWifiOnAndConnected()) {
                    this.cancel();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFinish(){ toConnectAgain.setVisibility(View.VISIBLE); }
        }.start();
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.not_connected);
        getSupportActionBar().hide();

        toConnectAgain = findViewById(R.id.connect_again);

        toConnectAgain.setOnClickListener(view -> {
            toConnectAgain.setVisibility(View.INVISIBLE);
            startTimer(7000, 1000);
        });
    }
}
