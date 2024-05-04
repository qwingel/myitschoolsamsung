package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    Button Logout, toTickets, telegram;
    TextView name, surname, number;
    SharedPreferences sPref;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

        String userName = sPref.getString("name", null);
        String userSurname = sPref.getString("surname", null);
        String userPhone = sPref.getString("phone", null);

        name = findViewById(R.id.profileName); name.setText(userName);
        surname = findViewById(R.id.profileSurname); surname.setText(userSurname);
        number = findViewById(R.id.profileNum); number.setText(userPhone);

        Logout = (Button) findViewById(R.id.logout);
        toTickets = (Button) findViewById(R.id.toTicketsActivity);
        telegram = (Button) findViewById(R.id.telegramBut); telegram.setText("");

        Logout.setOnClickListener(view -> {
            this.getSharedPreferences("account", Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });

        toTickets.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        });

        telegram.setOnClickListener(view -> {
            Uri uriUrl = Uri.parse("https://t.me/girlsarethesame");
            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
            startActivity(launchBrowser);
        });
    }
}
