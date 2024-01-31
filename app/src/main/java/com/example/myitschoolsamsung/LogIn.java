package com.example.myitschoolsamsung;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogIn extends AppCompatActivity {
    EditText phoneNumber;
    Button toNext, toBack;

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
                        Toast.makeText(getApplicationContext(),
                                "Account didn't find",
                                Toast.LENGTH_LONG).show();
//                            Intent intent = new Intent(getApplicationContext(), Registration.class);
//                            intent.putExtra("PhoneNumber", phoneNum);
//                            startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), ConfirmNumber.class);
                        intent.putExtra("PhoneNumber", phoneNum);
                        startActivity(intent);
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
}
