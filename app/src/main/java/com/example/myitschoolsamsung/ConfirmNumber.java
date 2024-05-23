package com.example.myitschoolsamsung;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfirmNumber extends AppCompatActivity implements GestureDetector.OnGestureListener {
    TextView phoneNumber, codeTimer;
    EditText phoneCode;
    Button codeAgain;
    SharedPreferences sPref;

    private float x1, x2;
    private static int MIN_DISTANCE = 150;
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
        sPref = getSharedPreferences("account", Context.MODE_PRIVATE);

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
                        Toast.makeText(
                                getApplicationContext(),
                                "Неверный код",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        String[] user_data = response.body().user_data.split(" ");
                        String phone = getIntentPhoneText;
                        String pass = user_data[0];
                        String name = user_data[1];
                        String surname = user_data[2];
                        String pass_series = user_data[3];
                        String pass_number = user_data[4];
                        String birthday = user_data[5];

                        SharedPreferences.Editor editor = sPref.edit();
                        editor.putString("phone", phone);
                        editor.putString("password", pass);
//                        editor.putString("login", login);
                        editor.putString("name", name);
                        editor.putString("surname", surname);
                        editor.putString("passport_series", pass_series);
                        editor.putString("passport_number", pass_number);
                        editor.putString("birthday", birthday);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Welcome.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
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
                if(charSequence.length() == 4){
                    String str = phoneCode.getText().toString();
                    responseCode(getIntentPhoneText, str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        codeAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTimer(61000, 1000);
                responseCode(getIntentPhoneText, phoneCode.getText().toString());
                codeAgain.setVisibility(View.INVISIBLE);
                codeTimer.setVisibility(View.VISIBLE);
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                if (x2 - x1 > MIN_DISTANCE){
                    Intent intent = new Intent(getApplicationContext(), LogIn.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
                }
        }
        return super.onTouchEvent(event);
    }
    @Override
    public boolean onDown(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(@NonNull MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(@NonNull MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(@NonNull MotionEvent motionEvent, @NonNull MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}