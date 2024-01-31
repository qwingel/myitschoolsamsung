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
    TextView phoneNumber;
    EditText password, login, birthday, name, surname;
    Button toNext, toCancel;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
//        setContentView(R.layout.registration);
        getSupportActionBar().hide();
//        toNext = (Button) findViewById(R.id.tonext);
//        toCancel = (Button) findViewById(R.id.tocancel);
//
//        phoneNumber = (TextView) findViewById(R.id.phoneNumber);
//
//        login = (EditText) findViewById(R.id.login);
//        name = (EditText) findViewById(R.id.name);
//        surname = (EditText) findViewById(R.id.surname);
//        birthday = (EditText) findViewById(R.id.birthday);
//        password = (EditText) findViewById(R.id.pass);

        Intent bIntent = getIntent();
        String phoneNum = bIntent.getStringExtra("PhoneNumber");

        phoneNumber.setText(phoneNum);
        toNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = phoneNum + login.getText().toString() + name.getText().toString()
                        + surname.getText().toString() + birthday.getText().toString() + password.getText().toString();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
                Call<RequestToServe.ResponseMessage> toRegistration = userService.registration(new RequestToServe.RegistrationRequest(phoneNum, data));
                toRegistration.enqueue(new Callback<RequestToServe.ResponseMessage>() {
                    @Override
                    public void onResponse(Call<RequestToServe.ResponseMessage> call, Response<RequestToServe.ResponseMessage> response) {
                        if(response.body() != null){
                            if(response.body().status.equals("Failed")){
                                Toast.makeText(
                                        getApplicationContext(),
                                        "Ошибка",
                                        Toast.LENGTH_LONG
                                ).show();
                                Intent intent = new Intent(getApplicationContext(), Log_in_window.class);
                                startActivity(intent);
                            } else {
                                String welcome = response.body().message;
                                String[] user_data = data.split(" ");
//                                UserDATAS userDatas = new UserDATAS();
//                                userDatas.setForIt(getIntentPhoneText, user_data[0], user_data[1], user_data[2], user_data[3], user_data[4], user_data[5]);
                                Toast.makeText(
                                        getApplicationContext(),
                                        welcome,
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RequestToServe.ResponseMessage> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });

        toCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Log_in_window.class);
                startActivity(intent);
            }
        });
    }
}
