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
            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        if(!isConnectingToNetwork(getApplicationContext()) && !checkWifiOnAndConnected()){
            Intent intent = new Intent(getApplicationContext(), NotConnected.class);
            startActivity(intent);
        }
        //^^^^^^^^^^^^ проверка на подключение к интернету ^^^^^^^^^^^^^^^^^

        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        for_test = (Button) findViewById(R.id.testet);

        sPref = getSharedPreferences("Account", MODE_PRIVATE);
        String savedText = sPref.getString("Login", "");
        for_test.setText(savedText);
        for_test.setOnClickListener(view -> {
            Intent toLoginWindow = new Intent(getApplicationContext(), Log_in_window.class);
            startActivity(toLoginWindow);
        });
    }

}
