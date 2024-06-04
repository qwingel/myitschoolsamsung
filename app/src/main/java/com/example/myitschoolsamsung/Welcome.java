package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class Welcome extends AppCompatActivity {

    SharedPreferences sPref;
    TextView welcome_text;

    public void startTimer(int startMillis, int finishMillis){
        new CountDownTimer(startMillis, finishMillis){
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }.start();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getSupportActionBar().hide();
        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

//        String login = sPref.getString("login", null);
        String name = sPref.getString("name", null);

        welcome_text = (TextView) findViewById(R.id.welcome_text);

        if (name.equals("Пусто")) welcome_text.setText("Добро\nпожаловать!");
        else welcome_text.setText("Добро\nпожаловать,\n" + name + "!");

        startTimer(1100, 1000);
    }
}
