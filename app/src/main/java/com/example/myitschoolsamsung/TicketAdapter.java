package com.example.myitschoolsamsung;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketAdapter extends ArrayAdapter<String[]> {
    final static int CONST_ID_TICKETID = 0;
    final static int CONST_ID_FROMWHERE = 1;
    final static int CONST_ID_TOWHERE = 2;
    final static int CONST_ID_MONTH = 3;
    final static int CONST_ID_DATE = 4;
    final static int CONST_ID_TIMEOUT = 5;
    final static int CONST_ID_TIMETO = 6;
    final static int CONST_ID_ISCHILD = 7;
    final static int CONST_ID_ISKID = 8;
    final static int CONST_ID_CLASS = 9;
    final static int CONST_ID_BAGGAGE = 10;
    final static int CONST_ID_TICKETSCOUNT = 11;
    final static int CONST_ID_PRICE = 12;
    public TicketAdapter(Context context, String[][] data){
        super(context, R.layout.ticket, data);
    }

    public int setBackTickets(String city){
        if (city.equalsIgnoreCase("Sochi")) return R.drawable.so4i;
        if (city.equalsIgnoreCase("Skrt")) return R.drawable.syk;
        if (city.equalsIgnoreCase("Moscow")) return R.drawable.msk__1_;
        if (city.equalsIgnoreCase("SPB")) return R.drawable.spb;

        return 0;
    }

    public View getView(
            int position,
            View convertView,
            ViewGroup parent
    ){
        String[] lineData = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ticket, null);
        }

        TextView tv_Month = convertView.findViewById(R.id.tv_dateMonth);
        TextView tv_Date = convertView.findViewById(R.id.tv_dateNum);
        TextView tv_Price = convertView.findViewById(R.id.tv_Price);
        TextView tv_Time = convertView.findViewById(R.id.tv_Time);
        LinearLayout ll_BackTickets = convertView.findViewById(R.id.lv_Background);

        if (setBackTickets(lineData[CONST_ID_TOWHERE]) != 0) ll_BackTickets.setBackground(getDrawable(getContext(), setBackTickets(lineData[CONST_ID_TOWHERE])));

        tv_Month.setText(lineData[CONST_ID_MONTH]);
        tv_Date.setText(lineData[CONST_ID_DATE]);
        tv_Price.setText(lineData[CONST_ID_PRICE] + "₽");
        tv_Time.setText(lineData[CONST_ID_TIMEOUT] + " - " + lineData[CONST_ID_TIMETO]);


        return convertView;
    }

}
