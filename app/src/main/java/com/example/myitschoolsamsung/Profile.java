package com.example.myitschoolsamsung;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvContract;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.cert.X509CRLEntry;

public class Profile extends AppCompatActivity {

    Button Logout, toTickets, telegram, editProfile, confirmPassForEdit;
    TextView name, surname, number;
    EditText et_Password;
    SharedPreferences sPref;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        getSupportActionBar().hide();

        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

        String userName = sPref.getString("name", null);
        String userSurname = sPref.getString("surname", null);
        String userPhone = sPref.getString("phone", null);
        String userPassword = sPref.getString("password", null);

        name = findViewById(R.id.profileName); name.setText(userName);
        surname = findViewById(R.id.profileSurname); surname.setText(userSurname);
        number = findViewById(R.id.profileNum); number.setText(userPhone);

        Logout = (Button) findViewById(R.id.logout);
        toTickets = (Button) findViewById(R.id.toTicketsActivity);
        telegram = (Button) findViewById(R.id.telegramBut); telegram.setText("");
        editProfile = (Button) findViewById(R.id.b_profileEdit);

        AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
        final View customView = getLayoutInflater().inflate(R.layout.dialog_password, null);
        builder.setView(customView);
        confirmPassForEdit = (Button) customView.findViewById(R.id.confButForEdit);
        et_Password = (EditText) customView.findViewById(R.id.passconf);

        Logout.setOnClickListener(view -> {
            this.getSharedPreferences("account", Context.MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

        confirmPassForEdit.setOnClickListener(view -> {
            if(et_Password.getText().toString().equals(userPassword)){
                Toast.makeText(getApplicationContext(), "nice", Toast.LENGTH_LONG).show();
            }
        });
        editProfile.setOnClickListener(view -> {
            if (customView.getParent() != null){
                ((ViewGroup)customView.getParent()).removeView(customView);
            }
            AlertDialog dialog = builder.create();
            if(dialog.isShowing()) dialog.dismiss();
            dialog.show();
        });
    }
}
