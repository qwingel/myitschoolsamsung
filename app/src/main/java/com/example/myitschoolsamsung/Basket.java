package com.example.myitschoolsamsung;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Basket extends AppCompatActivity {
    final static int CONST_ID_TICKETID = 0;
    final static int CONST_ID_FROMWHERE = 1;
    final static int CONST_ID_TOWHERE = 2;
    final static int CONST_ID_DATE = 3;
    final static int CONST_ID_ISCHILD = 4;
    final static int CONST_ID_ISKID = 5;
    final static int CONST_ID_CLASS = 6;
    final static int CONST_ID_BAGGAGE = 7;
    final static int CONST_ID_TICKETSCOUNT = 8;
    public static void addNewTicket(String[] data){
        
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket);
        getSupportActionBar().hide();
    }
}
