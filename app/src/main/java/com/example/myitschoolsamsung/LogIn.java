package com.example.myitschoolsamsung;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity implements GestureDetector.OnGestureListener {
    EditText phoneNumber;
    TextView tv;
    Button toNext, toBack;
    private float x1, x2;
    private static int MIN_DISTANCE = 150;

    public void responsePhone(String phoneNum){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseLoginMessage> toLogin = userService.login(new RequestToServe.LoginRequest(phoneNum));

        toLogin.enqueue(new Callback<RequestToServe.ResponseLoginMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseLoginMessage> call, Response<RequestToServe.ResponseLoginMessage> response) {
                if(response.body() != null){
                    if(response.body().status.equals("Failed")){
                            Intent intent = new Intent(getApplicationContext(), Registration.class);
                            intent.putExtra("PhoneNumber", phoneNum);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);

                    } else if(response.body().status.equals("Error")){
                        tv.setVisibility(View.VISIBLE);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ConfirmNumber.class);
                        intent.putExtra("PhoneNumber", phoneNum);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseLoginMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.log_in_window);
        getSupportActionBar().hide();
        toNext = (Button) findViewById(R.id.to_next);

        tv = (TextView) findViewById(R.id.tv_Error);

        phoneNumber = (EditText) findViewById(R.id.phonenumber);
        phoneNumber.setText("");

        toNext.setOnClickListener(view -> {
            String phoneNum = "7" + phoneNumber.getText().toString();
            responsePhone(phoneNum);
//            Intent intent = new Intent(getApplicationContext(), ConfirmNumber.class);
//            intent.putExtra("PhoneNumber", phoneNum);
//            startActivity(intent);
        });
    }

    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();

                if ( x2 - x1 > MIN_DISTANCE) {
                    Intent intent = new Intent(getApplicationContext(), Log_in_window.class);
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
