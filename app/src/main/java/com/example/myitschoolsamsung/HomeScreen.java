package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class HomeScreen extends AppCompatActivity {
    Button logOut;
    boolean isUserLogged = false;
    SharedPreferences sPref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        getSupportActionBar().hide();

        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

        logOut = (Button) findViewById(R.id.logOut_but);
        if (sPref.getString("phone", null) != null) isUserLogged = true;

        logOut.setOnClickListener(view -> {
            if(isUserLogged){
                SharedPreferences.Editor editor = sPref.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), Log_in_window.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });
    }
}
