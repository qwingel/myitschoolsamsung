package com.example.myitschoolsamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Registration extends AppCompatActivity {
    EditText password;
    Button toNext;

    public void responseRegistration(String number, String pass){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseRegistrationMessage> toRegistr = userService.registration(new RequestToServe.RegistrationRequest(number, pass));
        toRegistr.enqueue(new Callback<RequestToServe.ResponseRegistrationMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseRegistrationMessage> call, Response<RequestToServe.ResponseRegistrationMessage> response) {
                if(response.body() != null){
                    if(response.body().status.equals("failed")){
                        Toast.makeText(getApplicationContext(),
                                "Failed",
                                Toast.LENGTH_LONG).show();
                    } else if(response.body().status.equals("Error")) {
                        Toast.makeText(getApplicationContext(),
                                "Ошибка",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ConfirmNumber.class);
                        intent.putExtra("PhoneNumber", number);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseRegistrationMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.registration);
        getSupportActionBar().hide();

        Intent bIntent = getIntent();
        String phoneNum = bIntent.getStringExtra("PhoneNumber");

        password = (EditText) findViewById(R.id.password);
        password.setText("");
        toNext = (Button) findViewById(R.id.to_next);
        toNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = password.getText().toString();
                responseRegistration(phoneNum, pass);
            }
        });
    }
}
