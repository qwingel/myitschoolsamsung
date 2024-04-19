package com.example.myitschoolsamsung;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeScreen extends AppCompatActivity{

    String[][] resultTickets;
    ListView listView;
    int ticketsCount;
    Button profileBut, basketBut, calendarBut, countPassBut, filtersBut;
    EditText fromWhere, toWhere;

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
                        TicketAdapter ticketAdapter = new TicketAdapter(getApplicationContext(), ticketMatrix);
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

        Typeface tf_Raleway = ResourcesCompat.getFont(getApplicationContext(), R.font.raleway);
        Typeface tf_RalewaySemibold = ResourcesCompat.getFont(getApplicationContext(), R.font.raleway_semibold);

        profileBut = (Button) findViewById(R.id.profile);
        basketBut = (Button) findViewById(R.id.basket);
        calendarBut = (Button) findViewById(R.id.calendar);
        countPassBut = (Button) findViewById(R.id.countPass);
        filtersBut = (Button) findViewById(R.id.filters);

        fromWhere = (EditText) findViewById(R.id.from_Where);
        toWhere = (EditText) findViewById(R.id.to_Where);

        profileBut.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LogIn.class);
            startActivity(intent);
        });
        responseTickets("Syktyvkar", "Moscow", "*", "null_1_null_any_null");


//        listView = findViewById(R.id.list_view);
//        TicketAdapter ticketAdapter = new TicketAdapter(getApplicationContext(), resultTickets);
//        listView.setAdapter(ticketAdapter);

//        basketBut.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(), Basket.class);
//            startActivity(intent);
//        });

//        profileBut.setOnClickListener(view -> {
//
//        });
    }
}
