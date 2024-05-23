package com.example.myitschoolsamsung;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Basket extends AppCompatActivity {
    ListView listView;
    TextView tv_dTicketsCount;
    Button backToTickets;

    String[] sz_Cities = {"Сыктывкар", "Москва", "Санкт-Петербург", "Сочи"};
    public String getCity(String city){
        return city.equalsIgnoreCase("Сыктывкар") ? city = "Sktr" : city.equalsIgnoreCase("Сочи") ? city = "Sochi" : city.equalsIgnoreCase("Санкт-Петербург") ? city = "SPB" : "Moscow";
    }

    public void responseTickets(String[] ids){
        String toResponse = "";
        for(int i = 0; i < ids.length; i++){
            toResponse += ids[i] + " ";
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestToServe.SQurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestToServe.UserService userService = retrofit.create(RequestToServe.UserService.class);
        Call<RequestToServe.ResponseDelayedTickets> getTickets = userService.dtickets(new RequestToServe.DelayedTicketsRequest(toResponse));
        getTickets.enqueue(new Callback<RequestToServe.ResponseDelayedTickets>() {
            @Override
            public void onResponse(Call<RequestToServe.ResponseDelayedTickets> call, Response<RequestToServe.ResponseDelayedTickets> response) {
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
                        listView = findViewById(R.id.list_view_d);
                        TicketAdapter ticketAdapter = new TicketAdapter(getApplicationContext(), R.id.list_view_d,ticketMatrix);
                        listView.setAdapter(ticketAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<RequestToServe.ResponseDelayedTickets> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.basket);
        getSupportActionBar().hide();
        String[] ids = RequestToServe.getIds();
        if (ids.length > 0) {
            tv_dTicketsCount = (TextView) findViewById(R.id.dTicketsCount);
            tv_dTicketsCount.setText("Отложенные\nбилеты (" + ids.length + ")");
            responseTickets(RequestToServe.getIds());
        }


        backToTickets = (Button) findViewById(R.id.toTicketsActivity);

        backToTickets.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_out_right, R.anim.slide_in_right);
        });
    }
}
