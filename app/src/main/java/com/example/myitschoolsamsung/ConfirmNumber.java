package com.example.myitschoolsamsung;

import java.util.Timer;
import java.util.TimerTask;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmNumber extends AppCompatActivity {
    TextView phoneNumber, codeTimer;
    EditText phoneCode;
    Button codeAgain;
    SharedPreferences sPref;
    final String[] forSavePreferences = {"Login", "Name", "Surname", "Passport_Series", "Passport_Number", "Birthday", "Phone"};


    public void startTimer(int startMillis, int finishMillis){
        new CountDownTimer(startMillis, finishMillis){
            @Override
            public void onTick(long l) {
                if(l > 10000){
                    codeTimer.setText("Отправить код повторно 00:" + l/1000);
                } else {
                    codeTimer.setText("Отправить код повторно 00:0" + l/1000);
                }
            }

            @Override
            public void onFinish() {
                codeTimer.setVisibility(View.INVISIBLE);
                codeAgain.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void responseCode(String getIntentPhoneText, String str){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseCodeMessage> inSystem = userService.code(new RequestToServe.CodeRequest(getIntentPhoneText, str));
        inSystem.enqueue(new Callback<RequestToServe.ResponseCodeMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseCodeMessage> call, Response<RequestToServe.ResponseCodeMessage> response) {
                if(response.body() != null){
                    if(response.body().status.equals("failed")){
                        Intent intent = new Intent(getApplicationContext(), Registration.class);
                        intent.putExtra("PhoneNumber", getIntentPhoneText);
                        startActivity(intent);
                    } else {
                        String welcome = response.body().message;
                        String[] user_data = response.body().user_data.split(" ");
                        sPref = getSharedPreferences("Account", MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putString("Phone", getIntentPhoneText);
                        for(int i = 0; i < user_data.length; i++){
                            ed.putString(forSavePreferences[i], user_data[i]);
                        }
                        ed.commit();
                        Toast.makeText(
                                getApplicationContext(),
                                welcome,
                                Toast.LENGTH_LONG
                        ).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseCodeMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.confirmnumber);
        getSupportActionBar().hide();

        Intent intent = getIntent();
        String getIntentPhoneText = intent.getStringExtra("PhoneNumber");

        codeAgain = (Button) findViewById(R.id.code_again);

        codeTimer = (TextView) findViewById(R.id.codeTimer);

        phoneNumber = (TextView) findViewById(R.id.setTextPhone);
        phoneNumber.setText("Код отправлен на номер +" + getIntentPhoneText);

        phoneCode = (EditText) findViewById(R.id.codeForConfrirm);
        phoneCode.setText("");

        startTimer(61000, 1000);
        phoneCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() >= 4){
                    String str = phoneCode.getText().toString();
//                    responseCode(getIntentPhoneText, str);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        codeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer(60000, 1000);
//                responseCode(getIntentPhoneText, phoneCode.getText().toString());
                codeTimer.setVisibility(View.INVISIBLE);
                codeTimer.setVisibility(View.VISIBLE);
            }
        });
    }
}