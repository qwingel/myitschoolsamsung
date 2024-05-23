package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen extends AppCompatActivity{
    String[][] resultTickets;
    ListView listView;
    String s_fromCity = "Сыктывкар", s_toCity = "Москва";
    TextView tv_FromTo;
    Button profileBut, basketBut, calendarBut, countPassBut, filtersBut;
    AutoCompleteTextView fromWhere, toWhere;

    String[] sz_Cities = {"Сыктывкар", "Москва", "Санкт-Петербург", "Сочи"};
    public String getCity(String city){
        return city.equalsIgnoreCase("Сыктывкар") ? city = "Sktr" : city.equalsIgnoreCase("Сочи") ? city = "Sochi" : city.equalsIgnoreCase("Санкт-Петербург") ? city = "SPB" : "Moscow";
    }
    public void responseTickets(String fromWhere, String toWhere, String date, String filters){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseTicketsMessage> getTickets = userService.tickets(new RequestToServe.TicketsRequest(fromWhere, toWhere, date, filters));
        getTickets.enqueue(new Callback<RequestToServe.ResponseTicketsMessage>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseTicketsMessage> call, Response<RequestToServe.ResponseTicketsMessage> response) {
                if (response.body() != null){
                    if (response.body().status.equals("failed")){
                        Toast.makeText(getApplicationContext(),
                                "Не нашлось билетов =()",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        String[] rows = response.body().message.split("SPLITTING");
                        String[][] ticketMatrix = new String[rows.length][13];
                        for(int i = 0; i < ticketMatrix.length; i++){
                            ticketMatrix[i] = rows[i].replace("'", "").split(", ");
                        }
                        listView = findViewById(R.id.list_view);
                        TicketAdapter ticketAdapter = new TicketAdapter(getApplicationContext(), R.id.list_view, ticketMatrix);
                        listView.setAdapter(ticketAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseTicketsMessage> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
        getSupportActionBar().hide();

        profileBut = (Button) findViewById(R.id.profile);
        basketBut = (Button) findViewById(R.id.basket);
        calendarBut = (Button) findViewById(R.id.calendar);
        countPassBut = (Button) findViewById(R.id.countPass);
        filtersBut = (Button) findViewById(R.id.filters);

        tv_FromTo = (TextView) findViewById(R.id.textViewFromTo);

        fromWhere = (AutoCompleteTextView) findViewById(R.id.from_Where);
        toWhere = (AutoCompleteTextView) findViewById(R.id.to_Where);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_item, R.id.autoCompleteItem, sz_Cities);
        fromWhere.setThreshold(1);
        toWhere.setThreshold(1);
        fromWhere.setAdapter(adapter);
        toWhere.setAdapter(adapter);

        profileBut.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Profile.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        });

        basketBut.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Basket.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        });

//        responseTickets("Sktr", "Moscow", "*", "null_1_null_any_null");

        fromWhere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("Сыктывкар")
                        || s.toString().equalsIgnoreCase("Москва")
                        || s.toString().equalsIgnoreCase("Санкт-Петербург")
                        || s.toString().equalsIgnoreCase("Сочи")){

                    s_fromCity = fromWhere.getText().toString();

                    if (s_fromCity.equalsIgnoreCase("Москва")){
                        s_toCity = toWhere.getText().toString();
                        if (s_toCity.equalsIgnoreCase("")) {
                            s_toCity = "Сыктывкар";
                        }
                    }

                    tv_FromTo.setText(s_fromCity.substring(0, 1).toUpperCase() + s_fromCity.substring(1) + " - " + s_toCity.substring(0, 1).toUpperCase() + s_toCity.substring(1));

                    if (s_fromCity.equalsIgnoreCase(s_toCity)){
                        Toast.makeText(getApplicationContext(), "Вы ввели одинаковые города =)", Toast.LENGTH_LONG).show();
                    } else {
                        responseTickets(getCity(s_fromCity), getCity(s_toCity), "*", "null_1_null_any_null");
                    }
                }
            }
        });

        toWhere.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase("Сыктывкар")
                        || s.toString().equalsIgnoreCase("Москва")
                        || s.toString().equalsIgnoreCase("Санкт-Петербург")
                        || s.toString().equalsIgnoreCase("Сочи")) {

                    s_toCity = toWhere.getText().toString();

                    if (s_toCity.equalsIgnoreCase("Сыктывкар")){
                        s_fromCity = fromWhere.getText().toString();
                        if (s_fromCity.equalsIgnoreCase("")) {
                            s_fromCity = "Москва";
                        }
                    }

                    tv_FromTo.setText(s_fromCity.substring(0, 1).toUpperCase() + s_fromCity.substring(1) + " - " + s_toCity.substring(0, 1).toUpperCase() + s_toCity.substring(1));

                    if (s_fromCity.equalsIgnoreCase(s_toCity)){
                        Toast.makeText(getApplicationContext(), "Вы ввели одинаковые города =)", Toast.LENGTH_LONG).show();
                    } else {
                        responseTickets(getCity(s_fromCity), getCity(s_toCity), "*", "null_1_null_any_null");
                    }
                }
            }
        });
//        basketBut.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), Basket.class);
//            startActivity(intent);
//        });

//        profileBut.setOnClickListener(view -> {
//
//        });
    }
}
