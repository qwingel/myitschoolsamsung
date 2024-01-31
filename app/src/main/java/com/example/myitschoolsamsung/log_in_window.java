package com.example.myitschoolsamsung;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Log_in_window extends AppCompatActivity {
    Button authorization, toCancel;
    @Override
    protected void  onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.authorezation);
        getSupportActionBar().hide();

        authorization = (Button) findViewById(R.id.authorization);
        toCancel = (Button) findViewById(R.id.to_back);

        authorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(getApplicationContext(), LogIn.class);
                startActivity(toLogin);
            }
        });
        toCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLater = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(toLater);
            }
        });
    }
}
